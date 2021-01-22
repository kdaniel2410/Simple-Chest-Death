package com.github.kdaniel2410;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class SimpleChestDeath extends JavaPlugin {

    @Override
    public void onEnable() {
        super.onEnable();
        Bukkit.getPluginManager().registerEvents(new MyListener(), this);
    }
}
