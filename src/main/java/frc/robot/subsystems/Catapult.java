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
  private Solenoid sol1;
  private Solenoid sol2;

  private boolean extended = false;
  private ShuffleboardTab catapultTab = Shuffleboard.getTab("Catapult");
  private Timer timer = new Timer();
 
  public Catapult(char which) {
    if(which == 'r'){
      sol1 = new Solenoid(PneumaticsModuleType.REVPH,Constants.CATAPULT_RIGHT_SOLENOID_1);
      sol2 = new Solenoid(PneumaticsModuleType.REVPH,Constants.CATAPULT_RIGHT_SOLENOID_2);
      catapultTab.addBoolean("Right Up?", () -> isExtended() );
    }else{
      sol1 = new Solenoid(PneumaticsModuleType.REVPH,Constants.CATAPULT_LEFT_SOLENOID_1);
      sol2 = new Solenoid(PneumaticsModuleType.REVPH,Constants.CATAPULT_LEFT_SOLENOID_2);
      catapultTab.addBoolean("Left Up?", () -> isExtended() );
    }
    
  }

  public void fire(){
      sol1.set(true);
      sol2.set(true);
      extended = true;
      timer.stop();
      timer.reset();
  }
  public void retract(){
      sol1.set(false);
      sol2.set(false);
      timer.start();
    }

  public boolean isExtended(){
    return extended;
  }

  @Override
  public void periodic() {
    if(timer.hasElapsed(Constants.CATAPULT_WAIT_TIME)){
      extended = false;
      timer.stop();
      timer.reset();
    }
    
  }

  @Override
  public void simulationPeriodic() {
    
  }
}
