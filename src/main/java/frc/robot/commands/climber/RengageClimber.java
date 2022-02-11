// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.climber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climber;

public class RengageClimber extends CommandBase {
  private final Climber climber;

  public RengageClimber(Climber climber) {
    this.climber = climber;
    addRequirements(this.climber);
  }

  @Override
  public void initialize() {
    climber.reengage();
  }

  @Override
  public void execute() {}

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return true;
  }
}
