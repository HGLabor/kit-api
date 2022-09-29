package de.hglabor.kitapi.kit.player;

import de.hglabor.kitapi.kit.AbstractKit;
import it.unimi.dsi.fastutil.Pair;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;

public interface IKitPlayer {
    UUID getUuid();

    boolean hasKit(AbstractKit kit);

    Optional<Player> getPlayer();

    void sendCooldownInfo(AbstractKit kit, String key);

    Pair<@Nullable Entity, Long> getLatestTarget();

    void setLatestTarget(Entity entity);

    <T> T getKitAttribute(String key);

    <T> T getKitAttributeOrDefault(String key, T defaultValue);

    <T> void putKitAttribute(String key, T value);

    default void addCooldown(AbstractKit abstractKit, float amount) {
        addCooldown(abstractKit, amount, AbstractKit.DEFAULT_COOLDOWN_KEY);
    }

    void addCooldown(AbstractKit abstractKit, float amount, String action);

    default boolean hasCooldown(AbstractKit abstractKit) {
        return hasCooldown(abstractKit, AbstractKit.DEFAULT_COOLDOWN_KEY);
    }

    boolean hasCooldown(AbstractKit abstractKit, String action);

    void sendMessage(Component component);

    default void sendMessage(String text) {
        sendMessage(Component.text(text));
    }
}
