package scrublords.misc;

import scrublords.main.GamePanel;
import scrublords.entities.characters.Player;
/**
 * @author Nikolay Zahariev <nikolay.g.zahariev@gmail.com>.
 */
public class BossMechanics {

    public void levelOneBossBattleStartTeleport(Player player) {
        if (player.collision.characterMapPlacement.x >= 3060 && player.collision.characterMapPlacement.x <= 3100) {
            player.collision.characterMapPlacement.setPosition(3491, 150);
        }
    }

    public void bossBattleEndTeleport() {
        GamePanel.stateManager.setState(0);
    }
}
