package org.firstinspires.ftc.teamcode;

        import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
        import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
        import com.qualcomm.robotcore.hardware.Gamepad;

@TeleOp(name = "testgamepad (Blocks to Java)")
public class testgamepad extends LinearOpMode {

    Gamepad.LedEffect ledEffect;

    /**
     * This OpMode shows how to use Gamepad.setLedColor and Gamepad.runLedEffect
     * to change the color of the LED on supported gamepads.
     */
    @Override
    public void runOpMode() {
        boolean lastX;
        boolean lastB;
        boolean X;
        boolean B;
        telemetry.addData("Hi",0);
        telemetry.update();
        // First we create an LedEffect.
        createLedEffect();
        waitForStart();
        //if (opModeIsActive()) {
            // We'll change the LED when the user presses X or B.
            // Initialize variables for those buttons.
        lastX = gamepad1.x;
        lastB = gamepad1.b;

        while (opModeIsActive()) {
            // Get the current state of buttons X and B.
            X = gamepad1.x;
            B = gamepad1.b;
            telemetry.addData("Hi2",0);
            telemetry.update();
            if (!lastX && X)
            {
                    // If the user presses X, set the LED color to blue
                    // for 1 second.
                    telemetry.addData("x", "Configuring");
                    telemetry.update();
                } else if (B) {
                    // If the user presses B, run the LedEffect that we
                    // create earlier.
                    telemetry.addData("b", "Configuring");
                    telemetry.update();
                }
                lastX = X;
                lastB = B;
            }
        }
   // }

    /**
     * createLedEffect uses an LedEffect.Builder to create
     * an LedEffect that alternates between red and green.
     * setRepeating is used to make the LedEffect keep going.
     */
    private void createLedEffect() {
        Gamepad.LedEffect.Builder ledEffectBuilder;

        ledEffectBuilder = new Gamepad.LedEffect.Builder();
        ledEffectBuilder.addStep(1, 0, 0, 500);
        ledEffectBuilder.addStep(0, 1, 0, 500);
        ledEffectBuilder.setRepeating(true);
        ledEffect = ledEffectBuilder.build();
    }
}