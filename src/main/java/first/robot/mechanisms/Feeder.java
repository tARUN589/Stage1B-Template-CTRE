package first.robot.mechanisms;

import com.ctre.phoenix6.hardware.TalonFX;
import first.robot.Ports;
import first.robot.simulation.SingleFlywheelSim;
import org.wpilib.command3.Command;
import org.wpilib.command3.Mechanism;
import org.wpilib.framework.RobotBase;

public class Feeder implements Mechanism {
  private final TalonFX feeder = new TalonFX(Ports.feeder.id, Ports.feeder.bus);

  private final SingleFlywheelSim sim = SingleFlywheelSim.forFeeder(feeder);

  public Feeder() {
    setDefaultCommand(idle());
  }

  public Command feed() {
    return run(coroutine -> {
          while (true) {
            feeder.setThrottle(0.75);
            coroutine.yield();
          }
        })
        .named("Feed");
  }

  public Command intake() {
    return run(coroutine -> {
          while (true) {
            feeder.setThrottle(-1);
            coroutine.yield();
          }
        })
        .named("Intake");
  }

  public Command outtake() {
    return run(coroutine -> {
          while (true) {
            feeder.setThrottle(1);
            coroutine.yield();
          }
        })
        .named("Outtake");
  }

  public Command idle() {
    return run(coroutine -> {
          while (true) {
            feeder.setThrottle(0);
            coroutine.yield();
          }
        })
        .named("FeederIdle");
  }

  public void periodic() {
    if (RobotBase.isSimulation()) {
      sim.periodic();
    }
  }
}
