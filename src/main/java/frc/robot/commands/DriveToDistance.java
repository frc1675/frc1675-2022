// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Drive;

public class DriveToDistance extends CommandBase {  
  private Drive drive;
  private double inches;
  private double maxSpeed;
  private int count = 0;

  /** Creates a new DriveToDistance. */
  public DriveToDistance(Drive drive, double inches, double maxSpeed) {
    this.drive = drive;
    this.inches = inches;
    this.maxSpeed = maxSpeed;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(this.drive);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    drive.resetEncoders();
    drive.setRightPositionPID(inches, maxSpeed);
    drive.setLeftPositionPID(inches, maxSpeed);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (Math.abs(drive.getAveragePositionInches() - inches) < Constants.DRIVE_TOLERANCE) {
      count++;
    } else {
      count = 0;
    }

    return count >= 20;
  }
}
