package com.maxbucknell.minecraft;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.ConsoleCommandSender;
import com.maxbucknell.minecraft.listeners.MVRespawnEventListener;

public final class MaxCraft extends JavaPlugin
{
    @Override
    public void onEnable()
    {
        ConsoleCommandSender console = this.getServer().getConsoleSender();

        this.getCommand("mbspawnpoint").setExecutor(new SpawnPointCommand(console));

        this.getServer().getPluginManager().registerEvents(new MVRespawnEventListener(), this);
    }
}
