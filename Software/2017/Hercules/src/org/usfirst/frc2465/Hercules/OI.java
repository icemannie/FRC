// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc2465.Hercules;

import org.usfirst.frc2465.Hercules.commands.AutonomousCommand;
import org.usfirst.frc2465.Hercules.commands.DriveDistance;
import org.usfirst.frc2465.Hercules.commands.StickDrive;
import org.usfirst.frc2465.Hercules.commands.*;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.buttons.Trigger;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
    // Joystick stick = new Joystick(port);
    // Button button = new JoystickButton(stick, buttonNumber);

    // There are a few additional built in buttons you can use. Additionally,
    // by subclassing Button you can create custom triggers and bind those to
    // commands the same as any other Button.

    //// TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:

    // Start the command when the button is pressed and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenPressed(new ExampleCommand());

    // Run the command while the button is being held down and interrupt it once
    // the button is released.
    // button.whileHeld(new ExampleCommand());

    // Start the command when the button is released  and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());

    public Joystick driver;
    public Joystick opJoystick;
    public Joystick lights;
    public JoystickButton autoSpeedPIDTuneButton;
    public JoystickButton autoRotatePIDTuneButton;
    public Trigger t;
    
    public JoystickButton closeUpPinchersButton;
    public JoystickButton tiltFunnelButton;


    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    public OI() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS



        
        lights =  new Joystick(2);
    	driver = new Joystick(1);
        opJoystick= new Joystick(0);
        
        closeUpPinchersButton = new JoystickButton(driver, 2);
        closeUpPinchersButton.whileHeld(new CloseUpPinchers());
        
        
        tiltFunnelButton = new JoystickButton(driver, 4);
        tiltFunnelButton.whileHeld(new TiltFunnelDump());
        
 
        
        // SmartDashboard Buttons
        SmartDashboard.putData("Autonomous Command", new AutonomousCommand());
        SmartDashboard.putData("OpenPincher", new OpenPincher());
        SmartDashboard.putData("ClosePincher", new ClosePincher());
        SmartDashboard.putData("Deploy", new Deploy());
        SmartDashboard.putData("Retract", new Retract());
        SmartDashboard.putData("TiltPincherU", new TiltPincherU());
        SmartDashboard.putData("TiltPincherD", new TiltPincherD());
        SmartDashboard.putData("TiltFunnelDump", new TiltFunnelDump());
        SmartDashboard.putData("TiltFunnelIntake", new TiltFunnelIntake());
        SmartDashboard.putData("WinchClimb", new WinchClimb());
        
        SmartDashboard.putData("Autonomous Command", new AutonomousCommand());

        SmartDashboard.putData("StickDrive", new StickDrive());
        
        SmartDashboard.putData("DriveForwardTwelveInches", new DriveDistance(31.4f, 0.1f, 0.0f));
        



        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
    }
    public Joystick getdriver() {
        return driver;
    }

    public Joystick getLights() {
        return lights;
    }

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=FUNCTIONS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=FUNCTIONS
}

