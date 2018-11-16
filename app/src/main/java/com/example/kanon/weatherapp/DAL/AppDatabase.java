package com.example.kanon.weatherapp.DAL;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {City.class, Weather.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase{

    public abstract CityDAO cityDAO();
    public abstract WeatherDAO weatherDAO();

}
