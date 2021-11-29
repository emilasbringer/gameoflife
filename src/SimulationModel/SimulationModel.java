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
    private int width = 1800;
    private int height = 900;
    private int scale = 10;
    private boolean[][] cellGridArray = new boolean[width/scale][height/scale];
    private boolean[][] cellGridArrayTemporary = new boolean[width/scale][height/scale];

    public SimulationModel() {

    }

    public boolean randomBoolean() {
        boolean output;
        int randomInt = (int) Math.floor(Math.random() * 2);
        if (randomInt == 1) {output = true;}
        else output = false;
        return output;
    }

    public int getPixels() {
        return 20;
    }

    public void randomizeCellGrid() {
        System.out.println("Randomizing cells...");
        for (int z = 0; z < (height/scale)-1; z++) {
            for (int i = 0; i < (width / scale) - 1; i++) {
                cellGridArray[i][z] = randomBoolean();
            }
        }
    }

    public int checkReturnAliveNeighbours(int x, int y) {
        int aliveNeighbours = 0;
        for (int i = 0; i < 3; i++) {
            if (cellGridArray[x-1+i][y-1]) {
                aliveNeighbours++;
            }
        }
        if (cellGridArray[x-1][y]) {
            aliveNeighbours++;
        }
        if (cellGridArray[x+1][y]) {
            aliveNeighbours++;
        }
        for (int i = 0; i < 3; i++) {
            if (cellGridArray[x-1+i][y+1]) {
                aliveNeighbours++;
            }
        }
        //System.out.println("Pixel x = " + x + " and y = " + y + " aliveNeighbours = " + aliveNeighbours);
        return aliveNeighbours;
    }

    public void spawnOrKillCell(int x, int y) {
        if (cellGridArray[x][y]) {
            if(checkReturnAliveNeighbours(x, y) == 2 || checkReturnAliveNeighbours(x, y) == 3) {
                cellGridArrayTemporary[x][y] = true;
            }
        }
        if (!cellGridArray[x][y]) {
            if(checkReturnAliveNeighbours(x, y) == 3) {
                cellGridArrayTemporary[x][y] = true;
            }
        }
        else cellGridArrayTemporary[x][y] = false;
    }

    public void update() {
        System.out.println("Updating grid");
        for (int z = 1; z < (height / scale)-2; z++) {
            for (int i = 1; i < (width / scale) - 2; i++) {
                spawnOrKillCell(i,z);
            }
        }

        for(int i = 0; i < (width/scale-1); i++ ) {
            cellGridArrayTemporary[i][0] = false;
            cellGridArrayTemporary[i][height/scale-1] = false;
        }

        for(int i = 0; i < (height/scale-1); i++ ) {
            cellGridArrayTemporary[0][i] = false;
            cellGridArrayTemporary[width/scale-1][i] = false;
        }

        for (int z = 0; z < (height / scale)-1; z++) {
            for (int i = 0; i < (width / scale) - 1; i++) {
                cellGridArray[i][z] = cellGridArrayTemporary[i][z];
            }
        }

    }


    public boolean[][] getCellGridArray() {
        return cellGridArray;
    }
}
