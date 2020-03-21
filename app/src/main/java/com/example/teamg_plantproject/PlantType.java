package com.example.teamg_plantproject;

public class PlantType {
    private int typeID;
    private String typeName;
    private int airHumidity;
    private int airTemperature;
    private int soilMoisture;

    public PlantType() {
    }

    public PlantType(int typeID, String typeName, int airHumidity, int airTemperature, int soilMoisture) {
        this.typeID = typeID;
        this.typeName = typeName;
        this.airHumidity = airHumidity;
        this.airTemperature = airTemperature;
        this.soilMoisture = soilMoisture;
    }

    public int getTypeID() {
        return typeID;
    }

    public void setTypeID(int typeID) {
        this.typeID = typeID;
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
