package org.alex032russ.hitcolor;

import org.alex032russ.hitcolor.config.HitcolorConfig;
import org.alex032russ.hitcolor.config.HitcolorConfigScreen;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.FMLNetworkConstants;
import org.apache.commons.lang3.tuple.Pair;

@Mod(HitcolorMod.MOD_ID)
public class HitcolorMod {
    public static final String MOD_ID = "hitcolor";
    public static DynamicTexture texture = null;
    private static boolean openConfig = false;
    public static boolean needsOverlayTextureUpdate = false;

    public HitcolorMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::clientSetup);

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, HitcolorConfig.spec);

        ModLoadingContext.get().registerExtensionPoint(
                ExtensionPoint.DISPLAYTEST,
                () -> Pair.of(() -> FMLNetworkConstants.IGNORESERVERONLY, (a, b) -> true)
        );

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void clientSetup(final FMLClientSetupEvent event) {
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
    public static class ClientEvents {

        @SubscribeEvent
        public static void onClientTick(TickEvent.ClientTickEvent event) {
            if (event.phase == TickEvent.Phase.END && openConfig) {
                openConfig = false;
                Minecraft.getInstance().setScreen(new HitcolorConfigScreen());
            }
        }
        @SubscribeEvent
        public static void onCommandsRegister(RegisterCommandsEvent event) {
            CommandDispatcher<CommandSource> dispatcher = event.getDispatcher();

            LiteralArgumentBuilder<CommandSource> command = Commands.literal("hitcolor")
                    .executes(context -> {
                        if (context.getSource().getEntity() != null) {
                            openConfig = true;
                        }
                        return 1;
                    });

            dispatcher.register(command);
        }

        @SubscribeEvent
        public static void onRenderGameOverlay(RenderGameOverlayEvent.Pre event) {
            if (event.getType() == RenderGameOverlayEvent.ElementType.ALL) {
                needsOverlayTextureUpdate = true;
            }
        }

        private void clientSetup(final FMLClientSetupEvent event) {
            KeyBindings.init(event);
        }
    }

    public static void openConfigScreen() {
        openConfig = true;
    }
}