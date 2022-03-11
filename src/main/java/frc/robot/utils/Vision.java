package frc.robot.utils;

import edu.wpi.first.networktables.NetworkTableInstance;

public class Vision {
    public double isTargetValid(){
        return NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0);
    }
    public double horizOffset(){
        return NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
    }
    public double vertOffset(){
        return NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0);
    }
    public double areaPercent(){
        return NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta").getDouble(0);
    }
}
