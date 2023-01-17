package org.firstinspires.ftc.teamcode;

import android.view.MotionEvent;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import java.util.List;
import org.firstinspires.ftc.robotcore.external.JavaUtil;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaBase;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaCurrentGame;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.Tfod;

@TeleOp(name = "navtest (Blocks to Java)")
public class Navtest extends LinearOpMode {

    private DcMotor LFront;
    private DcMotor RFront;
    private DcMotor LRear;
    private DcMotor RRear;

    static final float DPAD_POWER_LVL = 1.0F;

    int robotX = 6;
    int robotY = 2;
    int startPos = 1;
    int targetPosX = 1;
    int targetPosY = 1;
    int differenceX;
    int differenceY;
    int parkingPosX = 1;
    int parkingPosY = 1;

    private int detected = 1;
    int h = 0;
    private boolean MoveToSpot = false;

    double Current_Power_Lvl = 0.30;

    /** tile size in inches */
    final private int tileSizeForward = 668;
    final private int tileSizeSideways = 695;

    int SignalNumber;
    Recognition recognition;



    private VuforiaCurrentGame vuforiaPOWERPLAY;
    private Tfod tfod;

    VuforiaBase.TrackingResults vuforiaResults;

    /**
     * Autonomous Parking
     */

    private void ParkingLocation (String signal) {
        if(signal == "chip") {
            telemetry.addData(" parking location:", 1);
            SignalNumber = 1;
        } else if(signal == "macaroon") {
            telemetry.addData(" parking location:", 2);
            SignalNumber = 2;
        } else if(signal == "pizza") {
            telemetry.addData(" parking location:", 3);
            SignalNumber = 3;
        } else {
            SignalNumber = 0;
        }

    }
    private void RunToSignal (int signal) {
        MoveToSpot = true;
        if (signal == 1) {
            Move_F_B(1);
            Move_L_R(-1 * tileSizeSideways);
            Move_F_B(1.5 * tileSizeForward);
        } else if (signal == 2) {
            Move_F_B(1);
            Move_F_B(1.5 * tileSizeForward);
        } else if (signal == 3) {
            Move_L_R(tileSizeSideways);
            Move_F_B(1);
            Move_F_B(1.5 * tileSizeForward);
        } else {
            MoveToSpot = false;
            return;
        }
    }

