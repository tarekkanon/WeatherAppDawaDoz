package com.example.kanon.weatherapp.DAL;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;


@Entity(foreignKeys = @ForeignKey(entity = City.class,
        parentColumns = "id",
        childColumns = "city_id"))
public class Weather {

    @PrimaryKey(autoGenerate = true)
    public int id = 0;

    @ColumnInfo(name = "temperature")
    public double Temperature;

    @ColumnInfo(name = "min_temperature")
    public double MinTemperature;

    @ColumnInfo(name = "max_temperature")
    public double MaxTemperature;

    @ColumnInfo(name = "city_id")
    public int CityId;
}
