package com.example.owner.lab9;

public class Weather {
    private String date, description, temperature;

    public String getDate() {
        return date;
    }

    public String getWeather_description() {
        return description;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setDate(String _date) {
        date = _date;
    }

    public void setWeather_description(String _description) {
        description = _description;
    }

    public void setTemperature(String _temperature) {
        temperature = _temperature;
    }
}
