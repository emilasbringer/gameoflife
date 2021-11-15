package ScreenRenderer;

import Shapes.*;

/**
 * This is a class
 * Created 2021-10-18
 *
 * @author Magnus Silverdal
 */
public class Screen {
    private int[] pixels;
    private int width;
    private int height;

    public Screen(int[] pixels, int width, int height) {
        this.width = width;
        this.height = height;
        this.pixels = pixels;
    }

    public void draw(int x, int y , int color) {
        pixels[y*width+x] = color;
    }

    public void draw(Point p, int color) {
        draw(p.getX(), p.getY(), color);
    }

    public void draw(Line l, int color) {
        draw(l.getStart(),l.getEnd(),color);
    }

    public void draw(Point start, Point end, int color) {
        int[] pixels = getPointsFromLine(start,end);
        for (int i = 0 ; i < pixels.length ; i++) {
            draw(pixels[i++],pixels[i],color);
        }
    }

    public void draw(Rectangle r, int color) {
        Line l1 =  new Line(r.getStart(),new Point(r.getEnd().getX(), r.getStart().getY()));
        Line l2 =  new Line(new Point(r.getEnd().getX(), r.getStart().getY()),r.getEnd());
        Line l3 =  new Line(r.getEnd(),new Point(r.getStart().getX(), r.getEnd().getY()));
        Line l4 =  new Line(new Point(r.getStart().getX(), r.getEnd().getY()),r.getStart());
        draw(l1, color);
        draw(l2, color);
        draw(l3, color);
        draw(l4, color);
    }

    public void fill(Rectangle r, int color) {
        for (int i = r.getStart().getY() ; i < r.getEnd().getY() ; i++) {
            for (int j = r.getStart().getX() ; j < r.getEnd().getX() ; j++) {
                draw(j,i,color);
            }
        }
    }

    public void draw(Sprite s) {
        for (int y = 0 ; y < s.getHeight() ; y++) {
            for (int x = 0 ; x < s.getWidth() ; x++) {

            }
        }
    }

    public void fill(Circle c, int color) {
        Point p1 = new Point(c.getCenter().getX()-c.getRadius(), c.getCenter().getY()-c.getRadius());
        Point p2 = new Point(c.getCenter().getX()+c.getRadius(), c.getCenter().getY()+c.getRadius());
        for (int i = p1.getY() ; i < p2.getY() ; i++) {
            for (int j = p1.getX() ; j < p2.getX() ; j++) {
                if ((j-c.getCenter().getX())*(j-c.getCenter().getX()) + (i-c.getCenter().getY())*(i-c.getCenter().getY()) < c.getRadius()*c.getRadius())
                    draw(j,i,color);
            }
        }
    }

    public void drawTriangle(Triangle t, int color) {
        Line[] triangel = t.getEdges();
        for (Line l : triangel) {
            draw(l,color);
        }
    }

    // Find the top
    // fill between the lines leading up to it
    // find the slope of the third line
    // use it to decide

