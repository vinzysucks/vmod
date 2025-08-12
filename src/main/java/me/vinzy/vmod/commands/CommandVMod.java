package me.vinzy.vmod.commands;

import me.vinzy.vmod.VMod;
import me.vinzy.vmod.utils.ConfigFile;
import me.vinzy.vmod.utils.GhostBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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

        String sub = args[0].toLowerCase();

        if (sub.equals("help")) {
            sender.addChatMessage(new ChatComponentText(PREFIX + "Available subcommands:"));
            sender.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_PURPLE + "/vmod" + EnumChatFormatting.GRAY + " - Open the VMod GUI"));
            sender.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_PURPLE + "/vmod help" + EnumChatFormatting.GRAY + " - Show this help menu"));
            sender.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_PURPLE + "/vmod restoreghosts" + EnumChatFormatting.GRAY + " - Restores ghost blocks"));
            sender.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_PURPLE + "/vmod sethand <block> <amount>" + EnumChatFormatting.GRAY + " - Sets your hand to an item"));
            sender.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_PURPLE + "/vmod reloadConfig" + EnumChatFormatting.GRAY + " - Reloads config"));
        } else if (sub.equals("restoreghosts")) {
            GhostBlock.restoreghosts();
        } else if (sub.equals("reloadconfig")) {
            ConfigFile.checkForConfig();
            VMod.mc.thePlayer.addChatMessage(new ChatComponentText(VMod.PREFIX + "Reloaded config."));
        } else if (sub.equals("sethand")){
            if (args.length < 3) {
                sender.addChatMessage(new ChatComponentText(PREFIX + EnumChatFormatting.RED + "Usage: /vmod sethand <item> <amount>"));
                return;
            }

            String itemName = args[1];
            String amountStr = args[2];

            int amount;
            try {
                amount = Integer.parseInt(amountStr);
            } catch (NumberFormatException e) {
                sender.addChatMessage(new ChatComponentText(PREFIX + EnumChatFormatting.RED + "Invalid amount."));
                return;
            }

            Item item = Item.getByNameOrId(itemName.toLowerCase());
            if (item == null) {
                sender.addChatMessage(new ChatComponentText(PREFIX + EnumChatFormatting.RED + "Unknown item: " + itemName));
                return;
            }

            ItemStack stack = new ItemStack(item, amount);
            VMod.mc.thePlayer.inventory.setInventorySlotContents(VMod.mc.thePlayer.inventory.currentItem, stack);
            sender.addChatMessage(new ChatComponentText(PREFIX + EnumChatFormatting.GREEN + "Set your hand to " + amount + "x " + itemName));
        } else {
            sender.addChatMessage(new ChatComponentText(PREFIX + EnumChatFormatting.RED + "Unknown subcommand. Use /vmod help"));
        }
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }
}
