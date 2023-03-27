package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import tk.devmello.mellolib.swerve.swerve.SwerveDrive;

@TeleOp(name = "Test Swerve", group = "Test")
public class TestSwerve extends OpMode {

    //SwerveDrive bot = new SwerveDrive();

    public void init(){

        //bot.init(hardwareMap);
    }

    public void loop(){
/**
 * vx -> strafe
 * vy -> forward backward
 * va -> turn(angular)
 * inverse forward backward
 * inverse angular
 */
        float vx = gamepad1.left_stick_x * (float)SwerveDrive.MAX_DRIVE_SPEED;
        float vy = -gamepad1.left_stick_y * (float)SwerveDrive.MAX_DRIVE_SPEED;
        float va = -gamepad1.right_stick_x * (float)SwerveDrive.MAX_ANGULAR_SPEED;
//        bot.setDriveSpeed(vx, vy, va);
    }

}
