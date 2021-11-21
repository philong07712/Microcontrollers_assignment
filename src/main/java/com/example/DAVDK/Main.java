package com.example.DAVDK;

import com.example.DAVDK.models.Movement;

import java.awt.*;
import java.util.*;
import java.util.List;

import static java.lang.Math.max;

public class Main {
    // x : xe
    // m: ma
    // t: tuong
    // s: si
    // v: vua
    // c: phao
    // p: tot

    // TODO: lay cai nay tu chinese chess unity

    public static char[][] board =
            {{'x','m','t','s','v','s','t','m','x'},
            {'_','_','_','_','_','_','_','_','_'},
            {'_','c','_','_','_','_','_','c','_'},
            {'p','_','p','_','p','_','p','_','p'},
            {'_','_','_','_','_','_','_','_','_'},
            {'_','_','_','_','_','_','_','_','_'},
            {'P','_','P','_','P','_','P','_','P'},
            {'_','C','_','_','_','_','_','C','_'},
            {'_','_','_','_','_','_','_','_','_'},
            {'X','M','T','S','V','S','T','M','X'}};

    public static Map<Character, Integer> scores = new HashMap<>();

    public static int depth = 3;

    public static Random rand = new Random();

    public static void main(String[] args) {
        generateScores();
        boolean userTurn = false;
        boolean cpuTurn = true;
        boolean now = cpuTurn;
        char[][] _board = board.clone();
        while (!isFinish(_board)) {
            printBoard(_board);
            System.out.println("----" + now + "Turn----");
            if (now == cpuTurn) {
                Movement m = CPUMiniMaxTurn(_board, cpuTurn, depth);
                _board[m.newPoint.y][m.newPoint.x] = _board[m.oldPoint.y][m.oldPoint.x];
                _board[m.oldPoint.y][m.oldPoint.x] = '_';
                now = userTurn;
                System.out.println(m);
            } else {
                Movement m = CPUMiniMaxTurn(_board, userTurn, depth - 1);
                _board[m.newPoint.y][m.newPoint.x] = _board[m.oldPoint.y][m.oldPoint.x];
                _board[m.oldPoint.y][m.oldPoint.x] = '_';
                System.out.println(m);
                now = cpuTurn;
            }
        }
        printBoard(_board);
        // after cpu move then user turn, if end then it is cpu win
        if (now == userTurn) {
            System.out.println("AI win");
        } else {
            System.out.println("Random win");
        }
    }

    private static Movement CPURandomTurn(char[][] board, boolean isLower) {
        List<Movement> list = canGoList(board, isLower);
        int choice = rand.nextInt(list.size());
        return list.get(choice);
    }

    private static void printBoard(char[][] board) {
        for (char[] row : board) {
            for (char c : row) {
                System.out.print(c + " ");
            }
            System.out.println("");
        }
    }

    public static boolean isFinish(char[][] board) {
        boolean u = false;
        boolean l = false;
        for (char[] row : board) {
            for (char c : row) {
                if (c == 'V') u = true;
                else if (c == 'v') l = true;
            }
        }
        return !(u && l);
    }

