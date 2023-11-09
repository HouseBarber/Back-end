package com.projetoIntegradorII.HouseBarber.enums;

public enum Status {
    OPEN("OPEN"), CLOSED("CLOSED"), SCHEDULE("SCHEDULE"), REQUESTED("REQUESTED");

    private String status;

    Status(String role){
        this.status = role;
    }

    public String getStatus(){
        return status;
    }
}
