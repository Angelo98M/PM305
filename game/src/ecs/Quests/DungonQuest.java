package ecs.Quests;

import starter.Game;

public class DungonQuest extends Quest{

    private int tiefe;

    public DungonQuest(int tiefe)
    {
        this.tiefe=tiefe;
        questName="Erkunde den Dungon";
        questText="Stelle dich den Gefahren des Dungons und ereiche die 5te Ebene";
    }

    @Override
    public void cheackquest() {

    }
}
