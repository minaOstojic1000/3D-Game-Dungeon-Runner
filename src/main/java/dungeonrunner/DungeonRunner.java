package dungeonrunner;

import dungeonrunner.constants.Constants;
import dungeonrunner.constants.Enums;
import dungeonrunner.constants.PaneConstants;
import dungeonrunner.figures.CircularSaw;
import dungeonrunner.figures.Key;
import dungeonrunner.figures.Octahedron;
import dungeonrunner.figures.Thorns;
import dungeonrunner.infoPanes.AdditionalInformation;
import dungeonrunner.infoPanes.EndOfGame;
import dungeonrunner.interfaces.IPickup;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.Material;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.List;
import java.util.Objects;

public class DungeonRunner extends Application {

    private GameGenerator generator;

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

        this.generator = new GameGenerator(worldScene);
        generator.generateGame();

        setUpInput(mainScene);

        EndOfGame endOfGame = new EndOfGame(
                Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, 0, 0
        );
        root.getChildren().add(endOfGame);

        AdditionalInformation info = new AdditionalInformation(
                Constants.SCREEN_WIDTH, PaneConstants.INFO_PANE_HEIGHT, 0, 0,
                Constants.PLAYER_LIVES
        );
        root.getChildren().add(info);

        generator.startGame(info, endOfGame);

        stage.setTitle("Escape dungeon");
        stage.setScene(mainScene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main ( String[] args ) {
        launch ( args );
    }
}