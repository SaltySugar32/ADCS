package com.company.client.gui.Database;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

public class DBHandler {

    public static String materialPath = "data/materialDB/";
    public static String graphPath = "data/graphDB/";

    public static String collisionsPath = "data/collisions.txt";
    public static String inputImgsPath = "data/inputImages/";
    public static String outputImgsPath = "data/outputImages/";
    public static String resourcesPath = "resources/images/";

    public static List<Material> materials;

    public static ArrayList<CollisionDesc> collissionDescs;

    public static ArrayList<String> firstLayers;

    public static ArrayList<String> secondLayers;

    private static final Pattern FRONT_VALID_PATTERN = Pattern.compile("^(O|(xi|sigma|gamma)\\([ab\\-0\\*]*\\))$");
    private static final Pattern RESULT_VALID_PATTERN = Pattern.compile("^((xi|sigma|gamma)\\([ab\\-0\\*]*\\))*$");

    public static void getAllCollisions(){
        collissionDescs = new ArrayList<CollisionDesc>();

        try (BufferedReader br = new BufferedReader(new FileReader(DBHandler.collisionsPath))) {

            firstLayers = new ArrayList<>(Arrays.asList(br.readLine().split(",")));
            secondLayers = new ArrayList<>(Arrays.asList(br.readLine().split(",")));

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    CollisionDesc collision = new CollisionDesc();

                    collision.firstLayer = parts[0];
                    collision.secondLayer = parts[1];
                    // все оставшиеся части в resultLayer
                    collision.resultLayers = new ArrayList<>();
                    collision.resultLayers.addAll(Arrays.asList(parts).subList(2, parts.length));
                    collision.shortDescription = collision.firstLayer + ", " + collision.secondLayer + " > " + collision.resultLayers;
                    collissionDescs.add(collision);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeCollisionsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(collisionsPath))) {
            // Write firstLayers to the second line, comma-separated
            String firstLayersLine = String.join(",", firstLayers);
            writer.write(firstLayersLine);
            writer.newLine();

            // Write secondLayers to the first line, comma-separated
            String secondLayersLine = String.join(",", secondLayers);
            writer.write(secondLayersLine);
            writer.newLine();

            // Write each collision description
            for (CollisionDesc collisionDesc : collissionDescs) {
                StringBuilder lineBuilder = new StringBuilder();
                lineBuilder.append(collisionDesc.firstLayer).append(",")
                        .append(collisionDesc.secondLayer).append(",");

                // Append resultLayers, comma-separated
                if (!collisionDesc.resultLayers.isEmpty()) {
                    lineBuilder.append(String.join(",", collisionDesc.resultLayers));
                }

                writer.write(lineBuilder.toString());
                writer.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
            // Handle exception (e.g., log it or throw a custom exception)
        }
    }

    public static void collisionSwapElements(CollisionDesc collisionDesc, int index) {

        for (int i = 0; i < collissionDescs.size(); i++) {
            CollisionDesc curr = collissionDescs.get(i);
            // Сравнение текущего элемента с заданным desc
            if (curr.equals(collisionDesc)) {
                // Проверка наличия нужных индексов
                if (index < 0 || index >= curr.resultLayers.size()) {
                    throw new IndexOutOfBoundsException("index is out of bounds.");
                }

                // Получить элемент на позиции 0
                String temp = curr.resultLayers.get(0);

                // Заменить элемент на позиции 0 элементом на позиции selectedRow
                curr.resultLayers.set(0, curr.resultLayers.get(index));

                // Заменить элемент на позиции selectedRow сохраненным элементом temp
                curr.resultLayers.set(index, temp);
            }
        }
    }

    public static ArrayList<String> getCollisionResult(String firstLayer, String secondLayer){
        for(CollisionDesc col:collissionDescs){
            if((col.firstLayer.equals(firstLayer)) && (col.secondLayer.equals(secondLayer)))
                return col.resultLayers;
        }
        return null;
    }

