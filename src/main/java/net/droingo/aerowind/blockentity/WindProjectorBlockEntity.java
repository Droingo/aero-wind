package net.droingo.aerowind.blockentity;

import net.droingo.aerowind.AeroWind;
import net.droingo.aerowind.AeroWindBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class WindProjectorBlockEntity extends BlockEntity {
    private static final Vec3 DEBUG_WIND_DIRECTION = new Vec3(1.0D, 0.05D, 0.0D).normalize();
    private static final double DEBUG_WIND_SPEED = 0.035D;

    public WindProjectorBlockEntity(BlockPos pos, BlockState blockState) {
        super(AeroWindBlockEntities.WIND_PROJECTOR.get(), pos, blockState);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, WindProjectorBlockEntity blockEntity) {
        if (level.getGameTime() % 100 == 0) {
            AeroWind.LOGGER.info(
                    "Wind Projector at {} windDirection={} windSpeed={}",
                    pos,
                    DEBUG_WIND_DIRECTION,
                    DEBUG_WIND_SPEED
            );
        }
    }

    public static void clientTick(Level level, BlockPos pos, BlockState state, WindProjectorBlockEntity blockEntity) {
        RandomSource random = level.random;

        if (random.nextFloat() > 0.35F) {
            return;
        }

        double x = pos.getX() + 0.5D + ((random.nextDouble() - 0.5D) * 2.0D);
        double y = pos.getY() + 0.5D + (random.nextDouble() * 1.5D);
        double z = pos.getZ() + 0.5D + ((random.nextDouble() - 0.5D) * 2.0D);

        Vec3 particleVelocity = DEBUG_WIND_DIRECTION.scale(DEBUG_WIND_SPEED);

        level.addParticle(
                ParticleTypes.CLOUD,
                x,
                y,
                z,
                particleVelocity.x,
                particleVelocity.y,
                particleVelocity.z
        );
    }
}