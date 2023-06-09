package ecs.entities;

import SaveManager.GameSave;
import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.components.AnimationComponent;
import ecs.components.PositionComponent;
import ecs.components.VelocityComponent;
import ecs.components.collision.ICollide;
import ecs.components.skill.*;
import ecs.components.xp.ILevelUp;
import ecs.components.xp.XPComponent;
import ecs.damage.Damage;
import ecs.damage.DamageType;
import ecs.items.ItemData;
import ecs.items.Tasche;
import graphic.Animation;
import java.io.Serializable;
import java.util.List;
import java.util.Scanner;
import level.elements.tile.Tile;
import starter.Game;

/**
 * The Hero is the player character. It's entity in the ECS. This class helps to setup the hero with
 * all its components and attributes .
 */
public class Hero extends Entity implements Serializable {

    private static final long serialVerisonUID = 1L;

    private final int fireballCoolDown = 3;
    private final float xSpeed = 0.3f;
    private final float ySpeed = 0.3f;
    private Damage dmg = new Damage(2, DamageType.PHYSICAL, null);
    private final String pathToIdleLeft = "knight/idleLeft";
    private final String pathToIdleRight = "knight/idleRight";
    private final String pathToRunLeft = "knight/runLeft";
    private final String pathToRunRight = "knight/runRight";
    private Skill melee;
    private Skill firstSkill;
    private Skill secondSkill;
    private Skill thirdSkill;
    private SkillComponent skillComp;
    private MagicPointsComponent mpc;
    private Scanner scanner;
    private int gold = 1000;

    /** Entity with Components */
    public Hero() {
        super();
        new PositionComponent(this);
        setupSkillComponent();
        new HealthComponent(
                this,
                26,
                new IOnDeathFunction() {
                    @Override
                    public void onDeath(Entity entity) {
                        Game.GameOver();
                    }
                },
                AnimationBuilder.buildAnimation("character/knight/hit/knight_m_hit_anim_f0.png"),
                AnimationBuilder.buildAnimation("character/knight/hit/knight_m_hit_anim_f0.png"));
        new InventoryComponent(this, 4);
        ((InventoryComponent) this.getComponent(InventoryComponent.class).get())
                .addItem(new Tasche());
        setupMeleeAttack();
        setupVelocityComponent();
        setupAnimationComponent();
        setupHitboxComponent();
        mpc = setupMagicPointsComponent();
        PlayableComponent pc = new PlayableComponent(this);
        setupFireballSkill();
        setupGhostSkill();
        setupHealSkill();
        setupXPComponent();
        skillComp = new SkillComponent(this);
        skillComp.addSkill(firstSkill);
        skillComp.addSkill(secondSkill);
        skillComp.addSkill(thirdSkill);
        skillComp.addSkill(melee);
        pc.setSkillSlot1(firstSkill);
        pc.setSkillSlot4(melee);
    }

    private void setupMeleeAttack() {
        melee = new Skill(new MeleeAttack(), (int) 1, 0);
    }

    private void setupVelocityComponent() {
        Animation moveRight = AnimationBuilder.buildAnimation(pathToRunRight);
        Animation moveLeft = AnimationBuilder.buildAnimation(pathToRunLeft);
        new VelocityComponent(this, xSpeed, ySpeed, moveLeft, moveRight);
    }

    private void setupAnimationComponent() {
        Animation idleRight = AnimationBuilder.buildAnimation(pathToIdleRight);
        Animation idleLeft = AnimationBuilder.buildAnimation(pathToIdleLeft);
        new AnimationComponent(this, idleLeft, idleRight);
    }

    private void setupFireballSkill() {
        firstSkill =
                new Skill(
                        /*new FireballSkill(SkillTools::getCursorPositionAsPoint),
                        fireballCoolDown,
                        1);*/
                        new ShatterFloorSkill(),fireballCoolDown,0);
    }
    /** give hero the skill Ghost and Heal */
    private void setupGhostSkill() {
        secondSkill = new Skill(new GhostSkill(0.5f), 20, 1);
    }
    /** the amountOfHealing is set to 20% */
    private void setupHealSkill() {
        thirdSkill = new Skill(new HealSkill(0.2f), 20, 3);
    }

    private void setupHitboxComponent() {
        HitboxComponent hit =
                new HitboxComponent(
                        this,
                        new ICollide() {
                            @Override
                            public void onCollision(Entity a, Entity b, Tile.Direction from) {
                                if (b.getComponent(HealthComponent.class).isPresent()) {
                                    ((HealthComponent) b.getComponent(HealthComponent.class).get())
                                            .receiveHit(dmg);
                                }
                            }
                        },
                        (you, other, direction) ->
                                System.out.println(
                                        "HeroCollisionLeave") /*health.receiveHit(dmg)*/);
    }

