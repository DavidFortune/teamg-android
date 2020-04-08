package com.example.teamg_plantproject;

import com.google.firebase.Timestamp;

import java.util.Date;
import java.util.Map;

public class Notification {

    private String topic;
    private String category;
    private String sensorId;
    private String messageId;
    private Timestamp timestamp;
    private Map<String, String> notification;
    private Map<String, String> data;

    private Notification(){

    }

    public Notification(String topic, String category, String sensorId, String messageId, Timestamp timestamp, Map<String, String> notification) {
        this.topic = topic;
        this.category = category;
        this.sensorId = sensorId;
        this.messageId = messageId;
        this.timestamp = timestamp;
        this.notification = notification;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSensorId() {
        return sensorId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Map<String, String> getNotification() {
        return notification;
    }

    public void setNotification(Map<String, String> notification) {
        this.notification = notification;
    }

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }

    public String getDate() {
        Timestamp timestamp = (Timestamp) this.timestamp;
        Date date = timestamp.toDate();

        return date.toString();
    }



}
