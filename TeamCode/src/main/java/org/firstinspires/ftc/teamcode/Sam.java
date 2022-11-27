package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import java.util.List;
import org.firstinspires.ftc.robotcore.external.JavaUtil;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaCurrentGame;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.Tfod;

@TeleOp(name = "saaaM")
public class Sam extends LinearOpMode {
    private DcMotor LFront;
    private DcMotor RFront;
    private DcMotor LRear;
    private DcMotor RRear;
    double speed = .3;
    double stop = 0;
    double bad_speed = speed * -1;

    @Override
    public void runOpMode() {
        LFront = hardwareMap.get(DcMotor.class, "LFront");
        RFront = hardwareMap.get(DcMotor.class, "RFront");
        LRear = hardwareMap.get(DcMotor.class, "LRear");
        RRear = hardwareMap.get(DcMotor.class, "RRear");
        LFront.setDirection(DcMotorSimple.Direction.REVERSE);
        LRear.setDirection(DcMotorSimple.Direction.FORWARD);
        RFront.setDirection(DcMotorSimple.Direction.FORWARD);
        RRear.setDirection(DcMotorSimple.Direction.FORWARD);

        waitForStart();
        while (opModeIsActive()) {
            if (gamepad1.a) {
                LFront.setPower(speed);
                RFront.setPower(speed);
                LRear.setPower(speed);
                RRear.setPower(speed);
            } else if (gamepad1.y) {
                LFront.setPower(bad_speed);
                RFront.setPower(bad_speed);
                LRear.setPower(bad_speed);
                RRear.setPower(bad_speed);
            } else if (gamepad1.x) {
                LFront.setPower(speed);
                RFront.setPower(bad_speed);
                LRear.setPower(speed);
                RRear.setPower(bad_speed);
            } else if (gamepad1.b) {
                LFront.setPower(bad_speed);
                RFront.setPower(speed);
                LRear.setPower(bad_speed);
                RRear.setPower(speed);
            }else if (gamepad1.right_bumper) {
                LFront.setPower(speed);
                RFront.setPower(bad_speed);
                LRear.setPower(bad_speed);
                RRear.setPower(speed);
            }else if (gamepad1.left_bumper) {
                LFront.setPower(bad_speed);
                RFront.setPower(speed);
                LRear.setPower(speed);
                RRear.setPower(bad_speed);
            }


            else {
                LFront.setPower(0);
                RFront.setPower(0);
                LRear.setPower(0);
                RRear.setPower(0);
            }
        }



    }


}
