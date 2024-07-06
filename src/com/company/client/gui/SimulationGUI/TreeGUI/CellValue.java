package com.company.client.gui.SimulationGUI.TreeGUI;

public class CellValue {
    int marker;
    String label;

    CellValue(int marker, String label) {
        this.marker = marker;
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
