package dungeonrunner.infoPanes;

import dungeonrunner.constants.Maps;
import dungeonrunner.constants.PaneConstants;
import dungeonrunner.interfaces.ITargetAction;
import dungeonrunner.interfaces.ITrigger;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.transform.Translate;

import java.util.ArrayList;
import java.util.List;

public abstract class ChoicePane extends StackPane implements ITrigger {

    protected List<ImageView> choiceImages = new ArrayList<>();
    protected Button confirm;
    protected int selectedChoice = -1;
    protected HBox titleBox, buttonBox;
    protected VBox mainBox;
    protected Label title;
    protected GridPane centralImgPane;

    protected List<ITargetAction> actions = new ArrayList<>();

    protected ChoicePane(double width, double height, double x, double y) {
        this.setPrefSize(width, height);

        setImages();

        setBoxes(width, height);

        setHandlers();

        this.getTransforms().addAll(
                new Translate(x, y)
        );

        this.setBackground(getPaneBackground());
        this.hide();
    }

    protected void setBoxes(double width, double height) {
        mainBox = new VBox(height * 0.05);

        titleBox = new HBox(width * 0.05);
        title = new Label(getTitle());
        title.setTextFill(getTitleColor());
        title.setFont(getTitleFont());
        title.setAlignment(Pos.CENTER);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.getChildren().add(title);
        titleBox.setBackground(new Background(new BackgroundFill(
                getTitleBoxColor(), null, null)));

        centralImgPane = createCentralImgPane(width, height);
        centralImgPane.setAlignment(Pos.CENTER);

        buttonBox = new HBox(width * 0.05);
        confirm = new Button(getConfirmButtonText());
        confirm.setTextFill(getConfirmButtonTextColor());
        confirm.setFont(getConfirmButtonTextFont());
        confirm.setBackground(new Background(new BackgroundFill(getConfirmButtonColor(), null, null)));
        confirm.setBorder(Border.stroke(getConfirmButtonStroke()));
        confirm.setAlignment(Pos.CENTER);
        confirm.setEffect(getConfirmButtonShadow());
        confirm.setDisable(true);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().add(confirm);

        mainBox.getChildren().addAll(titleBox, centralImgPane, buttonBox);
        mainBox.setAlignment(Pos.CENTER);
        VBox.setVgrow(mainBox, Priority.ALWAYS);
        VBox.setVgrow(titleBox, Priority.ALWAYS);
        VBox.setVgrow(centralImgPane, Priority.ALWAYS);
        VBox.setVgrow(buttonBox, Priority.ALWAYS);

        setAlignment(mainBox, Pos.CENTER);

        this.getChildren().add(mainBox);
    }

    protected void setHandlers() {
        final int[] selectedI = {-1};
        final ImageView[] prevChoice = {null};
        for (int i = 0; i < choiceImages.size(); i++) {
            ImageView choice = choiceImages.get(i);
            selectedI[0] = i;
            choice.setOnMouseClicked(
                    event -> {
                        selectedChoice = choiceImages.indexOf(choice);
                        highlightImg(prevChoice[0], choice, true);
                        confirm.setDisable(false);
                        prevChoice[0] = choice;
                    }
            );
            choice.setOnMouseEntered(
                    event -> {
                        highlightImg(prevChoice[0], choice, false);
                        choice.setCursor(Cursor.HAND);
                    }
            );
            choice.setOnMouseExited(
                    event -> {
                        unhighlightImg(prevChoice[0], choice);
                        choice.setCursor(Cursor.DEFAULT);
                    }
            );
        }
        confirm.setOnMouseEntered(
                event -> {
                    confirm.setEffect(PaneConstants.MAP_CHOICE_CONFIRM_BUTTON_HIGHLIGHT);
                    confirm.setCursor(Cursor.HAND);
                }
        );
        confirm.setOnMouseExited(
                event -> {
                    confirm.setEffect(PaneConstants.MAP_CHOICE_CONFIRM_BUTTON_SHADOW);
                    confirm.setCursor(Cursor.DEFAULT);
                }
        );
        confirm.setOnMouseClicked(
                event -> {
                    confirm.setCursor(Cursor.WAIT);
                    this.hide();
                    trigger();
                }
        );
    }

    protected void highlightImg(ImageView prev, ImageView curr, boolean selected) {
        if (!selected)
            curr.setEffect(getHighlightImgShadow());
        else {
            curr.setEffect(getSelectedImgShadow());

            if (prev != null)
                unhighlightImg(null, prev);
        }
    }

    protected void unhighlightImg(ImageView prev, ImageView curr) {
        if (curr == prev)
            curr.setEffect(getSelectedImgShadow());
        else
            curr.setEffect(getDefaultImgShadow());
    }

    public void show() {
        this.setVisible(true);
    }

    public void hide() {
        this.setVisible(false);
    }

    protected abstract DropShadow getSelectedImgShadow();

    protected abstract DropShadow getDefaultImgShadow();

    protected abstract DropShadow getHighlightImgShadow();

    protected abstract String getTitle();

    protected abstract Color getTitleColor();

    protected abstract Color getTitleBoxColor();

    protected abstract Font getTitleFont();

    protected abstract Color getConfirmButtonColor();

    protected abstract String getConfirmButtonText();

    protected abstract Color getConfirmButtonTextColor();

    protected abstract Font getConfirmButtonTextFont();

    protected abstract Color getConfirmButtonStroke();

    protected abstract DropShadow getConfirmButtonShadow();

    protected abstract Background getPaneBackground();

    protected abstract GridPane createCentralImgPane(double width, double height);

    protected abstract void setImages();

    @Override
    public List<ITargetAction> getActions() {
        return actions;
    }

    @Override
    public void addAction(ITargetAction action) {
        actions.add(action);
    }

    @Override
    public void removeAction(ITargetAction action) {
        actions.remove(action);
    }
}
