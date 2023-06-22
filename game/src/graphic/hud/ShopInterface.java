package graphic.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Logger;
import controller.ScreenController;
import ecs.components.InventoryComponent;
import ecs.entities.Hero;
import ecs.entities.Items.HealthPotion;
import ecs.items.ItemData;
import ecs.items.Tasche;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import starter.Game;
import tools.Constants;
import tools.Point;

public class ShopInterface<T extends Actor> extends ScreenController<T> {

    private ItemData[] items;
    private String[] regex;
    private int[] amount;
    private ScreenInput input;
    private ScreenText itemLeft;
    private ScreenText itemCenter;
    private ScreenText itemRigth;
    private ScreenText itemLeftPrice;
    private ScreenText itemCenterPrice;
    private ScreenText itemRigthPrice;
    private ScreenButton leave;
    private ScreenButton buy;
    private ScreenButton sell;
    private ScreenButton negotiate;
    private ScreenButton accept;
    private int indexofItem = 0;
    private int shopState = 0; // 1 for buy 2 for sell 3 for negeotiation
    private float faktor;
    private Logger shoplogger;

    public ShopInterface() {
        this(new SpriteBatch());
    }
    /**
     * Creates a Screencontroller with a ScalingViewport which stretches the ScreenElements on
     * resize
     *
     * @param batch the batch which should be used to draw with
     */
    public ShopInterface(SpriteBatch batch) {
        super(batch);
        shoplogger = new Logger("Shoplogger");
        setup();
    }

    private void setup() {
        itemLeft =
                new ScreenText(
                        "test",
                        new Point(0, 0),
                        3,
                        new LabelStyleBuilder(FontBuilder.DEFAULT_FONT)
                                .setFontcolor(Color.WHITE)
                                .build());
        itemLeft.setFontScale(1.0f);
        itemLeft.setPosition(
                Constants.WINDOW_WIDTH / 4.5f,
                Constants.WINDOW_HEIGHT / 2f,
                Align.center | Align.bottom);

        itemCenter =
                new ScreenText(
                        "test2",
                        new Point(0, 0),
                        3,
                        new LabelStyleBuilder(FontBuilder.DEFAULT_FONT)
                                .setFontcolor(Color.WHITE)
                                .build());
        itemCenter.setFontScale(1.0f);
        itemCenter.setPosition(
                Constants.WINDOW_WIDTH / 2.1f,
                Constants.WINDOW_HEIGHT / 2f,
                Align.center | Align.bottom);

        itemRigth =
                new ScreenText(
                        "test3",
                        new Point(0, 0),
                        3,
                        new LabelStyleBuilder(FontBuilder.DEFAULT_FONT)
                                .setFontcolor(Color.WHITE)
                                .build());
        itemRigth.setFontScale(1.0f);
        itemRigth.setPosition(
                Constants.WINDOW_WIDTH / 1.37f,
                Constants.WINDOW_HEIGHT / 2f,
                Align.center | Align.bottom);

        leave =
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
        leave.setPosition(Constants.WINDOW_WIDTH / 2, 10, Align.center | Align.bottom);

        buy =
                new ScreenButton(
                        "Kaufen",
                        new Point(0, 0),
                        new TextButtonListener() {
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                shopState = 1;
                                showBuyHud();
                            }
                        },
                        new TextButtonStyleBuilder(FontBuilder.DEFAULT_FONT)
                                .setFontColor(Color.WHITE)
                                .build());
        buy.setPosition(Constants.WINDOW_WIDTH / 4, 10, Align.center | Align.bottom);

