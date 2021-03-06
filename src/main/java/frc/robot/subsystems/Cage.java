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


public class Cage extends SubsystemBase {
  /** Creates a new Catapult subsystem. */
  private Solenoid sol = new Solenoid(PneumaticsModuleType.REVPH, Constants.CAGE_SOLENOID);
  private ShuffleboardTab cageTab = Shuffleboard.getTab("Intake");
  private Timer timer = new Timer();
  private boolean closed = false;
 
  public Cage() {
    cageTab.addBoolean("Cage Closed?", () -> isClosed() );
    
  }

  public void close(){
    sol.set(true);
    closed = true;
    timer.stop();
    timer.reset();

  }

  public void open(){
    sol.set(false);
    timer.start();
    }

  public boolean isClosed(){
    return closed;
  }

  @Override
  public void periodic() {
    if(timer.hasElapsed(Constants.CAGE_WAIT_TIME)){
      closed = false;
      timer.stop();
      timer.reset();

    }

    
  }

  @Override
  public void simulationPeriodic() {
    
  }
}
