
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

  private double intSpeed;

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
    intSpeed = 0.5;
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    boolean yButtonPressed = c_xbox.getYButtonPressed();
    boolean bbuttonPressed = c_xbox.getBButtonPressed();
    boolean abuttonPressed = c_xbox.getAButtonPressed(); 
    double lXAxis = c_xbox.getRawAxis(1);
    double lYAxis = c_xbox.getRawAxis(0);

    boolean abutton = false;
    boolean bbutton = false;
    boolean ybutton = false;

    double speedMod = intSpeed;
    // String anyButtonPressed = "";

    if (yButtonPressed){
      ybutton = true;
      bbutton = false;
      abutton = false;
    } else if (bbuttonPressed){
      ybutton = false;
      bbutton = true;
      abutton = false;
    } else if (abuttonPressed){
      ybutton = false;
      bbutton = false;
      abutton = true;
    }

    if(ybutton) {
      
      System.out.println ("Y button is pressed female dog");
      speedMod = 0.75;
  
    }
    if(bbutton) { 
      
      System.out.println ("B button is pressed coronavirus");
      speedMod = 0.50;
  
    }
    if(abutton) {
      
      System.out.println ("A button is pressed black boy");
      speedMod = 0.25;
  
    }

    robotDrive.arcadeDrive(lXAxis * speedMod,lYAxis * speedMod);

    /*if (ybutton || bbutton || abutton){
      anyButtonPressed = "true";
    }*/

    /*if (anyButtonPressed != "true"){
      speedMod = intSpeed;
      robotDrive.arcadeDrive(lXAxis * speedMod,lYAxis * speedMod);

    }else if (anyButtonPressed == "true"){
      
      if(ybutton) {
      
        System.out.println ("Y button is pressed female dog");
        speedMod = 0.75;
    
      }
      if(bbutton) { 
        
        System.out.println ("B button is pressed coronavirus");
        speedMod = 0.50;
    
      }
      if(abutton) {
        
        System.out.println ("A button is pressed black boy");
        speedMod = 0.25;
    
      }

      robotDrive.arcadeDrive(lXAxis * speedMod,lYAxis * speedMod);

    }*/
    

    /*if(ybutton) {
      
      System.out.println ("Y button is pressed female dog");
      speedMod = 0.75;
  
    }
    if(bbutton) { 
      
      System.out.println ("B button is pressed coronavirus");
      speedMod = 0.50;
  
    }
    if(abutton) {
      
      System.out.println ("A button is pressed black boy");
      speedMod = 0.25;
  
    }*/

    // robotDrive.arcadeDrive(lXAxis * speedMod,lYAxis * speedMod);

   // m_myRobot.arcadeDrive(m_leftStick.getY(), m_rightStick.getY(Hand.kRight));
  }

  /*public double getspeedMod(boolean AButton, boolean BButton, boolean YButton) {
    boolean ybutton = c_xbox.getYButton();
    boolean bbutton = c_xbox.getBButton();
    boolean abutton = c_xbox.getAButton();
  
    boolean yButtonPressed = YButton;
    boolean bButtonPressed = BButton;
    boolean aButtonPressed = AButton;


    if(yButtonPressed) {
      
      System.out.println ("Y button is pressed female dog");
      intSpeed = 0.75;
  
    }
    if(bButtonPressed) { 
      
      System.out.println ("B button is pressed coronavirus");
      intSpeed = 0.50;
  
    }
    if(aButtonPressed) {
      
      System.out.println ("A button is pressed black boy");
      intSpeed = 0.25;
  
    }

    return intSpeed;

  }*/
  

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
