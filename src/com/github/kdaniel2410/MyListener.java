package com.github.kdaniel2410;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class MyListener implements org.bukkit.event.Listener {
    HashMap<Block, ItemStack[]> deathChests = new HashMap<>();

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (isInventoryEmpty(event.getEntity().getInventory())) {
            return;
        }
        Block block = event.getEntity().getLocation().getBlock();
        block.setType(Material.CHEST);
        deathChests.put(block, event.getEntity().getInventory().getContents());
        event.getDrops().clear();
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (!event.hasBlock()) {
            return;
        }
        if (!deathChests.containsKey(event.getClickedBlock())) {
            return;
        }
        for (ItemStack itemstack : deathChests.get(event.getClickedBlock())) {
            event.getPlayer().getWorld().dropItem(event.getClickedBlock().getLocation(), itemstack);
        }
        deathChests.remove(event.getClickedBlock());
        event.getClickedBlock().setType(Material.AIR);
    }

    public boolean isInventoryEmpty(Inventory inventory) {
        for (ItemStack itemstack : inventory.getContents()) {
            if (itemstack != null) return false;
        }
        return true;
    }
}
