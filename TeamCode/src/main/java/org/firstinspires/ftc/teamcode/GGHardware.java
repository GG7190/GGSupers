package org.firstinspires.ftc.teamcode;

import android.app.Activity;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import android.view.View;
import java.util.Locale;
import android.graphics.Color;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuMarkInstanceId;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
/**
 * Created by User on 10/29/2017.
 */

public class GGHardware
{
    GGParameters _parameters = null;
    /* Public OP Mode Members*/
    public DcMotor motor1 = null;

    ColorSensor sensorColor;
    DistanceSensor sensorDistance;

    /* Local OP Mode Members*/
    HardwareMap hwMap  = null;
    public LinearOpMode BaseOpMode = null;
    private ElapsedTime runtime = new ElapsedTime();

    public final double BOTTOMRCLAW_CLOSE = 0.0;
    public final double BOTTOMRCLAW_OPEN = 0.5;
    public final double TOPRCLAW_CLOSE = 0.04;
    public final double TOPRCLAW_OPEN = 0.7;
    public final double BOTTOMLCLAW_OPEN = 0.25;
    public static double BOTTOMLCLAW_CLOSE = 0.95;
    public static double TOPLCLAW_CLOSE = 0.90;
    public static double TOPLCLAW_OPEN = 0.3;
    public static double LEFT_CLAW_MID = 0.85;
    public static double RIGHT_CLAW_MID = 0.35;
    public static double BOTTOM_LEFT_MID = 0.70;
    public final double PIVOT_MIN_RANGE = 0.15;
    public final double PIVOT_MID_RANGE = 0.30;
    public final double PIVOT_MAX_RANGE = 0.90;
    public final double RELICUPDOWN_MIN_RANGE = 0.01;
    public final double RELICUPDOWN_MID_RANGE = 0.30;
    public final double RELICUPDOWN_MAX_RANGE = 0.90;
    public final double RELICCLAW_MIN_RANGE = 0.01;
    public final double RELICCLAW_MID_RANGE = 0.30;
    public final double RELICCLAW_MAX_RANGE = 0.9;
    //public final double SPIN_MIN_RANGE = 0.15;
    //public final double SPIN_MID_RANGE = 0.30;
    //public final double SPIN_MAX_RANGE = 0.90;

    public DcMotor frontleft, frontright, backleft, backright ,lift1, relicLift;
    public Servo pivot, LClaw1, LClaw2, RClaw1, RClaw2, relicClaw, relicUpDown;
    public float x, y, z, w, pwr;
    public static double deadzone = 0.2;
    public static double deadzone2 = 0.1;

    /* Constructor*s/
    public GGHardware()
    {


    }

    /*Initialize Standard Hardware Interfaces*/
    public void init(GGParameters parameters /*HardwareMap ahwMap*/)
    {
        _parameters = parameters;
        hwMap =  parameters.BaseOpMode.hardwareMap;/*ahwMap*/
        BaseOpMode = parameters.BaseOpMode;

        frontleft = hwMap.get(DcMotor.class, "fleft");
        BaseOpMode.telemetry.addData("fleft", 0);
        BaseOpMode.telemetry.update();

        frontright = hwMap.get(DcMotor.class, "fright");
        BaseOpMode.telemetry.addData("fright", 0);
        BaseOpMode.telemetry.update();

        backleft = hwMap.get(DcMotor.class, "bleft");
        BaseOpMode.telemetry.addData("bleft", 0);
        BaseOpMode.telemetry.update();

        backright = hwMap.get(DcMotor.class, "bright");
        BaseOpMode.telemetry.addData("bright", 0);
        BaseOpMode.telemetry.update();

        lift1 = hwMap.get(DcMotor.class, "lift1");
        BaseOpMode.telemetry.addData("lift1t", 0);
        BaseOpMode.telemetry.update();

        LClaw1 = hwMap.get(Servo.class, "lclaw1");
        BaseOpMode.telemetry.addData("lclaw1", 0);
        BaseOpMode.telemetry.update();

        LClaw2 = hwMap.get(Servo.class, "lclaw2");
        BaseOpMode.telemetry.addData("lclaw2", 0);
        BaseOpMode.telemetry.update();

        RClaw1 = hwMap.get(Servo.class, "rclaw1");
        BaseOpMode.telemetry.addData("rclaw1", 0);
        BaseOpMode.telemetry.update();

        RClaw2 = hwMap.get(Servo.class, "rclaw2");
        BaseOpMode.telemetry.addData("rclaw2", 0);
        BaseOpMode.telemetry.update();

        relicClaw = hwMap.get(Servo.class, "relicClaw");
        BaseOpMode.telemetry.addData("relicClaw", 0);
        BaseOpMode.telemetry.update();

        relicUpDown = hwMap.get(Servo.class, "relicUpDown");
        BaseOpMode.telemetry.addData("relicUpDown", 0);
        BaseOpMode.telemetry.update();

        relicLift = hwMap.get(DcMotor.class, "relicLift");
        BaseOpMode.telemetry.addData("relicLift", 0);
        BaseOpMode.telemetry.update();

        pivot = hwMap.get(Servo.class, "pivot");
        BaseOpMode.telemetry.addData("pivot", 0);
        BaseOpMode.telemetry.update();

        // spin = hwMap.get(Servo.class, "spin");




        // get a reference to the color sensor.
        //sensorColor = hwMap.get(ColorSensor.class, "sensor_color_distance");

        // get a reference to the distance sensor that shares the same name.
        //sensorDistance = hwMap.get(DistanceSensor.class, "sensor_color_distance");

        frontleft.setDirection(DcMotor.Direction.REVERSE);
        backleft.setDirection(DcMotor.Direction.REVERSE);
    }





