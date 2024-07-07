package com.company.client.gui.SimulationGUI.TreeGUI;

import java.util.ArrayList;
import java.util.List;

public class LocalResTree {
    public int marker;
    public ArrayList<String> result;
    public List<LocalResTree> children;
    boolean isCollapsed;
    public String collision;

    public LocalResTree(int marker, ArrayList<String> result,String collision){
        this.marker = marker;
        this.result = result;
        this.children = new ArrayList<>();
        this.isCollapsed = false;
        this.collision = collision;
    }

    public LocalResTree(int marker, String result,String collision, List<LocalResTree> children){
        this.marker = marker;
        ArrayList<String> results = new ArrayList<>();
        results.add(result);
        this.result = results;
        this.children = children;
        this.isCollapsed = false;
        this.collision = collision;
    }

    @Override
    public String toString() {
        return String.valueOf(marker);
    }
}