    public void fillTriangle(Triangle t, int color) {
        System.out.println("--" + color);
        Line[] edges = t.getEdges();
        Point[] vertices = t.getVertices();

        int uppermost = 0;
        if (vertices[0].getY() > vertices[1].getY()) {
            uppermost = 1;
        }
        if (vertices[uppermost].getY() > vertices[2].getY()) {
            uppermost = 2;
        }

        int[] pixelsLine1;
        int[] pixelsLine2;
        int[] pixelsLine3;
        boolean[] reverse = {false,false,false};

        if (uppermost == 0) {
            pixelsLine1 = getPointsFromLine(edges[0].getStart(),edges[0].getEnd());
            pixelsLine2 = getPointsFromLine(edges[1].getStart(),edges[1].getEnd());
            pixelsLine3 = getPointsFromLine(edges[2].getStart(),edges[2].getEnd());
            reverse[2] = true;
        } else if (uppermost == 1) {
            pixelsLine1 = getPointsFromLine(edges[0].getStart(),edges[0].getEnd());
            reverse[0] = true;
            pixelsLine2 = getPointsFromLine(edges[1].getStart(),edges[1].getEnd());
            pixelsLine3 = getPointsFromLine(edges[2].getStart(),edges[2].getEnd());
            if (edges[2].getEnd().getY() < edges[2].getStart().getY()) {
                reverse[2] = true;
            }
        }
        else {
            pixelsLine1 = getPointsFromLine(edges[1].getEnd(),edges[1].getStart());
            pixelsLine2 = getPointsFromLine(edges[2].getStart(),edges[2].getEnd());
            pixelsLine3 = getPointsFromLine(edges[0].getEnd(),edges[0].getStart());
        }

        // Debug...
        /*for (int i = 0 ; i < pixelsLine1.length ; i+=2) {
            System.out.println("("+pixelsLine1[i]+","+pixelsLine1[i+1]+")");
        }
        System.out.println();
        for (int i = 0 ; i < pixelsLine2.length ; i+=2) {
            System.out.println("("+pixelsLine2[i]+","+pixelsLine2[i+1]+")");
        }
        System.out.println();
        for (int i = 0 ; i < pixelsLine3.length ; i+=2) {
            System.out.println("("+pixelsLine3[i]+","+pixelsLine3[i+1]+")");
        }
        System.out.println();*/
        // end Debug..

        int index1 = 0;
        int bound1 =  pixelsLine1.length-1;
        int index2 = 0;
        int bound2 =  pixelsLine2.length-1;
        // reversing the index with slope...
        if (reverse[0]) {
            index1 = pixelsLine1.length - 1;
            bound1 = 0;
        }
        if (reverse[1]) {
            index2 = pixelsLine2.length-1;
            bound2 = 0;
        }

        while (index1 <= bound1 && index2 <= bound2) {
            System.out.println("u ("+pixelsLine1[index1]+","+pixelsLine1[index1+1]+") - ("+pixelsLine2[index2]+","+pixelsLine2[index2+1]+")");
            for (int x = pixelsLine1[index1] ; x <= pixelsLine2[index2] ; x++) {
                draw(x,pixelsLine1[index1+1],color);
            }
            while(index1 < pixelsLine1.length-3 && pixelsLine1[index1+1] == pixelsLine1[index1+3]) {
                index1+=2;
            }
            while(index2 < pixelsLine2.length-3 && pixelsLine2[index2+1] == pixelsLine2[index2+3]) {
                index2+=2;
            }
            index1+=reverse[0]?-2:2;
            index2+=reverse[1]?-2:2;
        }

        int index3 = 0;
        //while (index3 < pixelsLine3.length - 3 && pixelsLine3[index3 + 1] == pixelsLine1[index1 - 1]) {
        //    index3 += 2;
        //}
        if (edges[2].getStart().getY() > edges[2].getEnd().getY()) {
            System.out.println(index3 + " " + index2);

            while (index3 < pixelsLine3.length - 1 && index2 < pixelsLine2.length - 1 && pixelsLine3[index3 + 1] < pixelsLine2[index2 + 1]) {
                index3 += 2;
            }
            while (index3 <= pixelsLine3.length - 1 && index2 <= pixelsLine2.length - 1) {

                System.out.println("l\\ ("+pixelsLine3[index3]+","+pixelsLine3[index3+1]+") - ("+pixelsLine2[index2]+","+pixelsLine2[index2+1]+")");
                for (int x = pixelsLine3[index3]; x <= pixelsLine2[index2]; x++) {
                    draw(x, pixelsLine3[index3 + 1], color);
                }
                while (index3 <= pixelsLine3.length - 3 && pixelsLine3[index3 + 1] == pixelsLine3[index3 + 3]) {
                    index3 += 2;
                }
                while (index2 < pixelsLine2.length - 3 && pixelsLine2[index2 + 1] == pixelsLine2[index2 + 3]) {
                    index2 += 2;
                }
                index3 += 2;
                index2 += 2;
            }
                /*System.out.println("l\\ ("+pixelsLine3[index3]+","+pixelsLine3[index3+1]+") - ("+pixelsLine2[index2]+
                        ","+pixelsLine2[index2+1]+")");
                for (int x = pixelsLine3[index3]; x <= pixelsLine2[index2]; x++) {
                    drawPixel(x, pixelsLine3[index3 + 1], color);
                }*/
            System.out.println("Halt");
        }else {
            System.out.println(index1 + " " + index3 );
            while (index3 < pixelsLine3.length - 1 && index1 < pixelsLine1.length - 1 && pixelsLine3[index3 + 1] < pixelsLine1[index1 + 1]) {
                index3 += 2;
            }
            while (index3 <= pixelsLine3.length-1 && index1 <= pixelsLine1.length-1) {


                System.out.println("l/ ("+pixelsLine1[index1]+","+pixelsLine1[index1+1]+") - ("+pixelsLine3[index3]+","+pixelsLine3[index3+1]+")");
                for (int x = pixelsLine1[index1]; x <= pixelsLine3[index3]; x++) {
                    draw(x, pixelsLine3[index3 + 1], color);
                }
                while (index1 < pixelsLine1.length - 3 && pixelsLine1[index1 + 1] == pixelsLine1[index1 + 3]) {
                    index1 += 2;
                }
                while (index3 < pixelsLine3.length - 3 && pixelsLine3[index3 + 1] == pixelsLine3[index3 + 3]) {
                    index3 += 2;
                }
                index3 += 2;
                index1 += 2;
            }
        }

        /*for (Line l : triangle) {
            int[] pixels = getPointsFromLine(l.getStart(),l.getEnd());
            System.out.println();
            for (int i = 0 ; i < pixels.length ; i++) {
                System.out.println("("+pixels[i]+","+pixels[i+1]+")");
                drawPixel(pixels[i++],pixels[i],color);
            }
        }*/

    }

