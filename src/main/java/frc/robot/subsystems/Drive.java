// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Drive extends SubsystemBase {
  /** Creates a new Drive subsystem. */
  private MotorController rightFront = new CANSparkMax(Constants.RIGHT_FRONT, MotorType.kBrushless);
  private MotorController rightBack = new CANSparkMax(Constants.RIGHT_BACK, MotorType.kBrushless);
  private MotorController leftFront = new CANSparkMax(Constants.LEFT_FRONT, MotorType.kBrushless);
  private MotorController leftBack = new CANSparkMax(Constants.LEFT_BACK, MotorType.kBrushless);



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
