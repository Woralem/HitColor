package org.alex032russ.hitcolor;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = HitcolorMod.MOD_ID, value = Dist.CLIENT)
public class ChatCommandHandler {

    @SubscribeEvent
    public static void onClientChat(ClientChatEvent event) {
        // Проверяем, начинается ли сообщение с .hit
        if (event.getMessage().startsWith(".hit")) {
            // Отменяем отправку сообщения на сервер
            event.setCanceled(true);

            // Открываем меню мода
            HitcolorMod.openConfigScreen();
        }
    }
}