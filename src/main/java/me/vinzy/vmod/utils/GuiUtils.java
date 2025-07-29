package me.vinzy.vmod.utils;

import me.vinzy.vmod.VMod;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class GuiUtils {
    private static GuiScreen latest = null;
    public static void saveAndCloseScreen () {
        GuiScreen currentscreen = Minecraft.getMinecraft().currentScreen;
        latest = currentscreen;
        if (currentscreen == null) return;
        Minecraft.getMinecraft().displayGuiScreen(null);
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(
                VMod.PREFIX + "Soft closed and saved GUI."));
    }

    public static void displayGUI () {
        if (latest == null) {
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(
                    VMod.PREFIX + EnumChatFormatting.RED + "No saved GUI detected."));
        }
        Minecraft.getMinecraft().displayGuiScreen(latest);
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(
                VMod.PREFIX + "Reopened saved GUI."));
        latest = null;
    }
}
