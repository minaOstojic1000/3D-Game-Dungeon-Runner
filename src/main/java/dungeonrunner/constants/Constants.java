package dungeonrunner.constants;

import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.*;

import java.util.Objects;

public class Constants {
    public static final int EMPTY = 0;
    public static final int WALL  = 1;
    public static final int EXIT  = 2;
    public static final int OCTA  = 3;
    public static final int SAW_XL = 4;
    public static final int SAW_XR = 5;
    public static final int SAW_ZU = 6;
    public static final int SAW_ZD = 7;
    public static final int THORNS  = 8;
    public static final int KEY  = 9;
    public static final int DOORS_X  = 10;
    public static final int DOORS_Z  = 11;
    public static final int DOORS_CONTROL_U  = 12;
    public static final int DOORS_CONTROL_D  = 13;
    public static final int DOORS_CONTROL_R  = 14;
    public static final int DOORS_CONTROL_L  = 15;
    public static final int GUARD  = 16;

    public static final int SCREEN_WIDTH  = 800;
    public static final int SCREEN_HEIGHT = 600;

    public static final double LIFE_BOOSTER_FREQUENCY = 10;
    public static final double SHIELD_FREQUENCY = 15;
    public static final double POTION_FREQUENCY = 15;

    public static final double CELL_SIZE      = 2.0;  
    public static final double WALL_HEIGHT    = 2.0;  
    public static final double SLAB_THICKNESS = 0.05;

    public static final double OCTA_CELL_SIZE = 0.7 * CELL_SIZE;
    public static final double OCTA_HEIGHT    = WALL_HEIGHT;

    public static final double SAW_WIDTH_HEIGHT = WALL_HEIGHT;
    public static final double SAW_THICKNESS    = 0.05;
    public static final double SAW_SPEED    = 1;
    public static final double SAW_PERIOD    = 0.2;
    public static final double SAW_DISTANCE    = CELL_SIZE;
    public static final Color SAW_DIFFUSE_COLOR  = Color.color(0.70, 0.73, 0.78);
    public static final Color SAW_SPECULAR_COLOR = Color.color(0.35, 0.37, 0.40);

    public static final double ALL_THORNS_WIDTH = CELL_SIZE * 0.75;
    public static final int THORNS_NUMBER = 5;
    public static final double THORNS_ROW_GAP    = ALL_THORNS_WIDTH * 0.2;
    public static final double THORNS_COL_GAP    = THORNS_ROW_GAP;
    public static final double THORNS_HEIGHT    = WALL_HEIGHT * 0.5;
    public static final double THORNS_SPEED    = 0.5;
    public static final double THORNS_PERIOD    = 3;
    public static final Color THORNS_DIFFUSE_COLOR  = Color.color(0.18, 0.18, 0.19);
    public static final Color THORNS_SPECULAR_COLOR = Color.color(0.10, 0.10, 0.11);

    public static final double KEY_HEIGHT    = WALL_HEIGHT * 0.3;
    public static final double KEY_DURATION    = 3;
    public static final Color KEY_DIFFUSE_COLOR  = Color.color(0.85, 0.65, 0.20);
    public static final Color KEY_SPECULAR_COLOR = Color.color(0.95, 0.90, 0.70);

    public static final Color WALL_DIFFUSE_COLOR  = Color.rgb ( 110, 110, 110 );
    public static final Color WALL_SPECULAR_COLOR = Color.rgb ( 40,  40,  40 );
    public static final Color EXIT_DIFFUSE_COLOR  = Color.rgb ( 0, 200,  80 );
    public static final Color EXIT_SPECULAR_COLOR = Color.rgb ( 0,  80,  30 );

    public static final Color FLOOR_DIFFUSE_COLOR  = Color.rgb(60, 40, 20);
    public static final Color FLOOR_SPECULAR_COLOR = null;

    public static final Color CEILING_DIFFUSE_COLOR  = Color.rgb(25, 25, 45);
    public static final Color CEILING_SPECULAR_COLOR = null;

    public static final Color AMBIENT_LIGHT_COLOR_BRIGHT = Color.rgb(50, 45, 40);
    public static final Color AMBIENT_LIGHT_COLOR_MEDIUM_BRIGHT = Color.rgb(45, 40, 35);
    public static final Color AMBIENT_LIGHT_COLOR_MEDIUM_DARK = Color.rgb(38, 33, 28);
    public static final Color AMBIENT_LIGHT_COLOR_DARK = Color.rgb(32, 28, 23);

    public static final Color POINT_LIGHT_COLOR   = Color.rgb ( 255, 200, 120 );
    
    public static final double CAMERA_NEAR_CLIP     = 0.05;
    public static final double CAMERA_FAR_CLIP      = 500.0;
    public static final double CAMERA_FIELD_OF_VIEW = 75.0;

    public static final double PLAYER_START_X_DEFAULT = 1.5;
    public static final double PLAYER_START_Y_DEFAULT = 1.5;
    public static final double PLAYER_MOVE_SPEED     = 0.02;
    public static final double PLAYER_ROTATION_SPEED = 0.05;
    public static final double PLAYER_RADIUS         = 0.25;
    public static final int PLAYER_LIVES         = 3;

