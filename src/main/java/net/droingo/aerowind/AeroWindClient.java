package net.droingo.aerowind;

import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientChatEvent;

@EventBusSubscriber(modid = AeroWind.MOD_ID, value = Dist.CLIENT)
public final class AeroWindClient {
    private AeroWindClient() {
    }

    @SubscribeEvent
    public static void onClientChat(ClientChatEvent event) {
        AeroWind.LOGGER.info("Client is ready: {}", Minecraft.getInstance().getUser().getName());
    }
}