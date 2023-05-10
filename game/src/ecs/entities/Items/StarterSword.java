package ecs.entities.Items;

import dslToGame.AnimationBuilder;
import ecs.items.Swords;
import graphic.Animation;

public class StarterSword extends Swords {

    static Animation inventory = AnimationBuilder.buildAnimation("StarterSword1.png");
    static Animation world = AnimationBuilder.buildAnimation("StarterSword1.png");
    static String description = "Your mom bought you this for your grand Adventure";
    static String itemName = "Sword";
    public StarterSword(){
        super(inventory,world,itemName,description);
    }
}
