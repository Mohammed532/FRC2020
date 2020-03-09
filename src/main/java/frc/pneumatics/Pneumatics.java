/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.pneumatics;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Timer;
// import edu.wpi.first.wpilibj.Solenoid;
 

/**
 * Add your docs here.
 */
public class Pneumatics {
        boolean intakeOut = false;
        boolean climberOut = false;
        double motorUpSpeed =  0.0;
        double motorDownSpeed = 0.0;

        //Until we actually have a robot, almost all these are placeholders
        Compressor mainCompressor = new Compressor();
        DoubleSolenoid IntakePiston = new DoubleSolenoid(4, 5);
        DoubleSolenoid ClimberPiston = new DoubleSolenoid(0, 1);
        DoubleSolenoid ShooterPiston = new DoubleSolenoid(2, 3);
        //Placeholders have been switched for shooter and intake 
        /*public void Set() {
                DbleSolenoid.set(DoubleSolenoid.Value.kForward);
                DbleSolenoid.set(DoubleSolenoid.Value.kReverse);
                DbleSolenoid.set(DoubleSolenoid.Value.kOff);
        }*/

        public void startUp(){
                IntakePiston.set(DoubleSolenoid.Value.kReverse);
                ClimberPiston.set(DoubleSolenoid.Value.kReverse);
                ShooterPiston.set(DoubleSolenoid.Value.kReverse);

                motorUpSpeed = 0.0;
                motorDownSpeed = 0.0;

        }

        public void disable(){
                IntakePiston.set(DoubleSolenoid.Value.kOff);
                ClimberPiston.set(DoubleSolenoid.Value.kOff);
                ShooterPiston.set(DoubleSolenoid.Value.kOff);
        }

        public void Intake(XboxController sbox, Timer t) {

                boolean xButtonPress = false;

                if (sbox.getXButton() && !xButtonPress){
                        
                        xButtonPress = true;

                        if (!intakeOut){
                                IntakePiston.set(DoubleSolenoid.Value.kForward);
                                intakeOut = true;
                        } else {
                                IntakePiston.set(DoubleSolenoid.Value.kReverse);
                                intakeOut = false;
                        }

                } else if (!sbox.getBButton()){
                        xButtonPress = false;
                }
                

        }

        public void Shooter(XboxController sbox) {
                // When you press up on the D-pad
                if (sbox.getPOV() == 0) {
                        ShooterPiston.set(DoubleSolenoid.Value.kForward);
                // When you press down on the D-Pad  
              } if (sbox.getPOV() == 180) {
                        ShooterPiston.set(DoubleSolenoid.Value.kReverse);
              }
        }

        public void Climber(XboxController sbox, Spark motorUp, Spark motorDown) {
                if(sbox.getBButton()){
                        ClimberPiston.set(DoubleSolenoid.Value.kForward);
                        motorUpSpeed = 1.0;
                        motorDownSpeed = 0.0;
                }
                if(sbox.getAButton()){
                        ClimberPiston.set(DoubleSolenoid.Value.kReverse);
                        motorUpSpeed = 0.0;
                        motorDownSpeed = 1.0;
                }
                if(sbox.getStickButton(Hand.kRight)){
                        motorUpSpeed = 0.0;
                        motorDownSpeed = 1.0;
                }

                motorUp.set(motorUpSpeed);
                motorDown.set(motorDownSpeed);
        }
}


