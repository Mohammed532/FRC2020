
package frc.robot;

import com.revrobotics.ColorSensorV3;
import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
// import edu.wpi.first.networktables.NetworkTableEntry;
// import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.TimedRobot;
// import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
// import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.util.Color;

public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  private final DifferentialDrive robotDrive = new DifferentialDrive(new Spark(0), new Spark(1)); //Sparks have placeholder values
  private final XboxController c_xbox = new XboxController(0); //Xbox controller port is a placeholder
  private final Timer timer = new Timer();
  private final I2C.Port i2Port = I2C.Port.kOnboard;
  private final ColorSensorV3 s_color = new ColorSensorV3(i2Port);
  private ColorMatch colormatcher = new ColorMatch();
  private final Color kRedTarget = ColorMatch.makeColor(0.54, 0.33, 0.13);
  private final Color kGreenTarget = ColorMatch.makeColor(0.16, 0.57, 0.26); 
  private final Color kBlueTarget = ColorMatch.makeColor(0.12, 0.40, 0.47);
  private final Color kYellowTarget = ColorMatch.makeColor(0.31, 0.56, 0.13);
  
  
  
  // private ShuffleboardTab dataTab = Shuffleboard.getTab("Sensor");
  // private NetworkTableEntry redEntry, greenEntry, blueEntry, irEntry;
  Color detectedColor;
  double IR;
  


  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
    colormatcher.addColorMatch(kBlueTarget);
    colormatcher.addColorMatch(kRedTarget);
    colormatcher.addColorMatch(kGreenTarget);
    colormatcher.addColorMatch(kYellowTarget);
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

  /**
   * This function is called periodically during operator control.
   */
  // public void update() {
  //  redEntry = dataTab
  //  .add("colorSensor/red", detectedColor.red)
  //  .getEntry();
  //  greenEntry = dataTab
  //  .add("colorSensor/green", detectedColor.green)
  //     .getEntry(); 
  //   blueEntry = dataTab
  //   .add("colorSensor/blue", detectedColor.blue)
  //     .getEntry();
  //   irEntry = dataTab
  //   .add("colorSensor/ir", IR)
  //     .getEntry();
  // }

  @Override
  public void teleopPeriodic() {
  

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
  int proximity = s_color.getProximity();

  SmartDashboard.putNumber("Proximity", proximity);

   }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
