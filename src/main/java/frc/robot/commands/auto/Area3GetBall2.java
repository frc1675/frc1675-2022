// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.commandGroups.FireAnyCatapultsSafe;
import frc.robot.commands.commandGroups.RetractIntakeSafe;
import frc.robot.commands.intake.ExtendIntake;
import frc.robot.commands.intake.SetIntakeSpeed;
import frc.robot.subsystems.Cage;
import frc.robot.subsystems.Catapult;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Intake;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class Area3GetBall2 extends SequentialCommandGroup {
  /** Creates a new Area3GetBall2. */
  public Area3GetBall2(Drive drive, Intake intake, Cage cage, Catapult catapult) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new TurnToAngle(drive, 63, 1),
      new ExtendIntake(intake),
      new ParallelDeadlineGroup(
        new DriveToDistance(drive, 70.5, 1),
        new SetIntakeSpeed(intake, 1)
      ),
      new RetractIntakeSafe(intake, cage, catapult),
      new TurnToAngle(drive, -70, 1),
      new DriveToDistance(drive, -87, 1),
      new TurnToAngle(drive, 59, 1),
      new DriveToDistance(drive, -18, 12),
      new FireAnyCatapultsSafe(intake, cage, catapult, true, true),
      new DriveToDistance(drive, 63, 1)
    );
  }
}
