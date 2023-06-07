package ecs.entities.Traps;

import static starter.Game.currentLevel;

import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.components.collision.ICollide;
import ecs.damage.Damage;
import ecs.damage.DamageType;
import ecs.entities.Entity;
import graphic.Animation;
import java.util.ArrayList;
import java.util.Random;
import level.elements.tile.FloorTile;
import level.elements.tile.Tile;
import level.elements.tile.WallTile;
import level.tools.Coordinate;
import starter.Game;
import tools.Point;

public class Arrow extends Traps {

    private float xSpeed = 0f;
    private float ySpeed = 0f;
    private int direction = 0;
    private Point destination;
    private final String pathToRunUp = "dungeon/Traps/runUp_arrow";
    private final String pathToRunDown = "dungeon/Traps/runDown_arrow";
    private final String pathToRunLeft = "dungeon/Traps/runLeft_arrow";
    private final String pathToRunRight = "dungeon/Traps/runRight_arrow";
    private Animation arrowAnimation =
            AnimationBuilder.buildAnimation("dungeon/Traps/runRight_arrow");

    public Arrow() {
        super();

        new PositionComponent(this);
        new HitboxComponent(
                this,
                new ICollide() {
                    @Override
                    public void onCollision(Entity a, Entity b, Tile.Direction from) {
                        if (b.getComponent(HealthComponent.class).isPresent()) {
                            HealthComponent health =
                                    (HealthComponent) b.getComponent(HealthComponent.class).get();
                            health.receiveHit(new Damage(1, DamageType.PHYSICAL, null));
                        }
                        Game.removeEntity(a);
                    }
                },
                (you, other, direction) -> System.out.println("Left Arrow Hitbox"));
        setArrowTrap(new Pressureplate(this), this);
        updateDirection();
        updateAnimation();
        new VelocityComponent(this, 0f, 0f, arrowAnimation, arrowAnimation);
        new AnimationComponent(this, arrowAnimation);
    }

