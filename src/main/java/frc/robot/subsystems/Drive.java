// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.REVPhysicsSim;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.utils.Vision;

public class Drive extends SubsystemBase {
  /** Creates a new Drive subsystem. */
  private CANSparkMax rightMain = new CANSparkMax(Constants.RIGHT_FRONT, MotorType.kBrushless);
  private CANSparkMax rightFollower = new CANSparkMax(Constants.RIGHT_BACK, MotorType.kBrushless);
  private CANSparkMax leftMain = new CANSparkMax(Constants.LEFT_FRONT, MotorType.kBrushless);
  private CANSparkMax leftFollower = new CANSparkMax(Constants.LEFT_BACK, MotorType.kBrushless);

  private AHRS navx = new AHRS(I2C.Port.kMXP);

  private RelativeEncoder rightEncoder = rightMain.getEncoder();
  private RelativeEncoder leftEncoder = leftMain.getEncoder();

  private SparkMaxPIDController rightPIDController = rightMain.getPIDController();
  private SparkMaxPIDController leftPIDController = leftMain.getPIDController();

  private ShuffleboardTab driveTab = Shuffleboard.getTab("Drivetrain info");

  public Drive() {
    rightFollower.follow(rightMain);
    leftFollower.follow(leftMain);

    leftMain.setInverted(false);
    rightMain.setInverted(true);

    if(Robot.isSimulation()){
      REVPhysicsSim.getInstance().addSparkMax(rightMain, DCMotor.getNEO(1));
      REVPhysicsSim.getInstance().addSparkMax(rightFollower, DCMotor.getNEO(1));
      REVPhysicsSim.getInstance().addSparkMax(leftMain, DCMotor.getNEO(1));
      REVPhysicsSim.getInstance().addSparkMax(leftFollower, DCMotor.getNEO(1));
    }

    rightPIDController.setP(Constants.DRIVE_POSITION_P, Constants.POSITION_PID_SLOT);
    rightPIDController.setI(0, Constants.POSITION_PID_SLOT);
    rightPIDController.setD(Constants.DRIVE_POSITION_D, Constants.POSITION_PID_SLOT);
    rightPIDController.setIZone(0, Constants.POSITION_PID_SLOT);
    rightPIDController.setFF(0, Constants.POSITION_PID_SLOT);

    leftPIDController.setP(Constants.DRIVE_POSITION_P, Constants.POSITION_PID_SLOT);
    leftPIDController.setI(0, Constants.POSITION_PID_SLOT);
    leftPIDController.setD(Constants.DRIVE_POSITION_D, Constants.POSITION_PID_SLOT);
    leftPIDController.setIZone(0, Constants.POSITION_PID_SLOT);
    leftPIDController.setFF(0, Constants.POSITION_PID_SLOT);

    rightPIDController.setP(Constants.DRIVE_VELOCITY_P, Constants.VELOCITY_PID_SLOT);
    rightPIDController.setI(0, Constants.VELOCITY_PID_SLOT);
    rightPIDController.setD(0, Constants.VELOCITY_PID_SLOT);
    rightPIDController.setIZone(0, Constants.VELOCITY_PID_SLOT);
    rightPIDController.setFF(Constants.DRIVE_VELOCITY_FF, Constants.VELOCITY_PID_SLOT);
    rightPIDController.setOutputRange(-1 * Constants.DRIVE_MAX_ACCELERATION, Constants.DRIVE_MAX_ACCELERATION, Constants.VELOCITY_PID_SLOT);

    leftPIDController.setP(Constants.DRIVE_VELOCITY_P, Constants.VELOCITY_PID_SLOT);
    leftPIDController.setI(0, Constants.VELOCITY_PID_SLOT);
    leftPIDController.setD(0, Constants.VELOCITY_PID_SLOT);
    leftPIDController.setIZone(0, Constants.VELOCITY_PID_SLOT);
    leftPIDController.setFF(Constants.DRIVE_VELOCITY_FF, Constants.VELOCITY_PID_SLOT);
    leftPIDController.setOutputRange(-1 * Constants.DRIVE_MAX_ACCELERATION, Constants.DRIVE_MAX_ACCELERATION, Constants.VELOCITY_PID_SLOT);
    
    driveTab.addNumber("Right position", () -> rightEncoder.getPosition())
    .withSize(1, 1)
    .withPosition(0, 0);

    driveTab.addNumber("Left position", () -> leftEncoder.getPosition())
    .withSize(1, 1)
    .withPosition(0, 1);

    driveTab.addNumber("Right position inches", () -> {return rightEncoder.getPosition() / Constants.ROTATIONS_PER_INCH;})
    .withSize(2, 1)
    .withPosition(1, 0);

    driveTab.addNumber("Left position inches", () -> {return leftEncoder.getPosition() / Constants.ROTATIONS_PER_INCH;})
    .withSize(2, 1)
    .withPosition(1, 1);

    driveTab.addNumber("Heading", () -> getHeading())
    .withSize(1, 1)
    .withPosition(0, 2);
    
    /*driveTab.addBoolean("OK to shoot?", () ->
      Vision.isTargetValid()
      && Math.abs(Vision.horizOffset()) <= Constants.ANGLE_TOLERANCE
      && Vision.getDistanceFromSize() >= Constants.MIN_SCORING_DISTANCE
      && Vision.getDistanceFromSize() <= Constants.MAX_SCORING_DISTANCE
    )
    .withSize(1, 1)
    .withPosition(0, 4);*/
/*
    driveTab.addBoolean("Lock On Target status", () -> this.getCurrentCommand().getName().equals("LockOnTarget"))
    .withSize(1, 1)
    .withPosition(1, 4);
*/
  }