    public void forwBakw(double motorPwr)
    {
        frontright.setPower(motorPwr);
        frontleft.setPower(motorPwr);
        backright.setPower(motorPwr);
        backleft.setPower(motorPwr);
    }


    public void driftRight()
    {
        frontright.setPower(1);
        frontleft.setPower(-1);
        backright.setPower(-1);
        backleft.setPower(1);
    }

    public void driftLeft()
    {
        frontright.setPower(-1);
        frontleft.setPower(1);
        backright.setPower(1);
        backleft.setPower(-1);
    }


    public void turnRight()
    {
        frontright.setPower(1);
        frontleft.setPower(-1);
        backright.setPower(1);
        backleft.setPower(-1);
    }

    public void turnLeft()
    {
        frontright.setPower(-1);
        frontleft.setPower(1);
        backright.setPower(-1);
        backleft.setPower(1);
    }




    public void getJoyVals() {
        float pwrFactor = (float)1;
        float pwrFactorY = (float)0.5;
        y = pwrFactorY * _parameters.BaseOpMode.gamepad1.right_stick_y; // joyval = -1 to 1; forward right joy 0 = stop -1 = reverse 1 = forward
        x = pwrFactor * _parameters.BaseOpMode.gamepad1.right_stick_x; //-1 to 1
        z = pwrFactor * _parameters.BaseOpMode.gamepad1.left_stick_x;  //-1 to 1
        w = pwrFactor * _parameters.BaseOpMode.gamepad1.left_stick_y;  //-1 to 1
        //updates joystick values

        if (Math.abs(x) < deadzone*2.5) x = 0;
        if (Math.abs(y) < deadzone2) y = 0;
        if (Math.abs(z) < deadzone) z = 0;
        if (Math.abs(w) < 0.9) w = 0;
        //checks deadzones
    }

    public void DriveMotorUsingEncoder(double speed, int targetCount, double timeoutSeconds, int direction)
    {
        // Ensure that the opmode is still active
        if (_parameters.BaseOpMode.opModeIsActive())
        {
            //Set Target Position
            backleft.setTargetPosition(targetCount);
            //Reset Encoders
            backleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            // Turn On Run Without Encoders
            backleft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            // reset the timeout time and start motion.WITHOUT
            runtime.reset();
            if(direction == 0)
            {
                forwBakw(Math.abs(speed));
            }


            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (//_parameters.BaseOpMode.opModeIsActive() &&
                    (runtime.seconds() < timeoutSeconds) &&
                            (backleft.getCurrentPosition() < targetCount)
                    )
            {
                // Display it for the driver.
                _parameters.BaseOpMode.telemetry.addData("Path1",  "Running to %7d :%7d", targetCount,  backleft.getCurrentPosition());
                _parameters.BaseOpMode.telemetry.update();
            }

            // Stop all motion;
            forwBakw(0);
            //Reset Encoders
            backleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            //Set to Run Without Encoders
            backleft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
    }

    public void resetEncoders()
    {
        backleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void runWithOutEncoders()
    {
        backleft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

}