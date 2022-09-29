package de.hglabor.kitapi.kit.player;

import de.hglabor.kitapi.kit.AbstractKit;
import it.unimi.dsi.fastutil.Pair;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class AbstractKitPlayer implements IKitPlayer {
    protected final UUID uuid;
    protected final Map<String, Object> kitAttributes = new HashMap<>();
    protected Pair<Entity, Long> latestTarget = Pair.of(null, 0L);

    protected AbstractKitPlayer(UUID uuid) {
        this.uuid = uuid;
    }

    @SuppressWarnings("unchecked")
    public void addCooldown(AbstractKit abstractKit, float amount, String action) {
        ((Map<String, Long>) kitAttributes.computeIfAbsent(abstractKit.getName() + "kitCooldown", x -> new HashMap<>())).put(action, System.currentTimeMillis() + (long) (amount * 1000L));
    }

    @SuppressWarnings("unchecked")
    public final boolean hasCooldown(AbstractKit kit, String action) {
        return System.currentTimeMillis() < ((Map<String, Long>) kitAttributes.getOrDefault(kit.getName() + "kitCooldown", new HashMap<>())).getOrDefault(action, 0L);
    }

    @Override
    public final UUID getUuid() {
        return uuid;
    }

    @Override
    public Pair<@Nullable Entity, Long> getLatestTarget() {
        return latestTarget;
    }

    @Override
    public void setLatestTarget(Entity entity) {
        this.latestTarget = Pair.of(entity, System.currentTimeMillis());
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getKitAttribute(String key) {
        return (T) kitAttributes.get(key);
    }

    @Override
    public <T> T getKitAttributeOrDefault(String key, T defaultValue) {
        return getKitAttribute(key) == null ? defaultValue : getKitAttribute(key);
    }

    @Override
    public <T> void putKitAttribute(String key, T value) {
        kitAttributes.put(key, value);
    }

    @Override
    public void sendMessage(Component component) {
        getPlayer().ifPresent(player -> player.sendMessage(component));
    }
}
