package scrublords.states;

import scrublords.main.GamePanel;
import scrublords.main.State;
import scrublords.states.core.BackgroundStylization;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * @author Denis Dimitrov <denis.k.dimitrov@gmail.com>.
 */
public class MenuState implements State {
    private BackgroundStylization stylization = new BackgroundStylization();
    private int currentChoice = 0;
    private String[] options = {
            "Normal Stage",
            "Endless Stage",
            "Settings",
            "Help",
            "Quit"
    };

    public MenuState() {
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
        graphics.setColor(stylization.titleColor);
        graphics.setFont(stylization.titleFont);
        graphics.drawString("Scrub Lords", 100, 70);
        graphics.setFont(stylization.font);
        for (int i = 0; i < options.length; i++) {
            if (i == currentChoice) {
                graphics.setColor(Color.GRAY);
            } else {
                graphics.setColor(Color.WHITE);
            }
            graphics.drawString(options[i], 120, 140 + i * 15);
        }
    }

    @Override
    public void keyPressed(int k) {
        if (k == KeyEvent.VK_ENTER) {
            select();
        }
        if (k == KeyEvent.VK_UP) {
            currentChoice--;
            if (currentChoice == -1) {
                currentChoice = options.length - 1;
            }
        }
        if (k == KeyEvent.VK_DOWN) {
            currentChoice++;
            if (currentChoice == options.length) {
                currentChoice = 0;
            }
        }
    }

    @Override
    public void keyReleased(int k) {

    }

    private void select() {
        switch (currentChoice) {
            case 0:
                CharState.stage = "normal";
                GamePanel.stateManager.setState(1);
                break;
            case 1:
                CharState.stage = "endless";
                GamePanel.stateManager.setState(1);
                break;
            case 2:
                GamePanel.stateManager.setState(2);
                break;
            case 3:
                GamePanel.stateManager.setState(3);
                break;
            case 4:
                System.exit(0);
        }
    }
}
