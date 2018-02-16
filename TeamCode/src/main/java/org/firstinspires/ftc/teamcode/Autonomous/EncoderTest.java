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
                resetEncoders();
                runWithOutEncoders();
                //grab onto block
                robot.RClaw1.setPosition(robot.BOTTOMRCLAW_CLOSE);
                robot.LClaw1.setPosition(robot.BOTTOMLCLAW_CLOSE);
                robot.DriveMotorUsingEncoder(1,500,30, forward);
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

    public void runWithOutEncoders()
    {
        if(robot.backleft != null)
        {
            robot.backleft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
    }



}

