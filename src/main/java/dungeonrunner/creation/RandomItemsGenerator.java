package dungeonrunner.creation;

import dungeonrunner.constants.Constants;
import dungeonrunner.figures.LifeBooster;
import dungeonrunner.figures.Potion;
import dungeonrunner.figures.Shield;
import javafx.scene.Group;

import java.util.Random;

public class RandomItemsGenerator {

    private final double[] nextShieldMoments;
    private final double[] nextHeartMoments;
    private final double[] nextPotionMoments;
    private int currMomentH = 0, currMomentS = 0, currMomentP = 0;
    private double momentsSumH = 0, momentsSumS = 0, momentsSumP = 0;
    private final int numMoments;
    private final Group world;
    private final DungeonMap map;

    public RandomItemsGenerator(int numMoments, Group world, DungeonMap map) {
        Random random = new Random();

        this.numMoments = numMoments;
        nextShieldMoments = new double[numMoments];
        nextHeartMoments = new double[numMoments];
        nextPotionMoments = new double[numMoments];

        for (int i = 0; i < numMoments; i++) {
            nextHeartMoments[i] = random.nextDouble(Constants.LIFE_BOOSTER_FREQUENCY);
            nextShieldMoments[i] = random.nextDouble(Constants.SHIELD_FREQUENCY);
            nextPotionMoments[i] = random.nextDouble(Constants.POTION_FREQUENCY);
        }

        this.world = world;
        this.map = map;
    }

    private void generateNextHeart(double timeSlices) {
        while (timingCondition(timeSlices, momentsSumH, nextHeartMoments[currMomentH])) {

            momentsSumH += nextHeartMoments[currMomentH];
            currMomentH = (currMomentH + 1) % numMoments;

            LifeBooster life = LifeBooster.generateRandomLifeBooster(
                    map,
                    Constants.LIFE_BOOSTER_SIZE,
                    Constants.LIFE_BOOSTER_DIFFUSE,
                    Constants.LIFE_BOOSTER_SPECULAR,
                    Constants.LIFE_BOOSTER_DURATION,
                    Constants.LIFE_BOOSTER_ROTATION_TIME
            );
            world.getChildren().add(life);
        }
    }

    private void generateNextShield(double timeSlices) {
        while (timingCondition(timeSlices, momentsSumS, nextShieldMoments[currMomentS])) {
            momentsSumS += nextShieldMoments[currMomentS];
            currMomentS = (currMomentS + 1) % numMoments;

            Shield shield = Shield.generateRandomShields(
                    map,
                    Constants.SHIELD_RADIUS,
                    Constants.SHIELD_DIFFUSE, Constants.SHIELD_SPECULAR,
                    Constants.IMMUNITY_DURATION,
                    Constants.SHIELD_DURATION,
                    Constants.SHIELD_ROTATION_TIME
            );
            world.getChildren().add(shield);
        }
    }

    private void generateNextPotion(double timeSlices) {
        while (timingCondition(timeSlices, momentsSumP, nextPotionMoments[currMomentP])) {

            momentsSumP += nextPotionMoments[currMomentP];
            currMomentP = (currMomentP + 1) % numMoments;

            Potion potion = Potion.generateRandomPotions(
                    map,
                    Constants.POTION_RADIUS,
                    Constants.POTION_DIFFUSE_GLASS,
                    Constants.POTION_DIFFUSE_LIQUID,
                    Constants.POTION_SPECULAR,
                    Constants.POTION_EFFECT_DURATION,
                    Constants.POTION_DURATION,
                    Constants.POTION_ROTATION_TIME
            );
            world.getChildren().add(potion);
        }
    }

    private boolean timingCondition(double timeSlices, double momentsSum, double nextMoment) {
        return (timeSlices - momentsSum) >= nextMoment;
    }

    public void generateItems(double timeSlice) {
        generateNextHeart(timeSlice);
        generateNextShield(timeSlice);
        generateNextPotion(timeSlice);
    }

    public void cleanExpiredItems() {
        LifeBooster.cleanExpired();
        Shield.cleanExpired();
        Potion.cleanExpired();
    }
}
