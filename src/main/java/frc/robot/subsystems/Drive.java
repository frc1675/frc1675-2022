// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.motorcontrol.PWMVictorSPX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Drive extends SubsystemBase {
  /** Creates a new Drive subsystem. */
  private MotorController rightFront = new PWMVictorSPX(Constants.RIGHT_FRONT_CHANNEL);
  private MotorController rightBack = new PWMVictorSPX(Constants.RIGHT_BACK_CHANNEL);
  private MotorController leftFront = new PWMVictorSPX(Constants.LEFT_FRONT_CHANNEL);
  private MotorController leftBack = new PWMVictorSPX(Constants.LEFT_BACK_CHANNEL);



  public Drive() {
    rightFront.setInverted(true);
    rightBack.setInverted(true);

    
  }

  public void setRight(double speed){
    rightFront.set(speed);
    rightBack.set(speed);

  }

  public void setLeft(double speed){
    leftFront.set(speed);
    leftBack.set(speed);

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
