package ecs.items;

import dslToGame.AnimationBuilder;
import graphic.Animation;

public class Empty extends ItemData {

    static ItemType itemType = ItemType.Passive;
    static Animation inventoryTexture = AnimationBuilder.buildAnimation("items/Empty.png");
    static Animation worldTexture = AnimationBuilder.buildAnimation("items/Empty.png");
    ;
    static String itemName = "Empty";
    static String description = "It's an Empty slot what do you expect me to say?";

    public Empty() {
        super(itemType, inventoryTexture, worldTexture, itemName, description);
    }
}
