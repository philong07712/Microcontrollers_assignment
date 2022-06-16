package com.example.DAVDK;

import com.example.DAVDK.models.Movement;

import java.awt.*;
import java.util.*;
import java.util.List;

import static java.lang.Math.abs;
import static java.lang.Math.max;

public class Main {
    // x : xe
    // m: ma
    // t: tuong
    // s: si
    // v: vua
    // c: phao
    // p: tot

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
            } else {
                Movement m = CPURandomTurn(_board, userTurn);
                _board[m.newPoint.y][m.newPoint.x] = _board[m.oldPoint.y][m.oldPoint.x];
                _board[m.oldPoint.y][m.oldPoint.x] = '_';
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

    public static void printBoard(char[][] board) {
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
        int max = -Integer.MAX_VALUE;
        for (Movement m : list) {
            char[][] child = cloneBoard(board);
            child[m.newPoint.y][m.newPoint.x] = child[m.oldPoint.y][m.oldPoint.x];
            child[m.oldPoint.y][m.oldPoint.x] = '_';
            int value = minimax(child, depth - 1, isLower, !isLower, -Integer.MAX_VALUE, +Integer.MAX_VALUE);
            if ((max < value) || (max == value && rand.nextInt(2) == 0)) {
                max = value;
                ans = m.clone();
            }
        }
        return ans;
    }

    private static int minimax(char[][] node, int depth, boolean pMax, boolean pNow, int alpha, int beta) {
        if (isFinish(node) || depth == 0) {
            return value(node, pMax);
        }

        if (pMax == pNow) {
            int _max = -Integer.MAX_VALUE;
            for (Movement m : canGoList(node, pNow)) {
                char[][] child = cloneBoard(node);
                child[m.newPoint.y][m.newPoint.x] = child[m.oldPoint.y][m.oldPoint.x];
                child[m.oldPoint.y][m.oldPoint.x] = '_';
                _max = max(_max, minimax(child, depth - 1, pMax, !pNow, alpha, beta));
                alpha = max(alpha, _max);
                if (beta <= alpha) break;
            }
            return _max;
        } else {
            int _min = Integer.MAX_VALUE;
            for (Movement m : canGoList(node, pNow)) {
                char[][] child = cloneBoard(node);
                child[m.newPoint.y][m.newPoint.x] = child[m.oldPoint.y][m.oldPoint.x];
                child[m.oldPoint.y][m.oldPoint.x] = '_';
                _min = Math.min(_min, minimax(child, depth - 1, pMax, !pNow, alpha, beta));
                beta = Math.min(beta, _min);
                if (beta <= alpha) break;
            }
            return _min;
        }
    }

    private static int value(char[][] node, boolean isLower) {
        int vl = 0;
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 9; x++) {
                char c = node[y][x];
                int charValue = scoreForChar(c);
                charValue += scoreForIndex(c, x, y);
                if (isLower(c) == isLower) vl += charValue;
                else {
                    if (charValue >= scores.get('v')) {
                        charValue /= 2;
                    }
                    vl -= charValue;
                }
            }
        }
        return vl;
    }

    private static int scoreForChar(char c) {
        int value = scores.get(Character.toLowerCase(c));
        return value;
    }

    private static int scoreForIndex(char c, int x, int y) {
        boolean isLower = isLower(c);
        if (c == 'X' || c == 'x') {
            return isLower ? xScore[y][x] : xScore[9-y][x];
        }
        if (c == 'M' || c == 'm') {
            return isLower ? mScore[y][x] : mScore[9-y][x];
        }
        if (c == 'T' || c == 't') {
            return isLower ? tScore[y][x] : tScore[9-y][x];
        }
        if (c == 'S' || c == 's') {
            return isLower ? sScore[y][x] : sScore[9-y][x];
        }
        if (c == 'V' || c == 'v') {
            return isLower ? vScore[y][x] : vScore[9-y][x];
        }
        if (c == 'C' || c == 'c') {
            return isLower ? cScore[y][x] : cScore[9-y][x];
        }
        if (c == 'P' || c == 'p') {
            return isLower ? pScores[y][x] : pScores[9-y][x];
        }
        return 0;
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
        scores.put('x', 70);
        scores.put('m', 40);
        scores.put('t', 25);
        scores.put('s', 20);
        scores.put('c', 90);
        scores.put('v', 500000);
        scores.put('p', 10);
        scores.put('_', 0);

    }

    public static int[][] pScores =
            {{0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0},
                    {1,1,1,1,1,1,1,1,1},
                    {1,1,2,2,2,2,2,2,1},
                    {1,1,1,2,2,2,1,1,1},
                    {1,1,1,2,2,2,2,1,1},
                    {1,1,2,2,2,2,2,2,1},
                    {1,1,2,2,2,2,2,2,1}};

    public static int[][] mScore =
            {{-5,-4,-3,-3,-3,-3,-3,-4,-5},
                    {-4,-2,0,0,0,0,0,-2,-4},
                    {-3,0,0,3,3,3,0,0,-3},
                    {-3,0,0,4,4,4,0,0,-3},
                    {-3,1,1,4,4,4,1,1,-3},
                    {-3,2,2,3,3,3,2,2,-3},
                    {-3,3,3,4,4,4,3,3,-3},
                    {-3,2,2,2,2,2,2,2,-3},
                    {-4,-2,0,1,1,1,0,-2,-4},
                    {-5,-4,-3,-3,-3,-3,-3,-4,-5}};

    public static int[][] xScore =
            {{0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0},
                    {-1,2,2,2,2,2,2,2,-1},
                    {-1,0,0,0,0,0,0,0,-1},
                    {-1,0,0,0,0,0,0,0,-1},
                    {-1,0,0,0,0,0,0,0,-1},
                    {-1,0,0,0,0,0,0,0,-1},
                    {-1,0,0,0,0,0,0,0,-1},
                    {-1,1,1,1,1,1,1,1,-1},
                    {0,1,1,1,1,1,1,1,0}};

    public static int[][] vScore =
            {{0,0,0,1,1,1,0,0,0},
                    {0,0,0,0,2,0,0,0,0},
                    {0,0,0,1,0,1,0,0,0},
                    {0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0}};

    public static int[][] tScore =
            {{0,0,1,0,0,0,1,0,0},
                    {0,0,0,0,0,0,0,0,0},
                    {-1,0,0,0,2,0,0,0,-1},
                    {0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0}};

    public static int[][] sScore =
            {{0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,1,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0}};

    public static int[][] cScore =
            {{0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0},
                    {-1,2,2,2,2,2,2,2,-1},
                    {-1,0,0,0,0,0,0,0,-1},
                    {-1,0,0,0,0,0,0,0,-1},
                    {-1,0,0,0,0,0,0,0,-1},
                    {-1,0,0,0,0,0,0,0,-1},
                    {-1,0,0,0,0,0,0,0,-1},
                    {-1,1,1,1,1,1,1,1,-1},
                    {0,1,1,1,1,1,1,1,0}};

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
            if (board[y][i] == '_') {
                if (stop == 0) {
                    list.add(new Movement(x, y, i, y));
                }
            } else if ((isLower(board[y][x]) != isLower(board[y][i])) && stop == 1) {
                if (board[y][i] == '_') continue;
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
            if (board[y][i] == '_') {
                if (stop == 0) {
                    list.add(new Movement(x, y, i, y));
                }
            } else if ((isLower(board[y][x]) != isLower(board[y][i])) && stop == 1) {
                if (board[y][i] == '_') continue;
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
            if (board[i][x] == '_') {
                if (stop == 0) {
                    list.add(new Movement(x, y, x, i));
                }
            } else if ((isLower(board[y][x]) != isLower(board[i][x])) && stop == 1) {
                if (board[i][x] == '_') continue;
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
            if (board[i][x] == '_') {
                if (stop == 0) {
                    list.add(new Movement(x, y, x, i));
                }
            } else if ((isLower(board[y][x]) != isLower(board[i][x])) && stop == 1) {
                if (board[i][x] == '_') continue;
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
        for (int i = -1; i < 2; i+= 2) {
                int x1 = x + i;
                int y1 = y + i;
                if (isInsidePalace(x1, y, isLower)) {
                    // if in palace and is the enemy then we will add
                    if (isLower(board[y][x1]) != isLower || board[y][x1] == '_') {
                        list.add(new Movement(x, y, x1, y));
                    }
                }
                if (isInsidePalace(x, y1, isLower)) {
                    // if in palace and is the enemy then we will add
                    if (isLower(board[y1][x]) != isLower || board[y1][x] == '_') {
                        list.add(new Movement(x, y, x, y1));
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