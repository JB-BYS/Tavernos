package fr.chilli.Listeners;

import fr.chilli.Gui.Utils.menu.Menu;
import fr.chilli.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.InventoryHolder;

public class MenuListener implements Listener {


    @EventHandler
    public void onMenuClick(InventoryClickEvent e) {

        InventoryHolder holder = e.getInventory().getHolder();
        //If the inventoryholder of the inventory clicked on
        // is an instance of Menu, then gg. The reason that
        // an InventoryHolder can be a Menu is because our Menu
        // class implements InventoryHolder!!
        if (holder instanceof Menu) {
            e.setCancelled(true); //prevent them from fucking with the inventory
            if (e.getCurrentItem() == null) { //deal with null exceptions
                return;
            }
            //Since we know our inventoryholder is a menu, get the Menu Object representing
            // the menu we clicked on
            Menu menu = (Menu) holder;
            //Call the handleMenu object which takes the event and processes it
            menu.handleMenu(e);
        }

    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
            if (Main.getPlugin().event.get(event.getPlayer()) == true){
                Main.getPlugin().map.put(event.getPlayer(), event.getMessage());
                event.setCancelled(true);
            }
    }

}
