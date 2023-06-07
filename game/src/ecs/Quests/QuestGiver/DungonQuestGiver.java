package ecs.Quests.QuestGiver;

import dslToGame.AnimationBuilder;
import ecs.Quests.DungonQuest;
import ecs.Quests.QuestLog;
import ecs.components.AnimationComponent;
import ecs.components.IInteraction;
import ecs.components.InteractionComponent;
import ecs.components.PositionComponent;
import ecs.entities.Entity;
import starter.Game;

public class DungonQuestGiver extends Entity {
    /** This is an Entity used to give the Player a Quest * */
    public DungonQuestGiver() {
        new PositionComponent(this);
        new AnimationComponent(
                this, AnimationBuilder.buildAnimation("dungeon/ice/floor/floor_1.png"));
        new InteractionComponent(
                this,
                1.0f,
                false,
                new IInteraction() {
                    @Override
                    public void onInteraction(Entity entity) {
                        QuestLog.getInstance().addQuest(new DungonQuest(5));
                        Game.removeEntity(entity);
                    }
                });
    }
}
