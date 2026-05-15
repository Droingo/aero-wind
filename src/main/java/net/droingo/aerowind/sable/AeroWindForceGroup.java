package net.droingo.aerowind.sable;

import dev.ryanhcode.sable.api.physics.force.ForceGroup;
import net.minecraft.network.chat.Component;

public final class AeroWindForceGroup {
    public static final ForceGroup WIND = new ForceGroup(
            Component.literal("Aero Wind"),
            Component.literal("Wind Projector"),
            0x88CCFF,
            true
    );

    private AeroWindForceGroup() {
    }
}