package frc.robot.utils;

import frc.robot.Constants;

public class DeadzoneCorrection {

    public DeadzoneCorrection(){

    }

    public double CorrectDeadzone(double value){
        if(Math.abs(value) < Constants.DEADZONE_CONSTANT){
            return 0.0;
            //value is within deadzone

        }else if(value > 0){
            return (value - Constants.DEADZONE_CONSTANT) / (1 - Constants.DEADZONE_CONSTANT);
            //value is larger than deadzone

        }else{
            return (value + Constants.DEADZONE_CONSTANT) / (1 - Constants.DEADZONE_CONSTANT);
            //value is smaller than deadzone

        }

    }

    
    
}