    private MagicPointsComponent setupMagicPointsComponent() {
        return new MagicPointsComponent(this, 10);
    }

    private void setupXPComponent() {
        new XPComponent(
                this,
                new ILevelUp() {
                    @Override
                    public void onLevelUp(long nexLevel) {
                        System.out.println("Du bist nun Level: " + nexLevel);
                        if (nexLevel % 5 == 0) {
                            ((MagicPointsComponent)
                                            Game.getHero()
                                                    .get()
                                                    .getComponent(MagicPointsComponent.class)
                                                    .get())
                                    .addMP();
                            HealthComponent HC =
                                    (HealthComponent)
                                            Game.getHero()
                                                    .get()
                                                    .getComponent(HealthComponent.class)
                                                    .get();
                            HC.setMaximalHealthpoints(HC.getMaximalHealthpoints() + 1);

                        } else {
                            HealthComponent HC =
                                    (HealthComponent)
                                            Game.getHero()
                                                    .get()
                                                    .getComponent(HealthComponent.class)
                                                    .get();
                            HC.setMaximalHealthpoints(HC.getMaximalHealthpoints() + 1);
                        }
                        if (nexLevel == 5) {
                            ((PlayableComponent)
                                            (Game.getHero()
                                                    .get()
                                                    .getComponent(PlayableComponent.class)
                                                    .get()))
                                    .setSkillSlot3(thirdSkill);
                            System.out.println("Du hast den Heal skill erlernt !");
                        }
                        if (nexLevel == 10) {
                            ((PlayableComponent)
                                            (Game.getHero()
                                                    .get()
                                                    .getComponent(PlayableComponent.class)
                                                    .get()))
                                    .setSkillSlot2(secondSkill);
                            System.out.println("Du hast den Ghost skill erlernt !");
                        }
                    }
                });
    }

    public void setDmg(int dmg) {
        this.dmg = new Damage(dmg, DamageType.PHYSICAL, null);
    }

    public Skill getMelee() {
        return melee;
    }

    public void setupSkillComponent() {
        new SkillComponent(this);
    }

    public void checkcurrentSkills(Long level) {
        if (level == 5) {
            ((PlayableComponent) (Game.getHero().get().getComponent(PlayableComponent.class).get()))
                    .setSkillSlot3(thirdSkill);
            System.out.println("Du hast den Heal skill erlernt !");
        }
        if (level == 10) {
            ((PlayableComponent) (Game.getHero().get().getComponent(PlayableComponent.class).get()))
                    .setSkillSlot2(secondSkill);
            System.out.println("Du hast den Ghost skill erlernt !");
        }
    }
    /**
     * Mehtode is for loading the Hero SaveData
     *
     * @param save the GameSave with the old Data;
     */
    public void LoadHero(GameSave save) {

        List<ItemData> items =
                ((InventoryComponent) save.getHero().getComponent(InventoryComponent.class).get())
                        .getItems();
        ((HealthComponent) this.getComponent(HealthComponent.class).get())
                .setMaximalHealthpoints(
                        ((HealthComponent) save.getHero().getComponent(HealthComponent.class).get())
                                .getMaximalHealthpoints());
        ((HealthComponent) this.getComponent(HealthComponent.class).get())
                .setCurrentHealthpoints(
                        ((HealthComponent) save.getHero().getComponent(HealthComponent.class).get())
                                .getCurrentHealthpoints());
        ((XPComponent) this.getComponent(XPComponent.class).get())
                .setCurrentLevel(
                        ((XPComponent) save.getHero().getComponent(XPComponent.class).get())
                                .getCurrentLevel());
        ((XPComponent) this.getComponent(XPComponent.class).get())
                .setCurrentXP(
                        ((XPComponent) save.getHero().getComponent(XPComponent.class).get())
                                .getCurrentXP());
        ((InventoryComponent) this.getComponent(InventoryComponent.class).get()).setItemList(items);
        checkcurrentSkills(
                ((XPComponent) this.getComponent(XPComponent.class).get()).getCurrentLevel());
        this.removeComponent(MagicPointsComponent.class);
        this.addComponent(
                (MagicPointsComponent)
                        save.getHero().getComponent(MagicPointsComponent.class).get());
        mpc =
                ((MagicPointsComponent)
                        save.getHero().getComponent(MagicPointsComponent.class).get());
    }

    /**
     * a function to get current gold that the player has
     *
     * @return current gold that the player has
     */
    public int getGold() {
        return gold;
    }

    /**
     * a funtction to add Gold to the player
     *
     * @param value amount of Gold to be added
     */
    public void addGold(int value) {
        gold += value;
    }

    /**
     * a function that is used for paying a cost and reduce Player gold
     *
     * @param value cost of the object
     */
    public void pay(int value) {
        gold -= value;
    }

}
