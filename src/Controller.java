import ScreenRenderer.ScreenRenderer;
import SimulationModel.SimulationModel;

import javax.swing.*;

/**
 * This is a class
 * Created 2021-10-18
 *
 * @author Magnus Silverdal
 */
public class Controller implements Runnable{
    private Thread thread;
    private boolean running = false;
    private int fps = 1;
    private int ups = 1;
    private int width = 1800;//0;
    private int height = 900;//0;
    private int scale = 10;//1;
    private boolean[][] cellGridArray = new boolean[width][height];
    private double deltaT = 0.04;
    private double t = 0;
    private JFrame frame;
    private String title = "Game of life";
    private ScreenRenderer view;
    private SimulationModel model;


    public Controller() {
        view = new ScreenRenderer(width,height,scale);
        model = new SimulationModel();
        // Frame data
        frame = new JFrame(title);
        frame.setResizable(false);
        frame.add(view);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.requestFocus();
        createCellGrid();
    }

    private void createCellGrid() {
        /**
        int currentX = 0;
        int currentY = 0;
        int currentID = 0;
        for (int i = 0; i < width*height; i++) {

        }
        **/
    }

    public synchronized void start() {
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public synchronized void stop() {
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        double nsFPS = 1000000000.0 / fps;
        double nsUPS = 1000000000.0 / ups;
        double deltaFPS = 0;
        double deltaUPS = 0;
        int frames = 0;
        int updates = 0;
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();

        while (running) {
            long now = System.nanoTime();
            deltaFPS += (now - lastTime) / nsFPS;
            deltaUPS += (now - lastTime) / nsUPS;
            lastTime = now;

            while(deltaUPS >= 1) {
                model.update( t );
                int p = model.getPixels();
                view.draw(p);
                cellGridArray[1][1] = false;
                System.out.println(("1sec"));
                System.out.println(cellGridArray[1][1]);

                //
                updates++;
                deltaUPS--;
                t += deltaT;
            }

            while (deltaFPS >= 1) {
                view.render();
                frames++;
                deltaFPS--;
            }

            if(System.currentTimeMillis() - timer >= 1000) {
                timer += 1000;
                frame.setTitle(this.title + "  |  " + updates + " ups, " + frames + " fps");
                frames = 0;
                updates = 0;
            }
        }
        stop();
    }

    public static void main(String[] args) {
        Controller c = new Controller();
        c.start();
    }
}
