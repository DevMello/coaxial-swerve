package tk.devmello.theswerve.drive;

import tk.devmello.theswerve.math.Pose;

public interface Drivetrain {
    void set(Pose pose);

    void set(Pose pose, double maxPower);
}

