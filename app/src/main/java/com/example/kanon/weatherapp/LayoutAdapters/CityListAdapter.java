package com.example.kanon.weatherapp.LayoutAdapters;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kanon.weatherapp.Constants.ConstantValues;
import com.example.kanon.weatherapp.DAL.AppDatabase;
import com.example.kanon.weatherapp.DAL.City;
import com.example.kanon.weatherapp.R;

import java.util.List;

public class CityListAdapter extends RecyclerView.Adapter<CityListAdapter.ListViewHolder> {

    private List<City> cities;
    AppDatabase db;

    public CityListAdapter(List<City> allCities, Context currentContext)
    {
        cities = allCities;
        db = Room.databaseBuilder(currentContext, AppDatabase.class, ConstantValues.DBName).fallbackToDestructiveMigration().allowMainThreadQueries().build();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {

        public TextView cityName, currentTemp;

        public ListViewHolder(View itemView) {
            super(itemView);
            cityName = (TextView) itemView.findViewById(R.id.row_city_list_name);
            currentTemp = (TextView) itemView.findViewById(R.id.row_city_list_temp);
        }
    }

    @NonNull
    @Override
    public CityListAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cities_list_row, parent,false);

        return new ListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CityListAdapter.ListViewHolder holder, int position) {

        City c = cities.get(position);

        holder.cityName.setText(c.Name);
        holder.currentTemp.setText("Temperature : " + db.weatherDAO().getAllByCity(c.id).Temperature + "c");

    }

    @Override
    public int getItemCount() {
        return cities.size();
    }
}
