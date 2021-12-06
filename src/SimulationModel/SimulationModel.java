package SimulationModel;

import ScreenRenderer.ScreenRenderer;

/**
 * This is a class
 * Created 2021-10-18
 *
 * @author Magnus Silverdal
 */
public class SimulationModel {
    private ScreenRenderer view;
    private int width;
    private int height;
    private int scale;
    boolean[][] cellGridArray;
    boolean[][] cellGridArrayTemporary;


    public SimulationModel(int w, int h, int s) {
        this.width = w;
        this.height = h;
        this.scale = s;
        this.cellGridArray = new boolean[width/scale][height/scale];
        this.cellGridArrayTemporary = new boolean[width/scale][height/scale];
    }


    public boolean randomBoolean() {
        boolean output;
        int randomInt = (int) Math.floor(Math.random() * 2);
        if (randomInt == 1) {output = true;}
        else output = false;
        return output;
    }

    public void randomizeCellGrid() {
        System.out.println("Randomizing cells...");
        for (int z = 1; z < (height/scale) - 1; z++) {
            for (int i = 1; i < (width / scale) - 1; i++) {
                cellGridArray[i][z] = randomBoolean();
                //System.out.println("x=" + i + " y=" + z + " " + cellGridArray[i][z]);
            }
        }
    }

    public int checkAliveNeighbours(int x, int y) {
        int aliveNeighbours = 0;

        if (cellGridArray[x - 1][y - 1]) {aliveNeighbours++;} //System.out.println("Cell " + (x-1) + " " + (y-1) + " is alive");} else System.out.println("Cell " + (x-1) + " " + (y-1) + " is dead");
        if (cellGridArray[x + 0][y - 1]) {aliveNeighbours++;} //System.out.println("Cell " + (x) + " " + (y-1) + " is alive");} else System.out.println("Cell " + (x) + " " + (y-1) + " is dead");
        if (cellGridArray[x + 1][y - 1]) {aliveNeighbours++;} //System.out.println("Cell " + (x+1) + " " + (y-1) + " is alive");} else System.out.println("Cell " + (x+1) + " " + (y-1) + " is dead");
        if (cellGridArray[x - 1][y - 0]) {aliveNeighbours++;} //System.out.println("Cell " + (x-1) + " " + (y) + " is alive");} else System.out.println("Cell " + (x-1) + " " + (y) + " is dead");
        if (cellGridArray[x + 1][y - 0]) {aliveNeighbours++;} //System.out.println("Cell " + (x+1) + " " + (y) + " is alive");} else System.out.println("Cell " + (x+1) + " " + (y) + " is dead");
        if (cellGridArray[x - 1][y + 1]) {aliveNeighbours++;} //System.out.println("Cell " + (x-1) + " " + (y+1) + " is alive");} else System.out.println("Cell " + (x-1) + " " + (y+1) + " is dead");
        if (cellGridArray[x + 0][y + 1]) {aliveNeighbours++;} //System.out.println("Cell " + (x) + " " + (y+1) + " is alive");} else System.out.println("Cell " + (x) + " " + (y+1) + " is dead");
        if (cellGridArray[x + 1][y + 1]) {aliveNeighbours++;} //System.out.println("Cell " + (x+1) + " " + (y+1) + " is alive");} else System.out.println("Cell " + (x+1) + " " + (y+1) + " is dead");

        //if (aliveNeighbours > 0) {System.out.println("Pixel x = " + x + " and y = " + y + " aliveNeighbours = " + aliveNeighbours);}
        //else {System.out.println("No cells alive on grid AROUND " + "Pixel x = " + x + " and y = " + y);}
        return aliveNeighbours;
    }

    public void spawnOrKillCell(int x, int y) {
        if(cellGridArray[x][y] && (checkAliveNeighbours(x, y) == 2 || checkAliveNeighbours(x, y) == 3)) {
            cellGridArrayTemporary[x][y] = true;
        }
        else if (!cellGridArray[x][y] && checkAliveNeighbours(x, y) == 3) {
            cellGridArrayTemporary[x][y] = true;
        }
        else if (cellGridArray[x][y]){
            cellGridArrayTemporary[x][y] = false;
        }
    }

    public void update() {
        //Check if to spawn or kill next update
        for (int y = 1; y < (height / scale) - 1; y++) {
            for (int x = 1; x < (width / scale) - 1; x++) {
                spawnOrKillCell(x,y);
            }
        }

        //Apply Changes
        for (int y = 1; y < (height/scale) - 1; y++) {
            for (int x = 1; x < (width/scale) - 1; x++) {
                cellGridArray[x][y] = cellGridArrayTemporary[x][y];
                //System.out.println("CellgridArray " + x + " " + y + " is " + cellGridArray[x][y]);
            }
        }
    }
    public boolean[][] getCellGridArray() {
        return cellGridArray;
    }
}
