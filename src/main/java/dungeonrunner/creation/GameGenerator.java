package dungeonrunner.creation;

import dungeonrunner.Player;
import dungeonrunner.constants.Constants;
import dungeonrunner.constants.Enums;
import dungeonrunner.constants.PaneConstants;
import dungeonrunner.figures.*;
import dungeonrunner.infoPanes.AdditionalInformation;
import dungeonrunner.infoPanes.EndOfGame;
import dungeonrunner.infoPanes.Game2DPerspective;
import dungeonrunner.infoPanes.MapChoice;
import dungeonrunner.interfaces.IPickup;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Material;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

import java.util.List;
import java.util.Random;

public class GameGenerator {

    private DungeonMap map;
    private Player player;
    private final Group world;
    private AnimationTimer timer;
    private PointLight torch;
    private PerspectiveCamera camera;
    private Group cameraMount;
    private final SubScene scene;
    private Box exit;
    private boolean exitUnlocked = false;
    private Game2DPerspective littleMap;
    private MapChoice mapChoice;
    private AdditionalInformation info;
    private StackPane gameInfo;
    private EndOfGame endOfGame;

    public GameGenerator(SubScene worldScene) {
        this.world = (Group)worldScene.getRoot();
        this.scene = worldScene;
        this.cameraMount = new Group();
    }

    public void generateGame(MapChoice mapChoice, AdditionalInformation info, StackPane gameInfo, EndOfGame endOfGame) {
        this.mapChoice = mapChoice;
        this.info = info;
        this.gameInfo = gameInfo;
        this.endOfGame = endOfGame;

        createPlayer();
        setUpCamera();
        setUpLighting();

        mapChoice.addAction(this::startGame);
        mapChoice.show();
    }

    public void startGame() {
        buildDungeon();
        buildLittleMap(gameInfo);
        player.setUpLives(info.getLives());
        setUpGameTimer(info, endOfGame);
        timer.start();
        info.show();
        littleMap.show();
    }

