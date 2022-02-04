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
    public static final int GYRO = 1;
    public static final double DRIVE_VELOCITY_P = 0.00006;
    public static final double DRIVE_VELOCITY_FF = 0.000015;
    public static final double DRIVE_MAX_ACCELERATION = 1.0;
    public static final double DRIVE_POSITION_P = 0.2;
    public static final double DRIVE_POSITION_D = 0.01;
    public static final int VELOCITY_PID_SLOT = 1;
    public static final int POSITION_PID_SLOT = 0;
    public static final double ROTATIONS_PER_INCH = 0.4642;
    public static final double DRIVE_TOLERANCE = 2.0; //in inches, not rotations


    //climber constants
    public static final int CLIMBER_MOTOR_1 = 5;
    public static final int CLIMBER_MOTOR_2 = 6;
    public static final int CLIMBER_SOLENOID = 1;
    public static final double CLIMBER_POWER = 0.75;
    public static final int CLIMBER_ENCODER_SENSITIVITY = 4096;

    //catapult constants
    public static final int CATAPULT_RIGHT_SOLENOID_1 = 2;
    public static final int CATAPULT_RIGHT_SOLENOID_2 = 3;
    public static final int CATAPULT_LEFT_SOLENOID_1 = 4;
    public static final int CATAPULT_LEFT_SOLENOID_2 = 5;

    //intake constants
    public static final int INTAKE_MOTOR = 7;
    public static final int INTAKE_RIGHT_SOLENOID = 6;
    public static final int INTAKE_LEFT_SOLENOID = 7;

    //controller constants
    public static final int DRIVER_CONTROLLER = 0;
    public static final int OPERATOR_CONTROLLER = 1;

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

}
