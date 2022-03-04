// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.commandGroups.FireSingleCatapultSafe;
import frc.robot.commands.commandGroups.PrepareCatapultFire;
import frc.robot.subsystems.Cage;
import frc.robot.subsystems.Catapult;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Intake;

public class ShootInPlace extends SequentialCommandGroup {
  /** Creates a new ShootInPlace. */
  public ShootInPlace(Drive drive, Intake intake, Cage cage, Catapult catapult, double angle) {
    addCommands(
      new TurnToAngleWithTimeout(drive, angle, 0.5),
      new PrepareCatapultFire(intake, cage),
      new FireSingleCatapultSafe(catapult),
      new TurnToAngleWithTimeout(drive, angle * -1, 0.5)
    );
  }
}
