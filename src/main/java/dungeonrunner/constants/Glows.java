package dungeonrunner.constants;

import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;

public class Glows {
    public static final DropShadow CYAN_GLOW = new DropShadow() {{
        setColor(Color.web("#00f0ff"));
        setRadius(25);
        setSpread(0.65);
    }};

    public static final DropShadow DARK_BLUE_GLOW = new DropShadow() {{
        setColor(Color.web("#0a194f"));
        setRadius(35);
        setSpread(0.40);
    }};

    public static final DropShadow AMBER_GLOW = new DropShadow() {{
        setColor(Color.web("#ffaa00"));
        setRadius(25);
        setSpread(0.70);
    }};

    public static final DropShadow GOLD_GLOW = new DropShadow() {{
        setColor(Color.web("#ffd700"));
        setRadius(25);
        setSpread(0.60);
    }};

    public static final DropShadow GREEN_GLOW = new DropShadow() {{
        setColor(Color.web("#39ff14"));
        setRadius(20);
        setSpread(0.70);
    }};

    public static final DropShadow RED_GLOW = new DropShadow() {{
        setColor(Color.web("#ff0033"));
        setRadius(25);
        setSpread(0.65);
    }};

    public static final DropShadow DEFAULT_SHADOW = new DropShadow() {{
        setColor(Color.rgb(0, 0, 0, 0.6));
        setRadius(10);
        setOffsetX(0);
        setOffsetY(4);
        setSpread(0.0);
    }};
}
