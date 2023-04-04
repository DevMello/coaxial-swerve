package tk.devmello.theswerve.drive.swerve;

import com.qualcomm.robotcore.hardware.DcMotorEx;

public class Encoder {
    //custom encoder class to substitute for the AbsoluteAnalogEncoder class
    //this class is used to get the encoder values from the encoders
    DcMotorEx encoder;
    public Encoder(DcMotorEx encoder) {
        this.encoder = encoder;
    }

    public double getCurrentPosition() {
        return encoder.getCurrentPosition();
    }

    public Encoder getEncoder() {
        return this;
    }


}
