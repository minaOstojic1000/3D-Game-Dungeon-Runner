package dungeonrunner.constants;

import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class PaneConstants {

    public static final String TIME_FORMAT = "%02d:%02d";

    public static final Color LABEL_TIME_COLOR = Color.WHITE;
    public static final Font LABEL_TIME_FONT = Font.font("Consolas", FontWeight.BOLD, 28);
    public static final double BOX_LIFE_INDICATOR_SPACING = 20;
    public static final double INFO_PANE_HEIGHT = Constants.SCREEN_HEIGHT / 12.;

    public static final Color LABEL_END_GAME_COLOR = Color.WHITE;
    public static final Font LABEL_END_GAME_FONT = Font.font("Impact", FontWeight.BOLD, FontPosture.REGULAR, 48);
    public static final double PANEL_END_GAME_OPACITY = 0.8;
    public static final Background PANEL_END_GAME_BACKGROUND = new Background(new BackgroundFill(Color.BLACK, null, null));
    public static final String WIN_MSG = "YOU WIN!";
    public static final String LOSE_MSG = "GAME OVER";

    public static final double LIFE_INDICATOR_RADIUS = 10;
    public static final Color LIFE_INDICATOR_FILL = Color.ORANGERED;
    public static final Color LIFE_INDICATOR_STROKE = Color.ORANGE;
    public static final DropShadow LIFE_INDICATOR_GLOW = new DropShadow() {{
        setColor(Color.web("#ff0033"));
        setRadius(25);
        setSpread(0.65);
    }};

    public static final double PERSP_2D_WIDTH = Constants.SCREEN_WIDTH / 6.;
    public static final Color PERSP_2D_EMPTY_FILL = Color.color(0.11, 0.16, 0.29);
    public static final Color PERSP_2D_WALL_STROKE = Color.color(0.07, 0.09, 0.15);
    public static final Color PERSP_2D_WALL_FILL = Color.color(0.55, 0.75, 0.88);
    public static final Color PERSP_2D_EXIT_FILL = Constants.EXIT_DIFFUSE_COLOR;
    public static final BorderStroke PERSP_2D_STROKE = new BorderStroke(
            Color.BLACK,
            BorderStrokeStyle.SOLID,
            new CornerRadii(0),
            new BorderWidths(2)
    );
    public static final double PERSP_2D_OPACITY = 0.8;
    public static final Color PERSP_2D_PLAYER_FILL = Color.color(0.97, 0.98, 0.98);
    public static final Color PERSP_2D_PLAYER_STROKE = Color.color(0.65, 0.67, 0.71);
    public static final Color PERSP_2D_KEY_FILL = Constants.KEY_DIFFUSE_COLOR;
    public static final Color PERSP_2D_KEY_STROKE = Color.color(0.07, 0.09, 0.15);

}
