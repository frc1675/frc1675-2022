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

public class FireBothCatapultsSafe extends SequentialCommandGroup {
  public FireBothCatapultsSafe(Catapult rightCatapult, Catapult leftCatapult) {
    addCommands(
      new FireSingleCatapult(rightCatapult),
      new WaitCommand(Constants.CATAPULT_WAIT_BETWEEN_TIME),
      new FireSingleCatapult(leftCatapult),

      new WaitUntilCommand(()-> {return rightCatapult.isExtended() && leftCatapult.isExtended(); }),
      new WaitCommand(Constants.CATAPULT_FOLLOW_THROUGH_TIME),
      //wait an additional x amount of seconds to provide enough
      //follow through for balls to get good launch. Set to zero
      //in Constants if this behavior is not desired.

      new RetractSingleCatapult(rightCatapult),
      new RetractSingleCatapult(leftCatapult),

      new WaitUntilCommand(()-> {return !rightCatapult.isExtended() && !leftCatapult.isExtended();} )
      
    );
  }
}
