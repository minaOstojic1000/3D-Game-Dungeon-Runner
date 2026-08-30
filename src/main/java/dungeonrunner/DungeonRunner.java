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

    private DungeonMap map;
    private Player player;
    private Group world;
    private PerspectiveCamera camera;
    private Group cameraMount;
    private PointLight torch;
    private AnimationTimer timer;

    private void buildDungeon ( ) {
        PhongMaterial wallMaterial = new PhongMaterial ( );
        wallMaterial.setDiffuseColor ( Constants.WALL_DIFFUSE_COLOR );
        wallMaterial.setSpecularColor ( Constants.WALL_SPECULAR_COLOR );
        Image texture = new Image(
                Objects.requireNonNull(getClass().getResourceAsStream("/dungeonrunner/backgrounds/bricks.jpg")));
        wallMaterial.setDiffuseMap(texture);

        PhongMaterial exitMaterial = new PhongMaterial();
        exitMaterial.setDiffuseColor ( Constants.EXIT_DIFFUSE_COLOR );
        exitMaterial.setSpecularColor ( Constants.EXIT_SPECULAR_COLOR );

        PhongMaterial sawMaterial = new PhongMaterial();
        sawMaterial.setDiffuseColor ( Constants.SAW_DIFFUSE_COLOR );
        sawMaterial.setSpecularColor ( Constants.SAW_SPECULAR_COLOR );

        PhongMaterial thornsMaterial = new PhongMaterial();
        thornsMaterial.setDiffuseColor ( Constants.THORNS_DIFFUSE_COLOR );
        thornsMaterial.setSpecularColor ( Constants.THORNS_SPECULAR_COLOR );

        PhongMaterial keyMaterial = new PhongMaterial();
        keyMaterial.setDiffuseColor ( Constants.KEY_DIFFUSE_COLOR );
        keyMaterial.setSpecularColor ( Constants.KEY_SPECULAR_COLOR );
        keyMaterial.setSpecularPower(64.0);

        PhongMaterial floorMaterial = new PhongMaterial();
        floorMaterial.setDiffuseColor(Color.rgb(60, 40, 20));

        PhongMaterial ceilingMaterial = new PhongMaterial();
        ceilingMaterial.setDiffuseColor(Color.rgb(25, 25, 45));
        
        this.map = new DungeonMap ( Constants.MAP );

        int    rows       = this.map.getRows ( );
        int    columns    = this.map.getCols ( );
        double totalWidth = columns * Constants.CELL_SIZE;
        double totalDepth = rows * Constants.CELL_SIZE;

        Box floor = new Box ( totalWidth, Constants.SLAB_THICKNESS, totalDepth );
        Translate floorTranslate = new Translate (
                totalWidth / 2.0,
                Constants.WALL_HEIGHT / 2.0 + Constants.SLAB_THICKNESS / 2.0,
                totalDepth / 2.0
        );
        floor.getTransforms ( ).add ( floorTranslate );
        floor.setMaterial ( floorMaterial );

        Box ceiling = new Box ( totalWidth, Constants.SLAB_THICKNESS, totalDepth );
        Translate ceilingTranslate = new Translate (
                totalWidth / 2.0,
                -Constants.WALL_HEIGHT / 2.0 - Constants.SLAB_THICKNESS / 2.0,
                totalDepth / 2.0
        );
        ceiling.getTransforms ( ).add ( ceilingTranslate );
        ceiling.setMaterial ( ceilingMaterial );

        this.world.getChildren ( ).addAll ( floor, ceiling );

        for ( int row = 0; row < rows; row++ ) {
            for ( int column = 0; column < columns; column++ ) {
                int tile = this.map.get ( column, row );
                double positionX = column * Constants.CELL_SIZE + Constants.CELL_SIZE / 2.0;
                double positionY = 0;
                double positionZ = row * Constants.CELL_SIZE + Constants.CELL_SIZE / 2.0;

                if ( tile == Constants.WALL || tile == Constants.EXIT ||
                        tile == Constants.SAW_XL ||
                        tile == Constants.SAW_XR ||
                        tile == Constants.SAW_ZL ||
                        tile == Constants.SAW_ZR) {
                    Box wall = new Box ( Constants.CELL_SIZE, Constants.WALL_HEIGHT, Constants.CELL_SIZE );

                    Translate wallTranslate = new Translate (
                            positionX,
                            positionY,
                            positionZ
                    );
                    wall.getTransforms ( ).add ( wallTranslate );

                    wall.setMaterial ( tile == Constants.EXIT ? exitMaterial : wallMaterial );

                    this.world.getChildren().add ( wall );

                    if (tile == Constants.SAW_XL || tile == Constants.SAW_XR ||
                            tile == Constants.SAW_ZL || tile == Constants.SAW_ZR)
                        addSaw(tile, positionX, positionY, positionZ, sawMaterial);

                }
                else if (tile == Constants.OCTA) {

                    Octahedron octa = new Octahedron(
                            Constants.OCTA_HEIGHT, Constants.OCTA_CELL_SIZE,
                            positionX,
                            positionY,
                            positionZ,
                            wallMaterial
                    );
                    this.world.getChildren().add (octa);
                }
                else if (tile == Constants.THORNS) {
                    Thorns thorns = new Thorns(
                            Constants.THORNS_HEIGHT,
                            Constants.ALL_THORNS_WIDTH,
                            Constants.THORNS_NUMBER,
                            Constants.THORNS_ROW_GAP,
                            Constants.THORNS_COL_GAP,
                            positionX,
                            positionY + Constants.WALL_HEIGHT / 2.,
                            positionZ,
                            Constants.THORNS_SPEED,
                            Constants.THORNS_PERIOD,
                            thornsMaterial
                    );
                    this.world.getChildren().add(thorns);
                }
                else if (tile == Constants.KEY) {
                    Key key = new Key(
                            Constants.KEY_HEIGHT,
                            positionX,
                            positionY,
                            positionZ,
                            Constants.KEY_DURATION,
                            keyMaterial
                    );
                    this.world.getChildren().add(key);
                }
            }
        }
    }

    private void addSaw(int tile, double positionX, double positionY, double positionZ, Material sawMaterial) {
        Enums.AXIS axis = Enums.AXIS.X;
        Enums.DIRECTION dir = Enums.DIRECTION.LEFT;
        if (tile == Constants.SAW_XR) {
            dir = Enums.DIRECTION.RIGHT;
        }
        else if (tile == Constants.SAW_ZL) {
            axis = Enums.AXIS.Z;
        }
        else if (tile == Constants.SAW_ZR) {
            axis = Enums.AXIS.Z;
            dir = Enums.DIRECTION.RIGHT;
        }
        CircularSaw saw = new CircularSaw(
                Constants.SAW_WIDTH_HEIGHT,
                Constants.SAW_THICKNESS,
                positionX,
                positionY,
                positionZ,
                axis,
                dir,
                Constants.SAW_DISTANCE,
                Constants.SAW_SPEED,
                Constants.SAW_PERIOD,
                sawMaterial
        );
        this.world.getChildren().add(saw);
    }

    private void setupLighting ( ) {
        AmbientLight ambient = new AmbientLight ( Constants.AMBIENT_LIGHT_COLOR_BRIGHT );

        this.torch = new PointLight ( Constants.POINT_LIGHT_COLOR );
        this.torch.setMaxRange ( Constants.CELL_SIZE * 6 );

        Timeline blinking = new Timeline(
                new KeyFrame(
                        Duration.seconds(0),
                        event -> ambient.setColor(Constants.AMBIENT_LIGHT_COLOR_BRIGHT)
                ),
                new KeyFrame(
                        Duration.seconds(0.08),
                        event -> ambient.setColor(Constants.AMBIENT_LIGHT_COLOR_MEDIUM_DARK)
                ),
                new KeyFrame(
                        Duration.seconds(0.14),
                        event -> ambient.setColor(Constants.AMBIENT_LIGHT_COLOR_BRIGHT)
                ),
                new KeyFrame(
                        Duration.seconds(0.22),
                        event -> ambient.setColor(Constants.AMBIENT_LIGHT_COLOR_DARK)
                ),
                new KeyFrame(
                        Duration.seconds(0.32),
                        event -> ambient.setColor(Constants.AMBIENT_LIGHT_COLOR_MEDIUM_BRIGHT)
                ),
                new KeyFrame(
                        Duration.seconds(0.55),
                        event -> ambient.setColor(Constants.AMBIENT_LIGHT_COLOR_BRIGHT)
                )
        );
        blinking.setCycleCount(Animation.INDEFINITE);
        blinking.setAutoReverse(true);
        blinking.play();

        this.world.getChildren ( ).addAll ( ambient, torch );
    }

    private void setupCamera ( ) {
        this.camera = new PerspectiveCamera ( true );

        this.camera.setNearClip ( Constants.CAMERA_NEAR_CLIP );
        this.camera.setFarClip ( Constants.CAMERA_FAR_CLIP );
        this.camera.setFieldOfView ( Constants.CAMERA_FIELD_OF_VIEW );

        this.cameraMount = new Group ( this.camera );

        this.world.getChildren ( ).add ( cameraMount );

        updateCameraMount ( );
    }

    private void setupInput(Scene scene) {
        scene.setOnKeyPressed ( event -> {
            switch ( event.getCode ( ) ) {
                case UP: {
                    this.player.setMoveForward ( true );
                    break;
                }
                case DOWN: {
                    this.player.setMoveBackward ( true );
                    break;
                }
                case LEFT: {
                    this.player.setRotateLeft ( true );
                    break;
                }
                case RIGHT: {
                    this.player.setRotateRight ( true );
                    break;
                }
            }
        } );

        scene.setOnKeyReleased ( event -> {
            switch ( event.getCode ( ) ) {
                case UP: {
                    this.player.setMoveForward ( false );
                    break;
                }
                case DOWN: {
                    this.player.setMoveBackward ( false );
                    break;
                }
                case LEFT: {
                    this.player.setRotateLeft ( false );
                    break;
                }
                case RIGHT: {
                    this.player.setRotateRight ( false );
                    break;
                }
            }
        } );
    }

    private void updateCameraMount ( ) {
        Translate camerMountTranslate = new Translate (
                this.player.getPositionX( ) * Constants.CELL_SIZE,
                0,
                this.player.getPositionY( ) * Constants.CELL_SIZE
        );

        Rotate camerMountRotate = new Rotate (
                Math.toDegrees ( Math.atan2 ( this.player.getDirectionX ( ), this.player.getDirectionY ( ) ) ),
                Rotate.Y_AXIS
        );

        this.cameraMount.getTransforms ( ).setAll (
                camerMountTranslate,
                camerMountRotate
        );
    }

    private void updateTorch() {
        Translate torchTranslate = new Translate (
                this.player.getPositionX( ) * Constants.CELL_SIZE,
                0,
                this.player.getPositionY( ) * Constants.CELL_SIZE
        );

        this.torch.getTransforms ( ).setAll ( torchTranslate );
    }

    @Override
    public void start ( Stage stage ) {

        Group root = new Group();

        Scene mainScene = new Scene(root, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);

        this.player = new Player(Constants.PLAYER_START_X, Constants.PLAYER_START_Y, Constants.PLAYER_LIVES);
        this.world = new Group();

        buildDungeon();
        setupLighting();
        setupCamera();

        SubScene worldScene = new SubScene (
                this.world,
                Constants.SCREEN_WIDTH,
                Constants.SCREEN_HEIGHT,
                true,
                SceneAntialiasing.BALANCED
        );
        worldScene.setCamera(this.camera);

        root.getChildren().add(worldScene);

        setupInput(mainScene);

        List<IPickup> pickups = IPickup.getPickups();

        EndOfGame endOfGame = new EndOfGame(
                Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, 0, 0
        );
        root.getChildren().add(endOfGame);

        AdditionalInformation info = new AdditionalInformation(
                Constants.SCREEN_WIDTH, PaneConstants.INFO_PANE_HEIGHT, 0, 0,
                Constants.PLAYER_LIVES
        );
        root.getChildren().add(info);
        info.show();

        player.setUpLives(info.getLives());

        this.timer = new AnimationTimer ( ) {
            @Override
            public void handle ( long now ) {
                player.update ( map );

                updateCameraMount ( );
                updateTorch ( );
                info.updateAll(now);
                Key.clean();

                if (player.isAtExit(map)) {
                    timer.stop ( );
                    endOfGame.show(PaneConstants.WIN_MSG);
                }
                else if (player.getRestLives() == 0) {
                    timer.stop();
                    endOfGame.show(PaneConstants.LOSE_MSG);
                }

                for (IPickup pickup : pickups) {
                    if (pickup.touchesPlayer(player)) {
                        pickup.affect(player);
                    }
                }
            }
        };
        timer.start ( );

        stage.setTitle("Escape dungeon");
        stage.setScene(mainScene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main ( String[] args ) {
        launch ( args );
    }
}