package first.robot.mechanisms;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.hardware.Pigeon2;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.MotorAlignmentValue;
import first.robot.RobotConstants;
import first.robot.simulation.DrivetrainSim;
import java.util.function.DoubleSupplier;
import org.wpilib.command3.Command;
import org.wpilib.command3.Mechanism;
import org.wpilib.drive.DifferentialDrive;
import org.wpilib.framework.RobotBase;

public class Drive implements Mechanism {
  private final TalonFX leftLeader = new TalonFX(0, RobotConstants.can2);
  private final TalonFX leftFollower = new TalonFX(1, RobotConstants.can2);
  private final TalonFX rightLeader = new TalonFX(2, RobotConstants.can2);
  private final TalonFX rightFollower = new TalonFX(3, RobotConstants.can2);
  private final Pigeon2 pigeon = new Pigeon2(0, RobotConstants.can2);

  private final DrivetrainSim sim = new DrivetrainSim(leftLeader, rightLeader);

  private final DifferentialDrive drive =
      new DifferentialDrive(leftLeader::setThrottle, rightLeader::setThrottle);

  public Drive() {
    TalonFXConfiguration leftConfig = new TalonFXConfiguration();
    TalonFXConfiguration rightConfig = new TalonFXConfiguration();

    leftConfig.MotorOutput.withInverted(InvertedValue.Clockwise_Positive);
    rightConfig.MotorOutput.withInverted(InvertedValue.CounterClockwise_Positive);

    leftLeader.getConfigurator().apply(leftConfig);
    leftFollower.getConfigurator().apply(leftConfig);
    rightLeader.getConfigurator().apply(rightConfig);
    rightFollower.getConfigurator().apply(rightConfig);

    leftFollower.setControl(new Follower(leftLeader.getDeviceID(), MotorAlignmentValue.Aligned));
    rightFollower.setControl(new Follower(rightLeader.getDeviceID(), MotorAlignmentValue.Aligned));

    setDefaultCommand(arcadeDrive(() -> 0, () -> 0));
  }

  public Command arcadeDrive(DoubleSupplier x, DoubleSupplier y) {
    return run(coroutine -> {
          while (true) {
            drive.arcadeDrive(-x.getAsDouble(), y.getAsDouble());
            coroutine.yield();
          }
        })
        .named("ArcadeDrive");
  }

  public void periodic() {
    if (RobotBase.isSimulation()) {
      sim.periodic();
    }
  }
}
