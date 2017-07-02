package scrublords.entities.core;

import scrublords.core.SpriteSheet;
import scrublords.entities.characters.Player;
import scrublords.entities.enemies.Enemy;
import scrublords.states.Stage;
import scrublords.tilemaps.TileMap;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * @author Nikolay Zahariev <nikolay.g.zahariev@gmail.com>.
 */
public class EnemySpawner {
    private Enemy enemy;
    private Point enemySpawnPoint;
    private Random randomCoordinateGenerator = new Random();
    private int enemyXSpawnCoordinate;
    private int enemyYSpawnCoordinate;

    public void spawnEnemies(int enemyNumber, TileMap tileMap, SpriteSheet spriteSheet, EnemyStats enemyStats, Movement movement, ArrayList<Enemy> enemies, Player player) {
        for (int i = 0; i < enemyNumber; i++) {
            enemy = new Enemy(tileMap, spriteSheet, enemyStats, movement);

            while (true) {
                enemyXSpawnCoordinate = randomCoordinateGenerator.nextInt(Stage.maxX);
                enemyYSpawnCoordinate = randomCoordinateGenerator.nextInt(Stage.maxY);
                enemySpawnPoint = new Point(enemyXSpawnCoordinate, enemyYSpawnCoordinate);
                enemy.collision.calculateCorners(enemySpawnPoint.x, enemySpawnPoint.y);

                if (whenNearPlayer(player, 150, 150) || whenNoCollision(enemy)) {
                    continue;
                }

                if (whenBottomCollision(enemy)) {
                    enemyYSpawnCoordinate -= 1;
                    enemySpawnPoint = new Point(enemyXSpawnCoordinate, enemyYSpawnCoordinate);
                    enemy.collision.calculateCorners(enemySpawnPoint.x, enemySpawnPoint.y);
                }

                if (whenNoCollision(enemy)) {
                    enemy.collision.characterMapPlacement.setPosition(enemySpawnPoint.x, enemySpawnPoint.y);
                    enemies.add(enemy);
                    break;
                }
            }
        }
    }

    public void spawnBosses(int bossXSpawnCoordinate, int bossYSpawnCoordinate, TileMap tileMap, SpriteSheet spriteSheet, EnemyStats enemyStats, Movement movement, ArrayList<Enemy> enemies) {
        enemy = new Enemy(tileMap, spriteSheet, enemyStats, movement);

        enemySpawnPoint = new Point(bossXSpawnCoordinate, bossYSpawnCoordinate);
        enemy.collision.characterMapPlacement.setPosition(enemySpawnPoint.x, enemySpawnPoint.y);
        enemies.add(enemy);
    }

    private boolean whenNearPlayer(Player player, int leftRange, int rightRange) {
        return enemyXSpawnCoordinate > player.collision.characterMapPlacement.x - leftRange && enemyXSpawnCoordinate < player.collision.characterMapPlacement.x + rightRange;
    }

    private boolean whenNoCollision(Enemy enemy) {
        return ((!enemy.collision.bottomLeft && !enemy.collision.bottomRight) && (!enemy.collision.topLeft && !enemy.collision.topRight));
    }

    private boolean whenBottomCollision(Enemy enemy) {
        return (enemy.collision.bottomLeft || enemy.collision.bottomRight);
    }
}
