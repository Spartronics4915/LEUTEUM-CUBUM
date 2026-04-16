package frc.robot.subsystems;

import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.FeedbackConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.SlotConfigs;
import com.ctre.phoenix6.configs.TalonFXConfigurator;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.ctre.phoenix6.swerve.utility.LinearPath.State;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IntakeSubsystem extends SubsystemBase {

    private TalonFX mainIntakeMotor;

    private double currentSetSpeed;
    private State currentState;

    public IntakeSubsystem() {
        mainIntakeMotor = new TalonFX(Constants.IntakeConstants.mainIntakeMotorID); 
        TalonFXConfigurator configForMainShooterMotor = mainIntakeMotor.getConfigurator();
        configForMainShooterMotor.apply(new SlotConfigs()
            .withKP(Constants.IntakeConstants.mainP)
            .withKI(Constants.IntakeConstants.mainI)
            .withKD(Constants.IntakeConstants.mainD)
        );
        configForMainShooterMotor.apply(new CurrentLimitsConfigs()
            .withSupplyCurrentLimitEnable(true)
            .withSupplyCurrentLimit(60)
            .withSupplyCurrentLowerLimit(40)
            .withSupplyCurrentLowerTime(1.0)
        );
        configForMainShooterMotor.apply(new FeedbackConfigs()
            .withSensorToMechanismRatio(1)
        );
        MotorOutputConfigs mainShooterMotorOutputConfigs = new MotorOutputConfigs();
    }

    public void setSpeed(double zeroToOne){
            currentSetSpeed = zeroToOne * Constants.IntakeConstants.maxSpeed;
        }

    public void setExactSpeed(double newSpeed){
        currentSetSpeed = newSpeed;
    }

    @Override
        public void periodic() {

            currentSetSpeed = MathUtil.clamp(
                currentSetSpeed,
                Constants.IntakeConstants.minSpeed,
                Constants.IntakeConstants.maxSpeed
            );

        }

    public Command setSpeedCommand(double newSpeed) {
        return Commands.runOnce(() -> setSpeed(newSpeed));
    }

    public Command setExactSpeedCommand(double newSpeed) {
        return Commands.runOnce(() -> setExactSpeed(newSpeed));
    }
}
