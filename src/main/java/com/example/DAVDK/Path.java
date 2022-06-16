//package com.example.DAVDK;
//
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//
//public class Path {
//    static class Point {
//        int x;
//        int y;
//
//        @Override
//        public String toString() {
//            return
//                    x +
//                    "," + y;
//        }
//    }
//    static class Node {
//        Point point = new Point();
//        List<Point> list = new ArrayList<>();
//
//        public Node(int x, int y) {
//            this.point.x = x;
//            this.point.y = y;
//        }
//
//        @Override
//        public boolean equals(Object o) {
//            Point p = (Point) o;
//            if (this.point.x == p.x && this.point.y == p.y) return true;
//            return false;
//        }
//    }
//
//    public static void printNode(Node[][] nodes, int row, int column) {
//        for (int i = 0; i < row; i++) {
//            for (int j = 0; j < column; j++) {
//                System.out.print("[" + nodes[i][j].point + "] ");
//            }
//            System.out.println();
//        }
//    }
//
//    public static void printNodeScore(Node[][] nodes, int row, int column) {
//        for (int i = 0; i < row; i++) {
//            for (int j = 0; j < column; j++) {
//                System.out.print("[" + nodes[i][j].list.size() + "] ");
//            }
//            System.out.println();
//        }
//    }
//
//    public static void genScore(Node[][] nodeList, int x, int y, int row, int column) {
//        for (int i = -1; i <= 1; i+=2) {
//            int tempY = y + i;
//            for (int j = -1; j <= 1; j+=2) {
//                int tempX = x + j;
//                System.out.println(tempX + " " + tempY);
//                if (tempX < 0 || tempX > column - 1 || tempY < 0 || tempY > row - 1) {
//                }
//                else {
//                    Node tempNode = nodeList[tempY][tempX];
//                    if (tempNode.list.size() == 0) {
//                        System.out.println("add");
//                        tempNode.list.addAll(nodeList[y][x].list);
//                        tempNode.list.add(nodeList[y][x].point);
//                        genScore(nodeList, tempX, tempY, row, column);
//                    }
//                }
//
//            }
//        }
//    }
//
//    public static List<Point> genPath(Node[][] nodeList, int numPassed, int rows, int column) {
//        // Gen path
//        Node startNode = nodeList[0][0];
//        List<Point> path = new ArrayList<>();
//        path.add(startNode.point);
//        while (true) {
//            Point curPoint = path.get(path.size() - 1);
//            List<Point> ableToMoveList = new ArrayList<>();
//            for (int i = -1; i <= 1; i++) {
//                int tempY = curPoint.y + i;
//                for (int j = -1; j <= 1; j++) {
//                    if (i != 0 && j != 0) continue;
//                    int tempX = curPoint.x + j;
//                    if (tempX < 0 || tempX > rows - 1 || tempY < 0 || tempY > column - 1) {
//                        continue;
//                    }
//                    else {
//                        Node tempNode = nodeList[tempX][tempY];
//                        System.out.println(tempX + " " + tempY);
//                        int totalSize = path.size() + tempNode.list.size();
//                        System.out.println(totalSize + " size " + numPassed);
//                        if (totalSize == numPassed) {
//                            if (!path.contains(tempNode.point)) {
//                                ableToMoveList.add(tempNode.point);
//
//                                path.addAll(nodeList[tempNode.point.x][tempNode.point.y].list);
//                                return path;
//                            }
//                        }
//                        else if (totalSize < numPassed) {
//                            System.out.println("check");
//                            if (!path.contains(tempNode.point)) {
//                                ableToMoveList.add(tempNode.point);
//                            }
//                        }
//                    }
//                }
//            }
//            System.out.println(ableToMoveList.size());
//            if (ableToMoveList.size() == 0) {
//                return new ArrayList<Point>();
//            }
//            else {
//                int randomIndex = new Random().nextInt(ableToMoveList.size());
//                int x = ableToMoveList.get(randomIndex).x;
//                int y = ableToMoveList.get(randomIndex).y;
//                Point p = new Point();
//                p.x = x;
//                p.y = y;
//                path.add(p);
//            }
//        }
//    }
//
//    public static void main(String[] args) {
//        int rows = 5;
//        int column = 7;
//        int numPassed = 18;
//        Node[][] nodeList = new Node[rows][column];
//
//        for (int i = 0; i < rows; i++) {
//            for (int j = 0; j < column; j++) {
//                nodeList[i][j] = new Node(i, j);
//            }
//        }
//        printNode(nodeList, rows, column);
//        printNodeScore(nodeList, rows, column);
//
//        int curNodeX = column - 1;
//        int curNodeY = rows - 1;
//        List<Node> curNodeList = new ArrayList<>();
//        curNodeList.add(nodeList[curNodeY][curNodeX]);
//        nodeList[curNodeY][curNodeX].list.add(nodeList[curNodeY][curNodeX].point);
//
//        while (nodeList[0][0].list.size() == 0) {
//            List<Node> newCurNodeList = new ArrayList<>();
//            for (Node node : curNodeList) {
//                for (int i = -1; i <= 1; i++) {
//                    int tempY = node.point.y + i;
//                    for (int j = -1; j <= 1; j++) {
//                        if (i != 0 && j != 0) continue;
//                        int tempX = node.point.x + j;
//
//                        if (tempX < 0 || tempX > rows - 1 || tempY < 0 || tempY > column - 1) {
//                            continue;
//                        }
//                        else {
//                            Node tempNode = nodeList[tempX][tempY];
//                            if (tempNode.list.size() == 0) {
//                                tempNode.list.add(tempNode.point);
//                                tempNode.list.addAll(node.list);
//                                newCurNodeList.add(tempNode);
//                            }
//                        }
//
//                    }
//                }
//            }
//            curNodeList.clear();
//            curNodeList.addAll(newCurNodeList);
//        }
//
//
//        List<Point> path = genPath(nodeList, numPassed, rows, column);
//        for (Point p : path) {
//            System.out.print("[" + p + "] ");
//        }
//        System.out.println();
//        printNode(nodeList, rows, column);
//
//
//
//    }
//
//}
