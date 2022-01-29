// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.REVPhysicsSim;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;

/**
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim;
import edu.wpi.first.wpilibj.simulation.EncoderSim;
import edu.wpi.first.wpilibj.simulation.SimDeviceSim;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
*/
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Robot;

public class Drive extends SubsystemBase {
  /** Creates a new Drive subsystem. */
  private CANSparkMax rightFront = new CANSparkMax(Constants.RIGHT_FRONT, MotorType.kBrushless);
  private CANSparkMax rightBack = new CANSparkMax(Constants.RIGHT_BACK, MotorType.kBrushless);
  private CANSparkMax leftFront = new CANSparkMax(Constants.LEFT_FRONT, MotorType.kBrushless);
  private CANSparkMax leftBack = new CANSparkMax(Constants.LEFT_BACK, MotorType.kBrushless);

  private RelativeEncoder rightFrontEncoder = rightFront.getEncoder();
  private RelativeEncoder rightBackEncoder = rightBack.getEncoder();
  private RelativeEncoder leftFrontEncoder = leftFront.getEncoder();
  private RelativeEncoder leftBackEncoder = leftBack.getEncoder();

  private SparkMaxPIDController rightFrontController = rightFront.getPIDController();
  private SparkMaxPIDController rightBackController = rightBack.getPIDController();
  private SparkMaxPIDController leftFrontController = leftFront.getPIDController();
  private SparkMaxPIDController leftBackController = leftBack.getPIDController();

  private double targetRightPosition;
  private double targetLeftPosition;

  /** simulated encoders (INCOMPLETE) - Field2D simulation stuff is incomplete - may not be possible due to the motor controllers being from a third party controller*/
  //private SimDeviceSim leftEncoderSim = new SimDeviceSim(m_leftEncoder);
  //private EncoderSim m_rightEncoderSim = new EncoderSim((Encoder) m_rightEncoder);
  //private Field2d m_field = new Field2d();

  /** simulated drivetrain 
  DifferentialDrivetrainSim m_driveSim = new DifferentialDrivetrainSim(
  DCMotor.getNEO(2),       // 2 NEO motors on each side of the drivetrain.
  8.45,                    //gearing reduction.
  7.5,                     // MOI of 7.5 kg m^2 (from CAD model).
  54.0,                    // The mass
  Units.inchesToMeters(6), // wheel radius.
  0.58,                  // The track width
  null
  );
  */

  public Drive() {
    leftFront.setInverted(true);
    leftBack.setInverted(true);
    rightFront.setInverted(false);
    rightBack.setInverted(false);

    //DifferentialDriveOdometry m_odometry = new DifferentialDriveOdometry(m_gyro.getGyroHeading(), new Pose2d(5.0, 13.5, new Rotation2d()));
    if(Robot.isSimulation()){
      REVPhysicsSim.getInstance().addSparkMax(rightFront, DCMotor.getNEO(1));
      REVPhysicsSim.getInstance().addSparkMax(rightBack, DCMotor.getNEO(1));
      REVPhysicsSim.getInstance().addSparkMax(leftFront, DCMotor.getNEO(1));
      REVPhysicsSim.getInstance().addSparkMax(leftBack, DCMotor.getNEO(1));
    }
  }

  public void setRight(double speed){
    //setVoltage for simulation support
    rightFront.set(speed);
    rightBack.set(speed);
  }

  public void setLeft(double speed){
    leftFront.set(speed);
    leftBack.set(speed);
  }

  //using pid
  public void setRightVelocityPID(double targetVelocity){
    rightFrontController.setReference(targetVelocity, CANSparkMax.ControlType.kVelocity);
    rightBackController.setReference(targetVelocity, CANSparkMax.ControlType.kVelocity);
  }

  public void setLeftVelocityPID(double targetVelocity){
    leftFrontController.setReference(targetVelocity, CANSparkMax.ControlType.kVelocity);
    leftBackController.setReference(targetVelocity, CANSparkMax.ControlType.kVelocity);
  }

  public void setRightPositionPID(double inches, double maxSpeed) {
    rightFrontController.setP(Constants.DRIVE_POSITION_P);
    rightFrontController.setI(0);
    rightFrontController.setD(Constants.DRIVE_POSITION_D);
    rightFrontController.setIZone(0);
    rightFrontController.setFF(0);
    rightFrontController.setOutputRange(-1 * maxSpeed, maxSpeed);
    rightFrontController.setReference(inches * Constants.ROTATIONS_PER_INCH, CANSparkMax.ControlType.kPosition);

    rightBackController.setP(Constants.DRIVE_POSITION_P);
    rightBackController.setI(0);
    rightBackController.setD(Constants.DRIVE_POSITION_D);
    rightBackController.setIZone(0);
    rightBackController.setFF(0);
    rightBackController.setOutputRange(-1 * maxSpeed, maxSpeed);
    rightBackController.setReference(inches * Constants.ROTATIONS_PER_INCH, CANSparkMax.ControlType.kPosition);

    targetRightPosition = inches;
  }

