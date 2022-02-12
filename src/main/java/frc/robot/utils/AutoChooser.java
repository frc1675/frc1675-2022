package frc.robot.utils;

import java.util.Map;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.auto.Area1GetBall1;
import frc.robot.commands.auto.Area2GetBall1;
import frc.robot.commands.auto.Area2GetBall2;
import frc.robot.commands.auto.Area3GetBall2;
import frc.robot.commands.auto.Area3GetBall3;
import frc.robot.commands.auto.Area4GetBall3;
import frc.robot.commands.auto.ScoreThenTaxi;
import frc.robot.commands.auto.TurnToAngle;
import frc.robot.commands.auto.DriveThenScoreThenTaxi;
import frc.robot.subsystems.Cage;
import frc.robot.subsystems.Catapult;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Intake;

public class AutoChooser {
    public enum StartPosition {
        TOUCHING_HUB,
        BEHIND_HUB,
        AREA_1,
        AREA_2,
        AREA_3,
        AREA_4
    }

    public enum SelectedBall {
        NONE,
        BALL_1,
        BALL_2,
        BALL_3
    }

    private Drive drive;
    private Intake intake;
    private Cage cage;
    private Catapult catapult;

    private ShuffleboardTab autoTab = Shuffleboard.getTab("Choose auto routine");

    private SendableChooser<StartPosition> startPositionChooser = new SendableChooser<StartPosition>();
    private SendableChooser<SelectedBall> selectedBallsChooser = new SendableChooser<SelectedBall>();

    private NetworkTableEntry waitSlider = autoTab.add("Wait time", 2)
    .withWidget(BuiltInWidgets.kNumberSlider)
    .withProperties(Map.of("min", 0, "max", 10, "block increment", .5))
    .withSize(2, 1)
    .withPosition(0, 0)
    .getEntry();

    private String message;

    public AutoChooser(Drive drive, Intake intake, Cage cage, Catapult catapult) {
        this.drive = drive;
        this.intake = intake;
        this.cage = cage;
        this.catapult = catapult;

        startPositionChooser.setDefaultOption("Touching hub", StartPosition.TOUCHING_HUB);
        startPositionChooser.addOption("Robot in front of us touching hub", StartPosition.BEHIND_HUB);
        startPositionChooser.addOption("Start area 1", StartPosition.AREA_1);
        startPositionChooser.addOption("Start area 2", StartPosition.AREA_2);
        startPositionChooser.addOption("Start area 3", StartPosition.AREA_3);
        startPositionChooser.addOption("Start area 4", StartPosition.AREA_4);
        autoTab.add("Starting position", startPositionChooser)
        .withWidget(BuiltInWidgets.kComboBoxChooser)
        .withSize(2, 1).
        withPosition(0, 1);

        selectedBallsChooser.setDefaultOption("Get no balls", SelectedBall.NONE);
        selectedBallsChooser.addOption("Get ball 1", SelectedBall.BALL_1);
        selectedBallsChooser.addOption("Get ball 2", SelectedBall.BALL_2);
        selectedBallsChooser.addOption("Get ball 3", SelectedBall.BALL_3);
        autoTab.add("Which ball to get", selectedBallsChooser)
        .withWidget(BuiltInWidgets.kComboBoxChooser)
        .withSize(2, 1)
        .withPosition(0, 2);

        autoTab.addString("Selected auto path", () -> message);

        SmartDashboard.putData(startPositionChooser);
        SmartDashboard.putData(selectedBallsChooser);
    }

