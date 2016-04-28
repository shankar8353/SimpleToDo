package com.codepath.simpletodo;


public enum Status {
    DONE("DONE"),
    TODO("TO-DO");

    private String description;

    Status(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}
