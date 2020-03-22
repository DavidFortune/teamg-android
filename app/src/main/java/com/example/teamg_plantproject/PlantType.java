package com.example.teamg_plantproject;

public class PlantType {
    private String typeName;
    private int airHumidity;
    private int airTemperature;
    private int soilMoisture;

    public PlantType() {
    }

    public PlantType(String typeName, int airHumidity, int airTemperature, int soilMoisture) {
        this.typeName = typeName;
        this.airHumidity = airHumidity;
        this.airTemperature = airTemperature;
        this.soilMoisture = soilMoisture;
    }

    public String getTypeName() { return typeName; }

    public void setTypeName(String typeName) { this.typeName = typeName; }

    public int getAirHumidity() { return airHumidity; }

    public void setAirHumidity(int airHumidity) { this.airHumidity = airHumidity; }

    public int getAirTemperature() { return airTemperature; }

    public void setAirTemperature(int airTemperature) { this.airTemperature = airTemperature; }

    public int getSoilMoisture() { return soilMoisture; }

    public void setSoilMoisture(int soilMoisture) { this.soilMoisture = soilMoisture; }


}
