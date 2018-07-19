package danielferrandez.com.citiesoftheworld.db

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import danielferrandez.com.citiesoftheworld.model.CityModel
import danielferrandez.com.citiesoftheworld.model.Country
import java.util.*

class CitiesDBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    @Throws(SQLiteConstraintException::class)
    fun insertCity(cityModel: CityModel): Boolean {
        // Gets the data repository in write mode
        val db = writableDatabase

        // Create a new map of values, where column names are the keys
        val values = ContentValues()
        values.put(DBContract.CityEntity.COLUMN_CITY_ID, cityModel.id)
        values.put(DBContract.CityEntity.COLUMN_CITY_NAME, cityModel.name)
        values.put(DBContract.CityEntity.COLUMN_CITY_LOCAL_NAME, cityModel.local_name)
        values.put(DBContract.CityEntity.COLUMN_CITY_LAT, cityModel.lat)
        values.put(DBContract.CityEntity.COLUMN_CITY_LNG, cityModel.lng)
        values.put(DBContract.CityEntity.COLUMN_CITY_CREATED, cityModel.created_at)
        values.put(DBContract.CityEntity.COLUMN_CITY_UPDATED, cityModel.updated_at)
        values.put(DBContract.CityEntity.COLUMN_COUNTRY_ID, cityModel.country.id)
        values.put(DBContract.CityEntity.COLUMN_COUNTRY_NAME, cityModel.country.name)
        values.put(DBContract.CityEntity.COLUMN_COUNTRY_CREATED, cityModel.country.created_at)
        values.put(DBContract.CityEntity.COLUMN_COUNTRY_UPDATED, cityModel.country.updated_at)
        values.put(DBContract.CityEntity.COLUMN_COUNTRY_CONTINENT_ID, cityModel.country.continent_id)

        // Insert the new row, returning the primary key value of the new row
        db.insert(DBContract.CityEntity.TABLE_NAME, null, values)

        return true
    }

    fun truncateTable(){
        val db = writableDatabase
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    @SuppressLint("Recycle")
    fun selectAllCities(perPage: Int, page: Int): ArrayList<CityModel> {
        var offset = perPage*(page-1)
        var cities = ArrayList<CityModel>()
        val db = writableDatabase
        var cursor: Cursor
        try {
            cursor = db.rawQuery("select * from " + DBContract.CityEntity.TABLE_NAME + " limit "+perPage+" offset " + offset, null)
        } catch (e: SQLiteException) {
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var id: Int
        var name: String
        var local_name: String
        var lat: Double
        var lng: Double
        var created_at: String
        var updated_at: String
        var country_id: Int
        var country: Country
        if (cursor!!.moveToFirst()) {
            while (!cursor.isAfterLast) {
                id = cursor.getInt(cursor.getColumnIndex(DBContract.CityEntity.COLUMN_CITY_ID))
                name = cursor.getString(cursor.getColumnIndex(DBContract.CityEntity.COLUMN_CITY_NAME))
                local_name = cursor.getString(cursor.getColumnIndex(DBContract.CityEntity.COLUMN_CITY_LOCAL_NAME))
                lat = cursor.getDouble(cursor.getColumnIndex(DBContract.CityEntity.COLUMN_CITY_LAT))
                lng = cursor.getDouble(cursor.getColumnIndex(DBContract.CityEntity.COLUMN_CITY_LNG))
                created_at = cursor.getString(cursor.getColumnIndex(DBContract.CityEntity.COLUMN_CITY_CREATED))
                updated_at = cursor.getString(cursor.getColumnIndex(DBContract.CityEntity.COLUMN_CITY_UPDATED))
                country_id = cursor.getInt(cursor.getColumnIndex(DBContract.CityEntity.COLUMN_COUNTRY_ID))
                country = Country(cursor.getInt(cursor.getColumnIndex(DBContract.CityEntity.COLUMN_COUNTRY_ID)),
                        cursor.getString(cursor.getColumnIndex(DBContract.CityEntity.COLUMN_COUNTRY_NAME)),
                        cursor.getString(cursor.getColumnIndex(DBContract.CityEntity.COLUMN_COUNTRY_CREATED)),
                        cursor.getString(cursor.getColumnIndex(DBContract.CityEntity.COLUMN_COUNTRY_UPDATED)),
                        cursor.getInt(cursor.getColumnIndex(DBContract.CityEntity.COLUMN_COUNTRY_CONTINENT_ID)))

                cities.add(CityModel(id, name, local_name, lat, lng, created_at, updated_at, country_id, country))
                cursor.moveToNext()
            }
        }
        return cities
    }

    companion object {
        // If you change the database schema, you must increment the database version.
        val DATABASE_VERSION = 1
        val DATABASE_NAME = "CitiesOfTheWorld.db"

        private val SQL_CREATE_ENTRIES =
                "CREATE TABLE " + DBContract.CityEntity.TABLE_NAME + " (" +
                        DBContract.CityEntity.COLUMN_CITY_ID + " INTEGER," +
                        DBContract.CityEntity.COLUMN_CITY_NAME + " TEXT," +
                        DBContract.CityEntity.COLUMN_CITY_LOCAL_NAME + " TEXT," +
                        DBContract.CityEntity.COLUMN_CITY_LAT + " REAL," +
                        DBContract.CityEntity.COLUMN_CITY_LNG + " REAL," +
                        DBContract.CityEntity.COLUMN_CITY_CREATED + " TEXT," +
                        DBContract.CityEntity.COLUMN_CITY_UPDATED + " TEXT," +
                        DBContract.CityEntity.COLUMN_COUNTRY_ID + " INTEGER," +
                        DBContract.CityEntity.COLUMN_COUNTRY_NAME + " TEXT," +
                        DBContract.CityEntity.COLUMN_COUNTRY_CREATED + " TEXT," +
                        DBContract.CityEntity.COLUMN_COUNTRY_UPDATED + " TEXT," +
                        DBContract.CityEntity.COLUMN_COUNTRY_CONTINENT_ID + " INTEGER)"

        private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DBContract.CityEntity.TABLE_NAME
    }

}