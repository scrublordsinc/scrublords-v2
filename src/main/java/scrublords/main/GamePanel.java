package scrublords.main;

import scrublords.states.StateManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

/**
 * @author Denis Dimitrov <denis.k.dimitrov@gmail.com>.
 */
public class GamePanel extends JPanel implements Runnable, KeyListener {
    /**
     * scale 2 - 640x480
     * scale 2.5 - 800x600
     * scale 3.2 - 1024x768
     */
    public static StateManager stateManager = new StateManager();
    public static int defaultWidth = 320;
    public static int defaultHeight = 240;
    private static double scale = 2.5;
    private static double gameWidth = defaultWidth * scale;
    private static double gameHeight = defaultHeight * scale;
    private Thread thread;
    private boolean running;
    private int FPS = 60;
    private long targetTime = 1000 / FPS;
    private BufferedImage image;
    private Graphics graphics;

    public GamePanel() {
        super();
        setPreferredSize(new Dimension((int) gameWidth, (int) gameHeight));
        setFocusable(true);
        requestFocus();
    }

    public void addNotify() {
        super.addNotify();
        if (thread == null) {
            thread = new Thread(this);
            addKeyListener(this);
            thread.start();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        stateManager.keyReleased(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        stateManager.keyPressed(e.getKeyCode());
    }

    @Override
    public void run() {
        image = new BufferedImage(defaultWidth, defaultHeight, BufferedImage.TYPE_INT_RGB);
        graphics = image.getGraphics();
        running = true;
        long start;
        long elapsed;
        long wait;
        while (running) {
            start = System.nanoTime();
            update();
            draw();
            drawToScreen();
            elapsed = System.nanoTime() - start;
            wait = targetTime - elapsed / 1000000;
            if (wait < 0) {
                wait = 5;
            }
            try {
                Thread.sleep(wait);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void update() {
        stateManager.update();
    }

    private void draw() {
        stateManager.draw(graphics);
    }

    private void drawToScreen() {
        Graphics g2 = getGraphics();
        g2.drawImage(image, 0, 0, (int) gameWidth, (int) gameHeight, null);
        g2.dispose();
    }
}
