package com.company.server.simulation.general;

import com.company.client.gui.Database.CollisionDesc;

import java.util.*;

public class TreeBuilder2 {

    public static LocalResTree createTree(ArrayList<String> waveFrontStrings, ArrayList<CollisionDesc> collisionDescs) {
        if (waveFrontStrings == null || waveFrontStrings.isEmpty()) {
            return null;
        }

        // Сброс счетчика маркеров
        LocalResTree.resetMarkerCounter();

        // Создаем корень дерева
        ArrayList<String> rootResult = new ArrayList<>();
        rootResult.add(0, waveFrontStrings.get(0));
        LocalResTree root = new LocalResTree(rootResult, "");
        LocalResTree currentNode = root;

        for (int i = 1; i < waveFrontStrings.size(); i++) {
            String currentWaveFront = waveFrontStrings.get(i);
            currentNode = addNodeWithCollisions(currentNode, currentWaveFront, collisionDescs);
        }

        return root;
    }

    private static LocalResTree addNodeWithCollisions(LocalResTree parentNode, String waveFront, ArrayList<CollisionDesc> collisionDescs) {
        ArrayList<String> newResult = new ArrayList<>(parentNode.result);

        // Проверяем на столкновения и добавляем узлы-потомки
        checkAndAddCollisionNodes(parentNode, newResult, collisionDescs);

        newResult.add(0, waveFront);
        // Добавляем обычный узел-потомок
        LocalResTree childNode = new LocalResTree(newResult, "");
        parentNode.children.add(childNode);

        // Рекурсивно добавляем столкновения для нового узла
        checkAndAddCollisionNodes(childNode, newResult, collisionDescs);

        return childNode;
    }

    private static void checkAndAddCollisionNodes(LocalResTree parentNode, ArrayList<String> result, ArrayList<CollisionDesc> collisionDescs) {
        for (CollisionDesc collisionDesc : collisionDescs) {
            int index = checkCollision(result, collisionDesc);
            if (index != -1) {

                for (String layer : collisionDesc.resultLayers) {

                    ArrayList<String> collidedResult = new ArrayList<>(result);
                    collidedResult.remove(index);
                    collidedResult.remove(index); // Удаляем два элемента по одному индексу

                    // Разбиваем layer
                    String[] newResult = splitFronts(layer);

                    collidedResult.addAll(index, Arrays.asList(newResult));

                    LocalResTree childNode = new LocalResTree(collidedResult, collisionDesc.firstLayer + collisionDesc.secondLayer);
                    parentNode.children.add(childNode);

                    // Рекурсивно добавляем следующий узел с учетом текущих столкновений
                    checkAndAddCollisionNodes(childNode, collidedResult, collisionDescs);
                }
            }
        }
    }

    private static String[] splitFronts(String layer) {
        return layer.split("(?<=\\))(?=[a-zA-Z]+\\()");
    }

    private static int checkCollision(ArrayList<String> result, CollisionDesc collisionDesc) {
        for (int i = 0; i < result.size() - 1; i++) {
            if (isLayerMatch(result.get(i), collisionDesc.firstLayer) && isLayerMatch(result.get(i + 1), collisionDesc.secondLayer)) {
                System.out.println("Collision: " + collisionDesc.firstLayer + " + " + collisionDesc.secondLayer);
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
