// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.climber.ToggleClimberLimitEnforce;
import frc.robot.commands.commandGroups.ClimberPullUpSafe;
import frc.robot.commands.commandGroups.ClimberReleaseSafe;
import frc.robot.commands.commandGroups.ExtendThenRunIntake;
import frc.robot.commands.commandGroups.FireSingleCatapultSafe;
import frc.robot.commands.commandGroups.PrepareCatapultFire;
import frc.robot.commands.commandGroups.RetractIntakeSafe;
import frc.robot.commands.drive.CheesyDrive;
import frc.robot.commands.drive.LockOnTarget;
//import frc.robot.commands.drive.CheesyDrivePID;
import frc.robot.subsystems.Cage;
import frc.robot.subsystems.Catapult;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Intake;
import frc.robot.utils.AutoChooser;
import frc.robot.utils.DeadzoneCorrection;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  //driver controller
  private final Joystick driverController = new Joystick(Constants.DRIVER_CONTROLLER);
  private final JoystickButton driverControllerBackButton = new JoystickButton(driverController, Constants.BACK_BUTTON);
  private final JoystickButton driverControllerBButton = new JoystickButton(driverController, Constants.B_BUTTON);
  //private final JoystickButton driverControllerAButton = new JoystickButton(driverController, Constants.A_BUTTON);
  private final JoystickButton driverControllerStartButton = new JoystickButton(driverController, Constants.START_BUTTON);
  private final JoystickButton driverControllerRightBumper = new JoystickButton(driverController, Constants.RIGHT_BUMPER);
  private final JoystickButton driverControllerLeftBumper = new JoystickButton(driverController, Constants.LEFT_BUMPER);
  //private final JoystickButton driverControllerXButton = new JoystickButton(driverController, Constants.X_BUTTON);
  private final Trigger driverControllerClimberButtons = driverControllerBackButton.and(driverControllerRightBumper).and(driverControllerLeftBumper);

  //operator controller
  private final Joystick operatorController = new Joystick(Constants.OPERATOR_CONTROLLER);
  private final JoystickButton operatorControllerXButton = new JoystickButton(operatorController, Constants.X_BUTTON);
  private final JoystickButton operatorControllerAButton = new JoystickButton(operatorController, Constants.A_BUTTON);
  private final JoystickButton operatorControllerBButton = new JoystickButton(operatorController, Constants.B_BUTTON);
  private final JoystickButton operatorControllerRightBumper = new JoystickButton(operatorController, Constants.RIGHT_BUMPER);
  private final JoystickButton operatorControllerLeftBumper = new JoystickButton(operatorController, Constants.LEFT_BUMPER);
  private final JoystickButton operatorControllerYButton = new JoystickButton(operatorController, Constants.Y_BUTTON);
  private final JoystickButton operatorControllerStartButton = new JoystickButton(operatorController, Constants.START_BUTTON);

  private final Cage cage = new Cage();
  private final Drive drive = new Drive();
  private final Intake intake = new Intake();
  private final Climber climber = new Climber();
  private final Catapult rightCatapult = new Catapult('r');
  private final Catapult leftCatapult = new Catapult('l');

  private final Joystick adaptiveOperatorController = new Joystick(Constants.ADAPTIVE_OPERATOR_CONTROLLER);
  private final JoystickButton adaptiveOperatorControllerAButton = new JoystickButton(adaptiveOperatorController, Constants.A_BUTTON);
  private final JoystickButton adaptiveOperatorControllerBButton = new JoystickButton(adaptiveOperatorController, Constants.B_BUTTON);
  private final JoystickButton adaptiveOperatorControllerYButton = new JoystickButton(adaptiveOperatorController, Constants.Y_BUTTON);
  

  private boolean isCatapultPrepared(){
    return intake.isExtended() && !cage.isClosed();
  }

  private boolean isCatapultExtended(){
    return rightCatapult.isExtended() || leftCatapult.isExtended();
  }

  private double getDriverLeftY(){
    return -1 * DeadzoneCorrection.correctDeadzone(driverController.getRawAxis(Constants.LEFT_Y_AXIS));
  }
  /*
  private double getDriverLeftX(){
    return DeadzoneCorrection.correctDeadzone(driverController.getRawAxis(Constants.LEFT_X_AXIS));
  }

  private double getDriverRightY(){
    return -1 * DeadzoneCorrection.correctDeadzone(driverController.getRawAxis(Constants.RIGHT_Y_AXIS));
  }
  */

  private double getDriverRightX(){
    return DeadzoneCorrection.correctDeadzone(driverController.getRawAxis(Constants.RIGHT_X_AXIS));
  }
