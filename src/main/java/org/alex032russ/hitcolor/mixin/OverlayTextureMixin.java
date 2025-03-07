package org.alex032russ.hitcolor.mixin;

import org.alex032russ.hitcolor.config.HitcolorConfig;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.DynamicTexture;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(OverlayTexture.class)
public class OverlayTextureMixin {
    @Shadow @Final private DynamicTexture texture;

    private static int lastUsedColor = 0;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void onInit(CallbackInfo ci) {
        modifyDamageOverlayColor();
    }

    @Inject(method = "setupOverlayColor", at = @At("RETURN"))
    private void onSetupOverlayColor(CallbackInfo ci) {
        int currentColor = HitcolorConfig.getColor();
        if (lastUsedColor != currentColor) {
            modifyDamageOverlayColor();
            lastUsedColor = currentColor;
        }
    }

    private void modifyDamageOverlayColor() {
        try {
            NativeImage image = texture.getPixels();
            int color = HitcolorConfig.getColor();
            lastUsedColor = color;
            image.setPixelRGBA(0, 3, color);

            texture.upload();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}