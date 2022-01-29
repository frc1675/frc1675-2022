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
  private Solenoid rightSol = new Solenoid(PneumaticsModuleType.REVPH,Constants.CATAPULT_RIGHT_SOLENOID);
  private Solenoid leftSol = new Solenoid(PneumaticsModuleType.REVPH,Constants.CATAPULT_LEFT_SOLENOID);
 
  public Catapult() {
    
  }

  public void fireRight(){
    rightSol.set(true);
  }

  public void fireLeft(){
    leftSol.set(true);
  }
  
  public void retractRight(){
    rightSol.set(false);
  }

  public void retractLeft(){
    leftSol.set(false);
  }

  @Override
  public void periodic() {
    
  }

  @Override
  public void simulationPeriodic() {
    
  }
}
