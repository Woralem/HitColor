package org.alex032russ.hitcolor;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(modid = HitcolorMod.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class KeyBindings {

    public static KeyBinding openMenuKey;
    private static boolean isInitialized = false;

    @SubscribeEvent
    public static void init(final FMLClientSetupEvent event) {
        openMenuKey = new KeyBinding("key.hitcolor.open_menu",
                GLFW.GLFW_KEY_INSERT,
                "key.categories.hitcolor");

        ClientRegistry.registerKeyBinding(openMenuKey);
        isInitialized = true;
    }

    @Mod.EventBusSubscriber(modid = HitcolorMod.MOD_ID, value = Dist.CLIENT)
    public static class KeyboardInputHandler {
        @SubscribeEvent
        public static void onKeyInput(InputEvent.KeyInputEvent event) {
            if (isInitialized && openMenuKey != null && openMenuKey.isDown()) {
                HitcolorMod.openConfigScreen();
            }
        }
    }
}