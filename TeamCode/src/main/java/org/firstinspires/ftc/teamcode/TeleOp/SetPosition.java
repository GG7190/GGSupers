/**
 * Created by User on 12/15/2017.
 */
package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.GGHardware;
import org.firstinspires.ftc.teamcode.GGParameters;


@TeleOp(name="SetPosition",group="TeleOp")
public class SetPosition extends LinearOpMode {

    GGHardware robot = new GGHardware();
    private ElapsedTime  runtime= new ElapsedTime();

    @Override
    public void runOpMode(){
        GGParameters parameters = new GGParameters(this);
        robot.init(parameters);
        boolean elevatorButtonPressed = false; //is "open pivot" button currently pressed
        waitForStart();
        while (opModeIsActive())
        {
            double servoValue = 0.0;
            if(gamepad2.right_bumper)
            {
                servoValue = servoValue + 0.06;
                robot.RClaw1.setPosition(servoValue);
                telemetry.addData("Servo Position: ", servoValue);
                telemetry.update();
            }

            if(gamepad2.left_bumper)
            {
                servoValue = servoValue - 0.05;
                robot.RClaw1.setPosition(servoValue);
                telemetry.addData("Servo Position: ", servoValue);
                telemetry.update();
            }

        }
    }

}