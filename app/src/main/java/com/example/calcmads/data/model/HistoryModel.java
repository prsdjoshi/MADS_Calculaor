package com.example.calcmads.data.model;

public class HistoryModel {

    String user_input;
    float result;

    public HistoryModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String id;
    public HistoryModel(String user_input, float result) {
        this.user_input = user_input;
        this.result = result;
    }

    public String getUser_input() {
        return user_input;
    }

    public void setUser_input(String user_input) {
        this.user_input = user_input;
    }

    public float getResult() {
        return result;
    }

    public void setResult(float result) {
        this.result = result;
    }


}
