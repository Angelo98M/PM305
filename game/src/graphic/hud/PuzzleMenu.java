package graphic.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.Align;
import controller.ScreenController;
import ecs.components.InventoryComponent;
import ecs.entities.Items.HealthPotion;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import starter.Game;
import tools.Constants;
import tools.Point;

/** Class for the Puzzle menu which gives a */
public class PuzzleMenu<T extends Actor> extends ScreenController<T> {
    public String answerFromHero;
    private ScreenText screenText;
    private ScreenInput screenInput;
    private ScreenButton screenButton;
    private ScreenButton screenButtonleave;
    private String[] questions;
    private String[] answers;
    private String[] regex;
    private String currendAnswer;
    private String currendregex;
    private Boolean hasStarted = false;

    public PuzzleMenu() {
        this(new SpriteBatch());
    }
    /**
     * Creates a Screencontroller with a ScalingViewport which stretches the ScreenElements on
     * resize
     *
     * @param batch the batch which should be used to draw with
     */
    public PuzzleMenu(SpriteBatch batch) {
        super(batch);
        setupQuestions();
        setupAnswers();
        setupRegex();
        /** creates a ScreenText, screenInput, ScreenButton´s for the player to interact with */
        screenText =
                new ScreenText(
                        "beantworte eine frage und bekomme eine Belohnung",
                        new Point(0, 0),
                        3,
                        new LabelStyleBuilder(FontBuilder.DEFAULT_FONT)
                                .setFontcolor(Color.WHITE)
                                .build());

        screenText.setFontScale(1);
        screenText.setPosition(Constants.WINDOW_WIDTH/2, 50f, Align.center | Align.bottom);

        screenInput =
                new ScreenInput(
                        "Drücke Start ",
                        new Point(3f, 3f),
                        new TextFieldStyleBuilder(FontBuilder.DEFAULT_FONT)
                                .setFontColor(Color.WHITE)
                                .build());

        screenInput.setPosition(360f, 30f, Align.center | Align.bottom);

        screenButton =
                new ScreenButton(
                        "Start",
                        new Point(0f, 0f),
                        new TextButtonListener() {
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                if (isPressed() == true && !hasStarted) {
                                    setupGame();
                                } else if (isPressed() == true) {
                                    answerFromHero = screenInput.getText();
                                    if (compareAnswer(answerFromHero)) {
                                        getreward();
                                        hideMenu();
                                        Game.freeze();
                                    }
                                }
                            }
                        });
        screenButton.setPosition(
                Constants.WINDOW_WIDTH / 2 - screenButton.getWidth(),
                10,
                Align.center | Align.bottom);
        screenButtonleave =
                new ScreenButton(
                        "Verlassen",
                        new Point(5f, 5f),
                        new TextButtonListener() {
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                if (isPressed()) {
                                    Game.toggleRaetsel();
                                }
                            }
                        });
        screenButtonleave.setPosition(
                Constants.WINDOW_WIDTH / 2 + screenButton.getWidth(),
                10,
                Align.center | Align.bottom);

        add((T) screenButton);
        add((T) screenButtonleave);
        add((T) screenText);
        add((T) screenInput);
        hideMenu();
    }
    /** set a new Random for a Random Question */
    private void setupGame() {
        Random random = new Random();
        int randomindex = random.nextInt(0, 2);
        screenText.setText(questions[randomindex]);
        currendAnswer = answers[randomindex];
        currendregex = regex[randomindex];
        screenInput.setText("Eingabe hier");
        hasStarted = true;
    }
    /** Gives the player a Reward */
    private void getreward() {
        Game.getHero()
                .get()
                .getComponent(InventoryComponent.class)
                .ifPresent(
                        (x) -> {
                            ((InventoryComponent) x).addItem((new HealthPotion()));
                        });
    }
    /** if the player comes to the next NpcPenguin the question will be reset */
    public void reset() {
        screenText.setText("beantworte eine frage und bekomme eine Belohnung");
        currendAnswer = "";
        currendregex = "none";
        screenInput.setText("Drücke Start");
        hasStarted = false;
    }

    public void showMenu() {
        if (!hasStarted) {
            this.forEach((Actor s) -> s.setVisible(true));
        }
    }

    /** hides the Menu */
    public void hideMenu() {
        this.forEach((Actor s) -> s.setVisible(false));
    }
    /** implements the Questions which will be asked */
    private void setupQuestions() {
        questions = new String[3];
        questions[0] = "wie viele Beine hat eine Spinne?";
        questions[1] = "Bei welcher Ampelfarbe darf man fahren?";
        questions[2] = "wie viele Spieler sind in einer Fussballmanschaft?";
    }
    /** implements the Answers for the Questions */
    private void setupAnswers() {
        answers = new String[3];
        answers[0] = "Acht";
        answers[1] = "Grün";
        answers[2] = "Elf";
    }
    /** the regex for the game */
    private void setupRegex() {
        regex = new String[3];
        regex[0] = "[acht\\\\ACHT\\\\8]+";
        regex[1] = "[grün\\\\GRÜN\\\\gruen\\\\GRUEN]+";
        regex[2] = "[ELF\\\\elf\\\\11]+";
    }
    /**
     * Get the component
     *
     * @param answer of the question
     */
    private Boolean compareAnswer(String answer) {
        Pattern p = Pattern.compile(currendregex);
        Matcher m = p.matcher(answer);
        return m.matches();
    }
}
