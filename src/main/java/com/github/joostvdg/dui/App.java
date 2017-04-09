package com.github.joostvdg.dui;


public class App {
    public String getGreeting() {
        return "Hello cruel world.";
    }

    public static void main(String[] args) {
        System.out.println(new App().getGreeting());
    }
}
