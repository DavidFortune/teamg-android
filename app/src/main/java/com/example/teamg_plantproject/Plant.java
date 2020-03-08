package com.example.teamg_plantproject;

public class Plant {

    private int plantID;
    private String plantName;
    private String plantType;
    private String waterTime;

    public Plant() {
    }

    public Plant(int plantID, String plantName, String plantType, String waterTime) {
        this.plantID = plantID;
        this.plantName = plantName;
        this.plantType = plantType;
        this.waterTime = waterTime;
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

    public String getWaterTime() {
        return waterTime;
    }

    public void setWaterTime(String waterTime) {
        this.waterTime = waterTime;
    }
}
