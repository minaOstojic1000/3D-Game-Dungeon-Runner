package dungeonrunner;

import dungeonrunner.constants.Constants;
import dungeonrunner.constants.PaneConstants;
import dungeonrunner.creation.GameGenerator;
import dungeonrunner.infoPanes.AdditionalInformation;
import dungeonrunner.infoPanes.EndOfGame;
import dungeonrunner.infoPanes.MapChoice;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class DungeonRunner extends Application {

    private GameGenerator generator;
    private StackPane gameInfo;

    private void setUpInput(Scene scene) {
        if (generator == null) return;
        Player player = generator.getPlayer();
        if (player == null) return;

        scene.setOnKeyPressed ( event -> {
            switch ( event.getCode ( ) ) {
                case UP: {
                    player.setMoveForward ( true );
                    break;
                }
                case DOWN: {
                    player.setMoveBackward ( true );
                    break;
                }
                case LEFT: {
                    player.setRotateLeft ( true );
                    break;
                }
                case RIGHT: {
                    player.setRotateRight ( true );
                    break;
                }
            }
        } );

        scene.setOnKeyReleased ( event -> {
            switch ( event.getCode ( ) ) {
                case UP: {
                    player.setMoveForward ( false );
                    break;
                }
                case DOWN: {
                    player.setMoveBackward ( false );
                    break;
                }
                case LEFT: {
                    player.setRotateLeft ( false );
                    break;
                }
                case RIGHT: {
                    player.setRotateRight ( false );
                    break;
                }
            }
        } );
    }

    @Override
    public void start ( Stage stage ) {
        Group root = new Group();

        Scene mainScene = new Scene(root, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);

        Group world = new Group();

        SubScene worldScene = new SubScene (
                world,
                Constants.SCREEN_WIDTH,
                Constants.SCREEN_HEIGHT,
                true,
                SceneAntialiasing.BALANCED
        );

        root.getChildren().add(worldScene);

        MapChoice mapChoice = new MapChoice(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, 0, 0);

        EndOfGame endOfGame = new EndOfGame(
                Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, 0, 0
        );
        root.getChildren().add(endOfGame);

        gameInfo = new StackPane();
        gameInfo.setPrefSize(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        root.getChildren().add(gameInfo);

        AdditionalInformation hudInfo = new AdditionalInformation(
                Constants.SCREEN_WIDTH, PaneConstants.INFO_PANE_HEIGHT, 0, 0,
                Constants.PLAYER_LIVES
        );
        gameInfo.getChildren().add(hudInfo);
        gameInfo.setAlignment(hudInfo, Pos.TOP_CENTER);

        this.generator = new GameGenerator(worldScene);
        generator.generateGame(mapChoice, hudInfo, gameInfo, endOfGame);

        setUpInput(mainScene);

        root.getChildren().add(mapChoice); // has to be last because of mouse focus!!!

        stage.setTitle("Escape dungeon");
        stage.setScene(mainScene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main ( String[] args ) {
        launch ( args );
    }
}