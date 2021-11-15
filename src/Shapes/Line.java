package Shapes;

/**
 * 2D line in screen coordinates
 * Created 2021-03-31
 *
 * @author Magnus Silverdal
 */
public class Line extends Shape{
    private Point start;
    private Point end;

    public Line(Point s, Point e) {
        this.start = s;
        this.end = e;
    }

    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }
}
