// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.commandGroups.ExtendThenRunIntake;
import frc.robot.commands.commandGroups.FireAnyCatapultsSafe;
import frc.robot.commands.commandGroups.RetractIntakeSafe;
import frc.robot.subsystems.Cage;
import frc.robot.subsystems.Catapult;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Intake;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class Area1GetBall1 extends SequentialCommandGroup {
  /** Creates a new Area1GetBall1. */
  public Area1GetBall1(Drive drive, Intake intake, Cage cage, Catapult catapult) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new TurnToAngleWithTimeout(drive, 28, 1),
      new DriveToDistanceWithTimeout(drive, 37.5, 1),
      new TurnToAngleWithTimeout(drive, 62, 1),
      //ParallelDeadlineGroup ends when the first command
      //ends, and interrupts the others.
      new ParallelDeadlineGroup(
        new DriveToDistanceWithTimeout(drive, 22.5, 1),
        new ExtendThenRunIntake(intake, cage, 1)
      ),
      new RetractIntakeSafe(intake, cage, catapult),
      new DriveToDistanceWithTimeout(drive, -60.75, 1),
      new TurnToAngleWithTimeout(drive, -23, 1),
      new DriveToDistanceWithTimeout(drive, -18, 12),
      new FireAnyCatapultsSafe(intake, cage, catapult, true, true),
      new DriveToDistanceWithTimeout(drive, 63, 1)
    );
  }
}
