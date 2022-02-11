// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.intake.ExtendIntake;
import frc.robot.commands.intake.SetIntakeSpeed;
import frc.robot.subsystems.Catapult;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Intake;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class Area2GetBall2 extends SequentialCommandGroup {
  /** Creates a new Area2GetBall2. */
  public Area2GetBall2(Drive drive, Intake intake, Catapult catapult) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new ExtendIntake(intake),
      new ParallelDeadlineGroup(
          new DriveToDistance(drive, 50, 1).withTimeout(3),
          new SetIntakeSpeed(intake, 1)
      )/*,
      new SafeRetractIntake(intake),
      new TurnToAngle(drive, 180, 1).withTimeout(2),
      new DriveToDistance(drive, 100, 1).withTimeout(3),
      new ParallelCommandGroup(
          new SafeFireCatapultRight(catapult),
          new SafeFireCatapultLeft(catapult)
      )*/
    );
  }
}
