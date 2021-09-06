package model.game;

import controller.DecorationController;
import javafx.scene.layout.GridPane;
import model.field.Cell;
import model.field.FieldManager;
import model.field.Status;
import model.player.Player;

public class Game {
    private GameType gameType;
    private static Game game;
    private Player player;
    private Player player1;
    private Thread gameLoopThread;
    private final Object key = new Object();

    public Thread getGameLoopThread() {
        return gameLoopThread;
    }

    public void setGameLoopThread(Thread gameLoopThread) {
        this.gameLoopThread = gameLoopThread;
    }

    public Object getKey() {
        return key;
    }

    private Game(Player player, Player player1) {
        this.player = player;
        this.player1 = player1;
    }

    //Singleton
    public static Game getInstance(Player player, Player player1) {
        if (game == null) game = new Game(player, player1);
        return game;
    }

    //для предотвращения багов при перезапуске игры
    public static void removeInstance() {
        game = null;
    }

    public static Game getInstance() {
        return game;
    }

    public Player getPlayer() {
        return player;
    }

    public Player getPlayer1() {
        return player1;
    }

    public GameType getGameType() {
        return gameType;
    }

    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }

    public void startGameLooopPvP(GridPane gridPlayer, GridPane gridPlayer1) {
        ThreadGameLoopPvP threadGameLoopPvP = new ThreadGameLoopPvP(gridPlayer, gridPlayer1);
        Thread thread = new Thread(threadGameLoopPvP);
        setGameLoopThread(thread);
        thread.start();
    }

    //игровой цикл PvP режима
    private class ThreadGameLoopPvP implements Runnable {
        public GridPane gridPlayer;
        public GridPane gridPlayer1;

        public ThreadGameLoopPvP(GridPane gridPlayer, GridPane gridPlayer1) {
            this.gridPlayer = gridPlayer;
            this.gridPlayer1 = gridPlayer1;
        }

        @Override
        public void run() {
            while (true) {

                gridPlayer.setDisable(true);
                gridPlayer1.setDisable(false);
                try {
                    synchronized (key) {
                        key.wait();
                    }
                } catch (InterruptedException e) {
                    break;
                }

                if (Thread.currentThread().isInterrupted()) {
                    break;
                }

                gridPlayer.setDisable(false);
                gridPlayer1.setDisable(true);
                try {
                    synchronized (key) {
                        key.wait();
                    }
                } catch (InterruptedException e) {
                    break;
                }

                if (Thread.currentThread().isInterrupted()) {
                    break;
                }
            }
        }
    }

    public void startGameLooopPvB(GridPane gridPlayer, GridPane gridPlayer1) {
        ThreadGameLoopPvB threadGameLoopPvB = new ThreadGameLoopPvB(gridPlayer, gridPlayer1);
        Thread thread = new Thread(threadGameLoopPvB);
        setGameLoopThread(thread);
        thread.start();
    }

    //    игровой цикл PvB режима
    private class ThreadGameLoopPvB implements Runnable {
        public GridPane gridPlayer;
        public GridPane gridPlayer1;

        public ThreadGameLoopPvB(GridPane gridPlayer, GridPane gridPlayer1) {
            this.gridPlayer = gridPlayer;
            this.gridPlayer1 = gridPlayer1;
        }

        @Override
        public void run() {
            gridPlayer.setDisable(true);
            gridPlayer1.setDisable(false);
            while (true) {

                try {
                    synchronized (key) {
                        key.wait();
                    }
                } catch (InterruptedException e) {
                    break;
                }
                if (Thread.interrupted()) {
                    break;
                }

                player1.makeStep(player.getClassField());
                if (Thread.interrupted()) {
                    break;
                }
            }
        }
    }

    public Status makeStep(Cell cell, Player target) {
        FieldManager fm = new FieldManager();
        DecorationController decorationController = new DecorationController();
        Status resultOfShot;

        resultOfShot = fm.processingShot(cell);
        cell.setStatus(resultOfShot);
        target.getClassField().getLivesCells().remove(cell);
        decorationController.showStatus(cell, false);

        if (resultOfShot == Status.UNDAMAGED) {
            fm.checkDeadShips(target.getClassField().getShips());
            return resultOfShot;
        }
        return resultOfShot;
    }

    public boolean checkWin(Player targetPlayer) {
        FieldManager fm = new FieldManager();

        boolean result = fm.shipsIsAlive(targetPlayer.getClassField());
        if (!result) {
            return true;
        }
        return false;
    }

    public void blockFields(GridPane gridPlayer, GridPane gridPlayer1) {
        gridPlayer.setDisable(true);
        gridPlayer1.setDisable(true);
    }
}
