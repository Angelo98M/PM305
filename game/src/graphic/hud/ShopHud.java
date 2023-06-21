package graphic.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.Align;
import controller.ScreenController;
import starter.Game;
import tools.Constants;
import tools.Point;

public class ShopHud<T extends Actor> extends ScreenController<T> {


    public String priceFromHero;
    public String moneyFromHero;
    private ScreenImage screenImage1;
    private ScreenImage screenImage2;
    private ScreenImage screenImage3;
    private ScreenText screenText1;
    private ScreenText screenText2;
    private ScreenText screenText3;
    private ScreenText itemPreis1;
    private ScreenText itemPreis2;
    private ScreenText itemPreis3;
    private ScreenInput screenInput;
    private ScreenButton screenButton1;
    private ScreenButton screenButton2;
    private ScreenButton screenButton3;
    private ScreenButton screenNegButton1;
    private ScreenButton screenNegButton2;
    private ScreenButton screenNegButton3;
    private ScreenButton screenButtonLeave;
    private Boolean hasStarted = false;

    public ShopHud() {
        this(new SpriteBatch());
    }

    /**
     * creates a ScreenText,screenInput, screenButton´s for the player to interact with
     */
    public ShopHud(SpriteBatch batch) {
        super(batch);

        //Shop window first Item
        screenText1 =
            new ScreenText(
                "item",
                new Point(0, 0),
                3,
                new LabelStyleBuilder(FontBuilder.DEFAULT_FONT)
                    .setFontcolor(Color.WHITE)
                    .build());

        screenText1.setFontScale(1.5f);
        screenText1.setPosition(Constants.WINDOW_WIDTH/4.2f, Constants.WINDOW_HEIGHT/1.3f, Align.center | Align.bottom);

        screenImage1 =
            new ScreenImage("game/assets/items/swords/greatSword.png", new Point(0, 0));
        screenImage1.setScale(4);
        screenImage1.setPosition(Constants.WINDOW_WIDTH / 4.5f, Constants.WINDOW_HEIGHT/2f, Align.center | Align.bottom);

        itemPreis1=
            new ScreenText(
                "Preis",
                new Point(0f,0f),
                3,
            new LabelStyleBuilder(FontBuilder.DEFAULT_FONT)
                .setFontcolor(Color.WHITE)
                .build());
        itemPreis1.setScale(1.5f);
        itemPreis1.setPosition(Constants.WINDOW_WIDTH/4.2f, Constants.WINDOW_HEIGHT/2.6f, Align.center | Align.bottom);
        screenButton1 =
            new ScreenButton(
                "Kaufen",
                new Point(0f, 0f),
                new TextButtonListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        if (isPressed() == true && !hasStarted) {
                            setupShop();
                        }
                    }
                },
                new TextButtonStyleBuilder(FontBuilder.DEFAULT_FONT)
                    .setFontColor(Color.WHITE)
                    .build());

        screenButton1.setScale(1.5f);
        screenButton1.setPosition(Constants.WINDOW_WIDTH / 4.2f, Constants.WINDOW_HEIGHT/3.1f, Align.center | Align.bottom);

        screenNegButton1 =
            new ScreenButton(
                "verhandeln", new Point(0f, 0f),
                new TextButtonListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        negotiationmenu();
                        hideShop();
                    }
                },
                new TextButtonStyleBuilder(FontBuilder.DEFAULT_FONT)
                    .setFontColor(Color.WHITE)
                    .build());
        screenNegButton1.setScale(1.5f);
        screenNegButton1.setPosition(Constants.WINDOW_WIDTH / 4.2f, Constants.WINDOW_HEIGHT/3.8f, Align.center | Align.bottom);



