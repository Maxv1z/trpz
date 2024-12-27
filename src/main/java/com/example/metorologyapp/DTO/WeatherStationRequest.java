package com.example.metorologyapp.DTO;

public class WeatherStationRequest {
    private String name;
    private String location;
    private double temperature;
    private double humidity;

    public WeatherStationRequest() {}

    public WeatherStationRequest(String name, String location, double temperature, double humidity) {
        this.name = name;
        this.location = location;
        this.temperature = temperature;
        this.humidity = humidity;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }
}
