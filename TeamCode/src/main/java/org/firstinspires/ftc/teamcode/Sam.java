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
    private VuforiaCurrentGame vuforiaPOWERPLAY;
    private Tfod tfod;

    boolean reset;

    Recognition recognition;
    private DcMotor LFront;
    private DcMotor RFront;
    private DcMotor LRear;
    private DcMotor RRear;
    double speed = .3;
    double stop = 0;
    double bad_speed = speed * -1;
    double Current_Power_Lvl = 0.30;
    static final double THY_MAGIC_NUMBER = 26.67;
    private void GetLabel  () {


    }
    private void Move_Spin(double dist_F_B) {
        LFront.setTargetPosition((int) (dist_F_B * THY_MAGIC_NUMBER));
        LRear.setTargetPosition((int) (dist_F_B * THY_MAGIC_NUMBER));
        RFront.setTargetPosition((int) -(dist_F_B * THY_MAGIC_NUMBER));
        RRear.setTargetPosition((int) -(dist_F_B * THY_MAGIC_NUMBER));

        Config_Drive_to_RunToPos();
        telemetry.addData("LFront", ((DcMotorEx) LFront).getTargetPositionTolerance());
        telemetry.addData("LRear", ((DcMotorEx) LRear).getTargetPositionTolerance());
        telemetry.addData("RFront", ((DcMotorEx) RFront).getTargetPositionTolerance());
        telemetry.addData("RRear", ((DcMotorEx) RRear).getTargetPositionTolerance());
        Wait_for_Drive_Motors_to_Move();
        Config_Drive_to_Manual();
    }
    private void Forward(int dist_F_B) {
        LFront.setTargetPosition(dist_F_B);
        LRear.setTargetPosition(dist_F_B);
        RFront.setTargetPosition(dist_F_B);
        RRear.setTargetPosition(dist_F_B);
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
    private void Movement (){
        if (gamepad1.dpad_up) {
            LFront.setPower(speed);
            RFront.setPower(speed);
            LRear.setPower(speed);
            RRear.setPower(speed);
        } else if (gamepad1.dpad_down) {
            LFront.setPower(bad_speed);
            RFront.setPower(bad_speed);
            LRear.setPower(bad_speed);
            RRear.setPower(bad_speed);
        } else if (gamepad1.right_bumper) {
            LFront.setPower(speed);
            RFront.setPower(bad_speed);
            LRear.setPower(speed);
            RRear.setPower(bad_speed);
        } else if (gamepad1.left_bumper) {
            LFront.setPower(bad_speed);
            RFront.setPower(speed);
            LRear.setPower(bad_speed);
            RRear.setPower(speed);
        }else if (gamepad1.dpad_right) {
            LFront.setPower(speed);
            RFront.setPower(bad_speed);
            LRear.setPower(bad_speed);
            RRear.setPower(speed);
        }else if (gamepad1.dpad_left) {
            LFront.setPower(bad_speed);
            RFront.setPower(speed);
            LRear.setPower(speed);
            RRear.setPower(bad_speed);
        }else if (gamepad1.y) {
            Dtech();
        } else if (gamepad1.x) {
            Forward(668);
        }


        else {
            LFront.setPower(0);
            RFront.setPower(0);
            LRear.setPower(0);
            RRear.setPower(0);
        }
    }
    private void Route1 () {

        Move_Spin(22);
        Forward(48);
        Move_Spin(-22);
        Forward(48);
    }
    private void Route2 () {

        Move_Spin(22);
        Forward(72);
        Move_Spin(-22);
        Forward(96);
        Move_Spin(-22);
        Forward(72);
        Move_Spin(-22);
        Forward(96);
        Move_Spin(-44);

    }
    private void Route3 () {

        Move_Spin(22);
        Forward(24);
        Move_Spin(-22);
        Forward(48);
        Move_Spin(44);
        Forward(48);
        Move_Spin(22);
        Forward(24);
        Move_Spin(22);
    }
    private void Dtech (){
        if (reset == false) {
            if (recognition.getLabel() == "1 Bolt") {
                Route1();


            } else if (recognition.getLabel() == "2 Bulb") {
                Route2();

            } else if (recognition.getLabel() == "3 Panel") {
                Route3();
            }
        } else {
            return;
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
        waitForStart();
        if (opModeIsActive()) {
            // Put run blocks here.
            while (opModeIsActive()) {
                Movement();
                // Put loop blocks here.
                // Get a list of recognitions from TFOD.
                recognitions = tfod.getRecognitions();
                // If list is empty, inform the user. Otherwise, go
                // through list and display info for each recognition.
                if (JavaUtil.listLength(recognitions) == 0) {
                    telemetry.addData("TFOD", "No items detected.");
                    reset = true;
                } else {
                    index = 0;
                    // Iterate through list and call a function to
                    // display info for each recognized object.
                    for (Recognition recognition_item : recognitions) {
                        recognition = recognition_item;
                        reset = false;
                        // Display info.
                        //displayInfo(index);
                        // Increment index.
                        //index = index + 1;
                    }

                }
                telemetry.update();
            }
        }
        // Deactivate TFOD.
        tfod.deactivate();

        vuforiaPOWERPLAY.close();
        tfod.close();
    }


}