    private void setArrowTrap(Entity Trap, Entity Projectile) {
        PositionComponent pcTrap =
                (PositionComponent)
                        Trap.getComponent(PositionComponent.class)
                                .orElseThrow(
                                        () -> new MissingComponentException("PositionComponent"));
        PositionComponent pcProjectile =
                (PositionComponent)
                        Projectile.getComponent(PositionComponent.class)
                                .orElseThrow(
                                        () -> new MissingComponentException("PositionComponent"));

        ArrayList<WallTile> eligible = new ArrayList<>();
        eligible = (ArrayList<WallTile>) currentLevel.getWallTiles();
        ArrayList<FloorTile> floortest = new ArrayList<>();
        floortest = (ArrayList<FloorTile>) currentLevel.getFloorTiles();
        ArrayList<Tile> walls = new ArrayList<>();
        for (int i = 0; i < eligible.size(); i++) {
            walls.add((Tile) eligible.get(i));
        }
        ArrayList<Tile> floor = new ArrayList<>();
        for (int i = 0; i < floortest.size(); i++) {
            floor.add((Tile) floortest.get(i));
        }
        Random rnd = new Random();
        Coordinate wall = eligible.get(rnd.nextInt(eligible.size())).getCoordinate();
        pcProjectile.setPosition(wall.toPoint());
        int i = 1;
        if (floor.contains(currentLevel.getTileAt(new Coordinate(wall.x - 1, wall.y)))) {
            direction = 1;

            while (floor.contains(currentLevel.getTileAt(new Coordinate(wall.x - i, wall.y)))) {
                i++;
            }

            destination =
                    currentLevel
                            .getTileAt(new Coordinate(wall.x - i + 1, wall.y))
                            .getCoordinateAsPoint();
            i = rnd.nextInt(4) + 1;
            while (!floor.contains(currentLevel.getTileAt(new Coordinate(wall.x - i, wall.y)))) {
                i = rnd.nextInt(4) + 1;
            }
            pcTrap.setPosition(new Coordinate(wall.x - i, wall.y).toPoint());
            return;
        } else if (floor.contains(currentLevel.getTileAt(new Coordinate(wall.x + 1, wall.y)))) {
            direction = 2;
            while (floor.contains(currentLevel.getTileAt(new Coordinate(wall.x + i, wall.y)))) {
                i++;
            }

            destination =
                    currentLevel
                            .getTileAt(new Coordinate(wall.x + i - 1, wall.y))
                            .getCoordinateAsPoint();
            i = rnd.nextInt(4) + 1;
            while (!floor.contains(currentLevel.getTileAt(new Coordinate(wall.x + i, wall.y)))) {
                i = rnd.nextInt(4) + 1;
            }
            pcTrap.setPosition(new Coordinate(wall.x + i, wall.y).toPoint());
            return;
        } else if (floor.contains(currentLevel.getTileAt(new Coordinate(wall.x, wall.y - 1)))) {
            direction = 3;
            while (floor.contains(currentLevel.getTileAt(new Coordinate(wall.x, wall.y - i)))) {
                i++;
            }

            destination =
                    currentLevel
                            .getTileAt(new Coordinate(wall.x, wall.y - i + 1))
                            .getCoordinateAsPoint();
            i = rnd.nextInt(4) + 1;
            while (!floor.contains(currentLevel.getTileAt(new Coordinate(wall.x, wall.y - i)))) {
                i = rnd.nextInt(4) + 1;
            }
            pcTrap.setPosition(new Coordinate(wall.x, wall.y - i).toPoint());
            return;
        } else if (floor.contains(currentLevel.getTileAt(new Coordinate(wall.x, wall.y + 1)))) {
            direction = 4;
            while (floor.contains(currentLevel.getTileAt(new Coordinate(wall.x, wall.y + i)))) {
                i++;
            }

            destination =
                    currentLevel
                            .getTileAt(new Coordinate(wall.x, wall.y + i - 1))
                            .getCoordinateAsPoint();
            i = rnd.nextInt(4) + 1;
            while (!floor.contains(currentLevel.getTileAt(new Coordinate(wall.x, wall.y + i)))) {
                i = rnd.nextInt(4) + 1;
            }
            pcTrap.setPosition(new Coordinate(wall.x, wall.y + i).toPoint());
            return;
        }
        setArrowTrap(Trap, Projectile);
    }

    public Point getDestination() {
        return destination;
    }

    private void updateDirection() {
        if (direction == 1) {
            xSpeed = 1f;
        }
        if (direction == 2) {
            xSpeed = 1f;
        }
        if (direction == 3) {
            ySpeed = 1f;
        }
        if (direction == 4) {
            ySpeed = 1f;
        }
    }

    private void updateAnimation() {
        if (direction == 1) {
            arrowAnimation = AnimationBuilder.buildAnimation(pathToRunLeft);
        }
        if (direction == 2) {
            arrowAnimation = AnimationBuilder.buildAnimation(pathToRunRight);
        }
        if (direction == 3) {
            arrowAnimation = AnimationBuilder.buildAnimation(pathToRunDown);
        }
        if (direction == 4) {
            arrowAnimation = AnimationBuilder.buildAnimation(pathToRunUp);
        }
    }

    public float getxSpeed() {
        return xSpeed;
    }

    public float getySpeed() {
        return ySpeed;
    }

    public Animation getArrowAnimation() {
        return arrowAnimation;
    }

    public Point getPosition() {
        PositionComponent help =
                (PositionComponent) this.getComponent(PositionComponent.class).get();
        Point helper = help.getPosition();
        if (direction == 1) {
            return new Point(helper.toCoordinate().x - 1, helper.toCoordinate().y);
        }
        if (direction == 2) {
            return new Point(helper.toCoordinate().x + 1, helper.toCoordinate().y);
        }
        if (direction == 3) {
            return new Point(helper.toCoordinate().x, helper.toCoordinate().y - 1);
        }
        if (direction == 4) {
            return new Point(helper.toCoordinate().x, helper.toCoordinate().y + 1);
        }
        return help.getPosition();
    }
}
