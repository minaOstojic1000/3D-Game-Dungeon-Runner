package dungeonrunner.constants;

import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class PaneConstants {

    public static final String TIME_FORMAT = "%02d:%02d";

    public static final Color LABEL_TIME_COLOR = Color.WHITE;
    public static final Font LABEL_TIME_FONT = Font.font("Consolas", FontWeight.BOLD, 28);
    public static final double BOX_LIFE_INDICATOR_SPACING = 20;
    public static final double INFO_PANE_HEIGHT = 40;

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
}
