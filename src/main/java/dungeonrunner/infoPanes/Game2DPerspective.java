package dungeonrunner.infoPanes;

import dungeonrunner.DungeonMap;
import dungeonrunner.Player;
import dungeonrunner.constants.Constants;
import dungeonrunner.constants.PaneConstants;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class Game2DPerspective extends StackPane {

    private final DungeonMap map;
    private final double width;
    private double height;
    private double tileSize;
    private Group fields;
    private Group playerView;
    private final Player player;
    private Rotate direction;
    private Translate position;
    private Circle keyCircle;
    private Rectangle exitRect;
    private boolean keyErased = false;

    public Game2DPerspective(double width, double positionX, double positionY, DungeonMap map, Player player) {
        super();

        this.player = player;
        this.setMaxWidth(width);
        this.width = width;

        this.map = map;
        drawMap();
        this.setMaxHeight(height);

        drawPlayer();

        this.setBorder(new Border(PaneConstants.PERSP_2D_STROKE));
        setAlignment(fields, Pos.CENTER);

        this.getTransforms().add(
                new Translate(positionX, positionY)
        );

        this.setOpacity(PaneConstants.PERSP_2D_OPACITY);
        StackPane.setMargin(this, new Insets(20, 20, 20, 20));

        this.hide();
    }

    private void drawMap() {
        int rows = map.getRows();
        int cols = map.getCols();

        this.tileSize = width / cols;

        this.height = tileSize * rows;

        fields = new Group();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Rectangle field = new Rectangle(
                        j * tileSize, i * tileSize,
                        tileSize, tileSize);
                field.setStroke(PaneConstants.PERSP_2D_WALL_STROKE);
                int fieldVal = map.get(j, i);
                if (!map.getCanStep().contains(fieldVal)) {
                    field.setFill(PaneConstants.PERSP_2D_WALL_FILL);
                }
                else {
                    field.setFill(PaneConstants.PERSP_2D_EMPTY_FILL);
                }
                fields.getChildren().add(field);
                if (fieldVal == Constants.KEY) {
                    double keyRadius = tileSize * 0.3;
                    keyCircle = new Circle(
                            j * tileSize + tileSize / 2.,
                            i * tileSize + tileSize / 2.,
                            keyRadius);
                    keyCircle.setFill(PaneConstants.PERSP_2D_KEY_FILL);
                    keyCircle.setStroke(PaneConstants.PERSP_2D_KEY_STROKE);
                    fields.getChildren().add(keyCircle);
                }
                else if (fieldVal == Constants.EXIT)
                    exitRect = field;
            }
        }
        this.getChildren().addAll(fields);
    }

    private void drawPlayer() {
        playerView = new Group();

        double x = player.getPositionX() * tileSize;
        double y = player.getPositionY() * tileSize;
        position = new Translate(x, y);

        double rectHeight = 0.1 * tileSize;
        double rectWidth = 0.8 * tileSize;
        double circleRadius = 0.3 * tileSize;

        Rectangle rect = new Rectangle(0, -rectHeight / 2., rectWidth, rectHeight);
        rect.setFill(PaneConstants.PERSP_2D_PLAYER_FILL);
        rect.setStroke(PaneConstants.PERSP_2D_PLAYER_STROKE);

        Circle circle = new Circle(circleRadius);
        circle.setFill(PaneConstants.PERSP_2D_PLAYER_FILL);
        circle.setStroke(PaneConstants.PERSP_2D_PLAYER_STROKE);

        direction = new Rotate(
                Math.atan2(player.getDirectionX(), player.getDirectionY()),
                position.getX(), position.getY());

        playerView.getChildren().addAll(rect, circle);

        playerView.getTransforms().addAll(direction, position);
        fields.getChildren().addAll(playerView);
    }

    public void update() {
        double newX = player.getPositionX() * tileSize;
        double newY = player.getPositionY() * tileSize;

        position.setX(newX);
        position.setY(newY);
        direction.setAngle(Math.toDegrees(Math.atan2(this.player.getDirectionY(),this.player.getDirectionX())));
        direction.setPivotX(newX);
        direction.setPivotY(newY);

        if (!keyErased && player.getHasTheKey()) {
            keyErased = true;
            fields.getChildren().remove(keyCircle);
            exitRect.setFill(PaneConstants.PERSP_2D_EXIT_FILL);
        }
    }

    public void show() {
        setVisible(true);
    }

    public void hide() {
        setVisible(false);
    }

}