package ecs.entities;

import dslToGame.AnimationBuilder;
import ecs.components.AnimationComponent;
import ecs.components.PositionComponent;
import ecs.components.VelocityComponent;
import ecs.components.ai.AIComponent;
import ecs.components.ai.fight.CollideAI;
import ecs.components.ai.fight.IFightAI;
import ecs.components.ai.idle.IIdleAI;
import ecs.components.ai.idle.RadiusWalk;
import ecs.components.ai.transition.ITransition;
import ecs.components.ai.transition.RangeTransitionOutofRange;
import graphic.Animation;
import starter.Game;

public class Ghost extends Entity{
/**
 * this Ghost is a frendly NPC that follows the player around until you found and tocht
 * his Tomestone
 * */
    private final Animation spriteLeft=AnimationBuilder.buildAnimation("character/Geist/Links");
    private final Animation spriteRight=AnimationBuilder.buildAnimation("character/Geist/Rechts");
    private final IIdleAI radiusWalk= new RadiusWalk(2,1);
    private final IFightAI isInRange = new CollideAI(2);
    private final ITransition aiSwitch=new RangeTransitionOutofRange(6);
    private final float speed=0.2f;
    private final AnimationComponent animation;
    private final VelocityComponent movement;
    private final AIComponent ai;
    private boolean isVisibil=true;
    /**
     * Constructor that helps to set the Components
     * */
    public Ghost()
    {
        new PositionComponent(this);
        animation=new AnimationComponent(this, spriteLeft);
        movement=new VelocityComponent(this,speed,speed,spriteLeft,spriteRight);
        ai=new AIComponent(this,isInRange,radiusWalk,aiSwitch);


    }

    /**
     * A Function to see if the Ghost is visibil or not
     *
     * @return boolean isVisibil
     * */
    public boolean isVisibil() {
        return isVisibil;
    }

    /**
     * A Function to remove the Ghost from the Game
     * */
    public void RemoveGeist()
    {
        Game.removeEntity(this);
    }
    /**
     * A Funktion that removed Components form the Ghost so that he can be invisibil
     * */
    public void SetInvisibil()
    {
        removeComponent(AIComponent.class);
        removeComponent(VelocityComponent.class);
        removeComponent(AnimationComponent.class);
        removeComponent(PositionComponent.class);
        isVisibil=false;
    }
    /**
     * A Function that add the old Components to the Ghost and give him a new position
     * so u can see him again
     * */
    public void SetVisibil()
    {
        new PositionComponent(this);
        addComponent(animation);
        addComponent(movement);
        addComponent(ai);
        isVisibil=true;

    }




}
