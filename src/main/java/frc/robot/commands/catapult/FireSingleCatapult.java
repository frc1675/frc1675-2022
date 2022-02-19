// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.catapult;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Catapult;

public class FireSingleCatapult extends CommandBase {
  private final Catapult catapult;

  /** Creates a new FireCatapultLeft. Make sure to pass the correct subsystem*/
  public FireSingleCatapult(Catapult catapult) {
    // make sure to 

    this.catapult = catapult;
    addRequirements(this.catapult);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    catapult.fire();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}
