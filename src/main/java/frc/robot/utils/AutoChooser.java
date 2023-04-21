package frc.robot.utils;

import java.util.Map;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.auto.Area1GetBall1;
import frc.robot.commands.auto.Area1ThreeBalls;
import frc.robot.commands.auto.Area2GetBall1;
import frc.robot.commands.auto.Area2GetBall2;
import frc.robot.commands.auto.Area3GetBall2;
import frc.robot.commands.auto.Area3GetBall3;
import frc.robot.commands.auto.Area4GetBall3;
import frc.robot.commands.auto.ScoreThenTaxi;
import frc.robot.subsystems.Cage;
import frc.robot.subsystems.Catapult;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Intake;

public class AutoChooser {
    public enum SelectedStart {
        AREA_1,
        AREA_2,
        AREA_3,
        AREA_4
    }

    public enum SelectedBall {
        NONE,
        BALL_1,
        BALL_2,
        BALL_3,
        TWO_BALLS
    }

    private Drive drive;
    private Intake intake;
    private Cage cage;
    private Catapult rightCatapult;
    private Catapult leftCatapult;

    private ShuffleboardTab autoTab = Shuffleboard.getTab("Choose auto routine");

    private SendableChooser<SelectedStart> selectedStartChooser = new SendableChooser<SelectedStart>();
    private SendableChooser<SelectedBall> selectedBallsChooser = new SendableChooser<SelectedBall>();


    private GenericEntry waitSlider = autoTab.add("Wait time", 0)
    .withWidget(BuiltInWidgets.kNumberSlider)
    .withProperties(Map.of("min", 0, "max", 10, "block increment", 0.5))
    .withSize(2, 1)
    .withPosition(0, 0)
    .getEntry();

    private String message;

    public AutoChooser(Drive drive, Intake intake, Cage cage, Catapult rightCatapult, Catapult leftCatapult) {
        this.drive = drive;
        this.intake = intake;
        this.cage = cage;
        this.rightCatapult = rightCatapult;
        this.leftCatapult = leftCatapult;

        selectedStartChooser.setDefaultOption("Start area 1", SelectedStart.AREA_1);
        selectedStartChooser.addOption("Start area 2", SelectedStart.AREA_2);
        selectedStartChooser.addOption("Start area 3", SelectedStart.AREA_3);
        selectedStartChooser.addOption("Start area 4", SelectedStart.AREA_4);     
        autoTab.add("Starting position", selectedStartChooser)
        .withWidget(BuiltInWidgets.kComboBoxChooser)
        .withSize(2, 1)
        .withPosition(0, 1);

        selectedBallsChooser.setDefaultOption("Get no balls", SelectedBall.NONE);
        selectedBallsChooser.addOption("Get ball 1", SelectedBall.BALL_1);
        selectedBallsChooser.addOption("Get ball 2", SelectedBall.BALL_2);
        selectedBallsChooser.addOption("Get ball 3", SelectedBall.BALL_3);
        selectedBallsChooser.addOption("3-ball auto", SelectedBall.TWO_BALLS);
        autoTab.add("Which ball to get", selectedBallsChooser)
        .withWidget(BuiltInWidgets.kComboBoxChooser)
        .withSize(2, 1)
        .withPosition(0, 2);

        autoTab.addString("Selected auto path", () -> message)
        .withSize(4, 1)
        .withPosition(0, 3);
    }

