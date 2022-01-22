// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxAlternateEncoder;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Climber extends SubsystemBase {
  private CANSparkMax climberMotor1 = new CANSparkMax(Constants.CLIMBER_MOTOR_1, MotorType.kBrushless);
  private CANSparkMax climberMotor2 = new CANSparkMax(Constants.CLIMBER_MOTOR_2, MotorType.kBrushless);
  private Solenoid climberSolenoid = new Solenoid(PneumaticsModuleType.REVPH, Constants.CLIMBER_SOLENOID);
  private RelativeEncoder encoder1 = climberMotor1.getAlternateEncoder(SparkMaxAlternateEncoder.Type.kQuadrature, Constants.CLIMBER_ENCODER_SENSITIVITY);
  private RelativeEncoder encoder2 = climberMotor2.getAlternateEncoder(SparkMaxAlternateEncoder.Type.kQuadrature, Constants.CLIMBER_ENCODER_SENSITIVITY);

  private boolean isExtended = false;

  private ShuffleboardTab climberTab = Shuffleboard.getTab("Climber");
  
  /** Creates a new Climber. */
  public Climber() {
    climberSolenoid.set(false);
    encoder1.setPosition(0);
    encoder2.setPosition(0);

    climberTab.addBoolean("Extended?", () -> isExtended);
    double averageEncoderPosition = (encoder1.getPosition() + encoder2.getPosition()) / 2;
    climberTab.addNumber("Average climber position", () -> averageEncoderPosition);
  }

  public void release() {
    climberSolenoid.set(true);
    isExtended = true;
  }

  public void pullUp() {
    if (isExtended) {
      climberMotor1.set(Constants.CLIMBER_POWER);
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

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}