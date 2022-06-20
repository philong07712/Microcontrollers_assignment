package com.example.DAVDK;

import java.io.Serializable;
import java.util.*;

public class NewPath {
    public static class Point implements Serializable {
        public int x;
        public int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
        @Override
        public String toString() {
            return
                    x +
                            "," + y;
        }



        public Point clone() {
            return new Point(x, y);
        }

        public boolean isSameLine(Point p) {
            if (this.x - p.x == 0 || this.y - p.y == 0) {
                return true;
            }
            return false;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x && y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    public static class Node implements Serializable {
        public Point point;
        public int tag = 0;
        public int powerTag = 0;
        public Node(int x, int y) {
            this.point = new Point(x, y);
        }

        public Node clone() {
            Node node = new Node(this.point.x, this.point.y);
            node.tag = this.tag;
            return node;
        }
    }

    public static void printNode(Node[][] nodes, int rows, int columns, boolean isPlayer) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                int tag;
                if (isPlayer) {
                    tag = nodes[j][i].tag;
                } else {
                    tag = nodes[columns - j - 1][rows - i - 1].tag;
                }
                System.out.print("[" + tag + "] ");
            }
            System.out.println();
        }
    }

    public static List<Point> genPath(Node[][] nodeList, Point curPoint, List<Point> curPath) {
        int bestScore = 0;
        Node[][] tempNodeList = cloneNodeList(nodeList);
        tempNodeList[curPoint.x][curPoint.y].tag = PATH_TAG;
        curPath.add(curPoint);
        List<Point> bestPath = new ArrayList<>(curPath);
        if (curPoint.equals(targetPoint)) {
            return bestPath;
        }
        List<Point> moveList = genNextMove(nodeList, curPoint);
        if (moveList.isEmpty()) return new ArrayList<>();
        for (Point move : moveList) {
            if (curPath.contains(move)) {
                continue;
            }
            // check if next move contact anything
            List<Point> nextMoveList = genNextMove(nodeList, move);
            boolean isContacted = false;
            for (Point p : nextMoveList) {
                if (!p.equals(curPoint) && curPath.contains(p)) {
                    isContacted = true;
                }
            }
            if (isContacted) continue;

            List<Point> newPath = genPath(nodeList, move, new ArrayList<>(curPath));
            int score = calPathScore(tempNodeList, newPath);
            if (score > bestScore) {
                bestScore = score;
                bestPath.clear();
                bestPath.addAll(newPath);
            }
        }

        return bestPath;
    }

    public static int calPathScore(Node[][] nodeList, List<Point> path) {
        int score = 0;
        for (int i = 0; i < path.size(); i++) {
            Point p = path.get(i);
            if (nodeList[p.x][p.y].tag == CONTACTED_TAG) {
                if (p.x == 2 && p.y == 3) {
                }
                score += 100;
            }
            if (p.equals(targetPoint)) score += 100000;
            int prevIndex = i - 1;
            int nextIndex = i + 1;
            if (prevIndex >= 0 && nextIndex < path.size()) {
                boolean isSameLine = path.get(prevIndex).isSameLine(path.get(nextIndex));
                if (!isSameLine) score += 2;
            }
        }
        score += path.size();
        return score;
    }

    public static Node[][] cloneNodeList(Node[][] nodeList) {
        Node[][] cloneList = new Node[numColumns][numRows];
        for(int i=0; i<nodeList.length; i++)
            for(int j=0; j<nodeList[i].length; j++)
                cloneList[i][j]=nodeList[i][j].clone();
        return cloneList;
    }

    public static List<Point> genNextMove(Node[][] nodeList, Point p) {
        List<Point> nextMoveList = new ArrayList<>();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if ((i != 0 && j != 0) || (i == 0 && j == 0)) continue;
                Point newPoint = new Point(p.x + i, p.y + j);
                if (isValidPosition(newPoint.x, newPoint.y)) {
                    if (nodeList[newPoint.x][newPoint.y].tag != POWER_TAG) {
                        nextMoveList.add(newPoint);
                    }
                }
            }
        }
        return nextMoveList;
    }

    public static void updateNodePowerUp(Node[][] nodeList, List<Point> powerUp) {
        int powerTag = 0;
        for (Point p : powerUp) {
            nodeList[p.x][p.y].tag = POWER_TAG;
            nodeList[p.x][p.y].powerTag = powerTag++;
            List<Point> contactList = getDiagonalContactList(p);
            for (Point contact : contactList) {
                nodeList[contact.x][contact.y].tag = CONTACTED_TAG;

            }
        }

    }

    public static boolean isValidPosition(int x, int y) {
        if (x >= 0 && x < numColumns && y >= 0 && y < numRows) {
            return true;
        }
        return false;
    }

    public static List<Point> genTree(Node[][] nodeList, List<Point> path) {
        List<Point> forkList = new ArrayList<>();
        Map<Point, Boolean> forkMap = new HashMap<>();
        List<Point> treeList = new ArrayList<>();
        for (int i = 0; i < path.size(); i++) {
            Point p = path.get(i);
            int prevIndex = i - 1;
            int nextIndex = i + 1;
            if (prevIndex >= 0 && nextIndex < path.size()) {
                boolean isSameLine = path.get(prevIndex).isSameLine(path.get(nextIndex));
                if (!isSameLine) {
                    forkList.add(p);
                    forkMap.put(p, false);
                }
            }
        }

        int numTreeLeft = numTree;
        for (Point p : forkList) {
            if (numTreeLeft == 0) break;
            List<Point> availableSpots = new ArrayList<>();
            List<Point> contactList = getDiagonalContactList(p);
            for (Point contact : contactList) {
                if (nodeList[contact.x][contact.y].tag == EMPTY_TAG) {
                    availableSpots.add(contact);
                }
            }
            for (Point p1 : availableSpots) {
                Point treePoint = null;
                boolean canBuildTree = true;
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        if ((i == 0 && j == 0) || ((i != 0) && (j != 0))) {
                            continue;
                        }
                        Point checkPoint = new Point(p1.x + i, p1.y + j);
                        if (isValidPosition(checkPoint.x, checkPoint.y)) {
                            Node node = nodeList[checkPoint.x][checkPoint.y];
                            if ((node.tag == POWER_TAG) || treeList.contains(p1) || treeList.contains(checkPoint)
                                    || isForkAlreadyHasTree(p1, forkMap)) {
                                canBuildTree = false;
                            }
                        }
                    }
                }
                if (canBuildTree) {
                    // Add tree here
                    treePoint = p1.clone();
                    treeList.add(treePoint);
                        List<Point> contactedTreeList = getForkContactedTreeList(treePoint, forkMap);
                    for (Point fork : contactedTreeList) {
                        forkMap.put(fork, true);
                    }
                    numTreeLeft--;
                    break;
                }
            }
        }
        return treeList;
    }

    public static boolean isForkAlreadyHasTree(Point treePos, Map<Point, Boolean> forkMapList) {
        List<Point> contactList = getDiagonalContactList(treePos);
        for (Point contact : contactList) {
            if (forkMapList.containsKey(contact)) {
                boolean hasTree = forkMapList.get(contact);
                if (hasTree) return true;
            }
        }
        return false;
    }

    public static List<Point> getForkContactedTreeList(Point treePos, Map<Point, Boolean> forkMapList) {
        // contact include diagonal
        List<Point> forkList = new ArrayList<>();
        List<Point> contactList = getDiagonalContactList(treePos);
        for (Point contact : contactList) {
            if (forkMapList.containsKey(contact)) {
                forkList.add(contact);
            }
        }
        return forkList;
    }

    public static List<Point> genHole(Node[][] nodeList, List<Point> path) {
        List<Point> holeList = new ArrayList<>();
        List<Point> availableSpot = new ArrayList<>();
        for (int i = 0; i < nodeList.length; i++) {
            for (int j = 0; j < nodeList[i].length; j++) {
                if (nodeList[i][j].tag == EMPTY_TAG) availableSpot.add(new Point(i, j));
            }
        }
        int holeLeft = numHole;
        for (Point spot : availableSpot) {
            if (holeLeft == 0) break;
            List<Point> contactList = getDiagonalContactList(spot);
            boolean ísContactWithBarrier = false;
            boolean isContactWithPath = false;
            for (Point contactPoint : contactList) {
                Node node = nodeList[contactPoint.x][contactPoint.y];
                if (node.tag == POWER_TAG || node.tag == TREE_TAG) {
                    ísContactWithBarrier = true;
                }
                if (node.tag == PATH_TAG) isContactWithPath = true;
            }
            if (!ísContactWithBarrier && isContactWithPath && !holeList.contains(spot)) {
                holeLeft--;
                holeList.add(spot);
            }
        }
        return holeList;
    }

    public static List<Point> getDiagonalContactList(Point p) {
        List<Point> list = new ArrayList<>();
        for (int i = -1; i <= 1; i++) {
            int tempX = p.x + i;
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;
                int tempY = p.y + j;
                if (isValidPosition(tempX, tempY)) {
                    list.add(new Point(tempX, tempY));
                }
            }
        }
        return list;
    }

    public static List<Point> genPowerUp(List<Point> powerUpAblePoints) {
        List<Point> powerUpList = new ArrayList<>();
        List<Point> powerUpTempList = new ArrayList<>(powerUpAblePoints);

        while (powerUpList.size() < numPowerUp) {
            System.out.println(powerUpList.size());
            int random = new Random().nextInt(powerUpTempList.size());
            Point p = powerUpTempList.get(random);
            List<Point> contactList = getDiagonalContactList(p);
            boolean canPlaceHere = true;
            for (Point contact : contactList) {
                if (powerUpList.contains(contact)) {
                    canPlaceHere = false;
                }
            }
            if (canPlaceHere) {
                powerUpList.add(p);
                powerUpTempList.remove(p);
            }
        }
        return powerUpList;
    }

    public static Node[][] genMap() {
        Node[][] map = new Node[numColumns][numRows];
        for (int i = 0; i < numColumns; i++) {
            for (int j = 0; j < numRows; j++) {
                map[i][j] = new Node(i, j);
            }
        }
        updateNodePowerUp(map, genPowerUp(powerUps));
        List<Point> path = genPath(map, new Point(0, 0), new ArrayList<>());
        for (Point p : path) {
            map[p.x][p.y].tag = PATH_TAG;
        }
        List<Point> trees = genTree(map, path);
        for (Point p : trees) {
            map[p.x][p.y].tag = TREE_TAG;
        }
        List<Point> holes = genHole(map, path);
        for (Point p : holes) {
            map[p.x][p.y].tag = HOLE_TAG;
        }
        return map;
    }

    public static void init() {
        int[][] powerUpAbleMatrix =
                {
                        {0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 1, 1, 1, 1, 0},
                        {0, 1, 1, 1, 1, 1, 0},
                        {0, 1, 1, 1, 1, 1, 0},
                        {0, 0, 0, 0, 0, 0, 0}};
        powerUps = new ArrayList<>();
        for (int i = 0; i < powerUpAbleMatrix.length; i++) {
            for (int j = 0; j < powerUpAbleMatrix[i].length; j++) {
                int tag = powerUpAbleMatrix[i][j];
                if (tag == PATH_TAG) {
                    powerUps.add(new Point(j, i));
                }
            }
        }
    }
    static int numHole = 1;
    static int numTree = 2;
    static int numRows = 5;
    static int numColumns = 7;
    static int numPowerUp = 3;

    static int EMPTY_TAG = 0;
    static int PATH_TAG = 1;
    static int TREE_TAG = 2;
    static int POWER_TAG = 3;
    static int HOLE_TAG = 4;
    static int CONTACTED_TAG = 5;

    static List<Point> powerUps = new ArrayList<>();
    static Point targetPoint = new Point(numColumns - 1, numRows -1);
    public static void main(String[] args) {
        init();
        Node[][] playerMap = genMap();
        printNode(playerMap, numRows, numColumns, true);
//        Node[][] playerNodeList = new Node[numColumns][numRows];
//        Node[][] opponentNodeList = new Node[numColumns][numRows];
//        List<Point> playerPowerUps = new ArrayList<>();
//        List<Point> opponentPowerUps = new ArrayList<>();
//        playerPowerUps.add(new Point(2, 2));
//        playerPowerUps.add(new Point(4, 1));
//        playerPowerUps.add(new Point(4, 3));

//        opponentPowerUps.add(new Point(1, 3));
//        opponentPowerUps.add(new Point(3, 3));
//        opponentPowerUps.add(new Point(5, 3));

//        for (int i = 0; i < numColumns; i++) {
//            for (int j = 0; j < numRows; j++) {
//                playerNodeList[i][j] = new Node(i, j);
//                opponentNodeList[i][j] = new Node(i, j);
//            }
//        }
//
//        updateNodePowerUp(playerNodeList, genPowerUp(powerUpPoints));
//        updateNodePowerUp(opponentNodeList, genPowerUp(powerUpPoints));
//
//
//        List<Point> playerPath = genPath(playerNodeList, new Point(0, 0), new ArrayList<>());
//        List<Point> opponentPath = genPath(opponentNodeList, new Point(0, 0), new ArrayList<>());
//
//        Node[][] playerClone = cloneNodeList(playerNodeList);
//        Node[][] opponentClone = cloneNodeList(opponentNodeList);
//        for (Point p : playerPath) {
//            playerClone[p.x][p.y].tag = 1;
//        }
//        for (Point p : opponentPath) {
//            opponentClone[p.x][p.y].tag = 1;
//        }
//        List<Point> playerTrees = genTree(playerClone, playerPath);
//        List<Point> opponentTrees = genTree(opponentClone, opponentPath);
//        for (Point p : playerTrees) {
//            playerClone[p.x][p.y].tag = 2;
//        }
//
//        for (Point p : opponentTrees) {
//            opponentClone[p.x][p.y].tag = 2;
//        }
//
//        List<Point> playerHoleList = genHole(playerClone, playerPath);
//        List<Point> opponentHoleList = genHole(opponentClone, opponentPath);
//        for (Point p : playerHoleList) {
//            playerClone[p.x][p.y].tag = 8;
//        }
//        for (Point p : opponentHoleList) {
//            opponentClone[p.x][p.y].tag = 8;
//        }
//        printNode(opponentClone, numRows, numColumns, false);
//        System.out.println();
//        printNode(playerClone, numRows, numColumns, true);
    }
}
