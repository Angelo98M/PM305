package ecs.components.skill;

import ecs.components.MagicPointsComponent;
import ecs.entities.Entity;
import tools.Constants;

public class Skill {

    private ISkillFunction skillFunction;
    private int coolDownInFrames;
    private int currentCoolDownInFrames;
    private int cost;

    /**
     * @param skillFunction Function of this skill
     */
    public Skill(ISkillFunction skillFunction, float coolDownInSeconds,int cost) {
        this.skillFunction = skillFunction;
        this.coolDownInFrames = (int) (coolDownInSeconds * Constants.FRAME_RATE);
        this.currentCoolDownInFrames = 0;
        this.cost=cost;
    }

    /**
     * Execute the method of this skill
     *
     * @param entity entity which uses the skill
     */
    public void execute(Entity entity) {
        if (!isOnCoolDown())
        {
            if(((MagicPointsComponent)entity.getComponent(MagicPointsComponent.class).get()).useMp(cost))
            {
                skillFunction.execute(entity);
                activateCoolDown();
            }
            else
            {
                System.out.println("du Hast nicht genug MP um diesen Skill zu casten");
            }
        }
        else
        {
            System.out.println("Der skill ist noch: "+ currentCoolDownInFrames/ Constants.FRAME_RATE +" sekunden auf CoolDown");
        }
    }

    /**
     * @return true if cool down is not 0, else false
     */
    public boolean isOnCoolDown() {
        return currentCoolDownInFrames > 0;
    }

    /** activate cool down */
    public void activateCoolDown() {
        currentCoolDownInFrames = coolDownInFrames;
    }

    /** reduces the current cool down by frame */
    public void reduceCoolDown() {
        currentCoolDownInFrames = Math.max(0, --currentCoolDownInFrames);
    }

    /** checks if there is any duratuon effects that can be reseted
     * @param entity entity which uses the skill
     */
    public void CheckSkillAbilityReset(Entity entity){ skillFunction.skillAbilityReset(entity);}
}
