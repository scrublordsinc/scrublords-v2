package libgdxexample.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

/**
 * @author Denis Dimitrov <denis.k.dimitrov@gmail.com>.
 */
public class ScrubLords implements Screen {
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer tiledMapRenderer;
    private OrthographicCamera camera;

    @Override
    public void show() {
        tiledMap = new TmxMapLoader().load("C:/Users/Legion/Documents/Projects/game2d/src/main/resources/maps/test.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        camera = new OrthographicCamera();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        tiledMap.dispose();
        tiledMapRenderer.dispose();
    }
}
