package net.droingo.aerowind.wind;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public final class WindModel {
    private static final double CLEAR_BASE_SPEED = 1.0D;
    private static final double RAIN_MULTIPLIER = 4.0D;
    private static final double THUNDER_MULTIPLIER = 6.0D;

    private WindModel() {
    }

    public static WindSample sample(ServerLevel level, Vec3 worldPosition) {
        long day = level.getDayTime() / 24000L;
        long timeOfDay = level.getDayTime() % 24000L;

        double daySeed = hashToUnit(level.getSeed() + day * 31L);

        double baseAngle = daySeed * Math.TAU;
        double dayRotation = (timeOfDay / 24000.0D) * Math.TAU * 0.35D;

        double height = worldPosition.y;
        double heightLayer = Math.floor(height / 64.0D);
        double heightAngleOffset = heightLayer * 0.35D;

        double gust = gust(level.getGameTime(), daySeed, height);
        double weatherMultiplier = weatherMultiplier(level);
        double heightMultiplier = Mth.clamp(0.6D + (height / 192.0D), 0.5D, 2.2D);

        double daySpeedWave = 0.75D + (Math.sin((timeOfDay / 24000.0D) * Math.TAU - 1.2D) + 1.0D) * 0.25D;

        double angle = baseAngle + dayRotation + heightAngleOffset + gust * 0.25D;

        Vec3 direction = new Vec3(
                Math.cos(angle),
                0.03D,
                Math.sin(angle)
        ).normalize();

        double speed = CLEAR_BASE_SPEED
                * weatherMultiplier
                * heightMultiplier
                * daySpeedWave
                * (1.0D + gust);

        return new WindSample(direction, speed);
    }

    private static double weatherMultiplier(ServerLevel level) {
        if (level.isThundering()) {
            return THUNDER_MULTIPLIER;
        }

        if (level.isRaining()) {
            return RAIN_MULTIPLIER;
        }

        return 1.0D;
    }

    private static double gust(long gameTime, double daySeed, double height) {
        double slow = Math.sin((gameTime * 0.006D) + daySeed * 40.0D + height * 0.03D);
        double fast = Math.sin((gameTime * 0.031D) + daySeed * 91.0D);

        double combined = (slow * 0.7D) + (fast * 0.3D);
        return Math.max(0.0D, combined) * 0.45D;
    }

    private static double hashToUnit(long value) {
        long x = value;
        x ^= x >>> 33;
        x *= 0xff51afd7ed558ccdL;
        x ^= x >>> 33;
        x *= 0xc4ceb9fe1a85ec53L;
        x ^= x >>> 33;

        return (x & 0xFFFFFF) / (double) 0xFFFFFF;
    }
}