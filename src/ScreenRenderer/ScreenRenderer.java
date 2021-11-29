package ScreenRenderer;

import SimulationModel.SimulationModel;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import java.util.ArrayList;

/**
 * This is a class
 * Created 2021-10-18
 *
 * @author Magnus Silverdal
 */

/**
 * A system for drawing pixelgraphics to the screen using native Java.
 * Created 2021-03-31
 *
 * @author Magnus Silverdal
 */
public class ScreenRenderer extends Canvas {
    private int WIDTH;
    private int HEIGTH;
    private int scale;

    private Screen screen;
    private BufferedImage image;
    private SimulationModel model;

    public ScreenRenderer(int width, int height, int scale) {
        // Screen data
        this.WIDTH = width;
        this.HEIGTH = height;
        this.scale = scale;
        image = new BufferedImage(WIDTH/scale, HEIGTH/scale, BufferedImage.TYPE_INT_RGB);
        screen = new Screen(((DataBufferInt) image.getRaster().getDataBuffer()).getData(),image.getWidth(), image.getHeight());
        setPreferredSize(new Dimension(WIDTH, HEIGTH));
    }

    public void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        g.drawImage(image, 0, 0, WIDTH, HEIGTH, null);
        g.dispose();
        bs.show();
    }

    public void draw(boolean[][] array) {
        for (int z = 0; z < (HEIGTH/scale)-1; z++) {
            for (int i = 0; i < WIDTH/scale-1; i++) {
                if(array[i][z]) {
                    screen.draw(i,z,0xFFFFFF);
                }
            }
        }
    }

    public int getWIDTH() {
        return WIDTH;
    }

    public int getHEIGTH() {
        return HEIGTH;
    }

    public int getScale() {
        return scale;
    }
}
