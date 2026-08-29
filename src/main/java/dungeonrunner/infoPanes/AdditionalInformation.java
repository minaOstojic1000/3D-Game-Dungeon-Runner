package dungeonrunner.infoPanes;

import dungeonrunner.constants.PaneConstants;
import dungeonrunner.figures.LifeIndicator;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Translate;

import java.util.ArrayList;
import java.util.List;

import static javafx.geometry.Pos.CENTER;

public class AdditionalInformation extends StackPane {

    private Label timeLabel;
    private List<LifeIndicator> lives;
    long startTime;

    public AdditionalInformation(double paneWidth, double paneHeight, double paneX, double paneY, int numLives) {

        super();

        setLabels();

        setPlayerLives(numLives);

        this.setPrefSize(paneWidth, paneHeight);

        this.getTransforms().add(
                new Translate(paneX, paneY)
        );

        this.hide();
    }

    private void setLabels() {
        timeLabel = new Label(String.format(PaneConstants.TIME_FORMAT, 0, 0));
        timeLabel.setTextFill(PaneConstants.LABEL_TIME_COLOR);
        timeLabel.setFont(PaneConstants.LABEL_TIME_FONT);
        timeLabel.setAlignment(CENTER);

        setAlignment(timeLabel, Pos.CENTER_RIGHT);
        setMargin(timeLabel, new Insets(10, 10, 0, 0));

        this.getChildren().addAll(timeLabel);
    }

    private void setPlayerLives(int num) {

        HBox livesBox = new HBox(PaneConstants.BOX_LIFE_INDICATOR_SPACING);
        livesBox.setPadding(new Insets(0.1 * PaneConstants.BOX_LIFE_INDICATOR_SPACING));
        livesBox.setPrefWidth(Region.USE_COMPUTED_SIZE);

        lives = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            LifeIndicator life = new LifeIndicator(
                    PaneConstants.LIFE_INDICATOR_RADIUS,
                    PaneConstants.LIFE_INDICATOR_FILL,
                    PaneConstants.LIFE_INDICATOR_STROKE,
                    PaneConstants.LIFE_INDICATOR_GLOW
            );
            lives.add(life);
        }

        livesBox.getChildren().addAll(lives);
        livesBox.setAlignment(Pos.CENTER_LEFT);
        setAlignment(livesBox, Pos.CENTER_LEFT);
        setMargin(livesBox, new Insets(10, 0, 0, 10));

        this.getChildren().add(livesBox);
    }

    public void updateTimeLabel(long now) {
        if (startTime == 0)
            startTime = now;
        long passedSeconds = (now - startTime) / 1_000_000_000;
        long minutes = passedSeconds / 60;
        long seconds = passedSeconds % 60;
        timeLabel.setText(String.format(PaneConstants.TIME_FORMAT, minutes, seconds));
    }

    public void updateAll(long now) {
        updateTimeLabel(now);
    }

    public String getTimeLabelText() {
        return timeLabel.getText();
    }

    public void show() {
        setVisible(true);
    }

    public void hide() {
        setVisible(false);
    }

    public List<LifeIndicator> getLives() {
        return lives;
    }
}