    public static final double LIFE_BOOSTER_SIZE = CELL_SIZE / 5.0;
    public static final Color LIFE_BOOSTER_DIFFUSE = Color.color(0.55, 0.04, 0.12);
    public static final Color LIFE_BOOSTER_SPECULAR = Color.color(0.95, 0.40, 0.45);
    public static final double LIFE_BOOSTER_DURATION = 5;
    public static final double LIFE_BOOSTER_ROTATION_TIME = 3;

    public static final double SHIELD_RADIUS = CELL_SIZE / 8.0;
    public static final Color SHIELD_DIFFUSE = Color.color(0.12, 0.45, 0.95, 0.25);
    public static final Color SHIELD_SPECULAR = Color.color(0.75, 0.92, 1.00);
    public static final double SHIELD_DURATION = 5;
    public static final double IMMUNITY_DURATION = 10;
    public static final double SHIELD_ROTATION_TIME = 3;

    public static final Background SHIELD_VIEW = new Background(
            new BackgroundFill(
                    new RadialGradient(
                            0,
                            0,
                            0.5,
                            0.5,
                            0.75,
                            true,
                            CycleMethod.NO_CYCLE,
                            new Stop(0.45, Color.rgb(40, 120, 255, 0.00)),
                            new Stop(0.65, Color.rgb(40, 130, 255, 0.10)),
                            new Stop(0.85, Color.rgb(60, 150, 255, 0.25)),
                            new Stop(1.00, Color.rgb(100, 190, 255, 0.42))
                    ),
                    CornerRadii.EMPTY,
                    Insets.EMPTY
            )
    );

    public static final Background SHIELD_FLASH = new Background(
            new BackgroundFill(
                    new LinearGradient(
                            0, 0,
                            1, 0,
                            true,
                            CycleMethod.NO_CYCLE,
                            new Stop(0.0, Color.TRANSPARENT),
                            new Stop(0.35, Color.TRANSPARENT),
                            new Stop(0.50, Color.rgb(160, 220, 255, 0.25)),
                            new Stop(0.65, Color.TRANSPARENT),
                            new Stop(1.0, Color.TRANSPARENT)
                    ),
                    CornerRadii.EMPTY,
                    Insets.EMPTY
            )
    );

    public static final double POTION_RADIUS = CELL_SIZE / 8.0;
    public static final Color POTION_DIFFUSE_LIQUID = Color.color(0.02, 0.43, 0.29, 0.75);
    public static final Color POTION_DIFFUSE_GLASS = Color.color(0.12, 0.45, 0.95, 0.25);
    public static final Color POTION_SPECULAR = Color.color(0.50, 0.55, 0.60);
    public static final double POTION_DURATION = 10;
    public static final double POTION_EFFECT_DURATION = 10;
    public static final double POTION_ROTATION_TIME = 3;

    public static final double DOORS_WIDTH = CELL_SIZE;
    public static final double DOORS_HEIGHT = WALL_HEIGHT;
    public static final double DOORS_DEPTH = CELL_SIZE * 0.8;
    public static final Color DOORS_DIFFUSE_COLOR = Color.color(0.42, 0.26, 0.15);
    public static final Color DOORS_SPECULAR_COLOR = Color.color(0.55, 0.45, 0.38);
    public static final double DOORS_OPENING_TIME = 3;
    public static final Image DOOR_LEFT_IMG =
            new Image(Objects.requireNonNull(Maps.class.getResourceAsStream("/dungeonrunner/doors/leftDoor.png")));
    public static final Image DOOR_RIGHT_IMG =
            new Image(Objects.requireNonNull(Maps.class.getResourceAsStream("/dungeonrunner/doors/rightDoor.png")));


    public static final double DOORS_CONTROL_WIDTH = CELL_SIZE * 0.1;
    public static final double DOORS_CONTROL_HEIGHT = WALL_HEIGHT * 0.2;
    public static final double DOORS_CONTROL_DEPTH = CELL_SIZE * 0.15;
    public static final Color DOORS_CONTROL_DARK_DIFFUSE = Color.color(0.18, 0.18, 0.20);
    public static final Color DOORS_CONTROL_LIGHT_DIFFUSE = Color.color(0.35, 0.32, 0.30);
    public static final Color DOORS_CONTROL_SPECULAR = Color.color(0.70, 0.70, 0.75);

    public static final double GUARD_WIDTH = CELL_SIZE * 0.3;
    public static final double GUARD_HEIGHT = WALL_HEIGHT * 0.8;
    public static final double GUARD_SPEED = 1;

    public static final Image GUARD_IMAGE_HEAD =
            new Image(Objects.requireNonNull(Maps.class.getResourceAsStream("/dungeonrunner/guard/head.png")));
    public static final Image GUARD_IMAGE_BODY =
            new Image(Objects.requireNonNull(Maps.class.getResourceAsStream("/dungeonrunner/guard/body.png")));
    public static final Image GUARD_IMAGE_ARM =
            new Image(Objects.requireNonNull(Maps.class.getResourceAsStream("/dungeonrunner/guard/arm.png")));
    public static final Image GUARD_IMAGE_LEGS =
            new Image(Objects.requireNonNull(Maps.class.getResourceAsStream("/dungeonrunner/guard/legs.png")));
}