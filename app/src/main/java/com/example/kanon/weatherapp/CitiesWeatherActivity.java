package com.example.kanon.weatherapp;

import android.arch.persistence.room.Room;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kanon.weatherapp.Constants.ConstantValues;
import com.example.kanon.weatherapp.DAL.AppDatabase;
import com.example.kanon.weatherapp.DAL.City;
import com.example.kanon.weatherapp.DAL.Weather;
import com.example.kanon.weatherapp.LayoutAdapters.CityListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CitiesWeatherActivity extends AppCompatActivity {

    JSONObject responseJSON;
    AppDatabase db;

    RecyclerView citiesListView;
    CityListAdapter cityListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cities_weather);

        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, ConstantValues.DBName).fallbackToDestructiveMigration().allowMainThreadQueries().build();

        initComponents();

        CallAPI5Cities();
    }

    public void initComponents()
    {
        citiesListView = (RecyclerView) findViewById(R.id.CitiesTempRecyclerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        citiesListView.setLayoutManager(mLayoutManager);
        citiesListView.setItemAnimator(new DefaultItemAnimator());
        citiesListView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
    }

    public void CallAPI5Cities()
    {
        String url = ConstantValues.WeatherAPIBaseLink + ConstantValues.ExampleCityList + ConstantValues.WeatherAPIKey;

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {
                    responseJSON = new JSONObject(response);

                    JSONArray listOfCountryWeather = responseJSON.getJSONArray("list");

                    List<City> cities = new ArrayList<City>();

                    db.weatherDAO().truncate();

                    for (int i = 0; i < listOfCountryWeather.length(); i++) {

                        JSONObject c = listOfCountryWeather.getJSONObject(i);

                        City city = new City();
                        city.id = c.getInt("id");
                        city.Name = c.getString("name");

                        cities.add(city);

                        JSONObject mainNode = c.getJSONObject("main");

                        Weather cityWeather = new Weather();
                        cityWeather.CityId = city.id;
                        cityWeather.Temperature = mainNode.getDouble("temp");
                        cityWeather.MaxTemperature = mainNode.getDouble("temp_max");
                        cityWeather.MinTemperature = mainNode.getDouble("temp_min");

                        db.cityDAO().insertAll(city);
                        db.weatherDAO().insertAll(cityWeather);

                    }

                    cityListAdapter = new CityListAdapter(cities, getApplicationContext());

                    citiesListView.setAdapter(cityListAdapter);

                    cityListAdapter.notifyDataSetChanged();


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

//                        List<City> cities = db.cityDAO().getAll();
//
//                        cityListAdapter = new CityListAdapter(cities, getApplicationContext());
//
//                        citiesListView.setAdapter(cityListAdapter);
//
//                        cityListAdapter.notifyDataSetChanged();
                    }
                }
        );

        requestQueue.add(stringRequest);
    }
}
