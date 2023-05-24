package ecs.components.skill;

import ecs.components.HealthComponent;
import ecs.entities.Entity;
import starter.Game;


public class HealSkill implements ISkillFunction {

    private float amountOfHeal;
    /**
     * methode for the amount Of Healing
     */

    public HealSkill(float amountOfHeal) {
        this.amountOfHeal = amountOfHeal;
    }
    /**
     * the methode looks what the health is then goes to hero and calculatet the amount of healing
     * to the maxlife of the player
     */
    public void execute(Entity entity) {
        HealthComponent hc =(HealthComponent)Game.getHero().get().getComponent(HealthComponent.class).get();
        hc.setCurrentHealthpoints(hc.getCurrentHealthpoints()+(int)(hc.getMaximalHealthpoints()*amountOfHeal));
    }

    @Override
    public void skillAbilityReset(Entity entity) {

    }
}
