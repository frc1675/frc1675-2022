// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;


public class Catapult extends SubsystemBase {
  /** Creates a new Catapult subsystem. */
  private Solenoid rightSol1 = new Solenoid(PneumaticsModuleType.REVPH,Constants.CATAPULT_RIGHT_SOLENOID_1);
  private Solenoid rightSol2 = new Solenoid(PneumaticsModuleType.REVPH,Constants.CATAPULT_RIGHT_SOLENOID_2);
  private Solenoid leftSol1 = new Solenoid(PneumaticsModuleType.REVPH,Constants.CATAPULT_LEFT_SOLENOID_1);
  private Solenoid leftSol2 = new Solenoid(PneumaticsModuleType.REVPH,Constants.CATAPULT_LEFT_SOLENOID_2);
 
  public Catapult() {
    
  }

  public void fireRight(){
    rightSol1.set(true);
    rightSol2.set(true);
  }

  public void fireLeft(){
    leftSol1.set(true);
    leftSol2.set(true);
  }
  
  public void retractRight(){
    rightSol1.set(false);
    rightSol2.set(false);
  }

  public void retractLeft(){
    leftSol1.set(false);
    leftSol2.set(false);
  }

  @Override
  public void periodic() {
    
  }

  @Override
  public void simulationPeriodic() {
    
  }
}
