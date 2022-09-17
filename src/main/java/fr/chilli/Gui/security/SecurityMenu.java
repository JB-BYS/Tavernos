package fr.chilli.Gui.security;

import com.earth2me.essentials.IEssentials;
import fr.chilli.Gui.Utils.menu.PaginatedMenu;
import fr.chilli.Gui.Utils.menu.PlayerMenuUtility;
import fr.chilli.Main;
import net.ess3.api.IUser;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SecurityMenu extends PaginatedMenu {

    public SecurityMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return ChatColor.GREEN+"Aide";
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        Main plugin = Main.getPlugin();
        IEssentials essentials = (IEssentials) plugin.getServer().getPluginManager().getPlugin("Essentials");
        IUser user = essentials.getUser(p.getUniqueId());

        switch (e.getCurrentItem().getType())
        {
            case RED_BANNER:
                e.getWhoClicked().closeInventory();
                e.getWhoClicked().sendMessage(ChatColor.GREEN + "Quel bug voulez vous faire remonter ?");
                Main.getPlugin().event.put((Player) e.getWhoClicked(),true);
                Main.getPlugin().map.clear();
                while (Main.getPlugin().map.get(e.getWhoClicked()) == null){
                    //waiting
                }
                Main.getPlugin().event.put((Player) e.getWhoClicked(),false);
                p.performCommand("mail send chilli_pepper "+Main.getPlugin().map.get(e.getWhoClicked()));
                p.performCommand("mail send l0lilian0l "+Main.getPlugin().map.get(e.getWhoClicked()));
                p.sendMessage(ChatColor.GREEN+"merci pour votre retours");
                break;
            case BARRIER:
                p.closeInventory();
                break;
            case ORANGE_BANNER:
                p.closeInventory();
                new SelectReport(playerMenuUtility).open();
        }
    }

    @Override
    public void setMenuItems() {

        addMenuBorder();
        ItemStack red_banner = new ItemStack(Material.RED_BANNER, 1);
        ItemMeta red_meta = red_banner.getItemMeta();
        red_meta.setDisplayName(ChatColor.RED + "Signaler un bug");
        red_banner.setItemMeta(red_meta);
        ItemStack or_banner = new ItemStack(Material.ORANGE_BANNER, 1);
        ItemMeta or_meta = or_banner.getItemMeta();
        or_meta.setDisplayName(ChatColor.RED + "Signaler un joueur");
        red_banner.setItemMeta(or_meta);

        inventory.setItem(30, red_banner);
        inventory.setItem(32, or_banner);
        }
}
