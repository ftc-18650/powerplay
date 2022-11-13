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

@TeleOp(name = "signals (Android Studio)")

public class signals extends LinearOpMode {

    private DcMotor LFront;
    private DcMotor RFront;
    private DcMotor LRear;
    private DcMotor RRear;

    double Current_Power_Lvl = 0.20;

    static final double THY_MAGIC_NUMBER = 26.67;
    static final double THY_MAGIC_NUMBER_SIDEWAYS = 28.125;

    final private int tileSize = 24;
    int SignalNumber;

    private VuforiaCurrentGame vuforiaPOWERPLAY;
    private Tfod tfod;

    Recognition recognition;

    /**
     * This function is executed when this Op Mode is selected from the Driver Station.
     */
    private void ParkingLocation (String signal) {
        if(signal == "1 Bolt") {
            telemetry.addData(" parking location:", 1);
            telemetry.update();
            RunToSignal(1);
        } else if(signal == "2 Bulb") {
            telemetry.addData(" parking location:", 2);
            telemetry.update();
            RunToSignal(2);
        } else if(signal == "3 Panel") {
            telemetry.addData(" parking location:", 3);
            telemetry.update();
            RunToSignal(3);
        }
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
    private void ChooseSignal () {
        if (gamepad1.dpad_left) {
            RunToSignal(1);
        } else if (gamepad1.dpad_up) {
            RunToSignal(2);
        } else if (gamepad1.dpad_right) {
            RunToSignal(3);
        }

    }
    private void RunToSignal (int Signal) {
        SignalNumber = Signal;
        if (SignalNumber == 1) {
            Move_L_R(-1 * tileSize);
            Move_F_B(1.5 * tileSize);
        } else if (SignalNumber == 2) {
            Move_F_B(1.5 * tileSize);
        } else if (SignalNumber == 3) {
            Move_L_R(tileSize);
            Move_F_B(1.5 * tileSize);
        }
    }
    /**
     * This function is executed when this Op Mode is selected from the Driver Station.
     */
    @Override
    public void runOpMode() {
        List<Recognition> recognitions;
        int index;

        vuforiaPOWERPLAY = new VuforiaCurrentGame();
        tfod = new Tfod();

        // Sample TFOD Op Mode
        // Initialize Vuforia.
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
        tfod.initialize(vuforiaPOWERPLAY, (float) 0.7, true, true);
        // Initialize TFOD before waitForStart.
        // Activate TFOD here so the object detection labels are visible
        // in the Camera Stream preview window on the Driver Station.
        tfod.activate();
        // Enable following block to zoom in on target.
        tfod.setZoom(1.75, 16 / 9);
        telemetry.addData("DS preview on/off", "3 dots, Camera Stream");
        telemetry.addData(">", "Press Play to start");
        telemetry.update();
        // Wait for start command from Driver Station.
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

        waitForStart();
        while (opModeIsActive()) {
            ChooseSignal();
            recognitions = tfod.getRecognitions();
            // If list is empty, inform the user. Otherwise, go
            // through list and display info for each recognition.
            if (JavaUtil.listLength(recognitions) == 0) {
                telemetry.addData("TFOD", "No items detected.");
            } else {
                index = 0;
                // Iterate through list and call a function to
                // display info for each recognized object.
                for (Recognition recognition_item : recognitions) {
                    recognition = recognition_item;
                    // Display info.
                    //displayInfo(index);
                    ParkingLocation(recognition.getLabel());
                    return;
                    // Increment index.
                    //index = index + 1;
                }
            }
            telemetry.update();
        }
        // Deactivate TFOD.
        tfod.deactivate();

        vuforiaPOWERPLAY.close();
        tfod.close();

    }
}

