package fr.chilli.Gui.balance;

import com.earth2me.essentials.IEssentials;
import fr.chilli.Gui.Utils.menu.PaginatedMenu;
import fr.chilli.Gui.Utils.menu.PlayerMenuUtility;
import fr.chilli.Gui.pay.PayConfirmMenu;
import fr.chilli.Main;
import net.ess3.api.IUser;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.UUID;

import static org.bukkit.Bukkit.getServer;

public class BalancePlayerMenu extends PaginatedMenu {

    public BalancePlayerMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return ChatColor.GREEN+"Ma trésorerie";
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();

        switch (e.getCurrentItem().getType()){
            case BARRIER:
                p.closeInventory();
        }
    }

    @Override
    public void setMenuItems() {
        Main plugin = Main.getPlugin();
        Player player = playerMenuUtility.getOwner();
        IEssentials essentials = (IEssentials) plugin.getServer().getPluginManager().getPlugin("Essentials");
        IUser user = essentials.getUser(player.getUniqueId());

        addMenuBorder();
        ItemStack current = new ItemStack(Material.BARRIER, 1);
        ItemMeta current_meta = current.getItemMeta();
        current_meta.setDisplayName(ChatColor.DARK_RED + "Valeur du compte:");
        ArrayList<String> lore = new ArrayList();
        lore.add(String.valueOf(user.getMoney()));
        current_meta.setLore(lore);
        current.setItemMeta(current_meta);

        inventory.setItem(15,current);
    }
}
