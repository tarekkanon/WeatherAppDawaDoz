package com.example.kanon.weatherapp.DAL;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface WeatherDAO {

    @Query("SELECT * FROM Weather")
    List<Weather> getAll();

    @Query("SELECT * FROM Weather WHERE id IN (:weatherIds)")
    List<Weather> loadAllByIds(int[] weatherIds);

    @Query("SELECT * FROM Weather WHERE city_id = :cityId")
    Weather getAllByCity(int cityId);

    @Query("DELETE FROM Weather")
    void truncate();

    @Insert
    void insertAll(Weather... weathers);

    @Delete
    void delete(Weather weather);
}
