// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

    //drivebase constants
    public static final int RIGHT_FRONT = 1;
    public static final int RIGHT_BACK = 2;
    public static final int LEFT_FRONT = 4;
    public static final int LEFT_BACK = 3;
    public static final double SCALER_EXPONENT = 2.0;
    public static final double TURN_SCALER = 0.5;
    public static final double DRIVE_VELOCITY_P = 0.00006;
    public static final double DRIVE_VELOCITY_FF = 0.000015;
    public static final double DRIVE_MAX_ACCELERATION = 1.0;
    public static final double DRIVE_POSITION_P = 0.05;
    public static final double DRIVE_POSITION_D = 0.01;
    public static final double ANGLE_P = 0.02;
    public static final double ANGLE_D = 0.002;
    public static final int PID_AT_TARGET_LOOPS = 20;
    public static final int VELOCITY_PID_SLOT = 1;
    public static final int POSITION_PID_SLOT = 0;
    public static final double ROTATIONS_PER_INCH = 0.4483;
    public static final double DRIVE_TOLERANCE = 2.0; //in inches, not rotations
    public static final double CLIMBER_DRIVE_MULTIPLIER = 0.3;
    public static final double ANGLE_TOLERANCE = 1.0; //in degrees

    //climber constants
    public static final int CLIMBER_MOTOR_1 = 5;
    public static final int CLIMBER_MOTOR_2 = 6;
    public static final int CLIMBER_SOLENOID = 2;
    public static final double CLIMBER_POWER = 0.75;
    public static final int CLIMBER_ENCODER_SENSITIVITY = 4096;
    public static final double CLIMBER_WAIT_TIME = 0.5;
    public static final double CLIMBER_MAX_RETRACT = 120; 
    public static final int CLIMBER_LIMIT_SWITCH = 0;

    //catapult constants
    public static final int CATAPULT_RIGHT_SOLENOID_1 = 5;
    public static final int CATAPULT_RIGHT_SOLENOID_2 = 6;
    public static final int CATAPULT_LEFT_SOLENOID_1 = 0;
    public static final int CATAPULT_LEFT_SOLENOID_2 = 1;
    public static final double CATAPULT_WAIT_TIME = 1.5;
    public static final double CATAPULT_FOLLOW_THROUGH_TIME = 1.5;
    public static final double CATAPULT_WAIT_BETWEEN_TIME = 0;

    //intake constants
    public static final int INTAKE_MOTOR = 7;
    public static final int INTAKE_SOLENOID = 4;
    public static final double INTAKE_WAIT_TIME = 1.0;
    public static final double INTAKE_CONSTANT_SPEED = 1.0;
    public static final double INTAKE_CONSTANT_BACKWARD = -1.0;

    //cage constants
    public static final int CAGE_SOLENOID = 3;
    public static final double CAGE_WAIT_TIME = 0.5;

    //controller constants
    public static final int DRIVER_CONTROLLER = 0;
    public static final int OPERATOR_CONTROLLER = 1;
    public static final int ACCESSIBLE_CONTROLLER = 2;

    public static final int LEFT_X_AXIS = 0;
    public static final int LEFT_Y_AXIS= 1;
    public static final int RIGHT_X_AXIS = 4;
    public static final int RIGHT_Y_AXIS = 5;

    public static final int A_BUTTON = 1;
    public static final int B_BUTTON = 2;
    public static final int X_BUTTON = 3;
    public static final int Y_BUTTON = 4;

    public static final int LEFT_BUMPER = 5;
    public static final int RIGHT_BUMPER = 6;
    public static final int BACK_BUTTON = 7;
    public static final int START_BUTTON = 8;
    public static final int LEFT_JOYSTICK_BUTTON = 9;
    public static final int RIGHT_JOYSTICK_BUTTON = 10;
    
    //mathematical constants
    public static final double DEADZONE_CONSTANT = 0.1675;

    //vision constants
    public static final double LOCK_ON_TARGET_MAX_SPEED = 0.5;
    public static final int ROLLING_AVERAGE_LOOPS = 10;
    public static final double LOCK_ON_P = 0.04;
    public static final double LOCK_ON_D = 0.004;
    public static final double MIN_VERT_OFFSET = 11.0;
    public static final double MAX_VERT_OFFSET = 23.0;
    public static final double MIN_AREA_PERCENT = 0.01;
    public static final double MAX_AREA_PERCENT = 0.02;
}
