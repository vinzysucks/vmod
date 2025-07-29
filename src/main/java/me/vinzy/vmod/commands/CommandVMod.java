package me.vinzy.vmod.commands;

import me.vinzy.vmod.VModConfig;
import me.vinzy.vmod.utils.PacketUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class CommandVMod extends CommandBase {

    private static final String PREFIX = EnumChatFormatting.DARK_PURPLE + "[VMod] " + EnumChatFormatting.RESET;
    private static final Minecraft mc = Minecraft.getMinecraft();

    @Override
    public String getCommandName() {
        return "vmod";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/vmod <subcommand>";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length == 0) {
            return;
        }

        // Handle "help" or invalid commands
        String sub = args[0].toLowerCase();

        if (sub.equals("help")) {
            sender.addChatMessage(new ChatComponentText(PREFIX + "Available subcommands:"));
            sender.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_PURPLE + "/vmod" + EnumChatFormatting.GRAY + " - Open the VMod GUI"));
            sender.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_PURPLE + "/vmod help" + EnumChatFormatting.GRAY + " - Show this help menu"));
            // Add more help lines if needed
        } else {
            sender.addChatMessage(new ChatComponentText(PREFIX + EnumChatFormatting.RED + "Unknown subcommand. Use /vmod help"));
        }
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0; // Allow all players to use
    }
}
