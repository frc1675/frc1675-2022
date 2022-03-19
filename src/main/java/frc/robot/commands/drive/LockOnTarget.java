// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.drive;

import java.util.function.DoubleSupplier;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.Constants;
import frc.robot.subsystems.Drive;
import frc.robot.utils.Vision;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class LockOnTarget extends PIDCommand {
  private Drive drive;
  
  /** Creates a new LockOnTarget. */
  public LockOnTarget(Drive drive, DoubleSupplier forwardPower, DoubleSupplier turnPower) {
    super(
      // The controller that the command will use
      new PIDController(Constants.LOCK_ON_P, 0, Constants.LOCK_ON_D),
      // This should return the measurement
      Vision::rollingAverageHorizOffset,
      // This should return the setpoint (can also be a constant)
      () -> 0,
      // This uses the output
      output -> {
        //identical to cheesy drive logic, except for the turn power
        double readTurnPower;
        if (Vision.isTargetValid()) {
          if (output > Constants.LOCK_ON_TARGET_MAX_SPEED) {
            readTurnPower = -1 * Constants.LOCK_ON_TARGET_MAX_SPEED;
          } else if (output < -1 * Constants.LOCK_ON_TARGET_MAX_SPEED) {
            readTurnPower = Constants.LOCK_ON_TARGET_MAX_SPEED;
          } else {
            readTurnPower = -1 * output;
          }
        } else {
          readTurnPower = turnPower.getAsDouble();
        }

        double readForwardPower = forwardPower.getAsDouble();

        double scaledForwardPower = Math.pow(Math.abs(readForwardPower), Constants.SCALER_EXPONENT);
        double scaledTurnPower = Math.pow(Math.abs(readTurnPower), Constants.SCALER_EXPONENT);

        scaledForwardPower *= Math.signum(readForwardPower);
        scaledTurnPower *= Math.signum(readTurnPower);

        scaledTurnPower *= Constants.TURN_SCALER;

        double rightPower = scaledForwardPower + -1 * scaledTurnPower;
        double leftPower =  scaledForwardPower +  scaledTurnPower;

        double scaler;
        if (rightPower > 1 || leftPower > 1) {
          scaler = Math.max(rightPower, leftPower);
          rightPower /= scaler;
          leftPower /= scaler;
        }

        drive.setRight(rightPower);
        drive.setLeft(leftPower);
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
    getController().reset();
    Vision.setPipelineVision();
  }

  @Override
  public void end(boolean interrupted) {
    super.end(interrupted);
    drive.setRight(0);
    drive.setLeft(0);
    Vision.setPipelineDriver();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
