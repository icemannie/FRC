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
    public Joystick arduino;


    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    public OI() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS

        driveStick = new Joystick(0);
        arduino = new Joystick(1);
        

        Button elevatorManUp = new JoystickButton(arduino, 2);
        Button elevatorManDown = new JoystickButton(arduino, 1);
        
        // Arduino buttons
        //Button elevatorHover = new JoystickButton(arduino, 1);
        //Button elevatorSwitch = new JoystickButton(arduino, 2);
        //Button elevatorScaleLow = new JoystickButton(arduino, 3);
        //Button elevatorScaleMedium = new JoystickButton(arduino, 4);
        //Button elevatorScaleHigh = new JoystickButton(arduino, 5);
        
        Button clawOpen = new JoystickButton(arduino, 6);
        Button clawOpenStick = new JoystickButton(driveStick, 2);
        Button clawIn = new JoystickButton(arduino, 7);
        Button clawOut = new JoystickButton(arduino, 8);
        
        //Button rotate0 = new JoystickButton(arduino, 9);
        //Button rotate90 = new JoystickButton(arduino, 10);
        //Button rotateNeg90 = new JoystickButton(arduino, 11);
        //Button rotate180 = new JoystickButton(arduino, 12);

        // rotate buttons
        //rotate90.whenPressed(new RotateToAngle(90));
        //rotateNeg90.whenPressed(new RotateToAngle(-90));
        //rotate0.whenPressed(new RotateToAngle(0));
        //rotate180.whenPressed(new RotateToAngle(180));
       //Elevator
        //elevatorHover.whileHeld(new ElevatorGoToInch(14, false));
        //elevatorSwitch.whileHeld(new ElevatorGoToInch(24, false));
        //elevatorScaleLow.whileHeld(new ElevatorGoToInch(54, false));
        //elevatorScaleMedium.whileHeld(new ElevatorGoToInch(65, false));
        //elevatorScaleHigh.whileHeld(new ElevatorGoToInch(76, false));
        elevatorManUp.whenPressed(new ElevatorManual(Elevator.Motion.UP));
        elevatorManUp.whenReleased(new ElevatorManual(Elevator.Motion.HOLD));
        elevatorManDown.whenPressed(new ElevatorManual(Elevator.Motion.DOWN));
        elevatorManDown.whenReleased(new ElevatorManual(Elevator.Motion.HOLD));
        //Claw
        clawOpen.whenPressed(new ClawGrab(Value.kForward));
        clawOpen.whenReleased(new ClawGrab(Value.kReverse));
        clawOpenStick.whenPressed(new ClawGrab(Value.kForward));
        clawOpenStick.whenReleased(new ClawGrab(Value.kReverse));
        clawIn.whileHeld(new ClawSpin(Motion.IN));
        clawIn.whenReleased(new ClawSpin(Motion.STOP));
        clawOut.whileHeld(new ClawSpin(Motion.OUT));
        clawOut.whenReleased(new ClawSpin(Motion.STOP));
        
        //elevatorScaleLow.whenReleased(new ElevatorManual(Elevator.Motion.HOLD));
        
        SmartDashboard.putData("Calibrate Elevator", new CalibrateElevator());

        SmartDashboard.putData("DriveStraight", new DriveDistance(20, true));

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
    }

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=FUNCTIONS
    public Joystick getDriveStick() {
        return driveStick;
    }


    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=FUNCTIONS
}

