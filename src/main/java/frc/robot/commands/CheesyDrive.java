// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;
import frc.robot.subsystems.Drive;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;

/** An example command that uses an example subsystem. */
public class CheesyDrive extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Drive m_drive;
  private final double forwardPower;
  private final double turnPower;

  public CheesyDrive(Drive Drive, DoubleSupplier leftInputDouble, DoubleSupplier rightInputDouble) {
    m_drive = Drive;
    forwardPower = leftInputDouble.getAsDouble();
    turnPower = rightInputDouble.getAsDouble();
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_drive);

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //cheesy drive

    double rightPower = forwardPower + turnPower;
    double leftPower =  forwardPower + -1 * turnPower;

    m_drive.setRight(rightPower);
    m_drive.setLeft(leftPower);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_drive.setLeft(0);
    m_drive.setRight(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
