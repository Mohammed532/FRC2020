
package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.DigitalInput;


import frc.pneumatics.Pneumatics;
// import frc.auto.PID;
import frc.color.ColorSensor;

public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  private final XboxController c_xbox = new XboxController(0); //Xbox controller port is a placeholder
  private final DifferentialDrive robotDrive = new DifferentialDrive(new Spark(0), new Spark(1)); //Sparks have placeholder values
  private final DifferentialDrive ShooterWheels = new DifferentialDrive(new Spark(2), new Spark(3)); 
  private final Spark InWheels = new Spark(4); //SUCC=INTAKE WHEELS
  private final Spark climberMotor = new Spark(5);
  private final Timer autoTimer = new Timer();
  private final Ultrasonic ultra = new Ultrasonic(0, 1);
  private final Timer teleITimer = new Timer();
  private final Timer teleCTimer = new Timer();

  // private final DigitalInput climberSwitch = new DigitalInput(1);

  private final Pneumatics Pneumatics = new Pneumatics();
  private final ColorSensor colorSensor = new ColorSensor();

  double Speed = 0.6;

  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser); 
  }

  @Override
  public void robotPeriodic(){
    colorSensor.ColorSensorPeriodic(c_xbox);
    colorSensor.ColorSensorInit();
  }
    
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);

    teleCTimer.start();
    double cTime = teleCTimer.get();
    
    while (cTime <= 5){
      climberMotor.set(-0.25);

    }


    ultra.setAutomaticMode(true);
    autoTimer.start();
  }

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
    Pneumatics.startUp();
    climberMotor.set(0.0);

  }


  @Override
  public void teleopPeriodic() {
    
    Pneumatics.Intake(c_xbox, teleITimer);
    Pneumatics.Shooter(c_xbox);
    Pneumatics.Climber(c_xbox, climberMotor);

    double lYAxis = c_xbox.getRawAxis(1);
    double RT = c_xbox.getTriggerAxis(Hand.kRight);
    boolean RB = c_xbox.getBumper(Hand.kRight);
    boolean LB = c_xbox.getBumper(Hand.kLeft);
    double rXAxis = c_xbox.getRawAxis(4);
    // double rYAxis = c_xbox.getRawAxis(5); 
    double speedMod = getspeedMod(c_xbox);

    boolean LBPress = false;
    int LBCount;

    robotDrive.arcadeDrive(lYAxis * speedMod, rXAxis * speedMod);
    //placeholder values por outake :)

    if (RT > 0.35){
      ShooterWheels.arcadeDrive(1.0, 0);
    }

    if (RB){
      ShooterWheels.arcadeDrive(-1.0, 0);
      InWheels.set(-1.0);

    }
    
    if (LB){
      climberMotor.set(0.25);

    }

    /*if (LB && !climberSwitch.get()){
      while (!climberSwitch.get()){
        climberMotor.set(1.0);

      }

    }else if (LB){
      climberMotor.set(-1.0);

    }*/

   // m_myRobot.arcadeDrive(m_leftStick.getY(), m_rightStick.getY(Hand.kRight));
  }

  public double getspeedMod(XboxController xbox) {
    boolean ybutton = xbox.getYButton();
    boolean yButtonPress = false;

    if (ybutton && !yButtonPress){
                        
      yButtonPress = true;

      if (Speed == 0.6){
        Speed = 1.0;
      } else if (Speed == 1.0){
        Speed = 0.6;
      }
    
    } else if (!ybutton){
        yButtonPress = false;

    }

    return Speed; 
  }

}

