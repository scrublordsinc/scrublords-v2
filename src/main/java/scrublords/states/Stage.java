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
import scrublords.misc.Timer;
import scrublords.tilemaps.Background;
import scrublords.tilemaps.TileMap;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

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
    private int currentChoice = 0;
    private boolean paused;
    private Enemy enemy;
    private AtomicBoolean flag = new AtomicBoolean(true);
    private boolean upgradeComplete;
    private Timer timer = new Timer(flag);
    private Thread thread = new Thread(timer);
    private String[] menuOptions = {
            "Continue",
            "Quit"
    };

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
                enemy = enemies.get(i);
                upgradeEnemies(enemy);
                if (!player.character.flinching) {
                    player.checkDamageTaken(enemy);
                }
                player.meleeAttack(enemy);
                enemyMovement = new EnemyMovement(player, enemy);
                enemyMovement.moveToHero();
                enemy.update();
                if (enemy.enemyStats.dead) {
                    enemies.remove(i);
                    slugger.giveExperience(player, enemy);
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
        g.drawString("Level " + player.level, 30, 30);
        g.drawString("Timer " + timer.minutes + " " + timer.seconds, 30, 60);
        for (Enemy enemy : enemies) {
            enemy.draw(g);
        }

        if (paused) {
            g.setColor(new Color(0, 0, 0));
            g.setFont(new Font("Arial", Font.BOLD, 12));
            g.drawString("Paused", 100, 70);
            g.setFont(new Font("Arial", Font.BOLD, 12));
            for (int i = 0; i < menuOptions.length; i++) {
                if (i == currentChoice) {
                    g.setColor(Color.LIGHT_GRAY);
                } else {
                    g.setColor(Color.BLACK);
                }
                g.drawString(menuOptions[i], 30, 140 + i * 15);
            }
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
        if (paused) {
            if (k == KeyEvent.VK_ENTER) {
                select();
            }
            if (k == KeyEvent.VK_UP) {
                currentChoice--;
                if (currentChoice == -1) {
                    currentChoice = menuOptions.length - 1;
                }
            }
            if (k == KeyEvent.VK_DOWN) {
                currentChoice++;
                if (currentChoice == menuOptions.length) {
                    currentChoice = 0;
                }
            }
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
    }

    private void select() {
        switch (currentChoice) {
            case 0:
                paused = false;
                break;
            case 1:
                GamePanel.stateManager.setState(0);
                break;
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
        thread.start();
    }

    private void upgradeEnemies(Enemy enemy){
        if (timer.seconds == 0){
            upgradeComplete = false;
        }
        if (timer.seconds == 59 && !upgradeComplete){
            enemy.enemyStats.maxHealth++;
            enemy.enemyStats.health++;
            enemy.enemyStats.attackDamage++;
            upgradeComplete = true;
            System.out.println(enemy.enemyStats.health);
        }
    }
}
