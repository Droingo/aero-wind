package net.droingo.aerowind.client;

import net.droingo.aerowind.AeroWind;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;

@EventBusSubscriber(modid = AeroWind.MOD_ID, value = Dist.CLIENT)
public final class AeroWindClientEvents {
    private AeroWindClientEvents() {
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        Minecraft minecraft = Minecraft.getInstance();

        if (minecraft.level == null || minecraft.player == null) {
            return;
        }

        RandomSource random = minecraft.level.random;

        // More frequent sky wind particles
        if (random.nextFloat() > 0.18F) {
            return;
        }

        Vec3 playerPos = minecraft.player.position();

        long dayTime = minecraft.level.getDayTime();

        // Client-safe visual wind direction.
        // This should broadly match the server's slow daily wind feel.
        double baseAngle = (dayTime / 24000.0D) * Math.TAU * 0.35D;

        int layerHeight = 64;
        int heightLayer = Math.floorDiv((int) playerPos.y, layerHeight);

        // No server seed available client-side, so use a stable visual layer offset.
        double layerOffset = Math.sin(heightLayer * 12.9898D) * Math.toRadians(45.0D);

        double finalAngle = baseAngle + layerOffset - (Math.PI / 2.0D);

        Vec3 direction = new Vec3(
                Math.cos(finalAngle),
                0.04D,
                Math.sin(finalAngle)
        ).normalize();

        // Spawn particles ahead/upwind-ish around the player so they streak across view
        double side = (random.nextDouble() - 0.5D) * 60.0D;
        double forward = -30.0D + random.nextDouble() * 20.0D;

        Vec3 sideways = new Vec3(-direction.z, 0.0D, direction.x).normalize();

        double x = playerPos.x + sideways.x * side + direction.x * forward;
        double y = playerPos.y + 10.0D + random.nextDouble() * 24.0D;
        double z = playerPos.z + sideways.z * side + direction.z * forward;

        Vec3 velocity = direction.scale(-0.28D);

        minecraft.level.addParticle(
                ParticleTypes.CLOUD,
                x,
                y,
                z,
                velocity.x,
                velocity.y,
                velocity.z
        );
    }
}