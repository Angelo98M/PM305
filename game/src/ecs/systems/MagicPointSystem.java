package ecs.systems;

import ecs.components.MagicPointsComponent;
import ecs.components.skill.SkillComponent;
import starter.Game;

public class MagicPointSystem extends ECS_System{
    @Override
    public void update() {
   //     Game.getEntities().stream()
            // Consider only entities that have a MagicPointComponent
       //     .flatMap(e -> e.getComponent(MagicPointSystem.class).stream())
       //     .forEach(mpc -> ((MagicPointsComponent) mpc).);
    }
}
