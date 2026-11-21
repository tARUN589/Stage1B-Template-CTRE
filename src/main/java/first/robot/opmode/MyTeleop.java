/*
 * Copyright 2026 FRCSoftware
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */
package first.robot.opmode;

import first.robot.Robot;
import first.robot.RobotConstants;
import org.wpilib.command3.Scheduler;
import org.wpilib.command3.button.CommandXboxController;
import org.wpilib.opmode.PeriodicOpMode;
import org.wpilib.opmode.Teleop;

@Teleop
public class MyTeleop extends PeriodicOpMode {
  private final Robot robot;

  private final CommandXboxController driverController =
      new CommandXboxController(RobotConstants.driverControllerPort);

  /** The Robot instance is passed into the opmode via the constructor. */
  public MyTeleop(Robot robot) {
    this.robot = robot;

    driverController
        .leftBumper()
        .whileTrue(robot.feeder.intake())
        .whileTrue(robot.intakeLauncher.intake());
    driverController
        .rightBumper()
        .whileTrue(robot.feeder.feed())
        .whileTrue(robot.intakeLauncher.shoot());
    driverController
        .a()
        .whileTrue(robot.feeder.outtake())
        .whileTrue(robot.intakeLauncher.outtake());

    robot.drive.setDefaultCommand(
        robot.drive.arcadeDrive(
            () -> driverController.getLeftY(), () -> driverController.getRightX()));
  }

  @Override
  public void periodic() {
    /* Called periodically (set time interval) while the robot is enabled. */
    Scheduler.getDefault().run();
  }
}
