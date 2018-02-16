package org.firstinspires.ftc.teamcode.Autonomous;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuMarkInstanceId;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.teamcode.GGHardware;
import org.firstinspires.ftc.teamcode.GGParameters;

import java.util.Locale;

/**
 * Created by User on 2/9/2018.
 */
@Autonomous(name = "keyAndJewelBlue", group = "Autonomous")
public class keyAndJewelBlue  extends LinearOpMode {


    /**
     * {@link #vuforia} is the variable we will use to store our instance of the Vuforia
     * localization engine.
     */
    VuforiaLocalizer vuforia;

    GGHardware robot = new GGHardware();

    @Override
    public void runOpMode() {
        GGParameters GGparameters = new GGParameters(this);
        robot.init(GGparameters);

        ColorSensor sensorColor;
        DistanceSensor sensorDistance;

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


        boolean finished = false;

        waitForStart();

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        parameters.vuforiaLicenseKey = "AelWwd//////AAAAGQDnHa68TEwbisDdlvJmnylYK2LsElZD9aL1bZpHc317BsOaJFu+XfN336gDBGhS+K1tbBSgoRSbghMFHhYrhwLv7QAm+cSJ1QdV/sWH4/j59cSO0Pc8XV0/TgSazwzWu3PZ+jJnas3IBOcFoI/s9GCDVUTM0GdIr1toNadpNn/MVGjFzD/unzP1A5OSlQpn3/hS33JyaLlWghEYjPoTV3qPI8mNhKry/pnPJm80Mu0a6V0kQBKKW8fSaApkYfzOtgCjCyGGDuYKAN7W/teQVcYuQHRsTzfW6i9YxfxvPwCnUu8/fVNDDppDjOyGjTPWIebISx9yJ1LvHYfHkvTvKXcW587pSW/aiDqThJH3HVh5";


        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);

        /**
         * Load the data set containing the VuMarks for Relic Recovery. There's only one trackable
         * in this data set: all three of the VuMarks in the game were created from this one template,
         * but differ in their instance id information.
         * @see VuMarkInstanceId
         */
        VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate"); // can help in debugging; otherwise not necessary

        telemetry.addData(">", "Press Play to start");
        telemetry.update();
        waitForStart();

