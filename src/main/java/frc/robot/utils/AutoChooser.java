package frc.robot.utils;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import frc.robot.subsystems.Drive;

public class AutoChooser {
    public enum StartPosition {
        TOUCHING_HUB,
        BEHIND_HUB,
        AREA_1,
        AREA_2,
        AREA_3,
        AREA_4
    }

    public enum GetBall {
        NONE,
        BALL_1,
        BALL_2,
        BALL_3
    }

    private Drive drive;

    private ShuffleboardTab autoTab = Shuffleboard.getTab("Choose auto routine");

    private SendableChooser<StartPosition> startPositionChooser = new SendableChooser<StartPosition>();
    private SendableChooser<GetBall> getBallChooser = new SendableChooser<GetBall>();

    public AutoChooser(Drive drive) {
        this.drive = drive;

        startPositionChooser.setDefaultOption("Touching hub", StartPosition.TOUCHING_HUB);
        startPositionChooser.addOption("A few feet behind hub", StartPosition.BEHIND_HUB);
        startPositionChooser.addOption("Start area 1", StartPosition.AREA_1);
        startPositionChooser.addOption("Start area 2", StartPosition.AREA_2);
        startPositionChooser.addOption("Start area 3", StartPosition.AREA_3);
        startPositionChooser.addOption("Start area 4", StartPosition.AREA_4);

        getBallChooser.setDefaultOption("Get no balls", GetBall.NONE);
        getBallChooser.setDefaultOption("Get ball 1", GetBall.BALL_1);
        getBallChooser.setDefaultOption("Get ball 2", GetBall.BALL_2);
        getBallChooser.setDefaultOption("Get ball 3", GetBall.BALL_3);
    }
}
