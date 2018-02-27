package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.GGHardware;
import org.firstinspires.ftc.teamcode.GGParameters;

/**
 * Created by User on 10/30/2017.
 */
@Autonomous(name="Test", group = "Autonomous")
public class EncoderTest extends LinearOpMode
{

    GGHardware robot  = new GGHardware();
    int forward = 0;
    int backward = 1;
    int turnRight = 2;
    int turnLeft = 3;
    int driftRight = 4;
    int driftLeft = 5;

    @Override
    public void runOpMode() {
        GGParameters parameters = new GGParameters(this);
        robot.init(parameters);

        boolean finished = false;

        waitForStart();

        while (opModeIsActive()) {

            while (!finished) {
                //grab onto block
                robot.RClaw1.setPosition(robot.BOTTOMRCLAW_CLOSE);
                robot.LClaw1.setPosition(robot.BOTTOMLCLAW_CLOSE);

                int distance = 24;
                //Drive off platform
                robot.DriveMotorUsingEncoder(0.25,24,30, forward);
                stop();
                finished = true;

            }
        }
    }


}

