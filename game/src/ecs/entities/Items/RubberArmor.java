package ecs.entities.Items;

import dslToGame.AnimationBuilder;
import ecs.items.Swords;
import graphic.Animation;

public class RubberArmor extends Swords {

    static Animation inventory = AnimationBuilder.buildAnimation("RubberArmor.png");
    static Animation world = AnimationBuilder.buildAnimation("RubberArmor.png");
    static String description = "It lets Attacks bounce right of you";
    static String itemName = "Armor with rubber-coating";
    public RubberArmor(){
        super(inventory,world,itemName,description);
    }
}
