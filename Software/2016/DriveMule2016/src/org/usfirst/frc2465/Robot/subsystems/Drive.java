// RobotBuilder Version: 1.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc2465.Robot.subsystems;

import org.usfirst.frc2465.Robot.RobotMap;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.CANTalon.ControlMode;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc2465.Robot.RobotPreferences;
import org.usfirst.frc2465.Robot.commands.StickDrive;

import com.kauailabs.navx.frc.AHRS;

//Yes
/**
 *
 */

public class Drive extends PIDSubsystem {
    
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    AHRS imu = RobotMap.imu;
    CANTalon leftFrontSC = RobotMap.driveLeftFrontSC;
    CANTalon leftRearSC = RobotMap.driveLeftRearSC;
    CANTalon rightFrontSC = RobotMap.driveRightFrontSC;
    CANTalon rightRearSC = RobotMap.driveRightRearSC;
    RobotDrive robotDrive = RobotMap.robotDrive;
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    static final double cWidth          = 25.0;                 // Distance between left/right wheels
    static final double cLength         = 17.5;                 // Distance btwn front/back wheels
    static final double wheelDiameter   = 8.0;                  // Per AndyMark Specs
    static final double wheelRadius     = wheelDiameter / 2;

    public static final int ROTATE_DIRECTION  = -1;
    
/////////////////////////////////////////////////////////////////////////////////////
// Mecanum Constants
/////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////
// Proportional translation vs. Rotation
//
// For the same motor speed, the distance of translation and distance of rotation
// are not the same, due to the proportions of the wheel radius, and the 
// distance between front/back and left/right wheels.
//////////////////////////////////////////////////////////

    static final double cRotK = ((cWidth + cLength)/2) / wheelRadius;               // Rotational Coefficient

    static double invMatrix[][] = new double[][] {
        {  -1, 1,  cRotK },
        {   1, 1, -cRotK },
        {   1, 1,  cRotK },
        {  -1, 1, -cRotK },        
    };
       
    CANTalon.ControlMode currControlMode;
    int maxOutputSpeed;
    int maxTicksPer100MS;    
    double tolerance_degrees;
    final int ticksPerRev = 4*256;
    final int num100msPerSec = 10;
    final float motorRPMs = 2650.0f;
    final float transRatio = 4.41f;
    
    public Drive() {
        super(  "Drive",
                RobotPreferences.getAutoRotateP(),
                RobotPreferences.getAutoRotateI(),
                RobotPreferences.getAutoRotateD());
        try {
            getPIDController().setContinuous( true );
            getPIDController().setInputRange(-180,180);
            getPIDController().setOutputRange(-1, 1);
            tolerance_degrees = RobotPreferences.getAutoRotateOnTargetToleranceDegrees();
            getPIDController().setAbsoluteTolerance(tolerance_degrees);
            setSetpoint(0.0/*RobotPreferences.getAutoRotateDefaultTargetDegrees()*/);
            disable();
            
            robotDrive.setSafetyEnabled(false);
            
            /*leftFrontSC.getPowerCycled();
            rightFrontSC.getPowerCycled();
            leftRearSC.getPowerCycled();
            rightRearSC.getPowerCycled();*/
            
            maxTicksPer100MS = (int)((motorRPMs/transRatio)*ticksPerRev)/num100msPerSec; /* ~20 Feet/Sec */
            
            setMode( CANTalon.ControlMode.Speed);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
      
    }
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND
        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND
	
        // Set the default command for a subsystem here.
        setDefaultCommand(new StickDrive());
    }

