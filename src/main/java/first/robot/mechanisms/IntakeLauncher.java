package first.robot.mechanisms;

import static org.wpilib.units.Units.Seconds;

import com.ctre.phoenix6.hardware.TalonFX;
import first.robot.Ports;
import first.robot.simulation.SingleFlywheelSim;
import org.wpilib.command3.Command;
import org.wpilib.command3.Mechanism;
import org.wpilib.framework.RobotBase;

public class IntakeLauncher implements Mechanism {
  private final TalonFX intakeLauncher =
      new TalonFX(Ports.intakeLauncher.id, Ports.intakeLauncher.bus);

  private final SingleFlywheelSim sim = SingleFlywheelSim.forIntakeLauncher(intakeLauncher);

  public IntakeLauncher() {
    setDefaultCommand(idle());
  }

  public Command shoot() {
    return run(coroutine -> {
          coroutine.wait(Seconds.of(2));
          while (true) {
            intakeLauncher.setThrottle(0.9);
            coroutine.yield();
          }
        })
        .named("Shoot");
  }

  public Command intake() {
    return run(coroutine -> {
          while (true) {
            intakeLauncher.setThrottle(0.8);
            coroutine.yield();
          }
        })
        .named("Intake");
  }

  public Command outtake() {
    return run(coroutine -> {
          while (true) {
            intakeLauncher.setThrottle(-0.8);
            coroutine.yield();
          }
        })
        .named("Outtake");
  }

  public Command idle() {
    return run(coroutine -> {
          while (true) {
            intakeLauncher.setThrottle(0);
            coroutine.yield();
          }
        })
        .named("IntakeLauncherIdle");
  }

  public void periodic() { // Update the simulation
    if (RobotBase.isSimulation()) {
      sim.periodic();
    }
  }
}
