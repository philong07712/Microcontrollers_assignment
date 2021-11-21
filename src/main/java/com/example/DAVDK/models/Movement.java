package com.example.DAVDK.models;

import java.awt.*;

public class Movement {
    public Point oldPoint;
    public Point newPoint;

    public Movement(int x, int y, int x1, int y1) {
        oldPoint = new Point(x, y);
        newPoint = new Point(x1, y1);
    }

    public Movement() {

    }

    public Movement clone() {
        return new Movement(oldPoint.x, oldPoint.y, newPoint.x, newPoint.y);
    }

    @Override
    public String toString() {
        return "oldPoint=" + oldPoint +
                ", newPoint=" + newPoint;
    }
}
