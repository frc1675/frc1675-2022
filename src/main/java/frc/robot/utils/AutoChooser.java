package frc.robot.utils;

import java.util.Map;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
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

    private EditableChooser<SelectedStart> selectedStartChooser = new EditableChooser<SelectedStart>();
    private EditableChooser<SelectedBall> selectedBallsChooser = new EditableChooser<SelectedBall>();

    private NetworkTableEntry waitSlider = autoTab.add("Wait time", 0)
    .withWidget(BuiltInWidgets.kNumberSlider)
    .withProperties(Map.of("min", 0, "max", 10, "block increment", 0.5))
    .withSize(2, 1)
    .withPosition(0, 0)
    .getEntry(); 

    private String message = "";

    private int option;
    private int prev = -1;

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
        autoTab.add("Which ball to get", selectedBallsChooser)
        .withWidget(BuiltInWidgets.kComboBoxChooser)
        .withSize(2, 1)
        .withPosition(0, 2);

        autoTab.addString("Selected auto path", () -> message)
        .withSize(4, 1)
        .withPosition(0, 3);
    }

    private void setA1Options(){
        selectedBallsChooser.clearExceptDefault();
        selectedBallsChooser.addOption("Get ball 1", SelectedBall.BALL_1);
        selectedBallsChooser.addOption("3-ball auto", SelectedBall.TWO_BALLS);

    }

    private void setA2Options(){
        selectedBallsChooser.clearExceptDefault();
        selectedBallsChooser.addOption("Get ball 1", SelectedBall.BALL_1);
        selectedBallsChooser.addOption("Get ball 2", SelectedBall.BALL_2);
    }

    private void setA3Options(){
        selectedBallsChooser.clearExceptDefault();
        selectedBallsChooser.addOption("Get ball 2", SelectedBall.BALL_2);
        selectedBallsChooser.addOption("Get ball 3", SelectedBall.BALL_3);
    }

    private void setA4Options(){
        selectedBallsChooser.clearExceptDefault();
        selectedBallsChooser.addOption("Get ball 3", SelectedBall.BALL_3);
    }

    //displays what the current auto routine is. default case is if 'Get no balls' is selected.
    //runs in disabledPeriodic.
    public void checkAutoPath() {
        SelectedStart selectedStart = (SelectedStart)selectedStartChooser.getSelected();

        switch (selectedStart) {
            case AREA_1: 
            option = 1;
            if(option != prev){
                setA1Options();
            }
            prev = 1;
            switch (selectedBallsChooser.getSelected()) {
                case BALL_1: message = "Start area 1, get ball 1, drive to hub, shoot both";
                    break;
                case TWO_BALLS: message = "Three ball auto from area 1!";
                    break;
                default: message = "Start area 1, shoot preloaded ball immediately. ";
                    break;
            }
            break;

            case AREA_2: 
            option = 2;
            if(option != prev){
                setA2Options();
            }
            prev = 2;
            switch (selectedBallsChooser.getSelected()) {
                case BALL_1: message = "Start area 2, get ball 1, drive to hub, shoot both";
                    break;
                case BALL_2: message = "Start area 2, get ball 2, drive to hub, shoot both";
                    break;
                default: message = "Start area 2, shoot preloaded ball immediately. ";
                    break;
            }
            break;

            case AREA_3: 
            option = 3;
            if(option != prev){
                setA3Options();
            }
            prev = 3;
            switch (selectedBallsChooser.getSelected()) {
                case BALL_2: message = "Start area 3, get ball 2, drive to hub, shoot both";
                    break;
                case BALL_3: message = "Start area 3, get ball 3, drive to hub, shoot both";
                    break;
                default: message = "Start area 3, shoot preloaded ball immediately. ";
                    break;
            }
            break;

            case AREA_4: 
            option = 4;
            if(option != prev){
                setA4Options();
            }
            prev = 4;
            switch (selectedBallsChooser.getSelected()) {
                case BALL_3: message = "Start area 4, get ball 3, drive to hub, shoot both";
                    break;
                default: message = "Start area 4, shoot preloaded ball immediately. ";
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
                case BALL_1: auto.addCommands(new Area1GetBall1(drive, intake, cage, rightCatapult, leftCatapult));
                    break;
                case TWO_BALLS: auto.addCommands(new Area1ThreeBalls(drive, intake, cage, rightCatapult, leftCatapult));
                    break;
                default: auto.addCommands(new ScoreThenTaxi(drive, intake, cage, rightCatapult, leftCatapult));
                    break;
            }
            break;

            case AREA_2: switch (selectedBall) {
                case BALL_1: auto.addCommands(new Area2GetBall1(drive, intake, cage, rightCatapult, leftCatapult));
                    break;
                case BALL_2: auto.addCommands(new Area2GetBall2(drive, intake, cage, rightCatapult, leftCatapult));
                    break;
                default: auto.addCommands(new ScoreThenTaxi(drive, intake, cage, rightCatapult, leftCatapult));
                    break;
            }
            break;

            case AREA_3: switch (selectedBall) {
                case BALL_2: auto.addCommands(new Area3GetBall2(drive, intake, cage,rightCatapult, leftCatapult));
                    break;
                case BALL_3: auto.addCommands(new Area3GetBall3(drive, intake, cage, rightCatapult, leftCatapult));
                    break;
                default: auto.addCommands(new ScoreThenTaxi(drive, intake, cage, rightCatapult, leftCatapult));
                    break;
            }
            break;

            case AREA_4: switch (selectedBall) {
                case BALL_3: auto.addCommands(new Area4GetBall3(drive, intake, cage, rightCatapult, leftCatapult));
                    break;
                default: auto.addCommands(new ScoreThenTaxi(drive, intake, cage, rightCatapult, leftCatapult));
                    break;
            }
            break;

            default: break;
        }

        return auto;
    }
}
