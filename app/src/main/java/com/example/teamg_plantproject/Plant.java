package com.example.teamg_plantproject;

public class Plant {

    private int plantID;
    private String plantName;
    private String plantState;
    private String waterTime;

    public Plant() {
    }

    public Plant(int plantID, String plantName, String plantState, int plantFave, String waterTime) {
        this.plantID = plantID;
        this.plantName = plantName;
        this.plantState = plantState;
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

    public String getPlantState() {
        return plantState;
    }

    public void setPlantState(String plantState) {
        this.plantState = plantState;
    }

    public String getWaterTime() {
        return waterTime;
    }

    public void setWaterTime(String waterTime) {
        this.waterTime = waterTime;
    }
}