    /**
     * Tile movement
     */
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
    private void Move_L_R(double dist_L_R) {
        LFront.setTargetPosition((int) dist_L_R);
        RFront.setTargetPosition((int) -dist_L_R);
        LRear.setTargetPosition((int) -dist_L_R);
        RRear.setTargetPosition((int) dist_L_R);
        Config_Drive_to_RunToPos();
        Wait_for_Drive_Motors_to_Move();
        Config_Drive_to_Manual();
    }
    private void Move_Rotate(double dist_angle) {
        LFront.setTargetPosition((int) -dist_angle);
        RFront.setTargetPosition((int) dist_angle);
        LRear.setTargetPosition((int) -dist_angle);
        RRear.setTargetPosition((int) dist_angle);
        Config_Drive_to_RunToPos();
        Wait_for_Drive_Motors_to_Move();
        Config_Drive_to_Manual();
    }
    private void Move_F_B(double dist_F_B) {
        LFront.setTargetPosition((int) dist_F_B);
        LRear.setTargetPosition((int) dist_F_B);
        RFront.setTargetPosition((int) dist_F_B);
        RRear.setTargetPosition((int) dist_F_B);
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

    /**
     * Autonomous movement
     */
    private void StartMovement () {
        Move_F_B(0.2 * tileSizeForward);
        Move_Rotate(350); //2400 ~ 360 degrees
        if (isTargetVisible("Red Audience Wall")) {
            processTarget();
            startPos = 1;

        } else if (isTargetVisible("Red Rear Wall")) {
            processTarget();
            startPos = 2;

        } else if (isTargetVisible("Blue Audience Wall")) {
            processTarget();
            startPos = 4;

        } else if (isTargetVisible("Blue Rear Wall")) {
            processTarget();
            startPos = 3;

        } else {
            telemetry.addData("No Targets Detected", "Targets are not visible.");
            Move_Rotate(-700);
        }
        StartingCoordinates(startPos);
    }
    private void AutonomousMode () {
        Detection();
        Move_L_R(tileSizeSideways); //will to be different direction dependent on starting position
        Move_F_B(tileSizeForward);

    }

    /**
     * TFOD
     */
    private void Detection () {
        List<Recognition> recognitions;
        recognitions = tfod.getRecognitions();
        // If list is empty, inform the user. Otherwise, go
        // through list and display info for each recognition.
        if (JavaUtil.listLength(recognitions) == 0) {
            telemetry.addData("TFOD", "No items detected.");
            ParkingLocation("no detection");
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


    }
    /**private void MoveForSignal () {
        Detection();
        if (h == 0) {
            Move_L_R(1);
            h++;
            Detection();
        } else {
            Move_L_R(-1);
            h--;
        }
    }
    private void ScanForSignal () {
        while (true) {
            if (MoveToSpot == false) {
                MoveForSignal();
            } else {
                return;
            }
        }
    }*/

    /**
     * Initialization for target locations
     */
    private void ResetValues () {
        robotX = 6;
        robotY = 2;
        startPos = 1;
        targetPosX = 1;
        targetPosY = 1;
    }
    private void StartingPosition() {

        String[] startPosName = new String[] {"F2", "F5", "A5", "A2"};
        while (gamepad1.a == false) {
            if (gamepad1.right_bumper && startPos < 4) {
                startPos += 1;
                sleep(200);
            } else if (gamepad1.right_bumper && startPos >= 4) {
                startPos = 1;
                sleep(200);
            } else if (gamepad1.left_bumper && startPos > 1) {
                startPos -= 1;
                sleep(200);
            } else if (gamepad1.left_bumper && startPos <= 1) {
                startPos = 4;
                sleep(200);
            }
            telemetry.addData(startPosName[startPos - 1], startPos);
            telemetry.update();
        }
        TargetPosition();
        StartingCoordinates(startPos);
    }
    private void StartingCoordinates(int pos) {

        if(pos == 1) {
            robotX = 6;
            robotY = 2;
        } else if (pos == 2) {
            robotX = 6;
            robotY = 5;
        } else if (pos == 3) {
            robotX = 1;
            robotY = 5;
        } else if (pos == 4) {
            robotX = 1;
            robotY = 2;
        }

        telemetry.addData("coordinates: " + robotX + ", " + robotY, 0);
        telemetry.addData("difference " + differenceX + ", " + differenceY, 0);
        telemetry.update();
    }
    private void TargetPosition () {

        String[] targetPosName = new String[] {"A", "B", "C", "D", "E", "F"};
        while (gamepad1.b == false) {
            if (gamepad1.dpad_right && targetPosX < 6) {
                targetPosX += 1;
                sleep(200);
            } else if (gamepad1.dpad_right && targetPosX >= 6) {
                targetPosX = 1;
                sleep(200);
            } else if (gamepad1.dpad_left && targetPosX > 1) {
                targetPosX -= 1;
                sleep(200);
            } else if (gamepad1.dpad_left && targetPosX <= 1) {
                targetPosX = 6;
                sleep(200);
            } else if (gamepad1.dpad_up && targetPosY < 6) {
                targetPosY += 1;
                sleep(200);
            } else if (gamepad1.dpad_up && targetPosY >= 6) {
                targetPosY = 1;
                sleep(200);
            } else if (gamepad1.dpad_down && targetPosY > 1) {
                targetPosY -= 1;
                sleep(200);
            } else if (gamepad1.dpad_down && targetPosY <= 1) {
                targetPosY = 6;
                sleep(200);
            }
            telemetry.addData(targetPosName[targetPosX - 1] + targetPosY, 0);
            telemetry.update();
        }
    }
    private void MoveToTargetPosition() {
        differenceX = targetPosX - robotX;
        differenceY = targetPosY - robotY;
        if (startPos == 1 || startPos == 2) {
            Move_F_B(Math.abs(differenceX * tileSizeForward));
            Move_L_R(differenceY * tileSizeSideways);
        } else if (startPos == 3 || startPos == 4) {
            Move_F_B(Math.abs(differenceX * tileSizeForward));
            Move_L_R(differenceY * tileSizeSideways);
        }

    }

    @Override
    public void runOpMode() {
        vuforiaPOWERPLAY = new VuforiaCurrentGame();
        tfod = new Tfod();

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

        // Initialize Vuforia
        telemetry.addData("Status", "Initializing Vuforia. Please wait...");
        telemetry.update();
        initVuforia();
        tfod.useModelFromAsset(
                "model_new_style.tflite",
                new String[] { "chip", "macaroon", "pizza"});
        tfod.initialize(vuforiaPOWERPLAY, (float) 0.7, true, true);
        tfod.activate();
        tfod.setZoom(1.75, 16 / 9);
        telemetry.addData("DS preview on/off", "3 dots, Camera Stream");
        telemetry.addData(">", "Press Play to start");

        // Activate here for camera preview.
        vuforiaPOWERPLAY.activate();
        telemetry.addData("DS preview on/off", "3 dots, Camera Stream");
        telemetry.addData(">>", "Vuforia initialized, press start to continue...");
        telemetry.update();
        ResetValues();
        StartingPosition();
        ResetValues();
        waitForStart();
        if (opModeIsActive()) {
            //StartMovement();
            //Move_Rotate(350);
            //Move_F_B(-0.1 * tileSizeForward);
            while (opModeIsActive()) {
                // Are the targets visible?
                // (Note we only process first visible target).

                Process_Movement();
                if (isTargetVisible("Red Audience Wall")) {
                    processTarget();

                } else if (isTargetVisible("Red Rear Wall")) {
                    processTarget();

                } else if (isTargetVisible("Blue Audience Wall")) {
                    processTarget();

                } else if (isTargetVisible("Blue Rear Wall")) {
                    processTarget();

                } else {
                    telemetry.addData("No Targets Detected", "Targets are not visible.");

                }
                telemetry.update();
            }
        }
        tfod.deactivate();

        tfod.close();
        // Don't forget to deactivate Vuforia.
        vuforiaPOWERPLAY.deactivate();

        vuforiaPOWERPLAY.close();
    }

    /**
     Vuforia functions
     */
    private void initVuforia() {
        // Initialize using external web camera.
        vuforiaPOWERPLAY.initialize(
                "", // vuforiaLicenseKey
                hardwareMap.get(WebcamName.class, "Webcam 1"), // cameraName
                "", // webcamCalibrationFilename
                false, // useExtendedTracking
                true, // enableCameraMonitoring
                VuforiaLocalizer.Parameters.CameraMonitorFeedback.AXES, // cameraMonitorFeedback
                0, // dx
                0, // dy
                0, // dz
                AxesOrder.XZY, // axesOrder
                90, // firstAngle
                90, // secondAngle
                0, // thirdAngle
                true); // useCompetitionFieldTargetLocations
    }
    private boolean isTargetVisible(String trackableName) {
        boolean isVisible;

        // Get vuforia results for target.
        vuforiaResults = vuforiaPOWERPLAY.track(trackableName);
        // Is this target visible?
        if (vuforiaResults.isVisible) {
            isVisible = true;
        } else {
            isVisible = false;
        }
        return isVisible;
    }
    private void processTarget() {
        // Display the target name.
        telemetry.addData("Target Detected", vuforiaResults.name + " is visible.");
        telemetry.addData("X (in)", Double.parseDouble(JavaUtil.formatNumber(displayValue(vuforiaResults.x, "IN"), 2)));
        telemetry.addData("Y (in)", Double.parseDouble(JavaUtil.formatNumber(displayValue(vuforiaResults.y, "IN"), 2)));
        telemetry.addData("Z (in)", Double.parseDouble(JavaUtil.formatNumber(displayValue(vuforiaResults.z, "IN"), 2)));
        telemetry.addData("Rotation about Z (deg)", Double.parseDouble(JavaUtil.formatNumber(vuforiaResults.zAngle, 2)));
    }
    private double displayValue(float originalValue, String units) {
        double convertedValue;

        // Vuforia returns distances in mm.
        if (units.equals("CM")) {
            convertedValue = originalValue / 10;
        } else if (units.equals("M")) {
            convertedValue = originalValue / 1000;
        } else if (units.equals("IN")) {
            convertedValue = originalValue / 25.4;
        } else if (units.equals("FT")) {
            convertedValue = (originalValue / 25.4) / 12;
        } else {
            convertedValue = originalValue;
        }
        return convertedValue;
    }

    /**
     * Movement Functions
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
            telemetry.addData("Move_F_B",tileSizeForward);
            Move_F_B(tileSizeForward);
        } else if (gamepad1.a) {
            Move_F_B(-1 * tileSizeForward);
        } else if (gamepad1.x) {
            Move_L_R(-1 * tileSizeSideways);
        } else if (gamepad1.b) {
            Move_L_R(tileSizeSideways);
        } else if (gamepad1.right_bumper) {
            Detection();
        } else if (gamepad1.left_bumper) {
            MoveToTargetPosition();
        }
        else {
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