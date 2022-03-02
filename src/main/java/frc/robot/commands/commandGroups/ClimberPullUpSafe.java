// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.commandGroups;

import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.climber.PullUpRobot;
import frc.robot.commands.climber.RengageClimber;
import frc.robot.subsystems.Climber;

public class ClimberPullUpSafe extends SequentialCommandGroup {
  public ClimberPullUpSafe(Climber climber) {
    addCommands(
      new RengageClimber(climber),
      new ConditionalCommand(
        new PullUpRobot(climber),
        new PrintCommand("Climber retraction stopped to prevent climber motor overrun."),
        ()-> {return 101 > climber.averageEncoderPosition();}
      )
    );
  }
}
