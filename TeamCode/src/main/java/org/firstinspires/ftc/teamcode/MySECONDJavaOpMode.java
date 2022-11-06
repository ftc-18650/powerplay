package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.REVERSE;
import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.FORWARD;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@TeleOp

public class MySECONDJavaOpMode extends LinearOpMode {
    private DcMotor motor1;

    @Override
    public void runOpMode() {
        motor1 = hardwareMap.get(DcMotor.class, "motor1");


        telemetry.addData("Initializing", "Configuring");
        telemetry.update();
        // Wait for the game to start (driver presses PLAY)
        motor1.setDirection(REVERSE);
        waitForStart();
        // run until the end of the match (driver presses STOP)
//        int time = 20000;
//        while (time > 0) {
//            //motor1.setPower(1)
//            telemetry.addData("h", 2);
//            telemetry.update();
//            time -= 1;
        while (opModeIsActive()) {
            if (gamepad1.x) {
                motor1.setPower(1);
            } else if (gamepad1.b) {
                motor1.setPower(0);
            }
        }
    }
}

//package org.firstinspires.ftc.teamcode;
//import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.FORWARD;
//
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.hardware.DcMotor;
//
//public class MyFIRSTJavaOpMode extends LinearOpMode {
//    private DcMotor motor0;
//
//    @Override
//    public void runOpMode() {
//        motor0 = hardwareMap.get(DcMotor.class, "motorTest");
//
//        while(opModeIsActive()) {
//            motor0.setDirection(FORWARD);
//            motor0.setPower(1);
//        }
//    }
//}
