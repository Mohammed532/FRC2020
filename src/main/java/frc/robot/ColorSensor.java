package frc.robot;

import java.nio.ByteBuffer;
import edu.wpi.first.wpilibj.I2C;

public class ColorSensor{
    final I2C sensor = new I2C(port, 0x39);
    ByteBuffer buf = ByteBuffer.allocate(5);

    public ColorSensor(/*I2C.Port port*/) {
        sensor = new ; //port, I2c address    
    
        sensor.write(0x00, 0b00000011); //power on, color sensor on
    }
     
    public int m_read(){
        //reads the address 0x16, 2 bytes of data, gives it to the buffer
        sensor.read(0x16, 3, buf);
        return buf.get(0);
    } 
}

    