package SimulationModel;

import Shapes.Ball;
import Shapes.Cell;
import Shapes.Scene;
import Shapes.Shape;

import java.util.ArrayList;

/**
 * This is a class
 * Created 2021-10-18
 *
 * @author Magnus Silverdal
 */
public class SimulationModel {
    Scene scene;
    Ball b;
    Cell cell;
    public SimulationModel() {
        cell = new Cell(1,1,1 ,1,10);
    }
    public void update(double t) {

    }
    public ArrayList<Shape> getShapes() {
        ArrayList<Shape> shapes = new ArrayList<>();
        shapes.add(b.getShape());
        return shapes;
    }
}
