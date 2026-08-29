package dungeonrunner.figures;

import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class LifeIndicator extends Circle {

    private Color fill;
    private DropShadow glow;

    public LifeIndicator(double radius,
                         Color fill, Color stroke, DropShadow effect) {
        super(radius, fill);
        this.setStroke(stroke);
        this.setEffect(effect);
        this.glow = effect;

        this.fill = fill;
    }

    public void loseColor() {
        this.setFill(Color.BLACK);
        this.setEffect(null);
    }

    public void getColored() {
        this.setFill(fill);
        this.setEffect(glow);
    }
}
