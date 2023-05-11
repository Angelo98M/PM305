package ecs.items;

import dslToGame.AnimationBuilder;
import ecs.entities.Entity;
import graphic.Animation;
import starter.Game;

import java.util.ArrayList;
import java.util.List;

public class Tasche extends ItemData{

    private List<Consumables> content = new ArrayList<>(5);

    private int maxSize=5;
    static ItemType itemType = ItemType.Passive;
    static Animation inventoryTexture = AnimationBuilder.buildAnimation("items/potions/Potionbag.png");
    static Animation worldTexture = AnimationBuilder.buildAnimation("items/potions/Potionbag.png");
    static String itemName = "Tasche";
    static String description = "Eine Tasche zum Transportieren von Heiltränken";
    public Tasche (){
        super(itemType,inventoryTexture,worldTexture,itemName,description);
    }
    public boolean addConsumable(ItemData item){
        if(content.isEmpty()){
            content.add((Consumables) item);
            return true;
        }
        if(content.size()<maxSize){
            content.add((Consumables) item);
            return true;
        }
        return false;
    }
    public ItemData getConsumable(){
        if(content.get(content.size()-1) != null && content.size()>0) {
            ItemData item = content.get(content.size() - 1);
            content.remove(content.size()-1);
            item.triggerUse(Game.getHero().get());
            return item;
        }
        return null;
    }
    public Tasche getTasche(){
        return this;
    }

    public boolean isEmpty(){
        return content.isEmpty();
    }
    public int getAmount(){
        return content.size()-1;
    }
}
