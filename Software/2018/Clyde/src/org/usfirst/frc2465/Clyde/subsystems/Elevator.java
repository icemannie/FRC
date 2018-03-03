// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc2465.Clyde.subsystems;

import org.usfirst.frc2465.Clyde.RobotMap;
import org.usfirst.frc2465.Clyde.RobotPreferences;
import org.usfirst.frc2465.Clyde.commands.ElevatorGoToInch;

import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS

/**
 *
 */
public class Elevator extends PIDSubsystem {

	public enum Motion {UP, DOWN, STOP, HOLD};
	Motion motion;
	
	static final float SPEED = 0.4f;
	double tolerance_inches;
	public int encoderHeight;
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    private final DigitalInput bottomSwitch = RobotMap.elevatorBottomSwitch;
    private final DigitalInput topSwitch = RobotMap.elevatorTopSwitch;
    private final WPI_TalonSRX elevatorMotor = RobotMap.elevatorMotor; 

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    // Initialize your subsystem here
	public Elevator() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=PID
       
        
        super(  "Elevator",
                RobotPreferences.getAutoRotateP(),
                RobotPreferences.getAutoRotateI(),
                RobotPreferences.getAutoRotateD(),
                0,
                0.02);
        LiveWindow.addActuator("Elevator", "PIDSubsystem Controller", getPIDController());

        if ( calibrated() ) {
        	getPIDController().setInputRange(0 , RobotPreferences.getTopEncoderPos());
        }
        getPIDController().setContinuous( false );
        getPIDController().setOutputRange(-0.8, 0.8);
        tolerance_inches = RobotPreferences.getElevatorOnTargetToleranceInches();
        getPIDController().setAbsoluteTolerance(tolerance_inches);
        setSetpoint(RobotPreferences.getElevatorDefaultTaretInches());
        disable();
        
        elevatorMotor.configSelectedFeedbackSensor(com.ctre.phoenix.motorcontrol.FeedbackDevice.QuadEncoder, 0, 0);
        elevatorMotor.setSensorPhase(true);
        motion = Motion.STOP;
        setHome();
        

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=PID

        // Use these to get going:
        // setSetpoint() -  Sets where the PID controller should move the system
        //                  to
        // enable() - Enables the PID controller.
    }
    
    
    
    public boolean isBottom() {
    	return ! bottomSwitch.get();
    }
    
    public boolean isTop() {
    	return ! topSwitch.get();
    }
    
    public void setHome() {
    	elevatorMotor.setSelectedSensorPosition(0, 0, 0);
    }
    
    public void setTop() {
    	RobotPreferences.setTopEncoderPos(elevatorMotor.getSelectedSensorPosition(0));
    }
    
    public void updateDashboard() {
    	
		SmartDashboard.putNumber("Elevator Inches", getCurrentInches());
		SmartDashboard.putNumber("Elevator Encoder Count",elevatorMotor.getSelectedSensorPosition(0));
		SmartDashboard.putNumber("Encoder Counts Per Inch", Elevator.getCountsPerInch());
		SmartDashboard.putBoolean("Calibrated", calibrated());
		SmartDashboard.putBoolean("At Top", isTop());
		SmartDashboard.putBoolean("At Bottom", isBottom());
		SmartDashboard.putString("Elevator State", getMotion().toString());
		SmartDashboard.putBoolean("PID Enabled", getGoToInch());
		SmartDashboard.putNumber("Height Encoder Count",  RobotPreferences.getTopEncoderPos());
		
		SmartDashboard.putBoolean("Elevator Go To Inch Enabled", getGoToInch());

    }
    
    public int getEncoderCount() {
    	
    	return elevatorMotor.getSelectedSensorPosition(0);
    }
    
    private boolean calibrated() {
    	
    	if (RobotPreferences.getTopEncoderPos() == 0.0) {
    		return false;
    	} else {
    		return true;
    		
    	}
    }
    
    public double getCurrentInches() {
    	
    	return getInchesPerCount() * elevatorMotor.getSelectedSensorPosition(0);
    }
    
    private int InchesToCount(double inches) {
    	
    	return (int) (inches / getInchesPerCount());
    }
    
    public static double getInchesPerCount() {
    	
    	return RobotPreferences.getElevatorHeight() / RobotPreferences.getTopEncoderPos();
    }
    
    public static double getCountsPerInch() {
    	return RobotPreferences.getTopEncoderPos() / RobotPreferences.getElevatorHeight();
    }
    
    public void setSetpoint(double inches) {
    	
		getPIDController().setSetpoint(InchesToCount(inches));
    }
    
    public void setMotion(Motion motion) {
    	
    	if (getPIDController().isEnabled() == false){
    		switch(motion) {
            	case UP:    elevatorMotor.set(SPEED);   break;
            	case DOWN:	elevatorMotor.set(-SPEED);  break;
            	case STOP:  elevatorMotor.set(0);       break;
            	case HOLD:  elevatorMotor.set(0.11);    break;  //0.14 is current power required to hold in place w/ cube, change if needed
    		}
    	}
    }
    
    public Motion getMotion() {
    	if (elevatorMotor.get() >= 0.05) {
    		return Motion.UP;
    	}
    	else  if (elevatorMotor.get() <= -0.05) {
    		return Motion.DOWN;
    	}
    	else  if (elevatorMotor.get() <= -0.05 && elevatorMotor.get() >= 0.05) {
    		return Motion.STOP;
    	}
		return motion;
    }
    
    //public float rotPerInch() {
    	
    //}
    
    public void setGoToInch(boolean b) {
    	
    	if ( b ) {
    		getPIDController().enable();
    	} else {
    		getPIDController().disable();
    	}
    }
    
    public boolean getGoToInch() {
    	
    	return getPIDController().isEnabled();
    }

    @Override
    public void initDefaultCommand() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND


    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        // Set the default command for a subsystem here.
        setDefaultCommand(new ElevatorGoToInch((float) RobotPreferences.getFloorOffset(), false));
    }

    @Override
    protected double returnPIDInput() {
        // Return your input value for the PID loop
        // e.g. a sensor, like a potentiometer:
        // yourPot.getAverageVoltage() / kYourMaxVoltage;

        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=SOURCE
        return elevatorMotor.getSelectedSensorPosition(0);

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=SOURCE
    }

    @Override
    protected void usePIDOutput(double output) {
        // Use output to drive your system, like a motor
        // e.g. yourMotor.set(output);

        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=OUTPUT
        elevatorMotor.pidWrite(output);

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=OUTPUT
    }
}
