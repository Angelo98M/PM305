package ecs.Quests;

import ecs.entities.Entity;
import ecs.entities.Hero;
import java.util.ArrayList;
import java.util.List;

public class QuestLog {
    private Hero hero;
    private static QuestLog questLog = new QuestLog();
    private List<Quest> quests=new ArrayList<>();

    private QuestLog()
    {

    }

    public String printLog()
    {
        StringBuilder builder=new StringBuilder();
        for (Quest quest:quests)
        {
            builder.append(quest.printQuest());
            builder.append("\n");
        }
        return builder.toString();
    }
    public void checkAllQuests()
    {
        for (Quest quest:quests)
        {
          quest.cheackquest();
        }
    }
    public void checkAllQuests(Entity entity)
    {
        for (Quest quest:quests)
        {
            quest.cheackquest(entity);
        }
    }
    /*public void checkAllQuests()
    {
        for (Quest quest:quests)
        {
            quest.cheackquest();
        }
    }*/

    public void SetPlayer(Hero hero)
    {
        this.hero=hero;
    }

    public void dealetQuest(Quest quest)
    {
        quests.remove(quest);
    }
    public void addQuest(Quest quest)
    {
        quests.add(quest);
    }
    public static QuestLog getInstance()
    {
        return questLog;
    }


}