    private void buildDungeon() {
        PhongMaterial wallMaterial = buildMaterial(Constants.WALL_DIFFUSE_COLOR, Constants.WALL_SPECULAR_COLOR);
        wallMaterial.setDiffuseMap(mapChoice.getMapBricks());

        PhongMaterial sawMaterial = buildMaterial(Constants.SAW_DIFFUSE_COLOR, Constants.SAW_SPECULAR_COLOR);

        PhongMaterial thornsMaterial = buildMaterial(Constants.THORNS_DIFFUSE_COLOR, Constants.THORNS_SPECULAR_COLOR);

        PhongMaterial keyMaterial = buildMaterial(Constants.KEY_DIFFUSE_COLOR, Constants.KEY_SPECULAR_COLOR);
        keyMaterial.setSpecularPower(64.0);

        PhongMaterial floorMaterial = buildMaterial(Constants.FLOOR_DIFFUSE_COLOR, Constants.FLOOR_SPECULAR_COLOR);

        PhongMaterial ceilingMaterial = buildMaterial(Constants.CEILING_DIFFUSE_COLOR, Constants.CEILING_SPECULAR_COLOR);

        this.map = new DungeonMap(mapChoice.getMap());

        int    rows       = this.map.getRows ( );
        int    columns    = this.map.getCols ( );
        double totalWidth = columns * Constants.CELL_SIZE;
        double totalDepth = rows * Constants.CELL_SIZE;

        buildFloorAndCeiling(totalWidth, totalDepth, floorMaterial, ceilingMaterial);

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

                    addWall(tile, positionX, positionY, positionZ, wallMaterial);

                    if (tile == Constants.SAW_XL || tile == Constants.SAW_XR ||
                            tile == Constants.SAW_ZL || tile == Constants.SAW_ZR)
                        addSaw(tile, positionX, positionY, positionZ, sawMaterial);

                }
                else if (tile == Constants.OCTA) {
                    addOcta(positionX, positionY, positionZ, wallMaterial);
                }
                else if (tile == Constants.THORNS) {
                    addThorns(positionX, positionY, positionZ, thornsMaterial);
                }
                else if (tile == Constants.KEY) {
                    addKey(positionX, positionY, positionZ, keyMaterial);
                }
            }
        }
    }

    private void buildLittleMap(StackPane gameInfo) {
        this.littleMap = new Game2DPerspective(
                PaneConstants.PERSP_2D_WIDTH, 0, 0,
                map, player
        );
        gameInfo.getChildren().add(littleMap);
        gameInfo.setAlignment(littleMap, Pos.BOTTOM_RIGHT);
    }

    private void addWall(int tile, double positionX, double positionY, double positionZ, Material wallMaterial) {
        Box wall = new Box(Constants.CELL_SIZE, Constants.WALL_HEIGHT, Constants.CELL_SIZE);

        Translate wallTranslate = new Translate (
                positionX,
                positionY,
                positionZ
        );
        wall.getTransforms().add(wallTranslate);

        wall.setMaterial(wallMaterial);

        this.world.getChildren().add(wall);

        if (tile == Constants.EXIT)
            this.exit = wall;
    }

    private void addOcta(double positionX, double positionY, double positionZ, Material octaMaterial) {
        Octahedron octa = new Octahedron(
                Constants.OCTA_HEIGHT, Constants.OCTA_CELL_SIZE,
                positionX,
                positionY,
                positionZ,
                octaMaterial
        );
        this.world.getChildren().add (octa);
    }

    private void addThorns(double positionX, double positionY, double positionZ, Material thornsMaterial) {
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

    private void addKey(double positionX, double positionY, double positionZ, Material keyMaterial) {
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

    private void buildFloorAndCeiling(double width, double depth, Material floorMaterial, Material ceilingMaterial) {
        Box floor = new Box(width, Constants.SLAB_THICKNESS, depth);
        Translate floorTranslate = new Translate (
                width / 2.0,
                Constants.WALL_HEIGHT / 2.0 + Constants.SLAB_THICKNESS / 2.0,
                depth / 2.0
        );
        floor.getTransforms().add(floorTranslate);
        floor.setMaterial(floorMaterial);
        Box ceiling = new Box(width, Constants.SLAB_THICKNESS, depth);
        Translate ceilingTranslate = new Translate (
                width / 2.0,
                -Constants.WALL_HEIGHT / 2.0 - Constants.SLAB_THICKNESS / 2.0,
                depth / 2.0
        );
        ceiling.getTransforms().add(ceilingTranslate);
        ceiling.setMaterial(ceilingMaterial);

        this.world.getChildren ( ).addAll ( floor, ceiling );
    }

    private PhongMaterial buildMaterial(Color diffuseColor, Color specularColor) {
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(diffuseColor);
        material.setSpecularColor(specularColor);
        return material;
    }

    private void createPlayer() {
        this.player = new Player(Constants.PLAYER_START_X, Constants.PLAYER_START_Y, Constants.PLAYER_LIVES, cameraMount);
    }

    private void setUpGameTimer(AdditionalInformation info, EndOfGame endOfGame) {

        List<IPickup> pickups = IPickup.getPickups();
        final int numMoments = 10;
        RandomItemsGenerator itemsGenerator = new RandomItemsGenerator(numMoments, world, map);

        this.timer = new AnimationTimer() {
            private double last;
            private double timeSlices = 0;

            @Override
            public void handle(long now) {
                if ( this.last == 0 ) {
                    this.last = now;
                }
                double dt = (now - this.last) / 10e8;
                this.last = now;

                timeSlices += dt;
                itemsGenerator.generateItems(timeSlices);

                player.update(map);

                updateCameraMount();
                updateTorch();
                info.updateAll(now);
                littleMap.update();
                Key.clean();

                if (exitUnlocked && player.isAtExit(map)) {
                    timer.stop ( );
                    info.hide();
                    littleMap.hide();
                    endOfGame.show(PaneConstants.WIN_MSG);
                }
                else if (player.getRestLives() == 0) {
                    timer.stop();
                    info.hide();
                    littleMap.hide();
                    endOfGame.show(PaneConstants.LOSE_MSG);
                }

                for (IPickup pickup : pickups) {
                    if (pickup.touchesPlayer(player)) {
                        pickup.affect(player);
                    }
                }

                itemsGenerator.cleanExpiredItems();

                updateExit();
            }
        };
    }

    private void updateExit() {
        if (player == null || !player.getHasTheKey() || exitUnlocked) return;
        PhongMaterial exitMaterial = buildMaterial(Constants.EXIT_DIFFUSE_COLOR, Constants.EXIT_SPECULAR_COLOR);
        exit.setMaterial(exitMaterial);
        exitUnlocked = true;
        map.getCanStep().add(Constants.EXIT);
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

    private void setUpLighting ( ) {
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

        this.world.getChildren().addAll(ambient, torch);
    }

    private void setUpCamera ( ) {
        this.camera = new PerspectiveCamera(true);

        this.camera.setNearClip(Constants.CAMERA_NEAR_CLIP);
        this.camera.setFarClip(Constants.CAMERA_FAR_CLIP);
        this.camera.setFieldOfView(Constants.CAMERA_FIELD_OF_VIEW);

        this.cameraMount.getChildren().add(this.camera);

        this.world.getChildren().add(cameraMount);

        updateCameraMount();

        scene.setCamera(this.camera);
    }

    public Player getPlayer() {
        return player;
    }

    public DungeonMap getMap() { return map; }
}
