/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Timer;


/**
 * Add your docs here.
 */
public class Pneumatics {
        boolean intakeOut = false;
        boolean climberOut = false;

        //Until we actually have a robot, almost all these are placeholders
        Compressor mainCompressor = new Compressor();
        DoubleSolenoid IntakePiston = new DoubleSolenoid(0, 1);
        DoubleSolenoid ClimberPiston = new DoubleSolenoid(2, 3);
        DoubleSolenoid ShooterPiston = new DoubleSolenoid(4, 5);

        /*public void Set() {
                DbleSolenoid.set(DoubleSolenoid.Value.kForward);
                DbleSolenoid.set(DoubleSolenoid.Value.kReverse);
                DbleSolenoid.set(DoubleSolenoid.Value.kOff);
        }*/

        public void Intake(XboxController sbox, Timer t) {
                t.start();
                // System.out.println(intakeOut);

                double setTime = 0.5;
                // System.out.println(t.get());

                if (sbox.getXButton() && !intakeOut) { 
                        IntakePiston.set(DoubleSolenoid.Value.kForward);
                        System.out.println("hit1 " + t.get());
                        if (t.get() >= setTime){
                                System.out.println("hit2");
                                t.reset();   
                                intakeOut = true;
                        }
                        
                        // IntakePiston.set(DoubleSolenoid.Value.kForward);
                        // intakeOut = true;
                }else if (sbox.getXButton() && intakeOut) {
                        IntakePiston.set(DoubleSolenoid.Value.kReverse);
                        intakeOut = false; 

                        if (t.get() == setTime){
                                t.reset();   
                                intakeOut = false;
                        }
                    
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

        public void Climber(XboxController sbox) {
                System.out.println("climberOut");

                if (sbox.getBButtonPressed() && !climberOut) {
                        ClimberPiston.set(DoubleSolenoid.Value.kForward);
                        climberOut = true;

                
                }else if (sbox.getBButtonPressed() && climberOut) {
                        ClimberPiston.set(DoubleSolenoid.Value.kReverse);
                        climberOut = false; 
                }
        }
}


