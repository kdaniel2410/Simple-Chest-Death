package com.github.kdaniel2410;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
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
        deathChests.put(block, event.getDrops().toArray(new ItemStack[0]));
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

    @EventHandler
    public void onBlockExplode(BlockExplodeEvent event) {
        event.blockList().removeIf(block -> deathChests.containsKey(block));
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        event.blockList().removeIf(block -> deathChests.containsKey(block));
    }

    public boolean isInventoryEmpty(Inventory inventory) {
        for (ItemStack itemstack : inventory.getContents()) {
            if (itemstack != null) return false;
        }
        return true;
    }
}
