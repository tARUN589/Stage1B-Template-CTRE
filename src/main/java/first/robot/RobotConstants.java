package first.robot;

import com.ctre.phoenix6.CANBus;
import org.wpilib.hardware.bus.CANPort;

public class RobotConstants {
  public static final CANBus can0 = new CANBus(CANPort.CAN_S0);
  public static final CANBus can1 = new CANBus(CANPort.CAN_S1);
  public static final CANBus can2 = new CANBus(CANPort.CAN_S2);
  public static final CANBus can3 = new CANBus(CANPort.CAN_S3);
  public static final CANBus can4 = new CANBus(CANPort.CAN_S4);

  public static final int driverControllerPort = 0;
}