    void initMotor( CANTalon motor ) {
        try {
            if ( currControlMode == CANTalon.ControlMode.Speed )
            {
                //motor.configMaxOutputVoltage(12.0);
                motor.setFeedbackDevice(FeedbackDevice.QuadEncoder); //motor.setSpeedMode(CANTalon.kQuadEncoder, 256, .4, .01, 0);
            	//We don't tell the motor controller the number of ticks per encoder revolution
                //The Talon needs to be told the number of encoder ticks per 10 ms to rotate
                motor.setPID(.025, 0, 0);
                motor.changeControlMode(ControlMode.Speed);
                motor.setCloseLoopRampRate(0);
            }
            else
            {
            	motor.changeControlMode(ControlMode.PercentVbus);
            }
            motor.enableBrakeMode(true);
            motor.setVoltageRampRate(0);
            motor.enableControl();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }    
    
    void setMode( CANTalon.ControlMode controlMode ) {
        
        currControlMode = controlMode;

        if ( currControlMode == CANTalon.ControlMode.Speed )
        {
                maxOutputSpeed = maxTicksPer100MS;
        }
        else // kPercentVbus
        {
                maxOutputSpeed = 1;
        }
        
        initMotor(leftFrontSC);
        initMotor(rightFrontSC);
        initMotor(leftRearSC);
        initMotor(rightRearSC);    
    }    
    
    void checkForRestartedMotor( CANTalon motor, String strDescription )
    {
        if ( currControlMode != CANTalon.ControlMode.Speed )   // kSpeed is the default
        {
            try {
            	if ( !motor.isAlive() )
                {
                    Timer.delay(0.10); // Wait 100 ms
                    initMotor( motor );
                    String error = "\n\n>>>>" + strDescription + "Jaguar Power Cycled - re-initializing";
                    System.out.println(error);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }    

    void mecanumDriveFwdKinematics( double wheelSpeeds[], double velocities[] )
    {
        for ( int i = 0; i < 3; i++ )
        {
            velocities[i] = 0;
            for ( int wheel = 0; wheel < 4; wheel ++ )
            {
                    velocities[i] += wheelSpeeds[wheel] * (1 / invMatrix[wheel][i]);
            }
            velocities[i] *= ((double)1.0/4);
        }
    }

    void mecanumDriveInvKinematics( double velocities[], double[] wheelSpeeds)
    {
        for ( int wheel = 0; wheel < 4; wheel ++ )
        {
            wheelSpeeds[wheel] = 0;
            for ( int i = 0; i < 3; i++ )
            {
                    wheelSpeeds[wheel] += velocities[i] * invMatrix[wheel][i];
            }
        }
    }    

    boolean fod_enable = true;
    double next_autorotate_value = 0.0;
    
    public void doMecanum( double vX, double vY, double vRot) {
        
        // If auto-rotating, replace vRot with the next
        // calculated value
        
        if ( getAutoRotation() ) {
            vRot = next_autorotate_value;
        }
        
        boolean imu_connected = false;
        if ( imu != null ) { 
        	imu_connected = imu.isConnected();
        }
                
        // Field-oriented drive - Adjust input angle for gyro offset angle
        
        double curr_gyro_angle_degrees = 0;
        if ( fod_enable && imu_connected ) 
        {
                curr_gyro_angle_degrees = imu.getYaw();
        }
        double curr_gyro_angle_radians = curr_gyro_angle_degrees * Math.PI/180;       
          
        double temp = vX * Math.cos( curr_gyro_angle_radians ) + vY * Math.sin( curr_gyro_angle_radians );
        vY = -vX * Math.sin( curr_gyro_angle_radians ) + vY * Math.cos( curr_gyro_angle_radians );
        vX = temp;
        
        try {
            double excessRatio = (double)1.0 / ( Math.abs(vX) + Math.abs(vY) + Math.abs(vRot) );
            if ( excessRatio < 1.0 )
            {
                vX      *= excessRatio;
                vY      *= excessRatio;
                vRot    *= excessRatio;
            }
            
            vRot *= (1/cRotK);
            vRot *= ROTATE_DIRECTION;
            
            SmartDashboard.putNumber( "vRot", vRot);
            double wheelSpeeds[] = new double[4];
            double velocities[] = new double[3];
            velocities[0] = vX;
            velocities[1] = vY;
            velocities[2] = vRot;
            
            mecanumDriveInvKinematics( velocities, wheelSpeeds );
            
            byte syncGroup = (byte)0x80;
            
            checkForRestartedMotor( leftFrontSC, "Front Left" );
            checkForRestartedMotor( rightFrontSC, "Front Right" );
            checkForRestartedMotor( leftRearSC, "Rear Left" );
            checkForRestartedMotor( rightRearSC, "Rear Right" );
            
            leftFrontSC.set(maxOutputSpeed * wheelSpeeds[0] * -1, syncGroup );
            rightFrontSC.set(maxOutputSpeed * wheelSpeeds[1], syncGroup);
            leftRearSC.set(maxOutputSpeed * wheelSpeeds[2] * -1, syncGroup);
            rightRearSC.set(maxOutputSpeed * wheelSpeeds[3], syncGroup);
			
			//CANTalon.updateSyncGroup(syncGroup);
            
            SmartDashboard.putNumber( "SpeedOut_FrontLeft", maxOutputSpeed * wheelSpeeds[0] * -1);
            SmartDashboard.putNumber( "SpeedOut_RearLeft", maxOutputSpeed * wheelSpeeds[2] * -1);
            SmartDashboard.putNumber( "SpeedOut_FrontRight", maxOutputSpeed * wheelSpeeds[1]);
            SmartDashboard.putNumber( "SpeedOut_RearRight", maxOutputSpeed * wheelSpeeds[3]);
            
            SmartDashboard.putNumber( "Speed_FrontLeft", leftFrontSC.getEncVelocity());
            SmartDashboard.putNumber( "Speed_RearLeft", leftRearSC.getEncVelocity());
            SmartDashboard.putNumber( "Speed_FrontRight", rightFrontSC.getEncVelocity());
            SmartDashboard.putNumber( "Speed_RearRight", rightRearSC.getEncVelocity());
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }        
    }

    protected double returnPIDInput() {
        double current_yaw = 0.0;
        if ( imu.isConnected() ) {
            current_yaw = imu.getYaw();
        }
        SmartDashboard.putNumber( "AutoRotatePIDInput", current_yaw);
        return current_yaw;
    }

    protected void usePIDOutput(double d) {
        next_autorotate_value = d;
        SmartDashboard.putNumber( "AutoRotatePIDOutput", next_autorotate_value);
    }
    
    public void setAutoRotation(boolean enable) {
        if ( enable ) {
            getPIDController().enable();
        }
        else {
            getPIDController().disable();
        }
    }
    
    public boolean getAutoRotation() {
        SmartDashboard.putBoolean( "AutoRotateEnabled", getPIDController().isEnable());
        return getPIDController().isEnable();
    }
    
    public void setFODEnabled(boolean enabled) {
        fod_enable = enabled;
    }
    
    public boolean getFODEnabled() {
        return fod_enable;
    }
}