    public int[] getPointsFromLine(Point start, Point end) {
        int deltax = end.getX()-start.getX();
        int deltay = end.getY()-start.getY();
        if (deltax < 0)
            deltax = -deltax;
        if (deltay < 0)
            deltay = -deltay;
        int[] point = new int[(deltax>deltay?deltax+1:deltay+1)*2];
        // Bresenhams algorithm for fast lines
        int x, y;
        int dx, dy;
        int incx, incy;
        int balance;
        int count = 0;
        if (end.getX() >= start.getX()) {
            dx = end.getX() - start.getX();
            incx = 1;
        } else {
            dx = start.getX() - end.getX();
            incx = -1;
        }

        if (end.getY() >= start.getY()) {
            dy = end.getY() - start.getY();
            incy = 1;
        } else {
            dy = start.getY() - end.getY();
            incy = -1;
        }

        x = start.getX();
        y = start.getY();

        if (dx >= dy) {
            dy <<= 1;
            balance = dy - dx;
            dx <<= 1;

            while (x != end.getX()) {
                point[count++] = x;
                point[count++] = y;
                if (balance >= 0) {
                    y += incy;
                    balance -= dx;
                }
                balance += dy;
                x += incx;
            }
            point[count++] = x;
            point[count] = y;
        } else {
            dx <<= 1;
            balance = dx - dy;
            dy <<= 1;

            while (y != end.getY()) {
                point[count++] = x;
                point[count++] = y;
                if (balance >= 0) {
                    x += incx;
                    balance -= dy;
                }
                balance += dx;
                y += incy;
            }
            point[count++] = x;
            point[count] = y;
        }
        return point;
    }

    public void clear() {
        for (int i = 0 ; i < pixels.length; i++) {
            pixels[i] = 0x0;
        }
    }
}

