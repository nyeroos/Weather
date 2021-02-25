package com.nyeroos.weatherforrtuitlab.data;

public class Data {
    private Coord coord;
    private TempMain main;
    private String name;

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public TempMain getMain() {
        return main;
    }

    public void setMain(TempMain main) {
        this.main = main;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
