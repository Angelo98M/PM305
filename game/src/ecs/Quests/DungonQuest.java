package ecs.Quests;

import starter.Game;

public class DungonQuest extends Quest{
    /**
     * this is a Quest that reward the play if they manege to get to the 5 Dungonlayer
     *
     * **/

    private int depth;

    /**
     * construktor of the Class
     *
     * @param depth int
     * **/
    public DungonQuest(int depth)
    {
        this.depth=depth;
        questName="Erkunde den Dungon";
        questText="Stelle dich den Gefahren des Dungons und ereiche die 5te Ebene";
    }

    @Override
    public void cheackquest() {
        if(Game.getCurrentLevel()==depth)
        {
            System.out.println("Geschafft super");
            giveReward();
            isFinished=true;
        }
    }
}
