package com.example.lab4_20202396.network;

import com.example.lab4_20202396.models.ForecastModel;
import com.example.lab4_20202396.models.LocationModel;
import com.example.lab4_20202396.models.SportsModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApiService {
    @GET("search.json")
    Call<List<LocationModel>> searchLocations(
            @Query("key") String apiKey,
            @Query("q") String query
    );
    @GET("forecast.json")
    Call<ForecastModel> getForecast(
            @Query("key") String apiKey,
            @Query("q") String query,
            @Query("days") int days
    );
    @GET("sports.json")
    Call<SportsModel> getSports(
            @Query("key") String apiKey,
            @Query("q") String location
    );


}
