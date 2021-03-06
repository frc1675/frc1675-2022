// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.drive;
import frc.robot.subsystems.Drive;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

/** An example command that uses an example subsystem. */
public class CheesyDrivePID extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Drive drive;
  private final DoubleSupplier forwardPower;
  private final DoubleSupplier turnPower;

  public CheesyDrivePID(Drive Drive, DoubleSupplier forwardPower, DoubleSupplier turnPower) {
    drive = Drive;
    this.forwardPower = forwardPower;
    this.turnPower = turnPower;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(drive);

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    SmartDashboard.putBoolean("PID Mode?", true);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //cheesy drive

    double rightPower = forwardPower.getAsDouble() + -1 * turnPower.getAsDouble();
    double leftPower = forwardPower.getAsDouble() + turnPower.getAsDouble();

    drive.setRightVelocityPID(rightPower);
    drive.setLeftVelocityPID(leftPower);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    drive.setLeft(0);//I am leaving it at this so that if the command is changed the robot comes to a near instant stop
    drive.setRight(0);
    SmartDashboard.putBoolean("PID Mode?", false);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
