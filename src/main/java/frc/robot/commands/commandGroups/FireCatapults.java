// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.commandGroups;

import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.Constants;
import frc.robot.commands.cage.OpenCage;
import frc.robot.commands.catapult.FireCatapultLeft;
import frc.robot.commands.catapult.FireCatapultRight;
import frc.robot.commands.catapult.RetractCatapultLeft;
import frc.robot.commands.catapult.RetractCatapultRight;
import frc.robot.commands.intake.ExtendIntake;
import frc.robot.subsystems.Cage;
import frc.robot.subsystems.Catapult;
import frc.robot.subsystems.Intake;

public class FireCatapults extends SequentialCommandGroup {
  public FireCatapults(Intake intake, Cage cage, Catapult catapult, boolean fireRight, boolean fireLeft) {
    addCommands(
      new ExtendIntake(intake),
      new WaitUntilCommand( ()-> intake.isExtended()),
      
      new OpenCage(cage),
      new WaitUntilCommand( ()-> {return !cage.isClosed();}),

      new ConditionalCommand(new FireCatapultRight(catapult), new PrintCommand("Right catapult not set to fire"), ()-> fireRight),
      new WaitCommand(Constants.CATAPULT_WAIT_BETWEEN_TIME),
      new ConditionalCommand(new FireCatapultLeft(catapult), new PrintCommand("Left catapult not set to fire"), ()-> fireLeft),

      new WaitUntilCommand(()-> catapult.isExtended()),
      new WaitCommand(Constants.CATAPULT_FOLLOW_THROUGH_TIME),
      //wait an additional x amount of seconds to provide enough follow through for balls to get good launch. Set to zero in Constants if this behavior is not desired

      new RetractCatapultRight(catapult),
      new RetractCatapultLeft(catapult),

      new WaitUntilCommand(()-> {return !catapult.isExtended();} )
      
    );
  }
}
