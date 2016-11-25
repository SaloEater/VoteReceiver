package com.salo.votecore;

import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import net.milkbowl.vault.economy.Economy;

/**
 * Created by user on 24.11.2016.
 */
public class Main extends JavaPlugin {

    public static Economy econ = null;

    @Override
    public void onEnable() {
        setupEconomy();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof ConsoleCommandSender) {
            if (args.length > 0) {
                Integer balance = Math.toIntExact((long) econ.getBalance(args[1]));
                switch (args[0]) {
                    case "balance":
                        getServer().dispatchCommand(getServer().getConsoleSender(), "sync console " + args[2] + " coreanswer tell " + args[1] + " " + 0 + " " + balance);
                        return true;

                    case "receive":
                        getLogger().info(args[3]);
                        if (balance >= Integer.valueOf(args[3])) {
                            econ.withdrawPlayer(args[1], Integer.valueOf(args[3]));
                            getServer().dispatchCommand(getServer().getConsoleSender(), "sync console " + args[2] + " coreanswer pay " + args[1] + " " + args[3]);
                        } else {
                            getServer().dispatchCommand(getServer().getConsoleSender(), "sync console " + args[2] + " coreanswer tell " + args[1] + " " + 1 + " " + balance);
                        }
                        return true;

                }
            }
        }
        return true;
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
}
