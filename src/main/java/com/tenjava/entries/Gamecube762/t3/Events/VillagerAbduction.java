package com.tenjava.entries.Gamecube762.t3.Events;

import com.tenjava.entries.Gamecube762.t3.TenJava;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
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
        }, 20, 5);
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

        for (Entity entity : villager.getNearbyEntities(50,50,50))
            if (entity instanceof Player)
                BuildLiftBeam((Player)entity, villager.getLocation());
    }

    public void abductedVilager(Villager villager) {
        villagersBeingAbducted.remove(villager);
    }


    /*
        0,-1,0  - glass  - 1
        0,-2,0  - beacon - 2
        0,-3,0  - iron   - 3
        0,-3,1  - iron   - 4
        0,-3,-1 - iron   - 5
        1,-3,0  - iron   - 6
        1,-3,1  - iron   - 7
        1,-3,-1 - iron   - 8
        */
    static private HashMap<Integer, Material> a = new HashMap<Integer, Material>();
    static private HashMap<Integer, Vector>   b = new HashMap<Integer, Vector>();
    static {
        a.put(1, Material.GLASS);       b.put(1, new Vector(0,-1,0));
        a.put(2, Material.BEACON);      b.put(2, new Vector(0,-2,0));
        a.put(3, Material.IRON_BLOCK);  b.put(3, new Vector(0,-3,0));
        a.put(4, Material.IRON_BLOCK);  b.put(4, new Vector(0,-3,1));
        a.put(5, Material.IRON_BLOCK);  b.put(5, new Vector(0,-3,-1));
        a.put(6, Material.IRON_BLOCK);  b.put(6, new Vector(1,-3,0));
        a.put(7, Material.IRON_BLOCK);  b.put(7, new Vector(1,-3,1));
        a.put(8, Material.IRON_BLOCK);  b.put(8, new Vector(1,-3,-1));
    }

    public static void BuildLiftBeam(Player p, Location l) {
        for (int i = 1; i <= 8; i++)
            p.sendBlockChange(l.add(b.get(i)), a.get(i), (byte)0);
    }

}
