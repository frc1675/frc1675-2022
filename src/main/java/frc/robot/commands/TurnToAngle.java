// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.Constants;
import frc.robot.subsystems.Drive;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class TurnToAngle extends PIDCommand {
  private Drive drive;
  private int count = 0;

  /** Creates a new TurnToAngle. */
  public TurnToAngle(Drive drive, double angle, double maxSpeed) {
    super(
      // The controller that the command will use
      new PIDController(Constants.ANGLE_P, 0, Constants.ANGLE_D),
      // This should return the measurement
      drive::getHeading,
      // This should return the setpoint (can also be a constant)
      () -> angle,
      // This uses the output
      output -> {
        // Use the output here
        double power;
        if (output > maxSpeed) {
          power = maxSpeed;
        } else if (output < -1 * maxSpeed) {
          power = -1 * maxSpeed;
        } else {
          power = output;
        }

        drive.setRight(-1 * power);
        drive.setLeft(power);
    });
    this.drive = drive;
    addRequirements(this.drive);
    // Use addRequirements() here to declare subsystem dependencies.
    // Configure additional PID options by calling `getController` here.
    getController().enableContinuousInput(-180, 180);
    getController().setTolerance(Constants.ANGLE_TOLERANCE);
  }
  
  @Override
  public void initialize() {
    drive.resetAngle();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (getController().atSetpoint()) {
      count++;
    } else {
      count = 0;
    }

    return count >= 20;
  }
}
