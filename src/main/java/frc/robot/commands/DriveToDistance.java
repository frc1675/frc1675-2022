// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive;

public class DriveToDistance extends CommandBase {  
  private Drive drive;
  private double inches;
  private double maxSpeed;
  private int count;

  private ShuffleboardTab autoTab = Shuffleboard.getTab("Choose auto routine");

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
  public void execute() {
    if (drive.rightAtTargetPosition() && drive.leftAtTargetPosition()) {
      count++;
    } else {
      count = 0;
    }

    autoTab.addNumber("Right encoder value", () -> drive.getRightEncoderValue());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return count >= 20;
  }
}
