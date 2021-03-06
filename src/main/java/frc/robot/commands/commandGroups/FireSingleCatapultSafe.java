// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.commandGroups;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.Constants;
import frc.robot.commands.catapult.FireSingleCatapult;
import frc.robot.commands.catapult.RetractSingleCatapult;
import frc.robot.subsystems.Catapult;

public class FireSingleCatapultSafe extends SequentialCommandGroup {
  public FireSingleCatapultSafe(Catapult catapult) {
    addCommands(
      new FireSingleCatapult(catapult),

      new WaitUntilCommand(()-> catapult.isExtended()),
      new WaitCommand(Constants.CATAPULT_FOLLOW_THROUGH_TIME),
      new RetractSingleCatapult(catapult)
      
    );
  }
}
