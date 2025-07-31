package me.vinzy.vmod.utils;

import jdk.nashorn.internal.runtime.regexp.joni.Config;
import me.vinzy.vmod.VMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ChatComponentText;
import org.lwjgl.input.Keyboard;

public class ModuleManager {

    public static void handleKeybinding (int key) {
        GuiScreen gui = Minecraft.getMinecraft().currentScreen;
        if (!(gui instanceof GuiContainer || gui == null )) return;
        if(key == Keyboard.KEY_NONE) return;
        if (key == ConfigFile.blink_Key) {
            if (VMod.toggleBlinkButton == null) {
                return;
            }
            PacketUtils.toggleBlink();
        } else if (key == ConfigFile.saveGui_Key) {
            GuiUtils.saveAndCloseScreen();
        } else if (key == ConfigFile.ghostBlock_Key) {
            GhostBlock.deleteblock();
        } else if (key == ConfigFile.restoreGhosts_Key) {
            GhostBlock.restoreghosts();
        } else if (key == ConfigFile.reopenGui_Key){
            GuiUtils.displayGUI();
        } else if (key == ConfigFile.toggleTouch_Key) {
            Minecraft.getMinecraft().gameSettings.touchscreen = !Minecraft.getMinecraft().gameSettings.touchscreen;
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(
                    VMod.PREFIX + "Toggled touchscreen."));
        } else if (key == ConfigFile.printNbt_Key) {
            GuiUtils.printNBT();
        }

    }

}

