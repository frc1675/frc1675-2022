// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;
import frc.robot.Constants;
import frc.robot.subsystems.Drive;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;

/** An example command that uses an example subsystem. */
public class CheesyDriveSlow extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Drive drive;
  private final DoubleSupplier forwardPower;
  private final DoubleSupplier turnPower;

  public CheesyDriveSlow(Drive Drive, DoubleSupplier forwardPower, DoubleSupplier turnPower) {
    drive = Drive;
    this.forwardPower = forwardPower;
    this.turnPower = turnPower;
    addRequirements(drive);

  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {

    double rightPower = forwardPower.getAsDouble() + -1 * turnPower.getAsDouble();
    double leftPower =  forwardPower.getAsDouble() +  turnPower.getAsDouble();

    rightPower = rightPower * Constants.CLIMBER_DRIVE_MULTIPLIER;
    leftPower = leftPower * Constants.CLIMBER_DRIVE_MULTIPLIER;

    drive.setRight(rightPower);
    drive.setLeft(leftPower);
  }

  @Override
  public void end(boolean interrupted) {
    drive.setLeft(0);
    drive.setRight(0);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
