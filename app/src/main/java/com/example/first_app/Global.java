package com.example.first_app;

import java.util.Stack;

public class Global {
    public static Stack<String> prev_url = new Stack<>();
    public static Stack<String> next_url = new Stack<>();
    public static final String home = "https://duckduckgo.com/";
    public static String actual = home;
    public static boolean from_arrow = false;
}
