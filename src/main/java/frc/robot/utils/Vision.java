package frc.robot.utils;

import java.util.ArrayList;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.robot.Constants;

public class Vision {
    private static ShuffleboardTab visionTab = Shuffleboard.getTab("Vision");
    private static ArrayList<Double> horizOffsetList = new ArrayList<Double>();
    private static ArrayList<Double> vertOffsetList = new ArrayList<Double>();
    private static ArrayList<Double> areaPercentList = new ArrayList<Double>();

    public static void startShuffleboard() {
        visionTab.addBoolean("Target found?", () -> isTargetValid())
        .withPosition(0, 0);

        visionTab.addNumber("Horizontal angle", () -> horizOffset())
        .withPosition(1, 0);

        visionTab.addNumber("Horizontal angle rolling average", () -> rollingAverageHorizOffset())
        .withPosition(1, 1);

        visionTab.addNumber("Vertical angle", () -> vertOffset())
        .withPosition(2, 0);

        visionTab.addNumber("Vertical angle rolling average", () -> rollingAverageVertOffset())
        .withPosition(2, 1);

        visionTab.addNumber("Area percent of target", () -> areaPercent())
        .withPosition(3, 0);

        visionTab.addNumber("Area percent rolling average", () -> rollingAverageAreaPercent())
        .withPosition(3, 1);
    }

    public static boolean isTargetValid() {
        //convert this to a boolean using if statements or anything else you can think of
        return NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0) == 1.0;
    }

    public static double horizOffset() {
        return NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
    }

    public static double rollingAverageHorizOffset() {
        horizOffsetList.add(horizOffset());
        if (horizOffsetList.size() > Constants.ROLLING_AVERAGE_LOOPS) {
            horizOffsetList.remove(0);
        }

        double sum = 0;
        for (double offset : horizOffsetList) {
            sum += offset;
        }
        return sum / horizOffsetList.size();
    }

    public static double vertOffset() {
        return NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0);
    }

    public static double rollingAverageVertOffset() {
        vertOffsetList.add(vertOffset());
        if (vertOffsetList.size() > Constants.ROLLING_AVERAGE_LOOPS) {
            vertOffsetList.remove(0);
        }

        double sum = 0;
        for (double offset : vertOffsetList) {
            sum += offset;
        }
        return sum / vertOffsetList.size();
    }

    public static double areaPercent() {
        return NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta").getDouble(0);
    }

    public static double rollingAverageAreaPercent() {
        areaPercentList.add(areaPercent());
        if (areaPercentList.size() > Constants.ROLLING_AVERAGE_LOOPS) {
            areaPercentList.remove(0);
        }

        double sum = 0;
        for (double area : areaPercentList) {
            sum += area;
        }
        return sum / areaPercentList.size();
    }

    public static void setPipelineDriver() {
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setNumber(1);
    }

    public static void setPipelineVision() {
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setNumber(0);
    }

    /*public static double getDistanceFromSize() {
        double hypotenuse = Math.sqrt(1 / areaPercent()) * Constants.PERCENT_AREA_TO_INCHES;
        double distance = Math.sqrt(Math.pow(hypotenuse, 2) - Math.pow(Constants.HUB_HEIGHT, 2));
        return distance;
    }*/
}
