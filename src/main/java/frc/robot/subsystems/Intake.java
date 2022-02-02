// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;


public class Intake extends SubsystemBase {
  private CANSparkMax intakeMotor = new CANSparkMax(Constants.INTAKE_MOTOR , MotorType.kBrushless);
  private Solenoid rightSolenoid = new Solenoid(PneumaticsModuleType.REVPH, Constants.INTAKE_RIGHT_SOLENOID);
  private Solenoid leftSolenoid = new Solenoid(PneumaticsModuleType.REVPH, Constants.INTAKE_LEFT_SOLENOID);
  private static boolean intakeExtend = false;
 
  public Intake() {
    
  }

  public void setIntakeSpeed(double speed){
    intakeMotor.set(speed);
  }

  public void extendIntake(){
    rightSolenoid.set(true);
    leftSolenoid.set(true);
    intakeExtend = true;
  }

  public void retractIntake(){
    rightSolenoid.set(false);
    leftSolenoid.set(false);
    intakeExtend = false;
  }

  public static boolean isExtended(){
    return intakeExtend;
  }

  @Override
  public void periodic() {
    
  }

  @Override
  public void simulationPeriodic() {
    
  }
}
