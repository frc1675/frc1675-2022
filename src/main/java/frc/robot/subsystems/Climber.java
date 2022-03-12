// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Climber extends SubsystemBase {
  private CANSparkMax climberMotor1 = new CANSparkMax(Constants.CLIMBER_MOTOR_1, MotorType.kBrushless);
  private CANSparkMax climberMotor2 = new CANSparkMax(Constants.CLIMBER_MOTOR_2, MotorType.kBrushless);
  private Solenoid climberSolenoid = new Solenoid(PneumaticsModuleType.REVPH, Constants.CLIMBER_SOLENOID);
  private RelativeEncoder encoder1 = climberMotor1.getEncoder();
  private RelativeEncoder encoder2 = climberMotor2.getEncoder();
  private DigitalInput limitSwitch = new DigitalInput(Constants.CLIMBER_LIMIT_SWITCH);

  private boolean isExtended = false;
  private boolean motorHitEncoderLimit = true;
  private boolean switchPressed = true;
  private boolean enforceLimit = true;
  private Timer timer = new Timer();

  private ShuffleboardTab climberTab = Shuffleboard.getTab("Climber");
  
  /** Creates a new Climber. */
  public Climber() {
    climberSolenoid.set(false);
    encoder1.setPosition(0);
    encoder2.setPosition(0);
    climberMotor1.setInverted(true);
    climberMotor2.setInverted(true);

    climberTab.addBoolean("Extended?", () -> isExtended)
    .withSize(1, 1)
    .withPosition(0, 0);
    climberTab.addNumber("Average climber position", () -> averageEncoderPosition())
    .withSize(2, 1)
    .withPosition(1, 0);
  }

  public void release() {
    climberSolenoid.set(true);
    timer.reset();
    timer.start();
  }

  public void reengage(){
    climberSolenoid.set(false);
  }

  public void pullUp() {
    if ((isExtended && !motorHitEncoderLimit) && !switchPressed) {
        //climberMotor1.set(Constants.CLIMBER_POWER);
        climberMotor2.set(Constants.CLIMBER_POWER);
    }else if(!enforceLimit) {
      climberMotor2.set(Constants.CLIMBER_POWER);
    }
  }

  public void stop() {
    climberMotor1.set(0);
    climberMotor2.set(0);
  }

  public boolean getIsExtended() {
    return isExtended;
  }

  public double averageEncoderPosition(){
    return (encoder1.getPosition() + encoder2.getPosition()) / 2;
  }

  public void toggleEnforceLimit(){
    enforceLimit = !enforceLimit;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    if(timer.hasElapsed(Constants.CLIMBER_WAIT_TIME)){
      isExtended = true;
      motorHitEncoderLimit = false;
      encoder1.setPosition(0);
      encoder2.setPosition(0);
      timer.stop();
      timer.reset();
    }

    if(isExtended && (averageEncoderPosition() > Constants.CLIMBER_MAX_RETRACT)){
      climberMotor1.set(0);
      climberMotor2.set(0);
      isExtended = false;
      motorHitEncoderLimit = true;
      encoder1.setPosition(0);
      encoder2.setPosition(0);
    }

    switchPressed = limitSwitch.get();
  }
}
