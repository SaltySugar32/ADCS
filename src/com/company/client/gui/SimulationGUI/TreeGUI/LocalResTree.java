package com.company.client.gui.SimulationGUI.TreeGUI;

import java.util.ArrayList;
import java.util.List;

public class LocalResTree {
    public int marker;
    public String result;
    public List<LocalResTree> children;

    public LocalResTree(int marker, String result, List<LocalResTree> children){
        this.marker = marker;
        this.result = result;
        this.children = children;
    }
}
