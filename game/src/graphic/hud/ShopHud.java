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

public class ShopHud<T extends Actor> extends ScreenController<T>  {


    public String priceFromHero;
    public String moneyFromHero;
    private ScreenImage screenImage1;
    private ScreenImage screenImage2;
    private ScreenImage screenImage3;
    private ScreenText screenText1;
    private ScreenText screenText2;
    private ScreenText screenText3;
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

        /** creates a ScreenText,screenInput, screenButton´s for the player to interact with */
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

        screenText1.setFontScale(1);
        screenText1.setPosition(Constants.WINDOW_WIDTH / 0.33f, 0.33f, Align.center | Align.bottom);

        screenImage1=
            new ScreenImage("game/assets/items/swords/greatSword.png",new Point(0,0));
            screenButton1 =
                new ScreenButton(
                    "Kaufen",
                    new Point(0f, 0f),
                    new TextButtonListener() {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            if (isPressed() == true && !hasStarted) {
                                setupShop();
                            } else if (isPressed() == true) {
                                priceFromHero = screenInput.getText();
                                if (comparecost(moneyFromHero)) {
                                    hideShop();
                                    Game.freeze();
                                }
                            }
                        }
                    },
                    new TextButtonStyleBuilder(FontBuilder.DEFAULT_FONT)
                        .setFontColor(Color.WHITE)
                        .build());

            screenButton1.setPosition(
                Constants.WINDOW_WIDTH / 2 - screenButton1.getWidth(),
                10,
                Align.center | Align.bottom);
        screenNegButton1=
            new ScreenButton(
                "verhandeln",new Point(0f,0f),
                new TextButtonListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        negotiationmenu();
                        hideShop();
                    }
                },
        new TextButtonStyleBuilder(FontBuilder.DEFAULT_FONT)
            .setFontColor(Color.WHITE)
            .build());
        screenNegButton1.setPosition(
            Constants.WINDOW_WIDTH / 2 - screenButton1.getWidth(),
            10,
            Align.center | Align.bottom);



//Shop window Second Item
            screenText2 =
                new ScreenText(
                    "item",
                    new Point(0, 0),
                    3,
                    new LabelStyleBuilder(FontBuilder.DEFAULT_FONT)
                        .setFontcolor(Color.WHITE)
                        .build());
            screenImage2=
                new ScreenImage("game/assets/items/swords/greatSword.png",new Point(0,0));
            screenText2.setFontScale(1);
            screenText2.setPosition(Constants.WINDOW_WIDTH / 2, 50f, Align.center | Align.bottom);

        screenButton2 =
            new ScreenButton(
                "Kaufen",
                new Point(0f, 0f),
                new TextButtonListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        if (isPressed() == true && !hasStarted) {
                            setupShop();
                        } else if (isPressed() == true) {
                            priceFromHero = screenInput.getText();
                            if (comparecost(moneyFromHero)) {
                                hideShop();
                                Game.freeze();
                            }
                        }
                    }
                },
                new TextButtonStyleBuilder(FontBuilder.DEFAULT_FONT)
                    .setFontColor(Color.WHITE)
                    .build());

        screenButton2.setPosition(
            Constants.WINDOW_WIDTH / 2 - screenButton2.getWidth(),
            10,
            Align.center | Align.bottom);
            screenNegButton2=
                new ScreenButton(
                    "verhandeln",new Point(0f,0f),
                    new TextButtonListener(){
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            negotiationmenu();
                            hideShop();
                        }
                    },
            new TextButtonStyleBuilder(FontBuilder.DEFAULT_FONT)
                .setFontColor(Color.WHITE)
                .build());
            screenNegButton2.setPosition(
                Constants.WINDOW_WIDTH / 2 - screenNegButton2.getWidth(),
                10,
                Align.center | Align.bottom);

    //Shop window Third Item

    screenText3 =
        new ScreenText(
                "item",
                    new Point(0, 0),
                3,
                    new LabelStyleBuilder(FontBuilder.DEFAULT_FONT)
                    .setFontcolor(Color.WHITE)
                    .build());
        screenText3.setFontScale(1);
        screenText3.setPosition(Constants.WINDOW_WIDTH /9f, 50f, Align.center | Align.bottom);

            screenImage3=
                new ScreenImage("game/assets/items/swords/greatSword.png",new Point(0,0));screenButton3 =

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

            screenNegButton3=
                new ScreenButton(
                    "verhandeln",new Point(0f,0f),
                    new TextButtonListener(){
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            negotiationmenu();
                            hideShop();
                        }
                    },
            new TextButtonStyleBuilder(FontBuilder.DEFAULT_FONT)
                .setFontColor(Color.WHITE)
                .build());
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
                Constants.WINDOW_WIDTH / 2 + screenButton1.getWidth(),
                10,
                Align.center | Align.bottom);
            add((T)screenButton1);
            add((T)screenButton2);
            add((T)screenButton3);
            add((T)screenNegButton1);
            add((T)screenNegButton2);
            add((T)screenNegButton3);
            add((T)screenText1);
            add((T)screenText2);
            add((T)screenText3);
            add((T)screenImage1);
            add((T)screenImage2);
            add((T)screenImage3);
            add((T)screenButtonLeave);
            hideShop();
        }

    public void setupShop(){
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




    private Boolean comparecost(int cos) {
        return comparecost(2);
    }
}
