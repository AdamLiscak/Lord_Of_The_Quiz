package com.example.findyourprivategrandpa.Models;

import java.util.HashMap;

public class User {
    private String name;
    private HashMap<Quiz,Integer> scores;
    private Quiz[] authoredQuizzes;
}
