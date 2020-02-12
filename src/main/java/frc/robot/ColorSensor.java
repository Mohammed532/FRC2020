/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.revrobotics.ColorSensorV3;
import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.GenericHID;
/**
 * Add your docs here.
 */
public class ColorSensor {
    private final I2C.Port i2Port = I2C.Port.kOnboard;
    private final ColorSensorV3 s_color = new ColorSensorV3(i2Port);
    private ColorMatch colormatcher = new ColorMatch();
    private final Color kRedTarget = ColorMatch.makeColor(0.54, 0.33, 0.13);
    private final Color kGreenTarget = ColorMatch.makeColor(0.16, 0.57, 0.26); 
    private final Color kBlueTarget = ColorMatch.makeColor(0.12, 0.40, 0.47);
    private final Color kYellowTarget = ColorMatch.makeColor(0.31, 0.56, 0.13);
    private final SendableChooser<String> m_chooser = new SendableChooser<>();
    private static final String kDefaultAuto = "Default";
    private static final String kCustomAuto = "My Auto";


    Color detectedColor;
    double IR;
    
    
    public void robotInit() {
        m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
        m_chooser.addOption("My Auto", kCustomAuto);
        SmartDashboard.putData("Auto choices", m_chooser);
        colormatcher.addColorMatch(kBlueTarget);
        colormatcher.addColorMatch(kRedTarget);
        colormatcher.addColorMatch(kGreenTarget);
        colormatcher.addColorMatch(kYellowTarget);
    }
    
        public void robotPeriodic() {
            Color detectedColor = s_color.getColor();
            IR = s_color.getIR();
            // redEntry.setDouble(detectedColor.red);
            // greenEntry.setDouble(detectedColor.green);
            // blueEntry.setDouble(detectedColor.blue);
            // irEntry.setDouble(IR);
            
            // RGB Values
            //blue = 0.120605 0.407227 0.472412
            //green = 0.163086 0.573975 0.262939
            //red = 0.542236 0.327393 0.130127
            //yellow = 0.317871 0.556641 0.125488 
            System.out.println("ok");
            String colorString;
            ColorMatchResult match = colormatcher.matchClosestColor(detectedColor);
        
        
            if (match.color == kBlueTarget){
              colorString = "Blue";
            } else if (match.color == kRedTarget) {
              colorString = "Red";
            } else if (match.color == kGreenTarget) {
              colorString = "Green";
            } else if (match.color == kYellowTarget) {
              colorString = "Yellow";
            } else {
              colorString = "Unknown";
            }
            SmartDashboard.putNumber("Red", detectedColor.red);
            SmartDashboard.putNumber("Green", detectedColor.green);
            SmartDashboard.putNumber("Blue", detectedColor.blue);
            SmartDashboard.putNumber("Confidence", match.confidence);
            SmartDashboard.putString("Detected Color", colorString);
            SmartDashboard.putNumber("IR", IR);

            int proximity = s_color.getProximity();

            SmartDashboard.putNumber("Proximity", proximity);
         }
    }

