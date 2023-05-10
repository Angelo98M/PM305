package ecs.entities.Items;

import dslToGame.AnimationBuilder;
import ecs.items.Armor;
import graphic.Animation;

public class StarterArmor extends Armor {

    static Animation inventory = AnimationBuilder.buildAnimation("StarterArmor.png");
    static Animation world = AnimationBuilder.buildAnimation("StarterArmor.png");
    static String description = "Your mom bought you this for your grand Adventure";
    static String itemName = "Armor";
    public StarterArmor(){
        super(inventory,world,itemName,description);
    }
}
