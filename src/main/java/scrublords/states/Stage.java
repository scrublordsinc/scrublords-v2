package scrublords.states;

import scrublords.entities.characters.Berserker;
import scrublords.entities.characters.Lich;
import scrublords.entities.characters.Player;
import scrublords.entities.core.EnemyMovement;
import scrublords.entities.core.EnemySpawner;
import scrublords.entities.enemies.Enemy;
import scrublords.entities.enemies.Slugger;
import scrublords.main.GamePanel;
import scrublords.main.State;
import scrublords.tilemaps.Background;
import scrublords.tilemaps.TileMap;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

/**
 * @author Denis Dimitrov <denis.k.dimitrov@gmail.com>.
 */
public class Stage implements State {
    public static int mapPitfall;
    public static int maxX;
    public static int maxY;
    private TileMap tileMap;
    private Background background = new Background(0.1);
    private Berserker berserker;
    private Lich lich;
    private Player player;
    private EnemySpawner enemySpawner;
    private ArrayList<Enemy> enemies;
    private EnemyMovement enemyMovement;
    private Slugger slugger;
    private int enemyNumber = 10;
    private ArrayList<String> levels = new ArrayList<>();
    private boolean paused;

    public Stage() {
        if (Objects.equals(CharState.character, "berserker")) {
            loadRandomLevel();
        }
        if (Objects.equals(CharState.character, "lich")) {
            loadRandomLevel();
        }
    }

    @Override
    public void init() {

    }

    @Override
    public void update() {
        if (!paused) {
            player.update();
            if (player.collision.characterMapPlacement.y > mapPitfall) {
                player.isDead();
                return;
            }
            tileMap.setPosition(GamePanel.defaultWidth / 2 - player.collision.characterMapPlacement.getx(), GamePanel.defaultHeight / 2 - player.collision.characterMapPlacement.gety());
            for (int i = 0; i < enemies.size(); i++) {
                Enemy enemy = enemies.get(i);
                if (!player.character.flinching) {
                    player.checkDamageTaken(enemy);
                }
                player.meleeAttack(enemy);
                enemyMovement = new EnemyMovement(player, enemy);
                enemyMovement.moveToHero();
                enemy.update();
                if (enemy.enemyStats.dead) {
                    enemies.remove(i);
                    enemy.enemyStats.dead = false;
                }
            }
        }
    }

    @Override
    public void draw(Graphics g) {
        background.draw(g);
        tileMap.draw(g);
        player.draw(g);
        for (Enemy enemy : enemies) {
            enemy.draw(g);
        }
    }

    @Override
    public void keyPressed(int k) {
        if (k == KeyEvent.VK_A | k == KeyEvent.VK_LEFT) {
            player.moveSet.left = false;
        }
        if (k == KeyEvent.VK_D | k == KeyEvent.VK_RIGHT) {
            player.moveSet.right = false;
        }
        if (k == KeyEvent.VK_W | k == KeyEvent.VK_UP) {
            player.moveSet.jumping = false;
        }
        if (k == KeyEvent.VK_J | k == KeyEvent.VK_Z) {
            player.character.attacking = false;
        }
        if (k == KeyEvent.VK_ESCAPE) {
            paused = true;
        }
        if (k == KeyEvent.VK_F1) {
            paused = false;
        }
    }

    @Override
    public void keyReleased(int k) {
        if (k == KeyEvent.VK_A | k == KeyEvent.VK_LEFT) {
            player.moveSet.left = true;
        }
        if (k == KeyEvent.VK_D | k == KeyEvent.VK_RIGHT) {
            player.moveSet.right = true;
        }
        if (k == KeyEvent.VK_W | k == KeyEvent.VK_UP) {
            player.moveSet.jumping = true;
        }
        if (k == KeyEvent.VK_J | k == KeyEvent.VK_Z) {
            player.character.attacking = true;
        }
        if (k == KeyEvent.VK_ESCAPE) {
            paused = true;
        }
        if (k == KeyEvent.VK_F1) {
            paused = false;
        }
    }

    private void loadLevelOne() {
        mapPitfall = 220;
        maxX = 3200;
        maxY = 210;
        tileMap = new TileMap(30);
        tileMap.tileLoading.loadTiles("/tilesets/grasstileset.gif");
        tileMap.mapLoading.loadMap("/maps/levelOne.map");
        tileMap.setPosition(0, 0);
        background.getResource("/backgrounds/levelone.gif");
        berserker = new Berserker(tileMap);
        lich = new Lich(tileMap);
        berserker = new Berserker(tileMap);
        if (Objects.equals(CharState.character, "berserker")) {
            player = new Player(tileMap, berserker.spriteSheet, berserker.character, berserker.movement);
            player.collision.characterMapPlacement.setPosition(100, 200);
        }
        if (Objects.equals(CharState.character, "lich")) {
            player = new Player(tileMap, lich.spriteSheet, lich.character, lich.movement);
            player.collision.characterMapPlacement.setPosition(100, 200);
        }
        slugger = new Slugger(tileMap);
        enemies = new ArrayList<>();
        enemySpawner = new EnemySpawner();
        enemySpawner.spawnEnemies(enemyNumber, tileMap, slugger.spriteSheet, slugger.enemyStats, slugger.movement, enemies, player);
    }


    private void loadLevelTwo() {
        mapPitfall = 410;
        maxX = 3200;
        maxY = 400;
        tileMap = new TileMap(30);
        tileMap.tileLoading.loadTiles("/tilesets/grasstileset.gif");
        tileMap.mapLoading.loadMap("/maps/levelTwo.map");
        tileMap.setPosition(600, 380);
        background.getResource("/backgrounds/levelone.gif");
        berserker = new Berserker(tileMap);
        lich = new Lich(tileMap);
        berserker = new Berserker(tileMap);
        slugger = new Slugger(tileMap);
        if (Objects.equals(CharState.character, "berserker")) {
            player = new Player(tileMap, berserker.spriteSheet, berserker.character, berserker.movement);
            player.collision.characterMapPlacement.setPosition(600, 380);
        }
        if (Objects.equals(CharState.character, "lich")) {
            player = new Player(tileMap, lich.spriteSheet, lich.character, lich.movement);
            player.collision.characterMapPlacement.setPosition(600, 380);
        }
        enemies = new ArrayList<>();
        enemySpawner = new EnemySpawner();
        enemySpawner.spawnEnemies(enemyNumber, tileMap, slugger.spriteSheet, slugger.enemyStats, slugger.movement, enemies, player);
    }

    private void shuffleLevels() {
        levels.add("Level One");
        levels.add("Level Two");
        Collections.shuffle(levels);
    }

    private void loadRandomLevel() {
        shuffleLevels();
        if (Objects.equals(levels.get(0), "Level One")) {
            loadLevelOne();
        }
        if (Objects.equals(levels.get(0), "Level Two")) {
            loadLevelTwo();
        }
    }
}
