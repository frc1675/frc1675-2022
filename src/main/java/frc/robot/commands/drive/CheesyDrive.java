// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.drive;
import frc.robot.Constants;
import frc.robot.subsystems.Drive;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;

/** An example command that uses an example subsystem. */
public class CheesyDrive extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Drive drive;
  private final DoubleSupplier forwardPower;
  private final DoubleSupplier turnPower;
  private final Double DriveMultiplier;

  public CheesyDrive(Drive Drive, DoubleSupplier forwardPower, DoubleSupplier turnPower, double DriveMultiplier) {
    drive = Drive;
    this.forwardPower = forwardPower;
    this.turnPower = turnPower;
    this.DriveMultiplier = DriveMultiplier;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(drive);

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //cheesy drive

    double scaledForwardPower = Math.pow(forwardPower.getAsDouble(), 2);
    double scaledTurnPower = Math.pow(turnPower.getAsDouble(), 2);
    double scaler;

    if (forwardPower.getAsDouble() < 0) {
      scaledForwardPower *= -1;
    }

    if (turnPower.getAsDouble() < 0) {
      scaledTurnPower *= -1;
    }

    scaledTurnPower *= Constants.TURN_SCALER;

    double rightPower = scaledForwardPower + -1 * scaledTurnPower;
    double leftPower =  scaledForwardPower +  scaledTurnPower;

    if (rightPower > 1 || leftPower > 1) {
      scaler = Math.max(rightPower, leftPower);
      rightPower /= scaler;
      leftPower /= scaler;
    }

    rightPower *= DriveMultiplier;
    leftPower *= DriveMultiplier;

    drive.setRight(rightPower);
    drive.setLeft(leftPower);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    drive.setLeft(0);
    drive.setRight(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
