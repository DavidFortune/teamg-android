package com.example.teamg_plantproject;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class SensorData {
    private double rawHumidity;
    private double rawSoilValue;
    private double rawSolarValue;
    private double rawTemp;
    private Date createdAt;

    public SensorData() {
        //empty controctor needed
    }

    public SensorData(double rawHumidity, double rawSoilValue, double rawSolarValue, double rawTemp, Date createdAt) {
        this.rawHumidity = rawHumidity;
        this.rawSoilValue = rawSoilValue;
        this.rawSolarValue = rawSolarValue;
        this.rawTemp = rawTemp;
        this.createdAt = createdAt;
    }

    public double getRawHumidity() {
        return rawHumidity;
    }

    public void setRawHumidity(double rawHumidity) {
        this.rawHumidity = rawHumidity;
    }

    public double getRawSoilValue() {
        return rawSoilValue;
    }

    public void setRawSoilValue(double rawSoilValue) {
        this.rawSoilValue = rawSoilValue;
    }

    public double getRawSolarValue() {
        return rawSolarValue;
    }

    public void setRawSolarValue(double rawSolarValue) {
        this.rawSolarValue = rawSolarValue;
    }

    public double getRawTemp() {
        return rawTemp;
    }

    public void setRawTemp(double rawTemp) {
        this.rawTemp = rawTemp;
    }

    @ServerTimestamp
    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
