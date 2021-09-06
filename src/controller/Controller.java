package controller;

import initialization.Initialization;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import model.field.Cell;
import model.field.Field;
import model.field.Status;
import model.game.Game;
import model.game.GameType;
import model.player.Player;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class Controller {
    @FXML
    private AnchorPane gamePane = new AnchorPane();
    @FXML
    private AnchorPane startPane = new AnchorPane();
    @FXML
    private GridPane fieldPlayer = new GridPane();
    @FXML
    private GridPane fieldPlayer1 = new GridPane();
    @FXML
    private Text textPlayer = new Text();
    @FXML
    private Text textPlayer1 = new Text();

    //режим игры игрок против игрока
    public void playPvP(ActionEvent event) {
        Initialization init = new Initialization();

        //инициализация поля
        Field field = init.initField(fieldPlayer.getChildren());
        Field field1 = init.initField(fieldPlayer1.getChildren());

        //растановка кораблей
        init.automaticallyInitShips(field, false);
        init.automaticallyInitShips(field1, false);

        //инициализация игроков
        String name = "Игрок";
        textPlayer.setText(name);
        Player player = init.initPlayer(field, name);
        name = "Игрок1";
        textPlayer1.setText(name);
        Player player1 = init.initPlayer(field1, name);

        //открытие игрового окна
        openGameWindow();

        Game game = Game.getInstance(player, player1);
        game.setGameType(GameType.PvP);

        game.startGameLooopPvP(fieldPlayer, fieldPlayer1);
    }

    //режим игрок портив бота
    public void playWithBot(ActionEvent event) {
        Initialization init = new Initialization();

        //инициализация поля
        Field field = init.initField(fieldPlayer.getChildren());
        Field field1 = init.initField(fieldPlayer1.getChildren());

        //растановка кораблей
        init.automaticallyInitShips(field, true);
        init.automaticallyInitShips(field1, true);

        //инициализация игроков
        String name = "Игрок";
        textPlayer.setText(name);
        Player player = init.initPlayer(field, name);
        name = "Бот";
        textPlayer1.setText(name);
        Player bot = init.initBot(field1, "Bot");

        openGameWindow();

        Game game = Game.getInstance(player, bot);
        game.setGameType(GameType.WITHBOT);

        game.startGameLooopPvB(fieldPlayer, fieldPlayer1);
    }

    public void clickFieldPlayer(ActionEvent event) {
        Game game = Game.getInstance();
        Button button = (Button) event.getTarget();
        HashMap field = game.getPlayer().getClassField().getCells();
        Iterator<Map.Entry<String, Cell>> entries = field.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, Cell> entry = entries.next();
            if (entry.getValue().getButton() == button) {
                //если найдена нужная Cell
                Status status = game.makeStep(entry.getValue(), game.getPlayer());
                if (status == Status.MISS) {
                    synchronized (game.getKey()) {
                        game.getKey().notifyAll();
                    }
                } else {
                    boolean result = game.checkWin(game.getPlayer());
                    if (result) {
                        game.blockFields(fieldPlayer, fieldPlayer1);
                        game.getGameLoopThread().interrupt();
                    }
                }
                break;
            }
        }

    }

    public void clickFieldPlayer1(ActionEvent event) {
        Game game = Game.getInstance();
        Button button = (Button) event.getTarget();
        HashMap field = Game.getInstance().getPlayer1().getClassField().getCells();
        Iterator<Map.Entry<String, Cell>> entries = field.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, Cell> entry = entries.next();
            if (entry.getValue().getButton() == button) {
                //если найдена нужная Cell
                Status status = game.makeStep(entry.getValue(), game.getPlayer1());
                if (status == Status.MISS) {
                    synchronized (game.getKey()) {
                        game.getKey().notifyAll();
                    }
                } else {
                    boolean result = game.checkWin(game.getPlayer1());
                    if (result) {
                        game.blockFields(fieldPlayer, fieldPlayer1);
                        game.getGameLoopThread().interrupt();
                    }
                }
                break;
            }
        }
    }

    public void menuButton(ActionEvent event) {
        Game game = Game.getInstance();
        game.getGameLoopThread().interrupt();
        Game.removeInstance();
        openMenu();
    }

    @FXML
    public void openGameWindow() {
        startPane.setVisible(false);
        gamePane.setVisible(true);
    }

    @FXML
    public void openMenu() {
        startPane.setVisible(true);
        gamePane.setVisible(false);
    }
}

