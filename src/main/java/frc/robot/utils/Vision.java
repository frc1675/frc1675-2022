package frc.robot.utils;

import edu.wpi.first.networktables.NetworkTableInstance;

public class Vision {
    public boolean isTargetValid(){
        //convert this to a boolean using if statements or anything else you can think of
        return NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0) == 1;
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

    public void toggleCamMode(){
        if(NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").getDouble(0)==0){
            NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(1);
        }
        else{
            NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(0);
        }
    }
}
