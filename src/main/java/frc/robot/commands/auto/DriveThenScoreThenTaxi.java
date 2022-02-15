// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.commandGroups.FireAnyCatapultsSafe;
import frc.robot.subsystems.Cage;
import frc.robot.subsystems.Catapult;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Intake;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class DriveThenScoreThenTaxi extends SequentialCommandGroup {
  /** Creates a new WaitThenScore. */
  public DriveThenScoreThenTaxi(Drive drive, Intake intake, Cage cage, Catapult catapult, double waitTime) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new DriveToDistanceWithTimeout(drive, 117, 1),
      new WaitCommand(waitTime),
      new DriveToDistanceWithTimeout(drive, -117, 1),
      new TurnToAngleWithTimeout(drive, -90, 1),
      new DriveToDistanceWithTimeout(drive, -42, 1),
      new FireAnyCatapultsSafe(intake, cage, catapult, true, true),
      new DriveToDistanceWithTimeout(drive, 62, 1)
    );
  }
}
