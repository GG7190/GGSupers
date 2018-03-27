package org.firstinspires.ftc.teamcode.Autonomous;

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
/**
 * Created by User on 10/30/2017.
 */

@Autonomous(name="VuRed1", group ="Autonomous")
public class VuRed1 extends LinearOpMode
{
    public static final String TAG = "Vuforia VuMark Sample";
    double pidSpeed;
    //grace
    OpenGLMatrix lastLocation = null;

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
            autoMain(relicTemplate);
            telemetry.addData("done", "?");
            telemetry.update();
        }
    }

    String format(OpenGLMatrix transformationMatrix) {
        return (transformationMatrix != null) ? transformationMatrix.formatAsTransform() : "null";
    }

    boolean reachedPosition = false;


    public void forwBackw(double motorPwr) {
        robot.frontright.setPower(motorPwr);
        robot.frontleft.setPower(motorPwr);
        robot.backright.setPower(motorPwr);
        robot.backleft.setPower(motorPwr);
    }


    public void driftRight() {
        robot.frontright.setPower(1);
        robot.frontleft.setPower(-1);
        robot.backright.setPower(-1);
        robot.backleft.setPower(1);
    }

    public void driftLeft() {
        robot.frontright.setPower(-1);
        robot.frontleft.setPower(1);
        robot.backright.setPower(1);
        robot.backleft.setPower(-1);
    }


    public void turnRight() {
        robot.frontright.setPower(1);
        robot.frontleft.setPower(-1);
        robot.backright.setPower(1);
        robot.backleft.setPower(-1);
    }

    public void turnLeft() {
        robot.frontright.setPower(-1);
        robot.frontleft.setPower(1);
        robot.backright.setPower(-1);
        robot.backleft.setPower(1);
    }
    public void runEncodersUntil(int encoderAmount) {
        reachedPosition = false;

        while (!reachedPosition) {
            if (Math.abs(robot.backleft.getCurrentPosition()) > encoderAmount) {
                pidSpeed = .25 + ((encoderAmount - robot.backleft.getCurrentPosition()));
                forwBackw(pidSpeed);
                reachedPosition = true;
            } else {

            }

        }
    }

    public void resetEncoders() {
        robot.backleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void runWithEncoders() {
        if (robot.backleft != null) {
            robot.backleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }

    public void stopMotors() {
        robot.frontleft.setPower(0);
        robot.frontright.setPower(0);
        robot.backleft.setPower(0);
        robot.backright.setPower(0);
    }

    public void right() {
        forwBackw(1);
        sleep(500);
        telemetry.addData("first drive", "?");
        telemetry.update();
        stopMotors();
        vuMain();
        stop();
    }

    public void center() {
        forwBackw(1);
        sleep(700);
        telemetry.addData("first drive", "?");
        telemetry.update();
        stopMotors();
        vuMain();
        stop();
    }

    public void left() {
        forwBackw(1);
        sleep(900);
        stopMotors();
        telemetry.addData("first drive", "?");
        telemetry.update();
        vuMain();
        stop();
    }

    public void vuMain()
    {
        turnLeft();
        sleep(1000);
        telemetry.addData("turn", "?");
        telemetry.update();
        stopMotors();
       // robot.lift1.setPower(1);
        sleep(500);
        //robot.lift1.setPower(0);
        telemetry.addData("lift", "?");
        telemetry.update();
        forwBackw(1);
        sleep(1000);
        telemetry.addData("second drive forward", "?");
        telemetry.update();
        stopMotors();
        robot.LClaw1.setPosition(robot.BOTTOMLCLAW_OPEN);
        robot.RClaw1.setPosition(robot.BOTTOMRCLAW_OPEN);
        telemetry.addData("open claws", "?");
        telemetry.update();
        //robot.lift1.setPower(-0.5);
        telemetry.addData("lift 2", "?");
        telemetry.update();
        sleep(700);
        //robot.lift1.setPower(0);
    }

    public void autoMain(VuforiaTrackable relicTemplate) {
        telemetry.addData("before move", "!");
        telemetry.update();


        forwBackw(-.25);
        sleep(250);

        stopMotors();
        telemetry.addData("After stop", "!");
        telemetry.update();

        /**
         * See if any of the instances of {@link relicTemplate} are currently visible.
         * {@link RelicRecoveryVuMark} is an enum which can have the following values:
         * UNKNOWN, LEFT, CENTER, and RIGHT. When a VuMark is visible, something other than
         * UNKNOWN will be returned by {@link RelicRecoveryVuMark#from(VuforiaTrackable)}.
         */
        RelicRecoveryVuMark vuMark = searchVuMark(relicTemplate);
        if (vuMark != RelicRecoveryVuMark.UNKNOWN)
        {
                /* Found an instance of the template. In the actual game, you will probably
                 * loop until this condition occurs, then move on to act accordingly depending
                 * on which VuMark was visible. */
            telemetry.addData("VuMark", "%s visible", vuMark);
            telemetry.update();
            sleep(5000);

            if (vuMark == RelicRecoveryVuMark.RIGHT) {
                telemetry.addData("right", "?");
                telemetry.update();
                sleep(5000);
                right();
            }

            if (vuMark == RelicRecoveryVuMark.CENTER) {
                telemetry.addData("center", "?");
                telemetry.update();
                sleep(5000);
                center();
            }

            if (vuMark == RelicRecoveryVuMark.LEFT) {
                telemetry.addData("left", "?");
                telemetry.update();
                sleep(5000);
                left();
            }
            stop();
                /* For fun, we also exhibit the navigational pose. In the Relic Recovery game,
                 * it is perhaps unlikely that you will actually need to act on this pose information, but
                 * we illustrate it nevertheless, for completeness. */
            //OpenGLMatrix pose = ((VuforiaTrackableDefaultListener)relicTemplate.getListener()).getPose();
            //telemetry.addData("Pose", format(pose));

                /* We further illustrate how to decompose the pose into useful rotational and
                 * translational components */
                /*
                if (pose != null) {
                    VectorF trans = pose.getTranslation();
                    Orientation rot = Orientation.getOrientation(pose, AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);

                    // Extract the X, Y, and Z components of the offset of the target relative to the robot
                    double tX = trans.get(0);
                    double tY = trans.get(1);
                    double tZ = trans.get(2);

                    // Extract the rotational components of the target relative to the robot
                    double rX = rot.firstAngle;
                    double rY = rot.secondAngle;
                    double rZ = rot.thirdAngle;
                }
                */

        }
        //else {
        //    telemetry.addData("VuMark", "not visible");
        //}

        telemetry.addData("AutoMain Exit", " ");
        telemetry.update();
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