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

@TeleOp(name = "driver (Blocks to Java)")

public class driverCapableMode2 extends LinearOpMode {

    private DcMotor LFront;
    private DcMotor RFront;
    private DcMotor LRear;
    private DcMotor RRear;

    private double LFrontPower;
    private double RFrontPower;
    private double LRearPower;
    private double RRearPower;

    static final double THY_MAGIC_NUMBER = 26.67;
    static final double THY_MAGIC_NUMBER_SIDEWAYS = 28.125;
    static final double THY_MAGIC_NUMBER_ANGLE = 6.53;
    static final float DPAD_POWER_LVL = 2.0F;

    double Current_Power_Lvl = 0.30;

    /** tile size in inches */
    final private int tileSize = 24;

    int SignalNumber;

    private VuforiaCurrentGame vuforiaPOWERPLAY;
    private Tfod tfod;

    Recognition recognition;

    private void ParkingLocation (String signal) {
        if(signal == "1 Bolt") {
            telemetry.addData(" parking location:", 1);
            SignalNumber = 1;
        } else if(signal == "2 Bulb") {
            telemetry.addData(" parking location:", 2);
            SignalNumber = 2;
        } else if(signal == "3 Panel") {
            telemetry.addData(" parking location:", 3);
            SignalNumber = 3;
        }
    }

    private void RunToSignal (int signal) {
        if (signal == 1) {
            Move_F_B(1);
            Move_L_R(-1 * tileSize);
            Move_F_B(1.5 * tileSize);
        } else if (signal == 2) {
            Move_F_B(1);
            Move_F_B(1.5 * tileSize);
        } else if (signal == 3) {
            Move_L_R(tileSize);
            Move_F_B(1);
            Move_F_B(1.5 * tileSize);
        }
    }
    private void Config_Drive_to_Manual() {

        LFront.setPower(0);
        RFront.setPower(0);
        RRear.setPower(0);
        LRear.setPower(0);
        LFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        LRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    private void Config_Drive_to_RunToPos() {
        LFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        RFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        LFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        RRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        LFront.setPower(Current_Power_Lvl);
        RFront.setPower(Current_Power_Lvl);
        RRear.setPower(Current_Power_Lvl);
        LRear.setPower(Current_Power_Lvl);
    }
    private void Move_L_R(int dist_L_R) {
        LFront.setTargetPosition((int) (dist_L_R * THY_MAGIC_NUMBER_SIDEWAYS));
        RFront.setTargetPosition((int) -(dist_L_R * THY_MAGIC_NUMBER_SIDEWAYS));
        LRear.setTargetPosition((int) -(dist_L_R * THY_MAGIC_NUMBER_SIDEWAYS));
        RRear.setTargetPosition((int) (dist_L_R * THY_MAGIC_NUMBER_SIDEWAYS));
        Config_Drive_to_RunToPos();
        Wait_for_Drive_Motors_to_Move();
        Config_Drive_to_Manual();
    }

    /**
     * Describe this function...
     */
    private void Move_F_B(double dist_F_B) {
        LFront.setTargetPosition((int) (dist_F_B * THY_MAGIC_NUMBER));
        LRear.setTargetPosition((int) (dist_F_B * THY_MAGIC_NUMBER));
        RFront.setTargetPosition((int) (dist_F_B * THY_MAGIC_NUMBER));
        RRear.setTargetPosition((int) (dist_F_B * THY_MAGIC_NUMBER));
        Config_Drive_to_RunToPos();
        telemetry.addData("LFront", ((DcMotorEx) LFront).getTargetPositionTolerance());
        telemetry.addData("LRear", ((DcMotorEx) LRear).getTargetPositionTolerance());
        telemetry.addData("RFront", ((DcMotorEx) RFront).getTargetPositionTolerance());
        telemetry.addData("RRear", ((DcMotorEx) RRear).getTargetPositionTolerance());
        Wait_for_Drive_Motors_to_Move();
        Config_Drive_to_Manual();
    }
    private boolean Number_Within_Range_(double Num, int Min, int Max) {
        return Num == Math.min(Math.max(Num, Min), Max);
    }

    private boolean Is_opmode_stopped_() {
        return !opModeIsActive() || isStopRequested();
}
    private void Wait_for_Drive_Motors_to_Move() {
        while (
                !(Number_Within_Range_(LFront.getCurrentPosition(),
                LFront.getTargetPosition() - ((DcMotorEx) LFront).getTargetPositionTolerance(),
                LFront.getTargetPosition() + ((DcMotorEx) LFront).getTargetPositionTolerance()) &&
                Number_Within_Range_(RFront.getCurrentPosition(),
                RFront.getTargetPosition() - ((DcMotorEx) RFront).getTargetPositionTolerance(),
                RFront.getTargetPosition() + ((DcMotorEx) RFront).getTargetPositionTolerance()) &&
                Number_Within_Range_(LRear.getCurrentPosition(),
                LRear.getTargetPosition() - ((DcMotorEx) LRear).getTargetPositionTolerance(),
                LRear.getTargetPosition() + ((DcMotorEx) RRear).getTargetPositionTolerance()) &&
                Number_Within_Range_(RRear.getCurrentPosition(),
                RRear.getTargetPosition() - ((DcMotorEx) RRear).getTargetPositionTolerance(),
                RRear.getTargetPosition() + ((DcMotorEx) RRear).getTargetPositionTolerance()))
        ) {
            //Update_Telemetry();
            if (Is_opmode_stopped_()) {
                break;
            }
        }
    }

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

        LFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        LRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        List<Recognition> recognitions;

        vuforiaPOWERPLAY = new VuforiaCurrentGame();
        tfod = new Tfod();

        vuforiaPOWERPLAY.initialize(
                "", // vuforiaLicenseKey
                hardwareMap.get(WebcamName.class, "Webcam 1"), // cameraName
                "", // webcamCalibrationFilename
                false, // useExtendedTracking
                false, // enableCameraMonitoring
                VuforiaLocalizer.Parameters.CameraMonitorFeedback.NONE, // cameraMonitorFeedback
                0, // dx
                0, // dy
                0, // dz
                AxesOrder.XZY, // axesOrder
                90, // firstAngle
                90, // secondAngle
                0, // thirdAngle
                true); // useCompetitionFieldTargetLocations
        tfod.useDefaultModel();
        // Set min confidence threshold to 0.7
        tfod.initialize(vuforiaPOWERPLAY, (float) 0.6, true, true);
        // Initialize TFOD before waitForStart.
        // Activate TFOD here so the object detection labels are visible
        // in the Camera Stream preview window on the Driver Station.
        tfod.activate();
        // Enable following block to zoom in on target.
        tfod.setZoom(2.75, 16 / 9);
        telemetry.addData("DS preview on/off", "3 dots, Camera Stream");
        telemetry.addData(">", "Press Play to start");
        telemetry.update();

        waitForStart();
        while (opModeIsActive()) {

            telemetry.addData("LSX", gamepad1.left_stick_x);
            telemetry.addData("LSY", gamepad1.left_stick_y);


            Process_Movement();
            recognitions = tfod.getRecognitions();
            // If list is empty, inform the user. Otherwise, go
            // through list and display info for each recognition.
            if (JavaUtil.listLength(recognitions) == 0) {
                telemetry.addData("TFOD", "No items detected.");
            } else {
                // index = 0;
                // Iterate through list and call a function to
                // display info for each recognized object.
                for (Recognition recognition_item : recognitions) {
                    recognition = recognition_item;
                    // Display info.
                    //displayInfo(index);
                    //ParkingLocation(recognition.getLabel());
                    // Increment index.
                    //index = index + 1;
                }
                ParkingLocation(recognition.getLabel());
            }
            telemetry.update();
        }
        // Deactivate TFOD.
        tfod.deactivate();

        vuforiaPOWERPLAY.close();
        tfod.close();
    }

