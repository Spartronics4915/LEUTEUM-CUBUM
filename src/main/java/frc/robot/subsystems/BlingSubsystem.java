package frc.robot.subsystems;

import com.ctre.phoenix6.CANBus;
import com.ctre.phoenix6.controls.ColorFlowAnimation;
import com.ctre.phoenix6.controls.EmptyAnimation;
import com.ctre.phoenix6.controls.FireAnimation;
import com.ctre.phoenix6.controls.LarsonAnimation;
import com.ctre.phoenix6.controls.RainbowAnimation;
import com.ctre.phoenix6.controls.RgbFadeAnimation;
import com.ctre.phoenix6.controls.SingleFadeAnimation;
import com.ctre.phoenix6.controls.SolidColor;
import com.ctre.phoenix6.controls.StrobeAnimation;
import com.ctre.phoenix6.hardware.CANdle;
import com.ctre.phoenix6.signals.RGBWColor;
import com.ctre.phoenix6.signals.StatusLedWhenActiveValue;
import com.ctre.phoenix6.signals.StripTypeValue;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class BlingSubsystem extends SubsystemBase{
    private static final RGBWColor green = new RGBWColor(0, 255, 0, 0);
    private static final RGBWColor red = new RGBWColor(255, 0, 0, 0);
    private static final RGBWColor blue = new RGBWColor(0, 0, 255, 0);
    private static final RGBWColor purple = new RGBWColor(86, 26, 143, 0);
    private static final RGBWColor yellow = new RGBWColor(77, 42, 3, 0);
    private static final RGBWColor white = new RGBWColor(Color.kWhite).scaleBrightness(1);

    private static final int slot0StartIdx = 0;
    private static final int slot0EndIdx = 70;

    private final CANdle candle = new CANdle(0, CANBus.roboRIO());

    private enum AnimationType {
        None,
        ColorFlow,
        Fire,
        Larson,
        Rainbow,
        RgbFade,
        SingleFade,
        Strobe,
        Twinkle,
        TwinkleOff,
        SolidBlue,
        SolidGreen,
        SolidYellow,
        SolidRed,
        SolidWhite,
        SolidPurple,
    }

    private AnimationType anim0State = AnimationType.None;

    private final SendableChooser<AnimationType> anim0Chooser = new SendableChooser<AnimationType>();

    public BlingSubsystem() {
        var cfg = CANdleConfiguration();

        cfg.LED.StripType = StripTypeValue.RGB;
        cfg.LED.BrightnessScalar = 1;

        cfg.CANdleFeatures.StatusLedWhenActive = StatusLedWhenActiveValue.Disabled;

        candle.getConfiguration().apply(cfg);

        for (int i = 0; i < 8; ++i) {
            candle.setControl(new EmptyAnimation(i));
        }

        anim0Chooser.setDefaultOption("Color Flow", AnimationType.ColorFlow);
        anim0Chooser.addOption("Rainbow", AnimationType.Rainbow);
        anim0Chooser.addOption("SolidPurple", AnimationType.SolidPurple);
        anim0Chooser.addOption("SolidWhite", AnimationType.SolidWhite);
        anim0Chooser.addOption("SolidYellow", AnimationType.SolidYellow);
        anim0Chooser.addOption("SolidRed", AnimationType.SolidRed);
        anim0Chooser.addOption("SolidBlue", AnimationType.SolidBlue);
        anim0Chooser.addOption("SolidGreen", AnimationType.SolidGreen);
        anim0Chooser.addOption("Twinkle", AnimationType.Twinkle);
        anim0Chooser.addOption("Twinkle Off", AnimationType.TwinkleOff);
        anim0Chooser.addOption("Fire", AnimationType.Fire);
        anim0Chooser.addOption("Larson", AnimationType.Larson);
        anim0Chooser.addOption("RgbFade", AnimationType.RgbFade);
        anim0Chooser.addOption("SingleFade", AnimationType.SingleFade);
        anim0Chooser.addOption("Strobe", AnimationType.Strobe);

        SmartDashboard.putData("Animation 0", anim0Chooser);
    }

    public void colorFlowAnimation() {
        candle.setControl(
            new ColorFlowAnimation(slot0StartIdx, slot0EndIdx).withSlot(0)
                .withColor(yellow)
        );
    }

    public void fireAnimation() {
        candle.setControl(
            new FireAnimation(slot0StartIdx, slot0EndIdx).withSlot(0)
                //.withDirection(AnimationDirectionValue.Backward)
                .withCooling(0.6)
                .withSparking(0.4)
        );
    }

    public void larsonAnimation() {
        candle.setControl(
            new LarsonAnimation(slot0StartIdx, slot0EndIdx).withSlot(0)
            .withColor(yellow)
        );
    }

    public void rainbowAnimation() {
        candle.setControl(
            new RainbowAnimation(slot0StartIdx, slot0EndIdx).withSlot(0)
        );
    }

    public void rgbFadeAnimation() {
        candle.setControl(
            new RgbFadeAnimation(slot0StartIdx, slot0EndIdx).withSlot(0)
        );
    }

    public void singleFadeAnimation() {
        candle.setControl(
            new SingleFadeAnimation(slot0StartIdx, slot0EndIdx).withSlot(0)
                .withColor(yellow)
        );
    }

    public void strobeAnimation() {
        candle.setControl(
            new StrobeAnimation(slot0StartIdx, slot0EndIdx).withSlot(0)
                .withColor(yellow)
        );
    }

    public void positiveAnimation() {
        candle.setControl(new SolidColor(0, 70).withColor(green));
    }

    public void negativeAnimation() {
        candle.setControl(new SolidColor(0, 70).withColor(red));
    }

    public void spartronicsAnimation() {
        candle.setControl(new SolidColor(0, 9).withColor(blue));
        candle.setControl(new SolidColor(10, 19).withColor(yellow));
        candle.setControl(new SolidColor(20, 29).withColor(blue));
        candle.setControl(new SolidColor(30, 39).withColor(yellow));
        candle.setControl(new SolidColor(40, 49).withColor(blue));
        candle.setControl(new SolidColor(50, 59).withColor(yellow));
        candle.setControl(new SolidColor(60, 70).withColor(blue));
    }

    @Override
    public void periodic() {
        final var anim0Selection = anim0Chooser.getSelected();
        if (anim0State != anim0Selection) {
            anim0State = anim0Selection;

            candle.setControl(new EmptyAnimation(slot0StartIdx).withSot(0));
        }
    }
}
