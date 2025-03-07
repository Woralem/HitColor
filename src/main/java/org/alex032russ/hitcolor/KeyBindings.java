package org.alex032russ.hitcolor;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(modid = HitcolorMod.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class KeyBindings {
    // Объявляем привязку клавиш
    public static KeyBinding openMenuKey;
    private static boolean isInitialized = false;

    // Инициализируем привязку клавиш
    @SubscribeEvent
    public static void init(final FMLClientSetupEvent event) {
        openMenuKey = new KeyBinding("key.hitcolor.open_menu",
                GLFW.GLFW_KEY_INSERT, // Клавиша Insert
                "key.categories.hitcolor");

        // Регистрируем привязку
        ClientRegistry.registerKeyBinding(openMenuKey);
        isInitialized = true;
    }

    // Используем отдельный класс для обработки нажатий клавиш
    @Mod.EventBusSubscriber(modid = HitcolorMod.MOD_ID, value = Dist.CLIENT)
    public static class KeyboardInputHandler {
        @SubscribeEvent
        public static void onKeyInput(InputEvent.KeyInputEvent event) {
            // Добавляем проверку на null и инициализацию
            if (isInitialized && openMenuKey != null && openMenuKey.isDown()) {
                // Открываем меню мода
                HitcolorMod.openConfigScreen();
            }
        }
    }
}