package ecs.entities;

import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.entities.Items.GreatSword;
import ecs.entities.Items.HealthPotion;
import ecs.entities.Items.RubberArmor;
import ecs.items.ItemData;
import graphic.Animation;
import starter.Game;

/** Class for the Entity NpcPenguin */
public class NpcPenguin extends Entity {
    private ItemData[] items;
    private String[] regex;
    private int[] amount;

    private float faktor = 0.1f;
    private Animation idle =
            AnimationBuilder.buildAnimation(
                    "game/assets/character/Npc/NPC_pinguin_Sprechblase.png");
    /** Entity with Components */
    public NpcPenguin() {
        super();
        setupItems();
        new PositionComponent(this);
        new AnimationComponent(this, idle);
        new InteractionComponent(
                this,
                1.0f,
                true,
                new IInteraction() {
                    @Override
                    public void onInteraction(Entity entity) {
                        Game.openShop(items, regex, faktor, amount);
                    }
                });
    }

    private void setupItems() {
        items = new ItemData[3];
        items[0] = new GreatSword();
        items[1] = new RubberArmor();
        items[2] = new HealthPotion();

        regex = new String[3];
        regex[0] = "[GROßSCHWERT\\\\großschwert]+";
        regex[1] = "[GUMMIRÜSTUNG\\\\gummirüstung\\\\GUMMIRUESTUNG\\\\gummiruestung]+";
        regex[2] = "[HEILTRANK\\\\heiltrank]+";

        amount = new int[3];
        amount[0] = 1;
        amount[1] = 1;
        amount[2] = 3;
    }
}
