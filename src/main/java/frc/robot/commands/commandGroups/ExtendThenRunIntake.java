// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.commandGroups;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.PerpetualCommand;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.commands.cage.CloseCage;
import frc.robot.commands.intake.ExtendIntake;
import frc.robot.commands.intake.SetIntakeSpeed;
import frc.robot.subsystems.Cage;
import frc.robot.subsystems.Catapult;
import frc.robot.subsystems.Intake;

public class ExtendThenRunIntake extends SequentialCommandGroup {
  public ExtendThenRunIntake(Intake intake, Cage cage, Catapult rightCatapult, Catapult leftCatapult, DoubleSupplier intakeSpeed) {
    addCommands(
      new ConditionalCommand(
        new PrintCommand("Intake is already extended."),
        new ExtendIntake(intake),
        () -> intake.isExtended()
      ),
      new WaitUntilCommand(()-> intake.isExtended()),
      new CloseCage(cage), 
      new WaitUntilCommand(() -> cage.isClosed()),
      new WaitUntilCommand(()-> {return !rightCatapult.isExtended() && !leftCatapult.isExtended(); }),
      new PerpetualCommand(new SetIntakeSpeed(intake, intakeSpeed))
    );
  }
}
