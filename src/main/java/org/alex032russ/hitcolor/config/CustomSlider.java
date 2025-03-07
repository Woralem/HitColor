package org.alex032russ.hitcolor.config;

import net.minecraft.client.gui.widget.AbstractSlider;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.StringTextComponent;

public class CustomSlider extends AbstractSlider {
    private final String prefix;
    private final String suffix;
    private final double minValue;
    private final double maxValue;
    private double sliderValue;

    public CustomSlider(int x, int y, int width, int height, String prefix, String suffix,
                        double minValue, double maxValue, double defaultValue) {
        super(x, y, width, height, new StringTextComponent(""), 0.0);

        this.prefix = prefix;
        this.suffix = suffix;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.sliderValue = MathHelper.clamp(defaultValue, minValue, maxValue);
        this.value = (sliderValue - minValue) / (maxValue - minValue);

        updateMessage();
    }

    @Override
    protected void updateMessage() {
        String formattedValue = String.format("%.1f", sliderValue);
        setMessage(new StringTextComponent(prefix + formattedValue + suffix));
    }

    @Override
    protected void applyValue() {
        this.sliderValue = minValue + (maxValue - minValue) * this.value;
        this.sliderValue = Math.round(this.sliderValue * 10) / 10.0;

        updateMessage();
    }

    public double getValue() {
        return this.sliderValue;
    }
    public void setValue(double newValue) {
        this.sliderValue = MathHelper.clamp(newValue, minValue, maxValue);
        this.value = (sliderValue - minValue) / (maxValue - minValue);
        updateMessage();
    }
}