  public void setLeftPositionPID(double inches, double maxSpeed) {
    leftFrontController.setP(Constants.DRIVE_POSITION_P);
    leftFrontController.setI(0);
    leftFrontController.setD(Constants.DRIVE_POSITION_D);
    leftFrontController.setIZone(0);
    leftFrontController.setFF(0);
    leftFrontController.setOutputRange(-1 * maxSpeed, maxSpeed);
    leftFrontController.setReference(inches * Constants.ROTATIONS_PER_INCH, CANSparkMax.ControlType.kPosition);

    leftBackController.setP(Constants.DRIVE_POSITION_P);
    leftBackController.setI(0);
    leftBackController.setD(Constants.DRIVE_POSITION_D);
    leftBackController.setIZone(0);
    leftBackController.setFF(0);
    leftBackController.setOutputRange(-1 * maxSpeed, maxSpeed);
    leftBackController.setReference(inches * Constants.ROTATIONS_PER_INCH, CANSparkMax.ControlType.kPosition);

    targetLeftPosition = inches;
  }

  public void setCheesyDrivePID() {
    rightFrontController.setP(Constants.DRIVE_VELOCITY_P);
    rightFrontController.setI(0);
    rightFrontController.setD(0);
    rightFrontController.setIZone(0);
    rightFrontController.setFF(Constants.DRIVE_VELOCITY_FF);
    rightFrontController.setOutputRange(-1 * Constants.DRIVE_MAX_ACCELERATION, Constants.DRIVE_MAX_ACCELERATION);

    rightBackController.setP(Constants.DRIVE_VELOCITY_P);
    rightBackController.setI(0);
    rightBackController.setD(0);
    rightBackController.setIZone(0);
    rightBackController.setFF(Constants.DRIVE_VELOCITY_FF);
    rightBackController.setOutputRange(-1 * Constants.DRIVE_MAX_ACCELERATION, Constants.DRIVE_MAX_ACCELERATION);

    leftFrontController.setP(Constants.DRIVE_VELOCITY_P);
    leftFrontController.setI(0);
    leftFrontController.setD(0);
    leftFrontController.setIZone(0);
    leftFrontController.setFF(Constants.DRIVE_VELOCITY_FF);
    leftFrontController.setOutputRange(-1 * Constants.DRIVE_MAX_ACCELERATION, Constants.DRIVE_MAX_ACCELERATION);

    leftBackController.setP(Constants.DRIVE_VELOCITY_P);
    leftBackController.setI(0);
    leftBackController.setD(0);
    leftBackController.setIZone(0);
    leftBackController.setFF(Constants.DRIVE_VELOCITY_FF);
    leftBackController.setOutputRange(-1 * Constants.DRIVE_MAX_ACCELERATION, Constants.DRIVE_MAX_ACCELERATION);
  }

  public void resetEncoders() {
    rightFrontEncoder.setPosition(0);
    rightBackEncoder.setPosition(0);
    leftFrontEncoder.setPosition(0);
    leftBackEncoder.setPosition(0);
  }

  public boolean rightAtTargetPosition() {
    double encoderAverage = (rightFrontEncoder.getPosition() + rightBackEncoder.getPosition()) / 2;

    return Math.abs(encoderAverage - targetRightPosition) / Constants.ROTATIONS_PER_INCH < Constants.DRIVE_TOLERANCE;
  }

  public boolean leftAtTargetPosition() {
    double encoderAverage = (leftFrontEncoder.getPosition() + leftBackEncoder.getPosition()) / 2;

    return Math.abs(encoderAverage - targetLeftPosition) / Constants.ROTATIONS_PER_INCH < Constants.DRIVE_TOLERANCE;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    //var gyroAngle = Rotation2d.fromDegrees(-gyro.getAngle());

    // Update the pose
    //m_pose = m_odometry.update(gyroAngle, m_leftEncoder.getDistance(), m_rightEncoder.getDistance());
    
    //m_odometry.update(m_gyro.getRotation2d(),
    //((Encoder) m_leftEncoder).getDistance(),
    //((Encoder) m_rightEncoder).getDistance());
//m_field.setRobotPose(m_odometry.getPoseMeters());
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
    //m_driveSim.setInputs(leftFront.get() * RobotController.getInputVoltage(),
    //rightFront.get() * -1 * RobotController.getInputVoltage());

  //m_driveSim.update(0.02);

  /** Update all  sensors. (REQUIRES OTHER STUFF TO BE COMPLETED)
  m_leftEncoderSim.setDistance(m_driveSim.getLeftPositionMeters());
  m_leftEncoderSim.setRate(m_driveSim.getLeftVelocityMetersPerSecond());
  m_rightEncoderSim.setDistance(m_driveSim.getRightPositionMeters());
  m_rightEncoderSim.setRate(m_driveSim.getRightVelocityMetersPerSecond());
  */
  }
}
