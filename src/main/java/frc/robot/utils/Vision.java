package frc.robot.utils;

import edu.wpi.first.networktables.NetworkTableInstance;
import frc.robot.Constants;

public class Vision {
    public static boolean isTargetValid(){
        //convert this to a boolean using if statements or anything else you can think of
        return NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0) == 1;
    }
    public static double horizOffset(){
        return NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
    }
    public static double vertOffset(){
        return NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0);
    }
    public static double areaPercent(){
        return NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta").getDouble(0);
    }

    public static void setCamModeDriver(){
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(1);
    }
    public static void setCamModeVision(){
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(0);
    }

    public static double getDistanceFromSize() {
        double hypotenuse = Math.sqrt(1 / areaPercent()) * Constants.PERCENT_AREA_TO_INCHES;
        double distance = Math.sqrt(Math.pow(hypotenuse, 2) - Math.pow(Constants.HUB_HEIGHT, 2));
        return distance;
    }
}
