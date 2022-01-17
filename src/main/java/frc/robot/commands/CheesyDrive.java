// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.subsystems.Drive;

import org.ejml.equation.ManagerFunctions.Input1;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;

/** An example command that uses an example subsystem. */
public class CheesyDrive extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Drive m_drive;
  private final Joystick m_driverController;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public CheesyDrive(Drive Drive, Joystick input) {
    m_drive = Drive;
    m_driverController = input;
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
    double forwardPower =  m_driverController.getRawAxis(Constants.LEFT_AXIS_CHANNEL);
    double turnPower = m_driverController.getRawAxis(Constants.RIGHT_AXIS_CHANNEL);

    double rightPower = forwardPower + -1 * turnPower;
    double leftPower =  forwardPower + turnPower;

    m_drive.setRight(rightPower);
    m_drive.setLeft(leftPower);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
