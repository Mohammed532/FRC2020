
package frc.robot;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  private final DifferentialDrive robotDrive = new DifferentialDrive(new Spark(0), new Spark(1)); //Sparks have placeholder values
  private final XboxController c_xbox = new XboxController(0); //Xbox controller port is a placeholder
  private final Timer timer = new Timer();

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
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
  @Override
  public void robotPeriodic() {
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to
   * the switch structure below with additional strings. If using the
   * SendableChooser make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
  }

  @Override
  public void teleopInit() {
    double speedMod = 0.6;
  }
  
  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    double lXAxis = c_xbox.getRawAxis(1);
    double lYAxis = c_xbox.getRawAxis(0);
    getspeedMod();
    robotDrive.arcadeDrive(lXAxis * speedMod,lYAxis * speedMod);
   // m_myRobot.arcadeDrive(m_leftStick.getY(), m_rightStick.getY(Hand.kRight));
  }

  public double getspeedMod() {
    boolean ybutton = c_xbox.getYButton();
    boolean bbutton = c_xbox.getBButton();
    boolean abutton = c_xbox.getAButton();
  
    if(ybutton) {
      
      System.out.println ("Y button is pressed female dog")
      return 0.75;
  
    }
    if(bbutton) { 
      
      System.out.println ("B button is pressed coronavirus")
      return 0.50;
  
    }
    if(abutton) {
      
      System.out.println ("A button is pressed black boy")
      return 0.25;
  
    }
  
    return speedMod; 

    }
  
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
