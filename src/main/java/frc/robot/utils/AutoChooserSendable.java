package frc.robot.utils;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;

public class AutoChooserSendable implements Sendable{
    
    @Override
    public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType("AutoOption");
        builder.addStringProperty("pos", null, null);
        builder.addStringProperty("cargo", null, null);
    }
}
