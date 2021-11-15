package Shapes;

/**
 * This is a class
 * Created 2021-10-14
 *
 * @author Magnus Silverdal
 */
public class Rectangle extends Shape{
    private Point p1;
    private Point p2;

    public Rectangle(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public Point getStart() {
        return p1;
    }

    public Point getEnd() {
        return p2;
    }
}
