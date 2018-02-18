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

            robot.runWithOutEncoders();
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
                robot.RClaw2.setPosition(robot.TOPRCLAW_CLOSE);
                robot.LClaw2.setPosition(robot.TOPLCLAW_CLOSE);
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
                robot.RClaw2.setPosition(robot.TOPRCLAW_OPEN);
                robot.LClaw2.setPosition(robot.TOPLCLAW_OPEN);
            }

            //sets all claws to mid position
            if(gamepad2.dpad_right)
            {
                robot.RClaw1.setPosition(.25);
                robot.RClaw2.setPosition(robot.RIGHT_CLAW_MID);
                robot.LClaw1.setPosition(robot.BOTTOM_LEFT_MID);
                robot.LClaw2.setPosition(robot.LEFT_CLAW_MID);
            }

            //lift up
            if (gamepad2.y)
            {
                robot.lift1.setPower(1);
                robot.lift1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            }
            //lift down
            else if (gamepad2.a)
            {
                robot.lift1.setPower(-1);
                robot.lift1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            }
            //stop lift
            else
            {
                robot.lift1.setPower(0);
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
                robot.pivot.setPosition(robot.PIVOT_MIN_RANGE);
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