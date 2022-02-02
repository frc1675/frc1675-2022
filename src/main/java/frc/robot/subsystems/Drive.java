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
    rightPIDController.setReference(targetVelocity, CANSparkMax.ControlType.kVelocity);
  }

  public void setLeftVelocityPID(double targetVelocity){
    leftPIDController.setReference(targetVelocity, CANSparkMax.ControlType.kVelocity);
  }

  public void setRightPositionPID(double inches, double maxSpeed) {
    rightPIDController.setOutputRange(-1 * maxSpeed, maxSpeed);
    rightPIDController.setReference(inches * Constants.ROTATIONS_PER_INCH, CANSparkMax.ControlType.kPosition);

    targetRightPosition = inches * Constants.ROTATIONS_PER_INCH;
  }

  public void setLeftPositionPID(double inches, double maxSpeed) {
    leftPIDController.setOutputRange(-1 * maxSpeed, maxSpeed);
    leftPIDController.setReference(inches * Constants.ROTATIONS_PER_INCH, CANSparkMax.ControlType.kPosition);

    targetLeftPosition = inches * Constants.ROTATIONS_PER_INCH;
  }

  public void setCheesyDrivePID() {
    rightPIDController.setP(Constants.DRIVE_VELOCITY_P);
    rightPIDController.setI(0);
    rightPIDController.setD(0);
    rightPIDController.setIZone(0);
    rightPIDController.setFF(Constants.DRIVE_VELOCITY_FF);
    rightPIDController.setOutputRange(-1 * Constants.DRIVE_MAX_ACCELERATION, Constants.DRIVE_MAX_ACCELERATION);

    leftPIDController.setP(Constants.DRIVE_VELOCITY_P);
    leftPIDController.setI(0);
    leftPIDController.setD(0);
    leftPIDController.setIZone(0);
    leftPIDController.setFF(Constants.DRIVE_VELOCITY_FF);
    leftPIDController.setOutputRange(-1 * Constants.DRIVE_MAX_ACCELERATION, Constants.DRIVE_MAX_ACCELERATION);
  }

  public void setAutonomousPID() {
    rightPIDController.setP(Constants.DRIVE_POSITION_P);
    rightPIDController.setI(0);
    rightPIDController.setD(Constants.DRIVE_POSITION_D);
    rightPIDController.setIZone(0);
    rightPIDController.setFF(0);

    leftPIDController.setP(Constants.DRIVE_POSITION_P);
    leftPIDController.setI(0);
    leftPIDController.setD(Constants.DRIVE_POSITION_D);
    leftPIDController.setIZone(0);
    leftPIDController.setFF(0);
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
  public void periodic() {
    // Update the pose
    //m_pose = m_odometry.update(gyroAngle, m_leftEncoder.getDistance(), m_rightEncoder.getDistance());
    
    //m_odometry.update(m_gyro.getRotation2d(),
    //((Encoder) m_leftEncoder).getDistance(),
    //((Encoder) m_rightEncoder).getDistance());
    //m_field.setRobotPose(m_odometry.getPoseMeters());

    driveTab.addNumber("Right encoder value", () -> rightEncoder.getPosition());
    driveTab.addNumber("Left encoder value", () -> leftEncoder.getPosition());
  }

  @Override
  public void simulationPeriodic() {}
}
