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

import edu.wpi.first.wpilibj.command.CommandGroup;

import org.usfirst.frc1735.PowerUp2018.Robot;
import org.usfirst.frc1735.PowerUp2018.subsystems.*;

/**
 *
 */
public class AutoRRR extends CommandGroup {


    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=PARAMETERS
    public AutoRRR() {

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=PARAMETERS
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=COMMAND_DECLARATIONS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=COMMAND_DECLARATIONS
 
    	// The plan:
    	// Coding assumes we start in the right corner of the field (against the DS wall where it turns at the Portal
    	//
    	// RRR (Pirate mode!!) means both Switch and Scale are on our side.
    	// 1) Deliver to scale
    	// 2) turn and grab the outermost cube against the switch fence
    	// 3) Deliver that cube to the switch
    	
    	addSequential(new ResetGyro());
    	addSequential(new DriveWithPID(156)); //drive forward (in inches)
    	addSequential(new Turn(-26.6, DriveTrain.kAbsolute));
    	addSequential(new DriveWithPID(108.4));
    	addSequential(new Turn(0, DriveTrain.kAbsolute));
    	addSequential(new DriveWithPID(5));
    	addSequential(new Wait(2)); //Placeholder for cube delivery
    	addSequential(new DriveWithPID(-5));
    	addSequential(new Turn(175, DriveTrain.kAbsolute));
    	addSequential(new Turn(0, DriveTrain.kCamera)); // angle is not used; it is grabbed from the camera
    } 
}