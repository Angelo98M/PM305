package SaveManager;

import ecs.entities.Entity;
import ecs.entities.Ghost;
import java.io.Serializable;
import starter.Game;

/**
 * This Class is used to save Game data so when u dont want to play annaymore your progress is saved
 */
public class GameSave implements Serializable {
    private int depth;
    private Boolean hasGhost;
    private Entity hero;

    public GameSave() {
        depth = Game.getCurrentLevel();
        hero = Game.getHero().get();
        hasGhost = chekifghost();
    }

    public Entity getHero() {
        return hero;
    }

    public int getDepth() {
        return depth;
    }

    private boolean chekifghost() {
        return Game.getEntities().stream().anyMatch(i -> i.getClass().equals(Ghost.class));
    }

    public Boolean getHasGhost() {
        return hasGhost;
    }
}