        sell =
                new ScreenButton(
                        "Verkaufen",
                        new Point(0, 0),
                        new TextButtonListener() {
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                shopState = 2;

                                sellhud();
                            }
                        },
                        new TextButtonStyleBuilder(FontBuilder.DEFAULT_FONT)
                                .setFontColor(Color.WHITE)
                                .build());
        sell.setPosition(Constants.WINDOW_WIDTH * 3 / 4, 10, Align.center | Align.bottom);

        input =
                new ScreenInput(
                        "bitte hier eingabe tätigen",
                        new Point(0, 0),
                        new TextFieldStyleBuilder(FontBuilder.DEFAULT_FONT)
                                .setFontColor(Color.WHITE)
                                .build());
        input.setPosition(Constants.WINDOW_WIDTH / 2, 60, Align.center | Align.bottom);

        negotiate =
                new ScreenButton(
                        "Verhandeln",
                        new Point(0f, 0f),
                        new TextButtonListener() {
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                if (regexmatch(input.getText())) {
                                    itemCenter.setText(items[indexofItem].getItemName());
                                    itemCenterPrice.setText(items[indexofItem].getItemPrice());
                                    shopState = 3;
                                    negotiatetionHud();
                                }
                            }
                        },
                        new TextButtonStyleBuilder(FontBuilder.DEFAULT_FONT)
                                .setFontColor(Color.WHITE)
                                .build());
        negotiate.setPosition(Constants.WINDOW_WIDTH * 3 / 4, 10, Align.center | Align.bottom);

        accept =
                new ScreenButton(
                        "Akzeptieren",
                        new Point(0, 0),
                        new TextButtonListener() {
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                acceptFunction();
                            }
                        },
                        new TextButtonStyleBuilder(FontBuilder.DEFAULT_FONT)
                                .setFontColor(Color.WHITE)
                                .build());
        accept.setPosition(Constants.WINDOW_WIDTH / 4, 10, Align.center | Align.bottom);

        itemLeftPrice =
                new ScreenText(
                        "test",
                        new Point(0, 0),
                        3,
                        new LabelStyleBuilder(FontBuilder.DEFAULT_FONT)
                                .setFontcolor(Color.WHITE)
                                .build());
        itemLeftPrice.setFontScale(1.5f);
        itemLeftPrice.setPosition(
                Constants.WINDOW_WIDTH / 4.5f,
                Constants.WINDOW_HEIGHT / 2f - 30,
                Align.center | Align.bottom);

        itemCenterPrice =
                new ScreenText(
                        "test2",
                        new Point(0, 0),
                        3,
                        new LabelStyleBuilder(FontBuilder.DEFAULT_FONT)
                                .setFontcolor(Color.WHITE)
                                .build());
        itemCenterPrice.setFontScale(1.5f);
        itemCenterPrice.setPosition(
                Constants.WINDOW_WIDTH / 2.1f,
                Constants.WINDOW_HEIGHT / 2f - 30,
                Align.center | Align.bottom);

        itemRigthPrice =
                new ScreenText(
                        "test3",
                        new Point(0, 0),
                        3,
                        new LabelStyleBuilder(FontBuilder.DEFAULT_FONT)
                                .setFontcolor(Color.WHITE)
                                .build());
        itemRigthPrice.setFontScale(1.5f);
        itemRigthPrice.setPosition(
                Constants.WINDOW_WIDTH / 1.37f,
                Constants.WINDOW_HEIGHT / 2f - 30,
                Align.center | Align.bottom);

        add((T) itemLeft);
        add((T) itemCenter);
        add((T) itemRigth);
        add((T) itemLeftPrice);
        add((T) itemCenterPrice);
        add((T) itemRigthPrice);
        add((T) buy);
        add((T) sell);
        add((T) input);
        add((T) negotiate);
        add((T) leave);
        add((T) accept);
        hideShop();
    }

    public void hideShop() {
        this.forEach((Actor s) -> s.setVisible(false));
    }

    public void showBuyHud() {
        hideShop();
        this.accept.setVisible(true);
        this.leave.setVisible(true);
        this.negotiate.setVisible(true);
        this.itemCenter.setVisible(true);
        this.itemLeft.setVisible(true);
        this.itemRigth.setVisible(true);
        this.itemCenterPrice.setVisible(true);
        this.itemLeftPrice.setVisible(true);
        this.itemRigthPrice.setVisible(true);
        this.input.setVisible(true);
    }

    public void negotiatetionHud() {
        hideShop();
        this.itemCenter.setVisible(true);
        this.itemCenterPrice.setVisible(true);
        this.accept.setVisible(true);
        this.leave.setVisible(true);
        this.input.setVisible(true);
    }

    public void sellhud() {
        hideShop();
        this.itemCenter.setVisible(true);
        this.itemCenterPrice.setVisible(true);
        this.input.setVisible(true);
        this.leave.setVisible(true);
        this.accept.setVisible(true);
        itemCenter.setText("heiltranke");
        itemCenterPrice.setText("5");
    }

    public void showShop() {
        this.sell.setVisible(true);
        this.leave.setVisible(true);
        this.buy.setVisible(true);
    }

    private void pay(int price) {
        Hero customer = (Hero) Game.getHero().get();
        customer.pay(price);
    }

    private boolean canPay(int price) {
        if (((Hero) Game.getHero().get()).getGold() - price >= 0) {
            return true;
        } else {
            return false;
        }
    }

    public void getItem(ItemData item) {
        if (canPay(item.getItemPrice())) {
            pay(item.getItemPrice());
            ((InventoryComponent) Game.getHero().get().getComponent(InventoryComponent.class).get())
                    .addItem(item);
        }
    }

    public void setupNewShop(ItemData[] items, String[] regex, float faktor, int[] amount) {
        this.amount = amount;
        this.items = items;
        this.regex = regex;
        this.faktor = faktor;
        itemLeft.setText(items[0].getItemName() + " x" + amount[0]);
        itemCenter.setText(items[1].getItemName() + " x" + amount[1]);
        itemRigth.setText(items[2].getItemName() + " x" + amount[2]);
        itemLeftPrice.setText(items[0].getItemPrice());
        itemCenterPrice.setText(items[1].getItemPrice());
        itemRigthPrice.setText(items[2].getItemPrice());
    }
    private void updatehud()
    {
        itemLeft.setText(items[0].getItemName() + " x" + amount[0]);
        itemCenter.setText(items[1].getItemName() + " x" + amount[1]);
        itemRigth.setText(items[2].getItemName() + " x" + amount[2]);
        itemLeftPrice.setText(items[0].getItemPrice());
        itemCenterPrice.setText(items[1].getItemPrice());
        itemRigthPrice.setText(items[2].getItemPrice());
    }

    private boolean regexmatch(String input) {
        Pattern pattern;
        Matcher matcher;

        for (int i = 0; i < regex.length; i++) {
            pattern = Pattern.compile(regex[i]);
            matcher = pattern.matcher(input);
            if (matcher.matches()) {
                indexofItem = i;
                return true;
            }
        }
        return false;
    }

    private void acceptFunction() {
        switch (shopState) {
            case 1:
                if (regexmatch(input.getText())) {
                    if (amount[indexofItem] > 0) {

                        getItem(items[indexofItem]);
                        amount[indexofItem]-=1;
                        updatehud();
                        break;
                    } else {
                        shoplogger.error("dieses Item ist leider nicht mehr vorhanden");
                        break;
                    }
                }
                break;
            case 2:
                int inputvalue = -99999;
                try {
                    inputvalue = Integer.parseInt(input.getText());
                } catch (NumberFormatException | NullPointerException e) {
                    shoplogger.debug("eingeabe konnte nicht gewertet werden");
                }

                if (inputvalue > 0 && inputvalue <= amountOfHealingpotions()) {
                    InventoryComponent inv =
                            (InventoryComponent)
                                    Game.getHero()
                                            .get()
                                            .getComponent(InventoryComponent.class)
                                            .get();

                    ((Hero) Game.getHero().get())
                            .addGold(
                                    ((HealthPotion)
                                                            ((Tasche) inv.getItems().get(3))
                                                                    .getConsumable())
                                                    .getItemPrice()
                                            / 2
                                            * inputvalue);
                    for (int i = 0; i < inputvalue; i++) {
                        if (((Tasche) inv.getItems().get(3)).getAmount() > 0) {
                            ((Tasche) inv.getItems().get(3)).removeItem();
                        } else {
                            inv.removeItem(inv.getItems().get(2));
                        }
                    }
                }
                break;
            case 3:
                int tempPrice =
                        (int)
                                (items[indexofItem].getItemPrice()
                                        - items[indexofItem].getItemPrice() * faktor);
                int inputprice = -99999;
                try {
                    inputprice = Integer.parseInt(input.getText());
                } catch (NumberFormatException | NullPointerException e) {
                    shoplogger.error("eingeabe konnte nicht gewertet werden");
                }
                if (inputprice >= tempPrice) {

                    if (amount[indexofItem] > 0) {
                        getItem(items[indexofItem]);
                        amount[indexofItem]-=1;
                        updatehud();
                        break;
                    } else {
                        shoplogger.debug("dieses Item ist leider nicht mehr vorhanden");
                        break;
                    }
                }
                break;
            default:
                break;

        }
    }

    private int amountOfHealingpotions() {
        int amount = 0;
        InventoryComponent inv =
                (InventoryComponent)
                        Game.getHero().get().getComponent(InventoryComponent.class).get();
        if (inv.getItems().get(2) != null) {
            amount++;
        }
        amount += ((Tasche) inv.getItems().get(3)).getAmount();
        return amount;
    }
}
