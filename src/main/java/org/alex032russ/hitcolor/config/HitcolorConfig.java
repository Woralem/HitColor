package org.alex032russ.hitcolor.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.NativeImage;

import java.awt.*;

public class HitcolorConfig {
    public static final ForgeConfigSpec spec;
    public static final Config CONFIG;

    private static int processedColor = -1;

    static {
        final Pair<Config, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Config::new);
        spec = specPair.getRight();
        CONFIG = specPair.getLeft();
    }

    public static class Config {
        public final ForgeConfigSpec.ConfigValue<String> colorHex;
        public final ForgeConfigSpec.DoubleValue alpha;

        public Config(ForgeConfigSpec.Builder builder) {
            builder.comment("Hitcolor Configuration").push("general");

            colorHex = builder
                    .comment("Hit color in hex format")
                    .define("color", "#ff0000");

            alpha = builder
                    .comment("Transparency (0-100, where 0 is fully opaque and 100 is fully transparent)")
                    .defineInRange("alpha", 32.8, 0.0, 100.0);

            builder.pop();
        }
    }

    public static int getColor() {
        if (processedColor == -1) {
            updateColor();
        }
        return processedColor;
    }

    public static void updateColor() {
        Color rgbColor;
        try {
            rgbColor = Color.decode(CONFIG.colorHex.get());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            processedColor = -1308622593; // Default color if parsing fails
            return;
        }

        // Format: ARGB (alpha, red, green, blue)
        int alpha = (int)(Math.round(0xFF * (1 - CONFIG.alpha.get() / 100.0)));
        processedColor = rgbColor.getRed() | rgbColor.getGreen() << 8 | rgbColor.getBlue() << 16 | alpha << 24;
    }

    public static void updateHitcolor() {
        // В 1.16.5 обновление происходит через миксин при следующем рендеринге
        // Метод оставлен для совместимости с другими классами
        org.alex032russ.hitcolor.HitcolorMod.needsOverlayTextureUpdate = true;
    }
}