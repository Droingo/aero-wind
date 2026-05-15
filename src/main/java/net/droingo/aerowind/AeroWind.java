package net.droingo.aerowind;

import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

@Mod(AeroWind.MOD_ID)
public final class AeroWind {
    public static final String MOD_ID = "aero_wind";
    public static final Logger LOGGER = LogUtils.getLogger();

    public AeroWind(IEventBus modEventBus) {
        AeroWindBlocks.BLOCKS.register(modEventBus);
        AeroWindBlocks.ITEMS.register(modEventBus);

        LOGGER.info("Aero Wind loaded");
    }
}