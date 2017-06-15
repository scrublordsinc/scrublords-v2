package scrublords.entities.core;

import scrublords.entities.characters.Player;
import scrublords.entities.enemies.Enemy;

/**
 * @author Nikolay Zahariev <nikolay.g.zahariev@gmail.com>.
 */
public class EnemyMovement {
    private Player player;
    private Enemy enemy;

    public EnemyMovement(Player player, Enemy enemy) {
        this.player = player;
        this.enemy = enemy;
    }

    public void moveToHero() {
        if (checkHeroPosition()) {
            if (enemy.collision.characterMapPlacement.x < player.collision.characterMapPlacement.x + 100) {
                enemy.moveSet.right = false;
                enemy.moveSet.left = true;
            }
            if (enemy.collision.characterMapPlacement.x > player.collision.characterMapPlacement.x + 100) {
                enemy.moveSet.left = false;
                enemy.moveSet.right = false;
            }
        } else {
            if (enemy.collision.characterMapPlacement.x > player.collision.characterMapPlacement.x - 100) {
                enemy.moveSet.right = true;
                enemy.moveSet.left = false;
            } else {
                enemy.moveSet.right = false;
                enemy.moveSet.left = false;
            }
        }
        enemy.moveSet.jumping = jump();
    }

    private boolean checkHeroPosition() {
        return enemy.collision.characterMapPlacement.x > player.collision.characterMapPlacement.x;
    }

    private boolean jump() {
        return heroIsHigher() && (heroOnLeft() || heroOnRight());
    }

    private boolean heroOnLeft() {
        return enemy.collision.characterMapPlacement.x > player.collision.characterMapPlacement.x - 50 && enemy.collision.characterMapPlacement.x < player.collision.characterMapPlacement.x;
    }

    private boolean heroOnRight() {
        return enemy.collision.characterMapPlacement.x < player.collision.characterMapPlacement.x + 50 && enemy.collision.characterMapPlacement.x > player.collision.characterMapPlacement.x;
    }

    private boolean heroIsHigher() {
        return enemy.collision.characterMapPlacement.y > player.collision.characterMapPlacement.y;
    }
}
