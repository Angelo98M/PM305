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
    private ScreenImage screenImage1;
    private ScreenImage screenImage2;
    private ScreenImage screenImage3;
    private ScreenText screenText1;
    private ScreenText screenText2;
    private ScreenText screenText2;
    private ScreenInput screenInput1;
    private ScreenInput screenInput2;
    private ScreenInput screenInput3;
    private ScreenButton screenButton1;
    private ScreenButton screenButton2;
    private ScreenButton screenButton3;
    private ScreenButton screenNegButton1;
    private ScreenButton screenNegButton2;
    private ScreenButton screenNegButton3;
    private ScreenButton screenButtonleave1;
    private Boolean hasStarted = false;

    public ShopHud() {
        this(new SpriteBatch());
    }

        /** creates a ScreenText,screenInput, screenButton´s for the player to interact with */
        public ShopHud(SpriteBatch batch) {
                super(batch);

        //Shop window one


        screenText1 =
            new ScreenText(
                "beantworte eine frage und bekomme einen Heiltrank",
                new Point(0, 0),
                3,
                new LabelStyleBuilder(FontBuilder.DEFAULT_FONT)
                    .setFontcolor(Color.WHITE)
                    .build());

        screenText1.setFontScale(1);
        screenText1.setPosition(Constants.WINDOW_WIDTH / 2, 50f, Align.center | Align.bottom);


        screenInput1 =
            new ScreenInput(
                "Drücke Start ",
                new Point(3f, 3f),
                new TextFieldStyleBuilder(FontBuilder.DEFAULT_FONT)
                    .setFontColor(Color.WHITE)
                    .build());

        screenInput1.setPosition(360f, 30f, Align.center | Align.bottom);

        screenNegButton1=
            new ScreenButton(
                "verhandeln",new Point(0f,0f),
                new TextButtonListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                    }
                };
        new TextButtonStyleBuilder(FontBuilder.DEFAULT_FONT)
            .setFontColor(Color.WHITE)
            .build());


        screenButton1 =
            new ScreenButton(
                "Start",
                new Point(0f, 0f),
                new TextButtonListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                    }
                };
        new TextButtonStyleBuilder(FontBuilder.DEFAULT_FONT)
            .setFontColor(Color.WHITE)
            .build());

        screenButton1.setPosition(
            Constants.WINDOW_WIDTH / 2 - screenButton1.getWidth(),
            10,
            Align.center | Align.bottom);
        screenButtonleave1 =
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
                };
        new TextButtonStyleBuilder(FontBuilder.DEFAULT_FONT)
            .setFontColor(Color.WHITE)
            .build());
        screenButtonleave1.setPosition(
            Constants.WINDOW_WIDTH / 2 + screenButton2.getWidth(),
            10,
            Align.center | Align.bottom);

        add((T) screenButton1);
        add((T) screenButtonleave1);
        add((T) screenText1);
        add((T) screenInput1);
        hideShop();



        screenText2 =
            new ScreenText(
                "beantworte eine frage und bekomme einen Heiltrank",
                new Point(0, 0),
                3,
                new LabelStyleBuilder(FontBuilder.DEFAULT_FONT)
                    .setFontcolor(Color.WHITE)
                    .build());

        screenText2.setFontScale(1);
        screenText2.setPosition(Constants.WINDOW_WIDTH / 2, 50f, Align.center | Align.bottom);

        screenInput2 =
            new ScreenInput(
                "Drücke Start ",
                new Point(3f, 3f),
                new TextFieldStyleBuilder(FontBuilder.DEFAULT_FONT)
                    .setFontColor(Color.WHITE)
                    .build());

        screenInput2.setPosition(360f, 30f, Align.center | Align.bottom);

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
                            priceFromHero = screenInput2.getText();
                            if (compareAnswer(FromHero)) {
                                getreward();
                                hideMenu();
                                Game.freeze();
                            }
                        }
                    }
                },
                new TextButtonStyleBuilder(FontBuilder.DEFAULT_FONT)
                    .setFontColor(Color.WHITE)
                    .build());

        screenButton2.setPosition(
            Constants.WINDOW_WIDTH / 2 - screenButton.getWidth(),
            10,
            Align.center | Align.bottom);
    }
    public void setupShop(){
        hasStarted = true;

    }
    public void showShop() {
        if (!) {
            this.forEach((Actor s) -> s.setVisible(true));
        }
    }
    public void hideShop() {
        this.forEach((Actor s) -> s.setVisible(false));
    }
}
