package starter;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static logging.LoggerConfig.initBaseLogger;

import SaveManager.GameSave;
import SaveManager.SaveManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import configuration.Configuration;
import configuration.KeyboardConfig;
import controller.AbstractController;
import controller.SystemController;
import ecs.Quests.QuestGiver.DungonQuestGiver;
import ecs.Quests.QuestLog;
import ecs.components.*;
import ecs.components.xp.XPComponent;
import ecs.entities.*;
import ecs.entities.Items.HealthPotion;
import ecs.entities.Monsters.BlueChort;
import ecs.entities.Monsters.Chort;
import ecs.entities.Monsters.Imp;
import ecs.entities.Monsters.Mimic;
import ecs.entities.Traps.Arrow;
import ecs.entities.Traps.Spikes;
import ecs.items.ItemData;
import ecs.items.Tasche;
import ecs.items.WorldItemBuilder;
import ecs.systems.*;
import graphic.DungeonCamera;
import graphic.Painter;
import graphic.hud.PauseMenu;
import graphic.hud.gameOverScreen;
import java.io.IOException;
import graphic.hud.PuzzleMenu;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Logger;
import level.IOnLevelLoader;
import level.LevelAPI;
import level.elements.ILevel;
import level.elements.tile.Tile;
import level.generator.IGenerator;
import level.generator.postGeneration.WallGenerator;
import level.generator.randomwalk.RandomWalkGenerator;
import level.tools.LevelSize;
import tools.Constants;
import tools.Point;

/** The heart of the framework. From here all strings are pulled. */
public class Game extends ScreenAdapter implements IOnLevelLoader {

    private final LevelSize LEVELSIZE = LevelSize.SMALL;

    /**
     * The batch is necessary to draw ALL the stuff. Every object that uses draw need to know the
     * batch.
     */
    protected SpriteBatch batch;

    /** Contains all Controller of the Dungeon */
    protected List<AbstractController<?>> controller;

    public static DungeonCamera camera;
    /** Draws objects */
    protected Painter painter;

    protected LevelAPI levelAPI;
    /** Generates the level */
    protected IGenerator generator;

    private boolean doSetup = true;
    private static boolean paused = false;

    /** All entities that are currently active in the dungeon */
    private static final Set<Entity> entities = new HashSet<>();
    /** All entities to be removed from the dungeon in the next frame */
    private static final Set<Entity> entitiesToRemove = new HashSet<>();
    /** All entities to be added from the dungeon in the next frame */
    private static final Set<Entity> entitiesToAdd = new HashSet<>();

    /** List of all Systems in the ECS */
    public static SystemController systems;

    public static ILevel currentLevel;
    private static PauseMenu<Actor> pauseMenu;
    private static gameOverScreen gameOver;
    private static Entity hero;
    private Logger gameLogger;
    private static Entity monster;

    private static WorldItemBuilder itemBuilder = new WorldItemBuilder();

    private static Entity npcQuestion;
    private static PuzzleMenu rs;
    private static Boolean puzzle = false;

    private static Entity traps;
    private static Entity geist;
    private static Entity grabstein;
    private int countUpdatePerSecond = 0;
    private int geistInvisiblTime = 5;
    private DungonQuestGiver giver;

    private static int depth = 0;
    private Random rnd = new Random();
    private static GameSave save;
    private static String fileLoaction = "save/entities.txt";
    private boolean hasLoadat = false;

