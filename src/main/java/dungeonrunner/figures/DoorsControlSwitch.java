package dungeonrunner.figures;

import dungeonrunner.Player;
import dungeonrunner.constants.Constants;
import dungeonrunner.interfaces.IPowerUp;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Translate;

public class DoorsControlSwitch extends Box implements IPowerUp {

    private Doors myDoors;
    private final PhongMaterial material;
    private final double positionX;
    private final double positionZ;
    private Rectangle surfaceRect;

    public DoorsControlSwitch(double width, double height, double depth,
                              double positionX, double positionY, double positionZ,
                              PhongMaterial material) {
        super(width, height, depth);
        this.material = material;
        this.surfaceRect = new Rectangle(width, depth);
        this.positionX = positionX;
        this.positionZ = positionZ;

        this.setMaterial(material);

        this.getTransforms().add(
                new Translate(positionX, positionY, positionZ)
        );

        IPowerUp.addPowerUp(this);
    }

    public DoorsControlSwitch(double width, double height, double depth,
                              double positionX, double positionY, double positionZ,
                              PhongMaterial material, Doors myDoors) {
        this(width, height, depth, positionX, positionY, positionZ, material);
        this.myDoors = myDoors;
    }

    public void setMyDoors(Doors myDoors) {
        this.myDoors = myDoors;
    }

    public Doors getMyDoors() {
        return myDoors;
    }

    @Override
    public void affect(Player player) {
        if (myDoors == null) return;

        if (myDoors.getLocked()) {
            myDoors.unlock();
            material.setDiffuseColor(Constants.DOORS_CONTROL_DARK_DIFFUSE);
        }
    }

    @Override
    public boolean touchesPlayer(Player player) {
        double rectX = positionX;
        double rectZ = positionZ;

        double left = rectX - surfaceRect.getWidth() / 2.;
        double right = rectX + surfaceRect.getWidth() / 2.;
        double up = rectZ - surfaceRect.getHeight() / 2.;
        double down = rectZ + surfaceRect.getHeight() / 2.;

        return player.overlapsRectangle(left, right, up, down);
    }
}
