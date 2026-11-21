package first.robot;

import com.ctre.phoenix6.CANBus;

public class Ports {
  public final int id;
  public final CANBus bus;

  public Ports(int id, CANBus bus) {
    this.id = id;
    this.bus = bus;
  }

  public static final Ports feeder = new Ports(4, RobotConstants.can0);
  public static final Ports intakeLauncher = new Ports(5, RobotConstants.can0);
}
