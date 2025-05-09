package com.example.lab4_20202396.models;

import java.util.List;

public class SportsModel {
    public List<Match> football;

    public static class Match {
        public String tournament;
        public String start;
        public String stadium;
        public String match;     // Ejemplo: "Chelsea vs Djurgardens IF"
        public String country;
        public String region;
    }
}
