package com.example.teamg_plantproject;

public class Plant {

    private int plantID;
    private String plantName;
    private String plantType;
    private String sensorId;

    public Plant() {
    }

    public Plant(int plantID, String plantName, String plantType, String waterTime) {
        this.plantID = plantID;
        this.plantName = plantName;
        this.plantType = plantType;
        this.sensorId = sensorId;
    }

    public int getPlantID() {
        return plantID;
    }

    public void setPlantID(int plantID) {
        this.plantID = plantID;
    }

    public String getPlantName() {
        return plantName;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }

    public String getPlantType() {
        return plantType;
    }

    public void setPlantType(String plantType) {
        this.plantType = plantType;
    }

    public String getSensorId() {
        return sensorId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }
}
