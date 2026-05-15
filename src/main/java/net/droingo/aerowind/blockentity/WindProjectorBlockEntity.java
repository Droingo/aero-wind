package net.droingo.aerowind.blockentity;

import dev.ryanhcode.sable.sublevel.system.SubLevelPhysicsSystem;
import net.droingo.aerowind.AeroWind;
import net.droingo.aerowind.AeroWindBlockEntities;
import net.droingo.aerowind.sable.SableWindAccess;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3d;

public class WindProjectorBlockEntity extends BlockEntity {
    private static final Vec3 DEBUG_WIND_DIRECTION = new Vec3(1.0D, 0.05D, 0.0D).normalize();
    private static final double DEBUG_WIND_SPEED = 0.08D;

    private boolean loggedLevelClass = false;

    public WindProjectorBlockEntity(BlockPos pos, BlockState blockState) {
        super(AeroWindBlockEntities.WIND_PROJECTOR.get(), pos, blockState);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, WindProjectorBlockEntity blockEntity) {
        if (!blockEntity.loggedLevelClass) {
            blockEntity.loggedLevelClass = true;
            AeroWind.LOGGER.info("Level implementation at {}: {}", pos, level.getClass().getName());
        }

        if (!(level instanceof ServerLevel serverLevel)) {
            return;
        }

        var subLevel = SableWindAccess.findSubLevelAt(serverLevel, pos);
        if (subLevel == null) {
            return;
        }

        SubLevelPhysicsSystem physicsSystem = SubLevelPhysicsSystem.get(serverLevel);
        if (physicsSystem == null) {
            return;
        }

        var rigidBody = physicsSystem.getPhysicsHandle(subLevel);
        if (rigidBody == null || !rigidBody.isValid()) {
            return;
        }

        Vector3d impulse = new Vector3d(
                DEBUG_WIND_DIRECTION.x * DEBUG_WIND_SPEED,
                DEBUG_WIND_DIRECTION.y * DEBUG_WIND_SPEED,
                DEBUG_WIND_DIRECTION.z * DEBUG_WIND_SPEED
        );

        rigidBody.applyLinearImpulse(impulse);
        physicsSystem.getPipeline().wakeUp(subLevel);

        if (level.getGameTime() % 100 == 0) {
            AeroWind.LOGGER.info(
                    "Applied DIRECT wind impulse to sublevel {} runtimeId={} velocity={}",
                    subLevel.getUniqueId(),
                    subLevel.getRuntimeId(),
                    rigidBody.getLinearVelocity()
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

        Vec3 particleVelocity = DEBUG_WIND_DIRECTION.scale(0.035D);

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