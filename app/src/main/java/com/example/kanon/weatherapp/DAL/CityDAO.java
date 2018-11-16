package com.example.kanon.weatherapp.DAL;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface CityDAO {

    @Query("SELECT * FROM City")
    List<City> getAll();

    @Query("SELECT Count(id) FROM City")
    int getCount();

    @Query("SELECT * FROM City WHERE id IN (:cityIds)")
    List<City> loadAllByIds(int[] cityIds);

    @Query("SELECT * FROM City WHERE city_name LIKE :cityName LIMIT 1")
    City findByName(String cityName);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(City... cities);

    @Delete
    void delete(City city);

}
