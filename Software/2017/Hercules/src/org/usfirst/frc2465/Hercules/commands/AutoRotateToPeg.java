// RobotBuilder Version: 1.5
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc2465.Hercules.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.io.Console;

import org.usfirst.frc2465.Hercules.Robot;
import org.usfirst.frc2465.Hercules.RobotMap;

/**
 *
 */
public class  AutoRotateToPeg extends Command {
	
	float rotation_angle;
	double target_angle;
	boolean previousAutoRotate = false;
	float current_angle = RobotMap.imu.getYaw();

    public AutoRotateToPeg(float pi_Angle) {
    	target_angle = current_angle - pi_Angle;
        // Use requires() here to declare subsystem dependencies
        requires(Robot.drive);

        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	previousAutoRotate = Robot.drive.getAutoRotation();
    	Robot.drive.setAutoRotation(true);
    	Robot.drive.setSetpoint(target_angle); 
    	System.out.println("Auto-rotate command initialized.");
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.drive.doMecanum(0, 0, 0);
        SmartDashboard.putNumber("AutoRotate Error", Robot.drive.getPIDController().getError());
        SmartDashboard.putNumber("AutoRotate Setpoint", Robot.drive.getPIDController().getSetpoint());
        SmartDashboard.putBoolean("AutoRotate On Target", Robot.drive.getPIDController().onTarget());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.drive.onTarget();
    }

    // Called once after isFinished returns true
    protected void end() {
    	System.out.println("Auto-rotate command complete.");
    	Robot.drive.setAutoRotation(previousAutoRotate);
    	Robot.drive.doMecanum(0, 0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
