package com.example.teamg_plantproject;

public class Type {

    private int typeID;
    private String typeName;
    private String airHumidity;
    private String airTemperature;
    private String soilMoisture;
    private String sunlight;

    public Type() {
    }

    public Type(int typeID, String typeName, String airHumidity, String airTemperature, String soilMoisture, String sunlight) {
        this.typeID = typeID;
        this.typeName = typeName;
        this.airHumidity = airHumidity;
        this.airTemperature = airTemperature;
        this.soilMoisture = soilMoisture;
        this.sunlight = sunlight;
    }

    public int getTypeID() {
        return typeID;
    }

    public void setTypeID(int typeID) {
        this.typeID = typeID;
    }

    public String getTypeName() { return typeName; }

    public void setTypeName(String typeName) { this.typeName = typeName; }

    public String getAirHumidity() { return airHumidity; }

    public void setAirHumidity(String airHumidity) { this.airHumidity = airHumidity; }

    public String getAirTemperature() { return airTemperature; }

    public void setAirTemperature(String airTemperature) { this.airTemperature = airTemperature; }

    public String getSoilMoisture() { return soilMoisture; }

    public void setSoilMoisture(String soilMoisture) { this.soilMoisture = soilMoisture; }

    public String getSunlight() { return sunlight; }

    public void setSunlight(String sunlight) { this.sunlight = sunlight; }

}
