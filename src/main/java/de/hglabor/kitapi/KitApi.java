package de.hglabor.kitapi;

import de.hglabor.kitapi.kit.AbstractKit;
import de.hglabor.kitapi.kit.player.IKitPlayer;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Function;

public final class KitApi {
    private static final Map<String, AbstractKit> KIT_REGISTRY = new HashMap<>();
    private static JavaPlugin plugin;
    private static Function<UUID, IKitPlayer> playerGetter;

    @ApiStatus.Internal
    public static void init(Function<UUID, IKitPlayer> playerGetter, JavaPlugin plugin) {
        KitApi.plugin = plugin;
        KitApi.playerGetter = playerGetter;
    }

    public static void register(AbstractKit kit) {
        if (KIT_REGISTRY.containsKey(kit.getName().toLowerCase(Locale.ROOT))) {
            throw new RuntimeException(kit.getName() + " already exists");
        } else {
            KIT_REGISTRY.put(kit.getName().toLowerCase(Locale.ROOT), kit);
        }
    }

    public static AbstractKit get(String name) {
        AbstractKit kit = KIT_REGISTRY.get(name.toLowerCase(Locale.ROOT));
        if (kit == null) {
            throw new RuntimeException(name + "doesn't exist");
        } else {
            return kit;
        }
    }

    public static List<AbstractKit> getKits() {
        return KIT_REGISTRY.values().stream().toList();
    }

    public static JavaPlugin getPlugin() {
        return plugin;
    }

    @NotNull
    public static IKitPlayer getKitPlayer(UUID uuid) {
        return playerGetter.apply(uuid);
    }
}
