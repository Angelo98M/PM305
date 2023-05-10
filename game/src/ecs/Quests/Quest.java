package ecs.Quests;

import ecs.entities.Entity;

public class Quest {

    protected String questName;
    protected String questText;

    public void cheackquest()
    {

    }
    public void cheackquest(Entity entity)
    {

    }
    /*public void cheackquest(Item item)
    {

    }*/
    public String printQuest()
    {
        return questName+"\n"+questText;
    }
    public void giveReward()
    {

    }
}
