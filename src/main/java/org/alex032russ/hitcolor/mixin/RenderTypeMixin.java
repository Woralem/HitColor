//package org.alex032russ.hitcolor.mixin;
//
//import net.minecraft.client.renderer.RenderState;
//import net.minecraft.client.renderer.RenderType;
//import org.alex032russ.hitcolor.config.HitcolorConfig;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Redirect;
//
//@Mixin(RenderType.class)
//public abstract class RenderTypeMixin {
//
//    // Перехватываем создание типа рендера для эффекта урона
//    @Redirect(method = "getEntityTranslucentCull", at = @At(value = "NEW", target = "net/minecraft/client/renderer/RenderState$TransparencyState"))
//    private static RenderState.TransparencyState modifyHurtEffect(String nameIn, Runnable setupState, Runnable clearState) {
//        // Если это RenderType для эффекта урона, устанавливаем флаг для обновления цвета
//        if (nameIn.equals("translucent_cull") || nameIn.equals("entity_translucent_cull")) {
//            org.alex032russ.hitcolor.HitcolorMod.needsOverlayTextureUpdate = true;
//        }
//        // Возвращаем оригинальный объект TransparencyState
//        return new RenderState.TransparencyState(nameIn, setupState, clearState);
//    }
//}