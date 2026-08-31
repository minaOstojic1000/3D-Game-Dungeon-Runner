package dungeonrunner.infoPanes;

import dungeonrunner.constants.Maps;
import dungeonrunner.constants.PaneConstants;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.Objects;

public class MapChoice extends ChoicePane {


    public MapChoice(double width, double height, double x, double y) {
        super(width, height, x, y);
    }

    protected void setImages() {
        StringBuilder name = new StringBuilder();
        name.append("/dungeonrunner/maps/map0.jpg");
        double width = this.getPrefWidth() * 0.65 / (Maps.MAPS.size() / 2.);
        for (int i = 0; i < Maps.MAPS.size(); i++) {
            name.replace(name.length() - 5, name.length() - 4, Integer.toString(i + 1));
            ImageView imgView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream(name.toString()))));
            choiceImages.add(imgView);
            imgView.setFitWidth(width);
            imgView.setPreserveRatio(true);
        }
    }

    public int[][] getMap() {
        return Maps.MAPS.get(selectedChoice);
    }

    public Image getMapBricks() {
        return Maps.MAP_BRICKS_IMAGES[selectedChoice];
    }

    @Override
    protected DropShadow getSelectedImgShadow() {
        return PaneConstants.SELECTED_MAP_IMG_SHADOW;
    }

    @Override
    protected DropShadow getDefaultImgShadow() {
        return PaneConstants.DEFAULT_MAP_IMG_SHADOW;
    }

    @Override
    protected DropShadow getHighlightImgShadow() {
        return PaneConstants.HIGHLIGHT_MAP_IMG_SHADOW;
    }

    @Override
    protected String getTitle() {
        return PaneConstants.MAP_CHOICE_TITLE;
    }

    @Override
    protected Color getTitleColor() {
        return PaneConstants.MAP_CHOICE_TITLE_COLOR;
    }

    @Override
    protected Color getTitleBoxColor() {
        return PaneConstants.MAP_CHOICE_CONFIRM_BUTTON_COLOR;
    }

    @Override
    protected Font getTitleFont() {
        return PaneConstants.MAP_CHOICE_TITLE_FONT;
    }

    @Override
    protected Color getConfirmButtonColor() {
        return PaneConstants.MAP_CHOICE_CONFIRM_BUTTON_COLOR;
    }

    @Override
    protected String getConfirmButtonText() {
        return PaneConstants.MAP_CHOICE_CONFIRM_BUTTON_TEXT;
    }

    @Override
    protected Color getConfirmButtonTextColor() {
        return PaneConstants.MAP_CHOICE_CONFIRM_BUTTON_TEXT_COLOR;
    }

    @Override
    protected Font getConfirmButtonTextFont() {
        return PaneConstants.MAP_CHOICE_CONFIRM_BUTTON_FONT;
    }

    @Override
    protected Color getConfirmButtonStroke() {
        return PaneConstants.MAP_CHOICE_CONFIRM_BUTTON_STROKE;
    }

    @Override
    protected DropShadow getConfirmButtonShadow() {
        return PaneConstants.MAP_CHOICE_CONFIRM_BUTTON_SHADOW;
    }

    @Override
    protected Background getPaneBackground() {
        return PaneConstants.MAP_CHOICE_BACKGROUND;
    }

    @Override
    protected GridPane createCentralImgPane(double width, double height) {
        GridPane pane = new GridPane(width * 0.03, height * 0.03);
        for (int i = 0; i < choiceImages.size() / 2; i++) {
            for (int j = 0; j < 2; j++) {
                pane.add(choiceImages.get(i * 2 + j), j, i);
            }
        }
        return pane;
    }
}
