package configuration;

import base.State;

import java.util.ArrayList;

public enum Configuration {
    instance;

    public ArrayList<State> states = new ArrayList<>();

    public static final int CELL_SIZE = 8;
    public static final int GRID_SIZE = 100;

}
