package ecs.components.skill;

import ecs.components.PositionComponent;
import ecs.entities.Entity;
import ecs.entities.Monster;
import starter.Game;
import tools.Point;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.lang.Math.sqrt;
import static starter.Game.getEntities;

public class ClosestToHero implements ITargetSelection{
    /**
     * Calculates the shortest path to the closest Monster by using pythagorean theorem
     * @return the Point of the closest Enemy
     */
    @Override
    public Point selectTargetPoint() {
        Set<Entity> all = Game.getEntities();
        List<Entity> monster = new ArrayList<>();

        for (Entity current : all) {
            System.out.println(current.getClass().getSuperclass().getSimpleName());
            if (current.getClass().getSuperclass().getSimpleName().equals("Monster")) {
                monster.add(current);

            }
        }
        PositionComponent heroPos = (PositionComponent) Game.getHero().get().getComponent(PositionComponent.class).get();
        PositionComponent currentPos;
        PositionComponent closestPos = null;
        float heroPosX = heroPos.getPosition().x;
        float heroPosY = heroPos.getPosition().y;
        float currentPosX;
        float currentPosY;
        float distanceX;
        float distanceY;
        float distance;
        float minDistance = 999;
        if(monster.isEmpty()){System.out.println("Nice try Guy!");}
        if(!monster.isEmpty()) {
            for (Entity current : monster) {
                if (current.getComponent(PositionComponent.class).isPresent()) {
                    currentPos = (PositionComponent) current.getComponent(PositionComponent.class).get();
                    currentPosX = currentPos.getPosition().x;
                    currentPosY = currentPos.getPosition().y;

                    if ((currentPosX != heroPosX || currentPosY != heroPosY)) {
                        distanceX = Math.abs(currentPosX - heroPosX);
                        distanceY = Math.abs(currentPosY - heroPosY);
                        distance = (float) sqrt((distanceX * distanceX + distanceY * distanceY));
                        if (distance < minDistance) {
                            minDistance = distance;
                            closestPos = currentPos;
                        }

                    }

                }

            }
        }

        if (closestPos == null) {return new Point(heroPosX + 3, heroPosY+3);}
        return closestPos.getPosition();
    }
}
