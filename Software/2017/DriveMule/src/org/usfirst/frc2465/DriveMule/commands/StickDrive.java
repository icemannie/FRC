// RobotBuilder Version: 1.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc2465.DriveMule.commands;

import edu.wpi.first.wpilibj.CANSpeedController.ControlMode;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc2465.DriveMule.Robot;
import org.usfirst.frc2465.DriveMule.RobotMap;

import com.ctre.CANTalon;

/**
 *
 */
public class  StickDrive extends Command {

    static final double DEADZONE = .07;
  
    public boolean speed_mode;
    public class JoystickResponseCurve {
        double adjust;
        double power;
        double multiplier;
        double deadzone;
        
        public JoystickResponseCurve( double adj, double pow, double mult, double dead ) {
            adjust = adj;
            power = pow;
            multiplier = mult;
            deadzone = dead;
        }
        
        // f(x) = multiplier * (adjust * x^power+(1-adjust)*x)
        
        public double transform( double input ) {
            double output = 0.0;
            if ( ( input > deadzone ) || ( input < (-1 * deadzone ) ) ) {
                output = multiplier * ( adjust * Math.pow(input, power) + (1-adjust) * input );
            }
            return output;
        }
    }
    
    public class JoystickResponseCurveSet {
        JoystickResponseCurve fwd;
        JoystickResponseCurve strafe;
        JoystickResponseCurve rotate;
        
        public JoystickResponseCurveSet(
            JoystickResponseCurve fwd,
            JoystickResponseCurve strafe,
            JoystickResponseCurve rotate ) {
            this.fwd = fwd;
            this.strafe = strafe;
            this.rotate = rotate;
        }
        
        public double transformForward( double input )  { return fwd.transform(input); }
        public double transformStrafe( double input )   { return strafe.transform(input); }
        public double transformRotate( double input )   { return rotate.transform(input); }
    }
        
    JoystickResponseCurveSet linear = new JoystickResponseCurveSet(
            new JoystickResponseCurve( .00, 3, 1.0, DEADZONE ),
            new JoystickResponseCurve( .00, 3, 1.0, DEADZONE ),
            new JoystickResponseCurve( .00, 3, 1.0, DEADZONE ) );
    
    JoystickResponseCurveSet conservative = new JoystickResponseCurveSet(
            new JoystickResponseCurve( .40, 3, .50, DEADZONE ),
            new JoystickResponseCurve( .40, 3, .50, DEADZONE ),
            new JoystickResponseCurve( .40, 3, .35, DEADZONE ) );

    JoystickResponseCurveSet aggressive = new JoystickResponseCurveSet(
            new JoystickResponseCurve( .40, 3, 1.0, DEADZONE ),
            new JoystickResponseCurve( .40, 3, 1.0, DEADZONE ),
            new JoystickResponseCurve( .40, 3, 1.0, DEADZONE ) );
    
    public StickDrive() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.drive);
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
    	CANTalon.TalonControlMode curr_mode = Robot.drive.getMode();
    	speed_mode = (curr_mode == CANTalon.TalonControlMode.Speed);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        
        JoystickResponseCurveSet slow = conservative;
        JoystickResponseCurveSet fast = aggressive;
        
        Joystick driver = Robot.oi.driver;
        Joystick op = Robot.oi.opJoystick;
        double vX = driver.getX();
        double vY = driver.getY() * -1; /* FOrward on flight joysticks is negative Y - NOT GOOD FOR ROBOTS! */
        double vRot = driver.getRawAxis(3);
        
        SmartDashboard.putNumber("JoystickX",  vX);
        SmartDashboard.putNumber("JoystickY",  vY);
        SmartDashboard.putNumber("JoystickZ",  vRot);
                         
        if ( driver.getRawButton(4) )
        {
        	if ( RobotMap.imu != null ) {
	            RobotMap.imu.zeroYaw();
        	}
        }
        
        // By default, drive mode is Speed control (encoder-based)
        // If driver presses button 3, the mode should toggle.
        //boolean enable_percent_vbus = driver.getRawButton(3);
        //if ( speed_mode ) {
        //	if ( enable_percent_vbus ) {
        //		speed_mode = false;
        /*		Robot.drive.setMode(CANTalon.TalonControlMode.PercentVbus);
        	}
        } else {
        	if ( enable_percent_vbus ) {
        		speed_mode = true;
        		Robot.drive.setMode(CANTalon.TalonControlMode.Speed);
        	}
        }*/
        
        if ( driver.getRawButton(1))
        {
        	 vX = fast.transformStrafe(vX);
             vY = fast.transformForward(vY);
             vRot = fast.transformRotate(vRot);
             SmartDashboard.putString("DriveSpeed", "fast");
        } 
        else 
        {
            vX = slow.transformStrafe(vX);
            vY = slow.transformForward(vY);
            vRot = slow.transformRotate(vRot);
            SmartDashboard.putString("DriveSpeed", "slow");
        }
        
        if ( driver.getRawButton(2))
        {
            Robot.drive.setFODEnabled(true);
        }
        if ( driver.getRawButton(3))
        {
            Robot.drive.setFODEnabled(false);
        }
        
        if (driver.getRawButton(6))
        {
        	Robot.drive.setAutoRotation(true);
        	Robot.drive.setSetpoint(0.0);
        }
        else if (driver.getRawButton(7))
        {
        	Robot.drive.setAutoRotation(true);
        	Robot.drive.setSetpoint(90.0);
        }
        else if (driver.getRawButton(8))
        {
        	Robot.drive.setAutoRotation(true);
        	Robot.drive.setSetpoint(180.0);
        }
        else if (driver.getRawButton(9))
        {
        	Robot.drive.setAutoRotation(true);
        	Robot.drive.setSetpoint(-90.0);
        }
        else 
        {
        	Robot.drive.setAutoRotation(false);
        }
        
        /*
        if ( op.getRawButton(7))
        {
        	Robot.drive.setAutoRotation(true);
        	Robot.drive.setSetpoint(-90.0);
        }
        else if ( op.getRawButton(8))
        {
        	Robot.drive.setAutoRotation(true);
        	Robot.drive.setSetpoint(-179.0);
        }
        else if ( op.getRawButton(9))
        {
        	Robot.drive.setAutoRotation(true);
        	Robot.drive.setSetpoint(90.0);
        }
        else if ( op.getRawButton(10))
        {
        	Robot.drive.setAutoRotation(true);
        	Robot.drive.setSetpoint(0.0);	
        }
        else
        {
        	Robot.drive.setAutoRotation(false);
        }
        */
        if(RobotMap.robotDrive != null)
        {
        	Robot.drive.doMecanum(vX, vY, vRot);
        }
        SmartDashboard.putNumber("AutoRotate Error", Robot.drive.getPIDController().getError());
        SmartDashboard.putNumber("AutoRotate Setpoint", Robot.drive.getPIDController().getSetpoint());
        SmartDashboard.putBoolean("AutoRotate On Target", Robot.drive.getPIDController().onTarget());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