    // Добавить результат столкновения
    public static boolean addCollisionResult(int col, int row, String result) {
        if (!RESULT_VALID_PATTERN.matcher(result).matches())
            return false;

        CollisionDesc desc = getCollision(col, row);

        // Существует взаимодействие
        if (desc != null) {
            if (desc.resultLayers.contains(result))
                return false;

            desc.resultLayers.add(result);

            writeCollisionsToFile();
            return true;
        }

        // Создаем новое взаимодействие
        CollisionDesc newDesc = new CollisionDesc();
        newDesc.firstLayer = firstLayers.get(row);
        newDesc.secondLayer = secondLayers.get(col);
        newDesc.resultLayers = new ArrayList<String>();
        newDesc.resultLayers.add(result);
        newDesc.shortDescription = newDesc.firstLayer + ", " + newDesc.secondLayer + " > " + newDesc.resultLayers;
        collissionDescs.add(newDesc);

        writeCollisionsToFile();
        return true;
    }

    public static boolean deleteCollisionResult(int column, int row, int index){
        CollisionDesc desc = getCollision(column, row);

        if(desc == null)
            return false;

        if(desc.resultLayers.get(index)==null)
            return false;

        desc.resultLayers.remove(index);
        writeCollisionsToFile();
        return true;
    }

    public static CollisionDesc getCollision(int column, int row) {
        String firstLayer = firstLayers.get(row);
        String secondLayer = secondLayers.get(column);
        for (CollisionDesc col : collissionDescs) {
            if ((col.firstLayer.equals(firstLayer)) && (col.secondLayer.equals(secondLayer))) {
                return col;
            }
        }
        return null;
    }

    // добавить догоняющий фронт
    public static boolean addFirstLayer(String name){
        if (!FRONT_VALID_PATTERN.matcher(name).matches())
            return false;
        if(firstLayers.contains(name))
            return false;
        firstLayers.add(name);

        writeCollisionsToFile();
        return true;
    }

    // удалить догоняющий фронт
    public static boolean deleteFirstLayer(String name){
        if(!firstLayers.contains(name) || name==null)
            return false;

        firstLayers.remove(name);
        // удаление результатов с удаленным фронтом
        for (Iterator<CollisionDesc> iterator = collissionDescs.iterator(); iterator.hasNext();) {
            CollisionDesc desc = iterator.next();
            if (desc.firstLayer.equals(name)) {
                iterator.remove();
            }
        }
        writeCollisionsToFile();
        return true;
    }

    // добавить убегающий фронт
    public static boolean addSecondLayer(String name){
        if (!FRONT_VALID_PATTERN.matcher(name).matches())
            return false;
        if(secondLayers.contains(name))
            return false;
        secondLayers.add(name);

        writeCollisionsToFile();
        return true;
    }

    // удалить догоняющий фронт
    public static boolean deleteSecondLayer(String name){
        if(!secondLayers.contains(name))
            return false;

        secondLayers.remove(name);
        // удаление результатов с удаленным фронтом
        for (Iterator<CollisionDesc> iterator = collissionDescs.iterator(); iterator.hasNext();) {
            CollisionDesc desc = iterator.next();
            if (desc.secondLayer.equals(name)) {
                iterator.remove();
            }
        }
        writeCollisionsToFile();
        return true;
    }

    public static String formatCollisionLabel(String label){
        String newLabel;
        newLabel = label
                .replace("xi", "ξ")
                .replace("sigma", "Σ")
                .replace("gamma", "γ");
               /* .replace("A", "ᵃ")
                .replace("A-", "⁻ᵃ")
                .replace("B", "ᵇ")
                .replace("B-", "⁻ᵇ");*/
        return newLabel;
    }

    // заполнение списка материалов
    public static void getAllMaterials() {
        materials = new ArrayList<Material>();

        File folder = new File(materialPath);
        File[] materialFiles = folder.listFiles();

        if(materialFiles != null) {
            // чтение всех материалов
            for (File file : materialFiles) {

                if (file.isFile()) {

                    Material material = getMaterial(file);
                    if(material!=null)
                        materials.add(material);
                }
            }
        }
    }

