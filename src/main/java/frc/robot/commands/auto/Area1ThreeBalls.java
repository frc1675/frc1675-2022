// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.commands.commandGroups.ExtendThenRunIntake;
import frc.robot.commands.commandGroups.FireBothCatapultsSafe;
import frc.robot.commands.commandGroups.PrepareCatapultFire;
import frc.robot.commands.commandGroups.RetractIntakeSafe;
import frc.robot.subsystems.Cage;
import frc.robot.subsystems.Catapult;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Intake;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class Area1ThreeBalls extends SequentialCommandGroup {
  /** Creates a new Area1ThreeBalls. */
  public Area1ThreeBalls(Drive drive, Intake intake, Cage cage, Catapult rightCatapult, Catapult leftCatapult) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new PrepareCatapultFire(intake, cage),
            new FireBothCatapultsSafe(rightCatapult, leftCatapult),
      new TurnToAngleWithTimeout(drive, 32, 0.5),
      new ParallelDeadlineGroup(
        new DriveToDistanceWithTimeout(drive, 42, 0.25),
        new ExtendThenRunIntake(intake, cage, rightCatapult, leftCatapult, () -> {return Constants.INTAKE_CONSTANT_SPEED;})
      ),
      new ExtendThenRunIntake(intake, cage, rightCatapult, leftCatapult, () -> {return Constants.INTAKE_CONSTANT_SPEED;})
      .withTimeout(0.5),
      new TurnToAngleWithTimeout(drive, 76.5, 0.5),
      new ParallelDeadlineGroup(
        new DriveToDistanceWithTimeout(drive, 85.5, 0.4),
        new ExtendThenRunIntake(intake, cage, rightCatapult, leftCatapult, () -> {return Constants.INTAKE_CONSTANT_SPEED;})
      ),
      new ExtendThenRunIntake(intake, cage, rightCatapult, leftCatapult, () -> {return Constants.INTAKE_CONSTANT_SPEED;})
      .withTimeout(0.5),
      new ParallelCommandGroup(
        new SequentialCommandGroup(
          new TurnToAngleWithTimeout(drive, -62, 0.5),
          new DriveToDistanceWithTimeout(drive, -48, 0.5)
        ),
        new SequentialCommandGroup(
          new RetractIntakeSafe(intake, cage, rightCatapult, leftCatapult),
          new WaitCommand(1.5),
          new PrepareCatapultFire(intake, cage)
        )
      ),
      new FireBothCatapultsSafe(rightCatapult, leftCatapult)
    );
  }
}