//Shop window Second Item
        screenText2 =
            new ScreenText(
                "item",
                new Point(0, 0),
                3,
                new LabelStyleBuilder(FontBuilder.DEFAULT_FONT)
                    .setFontcolor(Color.WHITE)
                    .build());
        screenText2.setFontScale(1.5f);
        screenText2.setPosition(Constants.WINDOW_WIDTH / 2,Constants.WINDOW_HEIGHT/1.3f, Align.center | Align.bottom);

        screenImage2 =
            new ScreenImage("game/assets/items/swords/greatSword.png", new Point(0, 0));
        screenImage2.setScale(4f);
        screenImage2.setPosition(Constants.WINDOW_WIDTH / 2.1f,Constants.WINDOW_HEIGHT/2f, Align.center | Align.bottom);

        itemPreis2=
            new ScreenText(
                "Preis",
                new Point(0f,0f),
                3,
                new LabelStyleBuilder(FontBuilder.DEFAULT_FONT)
                    .setFontcolor(Color.WHITE)
                    .build());
                 itemPreis2.setScale(1.5f);
                 itemPreis2.setPosition(Constants.WINDOW_WIDTH / 2f, Constants.WINDOW_HEIGHT/2.6f, Align.center | Align.bottom); screenButton2 =
            new ScreenButton(
                "Kaufen",
                new Point(0f, 0f),
                new TextButtonListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        if (isPressed() == true && !hasStarted) {
                            setupShop();

                        }
                    }
                },
                new TextButtonStyleBuilder(FontBuilder.DEFAULT_FONT)
                    .setFontColor(Color.WHITE)
                    .build());
        screenButton2.setScale(1.5f);
        screenButton2.setPosition(Constants.WINDOW_WIDTH / 2f, Constants.WINDOW_HEIGHT/3.1f, Align.center | Align.bottom);

        screenNegButton2 =
            new ScreenButton(
                "verhandeln", new Point(0f, 0f),
                new TextButtonListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        negotiationmenu();
                        hideShop();
                    }
                },
                new TextButtonStyleBuilder(FontBuilder.DEFAULT_FONT)
                    .setFontColor(Color.WHITE)
                    .build());
        screenButton2.setScale(1.5f);
        screenButton2.setPosition(Constants.WINDOW_WIDTH / 2f, Constants.WINDOW_HEIGHT/3.1f, Align.center | Align.bottom);
        screenNegButton2.setScale(1.5f);
        screenNegButton2.setPosition(Constants.WINDOW_WIDTH / 2f, Constants.WINDOW_HEIGHT/3.8f, Align.center | Align.bottom);
        //Shop window Third Item

        screenText3 =
            new ScreenText(
                "item",
                new Point(0, 0),
                3,
                new LabelStyleBuilder(FontBuilder.DEFAULT_FONT)
                    .setFontcolor(Color.WHITE)
                    .build());
        screenText3.setFontScale(1.5f);
        screenText3.setPosition(Constants.WINDOW_WIDTH / 1.3f, Constants.WINDOW_HEIGHT/1.3f, Align.center | Align.bottom);

        screenImage3 =
            new ScreenImage("game/assets/items/swords/greatSword.png", new Point(0, 0));
        screenImage3.setScale(4);
        screenImage3.setPosition(Constants.WINDOW_WIDTH / 1.37f, Constants.WINDOW_HEIGHT/2f, Align.center | Align.bottom);

        itemPreis3=
            new ScreenText(
                "Preis",
                new Point(0f,0f),
                3,
                new LabelStyleBuilder(FontBuilder.DEFAULT_FONT)
                    .setFontcolor(Color.WHITE)
                    .build());
        itemPreis3.setScale(1.5f);
        itemPreis3.setPosition(Constants.WINDOW_WIDTH/1.3f, Constants.WINDOW_HEIGHT/2.6f, Align.center | Align.bottom);
        screenButton3 =

            new ScreenButton(
                "Kaufen",
                new Point(0f, 0f),
                new TextButtonListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        if (isPressed() == true && !hasStarted) {
                            setupShop();
                        }
                    }
                },
                new TextButtonStyleBuilder(FontBuilder.DEFAULT_FONT)
                    .setFontColor(Color.WHITE)
                    .build());
        screenButton3.setScale(1.5f);
        screenButton3.setPosition(Constants.WINDOW_WIDTH / 1.3f, Constants.WINDOW_HEIGHT/3.1f, Align.center | Align.bottom);

        screenNegButton3 =
            new ScreenButton(
                "verhandeln", new Point(0f, 0f),
                new TextButtonListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        negotiationmenu();
                        hideShop();
                    }
                },
                new TextButtonStyleBuilder(FontBuilder.DEFAULT_FONT)
                    .setFontColor(Color.WHITE)
                    .build());
        screenNegButton3.setScale(1.5f);
        screenNegButton3.setPosition(Constants.WINDOW_WIDTH / 1.3f, Constants.WINDOW_HEIGHT/ 3.8f,Align.center | Align.bottom);
        screenButtonLeave =
            new ScreenButton(
                "Verlassen",
                new Point(5f, 5f),
                new TextButtonListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        if (isPressed()) {
                            Game.toggleShop();
                        }
                    }
                },
                new TextButtonStyleBuilder(FontBuilder.DEFAULT_FONT)
                    .setFontColor(Color.WHITE)
                    .build());
        screenButtonLeave.setPosition(
            Constants.WINDOW_WIDTH / 2,
            10,
            Align.center | Align.bottom);

        add((T) screenButton1);
        add((T) screenButton2);
        add((T) screenButton3);
        add((T) screenNegButton1);
        add((T) screenNegButton2);
        add((T) screenNegButton3);
        add((T) screenText1);
        add((T)itemPreis1);
        add((T)itemPreis2);
        add((T)itemPreis3);
        add((T) screenText2);
        add((T) screenText3);
        add((T) screenImage1);
        add((T) screenImage2);
        add((T) screenImage3);
        add((T) screenButtonLeave);
        hideShop();
    }

    public void setupShop() {
        hasStarted = true;

    }

    public void showShop() {
        this.forEach((Actor s) -> s.setVisible(true));
    }

    public void hideShop() {
        this.forEach((Actor s) -> s.setVisible(false));
    }

    public void negotiationmenu() {
        screenInput =
            new ScreenInput(
                "hier Preis eingeben ",
                new Point(3f, 3f),
                new TextFieldStyleBuilder(FontBuilder.DEFAULT_FONT)
                    .setFontColor(Color.WHITE)
                    .build());

        screenInput.setPosition(360f, 30f, Align.center | Align.bottom);
    }
}
