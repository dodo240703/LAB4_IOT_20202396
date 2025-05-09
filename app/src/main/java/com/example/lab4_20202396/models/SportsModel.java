package com.example.lab4_20202396.models;

import java.util.List;

public class SportsModel {
    public Football football;

    public static class Football {
        public List<Match> match;
    }

    public static class Match {
        public String tournament;
        public String start;
        public String stadium;
        public String team1;
        public String team2;
    }
}