    /**
     * Get the denominator for ...?
     */
    private void GoStraight() {
        Set_Power_Values(
            0,
            -1 * DPAD_POWER_LVL,
            gamepad1.right_stick_x,
            gamepad1.right_stick_y,
            Current_Power_Lvl
        );
    }

    private void GoBackwards() {
        Set_Power_Values(
            0,
                DPAD_POWER_LVL,
            gamepad1.right_stick_x,
            gamepad1.right_stick_y,
            Current_Power_Lvl
        );
    }

    private void GoLeft() {
        Set_Power_Values(
            -1 * DPAD_POWER_LVL,
            0,
            gamepad1.right_stick_x,
            gamepad1.right_stick_y,
            Current_Power_Lvl
        );
    }

    private void GoRight() {
        Set_Power_Values(
                DPAD_POWER_LVL,
                0,
                gamepad1.right_stick_x,
                gamepad1.right_stick_y,
                Current_Power_Lvl
        );
    }

    private double Get_Denominator() {
        double sum = Math.abs(gamepad1.left_stick_y)
                   + Math.abs(gamepad1.left_stick_x)
                   + Math.abs(gamepad1.right_stick_x);

        if (sum > 1) {
            return sum;
        } else {
            return 1;
        }
    }

    /**
     * Gets power values.
     */
    private double[] Get_Power_Values(
            float LeftX,
            float LeftY,
            float RightX,
            float RightY,
            double Power_Mod)
    {
        double denominator = Get_Denominator();
        double lFront = (((-LeftY + LeftX) + RightX) / denominator) * Power_Mod;
        double lRear = (((-LeftY - LeftX) + RightX) / denominator) * Power_Mod;
        double rFront = (((-LeftY - LeftX) - RightX) / denominator) * Power_Mod;
        double rRear = (((-LeftY + LeftX) - RightX) / denominator) * Power_Mod;
        return new double[]{lFront, lRear, rFront, rRear};
    }

    private void Set_Power_Values(
            float LeftX,
            float LeftY,
            float RightX,
            float RightY,
            double Power_Mod)
    {
        double[] values = Get_Power_Values(
                LeftX,
                LeftY,
                RightX,
                RightY,
                Power_Mod);

        LFront.setPower(values[0]);
        LRear.setPower(values[1]);
        RFront.setPower(values[2]);
        RRear.setPower(values[3]);
    }

    /**
     * Processes movement, old-style adapted from last year (2021-22
     */
    private void Process_Movement() {
        telemetry.addData("Process_Movement",0);
        if (gamepad1.dpad_up) {
            GoStraight();
        } else if (gamepad1.dpad_down) {
            GoBackwards();
        } else if (gamepad1.dpad_left) {
            GoLeft();
        } else if (gamepad1.dpad_right) {
            GoRight();
        } else if (gamepad1.y) {
            telemetry.addData("Move_F_B",tileSize);
            Move_F_B(tileSize);
        } else if (gamepad1.a) {
            Move_F_B(-1 * tileSize);
        } else if (gamepad1.x) {
            Move_L_R(-1 * tileSize);
        } else if (gamepad1.b) {
            Move_L_R(tileSize);
        } else if (gamepad1.right_bumper) {
            RunToSignal(SignalNumber);
        } else {
            Set_Power_Values(
                gamepad1.left_stick_x,
                gamepad1.left_stick_y,
                gamepad1.right_stick_x,
                gamepad1.right_stick_y,
                Current_Power_Lvl
            );
        }
    }
}
