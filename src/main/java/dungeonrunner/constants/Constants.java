package dungeonrunner.constants;

import javafx.scene.paint.Color;

import java.util.List;

public class Constants {
    public static final int EMPTY = 0;
    public static final int WALL  = 1;
    public static final int EXIT  = 2;
    public static final int OCTA  = 3;
    public static final int SAW_XL = 4;
    public static final int SAW_XR = 5;
    public static final int SAW_ZL = 6;
    public static final int SAW_ZR = 7;
    public static final int THORNS  = 8;
    public static final int KEY  = 9;

    public static final int[][] MAP = {
            {1,1,1,1,1,1,1,1,1},
            {1,0,0,9,1,0,0,0,1},
            {1,0,8,0,0,0,1,0,1},
            {1,0,3,1,5,0,4,0,1},
            {1,0,0,0,0,0,0,0,1},
            {1,3,1,0,7,1,0,1,1},
            {1,0,0,0,0,0,0,2,1},
            {1,1,1,1,6,1,1,1,1},
    };

    public static final int SCREEN_WIDTH  = 800;
    public static final int SCREEN_HEIGHT = 600;

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

    public static final double PLAYER_START_X        = 1.5;
    public static final double PLAYER_START_Y        = 1.5;
    public static final double PLAYER_MOVE_SPEED     = 0.02;
    public static final double PLAYER_ROTATION_SPEED = 0.05;
    public static final double PLAYER_RADIUS         = 0.25;
    public static final int PLAYER_LIVES         = 3;
}
