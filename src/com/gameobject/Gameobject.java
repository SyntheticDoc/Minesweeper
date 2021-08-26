package com.gameobject;

public class Gameobject {
    public final String type = "Gameobject";
    public final String name;

    protected Gameobject(String name) {
        this.name = name;
    }

    protected String enumerateAttributes() {
        return "name=" + name;
    }

    @Override
    public String toString() {
        return type + "[" + enumerateAttributes() + "]";
    }
}
