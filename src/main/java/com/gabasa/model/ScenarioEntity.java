package com.gabasa.model;

public record ScenarioEntity(int id, String model_name, String plate_no, double daily_rate, String status) {
    @Override
    public String toString(){
        return String.format("[%d] %s %s (%s) - Status: %s", id, model_name, plate_no, daily_rate, status);
    }

}
