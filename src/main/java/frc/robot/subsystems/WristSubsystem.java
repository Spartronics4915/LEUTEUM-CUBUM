package frc.robot.subsystems;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.trajectory.TrapezoidProfile.Constraints;
import edu.wpi.first.math.trajectory.TrapezoidProfile.State;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.WristConstants.*;

import com.revrobotics.PersistMode;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.FeedbackSensor;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkLowLevel.MotorType;

public class WristSubsystem extends SubsystemBase {
    
    SparkMax motor = new SparkMax(MOTOR_ID, MotorType.kBrushless);
    RelativeEncoder motorEncoder = motor.getEncoder();

    TrapezoidProfile trapProfile = new TrapezoidProfile(
        new Constraints(MAX_VELOCITY, MAX_ACCELERATION)
    );

    private double position = 0;
    private double velocity = 0;
    State currentState = new State(position,velocity);
    State targetState = new State(position,velocity);

    SparkClosedLoopController closedLoopController = motor.getClosedLoopController();

    public WristSubsystem(){
        MOTOR_CONFIG.encoder
            .positionConversionFactor(POSITION_CONVERSION_FACTOR)
            .velocityConversionFactor(VELOCITY_CONVERSION_FACTOR);
        MOTOR_CONFIG
            .closedLoop.feedbackSensor(FeedbackSensor.kPrimaryEncoder)
            .pid(P, I, D);
        
        motor.configure(MOTOR_CONFIG,ResetMode.kResetSafeParameters,PersistMode.kPersistParameters);
        
    }

    @Override
    public void periodic() {
        targetState.position = MathUtil.clamp(
            targetState.position,
            MIN_ANGLE.getRotations(),
            MAX_ANGLE.getRotations()
        );   
        currentState = trapProfile.calculate(1/50, currentState, targetState);
        closedLoopController.setSetpoint(currentState.position, ControlType.kPosition);
    }

    public void setSetpoint(Rotation2d position){
        targetState.position = position.getRotations();
    }

    public void incrementSetpoint(Rotation2d input){
        targetState.position += input.getRotations();
    }

    public Rotation2d getAngle(){
        return Rotation2d.fromRotations(motorEncoder.getPosition());
    }

    //#region Commands
    public Command setSetpointCommand(Rotation2d setpoint){
        return this.runOnce(()->setSetpoint(setpoint));
    }
    
    public Command incrementSetpointCommand(Rotation2d change){
        return this.runOnce(()-> incrementSetpoint(change));
    }
}
