package net.droingo.aerowind.sable;

import dev.ryanhcode.sable.api.sublevel.ServerSubLevelContainer;
import dev.ryanhcode.sable.api.sublevel.SubLevelContainer;
import dev.ryanhcode.sable.sublevel.ServerSubLevel;
import dev.ryanhcode.sable.sublevel.plot.ServerLevelPlot;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;

public final class SableWindAccess {
    private SableWindAccess() {
    }

    public static ServerSubLevel findSubLevelAt(ServerLevel level, BlockPos pos) {
        ServerSubLevelContainer container = SubLevelContainer.getContainer(level);

        if (!container.inBounds(pos)) {
            return null;
        }

        ServerLevelPlot plot = (ServerLevelPlot) container.getPlot(pos.getX() >> 4, pos.getZ() >> 4);

        if (plot == null) {
            return null;
        }

        if (!plot.contains(pos.getX(), pos.getZ())) {
            return null;
        }

        return plot.getSubLevel();
    }
}