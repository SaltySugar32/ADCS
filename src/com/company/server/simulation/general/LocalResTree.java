package com.company.server.simulation.general;

import java.util.ArrayList;
import java.util.List;

public class LocalResTree {

    public static int markerCounter = 1;

    public int marker;
    public ArrayList<String> result;
    public List<LocalResTree> children;
    public boolean isCollapsed;
    public String collision;
    public ArrayList<String> waveFrontStrings;

    public LocalResTree(ArrayList<String> result, String collision) {
        this.marker = markerCounter++;
        this.result = result;
        this.children = new ArrayList<>();
        this.isCollapsed = true;
        this.collision = collision;
        this.waveFrontStrings = new ArrayList<>();
    }

    public static void resetMarkerCounter() {
        markerCounter = 1;
    }

    @Override
    public String toString() {
        return String.valueOf(marker);
    }
}
