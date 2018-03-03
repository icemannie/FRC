// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc2465.Clyde;

import org.usfirst.frc2465.Clyde.commands.*;
import org.usfirst.frc2465.Clyde.subsystems.Claw.Motion;
import org.usfirst.frc2465.Clyde.subsystems.Elevator;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;


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


    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public Joystick driveStick;
    


    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    public OI() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS

        driveStick = new Joystick(0);
        
        Button elevatorSwitch = new JoystickButton(driveStick, 8);
        Button elevatorScaleLow = new JoystickButton(driveStick, 7);
        Button elevatorScaleHigh = new JoystickButton(driveStick, 6);
        Button clawOpen = new JoystickButton(driveStick, 2);
        Button clawClosed = new JoystickButton(driveStick, 3);
        Button clawIn = new JoystickButton(driveStick, 10);
        Button clawOut = new JoystickButton(driveStick, 11);
        
        elevatorSwitch.whileHeld(new ElevatorGoToInch(24, false));
        elevatorScaleLow.whileHeld(new ElevatorGoToInch(48, false));
        elevatorScaleHigh.whileHeld(new ElevatorGoToInch(72, false));
        clawOpen.whileHeld(new ClawGrab(Value.kForward));
        clawClosed.whileHeld(new ClawGrab(Value.kReverse));
        clawIn.whenPressed(new ClawSpin(Motion.IN));
        clawIn.whenReleased(new ClawSpin(Motion.STOP));
        clawOut.whenPressed(new ClawSpin(Motion.OUT));
        clawOut.whenReleased(new ClawSpin(Motion.STOP));
        
        SmartDashboard.putData("Calibrate Elevator", new CalibrateElevator());

        
        // SmartDashboard Buttons
        SmartDashboard.putData("Autonomous Command", new AutonomousCommand());
        SmartDashboard.putData("StickDrive", new StickDrive());

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
    }

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=FUNCTIONS
    public Joystick getDriveStick() {
        return driveStick;
    }


    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=FUNCTIONS
}