    //displays what the current auto routine is, or an error if a combination with
    //no routine is selected.
    //runs in disabledPeriodic.
    public void checkAutoPath() {
        StartPosition selectedStart = (StartPosition)startPositionChooser.getSelected();
        SelectedBall selectedBalls = (SelectedBall)selectedBallsChooser.getSelected();

        switch (selectedStart) {
            case TOUCHING_HUB: switch (selectedBalls) {
                case NONE: message = "Shoot immediately, drive off tarmac";
                    break;
                default: message = "We do not have an auto routine programmed for this combination. DO NOT START THE MATCH WITH THIS COMBINATION.";
                    break;
            }
            break;

            case BEHIND_HUB: switch (selectedBalls) {
                case NONE: message = "Wait for team robot, drive to hub, shoot, drive off tarmac";
                    break;
                default: message = "We do not have an auto routine programmed for this combination. DO NOT START THE MATCH WITH THIS COMBINATION.";
                    break;
            }
            break;

            case AREA_1: switch (selectedBalls) {
                case BALL_1: message = "Start area 1, get ball 1, drive to hub, shoot both";
                    break;
                default: message = "We do not have an auto routine programmed for this combination. DO NOT START THE MATCH WITH THIS COMBINATION.";
                    break;
            }
            break;

            case AREA_2: switch (selectedBalls) {
                case BALL_1: message = "Start area 2, get ball 1, drive to hub, shoot both";
                    break;
                case BALL_2: message = "Start area 2, get ball 2, drive to hub, shoot both";
                    break;
                default: message = "We do not have an auto routine programmed for this combination. DO NOT START THE MATCH WITH THIS COMBINATION.";
                    break;
            }
            break;

            case AREA_3: switch (selectedBalls) {
                case BALL_2: message = "Start area 3, get ball 2, drive to hub, shoot both";
                    break;
                case BALL_3: message = "Start area 3, get ball 3, drive to hub, shoot both";
                    break;
                default: message = "We do not have an auto routine programmed for this combination. DO NOT START THE MATCH WITH THIS COMBINATION.";
                    break;
            }
            break;

            case AREA_4: switch (selectedBalls) {
                case BALL_3: message = "Start area 4, get ball 3, drive to hub, shoot both";
                    break;
                default: message = "We do not have an auto routine programmed for this combination. DO NOT START THE MATCH WITH THIS COMBINATION.";
                    break;
            }
            break;

            default: message = "No routine selected";
                break;
        }
    }

    public SequentialCommandGroup generateAuto() {
        SequentialCommandGroup auto = new SequentialCommandGroup();

        StartPosition selectedStart = (StartPosition)startPositionChooser.getSelected();
        SelectedBall selectedBalls = (SelectedBall)selectedBallsChooser.getSelected();

        auto.addCommands(new WaitCommand(waitSlider.getDouble(0)));

        //certain combinations of selectedStart and selectedBalls will add
        //commands to auto, but others will do nothing.
        switch (selectedStart) {
            case TOUCHING_HUB: switch (selectedBalls) {
                case NONE: auto.addCommands(new ScoreThenTaxi(drive, intake, cage, catapult));
                break;
                default: break;
            }
            break;

            case BEHIND_HUB: switch (selectedBalls) {
                case NONE: auto.addCommands(new DriveThenScoreThenTaxi(drive, intake, cage, catapult));
                break;
                default: break;
            }
            break;

            case AREA_1: switch (selectedBalls) {
                case BALL_1: auto.addCommands(new Area1GetBall1(drive, intake, cage, catapult));
                break;
                default: break;
            }
            break;

            case AREA_2: switch (selectedBalls) {
                case BALL_1: auto.addCommands(new Area2GetBall1(drive, intake, cage, catapult));
                break;
                case BALL_2: auto.addCommands(new Area2GetBall2(drive, intake, cage, catapult));
                break;
                default: break;
            }
            break;

            case AREA_3: switch (selectedBalls) {
                case BALL_2: auto.addCommands(new Area3GetBall2(drive, intake, cage, catapult));
                break;
                case BALL_3: auto.addCommands(new Area3GetBall3(drive, intake, cage, catapult));
                break;
                default: break;
            }
            break;

            case AREA_4: switch (selectedBalls) {
                case BALL_3: auto.addCommands(new Area4GetBall3(drive, intake, cage, catapult));
                break;
                default: break;
            }
            break;

            default: break;
        }

        return auto;
    }
}
