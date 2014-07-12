package com.tenjava.entries.Gamecube762.t3;

import com.tenjava.entries.Gamecube762.t3.Events.VillagerAbduction;
import com.tenjava.entries.Gamecube762.t3.Listeners.PlayerListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

public class TenJava extends JavaPlugin {

    protected Random random = new Random();

    //Listeners
    PlayerListener playerListener;

    //Events
    VillagerAbduction villagerAbduction;

    public void onEnable() {

        playerListener = new PlayerListener(this);


        Bukkit.getPluginManager().registerEvents(playerListener, this);

        villagerAbduction = new VillagerAbduction(this);

    }

    public VillagerAbduction getVillagerAbduction() {
        return villagerAbduction;
    }
}
