package scrublords.states;

import scrublords.main.GamePanel;
import scrublords.main.State;
import scrublords.states.core.BackgroundStylization;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * @author Denis Dimitrov <denis.k.dimitrov@gmail.com>.
 */
public class DeathState implements State {
    private BackgroundStylization stylization = new BackgroundStylization();
    private int currentChoice = 0;

    public DeathState() {
        stylization.loadBackgroundAndFonts();
    }

    @Override
    public void init() {

    }

    @Override
    public void update() {
        stylization.background.update();
    }

    @Override
    public void draw(Graphics graphics) {
        stylization.background.draw(graphics);
        graphics.setColor(Color.RED);
        graphics.setFont(stylization.titleFont);
        graphics.drawString("YOU DIED", 100, 70);
        graphics.setFont(stylization.font);
            graphics.drawString("Continue", 135, 140);
    }

    @Override
    public void keyPressed(int k) {
        if (k == KeyEvent.VK_ENTER) {
            select();
        }
    }

    @Override
    public void keyReleased(int k) {

    }

    private void select() {
        switch (currentChoice) {
            case 0:
                GamePanel.stateManager.setState(0);
                break;
        }
    }
}
