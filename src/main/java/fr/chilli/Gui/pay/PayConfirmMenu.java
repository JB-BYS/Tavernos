package fr.chilli.Gui.pay;

import com.earth2me.essentials.IEssentials;
import fr.chilli.Gui.Utils.menu.Menu;
import fr.chilli.Gui.Utils.menu.PlayerMenuUtility;
import fr.chilli.Main;
import net.ess3.api.IUser;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import static org.bukkit.Material.EMERALD;

public class PayConfirmMenu extends Menu implements Listener {

    public PayConfirmMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return "Payer " + playerMenuUtility.getPlayerToKill().getName();
    }

    @Override
    public int getSlots() {
        return 27;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Main plugin = Main.getPlugin();
        Player player = (Player) e.getWhoClicked();
        IEssentials essentials = (IEssentials) plugin.getServer().getPluginManager().getPlugin("Essentials");
        IUser user = essentials.getUser(player.getUniqueId());

        // and here is the code that launch the rest of the code
        switch (e.getCurrentItem().getType()){
            case EMERALD:
                e.getWhoClicked().closeInventory();
                player.performCommand("pay " + playerMenuUtility.getPlayerToKill().getName() + " " + Main.getPlugin().map.get(e.getWhoClicked()));
                break;
            case BARRIER:

                //go back to the previous menu
                new PayPlayerMenu(playerMenuUtility).open();

                break;
        }

    }

    @Override
    public void setMenuItems() {

        ItemStack yes = new ItemStack(EMERALD, 1);
        ItemMeta yes_meta = yes.getItemMeta();
        yes_meta.setDisplayName(ChatColor.GREEN + "Valider");
        ArrayList<String> yes_lore = new ArrayList<>();
        yes_lore.add(ChatColor.AQUA + "Envoyer "+Main.getPlugin().map.get(playerMenuUtility.getOwner())+" à "+playerMenuUtility.getPlayerToKill().getName());
        yes_meta.setLore(yes_lore);
        yes.setItemMeta(yes_meta);
        ItemStack no = new ItemStack(Material.BARRIER, 1);
        ItemMeta no_meta = no.getItemMeta();
        no_meta.setDisplayName(ChatColor.DARK_RED + "Annuler");
        no.setItemMeta(no_meta);

        inventory.setItem(12, yes);
        inventory.setItem(14, no);

        setFillerGlass();

    }


}
