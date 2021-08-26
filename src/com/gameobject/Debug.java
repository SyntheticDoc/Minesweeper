package com.gameobject;

public class Debug {
    public static boolean debugMode = false;

    public static void print(String msg) {
        if(debugMode) {
            System.out.println("DEBUG: " + msg);
        }
    }

    public static void print(Object o) {
        if(o != null) {
            print(o.toString());
        }
    }
}
