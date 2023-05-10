package ecs.Quests;

import ecs.entities.Entity;

public class Quest {
    /**
     * this is the Blueprint for Quests they will reward the player wiht item and later xp
     * **/

    protected String questName;
    protected String questText;
    protected Boolean isFinished=false;

    /**
     * this method checks if the quests Goal is complited
     * **/
    public void cheackquest()
    {

    }
    /**
     * this method checks if the quests Goal is complited mosty cares about Monster killing Quests
     *
     * @param entity Entity
     * **/
    public void cheackquest(Entity entity)
    {

    }
    /*public void cheackquest(Item item)
    {

    }*/
    /**
     * givs out a littel bit of text for the player to read about the Quest
     * **/
    public String printQuest()
    {
        return questName+"\n"+questText;
    }

    /**
     *  hands the player the reward
     * **/
    public void giveReward()
    {

    }

    public Boolean getFinished() {
        return isFinished;
    }
}
