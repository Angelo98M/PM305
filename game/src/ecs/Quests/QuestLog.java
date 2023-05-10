package ecs.Quests;

import ecs.entities.Entity;
import ecs.entities.Hero;
import java.util.ArrayList;
import java.util.List;

public class QuestLog {
    /**
     * The Questlog holds on to all the Quest the Player currently has and mange them
     * **/
    private Hero hero;
    private static QuestLog questLog = new QuestLog();
    private List<Quest> quests=new ArrayList<>();
    private List<Quest> removeQuest=new ArrayList<>();

    private QuestLog()
    {
    }

    /**
     * this Methode is used to print out all Quests in the Console for the Player to read
     * **/
    public String printLog()
    {
        StringBuilder builder = new StringBuilder();
        if(quests.size()>0)
        {
            for (Quest quest : quests) {
                builder.append(quest.printQuest());
                builder.append("\n");
            }
        }
        else
        {
            builder.append("derzeit ist keine Quest im Log");
        }
        return builder.toString();
    }
    /**
     * this method checks if the Goal of a Quest is complited
     * **/
    public void checkAllQuests()
    {
        for (Quest quest:quests)
        {
          quest.cheackquest();
          if(quest.isFinished)
          {
              removeQuest.add(quest);
          }
        }
        if(!removeQuest.isEmpty())
        {
            deletQuests();
        }
    }
    /**
     * this method checks if the Goal of a Quest is complited mosty cares about Monster killing Quests
     *
     * @param entity Entity
     * **/
    public void checkAllQuests(Entity entity)
    {
        for (Quest quest:quests)
        {
            quest.cheackquest(entity);
            if(quest.isFinished)
            {
                removeQuest.add(quest);
            }
        }
        if(!removeQuest.isEmpty())
        {
            deletQuests();
        }
    }
    /*public void checkAllQuests()
    {
        for (Quest quest:quests)
        {
            quest.cheackquest();
            if(quest.isFinished)
          {
              removeQuest.add(quest);
          }
        }
        if(!removeQuest.isEmpty())
        {
            dealetQuests();
        }
    }*/


    public void SetPlayer(Hero hero)
    {
        this.hero=hero;
    }

    /**
     * This Methode is used to delete alle finished Quests
     * **/
    public void deletQuests()
    {
        for (Quest quest:removeQuest) {
            quests.remove(quest);
        }
        removeQuest=new ArrayList<>();
    }

    /**
     * This Methode is used to add Quest to the Quests List
     *
     * @param  quest Quest
     * **/
    public void addQuest(Quest quest)
    {
        quests.add(quest);
    }
    public static QuestLog getInstance()
    {
        return questLog;
    }
    public void restar()
    {
        questLog=new QuestLog();
    }


}
