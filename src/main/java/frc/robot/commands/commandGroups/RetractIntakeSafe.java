// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.commandGroups;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.commands.cage.OpenCage;
import frc.robot.commands.catapult.RetractSingleCatapult;
import frc.robot.commands.intake.RetractIntake;
import frc.robot.subsystems.Cage;
import frc.robot.subsystems.Catapult;
import frc.robot.subsystems.Intake;

public class RetractIntakeSafe extends SequentialCommandGroup {
  public RetractIntakeSafe(Intake intake, Cage cage, Catapult rightCatapult, Catapult leftCatapult) {
    addCommands(
      new OpenCage(cage),
      new WaitUntilCommand(()-> {return !cage.isClosed();}),

      new RetractSingleCatapult(rightCatapult),
      new RetractSingleCatapult(leftCatapult),
      new WaitUntilCommand(()-> {return !rightCatapult.isExtended() && !leftCatapult.isExtended();}),

      new RetractIntake(intake),
      new WaitUntilCommand(()-> {return !intake.isExtended();})
      );
  }
}