  public void setRight(double speed){
    //setVoltage for simulation support
    rightMain.set(speed);
  }

  public void setLeft(double speed){
    leftMain.set(speed);
  }

  //using pid
  public void setRightVelocityPID(double targetVelocity){
    rightPIDController.setReference(targetVelocity, CANSparkMax.ControlType.kVelocity, Constants.VELOCITY_PID_SLOT);
  }

  public void setLeftVelocityPID(double targetVelocity){
    leftPIDController.setReference(targetVelocity, CANSparkMax.ControlType.kVelocity, Constants.VELOCITY_PID_SLOT);
  }

  public void setRightPositionPID(double inches, double maxSpeed) {
    rightPIDController.setOutputRange(-1 * maxSpeed, maxSpeed, Constants.POSITION_PID_SLOT);
    rightPIDController.setReference(inches * Constants.ROTATIONS_PER_INCH, CANSparkMax.ControlType.kPosition, Constants.POSITION_PID_SLOT);
  }

  public void setLeftPositionPID(double inches, double maxSpeed) {
    leftPIDController.setOutputRange(-1 * maxSpeed, maxSpeed, Constants.POSITION_PID_SLOT);
    leftPIDController.setReference(inches * Constants.ROTATIONS_PER_INCH, CANSparkMax.ControlType.kPosition, Constants.POSITION_PID_SLOT);
  }

  public void resetEncoders() {
    rightEncoder.setPosition(0);
    leftEncoder.setPosition(0);
  }

  public double getRightPosition() {
    return rightEncoder.getPosition();
  }

  public double getLeftPosition() {
    return leftEncoder.getPosition();
  }

  public double getAveragePositionInches() {
    return (rightEncoder.getPosition() + leftEncoder.getPosition()) / 2 / Constants.ROTATIONS_PER_INCH;
  }

  public void resetAngle() {
    navx.reset();
  }
  
  public double getHeading() {
    return navx.getAngle() % 360;
  }

  @Override
  public void periodic() {}

  @Override
  public void simulationPeriodic() {}
}
