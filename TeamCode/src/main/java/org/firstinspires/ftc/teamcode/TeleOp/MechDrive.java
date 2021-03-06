package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.GGHardware;
import org.firstinspires.ftc.teamcode.GGParameters;
/**
 * Created by User on 10/30/2017.
 */

@TeleOp(name="MTeleOp",group="TeleOp")
public class MechDrive extends LinearOpMode
{


    GGHardware robot = new GGHardware();
    private ElapsedTime  runtime= new ElapsedTime();


    @Override
    public void runOpMode()
    {


        GGParameters parameters = new GGParameters(this);
        robot.init(parameters);
        robot.resetEncoders();
        waitForStart();

        while (opModeIsActive())
        {

            robot.backleft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            robot.frontleft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            robot.backright.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            robot.frontright.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            int position = robot.backleft.getCurrentPosition();
            telemetry.addData("Encoder Position", position);
            telemetry.update();

            robot.getJoyVals();

            robot.pwr = robot.y; //this can be tweaked for exponential power increase

            robot.frontright.setPower(Range.clip(robot.pwr - robot.x + robot.z, -1, 1));
            robot.backleft.setPower(Range.clip(robot.pwr - robot.x - robot.z, -1, 1));
            robot.frontleft.setPower(Range.clip(robot.pwr + robot.x - robot.z, -1, 1));
            robot.backright.setPower(Range.clip(robot.pwr + robot.x + robot.z, -1, 1));

            //closes bottom claws
            if (gamepad2.left_trigger > 0) {
                robot.RClaw1.setPosition(robot.BOTTOMRCLAW_CLOSE);
                robot.LClaw1.setPosition(robot.BOTTOMLCLAW_CLOSE);
            }
            //closes top claws
            if (gamepad2.left_bumper)
            {
                robot.flipperl.setPosition(robot.FLIPPERR_MAX_RANGE);
                robot.flipperr.setPosition(robot.FLIPPERL_MAX_RANGE);
            }
            //opens bottom claws
            if (gamepad2.right_trigger > 0)
            {
                robot.RClaw1.setPosition(robot.BOTTOMRCLAW_OPEN);
                robot.LClaw1.setPosition(robot.BOTTOMLCLAW_OPEN);
            }
            //opens top claws
            if (gamepad2.right_bumper)
            {
                robot.flipperl.setPosition(0.25);
                robot.flipperr.setPosition(0.75);
            }

            //sets all claws to mid position
            if(gamepad2.dpad_left)
            {
                robot.RClaw1.setPosition(0.13);
                robot.RClaw2.setPosition(robot.RIGHT_CLAW_MID);
                robot.LClaw1.setPosition(robot.BOTTOM_LEFT_MID);
                robot.LClaw2.setPosition(robot.LEFT_CLAW_MID);
            }

            if(gamepad2.dpad_right)
            {
                robot.RClaw1.setPosition(0.20);
                robot.RClaw2.setPosition(0.25);
                robot.LClaw1.setPosition(0.6);
                robot.LClaw2.setPosition(0.8);
            }

            //lift up
            if (gamepad2.y)
            {
                robot.intakel.setPower(1);
                robot.intaker.setPower(-1);

            }
            //lift down
            else if (gamepad2.a)
            {
                robot.intakel.setPower(-1);
                robot.intaker.setPower(1);
                robot.intakel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            }
            //stop lift
            else
            {
                robot.intakel.setPower(0);
                robot.intaker.setPower(0);
                robot.intakel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
                robot.intaker.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            }

            if(gamepad2.x)
            {
                robot.relicLift.setPower(-1);

            }
            else
            {
                robot.relicLift.setPower(0);
                robot.relicLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            }

            if(gamepad2.b)
            {
                robot.relicLift.setPower(1);

            }
            else
            {
                robot.relicLift.setPower(0);
                robot.relicLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            }

            //relic pivot arm out
            if(gamepad2.dpad_down)
            {
                robot.relicUpDown.setPosition(robot.RELICUPDOWN_MAX_RANGE);
            }

            //color arm middle
            if(gamepad2.dpad_up)
            {
                robot.relicUpDown.setPosition(robot.RELICUPDOWN_MIN_RANGE);
            }

            if(gamepad1.b)
            {
                robot.pivot.setPosition(robot.PIVOT_MAX_RANGE);
                //robot.spin.setPosition(robot.SPIN_MID_RANGE);
            }

            if(gamepad1.dpad_left)
            {
                robot.relicClaw.setPosition(robot.RELICCLAW_MIN_RANGE);
            }
            if(gamepad1.dpad_right)
            {
                robot.relicClaw.setPosition(robot.RELICCLAW_MAX_RANGE);
            }

        }
    }


}