// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc1735.PowerUp2018.commands;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc1735.PowerUp2018.Robot;
import org.usfirst.frc1735.PowerUp2018.RobotMap;

/**
 *
 */
public class Turn extends Command {

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS
    private double m_angle;
    private boolean m_isAbsolute;
 
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
    public Turn(double angle, boolean isAbsolute) {

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING
        m_angle = angle;
        m_isAbsolute = isAbsolute;

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
        requires(Robot.driveTrain);

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
    	//@FIXME for debug ONLY:
    	//In order to do quick testing of the full system without a huge amount of recompiling and deployment, let's expose the critical info to SmartDashboard:
    	//m_isAbsolute = SmartDashboard.getBoolean("TurnAbsolute", false); // Assume relative
    	//m_angle      = SmartDashboard.getNumber("TurnAngle", 0); // Assuming relative, zero means no change.
    	//Robot.driveTrain.drivelineController.setP(SmartDashboard.getNumber("TurnP", 0.14));
    	//Robot.driveTrain.drivelineController.setI(SmartDashboard.getNumber("TurnI", 0));
    	//Robot.driveTrain.drivelineController.setD(SmartDashboard.getNumber("TurnD", 0));
    	Robot.driveTrain.drivelineController.setAbsoluteTolerance(SmartDashboard.getNumber("TurnErr", 0.5));
    	
    	if (!m_isAbsolute) {
    		// We are in RELATIVE mode here
        	double startAbsAngle = Robot.ahrs.getYaw(); // Get our current abs angle
        	m_targetAbsAngle = startAbsAngle + m_angle; // calc our new abs angle based on the relative turn we received as a param
        	if (m_targetAbsAngle > 180.0) {
        		m_targetAbsAngle = m_targetAbsAngle - 360.0;// Correct the angle to lie within -180:180 
        	}
        	if (m_targetAbsAngle < -180.0) {
        		m_targetAbsAngle = m_targetAbsAngle + 360.0;// Correct the angle to lie within -180:180 
        	}
    	}
    	else {
    		//We are in abs mode here
    		m_targetAbsAngle = m_angle; //abs angle was passed to us
    	}    	
    	
    	// Finally, enable the turn controller
    	Robot.driveTrain.drivelineController.setSetpoint(m_targetAbsAngle);	
    	Robot.driveTrain.drivelineController.enable();
    	System.out.println("Absolute= " + m_isAbsolute + " ReqAngle= " + m_angle + " absTarget= " + m_targetAbsAngle);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
    	double err = Robot.driveTrain.drivelineController.getError();
    	// For small errors (say, less than 5 degrees), run the PID with a low max and a high P-- this gets us a pretty constant but slow speed for small adjustments
    	// This avoids the "small P -> small motor values -> rely on very slow I values to get to the target
    	if (Math.abs(err) < Robot.driveTrain.kSmallTurnPIDLimit /*degrees*/) {
    		Robot.driveTrain.setSmallTurnPID();
    		SmartDashboard.putString("PID Mode", "Small");
    	}
    	else if (Math.abs(err) < Robot.driveTrain.kMedTurnPIDLimit /*degrees*/) {
    		Robot.driveTrain.setMedTurnPID();
    		SmartDashboard.putString("PID Mode", "Med");
    	}
    	else {
    		Robot.driveTrain.setLargeTurnPID(); // Otherwise use the normal PID values  
    		SmartDashboard.putString("PID Mode", "Large");
    	}
    	RobotMap.driveTrainDifferentialDrive1.tankDrive(Robot.driveTrain.m_rotateToAngleRate, -Robot.driveTrain.m_rotateToAngleRate, false);
    	SmartDashboard.putNumber("MotorOut", Robot.driveTrain.m_rotateToAngleRate);
    	SmartDashboard.putNumber("PIDErr", err);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return (Robot.driveTrain.drivelineController.onTarget()) && // Ask controller if our current position is "close enough"
        		(Math.abs(Robot.driveTrain.m_rotateToAngleRate) < Robot.driveTrain.kSmallTurnPIDOutputMax); // And the motors are low to no output, so we aren't actively oscillating around the target value
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
    	Robot.driveTrain.drivelineController.disable(); // Stop the turn controller
    	Robot.driveTrain.stop(); // Stop the motors;
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
    	end();
    }
    
    //Member variables
    double m_targetAbsAngle; //the field relative angle that we want to end up at
}
