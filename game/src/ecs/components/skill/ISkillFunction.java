package ecs.components.skill;

import ecs.entities.Entity;

public interface ISkillFunction {

    /**
     * Implements the concrete skill of an entity
     *
     * @param entity which uses the skill
     */
    void execute(Entity entity);

    /**
     * is Used to Check for Ability effects that have a duration
     *
     * @param entity the which has used the skill
     */
    void skillAbilityReset(Entity entity);
}
