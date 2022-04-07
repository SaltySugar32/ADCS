package com.company.GUI.DB;
import java.io.*;
import java.sql.*;
import java.util.*;

public class DBHandler {

    public static List<Material> materials;

    // список материалов в БД
    public static void getAllMaterials() {
        materials = new ArrayList<Material>();

        try (BufferedReader br = new BufferedReader(new FileReader("src/com/company/GUI/DB/materials.txt"))) {
            String line = br.readLine();

            while (line != null) {
                String[] parts = line.split("/");
                if (parts.length == 5) {
                    double d1 = 0, d2 = 0, d3 = 0, d4 = 0;
                    try {
                        d1 = Double.parseDouble(parts[1]);
                    } catch (Exception e) {
                    }
                    try {
                        d2 = Double.parseDouble(parts[2]);
                    } catch (Exception e) {
                    }
                    try {
                        d3 = Double.parseDouble(parts[3]);
                    } catch (Exception e) {
                    }
                    try {
                        d4 = Double.parseDouble(parts[4]);
                    } catch (Exception e) {
                    }

                    materials.add(new Material(parts[0], d1, d2, d3, d4));
                }
                line = br.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String[] getMaterialNames(){
        String[] names = new String[materials.size()];
        int i=0;
        for(Material material:materials)
            names[i++] = material.name;
        return names;
    }

    public static void rewriteDB(){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("src/com/company/GUI/DB/materials.txt"))) {
            for (Material material : materials) {
                bw.append(material.name + "/" + material.lameMu + "/" + material.lameLambda + "/" + material.materialDensity + "/" + material.coefficientNu);
                bw.append(System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean addMaterial(Material material){
        for(Material mat: materials){
            if(material.name.equals(mat.name)) return false;
        }
        materials.add(material);
        rewriteDB();
        return true;
    }

    public static void updateMaterial(int index, double p1, double p2, double p3, double p4){
        materials.get(index).lameMu = p1;
        materials.get(index).lameLambda = p2;
        materials.get(index).materialDensity = p3;
        materials.get(index).coefficientNu = p4;
    }

    public static void deleteMaterial(int index){
        materials.remove(index);
        rewriteDB();
    }

}
