package ru.tsu.myweather.models

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import android.util.Log
import ru.tsu.myweather.Api

object DBCities : BaseColumns {
    const val TABLE_NAME = "cities"
    const val COLUMN_NAME_CITY = "city"

    const val SQL_CREATE_ENTRIES =
        "CREATE TABLE $TABLE_NAME (${BaseColumns._ID} INTEGER PRIMARY KEY,$COLUMN_NAME_CITY TEXT)"
    const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS $TABLE_NAME"
}

object DBWeather{
    const val TABLE_NAME = "weather"
    const val REQUEST_TYPE = "request_type"
    const val REQUEST_QUERY = "query"
    const val REQUEST_LANGUAGE = "language"
    const val REQUEST_UNIT = "unit"
    const val LOCATION_NAME = "name"
    const val LOCATION_COUNTRY = "country"
    const val LOCATION_REGION = "region"
    const val LOCATION_LAT = "lat"
    const val LOCATION_LON = "lon"
    const val LOCATION_TIMEZONE_ID = "timezone_id"
    const val LOCATION_LOCALTIME = "localtime"
    const val LOCATION_LOCALTIME_EPOCH = "localtime_epoch"
    const val LOCATION_UTC_OFFSET = "utc_offset"
    const val CURRENT_OBSERVATION_TIME = "observation_time"
    const val CURRENT_TEMPERATURE = "temperature"
    const val CURRENT_WEATHER_CODE = "weather_code"
    const val CURRENT_WEATHER_ICONS = "weather_icons"
    const val CURRENT_WEATHER_DESCRIPTIONS = "weather_descriptions"
    const val CURRENT_WIND_SPEED = "wind_speed"
    const val CURRENT_WIND_DEGREE = "wind_degree"
    const val CURRENT_WIND_DIR = "wind_dir"
    const val CURRENT_PRESSURE = "pressure"
    const val CURRENT_PRECIP = "precip"
    const val CURRENT_HUMIDITY = "humidity"
    const val CURRENT_CLOUDCOVER = "cloudcover"
    const val CURRENT_FEELSLIKE = "feelslike"
    const val CURRENT_UV_INDEX = "uv_index"
    const val CURRENT_VISIBILITY = "visibility"
    const val CURRENT_IS_DAY = "is_day"
    const val ERROR_CODE = "code"
    const val ERROR_TYPE = "type"
    const val ERROR_INFO = "info"
    const val SQL_CREATE_ENTRIES =
        "CREATE TABLE $TABLE_NAME (${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "$REQUEST_TYPE TEXT," +
                "$REQUEST_QUERY TEXT," +
                "$REQUEST_LANGUAGE TEXT," +
                "$REQUEST_UNIT TEXT," +
                "$LOCATION_NAME TEXT," +
                "$LOCATION_COUNTRY TEXT," +
                "$LOCATION_REGION TEXT," +
                "$LOCATION_LAT TEXT," +
                "$LOCATION_LON TEXT," +
                "$LOCATION_TIMEZONE_ID TEXT," +
                "$LOCATION_LOCALTIME TEXT," +
                "$LOCATION_LOCALTIME_EPOCH TEXT," +
                "$LOCATION_UTC_OFFSET TEXT," +
                "$CURRENT_OBSERVATION_TIME TEXT," +
                "$CURRENT_TEMPERATURE TEXT," +
                "$CURRENT_WEATHER_CODE TEXT," +
                "$CURRENT_WEATHER_ICONS TEXT," +
                "$CURRENT_WEATHER_DESCRIPTIONS TEXT," +
                "$CURRENT_WIND_SPEED TEXT," +
                "$CURRENT_WIND_DEGREE TEXT," +
                "$CURRENT_WIND_DIR TEXT," +
                "$CURRENT_PRESSURE TEXT," +
                "$CURRENT_PRECIP TEXT," +
                "$CURRENT_HUMIDITY TEXT," +
                "$CURRENT_CLOUDCOVER TEXT," +
                "$CURRENT_FEELSLIKE TEXT," +
                "$CURRENT_UV_INDEX TEXT," +
                "$CURRENT_VISIBILITY TEXT," +
                "$CURRENT_IS_DAY TEXT," +
                "$ERROR_CODE TEXT," +
                "$ERROR_TYPE TEXT," +
                "$ERROR_INFO TEXT)"
    const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS $TABLE_NAME"
}

class WeatherDBHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    val ctx = context
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(DBWeather.SQL_CREATE_ENTRIES)
        db.execSQL(DBCities.SQL_CREATE_ENTRIES)
        val values = ContentValues().apply {
            put(DBCities.COLUMN_NAME_CITY, "fetch:ip")
        }
        db.insert(DBCities.TABLE_NAME, null, values)
        Api.beginSearch(ctx, "fetch:ip")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(DBWeather.SQL_DELETE_ENTRIES)
        db.execSQL(DBCities.SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "weather.db"
        fun insertWeather(ctx : Context, data : Data.Model){
            val dbHelper = WeatherDBHelper(ctx)
            val db = dbHelper.writableDatabase

            val values = ContentValues().apply {
                if(data.request != null) {
                    put(DBWeather.REQUEST_TYPE, data.request.type)
                    put(DBWeather.REQUEST_QUERY, data.request.query)
                    put(DBWeather.REQUEST_LANGUAGE, data.request.language)
                    put(DBWeather.REQUEST_UNIT, data.request.unit)
                    put(DBWeather.LOCATION_NAME, data.location.name)
                    put(DBWeather.LOCATION_COUNTRY, data.location.country)
                    put(DBWeather.LOCATION_REGION, data.location.region)
                    put(DBWeather.LOCATION_LAT, data.location.lat)
                    put(DBWeather.LOCATION_LON, data.location.lon)
                    put(DBWeather.LOCATION_TIMEZONE_ID, data.location.timezone_id)
                    put(DBWeather.LOCATION_LOCALTIME, data.location.localtime)
                    put(DBWeather.LOCATION_LOCALTIME_EPOCH, data.location.localtime_epoch)
                    put(DBWeather.LOCATION_UTC_OFFSET, data.location.utc_offset)
                    put(DBWeather.CURRENT_OBSERVATION_TIME, data.current.observation_time)
                    put(DBWeather.CURRENT_TEMPERATURE, data.current.temperature)
                    put(DBWeather.CURRENT_WEATHER_CODE, data.current.weather_code)
                    put(DBWeather.CURRENT_WEATHER_ICONS, data.current.weather_icons[0])
                    put(
                        DBWeather.CURRENT_WEATHER_DESCRIPTIONS,
                        data.current.weather_descriptions[0]
                    )
                    put(DBWeather.CURRENT_WIND_SPEED, data.current.wind_speed)
                    put(DBWeather.CURRENT_WIND_DEGREE, data.current.wind_degree)
                    put(DBWeather.CURRENT_WIND_DIR, data.current.wind_dir)
                    put(DBWeather.CURRENT_PRESSURE, data.current.pressure)
                    put(DBWeather.CURRENT_PRECIP, data.current.precip)
                    put(DBWeather.CURRENT_HUMIDITY, data.current.humidity)
                    put(DBWeather.CURRENT_CLOUDCOVER, data.current.cloudcover)
                    put(DBWeather.CURRENT_FEELSLIKE, data.current.feelslike)
                    put(DBWeather.CURRENT_UV_INDEX, data.current.uv_index)
                    put(DBWeather.CURRENT_VISIBILITY, data.current.visibility)
                    put(DBWeather.CURRENT_IS_DAY, data.current.is_day)
                }
                if(data.error != null){
                    put(DBWeather.ERROR_CODE, data.error.code)
                    put(DBWeather.ERROR_TYPE, data.error.type)
                    put(DBWeather.ERROR_INFO, data.error.info)
                }
            }
            db?.insert(DBWeather.TABLE_NAME, null, values)
        }

        fun getSize(ctx: Context) : Int{
            val dbHelper = WeatherDBHelper(ctx)
            val db = dbHelper.readableDatabase
            val projection = arrayOf(BaseColumns._ID, DBCities.COLUMN_NAME_CITY)
            val cursor = db.query(
                DBCities.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
            )
            cursor.moveToLast()
            return cursor.position + 1
        }
    }
}