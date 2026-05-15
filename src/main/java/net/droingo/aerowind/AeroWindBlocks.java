package net.droingo.aerowind;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredItem;
import net.droingo.aerowind.block.WindProjectorBlock;

public final class AeroWindBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(AeroWind.MOD_ID);

    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(AeroWind.MOD_ID);

    public static final DeferredBlock<Block> WIND_PROJECTOR = BLOCKS.register(
            "wind_projector",
            () -> new WindProjectorBlock(
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.COLOR_LIGHT_BLUE)
                            .strength(1.5F, 6.0F)
                            .sound(SoundType.METAL)
                            .requiresCorrectToolForDrops()
            )
    );

    public static final DeferredItem<BlockItem> WIND_PROJECTOR_ITEM =
            ITEMS.registerSimpleBlockItem(WIND_PROJECTOR, new Item.Properties());

    private AeroWindBlocks() {
    }
}