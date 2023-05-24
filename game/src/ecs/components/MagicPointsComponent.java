package ecs.components;

import ecs.entities.Entity;

public class MagicPointsComponent extends Component {
    private int amountOfMp;
    private int maxAmountOfMp;

    /**
     * Create a new component and add it to the associated entity
     *
     * @param entity associated entity
     */
    public MagicPointsComponent(Entity entity,int startAmountOfMp) {
        super(entity);
        maxAmountOfMp=startAmountOfMp;
        amountOfMp=maxAmountOfMp;

    }
    public void resetMp()
    {
        amountOfMp=maxAmountOfMp;
    }
    public void useMp(int value)
    {
        if(amountOfMp-value>=0)
        {
            amountOfMp-=value;
        }
        else
        {
            System.out.println("du Hast nicht genug MP um diesen Skill zu casten");
        }
    }
    public String printMP()
    {
        return "MP: "+ amountOfMp + "/"+ maxAmountOfMp;
    }
    public void addMP()
    {
        maxAmountOfMp++;
    }

}
