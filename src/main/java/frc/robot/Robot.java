
package frc.robot;



// import com.revrobotics.ColorSensorV3;
// import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import java.util.Set;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
// import edu.wpi.first.networktables.NetworkTableEntry;
// import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
// import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
// import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.MedianFilter;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Compressor;
import com.revrobotics.ColorSensorV3;
import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import edu.wpi.first.wpilibj.util.Color;

import frc.robot.Pneumatics;

public class Robot extends TimedRobot {
  
  double Speed;
  
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  private final DifferentialDrive robotDrive = new DifferentialDrive(new Spark(0), new Spark(1)); //Sparks have placeholder values
  private final XboxController c_xbox = new XboxController(0); //Xbox controller port is a placeholder
  private final ColorSensor colorSensor = new ColorSensor();
  
  
  // private ShuffleboardTab dataTab = Shuffleboard.getTab("Sensor");
  // private NetworkTableEntry redEntry, greenEntry, blueEntry, irEntry;
 
 
  private final DifferentialDrive ShooterWheels = new DifferentialDrive(new Spark(2), new Spark(3)); 
  private final Spark InWheels = new Spark(4); //SUCC=INTAKE WHEELS
  private final Timer autoTimer = new Timer();
  private final Pneumatics Pneumatics = new Pneumatics();
  private final Ultrasonic ultra = new Ultrasonic(0, 1);
  private final Timer teleTimer = new Timer();

  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser); 
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */

  //  public void ColorMatch() {


  //  }
  @Override
  public void robotPeriodic(){
    colorSensor.ColorSensorPeriodic(c_xbox);
    colorSensor.ColorSensorInit();
  }
    
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);

    ultra.setAutomaticMode(true);
    autoTimer.start();
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    
    double range = ultra.getRangeInches();
    double time = autoTimer.get();
    

    if (range >= 10){
      robotDrive.arcadeDrive(0.55, 0);
    } else if (time <= 15){
      robotDrive.arcadeDrive(0, 0.55);
    }

  }

  @Override
  public void teleopInit() {
  }


  @Override
  public void teleopPeriodic() {
    
    Pneumatics.Intake(c_xbox, teleTimer);
    Pneumatics.Shooter(c_xbox);
  
    double lXAxis = c_xbox.getRawAxis(0);
    double lYAxis = c_xbox.getRawAxis(1);
    double RT = c_xbox.getTriggerAxis(Hand.kRight);
    boolean RB = c_xbox.getBumper(Hand.kRight);
    double rXAxis = c_xbox.getRawAxis(4);
    // double rYAxis = c_xbox.getRawAxis(5); 
    double speedMod = getspeedMod(c_xbox);

    robotDrive.arcadeDrive(lYAxis * speedMod, rXAxis * speedMod);
    //placeholder values por outake :)

    if (RT > 0.35){
      ShooterWheels.arcadeDrive(1.0, 0);
    }

    if (RB){
      ShooterWheels.arcadeDrive(-1.0, 0);
      InWheels.set(-1.0);

    }
    

   // m_myRobot.arcadeDrive(m_leftStick.getY(), m_rightStick.getY(Hand.kRight));
  }

  public double getspeedMod(XboxController xbox) {
    boolean ybutton = xbox.getYButton();
    //boolean bbutton = xbox.getBButton();
    //boolean abutton = xbox.getAButton();

    boolean yButtonPressedTwice = false;

    if(ybutton && !yButtonPressedTwice) {
      
      // System.out.println ("Y button is pressed female dog");
      Speed = 0.75;
      yButtonPressedTwice = true;

    }else if (ybutton && yButtonPressedTwice){
      // System.out.println ("ybutton2x");
      Speed = 0.5;
      yButtonPressedTwice = false;
    }

   /* if(bbutton) { 
      
      System.out.println ("B button is pressed coronavirus");
      Speed = 0.50;
  
    }
    if(abutton) {
      
      System.out.println ("A button is pressed black boy");
      Speed = 0.25;
  
    } */

    return Speed; 

  }
  

  /**
   * In addition to RGB IR values, the color sensor can also return an 
   * infrared proximity value. The chip contains an IR led which will emit
   * IR pulses and measure the intensity of the return. When an object is 
   * close the value of the proximity will be large (max 2047 with default
   * settings) and will approach zero when the object is far away.
   * 
   * Proximity can be used to roughly approximate the distance of an object
   * or provide a threshold for when an object is close enough to provide
   * accurate color values.
   */
  


   
   
   

  /**
   * This function is called periodically during test mode.
   */

  @Override
  public void testPeriodic() {
 
  }


}