    public static void main(String[] args) {
        // start the game
        try {
            Configuration.loadAndGetConfiguration("dungeon_config.json", KeyboardConfig.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        DesktopLauncher.run(new Game());
    }

    /**
     * Main game loop. Redraws the dungeon and calls the own implementation (beginFrame, endFrame
     * and onLevelLoad).
     *
     * @param delta Time since last loop.
     */
    @Override
    public void render(float delta) {
        if (doSetup) setup();
        batch.setProjectionMatrix(camera.combined);
        frame();
        clearScreen();
        levelAPI.update();
        controller.forEach(AbstractController::update);
        camera.update();
        countUpdatePerSecond++;
        if (countUpdatePerSecond >= 30) {
            countUpdatePerSecond = 0;
            updatePerSecond();
        }
    }

    private void updatePerSecond() {
        if (getEntities().contains(geist)
                && ((Ghost) geist).isVisibil()

                && rnd.nextInt(0, 100) <= 10) {
            gameLogger.info("The Ghost is Disapperd but you can still feel his presents");
            ((Ghost) geist).SetInvisibil();
            geistInvisiblTime = rnd.nextInt(2, 7);

        }
        if (getEntities().contains(geist)
                && !((Ghost) geist).isVisibil()
                && geistInvisiblTime <= 0) {
            gameLogger.info("The Ghost has apperde once again");
            ((Ghost) geist).SetVisibil();
        }
        geistInvisiblTime -= 1;
    }

    /** Called once at the beginning of the game. */
    protected void setup() {
        doSetup = false;
        controller = new ArrayList<>();
        setupCameras();
        painter = new Painter(batch, camera);
        generator = new RandomWalkGenerator();
        levelAPI = new LevelAPI(batch, painter, generator, this);
        initBaseLogger();
        gameLogger = Logger.getLogger(this.getClass().getName());
        systems = new SystemController();
        controller.add(systems);
        pauseMenu = new PauseMenu<>();
        controller.add(pauseMenu);
        hero = new Hero();

        if (Files.exists(Paths.get(fileLoaction)) && depth == 0) {
            try {
                save = SaveManager.readObject(fileLoaction);
            } catch (IOException | ClassNotFoundException e) {
                gameLogger.info("Speicherstand konnte nicht geladen werden");
            } finally {
                if (save != null) {
                    depth = save.getDepth();
                    ((Hero) hero).LoadHero(save);
                    if (hasLevelgohst()) {
                        geist = new Ghost();
                        grabstein = new Tombstone((Ghost) geist);
                    }
                    gameLogger.info("Speicherstand wurde erfolgreich geladen");
                    hasLoadat = true;
                }
            }
        }

        rs = new PuzzleMenu<>();
        controller.add(rs);


        levelAPI = new LevelAPI(batch, painter, new WallGenerator(new RandomWalkGenerator()), this);
        levelAPI.loadLevel(LEVELSIZE);

        createSystems();
    }

    /** Called at the beginning of each frame. Before the controllers call <code>update</code>. */
    protected void frame() {
        setCameraFocus();
        manageEntitiesSets();
        getHero().ifPresent(this::loadNextLevelIfEntityIsOnEndTile);
        if (Gdx.input.isKeyJustPressed(Input.Keys.P)) togglePause();
        if (Gdx.input.isKeyJustPressed(Input.Keys.L))
            System.out.println(QuestLog.getInstance().printLog());
        if (Gdx.input.isKeyJustPressed(Input.Keys.I)) {
            InventoryComponent inventory =
                    ((InventoryComponent)
                            getHero().get().getComponent(InventoryComponent.class).get());
            XPComponent XPC = (XPComponent) getHero().get().getComponent(XPComponent.class).get();
            HealthComponent health =
                    ((HealthComponent) getHero().get().getComponent(HealthComponent.class).get());
            List<ItemData> inv = inventory.getItems();
            MagicPointsComponent MPC =
                    ((MagicPointsComponent)
                            getHero().get().getComponent(MagicPointsComponent.class).get());
            System.out.println(
                    "Du bist Aktuell Level "
                            + XPC.getCurrentLevel()
                            + " dir fehelen noch "
                            + XPC.getXPToNextLevel()
                            + " Erfahrung zum Levelaufstieg");
            System.out.println("Aktuelle HP : " + health.getCurrentHealthpoints());
            System.out.println(MPC.printMP());
            System.out.println("Das Inventar enthaelt");
            for (ItemData s : inv) {
                System.out.print(s.getItemName());
                if (s.getItemName().equals("Tasche")) {
                    System.out.print(" " + (((Tasche) s).getAmount() + 1));
                }
                System.out.println();
            }
            System.out.println(depth);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.H)) {
            InventoryComponent inventory =
                    ((InventoryComponent)
                            getHero().get().getComponent(InventoryComponent.class).get());
            List<ItemData> inv = inventory.getItems();

            if (inv.get(2).getDescription().equals("A Potion that restores 3 HP")) {
                inv.get(2).triggerUse(getHero().get());

            } else if (inv.get(3)
                    .getDescription()
                    .equals("Eine Tasche zum Transportieren von Heiltränken")) {
                Tasche bag =
                        ((Tasche)
                                        ((InventoryComponent)
                                                        getHero()
                                                                .get()
                                                                .getComponent(
                                                                        InventoryComponent.class)
                                                                .get())
                                                .getItems()
                                                .get(3))
                                .getTasche();
                if (!bag.isEmpty()) {
                    bag.getConsumable();
                }
            }
        }
    }

    @Override
    public void onLevelLoad() {
        currentLevel = levelAPI.getCurrentLevel();
        if (depth == 0 || !hasLoadat) {
            depth++;
        }
        entities.clear();
        getHero().ifPresent(this::placeOnLevelStart);
        spawnMonster();
        spawnNpcQuestion();

        // setTraps();
        /*if(random.nextInt(0,100)<=50) {

            gameLogger.info("a Haunted Spirit has been Locked in this layer free him ");
            geist = new Ghost();
            grabstein = new Tombstone(((Ghost) geist));
        }*/
        if (depth == 1) {
            giver = new DungonQuestGiver();
        }
        if (depth == 5) {
            setStart(new Chest());
        }
        if (depth == 10) {
            setStart(new Mimic());
        }
        if (depth % 15 == 0) {
            ((MagicPointsComponent)
                            Game.getHero().get().getComponent(MagicPointsComponent.class).get())
                    .resetMp();
        }
        itemBuilder.buildWorldItem(new HealthPotion());
        QuestLog.getInstance().checkAllQuests();
        save = new GameSave();
        try {
            SaveManager.writeObject(save, fileLoaction);
        } catch (IOException | ClassNotFoundException e) {
            gameLogger.info("es konnte nicht gespeichert werden");
        }
        hasLoadat = false;
    }

    private void manageEntitiesSets() {
        entities.removeAll(entitiesToRemove);
        entities.addAll(entitiesToAdd);
        for (Entity entity : entitiesToRemove) {
            gameLogger.info("Entity '" + entity.getClass().getSimpleName() + "' was deleted.");
        }
        for (Entity entity : entitiesToAdd) {
            gameLogger.info("Entity '" + entity.getClass().getSimpleName() + "' was added.");
        }
        entitiesToRemove.clear();
        entitiesToAdd.clear();
    }

    private void setCameraFocus() {
        if (getHero().isPresent()) {
            PositionComponent pc =
                    (PositionComponent)
                            getHero()
                                    .get()
                                    .getComponent(PositionComponent.class)
                                    .orElseThrow(
                                            () ->
                                                    new MissingComponentException(
                                                            "PositionComponent"));
            camera.setFocusPoint(pc.getPosition());

        } else camera.setFocusPoint(new Point(0, 0));
    }

    private void loadNextLevelIfEntityIsOnEndTile(Entity hero) {
        if (isOnEndTile(hero)) levelAPI.loadLevel(LEVELSIZE);
    }

    private boolean isOnEndTile(Entity entity) {
        PositionComponent pc =
                (PositionComponent)
                        entity.getComponent(PositionComponent.class)
                                .orElseThrow(
                                        () -> new MissingComponentException("PositionComponent"));
        Tile currentTile = currentLevel.getTileAt(pc.getPosition().toCoordinate());
        return currentTile.equals(currentLevel.getEndTile());
    }

    private void placeOnLevelStart(Entity hero) {
        entities.add(hero);
        PositionComponent pc =
                (PositionComponent)
                        hero.getComponent(PositionComponent.class)
                                .orElseThrow(
                                        () -> new MissingComponentException("PositionComponent"));
        pc.setPosition(currentLevel.getStartTile().getCoordinate().toPoint());
    }

    /** Toggle between pause and run */
    public static void togglePause() {
        paused = !paused;
        freeze();

        if (pauseMenu != null && !puzzle) {
            if (paused) pauseMenu.showMenu();
            else pauseMenu.hideMenu();
        }
    }

    public static void toggleRaetsel() {
        puzzle = !puzzle;
        freeze();
        if (rs != null) {
            if (puzzle) rs.showMenu();
            else rs.hideMenu();
        }
    }

    public static void freeze() {
        if (systems != null) {
            systems.forEach(ECS_System::toggleRun);
        }
    }

    /**
     * Given entity will be added to the game in the next frame
     *
     * @param entity will be added to the game next frame
     */
    public static void addEntity(Entity entity) {
        entitiesToAdd.add(entity);
    }

    /**
     * Given entity will be removed from the game in the next frame
     *
     * @param entity will be removed from the game next frame
     */
    public static void removeEntity(Entity entity) {
        entity.OnDelete();
        entitiesToRemove.add(entity);
    }

    /**
     * @return Set with all entities currently in game
     */
    public static Set<Entity> getEntities() {
        return entities;
    }

    /**
     * @return Set with all entities that will be added to the game next frame
     */
    public static Set<Entity> getEntitiesToAdd() {
        return entitiesToAdd;
    }

    /**
     * @return Set with all entities that will be removed from the game next frame
     */
    public static Set<Entity> getEntitiesToRemove() {
        return entitiesToRemove;
    }

    /**
     * @return the player character, can be null if not initialized
     */
    public static Optional<Entity> getHero() {
        return Optional.ofNullable(hero);
    }
    public static Optional<Entity> getnpcQuestion() {
        return Optional.ofNullable(npcQuestion);
    }

    /**
     * set the reference of the playable character careful: old hero will not be removed from the
     * game
     *
     * @param hero new reference of hero
     */
    public static void setHero(Entity hero) {
        Game.hero = hero;
    }

    public void setSpriteBatch(SpriteBatch batch) {
        this.batch = batch;
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
    }

    private void setupCameras() {
        camera = new DungeonCamera(null, Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
        camera.zoom = Constants.DEFAULT_ZOOM_FACTOR;

        // See also:
        // https://stackoverflow.com/questions/52011592/libgdx-set-ortho-camera
    }

    private void setStart(Entity monster) {
        entities.add(monster);
        PositionComponent pc =
                (PositionComponent)
                        monster.getComponent(PositionComponent.class)
                                .orElseThrow(
                                        () -> new MissingComponentException("PositionComponent"));
        if (monster.getClass() == Chort.class) {

        } else {
            pc.setPosition(currentLevel.getRandomFloorTile().getCoordinate().toPoint());
        }
    }

    private void spawnMonster() {
        int j = (((rnd.nextInt(3) + 1) * (int) (1 + 0.2 * depth)) + 1);
        int x;
        Monster[] mons = new Monster[j];
        for (int i = 0; i < j; i++) {
            x = /*rnd.nextInt(3)*/ 1;
            if (x == 0) {
                mons[i] = new Chort();
            } else if (x == 1) {
                mons[i] = new BlueChort();
            } else {
                mons[i] = new Imp();
            }
            setStart(mons[i]);
        }
    }
    private void spawnNpcQuestion() {
        if (depth == 1) {
            npcQuestion = new NpcPenguin();
        }

    }
    private void createSystems() {
        new VelocitySystem();
        new DrawSystem(painter);
        new PlayerSystem();
        new AISystem();
        new CollisionSystem();
        new HealthSystem();
        new XPSystem();
        new SkillSystem();
        new ProjectileSystem();
    }
    /** initalize Traps for the Dungeon This Methode is called onLevelLoad */
    private void setTraps() {
        for (int i = 0; i < 5; i++) {
            new Spikes();
        }
        new Spikes();
        new Arrow();
    }

    public static Boolean hasLevelgohst() {
        return entities.contains(geist);
    }

    /** Implementing Gameover Method */
    public static void GameOver() {
        if (Files.exists(Paths.get(fileLoaction))) {
            try {
                Files.delete(Paths.get(fileLoaction));
            } catch (IOException e) {
                System.out.println(e.getCause());
            }
            save = null;
        }
        // gameOver = new gameOverScreen();  // neu endeckter Fehler beim Game over
    }

    /** Implementing neustart Method Resets the level and other components */
    public void neustart() {
        depth = 0;
        hero = new Hero();
        ((InventoryComponent) Game.getHero().get().getComponent(InventoryComponent.class).get())
                .setupInventory();
        QuestLog.getInstance().restar();
        QuestLog.getInstance().SetPlayer((Hero) hero);
        levelAPI.loadLevel(LevelSize.SMALL);
    }


    public static WorldItemBuilder getWorldItemBuilder() {
        return itemBuilder;
    }
    /**
     * @return return the depth
     */
    public static int getCurrentLevel() {
        return depth;
    }
}
