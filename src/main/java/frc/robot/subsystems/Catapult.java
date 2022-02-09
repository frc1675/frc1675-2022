// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;


public class Catapult extends SubsystemBase {
  /** Creates a new Catapult subsystem. */
  private Solenoid rightSol1 = new Solenoid(PneumaticsModuleType.REVPH,Constants.CATAPULT_RIGHT_SOLENOID_1);
  private Solenoid rightSol2 = new Solenoid(PneumaticsModuleType.REVPH,Constants.CATAPULT_RIGHT_SOLENOID_2);
  private Solenoid leftSol1 = new Solenoid(PneumaticsModuleType.REVPH,Constants.CATAPULT_LEFT_SOLENOID_1);
  private Solenoid leftSol2 = new Solenoid(PneumaticsModuleType.REVPH,Constants.CATAPULT_LEFT_SOLENOID_2);

  private boolean rightExtended = false;
  private boolean leftExtended = false;

  private ShuffleboardTab catapultTab = Shuffleboard.getTab("Catapult");

  private Timer rightTimer = new Timer();
  private Timer leftTimer = new Timer();
 
  public Catapult() {
    catapultTab.addBoolean("Right Up?", () -> rightIsExtended() );
    catapultTab.addBoolean("Left Up?", () -> leftIsExtended() );
    
  }

  public void fireRight(){
      rightSol1.set(true);
      rightSol2.set(true);
      rightExtended = true;
      rightTimer.stop();
      rightTimer.reset();
  }
 
  public void fireLeft(){
      leftSol1.set(true);
      leftSol2.set(true);
      leftExtended = true;
      leftTimer.stop();
      leftTimer.reset();
    }
  
  public void retractRight(){
      rightSol1.set(false);
      rightSol2.set(false);
      rightTimer.start();
    }

  public void retractLeft(){
      leftSol1.set(false);
      leftSol2.set(false);
      leftTimer.start();
    }

  public boolean rightIsExtended(){
    return rightExtended;
  }

  public boolean leftIsExtended(){
    return leftExtended;
  }

  public boolean isExtended(){
    if(rightExtended || leftExtended){
      return false;
    }
    return true;
  }

  @Override
  public void periodic() {
    if(rightTimer.hasElapsed(Constants.CATAPULT_WAIT_TIME)){
      rightExtended = false;
      rightTimer.stop();
      rightTimer.reset();
    }

    if(leftTimer.hasElapsed(Constants.CATAPULT_WAIT_TIME)){
      leftExtended = false;
      leftTimer.stop();
      leftTimer.reset();
    }
    
  }

  @Override
  public void simulationPeriodic() {
    
  }
}