/*
  private double getOperatorLeftY(){
    return -1 * DeadzoneCorrection.correctDeadzone(operatorController.getRawAxis(Constants.LEFT_Y_AXIS));
  }
*/
  private final AutoChooser autoChooser = new AutoChooser(drive, intake, cage, rightCatapult, leftCatapult);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    adaptiveOperatorControllerAButton.onTrue(new ConditionalCommand( new FireSingleCatapultSafe(rightCatapult), new PrintCommand("Catapult not prepared to fire."), () -> isCatapultPrepared() ));
    adaptiveOperatorControllerBButton.onTrue(new PrepareCatapultFire(intake, cage));
    adaptiveOperatorControllerYButton.onTrue(new RetractIntakeSafe(intake, cage, rightCatapult, leftCatapult));

    drive.setDefaultCommand(new CheesyDrive(drive, () -> getDriverLeftY(), () -> getDriverRightX(), 1.0 ));

    //driver controller
    driverControllerClimberButtons.whileTrue(
      new ClimberReleaseSafe(intake, cage, climber, rightCatapult, leftCatapult)
    );
    driverControllerClimberButtons.whileTrue(
    new SequentialCommandGroup(
      new WaitUntilCommand(()-> climber.getIsExtended()),
      new CheesyDrive(drive, ()-> getDriverLeftY(), ()-> getDriverRightX() , Constants.CLIMBER_DRIVE_MULTIPLIER).until(()-> {return !climber.getIsExtended();})
    ));
    
    driverControllerBButton.whileTrue(new ClimberPullUpSafe(climber));
    //driverControllerStartButton.toggleWhenPressed(new CheesyDrivePID(drive, () -> getDriverLeftY(), () -> getDriverRightX() ));
    driverControllerStartButton.onTrue(new ToggleClimberLimitEnforce(climber));
    
    //operator controller
    operatorControllerLeftBumper.onTrue(new ConditionalCommand( new FireSingleCatapultSafe(rightCatapult), new PrintCommand("Catapult not prepared to fire."), () -> isCatapultPrepared() ));
    operatorControllerRightBumper.onTrue(new ConditionalCommand( new FireSingleCatapultSafe(leftCatapult), new PrintCommand("Catapult not prepared to fire."), () -> isCatapultPrepared() ));
    operatorControllerAButton.onTrue(new PrepareCatapultFire(intake, cage));
    operatorControllerXButton.onTrue(new RetractIntakeSafe(intake, cage, rightCatapult, leftCatapult));
    operatorControllerBButton.whileTrue(new ExtendThenRunIntake(intake, cage, rightCatapult, leftCatapult, () -> {return Constants.INTAKE_CONSTANT_SPEED;} ));
    operatorControllerYButton.whileTrue(new ExtendThenRunIntake(intake, cage, rightCatapult, leftCatapult, ()-> {return Constants.INTAKE_CONSTANT_BACKWARD;} ));
    operatorControllerStartButton.toggleOnTrue(
      new LockOnTarget(drive, () -> getDriverLeftY(), () -> getDriverRightX())
      .until(()-> isCatapultExtended()));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // .An ExampleCommand will run in autonomous
    return autoChooser.generateAuto();
  }

  public void checkAutoPath() {
    autoChooser.checkAutoPath();
  }

  public void disablePneumatics() {
    rightCatapult.retract();
    leftCatapult.retract();
    cage.open();
    intake.retractIntake();
    
  }
}
