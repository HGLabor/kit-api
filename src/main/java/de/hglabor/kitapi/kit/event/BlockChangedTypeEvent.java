package de.hglabor.kitapi.kit.event;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.BlockEvent;
import org.jetbrains.annotations.NotNull;

public class BlockChangedTypeEvent extends BlockEvent {
    private static final HandlerList HANDLERS = new HandlerList();
    private final Material type;

    public BlockChangedTypeEvent(@NotNull Block theBlock, Material type) {
        super(theBlock);
        this.type = type;
    }

    @Override
    @NotNull
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    @NotNull
    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public Material getType() {
        return type;
    }
}
