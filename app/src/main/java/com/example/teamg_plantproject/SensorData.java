package com.example.teamg_plantproject;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class SensorData {
    private double rawHumidity;
    private double rawSoilValue;
    private double rawSolarValue;
    private double rawTemp;
    private Date createdAt;

    public SensorData() {}  //empty controctor needed


    public SensorData(double rawHumidity, double rawSoilValue, double rawSolarValue, double rawTemp, Date createdAt) {
        this.rawHumidity = rawHumidity;
        this.rawSoilValue = rawSoilValue;
        this.rawSolarValue = rawSolarValue;
        this.rawTemp = rawTemp;
        this.createdAt = createdAt;
    }

    public double getrawHumidity() {
        return rawHumidity;
    }

    public double getrawSoilValue() {
        return rawSoilValue;
    }

    public double getrawSolarValue() {
        return rawSolarValue;
    }

    public double getrawTemp() {
        return rawTemp;
    }

    public void setRawTemp(double rawTemp) {
        this.rawTemp = rawTemp;
    }

    @ServerTimestamp
    public Date getCreatedAt() {
        return createdAt;
    }

    public void setrawHumidity(double rawHumidity) {
        this.rawHumidity = rawHumidity;
    }

    public void setrawSoilValue(double rawSoilValue) {
        this.rawSoilValue = rawSoilValue;
    }

    public void setrawSolarValue(double rawSolarValue) {
        this.rawSolarValue = rawSolarValue;
    }

    public void setrawTemp(double rawTemp) {
        this.rawTemp = rawTemp;
    }

    public void setcreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
