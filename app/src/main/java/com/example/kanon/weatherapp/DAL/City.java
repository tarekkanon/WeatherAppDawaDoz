package com.example.kanon.weatherapp.DAL;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.List;

@Entity
public class City {

    @PrimaryKey
    public int id;

    @ColumnInfo(name = "city_name")
    public String Name;

}
