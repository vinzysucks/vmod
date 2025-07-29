package me.vinzy.vmod.commands;

import me.vinzy.vmod.utils.PacketUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class CommandVMod extends CommandBase {
    private static boolean blinkEnabled = false;

    public static void toggleBlink() {
        blinkEnabled = !blinkEnabled;
        if (blinkEnabled) {
            PacketUtils.disableWriting();
        } else {
            PacketUtils.enableWriting();
        }
    }

    public static boolean isBlinkEnabled() {
        return blinkEnabled;
    }

    private static final String PREFIX = EnumChatFormatting.DARK_PURPLE + "[VMod] " + EnumChatFormatting.RESET;
    private static final Minecraft mc = Minecraft.getMinecraft();
    private static boolean packetDisabled = false;

    @Override
    public String getCommandName() {
        return "VMod";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/vmod <subcommand>";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length == 0) {
            sender.addChatMessage(new ChatComponentText(PREFIX + EnumChatFormatting.RED + "Missing subcommand. Use /vmod help"));
            return;
        }
    }
}
