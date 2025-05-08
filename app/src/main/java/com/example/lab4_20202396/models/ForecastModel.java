package com.example.lab4_20202396.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ForecastModel {
    public Location location;
    public Forecast forecast;

    public static class Location {
        public String name;
        public String region;
        public String country;
        public String tz_id;
    }

    public static class Forecast {
        @SerializedName("forecastday")
        public List<ForecastDay> forecastday;
    }

    public static class ForecastDay {
        public String date;
        public Day day;
    }

    public static class Day {
        @SerializedName("maxtemp_c")
        public float maxTempC;
        @SerializedName("mintemp_c")
        public float minTempC;
        public Condition condition;
    }

    public static class Condition {
        public String text;
        public String icon;
    }
}
