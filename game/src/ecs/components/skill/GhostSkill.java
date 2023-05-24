package ecs.components.skill;

import ecs.components.MissingComponentException;
import ecs.components.VelocityComponent;
import ecs.entities.Entity;

public class GhostSkill implements ISkillFunction{

    private boolean isUsed=false;
    private long start;
    private float speedGain;
    private float normalSpeed;

    public GhostSkill(float speedGain)
    {
        this.speedGain=speedGain;
    }
    @Override
    public void execute(Entity entity) {

        VelocityComponent speedComponent =
            (VelocityComponent)
                entity.getComponent(VelocityComponent.class)
                    .orElseThrow(
                        () -> new MissingComponentException("VelocityComponent"));

        normalSpeed= speedComponent.getXVelocity();
        float newSpeed=normalSpeed*speedGain+normalSpeed;
        speedComponent.setXVelocity(newSpeed);
        speedComponent.setYVelocity(newSpeed);
        isUsed=true;
        start=System.nanoTime();
    }

    @Override
    public void skillAbilityReset(Entity entity) {
        System.out.println("test");
        if(isUsed==true&&(System.nanoTime()-start)/1e9>=10)
        {

            VelocityComponent speedComponent =
                (VelocityComponent)
                    entity.getComponent(VelocityComponent.class)
                        .orElseThrow(
                            () -> new MissingComponentException("VelocityComponent"));
            speedComponent.setYVelocity(normalSpeed);
            speedComponent.setXVelocity(normalSpeed);
        }
    }
}
