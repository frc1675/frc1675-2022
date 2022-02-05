// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.CheesyDrive;
import frc.robot.commands.ExtendIntake;
import frc.robot.commands.FireCatapultRight;
import frc.robot.commands.InvertRobotFront;
import frc.robot.commands.PullUpRobot;
import frc.robot.commands.ReleaseClimber;
import frc.robot.commands.RetractIntake;
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
  private final JoystickButton driverControllerBButton = new JoystickButton(driverController, Constants.B_BUTTON);
  private final JoystickButton driverControllerStartButton = new JoystickButton(driverController, Constants.START_BUTTON);

  //operator controller
  private final Joystick operatorController = new Joystick(Constants.OPERATOR_CONTROLLER);
  private final JoystickButton operatorControllerAButton = new JoystickButton(operatorController, Constants.A_BUTTON);
  private final JoystickButton operatorControllerXButton = new JoystickButton(operatorController, Constants.X_BUTTON);
  private final JoystickButton operatorControllerBButton = new JoystickButton(operatorController, Constants.B_BUTTON);
  private final JoystickButton operatorControllerYButton = new JoystickButton(operatorController, Constants.Y_BUTTON);
  private final JoystickButton operatorControllerRightBumper = new JoystickButton(operatorController, Constants.RIGHT_BUMPER);
  private final JoystickButton operatorControllerLeftBumper = new JoystickButton(operatorController, Constants.LEFT_BUMPER);
  private final JoystickButton operatorControllerBackButton = new JoystickButton(operatorController, Constants.BACK_BUTTON);


  
  


  private final Drive drive = new Drive();
  private final Climber climber = new Climber();
  private final Catapult catapult = new Catapult();
  private final Intake intake = new Intake();

  private double getDriverLeftY(){
    return -1 * DeadzoneCorrection.correctDeadzone(driverController.getRawAxis(Constants.LEFT_Y_AXIS));
  }

  private double getDriverLeftX(){
    return DeadzoneCorrection.correctDeadzone(driverController.getRawAxis(Constants.LEFT_X_AXIS));
  }

  private double getDriverRightY(){
    return -1 * DeadzoneCorrection.correctDeadzone(driverController.getRawAxis(Constants.RIGHT_Y_AXIS));
  }

  private double getDriverRightX(){
    return DeadzoneCorrection.correctDeadzone(driverController.getRawAxis(Constants.RIGHT_X_AXIS));
  }


  private final AutoChooser autoChooser = new AutoChooser(drive);

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
    drive.setDefaultCommand(new CheesyDrive(drive, () -> getDriverLeftY(), () -> getDriverRightX() ));
    //driverControllerStartButton.toggleWhenPressed(new CheesyDrivePID(drive, () -> getDriverLeftY(), () -> getDriverRightX() ));
    driverControllerBButton.toggleWhenPressed(new InvertRobotFront(drive));
    operatorControllerXButton.whenHeld(new PullUpRobot(climber));
    operatorControllerBackButton.and(operatorControllerLeftBumper).and(operatorControllerRightBumper).whenActive(new ReleaseClimber(climber));
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
}
