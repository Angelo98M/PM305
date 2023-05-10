package ecs.entities.Items;

import dslToGame.AnimationBuilder;
import ecs.items.Swords;
import graphic.Animation;

public class GreatSword extends Swords {

    static Animation inventory = AnimationBuilder.buildAnimation("greatSword.png");
    static Animation world = AnimationBuilder.buildAnimation("greatSword.png");
    static String description = "Your mom bought you this for your grand Adventure";
    static String itemName = "Sword";
    public GreatSword(){
        super(inventory,world,itemName,description);
    }
}
