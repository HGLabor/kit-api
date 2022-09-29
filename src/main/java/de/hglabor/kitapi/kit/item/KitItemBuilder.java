package de.hglabor.kitapi.kit.item;


import de.hglabor.kitapi.KitApi;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class KitItemBuilder {
    public static final NamespacedKey MARKER = new NamespacedKey(KitApi.getPlugin(), "kititem");
    public static final NamespacedKey UNDROPPABLE = new NamespacedKey(KitApi.getPlugin(), "undroppable");
    private final ItemStack itemStack;

    public KitItemBuilder(Material material) {
        this.itemStack = new ItemStack(material);
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.getPersistentDataContainer().set(MARKER, PersistentDataType.BYTE, (byte) 1);
        itemMeta.getPersistentDataContainer().set(UNDROPPABLE, PersistentDataType.BYTE, (byte) 1);
        this.itemStack.setItemMeta(itemMeta);
    }

    public KitItemBuilder withAmount(int amount) {
        this.itemStack.setAmount(amount);
        return this;
    }

    public KitItemBuilder makeDroppable() {
        this.itemStack.getItemMeta().getPersistentDataContainer().remove(UNDROPPABLE);
        return this;
    }

    public KitItemBuilder withName(Component name) {
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.displayName(name);
        this.itemStack.setItemMeta(itemMeta);
        return this;
    }

    public KitItemBuilder makeUnbreakable() {
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.setUnbreakable(true);
        this.itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemStack build() {
        return itemStack;
    }
}