    public static List<Movement> canGoList(char[][] board, boolean isLower) {
        List<Movement> list = new ArrayList<>();
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 9; x++) {
                List<Movement> l = canGo(board, x, y, isLower);
                list.addAll(l);
            }
        }
        return list;
    }

    public static Movement CPUMiniMaxTurn(char[][] board, boolean isLower, int depth) {
        List<Movement> list = canGoList(board, isLower);
        Movement ans = new Movement(0, 0, 0, 0);
        int max = -1000;
        for (Movement m : list) {
            char[][] child = cloneBoard(board);
            child[m.newPoint.y][m.newPoint.x] = child[m.oldPoint.y][m.oldPoint.x];
            child[m.oldPoint.y][m.oldPoint.x] = '_';
            int value = minimax(child, depth - 1, isLower, !isLower);
            if ((max < value) || (max == value && rand.nextInt(2) == 0)) {
                max = value;
                ans = m.clone();
            }
        }
        return ans;
    }

    private static int minimax(char[][] node, int depth, boolean pMax, boolean pNow) {
        if (isFinish(node) || depth == 0) {
            return value(node, pMax);
        }
        if (pMax == pNow) {
            int _max = -1000;
            for (Movement m : canGoList(node, pNow)) {
                char[][] child = cloneBoard(node);
                child[m.newPoint.y][m.newPoint.x] = child[m.oldPoint.y][m.oldPoint.x];
                child[m.oldPoint.y][m.oldPoint.x] = '_';
                _max = max(_max, minimax(child, depth - 1, pMax, !pNow));
            }
            return _max;
        } else {
            int _min = 1000;
            for (Movement m : canGoList(node, pNow)) {
                char[][] child = cloneBoard(node);
                child[m.newPoint.y][m.newPoint.x] = child[m.oldPoint.y][m.oldPoint.x];
                child[m.oldPoint.y][m.oldPoint.x] = '_';
                _min = Math.min(_min, minimax(child, depth - 1, pMax, !pNow));
            }
            return _min;
        }
    }

    private static int value(char[][] node, boolean isLower) {
        int vl = 0;
        for (char[] row : node) {
            for (char c : row) {
                vl += scoreForChar(c, isLower);
            }
        }
        return vl;
    }

    private static int scoreForChar(char c, boolean isLower) {
        int value = scores.get(Character.toLowerCase(c));
        if (isLower(c) == isLower) return value;
        return -value;
    }

    public static List<Movement> canGo(char[][] board, int x, int y, boolean isLower) {
        List<Movement> list = new ArrayList<>();
        if (isLower(board[y][x]) != isLower) {
            return list;
        }
        if (board[y][x] == 'X' || board[y][x] == 'x') {
            return xX(x, y, board);
        }
        if (board[y][x] == 'M' || board[y][x] == 'm') {
            return mM(x, y, board);
        }
        if (board[y][x] == 'T' || board[y][x] == 't') {
            return tT(x, y, board);
        }
        if (board[y][x] == 'S' || board[y][x] == 's') {
            return sS(x, y, board);
        }
        if (board[y][x] == 'V' || board[y][x] == 'v') {
            return vV(x, y, board);
        }
        if (board[y][x] == 'C' || board[y][x] == 'c') {
            return cC(x, y, board);
        }
        if (board[y][x] == 'P' || board[y][x] == 'p') {
            return pP(x, y, board);
        }
        return list;
    }

    public static void generateScores() {
        scores.put('x', 7);
        scores.put('m', 4);
        scores.put('t', 3);
        scores.put('s', 2);
        scores.put('c', 5);
        scores.put('v', 500);
        scores.put('p', 1);
        scores.put('_', 0);
    }

    private static List<Movement> pP(int x, int y, char[][] board) {
        List<Movement> list = new ArrayList<>();
        boolean isLower = isLower(board[y][x]);
        if (!isLower) {
            if (isValidCoor(x, y - 1)) {
                if (isAbleToMoveCoor(x, y - 1, board[y-1][x], isLower)) {
                    list.add(new Movement(x, y, x, y - 1));
                }
            }
        } else {
            if (isValidCoor(x, y + 1)) {
                if (isAbleToMoveCoor(x, y + 1, board[y+1][x], isLower)) {
                    list.add(new Movement(x, y, x, y + 1));
                }
            }
        }

        if ((!isLower && y <= 4) || (isLower && y >= 5)) {
            if (isValidCoor(x + 1, y)) {
                if (isAbleToMoveCoor(x + 1, y, board[y][x + 1], isLower)) {
                    list.add(new Movement(x, y, x + 1, y));
                }
            }
            if (isValidCoor(x - 1, y)) {
                if (isAbleToMoveCoor(x - 1, y, board[y][x - 1], isLower)) {
                    list.add(new Movement(x, y, x - 1, y));
                }
            }
        }
        return list;
    }

    private static List<Movement> cC(int x, int y, char[][] board) {
        List<Movement> list = new ArrayList<>();
        // left
        int i = x;
        int stop = 0;
        while (i > 0) {
            i--;
            if (board[y][i] == '_' && stop == 0) {
                list.add(new Movement(x, y, i, y));
            } else if ((isLower(board[y][x]) != isLower(board[y][i])) && stop == 1) {
                list.add(new Movement(x, y, i, y));
                stop++;
            }
            else {
                stop++;
            }
            if (stop == 2) break;
        }
        // right
        i = x;
        stop = 0;
        while (i < 8) {
            i++;
            if (board[y][i] == '_' && stop == 0) {
                list.add(new Movement(x, y, i, y));
            } else if ((isLower(board[y][x]) != isLower(board[y][i])) && stop == 1) {
                list.add(new Movement(x, y, i, y));
                stop++;
            }
            else {
                stop++;
            }
            if (stop == 2) break;
        }
        // up
        i = y;
        stop = 0;
        while (i < 9) {
            i++;
            if (board[i][x] == '_' && stop == 0) {
                list.add(new Movement(x, y, x, i));
            } else if ((isLower(board[y][x]) != isLower(board[i][x])) && stop == 1) {
                list.add(new Movement(x, y, x, i));
                stop++;
            }
            else {
                stop++;
            }
            if (stop == 2) break;
        }
        // down
        i = y;
        stop = 0;
        while (i > 0) {
            i--;
            if (board[i][x] == '_' && stop == 0) {
                list.add(new Movement(x, y, x, i));
            } else if ((isLower(board[y][x]) != isLower(board[i][x])) && stop == 1) {
                list.add(new Movement(x, y, x, i));
                stop++;
            }
            else {
                stop++;
            }
            if (stop == 2) break;
        }
        return list;
    }

    private static List<Movement> vV(int x, int y, char[][] board) {
        List<Movement> list = new ArrayList<>();
        boolean isLower = isLower(board[y][x]);
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                int x1 = x + i;
                int y1 = y + j;
                if (isInsidePalace(x1, y1, isLower)) {
                    // if in palace and is the enemy then we will add
                    if (isLower(board[y1][x1]) != isLower || board[y1][x1] == '_') {
                        list.add(new Movement(x, y, x1, y1));
                    }
                }
            }
        }
        return list;
    }

    private static boolean isInsidePalace(int x, int y, boolean isLower) {
        if (isLower) {
            return (x >= 3 && x <= 5) && (y >= 0) && (y <= 2);
        } else {
            return (x >= 3 && x <= 5) && (y >= 7) && (y <= 9);
        }
    }

    private static List<Movement> sS(int x, int y, char[][] board) {
        List<Movement> list = new ArrayList<>();
        boolean isLower = isLower(board[y][x]);
        for (int i = -1; i < 2; i+=2) {
            for (int j = -1; j < 2; j+=2) {
                int x1 = x + i;
                int y1 = y + j;
                if (isInsidePalace(x1, y1, isLower)) {
                    if (isLower(board[y1][x1]) != isLower || board[y1][x1] == '_') {
                        list.add(new Movement(x, y, x1, y1));
                    }
                }
            }
        }
        return list;
    }

    private static List<Movement> tT(int x, int y, char[][] board) {
        List<Movement> list = new ArrayList<>();
        boolean isLower = isLower(board[y][x]);
        int xCur = -6;
        for (int i = 0; i < 2; i++) {
            xCur += 4;
            int yCur = -6;
            for (int j = 0; j < 2; j++) {
                yCur += 4;
                int x1 = x + xCur;
                int y1 = y + yCur;
                if (!isValidCoor(x1, y1)) continue;
                if (isLower && y < 5) continue;
                if (!isLower && y > 4) continue;
                if (board[y + yCur / 2][x + xCur / 2] != '_') continue;
                if (isAbleToMoveCoor(x1, y1, board[y1][x1], isLower)) {
                    list.add(new Movement(x, y, x1, y1));
                }
            }
        }
        return list;
    }

    private static List<Movement> mM(int x, int y, char[][] board) {
        List<Movement> list = new ArrayList<>();
        boolean isLower = isLower(board[y][x]);
        for (int i = -2; i < 3; i += 4) {
            for (int j = -1; j < 2; j += 2) {
                int xHalf = x + i / 2;
                int yHalf = y + i / 2;
                if (!isValidCoor(x, yHalf)) continue;
                if (!isValidCoor(xHalf, y)) continue;
                // if no blocking
                if (board[y][xHalf] == '_') {
                    int x1 = x + i;
                    int y1 = y + j;
                    if (!isValidCoor(x1, y1)) continue;
                    if (board[y1][x1] == '_' || isLower(board[y1][x1]) != isLower) {
                        list.add(new Movement(x, y, x1, y1));
                    }
                }
                if (board[yHalf][x] == '_') {
                    int x1 = x + j;
                    int y1 = y + i;
                    if (!isValidCoor(x1, y1)) continue;
                    if (board[y1][x1] == '_' || isLower(board[y1][x1]) != isLower) {
                        list.add(new Movement(x, y, x1, y1));
                    }
                }
            }
        }
        return list;
    }

    private static List<Movement> xX(int x, int y, char[][] board) {
        List<Movement> list = new ArrayList<>();
        // left
        int i = x;
        boolean stop = false;
        while (i > 0) {
            i--;
            if (board[y][i] == '_') {
                list.add(new Movement(x, y, i, y));
            } else if (isLower(board[y][x]) != isLower(board[y][i])) {
                list.add(new Movement(x, y, i, y));
                stop = true;
            }
            else {
                stop = true;
            }
            if (stop) break;
        }
        // right
        i = x;
        stop = false;
        while (i < 8) {
            i++;
            if (board[y][i] == '_') {
                list.add(new Movement(x, y, i, y));
            } else if (isLower(board[y][x]) != isLower(board[y][i])) {
                list.add(new Movement(x, y, i, y));
                stop = true;
            }
            else {
                stop = true;
            }
            if (stop) break;
        }
        // up
        i = y;
        stop = false;
        while (i < 9) {
            i++;
            if (board[i][x] == '_') {
                list.add(new Movement(x, y, x, i));
            } else if (isLower(board[y][x]) != isLower(board[i][x])) {
                list.add(new Movement(x, y, x, i));
                stop = true;
            }
            else {
                stop = true;
            }
            if (stop) break;
        }
        // down
        i = y;
        stop = false;
        while (i > 0) {
            i--;
            if (board[i][x] == '_') {
                list.add(new Movement(x, y, x, i));
            } else if (isLower(board[y][x]) != isLower(board[i][x])) {
                list.add(new Movement(x, y, x, i));
                stop = true;
            }
            else {
                stop = true;
            }
            if (stop) break;
        }
        return list;
    }

    private static boolean isAbleToMoveCoor(int x, int y, char c, boolean isLower) {
        if (!isValidCoor(x, y)) return false;
        return isLower(c) != isLower || c == '_';
    }

    private static boolean isValidCoor(int x, int y) {
        if (x < 0 || x >= 9 || y < 0 || y >= 10) return false;
        return true;
    }

    public static boolean isLower(char c) {
        if (Character.isLowerCase(c)) {
            return true;
        }
        return false;
    }

    public static char[][] cloneBoard(char[][] board) {
       return Arrays.stream(board).map(char[]::clone).toArray(char[][]::new);
    }
}