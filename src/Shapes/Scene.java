package Shapes;

import java.util.ArrayList;

/**
 * This is a class
 * Created 2021-10-20
 *
 * @author Magnus Silverdal
 */
public class Scene {
    ArrayList<Circle> circles;
    ArrayList<Line> lines;
    ArrayList<Point> points;
    ArrayList<Rectangle> rectangles;
    ArrayList<Triangle> triangles;

    public Scene() {
        circles = new ArrayList<>();
        lines = new ArrayList<>();
        points = new ArrayList<>();
        rectangles = new ArrayList<>();
        triangles = new ArrayList<>();
    }
}
