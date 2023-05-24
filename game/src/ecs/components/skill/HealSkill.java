package ecs.components.skill;

import ecs.components.HealthComponent;
import ecs.entities.Entity;
import starter.Game;


public class HealSkill implements ISkillFunction {

    private float amountOfHeal;

    public HealSkill(float amountOfHeal) {
        this.amountOfHeal = amountOfHeal;
    }

    public void execute(Entity entity) {
        HealthComponent hc =(HealthComponent)Game.getHero().get().getComponent(HealthComponent.class).get();
        hc.setCurrentHealthpoints(hc.getCurrentHealthpoints()+(int)(hc.getMaximalHealthpoints()*amountOfHeal));
    }
}
