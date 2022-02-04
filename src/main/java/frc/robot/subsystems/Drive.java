// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.REVPhysicsSim;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Robot;

public class Drive extends SubsystemBase {
  /** Creates a new Drive subsystem. */
  private CANSparkMax rightMain = new CANSparkMax(Constants.RIGHT_FRONT, MotorType.kBrushless);
  private CANSparkMax rightFollower = new CANSparkMax(Constants.RIGHT_BACK, MotorType.kBrushless);
  private CANSparkMax leftMain = new CANSparkMax(Constants.LEFT_FRONT, MotorType.kBrushless);
  private CANSparkMax leftFollower = new CANSparkMax(Constants.LEFT_BACK, MotorType.kBrushless);

  private RelativeEncoder rightEncoder = rightMain.getEncoder();
  private RelativeEncoder leftEncoder = leftMain.getEncoder();

  private SparkMaxPIDController rightPIDController = rightMain.getPIDController();
  private SparkMaxPIDController leftPIDController = leftMain.getPIDController();

  private ShuffleboardTab driveTab = Shuffleboard.getTab("Drivetrain info");

  private double targetRightPosition;
  private double targetLeftPosition;

  private boolean leftInverted = true;
  private boolean rightInverted = false;


  public Drive() {
    rightFollower.follow(rightMain);
    leftFollower.follow(leftMain);

    leftMain.setInverted(leftInverted);
    rightMain.setInverted(rightInverted);

    //DifferentialDriveOdometry m_odometry = new DifferentialDriveOdometry(m_gyro.getGyroHeading(), new Pose2d(5.0, 13.5, new Rotation2d()));
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

    driveTab.addNumber("Right encoder value", () -> rightEncoder.getPosition());
    driveTab.addNumber("Left encoder value", () -> leftEncoder.getPosition());
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
    rightPIDController.setReference(targetVelocity, CANSparkMax.ControlType.kVelocity, 0);
  }

  public void setLeftVelocityPID(double targetVelocity){
    leftPIDController.setReference(targetVelocity, CANSparkMax.ControlType.kVelocity, 0);
  }

  public void setRightPositionPID(double inches, double maxSpeed) {
    rightPIDController.setOutputRange(-1 * maxSpeed, maxSpeed, 0);
    rightPIDController.setReference(inches * Constants.ROTATIONS_PER_INCH, CANSparkMax.ControlType.kPosition, 1);

    targetRightPosition = inches * Constants.ROTATIONS_PER_INCH;
  }

  public void setLeftPositionPID(double inches, double maxSpeed) {
    leftPIDController.setOutputRange(-1 * maxSpeed, maxSpeed, 0);
    leftPIDController.setReference(inches * Constants.ROTATIONS_PER_INCH, CANSparkMax.ControlType.kPosition, 1);

    targetLeftPosition = inches * Constants.ROTATIONS_PER_INCH;
  }

  public void resetEncoders() {
    rightEncoder.setPosition(0);
    leftEncoder.setPosition(0);
  }

  public double getRightEncoderValue() {
    return rightEncoder.getPosition();
  }

  public double getLeftEncoderValue() {
    return leftEncoder.getPosition();
  }

  public boolean rightAtTargetPosition() {
    return Math.abs(rightEncoder.getPosition() - targetRightPosition) / Constants.ROTATIONS_PER_INCH < Constants.DRIVE_TOLERANCE;
  }

  public boolean leftAtTargetPosition() {
    return Math.abs(leftEncoder.getPosition() - targetLeftPosition) / Constants.ROTATIONS_PER_INCH < Constants.DRIVE_TOLERANCE;
  }

//switching the front
public void invertFront(){
  rightInverted = !rightInverted;
  leftInverted = !leftInverted;
  leftMain.setInverted(leftInverted);
  rightMain.setInverted(rightInverted);
  SmartDashboard.putBoolean("Drive Front Intake", rightInverted);
}

  @Override
  public void periodic() {}

  @Override
  public void simulationPeriodic() {}
}
