package com.company.server.simulation.general;

import com.company.client.gui.Database.CollisionDesc;

import java.util.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TreeBuilder {

    public static ArrayList<String> wavefrontStrings;

    public static LocalResTree createNode(LocalResTree node, ArrayList<CollisionDesc> collisionDescs){
        node = addNodeWithCollisions(node,collisionDescs);
        return node;
    }

    public static LocalResTree createTree(ArrayList<String> waveFrontStrings, ArrayList<CollisionDesc> collisionDescs) {
        if (waveFrontStrings == null || waveFrontStrings.isEmpty()) {
            return null;
        }

        wavefrontStrings = waveFrontStrings;

        // Создаем корень дерева
        ArrayList<String> rootResult = new ArrayList<>();
        rootResult.add(0, waveFrontStrings.get(0));
        LocalResTree root = new LocalResTree(rootResult, "");
        root.waveFrontStrings.add(waveFrontStrings.get(0));
        LocalResTree currentNode = root;

        root = addNodeWithCollisions(currentNode, collisionDescs);

        return root;
    }

    private static LocalResTree addNodeWithCollisions(LocalResTree parentNode, ArrayList<CollisionDesc> collisionDescs) {
        ArrayList<String> newResult = new ArrayList<>(parentNode.result);

        // Проверяем на столкновения и добавляем узлы-потомки
        checkAndAddCollisionNodes(parentNode, newResult, collisionDescs);

        removeDuplicateChildren(parentNode);

        if (parentNode.waveFrontStrings.size() < wavefrontStrings.size()) {

            String waveFront = wavefrontStrings.get(parentNode.waveFrontStrings.size());
            newResult.add(0, waveFront);

            // Добавляем обычный узел-потомок
            LocalResTree childNode = new LocalResTree(newResult, "");
            childNode.waveFrontStrings = new ArrayList<>(parentNode.waveFrontStrings);
            childNode.waveFrontStrings.add(waveFront);
            parentNode.children.add(childNode);
            parentNode.isCollapsed = false;
        }

        // Рекурсивно добавляем столкновения для нового узла

        return parentNode;
    }

    private static void checkAndAddCollisionNodes(LocalResTree parentNode, ArrayList<String> result, ArrayList<CollisionDesc> collisionDescs) {
        for (CollisionDesc collisionDesc : collisionDescs) {
            int index = checkCollision(result, collisionDesc);
            if (index != -1) {

                for (String layer : collisionDesc.resultLayers) {

                    ArrayList<String> collidedResult = new ArrayList<>(result);

                    if (collisionDesc.secondLayer.equals("O"))
                        collidedResult.add(index, "O");

                    collidedResult.remove(index);
                    collidedResult.remove(index); // Удаляем два элемента по одному индексу

                    // Разбиваем layer
                    collidedResult.addAll(index, Arrays.asList(splitFronts(layer)));

                    LocalResTree childNode = new LocalResTree(collidedResult, collisionDesc.firstLayer + collisionDesc.secondLayer);
                    childNode.waveFrontStrings = new ArrayList<>(parentNode.waveFrontStrings);
                    parentNode.children.add(childNode);

                    parentNode.isCollapsed = false;
                }
            }
        }
    }

    private static void checkAndAddCollisionNodes2(LocalResTree parentNode, ArrayList<String> result, ArrayList<CollisionDesc> collisionDescs) {
        for (CollisionDesc collisionDesc : collisionDescs) {
            int index = checkCollision(result, collisionDesc);
            if (index != -1) {

                String secondLayer = collisionDesc.secondLayer;

                for (String layer : collisionDesc.resultLayers) {

                    ArrayList<String> collidedResult = new ArrayList<>(result);

                    if(secondLayer.equals("O"))
                        collidedResult.add(index,"O");
                    collidedResult.remove(index);
                    collidedResult.remove(index); // Удаляем два элемента по одному индексу

                    // Разбиваем layer
                    String[] newResult = splitFronts(layer);

                    collidedResult.addAll(index, Arrays.asList(newResult));

                    LocalResTree childNode = new LocalResTree(collidedResult, collisionDesc.firstLayer + collisionDesc.secondLayer);
                    parentNode.children.add(childNode);
                }
            }
        }
    }

    private static String[] splitFronts(String layer) {
        return layer.split("(?<=\\))(?=[a-zA-Z]+\\()");
    }

    // result - список фронтов в ноде
    private static int checkCollision(ArrayList<String> result, CollisionDesc collisionDesc) {
        for (int i = 0; i < result.size() - 1; i++) {
            if(result.get(i).contains("-") && isLayerMatch(result.get(i), collisionDesc.firstLayer) && collisionDesc.secondLayer.equals("O"))
                return i;
            if (isLayerMatch(result.get(i), collisionDesc.firstLayer) && isLayerMatch(result.get(i + 1), collisionDesc.secondLayer)) {
                return i;
            }
        }
        return -1;
    }

    private static boolean isLayerMatch(String resultLayer, String collisionLayer) {
        if (collisionLayer.contains("sigma") && resultLayer.contains("sigma")) {
            return true;
        }
        return resultLayer.equals(collisionLayer);
    }

    public static void removeDuplicateChildren(LocalResTree root) {
        if (root == null) return;
        removeDuplicates(root);
    }

    private static void removeDuplicates(LocalResTree node) {
        if (node.children == null || node.children.isEmpty()) return;

        // Создаем set для отслеживания уникальных результатов
        Set<ArrayList<String>> uniqueResults = new HashSet<>();
        List<LocalResTree> uniqueChildren = new ArrayList<>();

        for (LocalResTree child : node.children) {
            if (uniqueResults.add(child.result)) {
                uniqueChildren.add(child);
            } else {
                System.out.println("Removed duplicate: " + child.result);
            }
        }

        node.children = uniqueChildren;

        // Рекурсивно проверяем потомков
        for (LocalResTree child : node.children) {
            removeDuplicates(child);
        }
    }
}

