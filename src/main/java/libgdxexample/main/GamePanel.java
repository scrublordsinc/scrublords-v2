package libgdxexample.main;

import com.badlogic.gdx.Game;

/**
 * @author Denis Dimitrov <denis.k.dimitrov@gmail.com>.
 */
public class GamePanel extends Game {

    @Override
    public void create() {
        setScreen(new ScrubLords());
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
