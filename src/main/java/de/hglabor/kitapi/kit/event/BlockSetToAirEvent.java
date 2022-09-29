package de.hglabor.kitapi.kit.event;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;

public class BlockSetToAirEvent extends BlockChangedTypeEvent {
    public BlockSetToAirEvent(@NotNull Block theBlock) {
        super(theBlock, Material.AIR);
    }
}
