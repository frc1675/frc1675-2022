// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.commandGroups;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.commands.climber.ReleaseClimber;
import frc.robot.subsystems.Cage;
import frc.robot.subsystems.Catapult;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Intake;

public class ClimberReleaseSafe extends SequentialCommandGroup {
  public ClimberReleaseSafe(Intake intake, Cage cage, Climber climber, Catapult rightCatapult, Catapult lefCatapult) {
    addCommands(
      new RetractIntakeSafe(intake, cage, rightCatapult, lefCatapult),
      new ReleaseClimber(climber),
      new WaitUntilCommand(()-> climber.getIsExtended())

    );
  }
}
