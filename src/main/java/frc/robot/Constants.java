// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.math.geometry.Rotation2d;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
  }

  public static class WristConstants {
    public static final int MOTOR_ID = 0;

    public static final double P = 0;
    public static final double I = 0;
    public static final double D = 0;


    public static final double MAX_VELOCITY = 5;
    public static final double MAX_ACCELERATION = 2;

    public static final Rotation2d MIN_ANGLE = Rotation2d.fromDegrees(0);
    public static final Rotation2d MAX_ANGLE = Rotation2d.fromDegrees(90);

    public static SparkMaxConfig MOTOR_CONFIG = (SparkMaxConfig) new SparkMaxConfig()
      .inverted(false)
      .idleMode(IdleMode.kBrake)
      .smartCurrentLimit(20)
      .secondaryCurrentLimit(15)
      .openLoopRampRate(0.25);

    public static final double POSITION_CONVERSION_FACTOR = 1;
    public static final double VELOCITY_CONVERSION_FACTOR = 1;
    }
}
