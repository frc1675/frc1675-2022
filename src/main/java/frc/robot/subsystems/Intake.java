// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;


public class Intake extends SubsystemBase {
  private CANSparkMax intakeMotor = new CANSparkMax(Constants.INTAKE_MOTOR , MotorType.kBrushless);
  private Solenoid sol = new Solenoid(PneumaticsModuleType.REVPH, Constants.INTAKE_SOLENOID);
  private boolean intakeExtended = false;
  private double currentSpeed = 0;

  private ShuffleboardTab IntakeTab = Shuffleboard.getTab("Intake");

  private Timer timer = new Timer();
 
  public Intake() {
    IntakeTab.addBoolean("Intake Extended?", () -> isExtended());
    IntakeTab.addNumber("Intake Speed", ()-> motorSpeed());
    
  }

  public void setIntakeSpeed(double speed){
    intakeMotor.set(speed * -1);
    currentSpeed = speed;
  }

  public void extendIntake(){
    sol.set(true);
    timer.reset();
    timer.start();
  }

  public void retractIntake(){
    sol.set(false);
    intakeExtended = false;
    timer.stop();
    timer.reset();
  }

  public boolean isExtended(){
    return intakeExtended;
  }

  public double motorSpeed(){
    return currentSpeed;
  }

  @Override
  public void periodic() {
    if(timer.hasElapsed(Constants.INTAKE_WAIT_TIME)){
      intakeExtended = true;
    }
    
  }

  @Override
  public void simulationPeriodic() {
    
  }
}