    // чтение материала из файла
    public static Material getMaterial(File file){
        try (BufferedReader br = new BufferedReader(new FileReader(file.getAbsolutePath()))) {
            String line = "";

            //name=...
            line = br.readLine();
            if (!line.startsWith("name="))
                return null;
            String name = line.substring(5);
            if (name.equals(""))
                return null;

            //lambda=...
            line = br.readLine();
            if (!line.startsWith("lambda="))
                return null;
            String lambda_string = line.substring(7);
            if (lambda_string.equals(""))
                return null;
            double lambda = 0;
            try {
                lambda = Double.parseDouble(lambda_string);
            } catch (Exception e) {return null;}

            //nu=...
            line = br.readLine();
            if (!line.startsWith("nu="))
                return null;
            String nu_string = line.substring(3);
            if (nu_string.equals(""))
                return null;
            double nu = 0;
            try {
                nu = Double.parseDouble(nu_string);
            } catch (Exception e) {return null;}

            //mu=...
            line = br.readLine();
            if (!line.startsWith("mu="))
                return null;
            String mu_string = line.substring(3);
            if (mu_string.equals(""))
                return null;
            double mu = 0;
            try {
                mu = Double.parseDouble(mu_string);
            } catch (Exception e) {return null;}

            //density=...
            line = br.readLine();
            if (!line.startsWith("density="))
                return null;
            String density_string = line.substring(8);
            if (density_string.equals(""))
                return null;
            double density = 0;
            try {
                density = Double.parseDouble(density_string);
            } catch (Exception e) {return null;}

            return new Material(name, mu, lambda, density, nu);
        }
        catch(IOException ex){}
        return null;
    }

    // получение массива названий материалов
    public static String[] getMaterialNames(){
        String[] names = new String[materials.size()];
        int i=0;
        for(Material material:materials)
            names[i++] = material.name;

        return names;
    }

    // добавление материала в массив
    public static boolean addMaterial(Material material){
        for(Material mat: materials){
            if(material.name.equals(mat.name))
                return false;
        }

        addMaterialFile(material);
        materials.add(material);

        return true;
    }

    // создание файла материала.
    public static void addMaterialFile(Material material){

        // создание файла
        String file_path = materialPath + material.name + ".txt";
        File file = new File(file_path);
        try {
            file.createNewFile();
        }
        catch (IOException ex){}

        //запись в файл
        try {
            FileWriter fileWriter = new FileWriter(file_path, false);
            fileWriter.write("name=" + material.name);
            fileWriter.write("\nlambda=" + material.lameLambda);
            fileWriter.write("\nnu=" + material.coefficientNu);
            fileWriter.write("\nmu=" + material.lameMu);
            fileWriter.write("\ndensity=" + material.materialDensity);
            fileWriter.close();
        }
        catch (IOException ex) {}

    }

    // обновление записи о материале
    public static boolean updateMaterial(int index, double p1, double p2, double p3, double p4){
        boolean updated = false;

        if(materials.get(index).lameMu != p1){
            updated = true;
            materials.get(index).lameMu = p1;
        }

        if(materials.get(index).lameLambda != p2){
            updated = true;
            materials.get(index).lameLambda = p2;
        }

        if(materials.get(index).materialDensity != p3){
            updated = true;
            materials.get(index).materialDensity = p3;
        }

        if(materials.get(index).coefficientNu != p4){
            updated = true;
            materials.get(index).coefficientNu = p4;
        }

        if(updated)
            addMaterialFile(materials.get(index));

        return updated;
    }

    // удаление материала
    public static void deleteMaterial(int index){
        Material material = materials.get(index);
        materials.remove(index);
        File file = new File(materialPath + material.name + ".txt");
        file.delete();
    }

