/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.pneumatics;

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

        }

        public void Intake(XboxController sbox, Timer t) {
                
                boolean xButtonPress = false;
                // t.start();
                // System.out.println(intakeOut);s

                // double setTime = 1;
                // System.out.println(t.get());
                //penis sauce!\
                if (sbox.getXButton() && !xButtonPress){
                        
                        xButtonPress = true;

                        if (!intakeOut){
                                System.out.println("Intake is out");
                                ClimberPiston.set(DoubleSolenoid.Value.kForward);
                                intakeOut = true;
                        } else {
                                System.out.println("Intake is in");
                                ClimberPiston.set(DoubleSolenoid.Value.kReverse);
                                intakeOut = false;
                        }

                } else if (!sbox.getXButton()){
                        xButtonPress = false;

                }

                /*if (sbox.getXButton() && !intakeOut) { 
                        IntakePiston.set(DoubleSolenoid.Value.kForward);
                        System.out.println("hit1 " + t.get());
                        if (t.get() >= setTime){
                                 System.out.println("hit2");
                                t.reset();   
                                intakeOut = true;
                        }
                
                        IntakePiston.set(DoubleSolenoid.Value.kForward);
                        intakeOut = true;
                }else if (sbox.getXButton() && intakeOut) {
                        IntakePiston.set(DoubleSolenoid.Value.kReverse);
                        intakeOut = false; 

                        if (t.get() == setTime){
                                t.reset();   
                                intakeOut = false;
                        }
                    
                }*/

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

        public void Climber(XboxController sbox, Timer t) {
                System.out.println("climberOut");

                boolean bButtonPress = false;

                t.start();

                double setTime = 1;
                System.out.println(t.get());

                if (sbox.getBButton() && !bButtonPress){
                        
                        bButtonPress = true;

                        if (!climberOut){
                                System.out.println("Climber is out");
                                ClimberPiston.set(DoubleSolenoid.Value.kForward);
                                climberOut = true;
                        } else {
                                System.out.println("Climber is in");
                                ClimberPiston.set(DoubleSolenoid.Value.kReverse);
                                climberOut = false;
                        }

                } else if (!sbox.getBButton()){
                        bButtonPress = false;
                }

                /*if (sbox.getBButton() && !climberOut) {

                        if (!sbox.getBButtonPressed()){
                                ClimberPiston.set(DoubleSolenoid.Value.kForward); 
                                climberOut = true;
                        }
                        
                        // ClimberPiston.set(DoubleSolenoid.Value.kForward); 
                        // climberOut = true;

                        /*System.out.println("hit1 " + t.get());
                        if (t.get() >= setTime){
                                System.out.println("hit2");
                                t.reset();   
                                climberOut = true;
                        }*/

                
                /*}else if (sbox.getBButton() && climberOut) {
                        
                        if (!sbox.getBButtonPressed()){
                                ClimberPiston.set(DoubleSolenoid.Value.kReverse); 
                                climberOut = false;
                        }
                        
                        // ClimberPiston.set(DoubleSolenoid.Value.kReverse);
                        // climberOut = false;

                        /*System.out.println("hit1 " + t.get());
                        if (t.get() >= setTime){
                                System.out.println("hit2");
                                t.reset();   
                                climberOut = false;
                        }
                }*/
        }
}


