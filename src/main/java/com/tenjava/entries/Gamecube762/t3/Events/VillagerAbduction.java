package com.tenjava.entries.Gamecube762.t3.Events;

import com.tenjava.entries.Gamecube762.t3.TenJava;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by Gamecube762 on 7/12/14.
 */
public class VillagerAbduction {

    TenJava plugin;


    int HeightDifference = 54;
    HashMap<Villager, Location> villagersBeingAbducted = new HashMap<Villager, Location>();

    public VillagerAbduction(TenJava plugin) {
        this.plugin = plugin;

        Bukkit.getScheduler().runTaskTimer(plugin, new Runnable() {
            @Override
            public void run() {
                if (villagersBeingAbducted.isEmpty()) return;

                for (Villager villager : villagersBeingAbducted.keySet()) {
                    villager.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20, 5));

                    Location l = villagersBeingAbducted.get(villager),
                            vl = villager.getLocation();

                    if ( (vl.getBlockX() != l.getBlockX()) || (vl.getBlockZ() != l.getBlockZ()) )
                        villager.teleport(new Location(l.getWorld(), l.getBlockX() + 0.5, vl.getY(), l.getBlockZ() + 0.5));

                    if (vl.getY() < l.getBlockY() + HeightDifference)
                        villager.setVelocity(new Vector(0.0, 0.5, 0.0));
                    else
                        abductedVilager(villager);
                }

            }
        }, 20, 10);
    }

    public void findVillagers(Player p, double range) {
        for (Entity entity : p.getNearbyEntities(range, range, range))
            if (entity instanceof Villager)
                if ( !villagersBeingAbducted.containsKey((Villager)entity) ){
                    Random r = new Random();
                    if ( r.nextBoolean() && r.nextBoolean() )
                        abductVillager((Villager)entity);
                }
    }

    public void abductVillager(Villager villager) {
        villagersBeingAbducted.put(villager, villager.getLocation());
    }

    public void abductedVilager(Villager villager) {
        villagersBeingAbducted.remove(villager);
    }
}
