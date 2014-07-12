package com.tenjava.entries.Gamecube762.t3.Listeners;

import com.tenjava.entries.Gamecube762.t3.TenJava;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerListener implements Listener {

    TenJava plugin;

    public PlayerListener(TenJava plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        plugin.getVillagerAbduction().findVillagers(e.getPlayer(), 30);
    }


}
