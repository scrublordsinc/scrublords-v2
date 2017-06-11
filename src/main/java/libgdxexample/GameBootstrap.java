package libgdxexample;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import libgdxexample.main.GamePanel;

/**
 * @author Denis Dimitrov <denis.k.dimitrov@gmail.com>.
 */
public class GameBootstrap {
    public static void main(String[] args) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "asd";
        config.width = 1280;
        config.height = 720;
        new LwjglApplication(new GamePanel(), config);
    }
}
