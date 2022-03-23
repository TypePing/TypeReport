package typestore.typereport.utils;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GlowUtils {

    public boolean hasGlow(ItemStack itemStack) {
        return itemStack.getItemMeta().hasEnchants();
    }

    public void removeGlow(ItemStack itemStack) {
        itemStack.removeEnchantment(Enchantment.DURABILITY);
    }

    public void addGlow(ItemStack item) {
        if (item == null) return;
        item.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
        ItemMeta meta = item.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
    }
}
