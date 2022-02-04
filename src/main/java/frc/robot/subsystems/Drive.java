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

  //PID coefficents
  private double kP = 6e-5; 
  private double kI = 0;
  private double kD = 0; 
  private double kIz = 0;
  private double kFF = 0.000015; 
  private double kMaxOutput = 1; 
  private double kMinOutput = -1;
  private double maxRPM = 5700;

  private boolean leftInverted = true;
  private boolean rightInverted = false;


  public Drive() {
    rightFollower.follow(rightMain);
    leftFollower.follow(leftMain);

    leftMain.setInverted(leftInverted);
    rightMain.setInverted(rightInverted);

    //set PID coefficents
    rightPIDController.setP(kP);
    rightPIDController.setD(kD);
    rightPIDController.setI(kI);
    rightPIDController.setIZone(kIz);
    rightPIDController.setOutputRange(kMinOutput, kMaxOutput);

    leftPIDController.setP(kP);
    leftPIDController.setD(kD);
    leftPIDController.setI(kI);
    leftPIDController.setIZone(kIz);
    leftPIDController.setOutputRange(kMinOutput, kMaxOutput);

    if(Robot.isSimulation()){
      REVPhysicsSim.getInstance().addSparkMax(rightMain, DCMotor.getNEO(1));
      REVPhysicsSim.getInstance().addSparkMax(rightFollower, DCMotor.getNEO(1));
      REVPhysicsSim.getInstance().addSparkMax(leftMain, DCMotor.getNEO(1));
      REVPhysicsSim.getInstance().addSparkMax(leftFollower, DCMotor.getNEO(1));
    }
    
  }

  public void setRight(double speed){
    //setVoltage for simulation support
    speed = speed * 0.2;
    rightMain.set(speed);
  
  }

  public void setLeft(double speed){
    speed = speed * 0.2;
    leftMain.set(speed);

  }

  //using pid
  public void setRightVelocity(double targetVelocity){
    rightPIDController.setReference(targetVelocity, CANSparkMax.ControlType.kVelocity);
  }

  public void setLeftVelocity(double targetVelocity){
    leftPIDController.setReference(targetVelocity, CANSparkMax.ControlType.kVelocity);
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
