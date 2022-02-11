// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.subsystems.Intake;

public class ExtendRunIntake extends SequentialCommandGroup {
  public ExtendRunIntake(Intake intake, double intakeSpeed) {
    addCommands(

      new ConditionalCommand(
          new PrintCommand("Intake is already extended."),
          new ExtendIntake(intake).withTimeout(Constants.INTAKE_WAIT_TIME),
          () -> intake.isExtended()
        ),
      new SetIntakeSpeed(intake, intakeSpeed)
    );
  }
}