    //displays what the current auto routine is, or an error if a combination with
    //no routine is selected.
    //runs in disabledPeriodic.
    public void checkAutoPath() {
        SelectedStart selectedStart = (SelectedStart)selectedStartChooser.getSelected();
        SelectedBall selectedBall = (SelectedBall)selectedBallsChooser.getSelected();

        switch (selectedStart) {
            case AREA_1: switch (selectedBall) {
                case BALL_1: message = "Start area 1, get ball 1, drive to hub, shoot both";
                    break;
                case NONE: message = "Start area 1, shoot preloaded ball immediately. ";
                    break;
                case TWO_BALLS: message = "Three ball auto from area 1!";
                    break;
                default: message = "We do not have an auto routine programmed for this combination. DO NOT START THE MATCH WITH THIS COMBINATION.";
                    break;
            }
            break;

            case AREA_2: switch (selectedBall) {
                case BALL_1: message = "Start area 2, get ball 1, drive to hub, shoot both";
                    break;
                case BALL_2: message = "Start area 2, get ball 2, drive to hub, shoot both";
                    break;
                case NONE: message = "Start area 2, shoot preloaded ball immediately. ";
                    break;
                default: message = "We do not have an auto routine programmed for this combination. DO NOT START THE MATCH WITH THIS COMBINATION.";
                    break;
            }
            break;

            case AREA_3: switch (selectedBall) {
                case BALL_2: message = "Start area 3, get ball 2, drive to hub, shoot both";
                    break;
                case BALL_3: message = "Start area 3, get ball 3, drive to hub, shoot both";
                    break;
                case NONE: message = "Start area 3, shoot preloaded ball immediately. ";
                    break;
                default: message = "We do not have an auto routine programmed for this combination. DO NOT START THE MATCH WITH THIS COMBINATION.";
                    break;
            }
            break;

            case AREA_4: switch (selectedBall) {
                case BALL_3: message = "Start area 4, get ball 3, drive to hub, shoot both";
                    break;
                case NONE: message = "Start area 4, shoot preloaded ball immediately. ";
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

        SelectedStart selectedStart = (SelectedStart)selectedStartChooser.getSelected();
        SelectedBall selectedBall = (SelectedBall)selectedBallsChooser.getSelected();

        auto.addCommands(new WaitCommand(waitSlider.getDouble(0)));

        //certain combinations of selectedStart and selectedBalls will add
        //commands to auto, but others will do nothing.
        switch (selectedStart) {
            case AREA_1: switch (selectedBall) {
                case NONE: auto.addCommands(new ScoreThenTaxi(drive, intake, cage, rightCatapult, leftCatapult));
                    break;
                case BALL_1: auto.addCommands(new Area1GetBall1(drive, intake, cage, rightCatapult, leftCatapult));
                    break;
                case TWO_BALLS: auto.addCommands(new Area1ThreeBalls(drive, intake, cage, rightCatapult, leftCatapult));
                    break;
                default: break;
            }
            break;

            case AREA_2: switch (selectedBall) {
                case NONE: auto.addCommands(new ScoreThenTaxi(drive, intake, cage, rightCatapult, leftCatapult));
                    break;
                case BALL_1: auto.addCommands(new Area2GetBall1(drive, intake, cage, rightCatapult, leftCatapult));
                    break;
                case BALL_2: auto.addCommands(new Area2GetBall2(drive, intake, cage, rightCatapult, leftCatapult));
                    break;
                default: break;
            }
            break;

            case AREA_3: switch (selectedBall) {
                case NONE: auto.addCommands(new ScoreThenTaxi(drive, intake, cage, rightCatapult, leftCatapult));
                    break;
                case BALL_2: auto.addCommands(new Area3GetBall2(drive, intake, cage,rightCatapult, leftCatapult));
                    break;
                case BALL_3: auto.addCommands(new Area3GetBall3(drive, intake, cage, rightCatapult, leftCatapult));
                    break;
                default: break;
            }
            break;

            case AREA_4: switch (selectedBall) {
                case NONE: auto.addCommands(new ScoreThenTaxi(drive, intake, cage, rightCatapult, leftCatapult));
                    break;
                case BALL_3: auto.addCommands(new Area4GetBall3(drive, intake, cage, rightCatapult, leftCatapult));
                    break;
                default: break;
            }
            break;

            default: break;
        }

        return auto;
    }
}
