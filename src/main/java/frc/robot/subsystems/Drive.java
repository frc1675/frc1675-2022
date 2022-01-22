// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;


import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.REVPhysicsSim;
import com.revrobotics.RelativeEncoder;

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
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Robot;


public class Drive extends SubsystemBase {
  /** Creates a new Drive subsystem. */
  private CANSparkMax rightFront = new CANSparkMax(Constants.RIGHT_FRONT, MotorType.kBrushless);
  private CANSparkMax rightBack = new CANSparkMax(Constants.RIGHT_BACK, MotorType.kBrushless);
  private CANSparkMax leftFront = new CANSparkMax(Constants.LEFT_FRONT, MotorType.kBrushless);
  private CANSparkMax leftBack = new CANSparkMax(Constants.LEFT_BACK, MotorType.kBrushless);

  private RelativeEncoder rightEncoder = rightFront.getEncoder();
  private RelativeEncoder leftEncoder = leftFront.getEncoder();

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
    rightFront.setInverted(true);
    rightBack.setInverted(true);

    //SmartDashboard.putData("Field", m_field);

    //DifferentialDriveOdometry m_odometry = new DifferentialDriveOdometry(m_gyro.getGyroHeading(), new Pose2d(5.0, 13.5, new Rotation2d()));
    if(Robot.isSimulation()){
      REVPhysicsSim.getInstance().addSparkMax(rightFront, DCMotor.getNEO(2));
      REVPhysicsSim.getInstance().addSparkMax(rightBack, DCMotor.getNEO(2));
      REVPhysicsSim.getInstance().addSparkMax(leftFront, DCMotor.getNEO(2));
      REVPhysicsSim.getInstance().addSparkMax(leftBack, DCMotor.getNEO(2));
    }
    
  }

  public void setRight(double speed){
    rightFront.set(speed);
    rightBack.set(speed);

  }

  public void setLeft(double speed){
    leftFront.set(speed);
    leftBack.set(speed);

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
