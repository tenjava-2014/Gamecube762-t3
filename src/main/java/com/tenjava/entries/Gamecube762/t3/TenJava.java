package com.tenjava.entries.Gamecube762.t3;

import com.tenjava.entries.Gamecube762.t3.Listeners.PlayerListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

public class TenJava extends JavaPlugin {

    Random random = new Random();

    //Listeners
    PlayerListener playerListener;

    public void onEnable() {

        playerListener = new PlayerListener(this);

    }



}
