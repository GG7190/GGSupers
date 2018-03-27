package org.firstinspires.ftc.teamcode.Autonomous;
import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.GGHardware;

import java.util.Locale;

import org.firstinspires.ftc.teamcode.GGParameters;

/*
 * This is an example LinearOpMode that shows how to use
 * the REV Robotics Color-Distance Sensor.
 *
 * It assumes the sensor is configured with the name "sensor_color_distance".
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list.
 */
@Autonomous(name = "jewel Red", group = "Autonomous")

public class JewelRed extends LinearOpMode {

    GGHardware robot  = new GGHardware();

    ColorSensor sensorColor;
    DistanceSensor sensorDistance;


    @Override
    public void runOpMode() {
        GGParameters parameters = new GGParameters(this);
        robot.init(parameters);

               // get a reference to the color sensor.
        sensorColor = hardwareMap.get(ColorSensor.class, "sensor_color_distance");

        // get a reference to the distance sensor that shares the same name.
        sensorDistance = hardwareMap.get(DistanceSensor.class, "sensor_color_distance");

        // hsvValues is an array that will hold the hue, saturation, and value information.
        float hsvValues[] = {0F, 0F, 0F};

        // values is a reference to the hsvValues array.
        final float values[] = hsvValues;

        // sometimes it helps to multiply the raw RGB values with a scale factor
        // to amplify/attentuate the measured values.
        final double SCALE_FACTOR = 255;

        // get a reference to the RelativeLayout so we can change the background
        // color of the Robot Controller app to match the hue detected by the RGB sensor.
        int relativeLayoutId = hardwareMap.appContext.getResources().getIdentifier("RelativeLayout", "id", hardwareMap.appContext.getPackageName());
        final View relativeLayout = ((Activity) hardwareMap.appContext).findViewById(relativeLayoutId);

        // wait for the start button to be pressed.
        waitForStart();

        // loop and read the RGB and distance data.
        // Note we use opModeIsActive() as our loop condition because it is an interruptible method.
        while (opModeIsActive()) {
            // convert the RGB values to HSV values.
            // multiply by the SCALE_FACTOR.
            // then cast it back to int (SCALE_FACTOR is a double)
            Color.RGBToHSV((int) (sensorColor.red() * SCALE_FACTOR),
                    (int) (sensorColor.green() * SCALE_FACTOR),
                    (int) (sensorColor.blue() * SCALE_FACTOR),
                    hsvValues);

            // send the info back to driver station using telemetry function.
            telemetry.addData("Distance (cm)",
                    String.format(Locale.US, "%.02f", sensorDistance.getDistance(DistanceUnit.CM)));
            telemetry.addData("Alpha", sensorColor.alpha());
            telemetry.addData("Red  ", sensorColor.red());
            telemetry.addData("Green", sensorColor.green());
            telemetry.addData("Blue ", sensorColor.blue());
            telemetry.addData("Hue", hsvValues[0]);

            // change the background color to match the color detected by the RGB sensor.
            // pass a reference to the hue, saturation, and value array as an argument
            // to the HSVToColor method.
            relativeLayout.post(new Runnable() {
                public void run() {
                    relativeLayout.setBackgroundColor(Color.HSVToColor(0xff, values));
                }
            });

            telemetry.update();

            robot.pivot.setPosition(robot.PIVOT_MAX_RANGE);

            //Wait for the sensor to move down into place.
            sleep(2000);


            if(sensorColor.red() > ballCheck())
            {
                robot.RClaw1.setPosition(robot.BOTTOMRCLAW_CLOSE);
                robot.LClaw1.setPosition(robot.BOTTOMLCLAW_CLOSE);
                sleep(700);
               // robot.lift1.setPower(.75);
                sleep(100);
                //robot.lift1.setPower(0);
                sleep(50);
                robot.forwBakw(.75);
                sleep(100);
                robot.forwBakw(0);
                sleep(300);
                robot.pivot.setPosition(robot.PIVOT_MIN_RANGE);
                sleep(100);
                robot.forwBakw(-.50);
                sleep(1500);
                robot.forwBakw(0);
                sleep(200);
                robot.turnLeft();
                sleep(300);
                robot.forwBakw(-.75);
                sleep(200);
                //robot.lift1.setPower(-.75);
                sleep(50);
                //robot.lift1.setPower(0);
                sleep(50);
                robot.forwBakw(-1);
                sleep(200);
                robot.forwBakw(0);
                sleep(50);
                robot.RClaw1.setPosition(robot.BOTTOMRCLAW_OPEN);
                robot.LClaw1.setPosition(robot.BOTTOMLCLAW_OPEN);
                robot.forwBakw(1);
                sleep(80);
                robot.forwBakw(0);
                stop();

            }
            else
            {
                robot.RClaw1.setPosition(robot.BOTTOMRCLAW_CLOSE);
                robot.LClaw1.setPosition(robot.BOTTOMLCLAW_CLOSE);
                sleep(700);
               // robot.lift1.setPower(.75);
                sleep(100);
                //robot.lift1.setPower(0);
                sleep(50);
                robot.forwBakw(-1);
                sleep(50);
                robot.forwBakw(0);
                sleep(200);
                robot.pivot.setPosition(robot.PIVOT_MIN_RANGE);
                sleep(100);
                robot.forwBakw(-1);
                sleep(600);
                robot.forwBakw(0);
                robot.turnLeft();
                sleep(300);
               // robot.lift1.setPower(-.75);
                sleep(50);
                //robot.lift1.setPower(0);
                sleep(50);
                robot.forwBakw(-1);
                sleep(400);
                robot.forwBakw(0);
                sleep(50);
                robot.forwBakw(1);
                sleep(50);
                robot.forwBakw(0);
                sleep(50);
                robot.RClaw1.setPosition(robot.BOTTOMRCLAW_OPEN);
                robot.LClaw1.setPosition(robot.BOTTOMLCLAW_OPEN);
                robot.forwBakw(1);
                sleep(80);
                robot.forwBakw(0);
               stop();

            }
        }

        // Set the panel back to the default color
        relativeLayout.post(new Runnable() {
            public void run() {
                relativeLayout.setBackgroundColor(Color.WHITE);
            }
        });
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

    public double ballCheck()
    {
        double x = sensorColor.blue();
        double y = (0.9*x) + 2.2;
        return y;
    }
}


