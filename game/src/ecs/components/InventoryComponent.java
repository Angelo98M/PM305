package ecs.components;

import ecs.entities.Entity;
import ecs.entities.Items.HealthPotion;
import ecs.items.Empty;
import ecs.items.ItemData;
import ecs.items.Tasche;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import logging.CustomLogLevel;

/** Allows an Entity to carry Items */
public class InventoryComponent extends Component {

    private List<ItemData> inventory;
    private int maxSize;
    private final transient Logger inventoryLogger = Logger.getLogger(this.getClass().getName());

    /**
     * creates a new InventoryComponent
     *
     * @param entity the Entity where this Component should be added to
     * @param maxSize the maximal size of the inventory
     */
    public InventoryComponent(Entity entity, int maxSize) {
        super(entity);
        inventory = new ArrayList<>(maxSize);
        this.maxSize = maxSize;
        setupInventory();
    }

    public void setupInventory() {
        for (int i = 0; i < maxSize; i++) {
            inventory.add(i, new Empty());
        }
    }
    /**
     * Adding an Element to the Inventory does not allow adding more items than the size of the
     * Inventory.
     *
     * @param itemData the item which should be added
     * @return true if the item was added, otherwise false
     */
    public boolean addItem(ItemData itemData) {
        System.out.println(itemData.getClass().getSuperclass().getSimpleName());
        switch (itemData.getClass().getSuperclass().getSimpleName()) {
            case "Swords":
                inventory.remove(0);
                inventory.add(0, itemData);
                inventoryLogger.log(
                        CustomLogLevel.INFO,
                        "Item '"
                                + this.getClass().getSimpleName()
                                + "' was added to the inventory of entity '"
                                + entity.getClass().getSimpleName()
                                + "'.");

                return true;
            case "Armor":
                inventory.remove(1);
                inventory.add(1, itemData);
                inventoryLogger.log(
                        CustomLogLevel.INFO,
                        "Item '"
                                + this.getClass().getSimpleName()
                                + "' was added to the inventory of entity '"
                                + entity.getClass().getSimpleName()
                                + "'.");

                return true;
            case "Consumables":
                inventoryLogger.log(
                        CustomLogLevel.INFO,
                        "Item '"
                                + this.getClass().getSimpleName()
                                + "' was added to the inventory of entity '"
                                + entity.getClass().getSimpleName()
                                + "'.");

                if (inventory.get(2).getDescription()
                        == "It's an Empty slot what do you expect me to say?") {
                    if (inventory.get(3).getDescription()
                            == "Eine Tasche zum Transportieren von Heiltränken") {
                        Tasche bag = ((Tasche) inventory.get(3));
                        if (bag.getAmount() < 4) {
                            bag.addConsumable(new HealthPotion());
                            return true;
                        }
                    }
                }
                inventory.remove(2);
                inventory.add(2, itemData);
                return true;
            case "ItemData":
                System.out.println("woozie");
                inventoryLogger.log(
                        CustomLogLevel.INFO,
                        "Item '"
                                + this.getClass().getSimpleName()
                                + "' was added to the inventory of entity '"
                                + entity.getClass().getSimpleName()
                                + "'.");
                inventory.remove(3);
                inventory.add(3, itemData);

                /*if (inventory.get(3).getClass()== Tasche.class){
                    Tasche tasche = (Tasche)inventory.get(3);
                    if(tasche.addConsumable(itemData) == false){
                        inventory.add(2,itemData);
                        inventoryLogger.log(
                            CustomLogLevel.DEBUG,
                            "Item '"
                                + this.getClass().getSimpleName()
                                + "' was added to the inventory of entity '"
                                + entity.getClass().getSimpleName()
                                + "'.");
                        return true;
                    }
                    inventoryLogger.log(
                        CustomLogLevel.DEBUG,
                        "Item '"
                            + this.getClass().getSimpleName()
                            + "' was added to the inventory of entity '"
                            + entity.getClass().getSimpleName()
                            + "'.");
                    return true;
                }
                else{
                    inventory.add(2,itemData);
                    inventoryLogger.log(
                        CustomLogLevel.DEBUG,
                        "Item '"
                            + this.getClass().getSimpleName()
                            + "' was added to the inventory of entity '"
                            + entity.getClass().getSimpleName()
                            + "'.");
                    return true;
                }*/
        }
        return false;

        /*if (inventory.size() >= maxSize) return false;
        inventoryLogger.log(
                CustomLogLevel.DEBUG,
                "Item '"
                        + this.getClass().getSimpleName()
                        + "' was added to the inventory of entity '"
                        + entity.getClass().getSimpleName()
                        + "'.");
        return inventory.add(itemData);*/
    }

    /**
     * removes the given Item from the inventory
     *
     * @param itemData the item which should be removed
     * @return true if the element was removed, otherwise false
     */
    public boolean removeItem(ItemData itemData) {

        if (itemData.getDescription() != "A Potion that restores 3 HP") {

            return false;
        } else {

            if (inventory.get(2).getDescription() == "A Potion that restores 3 HP") {
                inventory.remove(itemData);

                inventory.add(2, new Empty());
                return true;
            }
            if (inventory.get(3).getDescription()
                    == "Eine Tasche zum Transportieren von Heiltränken") {}
        }

        /*inventoryLogger.log(
                CustomLogLevel.DEBUG,
                "Removing item '"
                        + this.getClass().getSimpleName()
                        + "' from inventory of entity '"
                        + entity.getClass().getSimpleName()
                        + "'.");
        return inventory.remove(itemData);*/
        return false;
    }

    public void setItemList(List<ItemData> list) {
        inventory = list;
    }
    /**
     * @return the number of slots already filled with items
     */
    public int filledSlots() {
        return inventory.size();
    }

    /**
     * @return the number of slots still empty
     */
    public int emptySlots() {
        return maxSize - inventory.size();
    }

    /**
     * @return the size of the inventory
     */
    public int getMaxSize() {
        return maxSize;
    }

    /**
     * @return a copy of the inventory
     */
    public List<ItemData> getItems() {
        return new ArrayList<>(inventory);
    }

    private ItemData getItem(int i) {
        return inventory.get(i);
    }
}
