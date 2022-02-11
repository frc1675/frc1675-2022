// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Catapult;
import frc.robot.subsystems.Drive;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class DriveThenScoreThenTaxi extends SequentialCommandGroup {
  /** Creates a new WaitThenScore. */
  public DriveThenScoreThenTaxi(Drive drive, Catapult catapult) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new DriveToDistance(drive, -20, 0.5).withTimeout(3),
      //new SafeFireCatapultRight(catapult),
      new DriveToDistance(drive, 50, 0.5).withTimeout(3)
    );
  }
}