package ecs.components;

import ecs.entities.Entity;

public class MagicPointsComponent extends Component {
    private int amountOfMp;
    private int maxAmountOfMp;

    /**
     * Create a new MagicPoint Component and Add it to the entity
     *
     * @param entity associated entity
     * @param startAmountOfMp Amount of start MP points
     */
    public MagicPointsComponent(Entity entity, int startAmountOfMp) {
        super(entity);
        maxAmountOfMp = startAmountOfMp;
        amountOfMp = maxAmountOfMp;
    }
    /** A Methode to Reste the current MP ot the Maximum amount */
    public void resetMp() {
        amountOfMp = maxAmountOfMp;
    }

    /**
     * A Methode that is used to consome MP when called
     *
     * @param value amount of MP that will be used
     * @return returns True if the current MP - value equals 0 or more ohterwise it will retune
     *     False
     */
    public boolean useMp(int value) {
        if (amountOfMp - value >= 0) {
            amountOfMp -= value;
            return true;
        } else {

            return false;
        }
    }

    /**
     * is used to print the current MP
     *
     * @return a string that represent the current MP to the Max MP
     */
    public String printMP() {
        return "MP: " + amountOfMp + "/" + maxAmountOfMp;
    }

    public void addMP() {
        maxAmountOfMp++;
    }
}
