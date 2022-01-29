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
    public static final int RIGHT_FRONT= 1;
    public static final int RIGHT_BACK = 2;
    public static final int LEFT_FRONT = 4;
    public static final int LEFT_BACK = 3;
    public static final int DRIVETRAIN_ENCODER_RPM = 8192;
    public static final int GYRO = 1;

    //climber constants
    public static final int CLIMBER_MOTOR_1 = 5;
    public static final int CLIMBER_MOTOR_2 = 6;
    public static final int CLIMBER_SOLENOID = 1;
    public static final double CLIMBER_POWER = 0.75;
    public static final int CLIMBER_ENCODER_SENSITIVITY = 4096;

    //catapult constants
    public static final int CATAPULT_RIGHT_SOLENOID = 2;
    public static final int CATAPULT_LEFT_SOLENOID = 3;

    //controller constants
    public static final int DRIVER_CONTROLLER = 0;
    public static final int OPERATOR_CONTROLLER = 1;
    public static final int LEFT_X_AXIS = 0;
    public static final int LEFT_Y_AXIS= 1;
    public static final int RIGHT_X_AXIS = 4;
    public static final int RIGHT_Y_AXIS = 5;
    public static final int A_BUTTON = 1;
    public static final int X_BUTTON = 3;
    public static final int START_BUTTON = 11;
    public static final int RIGHT_BUMPER = 6;

    //mathematical constants
    public static final double DEADZONE_CONSTANT = 0.1675;

}
