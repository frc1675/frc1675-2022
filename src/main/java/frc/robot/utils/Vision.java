package frc.robot.utils;

import edu.wpi.first.networktables.NetworkTableInstance;

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
}