        relicTrackables.activate();
        telemetry.addData("Before while statement", "?");
        telemetry.update();
        if (opModeIsActive()) {
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
            autoMain(relicTemplate, sensorColor);
            telemetry.addData("done", "?");
            telemetry.update();
        }
    }


    public void autoMain(VuforiaTrackable relicTemplate, ColorSensor color) {
        telemetry.addData("before move", "!");
        telemetry.update();

        robot.pivot.setPosition(robot.PIVOT_MAX_RANGE);

        //Wait for the sensor to move down into place.
        sleep(2000);


        if(color.red() > ballCheck(color))
        {
            robot.RClaw1.setPosition(robot.BOTTOMRCLAW_CLOSE);
            robot.LClaw1.setPosition(robot.BOTTOMLCLAW_CLOSE);
            sleep(700);
            robot.lift1.setPower(.75);
            sleep(150);
            robot.lift1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            robot.lift1.setPower(0);
            sleep(50);
            robot.forwBakw(-.75);
            sleep(40);
            robot.forwBakw(0);
            sleep(300);
            robot.pivot.setPosition(robot.PIVOT_MIN_RANGE);
            sleep(100);
        }

        else
        {
            robot.RClaw1.setPosition(robot.BOTTOMRCLAW_CLOSE);
            robot.LClaw1.setPosition(robot.BOTTOMLCLAW_CLOSE);
            sleep(700);
            robot.lift1.setPower(.75);
            sleep(150);
            robot.lift1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            robot.lift1.setPower(0);
            sleep(50);
            robot.forwBakw(1);
            sleep(50);
            robot.forwBakw(0);
            sleep(200);
            robot.pivot.setPosition(robot.PIVOT_MIN_RANGE);
            sleep(100);
            forwBackw(-0.75);
            sleep(300);
            forwBackw(0.00);
        }

        forwBackw(-0.75);
        sleep(300);
        forwBackw(0.00);
        telemetry.addData("After stop", "!");
        telemetry.update();

        /**
         * See if any of the instances of {@link relicTemplate} are currently visible.
         * {@link RelicRecoveryVuMark} is an enum which can have the following values:
         * UNKNOWN, LEFT, CENTER, and RIGHT. When a VuMark is visible, something other than
         * UNKNOWN will be returned by {@link RelicRecoveryVuMark#from(VuforiaTrackable)}.
         */
        RelicRecoveryVuMark vuMark = searchVuMark(relicTemplate);
        if (vuMark != RelicRecoveryVuMark.UNKNOWN) {
                /* Found an instance of the template. In the actual game, you will probably
                 * loop until this condition occurs, then move on to act accordingly depending
                 * on which VuMark was visible. */
            telemetry.addData("VuMark", "%s visible", vuMark);
            telemetry.update();
            //sleep(5000);

            if (vuMark == RelicRecoveryVuMark.RIGHT) {
                telemetry.addData("right", "?");
                telemetry.update();
                sleep(2000);
                right();
            }

            if (vuMark == RelicRecoveryVuMark.CENTER) {
                telemetry.addData("center", "?");
                telemetry.update();
                sleep(2000);
                center();
            }

            if (vuMark == RelicRecoveryVuMark.LEFT) {
                telemetry.addData("left", "?");
                telemetry.update();
                sleep(2000);
                left();
            }
            stop();

        }
    }



    final int LEFT = 1400;
    final int CENTER = 1150;
    final int RIGHT = 1000;

    public void forwBackw(double motorPwr)
    {
        robot.frontright.setPower(motorPwr);
        robot.frontleft.setPower(motorPwr);
        robot.backright.setPower(motorPwr);
        robot.backleft.setPower(motorPwr);
    }

    public void turnLeft() {
        robot.frontright.setPower(-1);
        robot.frontleft.setPower(1);
        robot.backright.setPower(-1);
        robot.backleft.setPower(1);
    }

    public double ballCheck(ColorSensor color)
    {
        double x = color.blue();
        double y = (0.9*x) + 2.2;
        return y;
    }

    public RelicRecoveryVuMark searchVuMark(VuforiaTrackable relicTemplate)
    {
        RelicRecoveryVuMark result = RelicRecoveryVuMark.UNKNOWN;
        /**
         * See if any of the instances of {@link relicTemplate} are currently visible.
         * {@link RelicRecoveryVuMark} is an enum which can have the following values:
         * UNKNOWN, LEFT, CENTER, and RIGHT. When a VuMark is visible, something other than
         * UNKNOWN will be returned by {@link RelicRecoveryVuMark#from(VuforiaTrackable)}.
         */
        do
        {
            result = RelicRecoveryVuMark.from(relicTemplate);
        }
        while (result == RelicRecoveryVuMark.UNKNOWN);
        telemetry.addData("vuMark = ", result);
        telemetry.update();
        return result;
    }

    public void right() {
        forwBackw(1);
        sleep(600);
        telemetry.addData("first drive", "?");
        telemetry.update();
        forwBackw(0.00);
        vuMain(RIGHT);
        stop();
    }

    public void center() {
        forwBackw(1);
        sleep(1000);
        telemetry.addData("first drive", "?");
        telemetry.update();
        forwBackw(0.00);
        vuMain(CENTER);
        stop();
    }

    public void left() {
        forwBackw(1);
        sleep(900);
        forwBackw(0.00);
        telemetry.addData("first drive", "?");
        telemetry.update();
        vuMain(LEFT);
        stop();
    }

    public void vuMain(int column)
    {
        turnLeft();
        sleep(column);
        telemetry.addData("turn", "?");
        telemetry.update();
        forwBackw(0.00);
        forwBackw(-1);
        sleep(300);
        telemetry.addData("second drive forward", "?");
        telemetry.update();
        forwBackw(0.00);
        robot.LClaw1.setPosition(robot.BOTTOMLCLAW_OPEN);
        robot.RClaw1.setPosition(robot.BOTTOMRCLAW_OPEN);
        telemetry.addData("open claws", "?");
        telemetry.update();
        robot.lift1.setPower(0.5);
        telemetry.addData("lift 2", "?");
        telemetry.update();
        sleep(700);
        robot.lift1.setPower(0);
        forwBackw(1);
        sleep(40);
        forwBackw(0);
        sleep(200);
    }
}