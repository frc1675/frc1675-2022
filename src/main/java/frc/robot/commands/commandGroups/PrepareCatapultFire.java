// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.commandGroups;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.commands.cage.OpenCage;
import frc.robot.commands.intake.ExtendIntake;
import frc.robot.subsystems.Cage;
import frc.robot.subsystems.Intake;

public class PrepareCatapultFire extends SequentialCommandGroup {
  public PrepareCatapultFire(Intake intake, Cage cage) {
    addCommands(
      new ExtendIntake(intake),
      new OpenCage(cage),
      new WaitUntilCommand( ()-> intake.isExtended()),      
      new WaitUntilCommand( ()-> {return !cage.isClosed();})
    );
  }
}