    // чтение всех имен файлов графиков
    public static List<String> getGraphNames(){
        List<String> names = new ArrayList<>();

        File folder = new File(graphPath);
        File[] graphFiles = folder.listFiles();

        if(graphFiles != null) {
            // чтение всех материалов
            for (File file : graphFiles) {

                if (file.isFile()) {
                    names.add(file.getName());
                }
            }
        }

        // удаление из списка временных файлов
        try {
            names.remove("temp1.txt");
            names.remove("temp2.txt");
        }
        catch (Exception exception){}

        return names;
    }

    // чтение содержимого файла графика
    public static double[][] getGraphArray(String filename){

        File folder = new File(graphPath);
        File[] graphFiles = folder.listFiles();
        File target_file = null;

        // поиск файла по имени
        if(graphFiles != null) {
            for (File file : graphFiles) {
                if (file.isFile() && filename.equals(file.getName())) {
                    target_file = file;
                }
            }
        }

        if (target_file == null)
            return null;

        // создание массива размера файла
        int size = getGraphFileSize(target_file);
        if(size < 1)
            return null;

        double[][] array = new double[2][size];

        // чтение координат из файла
        try (BufferedReader br = new BufferedReader(new FileReader(target_file.getAbsolutePath()))) {
            int i=0;
            for(String line = br.readLine(); line!=null; line = br.readLine()) {

                String[] parts = line.split(";");
                try{
                    array[0][i] = Double.parseDouble(parts[0]);
                    array[1][i] = Double.parseDouble(parts[1]);
                }
                catch (Exception ex){return null;}

                // отмена, если точка с координатой X существует
                for(int j = 0; j < i; j++)
                    if(array[0][j] == array[0][i])
                        return null;
                i++;
            }
        }
        catch (Exception ex){return null;}

        return array;
    }

    // Проверка на корректность + подсчет строк в файле графика
    public static int getGraphFileSize(File file){
        int rows = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(file.getAbsolutePath()))) {
            for(String line = br.readLine(); line!=null; line = br.readLine()){
                // подсчет строк
                rows++;

                // проверка строки файла на 2 части, разделенные символом ";"
                String[] parts = line.split(";");
                if(parts.length!=2)
                    return -1;

                // проверка на числа
                try{
                    if((Double.parseDouble(parts[0]) > Integer.MAX_VALUE) ||
                            (Double.parseDouble(parts[1]) > Integer.MAX_VALUE))
                        return -1;
                }
                catch (Exception ex){return -1;}

            }
        }
        catch (Exception ex){return -1;}

        return rows;
    }

    // запись координат в файл
    public static void addGraphFile(String fileName, double[][] array){
        File file = new File(graphPath + fileName + ".txt");

        try{
            file.createNewFile();
        }
        catch (Exception ex){}

        //запись в файл
        try {
            FileWriter fileWriter = new FileWriter(file, false);

            for(int i=1; i<array[0].length; i++)
                fileWriter.write(array[0][i] + ";" + array[1][i] + "\n");

            fileWriter.close();
        }
        catch (IOException ex) {}
    }

    // удаление файла с координатами графика
    public static void deleteGraphFile(String fileName){

        File folder = new File(graphPath);
        File[] graphFiles = folder.listFiles();
        File target_file = null;

        // поиск файла по имени
        if(graphFiles != null) {
            for (File file : graphFiles) {
                if (file.isFile() && fileName.equals(file.getName())) {
                    target_file = file;
                }
            }
        }

        if(target_file != null)
            target_file.delete();
    }

    // Сохранение графика в файл
    public static void saveChart(JFreeChart chart, ChartPanel panel, String file){
        // Удаление названия графика в изображении
        String title = chart.getTitle().getText();
        chart.getTitle().setText("");

        // сохранение графика
        try {
            ChartUtils.saveChartAsPNG(
                    new File(file),
                    chart,
                    panel.getWidth(),
                    panel.getHeight()
            );
        }
        catch (IOException ex) {}

        // Откат названия графика
        chart.getTitle().setText(title);
    }

}
