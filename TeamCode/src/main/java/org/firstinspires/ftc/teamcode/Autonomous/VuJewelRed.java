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

        import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
        import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
        import com.qualcomm.robotcore.hardware.DcMotor;

        import org.firstinspires.ftc.robotcore.external.ClassFactory;
        import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
        import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
        import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
        import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
        import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
        import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
        import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
        import org.firstinspires.ftc.robotcore.external.navigation.VuMarkInstanceId;
        import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
        import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
        import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
        import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
        import org.firstinspires.ftc.teamcode.GGHardware;
        import org.firstinspires.ftc.teamcode.GGParameters;
        import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
        import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
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
@Autonomous(name = "VU jewel Red", group = "Autonomous")

public class VuJewelRed extends LinearOpMode {

    GGHardware robot  = new GGHardware();

    ColorSensor sensorColor;
    DistanceSensor sensorDistance;

    VuforiaLocalizer vuforia;

    @Override
    public void runOpMode() {
        GGParameters ggparameters = new GGParameters(this);
        robot.init(ggparameters);



        /**
         * Load the data set containing the VuMarks for Relic Recovery. There's only one trackable
         * in this data set: all three of the VuMarks in the game were created from this one template,
         * but differ in their instance id information.
         * @see VuMarkInstanceId
         */


        telemetry.addData(">", "Press Play to start");
        telemetry.update();

        waitForStart();
        VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate"); // can help in debugging; otherwise not necessary

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        parameters.vuforiaLicenseKey = "AelWwd//////AAAAGQDnHa68TEwbisDdlvJmnylYK2LsElZD9aL1bZpHc317BsOaJFu+XfN336gDBGhS+K1tbBSgoRSbghMFHhYrhwLv7QAm+cSJ1QdV/sWH4/j59cSO0Pc8XV0/TgSazwzWu3PZ+jJnas3IBOcFoI/s9GCDVUTM0GdIr1toNadpNn/MVGjFzD/unzP1A5OSlQpn3/hS33JyaLlWghEYjPoTV3qPI8mNhKry/pnPJm80Mu0a6V0kQBKKW8fSaApkYfzOtgCjCyGGDuYKAN7W/teQVcYuQHRsTzfW6i9YxfxvPwCnUu8/fVNDDppDjOyGjTPWIebISx9yJ1LvHYfHkvTvKXcW587pSW/aiDqThJH3HVh5";


        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);

        waitForStart();
        relicTrackables.activate();
        telemetry.addData("Before while statement", "?");
        telemetry.update();

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
        relicTrackables.activate();
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
                //sense color and knock off
                robot.RClaw1.setPosition(robot.BOTTOMRCLAW_CLOSE);
                robot.LClaw1.setPosition(robot.BOTTOMLCLAW_CLOSE);
                sleep(700);
                robot.lift1.setPower(.75);
                sleep(50);
                robot.lift1.setPower(0);
                sleep(50);
                robot.forwBakw(.75);
                sleep(100);
                robot.forwBakw(0);
                sleep(300);
                robot.pivot.setPosition(robot.PIVOT_MIN_RANGE);
                sleep(100);
                //drive to see pictograph
                forwBackw(-.25);
                sleep(250);
                RelicRecoveryVuMark vuMark = searchVuMark(relicTemplate);
                if(vuMark != RelicRecoveryVuMark.UNKNOWN)
                {

                    if (vuMark == RelicRecoveryVuMark.RIGHT)
                    {
                        red(right);
                    }

                    if (vuMark == RelicRecoveryVuMark.CENTER)
                    {
                        red(center);
                    }

                    if (vuMark == RelicRecoveryVuMark.LEFT)
                    {
                        red(left);
                    }
                }

                else
                {
                    stop();
                }


            }
            else
            {
                robot.RClaw1.setPosition(robot.BOTTOMRCLAW_CLOSE);
                robot.LClaw1.setPosition(robot.BOTTOMLCLAW_CLOSE);
                sleep(700);
                robot.lift1.setPower(.75);
                sleep(50);
                robot.lift1.setPower(0);
                sleep(50);
                robot.forwBakw(-1);
                sleep(50);
                robot.forwBakw(0);
                sleep(200);
                robot.pivot.setPosition(robot.PIVOT_MIN_RANGE);
                sleep(100);
                //blue(right);
                RelicRecoveryVuMark vuMark = searchVuMark(relicTemplate);
                if(vuMark != RelicRecoveryVuMark.UNKNOWN)
                {

                    if (vuMark == RelicRecoveryVuMark.RIGHT)
                    {
                        blue(right);
                    }

                    if (vuMark == RelicRecoveryVuMark.CENTER)
                    {
                        blue(center);
                    }

                    if (vuMark == RelicRecoveryVuMark.LEFT)
                    {
                        blue(left);
                    }
                }

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


    public double ballCheck()
    {
        double x = sensorColor.blue();
        double y = (0.9*x) + 2.2;
        return y;
    }

    int right = 300;
    int center = 400;
    int left = 500;

    public void blue(int turn)
    {
        robot.forwBakw(-1);
        sleep(600);
        robot.forwBakw(0);
        robot.turnLeft();
        sleep(turn);
        robot.lift1.setPower(-.75);
        sleep(50);
        robot.lift1.setPower(0);
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
        robot.forwBakw(-1);
        sleep(80);
        robot.forwBakw(0);
        stop();
    }

    public void red(int turn)
    {
        robot.forwBakw(-.50);
        sleep(1300);
        robot.forwBakw(0);
        sleep(200);
        robot.turnLeft();
        sleep(turn);
        robot.forwBakw(-.75);
        sleep(200);
        robot.lift1.setPower(-.75);
        sleep(50);
        robot.lift1.setPower(0);
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
}


