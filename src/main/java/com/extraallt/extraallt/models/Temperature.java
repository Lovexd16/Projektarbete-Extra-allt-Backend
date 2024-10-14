package com.extraallt.extraallt.models;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Temperatures")
public class Temperature {

    @Id
    private String temperatureId;
    private double celcius;
    private LocalDateTime timestamp;

    public Temperature(String temperatureId, double celcius, LocalDateTime timestamp) {
        this.temperatureId = temperatureId;
        this.celcius = celcius;
        this.timestamp = timestamp;
    }

    public String getTemperatureId() {
        return temperatureId;
    }

    public void setTemperatureId(String temperatureId) {
        this.temperatureId = temperatureId;
    }

    public double getCelcius() {
        return celcius;
    }

    public void setCelcius(double celcius) {
        this.celcius = celcius;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

}
