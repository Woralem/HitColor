package org.alex032russ.hitcolor.config;

import net.minecraft.client.gui.widget.AbstractSlider;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class CustomSlider extends AbstractSlider {
    private final String prefix;
    private final String suffix;
    private final double minValue;
    private final double maxValue;
    private double value;

    public CustomSlider(int x, int y, int width, int height, String prefix, String suffix,
                        double minValue, double maxValue, double defaultValue) {
        super(x, y, width, height, new StringTextComponent(""), defaultValue / (maxValue - minValue));
        this.prefix = prefix;
        this.suffix = suffix;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.value = defaultValue;
        updateMessage();
    }

    @Override
    protected void updateMessage() {
        setMessage(new StringTextComponent(prefix + Math.round(value * 10) / 10.0 + suffix));
    }

    @Override
    protected void applyValue() {
        value = minValue + (maxValue - minValue) * this.value;
        updateMessage();
    }

    public double getValue() {
        return value;
    }
}