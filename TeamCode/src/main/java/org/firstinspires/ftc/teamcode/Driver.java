//package org.firstinspires.ftc.teamcode;
//
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//import com.qualcomm.robotcore.hardware.CRServo;
//import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.hardware.DcMotorEx;
//import com.qualcomm.robotcore.hardware.DcMotorSimple;
//import com.qualcomm.robotcore.hardware.Servo;
//import java.util.List;
//import org.firstinspires.ftc.robotcore.external.JavaUtil;
//import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
//import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
//import org.firstinspires.ftc.robotcore.external.navigation.VuforiaCurrentGame;
//import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
//import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
//import org.firstinspires.ftc.robotcore.external.tfod.Tfod;
//
//@TeleOp(name = "Driver (Blocks to Java)")
//public class Driver extends LinearOpMode {
//
//    private Servo Collectorangle;
//    private DcMotor ArmMotor;
//    private DcMotor LFront;
//    private DcMotor RFront;
//    private DcMotor LRear;
//    private DcMotor RRear;
//    private DcMotor CameraMotor;
//    private VuforiaCurrentGame vuforiaFreightFrenzy;
//    private Tfod tfod;
//    private CRServo Collector;
//    private DcMotor CarouselMotor;
//
//    double Power;
//    List X_Samples;
//    int Left_range_min;
//    List<Recognition> recognitions;
//    List Last_Pressed_Times;
//    int Degree_Interval;
//    List Y_Samples;
//    int Left_range_max;
//    int Nav_Img_Check_Delay;
//    String Barcode_Pos;
//    List Log;
//    double Thy_Magic_Number_Angle;
//    double Thy_Magic_Number_Sideways;
//    double Thy_Magic_Number;
//    int Sample_Cnt;
//    List Deg_Samples;
//    int Mid_range_min;
//    double Current_Power_Lvl;
//    Recognition recognition;
//    int Sample_Interval;
//    int Mid_range_max;
//    int Right_range_min;
//    double Low_Power_Lvl;
//    int High_Power_Lvl;
//    int Right_range_max;
//
//    /**
//     * Describe this function...
//     */
//    private boolean Cooldown_Over_(int Cooldown_Index, double Cooldown_Time__Seconds_) {
//        return getRuntime() - ((Double) JavaUtil.inListGet(Last_Pressed_Times, JavaUtil.AtMode.FROM_START, (Cooldown_Index - 1), false)).doubleValue() > Cooldown_Time__Seconds_;
//    }
//
//    /**
//     * Describe this function...
//     */
//    private void Align_for_Element_Pickup() {
//        Collectorangle.setPosition(0.3);
//        ArmMotor.setTargetPosition(250);
//    }
//
//    /**
//     * Describe this function...
//     */
//    private void Align_for_Capping_Element() {
//        ArmMotor.setTargetPosition(1200);
//        Wait_for_Arm_Motor_to_Move();
//        Collectorangle.setPosition(0.75);
//    }
//
//    /**
//     * Describe this function...
//     */
//    private double Get_Denominator() {
//        return JavaUtil.maxOfList(JavaUtil.createListWith(JavaUtil.sumOfList(JavaUtil.createListWith(Math.abs(-gamepad1.left_stick_y), Math.abs(gamepad1.right_stick_x), Math.abs(gamepad1.left_stick_x))), 1));
//    }
//
//    /**
//     * Describe this function...
//     */
//    private void Turn_L_R(double Deg_L_R) {
//        LFront.setTargetPosition((int) (Deg_L_R * Thy_Magic_Number_Angle));
//        RFront.setTargetPosition((int) -(Deg_L_R * Thy_Magic_Number_Angle));
//        LRear.setTargetPosition((int) (Deg_L_R * Thy_Magic_Number_Angle));
//        RRear.setTargetPosition((int) -(Deg_L_R * Thy_Magic_Number_Angle));
//        Config_Drive_to_RunToPos();
//        Wait_for_Drive_Motors_to_Move();
//        Config_Drive_to_Manual();
//    }
//
//    /**
//     * Describe this function...
//     */
//    private double Get_Power_Value(String Motor, float LeftX, float LeftY, float RightX, float RightY, double Power_Mod) {
//        return Motor.equals("LFront") ? ((-LeftY + LeftX + RightX) / Get_Denominator()) * Power_Mod : (Motor.equals("LRear") ? (((-LeftY - LeftX) + RightX) / Get_Denominator()) * Power_Mod : (Motor.equals("RFront") ? (((-LeftY - LeftX) - RightX) / Get_Denominator()) * Power_Mod : (((-LeftY + LeftX) - RightX) / Get_Denominator()) * Power_Mod));
//    }
//
//    /**
//     * Describe this function...
//     */
//    private void Move_L_R(double Dist_L_R) {
//        LFront.setTargetPosition((int) (Dist_L_R * Thy_Magic_Number_Sideways));
//        RFront.setTargetPosition((int) -(Dist_L_R * Thy_Magic_Number_Sideways));
//        LRear.setTargetPosition((int) -(Dist_L_R * Thy_Magic_Number_Sideways));
//        RRear.setTargetPosition((int) (Dist_L_R * Thy_Magic_Number_Sideways));
//        Config_Drive_to_RunToPos();
//        Wait_for_Drive_Motors_to_Move();
//        Config_Drive_to_Manual();
//    }
//
//    /**
//     * Describe this function...
//     */
//    private void Drive_L_R(
//            // TODO: Enter the type for argument named Drive_Power__L_R_
//            UNKNOWN_TYPE Drive_Power__L_R_) {
//        double Drive_Power__F_B_;
//
//        LFront.setPower(Drive_Power__F_B_);
//        RFront.setPower(-Drive_Power__F_B_);
//        LRear.setPower(-Drive_Power__F_B_);
//        RRear.setPower(Drive_Power__F_B_);
//    }
//
//    /**
//     * Describe this function...
//     */
//    private void Move_F_B(double Dist_F_B) {
//        LFront.setTargetPosition((int) (Dist_F_B * Thy_Magic_Number));
//        LRear.setTargetPosition((int) (Dist_F_B * Thy_Magic_Number));
//        RFront.setTargetPosition((int) (Dist_F_B * Thy_Magic_Number));
//        RRear.setTargetPosition((int) (Dist_F_B * Thy_Magic_Number));
//        Config_Drive_to_RunToPos();
//        Wait_for_Drive_Motors_to_Move();
//        Config_Drive_to_Manual();
//    }
//
//    /**
//     * Describe this function...
//     */
//    private void Drive_F_B(double Drive_Power__F_B_) {
//        LFront.setPower(Drive_Power__F_B_);
//        RFront.setPower(Drive_Power__F_B_);
//        LRear.setPower(Drive_Power__F_B_);
//        RRear.setPower(Drive_Power__F_B_);
//    }
//
//    /**
//     * Describe this function...
//     */
//    private void Wait_for_Camera_Motor_to_Move() {
//        while (!Number_Within_Range_(CameraMotor.getCurrentPosition(), CameraMotor.getTargetPosition() - ((DcMotorEx) CameraMotor).getTargetPositionTolerance(), CameraMotor.getTargetPosition() + ((DcMotorEx) CameraMotor).getTargetPositionTolerance())) {
//            Update_Telemetry();
//            if (Is_opmode_stopped_()) {
//                break;
//            }
//        }
//    }
//
//    /**
//     * Describe this function...
//     */
//    private void Wait_for_Drive_Motors_to_Move() {
//        while (!(Number_Within_Range_(LFront.getCurrentPosition(), LFront.getTargetPosition() - ((DcMotorEx) LFront).getTargetPositionTolerance(), LFront.getTargetPosition() + ((DcMotorEx) LFront).getTargetPositionTolerance()) && Number_Within_Range_(RFront.getCurrentPosition(), RFront.getTargetPosition() - ((DcMotorEx) RFront).getTargetPositionTolerance(), RFront.getTargetPosition() + ((DcMotorEx) RFront).getTargetPositionTolerance()) && Number_Within_Range_(LRear.getCurrentPosition(), LRear.getTargetPosition() - ((DcMotorEx) LRear).getTargetPositionTolerance(), LRear.getTargetPosition() + ((DcMotorEx) RRear).getTargetPositionTolerance()) && Number_Within_Range_(RRear.getCurrentPosition(), RRear.getTargetPosition() - ((DcMotorEx) RRear).getTargetPositionTolerance(), RRear.getTargetPosition() + ((DcMotorEx) RRear).getTargetPositionTolerance()))) {
//            Update_Telemetry();
//            if (Is_opmode_stopped_()) {
//                break;
//            }
//        }
//    }
//
//    /**
//     * Describe this function...
//     */
//    private void Config_Motors() {
//        CameraMotor.setTargetPosition((int) 0.5);
//        CameraMotor.setPower(0.55);
//        CameraMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        CameraMotor.setDirection(DcMotorSimple.Direction.FORWARD);
//        CameraMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        ((DcMotorEx) CameraMotor).setTargetPositionTolerance(0);
//        Collectorangle.setDirection(Servo.Direction.REVERSE);
//        Collectorangle.setPosition(-1);
//        ArmMotor.setTargetPosition(0);
//        ((DcMotorEx) ArmMotor).setTargetPositionTolerance(7);
//        ArmMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        ArmMotor.setPower(0.5);
//        Config_Drive();
//    }
//
//    /**
//     * Describe this function...
//     */
//    private void Config_Drive() {
//        Config_Drive_to_Manual();
//        LFront.setDirection(DcMotorSimple.Direction.REVERSE);
//        LRear.setDirection(DcMotorSimple.Direction.FORWARD);
//        RFront.setDirection(DcMotorSimple.Direction.FORWARD);
//        RRear.setDirection(DcMotorSimple.Direction.FORWARD);
//        ((DcMotorEx) LFront).setTargetPositionTolerance(10);
//        ((DcMotorEx) LRear).setTargetPositionTolerance(10);
//        ((DcMotorEx) RFront).setTargetPositionTolerance(10);
//        ((DcMotorEx) RRear).setTargetPositionTolerance(10);
//        Low_Power_Lvl = 0.5;
//        High_Power_Lvl = 1;
//        Current_Power_Lvl = Low_Power_Lvl;
//        Thy_Magic_Number = 26.67;
//        Thy_Magic_Number_Sideways = 28.125;
//        Thy_Magic_Number_Angle = 6.53;
//    }
//
//    /**
//     * Describe this function...
//     */
//    private void Config_Drive_to_Manual() {
//        LFront.setPower(0);
//        RFront.setPower(0);
//        RRear.setPower(0);
//        LRear.setPower(0);
//        LFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        LRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        RFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        RRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//    }
//
//    /**
//     * Describe this function...
//     */
//    private void Configure_Hardware_Software() {
//        telemetry.addData("Initializing", "Configuring");
//        telemetry.update();
//        Config_Motors();
//        Barcode_Reader_Settings();
//        Pos_Finder_Settings();
//        Log = JavaUtil.createListWith();
//        Last_Pressed_Times = JavaUtil.createListWithItemRepeated(-5, 4);
//    }
//
//    /**
//     * Describe this function...
//     */
//    private double Get_Mid(float X_1_, float X_2_) {
//        return JavaUtil.averageOfList(JavaUtil.createListWith(X_1_, X_2_));
//    }
//
//    /**
//     * Describe this function...
//     */
//    private void Update_Cooldown(int Update_Index) {
//        JavaUtil.inListSet(Last_Pressed_Times, JavaUtil.AtMode.FROM_START, (Update_Index - 1), false, getRuntime());
//    }
//
//    /**
//     * Describe this function...
//     */
//    private void Rotate_Servo_Until_Finds_Nav_Image() {
//        // Check if motor has made a 360 yet.
//        if (!(CameraMotor.getTargetPosition() > 359 - Degree_Interval)) {
//            Move_Camera_Motor(CameraMotor.getTargetPosition() + Degree_Interval);
//            if (!Nav_Img_is_Visible_()) {
//                // Check if opmode is stopped.
//                if (!Is_opmode_stopped_()) {
//                    Rotate_Servo_Until_Finds_Nav_Image();
//                }
//            }
//        } else {
//            Move_Camera_Motor(0);
//        }
//    }
//
//    /**
//     * Describe this function...
//     */
//    private void Pos_Finder_Settings() {
//        Power = 0.5;
//        Degree_Interval = 2;
//        Sample_Cnt = 100;
//        Sample_Interval = 10;
//        Nav_Img_Check_Delay = 1500;
//    }
//
//    /**
//     * Describe this function...
//     */
//    private void Clear_Samples() {
//        X_Samples = JavaUtil.createListWith();
//        Y_Samples = JavaUtil.createListWith();
//        Deg_Samples = JavaUtil.createListWith();
//    }
//
//    /**
//     * Describe this function...
//     */
//    private void Barcode_Reader_Settings() {
//        Left_range_min = 0;
//        Left_range_max = 215;
//        Mid_range_min = 216;
//        Mid_range_max = 430;
//        Right_range_min = 431;
//        Right_range_max = 650;
//    }
//
//    /**
//     * Describe this function...
//     */
//    private void Config_Drive_to_RunToPos() {
//        LFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        RRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        RFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        LRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        LRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        RFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        LFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        RRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        LFront.setPower(0.1);
//        RFront.setPower(0.1);
//        RRear.setPower(0.1);
//        LRear.setPower(0.1);
//    }
//
//    /**
//     * Checks if opmode is stopped, returns true or false
//     */
//    private boolean Is_opmode_stopped_() {
//        return !opModeIsActive() || isStopRequested();
//    }
//
//    /**
//     * Describe this function...
//     */
//    private boolean Nav_Img_is_Visible_() {
//        sleep(Nav_Img_Check_Delay);
//        return vuforiaFreightFrenzy.track("Red Storage").isVisible || vuforiaFreightFrenzy.track("Red Alliance Wall").isVisible || vuforiaFreightFrenzy.track("Blue Alliance Wall").isVisible || vuforiaFreightFrenzy.track("Blue Storage").isVisible;
//    }
//
//    /**
//     * Describe this function...
//     */
//    private void Find_Initial_Barcode() {
//        while (!!Find_Barcode().equals("Did not find barcode")) {
//            Barcode_Pos = Find_Barcode();
//        }
//    }
//
//    /**
//     * This function is executed when this Op Mode is selected from the Driver Station.
//     */
//    @Override
//    public void runOpMode() {
//        Collectorangle = hardwareMap.get(Servo.class, "Collector angle");
//        ArmMotor = hardwareMap.get(DcMotor.class, "ArmMotor");
//        LFront = hardwareMap.get(DcMotor.class, "LFront");
//        RFront = hardwareMap.get(DcMotor.class, "RFront");
//        LRear = hardwareMap.get(DcMotor.class, "LRear");
//        RRear = hardwareMap.get(DcMotor.class, "RRear");
//        CameraMotor = hardwareMap.get(DcMotor.class, "CameraMotor");
//        vuforiaFreightFrenzy = new VuforiaCurrentGame();
//        tfod = new Tfod();
//        Collector = hardwareMap.get(CRServo.class, "Collector");
//        CarouselMotor = hardwareMap.get(DcMotor.class, "CarouselMotor");
//
//        Initialization();
//        waitForStart();
//        if (opModeIsActive()) {
//            When_Run();
//            while (opModeIsActive()) {
//                While_running();
//            }
//            When_stopped();
//        }
//
//        vuforiaFreightFrenzy.close();
//        tfod.close();
//    }
//
//    /**
//     * Describe this function...
//     */
//    private boolean Number_Within_Range_(double Num, int Min, int Max) {
//        return Num == Math.min(Math.max(Num, Min), Max);
//    }
//
//    /**
//     * Describe this function...
//     */
//    private void Add_to_Log(String To_Add) {
//        Log.add(To_Add + "|/|" + getRuntime());
//    }
//
//    /**
//     * Describe this function...
//     */
//    private double Ticks_to_Deg(double Degree_In) {
//        return Degree_In * (288 / 360);
//    }
//
//    /**
//     * Describe this function...
//     */
//    private void Disable_Object_Detection() {
//        // Deactivate TFOD
//        tfod.deactivate();
//        // Deactivate Vuforia Nav Images
//        vuforiaFreightFrenzy.deactivate();
//    }
//
//    /**
//     * Describe this function...
//     */
//    private void Update_Telemetry() {
//        // TODO: Enter the type for variable named Found_Start_Pos
//        UNKNOWN_TYPE Found_Start_Pos;
//
//        Process_recognisions_();
//        telemetry.addData("Found Pos", "" + Found_Start_Pos);
//        telemetry.addData("Initial Barcode", "" + Barcode_Pos);
//        telemetry.addData("Current Barcode", "" + Find_Barcode());
//        telemetry.addData("Arm Motor Pos", ArmMotor.getCurrentPosition());
//        telemetry.addData("Collector Angle", Collectorangle.getPosition());
//        Insert_Log();
//        telemetry.update();
//    }
//
//    /**
//     * Describe this function...
//     */
//    private void Insert_Log() {
//        String Log_Item;
//
//        telemetry.addData(" ", " ");
//        telemetry.addData("Log", " ");
//        // TODO: Enter the type for variable named Log_Item
//        for (UNKNOWN_TYPE Log_Item_item : Log) {
//            Log_Item = Log_Item_item;
//            if (-(((Double) JavaUtil.inListGet(JavaUtil.makeListFromText(Log_Item, "|/|"), JavaUtil.AtMode.LAST, 0, false)).doubleValue() - getRuntime()) < 5) {
//                telemetry.addData(">", JavaUtil.inListGet(JavaUtil.makeListFromText(Log_Item, "|/|"), JavaUtil.AtMode.FIRST, 0, false) + "(" + JavaUtil.formatNumber(5 - -(((Double) JavaUtil.inListGet(JavaUtil.makeListFromText(Log_Item, "|/|"), JavaUtil.AtMode.LAST, 0, false)).doubleValue() - getRuntime()), 1) + ")");
//            } else {
//                JavaUtil.inListGet(Log, JavaUtil.AtMode.FROM_START, ((Log.indexOf(Log_Item) + 1) - 1), true);
//            }
//        }
//    }
//
//    /**
//     * Runs when the opmode is initialized.
//     */
//    private void Initialization() {
//        Initialize_Vuforia();
//        Initialize_TFOD();
//        Configure_Hardware_Software();
//        telemetry.addData("Initialized", "Press Play to start");
//        telemetry.update();
//    }
//
//    /**
//     * Runs when the opmode is started once.
//     */
//    private void When_Run() {
//        Update_Telemetry();
//    }
//
//    /**
//     * This runs while its running.
//     */
//    private void While_running() {
//        Process_Inputs();
//        Update_Telemetry();
//    }
//
//    /**
//     * When the opmode is stopped
//     */
//    private void When_stopped() {
//        Disable_Object_Detection();
//    }
//
//    /**
//     * Describe this function...
//     */
//    private void Move_Camera_Motor(int Deg) {
//        CameraMotor.setTargetPosition((int) Ticks_to_Deg(Deg));
//        CameraMotor.setPower(Power);
//        Wait_for_Camera_Motor_to_Move();
//        CameraMotor.setPower(0);
//    }
//
//    /**
//     * Describe this function...
//     */
//    private void Process_Inputs() {
//        int Trigger_Toggle;
//
//        if (gamepad1.left_stick_button && Cooldown_Over_(4, 0.9)) {
//            Align_for_Element_Pickup();
//            Update_Cooldown(3);
//        }
//        if (gamepad1.right_stick_button && Cooldown_Over_(3, 0.9)) {
//            Align_for_Capping_Element();
//            Update_Cooldown(4);
//        }
//        if (gamepad1.left_bumper) {
//            Current_Power_Lvl = Low_Power_Lvl;
//        } else if (gamepad1.right_bumper) {
//            Current_Power_Lvl = High_Power_Lvl;
//        }
//        if (gamepad1.y) {
//            ArmMotor.setTargetPosition(ArmMotor.getTargetPosition() + 50);
//        } else if (gamepad1.a) {
//            ArmMotor.setTargetPosition(ArmMotor.getTargetPosition() - 50);
//        }
//        if (gamepad1.dpad_up) {
//            Collectorangle.setPosition(Collectorangle.getPosition() - 0.05);
//        } else if (gamepad1.dpad_down) {
//            Collectorangle.setPosition(Collectorangle.getPosition() + 0.05);
//        }
//        if (Cooldown_Over_(2, 0.5)) {
//            if (gamepad1.right_trigger > 0.9 && Trigger_Toggle == 0) {
//                Trigger_Toggle = 1;
//            } else if (gamepad1.left_trigger > 0.9 && Trigger_Toggle == 0) {
//                Trigger_Toggle = -1;
//            } else if ((gamepad1.right_trigger > 0.9 || gamepad1.left_trigger > 0.9) && Trigger_Toggle != 0) {
//                Trigger_Toggle = 0;
//            } else {
//                if (gamepad1.right_trigger > 0.02 && Trigger_Toggle == 0) {
//                    Collector.setPower(gamepad1.right_trigger);
//                } else if (gamepad1.left_trigger > 0.02 && Trigger_Toggle == 0) {
//                    Collector.setPower(gamepad1.left_trigger);
//                }
//            }
//            Update_Cooldown(2);
//        }
//        Collector.setPower(Trigger_Toggle);
//        if (gamepad1.x) {
//            // Blue Carousel
//            CarouselMotor.setPower(-1);
//        } else if (gamepad1.b) {
//            // Red Carousel
//            CarouselMotor.setPower(1);
//        } else {
//            CarouselMotor.setPower(0);
//        }
//        Process_Movement();
//    }
//
//    /**
//     * Describe this function...
//     */
//    private void Initialize_Vuforia() {
//        telemetry.addData("Initializing", "Vuforia");
//        telemetry.update();
//        vuforiaFreightFrenzy.initialize(
//                "", // vuforiaLicenseKey
//                hardwareMap.get(WebcamName.class, "Webcam 1"), // cameraName
//                "", // webcamCalibrationFilename
//                true, // useExtendedTracking
//                true, // enableCameraMonitoring
//                VuforiaLocalizer.Parameters.CameraMonitorFeedback.AXES, // cameraMonitorFeedback
//                0, // dx
//                0, // dy
//                0, // dz
//                AxesOrder.XYZ, // axesOrder
//                90, // firstAngle
//                90, // secondAngle
//                0, // thirdAngle
//                true); // useCompetitionFieldTargetLocations
//        vuforiaFreightFrenzy.activate();
//    }
//
//    /**
//     * Describe this function...
//     */
//    private void Process_Movement() {
//        LFront.setPower(Get_Power_Value("LFront", gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x, gamepad1.right_stick_y, Current_Power_Lvl));
//        LRear.setPower(Get_Power_Value("LRear", gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x, gamepad1.right_stick_y, Current_Power_Lvl));
//        RFront.setPower(Get_Power_Value("RFront", gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x, gamepad1.right_stick_y, Current_Power_Lvl));
//        RRear.setPower(Get_Power_Value("RRear", gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x, gamepad1.right_stick_y, Current_Power_Lvl));
//    }
//
//    /**
//     * Describe this function...
//     */
//    private void Drive_F_B2(
//            // TODO: Enter the type for argument named Drive_Power__F_B_
//            UNKNOWN_TYPE Drive_Power__F_B_) {
//        double Drive_Power;
//
//        LFront.setPower(Drive_Power);
//        RFront.setPower(Drive_Power);
//        LRear.setPower(Drive_Power);
//        RRear.setPower(Drive_Power);
//    }
//
//    /**
//     * Describe this function...
//     */
//    private void Initialize_TFOD() {
//        telemetry.addData("Initializing", "TFOD");
//        // Set isModelTensorFlow2 to true if you used a TensorFlow 2 tool,
//        // such as ftc-ml, to create the model. Set isModelQuantized to
//        // true if the model is quantized. Models created with ftc-ml are
//        // quantized. Set inputSize to the image size corresponding to
//        // the model. If your model is based on SSD MobileNet v2 320x320,
//        // the image size is 300 (srsly!). If your model is based on
//        // SSD MobileNet V2 FPNLite 320x320, the image size is 320.
//        // If your model is based on SSD MobileNet V1 FPN 640x640 or
//        // SSD MobileNet V2 FPNLite 640x640, the image size is 640.
//        tfod.useModelFromFile("CustomScoringElement.tflite", JavaUtil.createListWith("Custom"), true, true, 300);
//        tfod.initialize(vuforiaFreightFrenzy, (float) 0.9, true, true, 1, 2, 10, 10, 30, (float) 0.2, 16, (float) 0.75, (float) 0.6);
//        tfod.setZoom(1, 16 / 9);
//        tfod.activate();
//        telemetry.update();
//    }
//
//    /**
//     * Display info (using telemetry) for a recognized object.
//     */
//    private void displayInfo(int i) {
//        // Display label info.
//        // Display the label and index number for the recognition.
//        telemetry.addData("Recognition " + i, recognition.getLabel());
//        // Display confidence info.
//        // Display the location of the top left corner
//        // of the detection boundary for the recognition
//        telemetry.addData("Confidence" + i, Double.parseDouble(JavaUtil.formatNumber(recognition.getConfidence(), 3)) * 100 + "%");
//        // Display upper corner info.
//        // Display the location of the top left corner
//        // of the detection boundary for the recognition
//        telemetry.addData("Left, Top " + i, Math.round(recognition.getLeft()) + ", " + Math.round(recognition.getTop()));
//        // Display lower corner info.
//        // Display the location of the bottom right corner
//        // of the detection boundary for the recognition
//        telemetry.addData("Right, Bottom " + i, Math.round(recognition.getRight()) + ", " + Math.round(recognition.getBottom()));
//    }
//
//    /**
//     * Describe this function...
//     */
//    private void Power_Collector_For(long Time_to_Run,
//                                     // TODO: Enter the type for argument named Pick_Place
//                                     UNKNOWN_TYPE Pick_Place) {
//        if (Pick_Place == "Pick") {
//            Collector.setPower(-1);
//            sleep(Time_to_Run);
//            Collector.setPower(0);
//        } else if (Pick_Place == "Place") {
//            Collector.setPower(1);
//            sleep(Time_to_Run);
//            Collector.setPower(0);
//        } else {
//            Add_to_Log("Collector Function did not find an expected value.");
//        }
//    }
//
//    /**
//     * Describe this function...
//     */
//    private void Wait_for_Arm_Motor_to_Move() {
//        while (!Number_Within_Range_(ArmMotor.getCurrentPosition(), ArmMotor.getTargetPosition() - ((DcMotorEx) ArmMotor).getTargetPositionTolerance(), ArmMotor.getTargetPosition() + ((DcMotorEx) ArmMotor).getTargetPositionTolerance())) {
//            Update_Telemetry();
//            if (Is_opmode_stopped_()) {
//                break;
//            }
//        }
//    }
//
//    /**
//     * Describe this function...
//     */
//    private void Process_recognisions_() {
//        int index;
//
//        // Get a list of recognitions from TFOD.
//        recognitions = tfod.getRecognitions();
//        // If list is empty, inform the user. Otherwise, go
//        // through list and display info for each recognition.
//        if (JavaUtil.listLength(recognitions) == 0) {
//            telemetry.addData("TFOD", "No items Detected");
//        } else {
//            index = 0;
//            // Iterate through list and call a function to
//            // display info for each recognized object.
//            for (Recognition recognition_item : recognitions) {
//                recognition = recognition_item;
//                // Display info.
//                displayInfo(index);
//                // Increment index.
//                index = index + 1;
//            }
//        }
//    }
//
//    /**
//     * Describe this function...
//     */
//    private String Find_Start_Location() {
//        Pos_Finder_Settings();
//        Move_Camera_Motor(180);
//        sleep(Nav_Img_Check_Delay);
//        if (vuforiaFreightFrenzy.track("Red Alliance Wall").isVisible) {
//            Move_Camera_Motor(0);
//            if (true) {
//                return "Red-A";
//            }
//        } else if (vuforiaFreightFrenzy.track("Blue Alliance Wall").isVisible) {
//            Move_Camera_Motor(0);
//            if (true) {
//                return "Blue-A";
//            }
//        } else {
//            Add_to_Log("Did Not find Image Behind");
//            Move_Camera_Motor(65);
//            sleep(Nav_Img_Check_Delay);
//            if (vuforiaFreightFrenzy.track("Blue Storage").isVisible) {
//                Move_Camera_Motor(0);
//                if (true) {
//                    return "Blue-B";
//                }
//            } else {
//                Move_Camera_Motor(300);
//                sleep(Nav_Img_Check_Delay);
//                if (vuforiaFreightFrenzy.track("Red Storage").isVisible) {
//                    Move_Camera_Motor(0);
//                    if (true) {
//                        return "Red-B";
//                    }
//                } else {
//                    Move_Camera_Motor(0);
//                    Add_to_Log("No Images Found");
//                    if (true) {
//                        return "No Images Found";
//                    }
//                }
//            }
//        }
//    }
//
//    /**
//     * Describe this function...
//     */
//    private String Find_Barcode() {
//        Barcode_Reader_Settings();
//        // Get a list of recognitions from TFOD.
//        recognitions = tfod.getRecognitions();
//        // Iterate through list and call a function to
//        // display info for each recognized object.
//        for (Recognition recognition_item2 : recognitions) {
//            recognition = recognition_item2;
//            // Check if duk
//            if (recognition.getLabel().equals("Custom")) {
//                if (Number_Within_Range_(Get_Mid(recognition.getLeft(), recognition.getRight()), Left_range_min, Left_range_max)) {
//                    if (true) {
//                        return "Left";
//                    }
//                } else if (Number_Within_Range_(Get_Mid(recognition.getLeft(), recognition.getRight()), Mid_range_min, Mid_range_max)) {
//                    if (true) {
//                        return "Middle";
//                    }
//                } else if (Number_Within_Range_(Get_Mid(recognition.getLeft(), recognition.getRight()), Right_range_min, Right_range_max)) {
//                    if (true) {
//                        return "Right";
//                    }
//                }
//            }
//        }
//        return "Did not find barcode";
//    }
//
//    /**
//     * Describe this function...
//     */
//    private List Leveled_Tracking_Results() {
//        Clear_Samples();
//        for (int count = 0; count < Sample_Cnt; count++) {
//            X_Samples.add(vuforiaFreightFrenzy.track("Blue Storage").x);
//            Y_Samples.add(vuforiaFreightFrenzy.track("Blue Storage").y);
//            Deg_Samples.add(vuforiaFreightFrenzy.track("Blue Storage").zAngle);
//            sleep(Sample_Interval);
//        }
//        return JavaUtil.createListWith(Math.round(JavaUtil.averageOfList(X_Samples) / 25.4), Math.round(JavaUtil.averageOfList(Y_Samples) / 25.4), Math.round(JavaUtil.averageOfList(Deg_Samples) / 25.4));
//    }
//
//    /**
//     * Describe this function...
//     */
//    private List Find_Pos() {
//        Pos_Finder_Settings();
//        Rotate_Servo_Until_Finds_Nav_Image();
//        if (true) {
//            return Leveled_Tracking_Results();
//        }
//        Move_Camera_Motor(0);
//    }
//}