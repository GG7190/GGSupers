package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.GGHardware;
import org.firstinspires.ftc.teamcode.GGParameters;

/**
 * Created by User on 10/30/2017.
 */
@Autonomous(name="Park", group = "Autonomous")
public class Park extends LinearOpMode
{

    GGHardware robot  = new GGHardware();

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
                sleep(200);
                //lift the block up
                robot.lift1.setPower(1);
                sleep(80);
                robot.lift1.setPower(0);
                robot.lift1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

                //drive to the safe area
                forwBackw(-.75);
                sleep(1500);
                forwBackw(0);
                //open the claws
                robot.RClaw1.setPosition(robot.BOTTOMRCLAW_OPEN);
                robot.LClaw1.setPosition(robot.BOTTOMLCLAW_OPEN);
                sleep(500);
                //back up slightly
                forwBackw(.75);
                sleep(200);
                stop();
                finished = true;

            }
        }
    }
    boolean reachedPosition = false;



    public void forwBackw(double motorPwr)
    {
        robot.frontright.setPower(motorPwr);
        robot.frontleft.setPower(motorPwr);
        robot.backright.setPower(motorPwr);
        robot.backleft.setPower(motorPwr);
    }


    public void driftRight()
    {
        robot.frontright.setPower(1);
        robot.frontleft.setPower(-1);
        robot.backright.setPower(-1);
        robot.backleft.setPower(1);
    }

    public void driftLeft()
    {
        robot.frontright.setPower(-1);
        robot.frontleft.setPower(1);
        robot.backright.setPower(1);
        robot.backleft.setPower(-1);
    }


    public void turnRight()
    {
        robot.frontright.setPower(1);
        robot.frontleft.setPower(-1);
        robot.backright.setPower(1);
        robot.backleft.setPower(-1);
    }

    public void turnLeft()
    {
        robot.frontright.setPower(-1);
        robot.frontleft.setPower(1);
        robot.backright.setPower(-1);
        robot.backleft.setPower(1);
    }


    public void runEncodersUntil(int encoderAmount)
    {
        reachedPosition = false;

        while(!reachedPosition)
        {
            telemetry.addData("Encoder Value", robot.backleft.getCurrentPosition());
            telemetry.update();
            if(Math.abs(robot.backleft.getCurrentPosition()) > encoderAmount)
            {
                reachedPosition = true;
            }
            else
            {

            }

        }
    }

    public void resetEncoders()
    {
        robot.backleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void runWithEncoders()
    {
        if(robot.backleft != null)
        {
            robot.backleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }
}

