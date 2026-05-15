package net.droingo.aerowind.block;

import net.droingo.aerowind.blockentity.WindProjectorBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class WindProjectorBlock extends Block implements EntityBlock {
    public WindProjectorBlock(Properties properties) {
        super(properties);
    }

    @Override
    public int getLightBlock(BlockState state, BlockGetter level, BlockPos pos) {
        return 0;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new WindProjectorBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return (tickLevel, tickPos, tickState, blockEntity) -> {
            if (blockEntity instanceof WindProjectorBlockEntity windProjector) {
                if (tickLevel.isClientSide()) {
                    WindProjectorBlockEntity.clientTick(tickLevel, tickPos, tickState, windProjector);
                } else {
                    WindProjectorBlockEntity.serverTick(tickLevel, tickPos, tickState, windProjector);
                }
            }
        };
    }
}