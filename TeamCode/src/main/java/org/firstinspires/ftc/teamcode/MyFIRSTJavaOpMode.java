import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.REVERSE;
import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.FORWARD;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@TeleOp

public class MyFIRSTJavaOpMode extends LinearOpMode {
    private DcMotor motor1;

    @Override
    public void runOpMode() {
        motor1 = hardwareMap.get(DcMotor.class, "motor1");


        telemetry.addData("Initializing", "h");
        telemetry.update();
        // Wait for the game to start (driver presses PLAY)
        waitForStart();
            while (opModeIsActive()) {
                motor1.setDirection(REVERSE);
                if (gamepad1.b) {
                    telemetry.addData("b", "Configuring");
                    motor1.setPower(1);
                }
                if (gamepad1.y) {
                    telemetry.addData("y", "Configuring");
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
