package ecs.items;

import ecs.entities.Items.GreatSword;
import ecs.entities.Items.HealthPotion;
import ecs.entities.Items.RubberArmor;
import graphic.Animation;
import java.io.Serializable;
import java.util.List;
import java.util.Random;

/** Generator which creates a random ItemData based on the Templates prepared. */
public class ItemDataGenerator implements Serializable {
    private static final List<String> missingTexture = List.of("animation/missingTexture.png");

    private List<ItemData> monsterLoot = List.of((ItemData) new HealthPotion());

    private List<ItemData> chestLoot =
            List.of((ItemData) new GreatSword(), (ItemData) new RubberArmor());
    private Random rand = new Random();

    /**
     * @return a new randomItemData for the wanted loot pool type = 1 monsterLoot type = 2 chestLoot
     */
    public ItemData generateItemData(int type) {
        switch (type) {
            case 1:
                return monsterLoot.get(rand.nextInt(monsterLoot.size()));
            case 2:
                return chestLoot.get(rand.nextInt(chestLoot.size()));
        }
        return new ItemData(
                ItemType.Basic,
                new Animation(missingTexture, 1),
                new Animation(missingTexture, 1),
                "Fehler",
                "Irgendwas ist schiefgelaufen");
    }
}
