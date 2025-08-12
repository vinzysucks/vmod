package me.vinzy.vmod.utils;

import me.vinzy.vmod.VMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiEditSign;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.ChatComponentText;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.lang.reflect.Field;

public class ModuleManager {

    public static void handleKeybinding (int key) {
        handleModuleToggle(key);
        if(key == Keyboard.KEY_NONE) return;
        if (key == Keyboard.KEY_V && Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
            GuiScreen currentScreen = Minecraft.getMinecraft().currentScreen;
            if (currentScreen instanceof GuiEditSign) {
                GuiEditSign signGui = (GuiEditSign) currentScreen;
                try {
                    Field titleSignField = signGui.getClass().getDeclaredField("field_146848_f"); //tileSign
                    Field editLineField = signGui.getClass().getDeclaredField("field_146851_h"); //editLine
                    titleSignField.setAccessible(true);
                    editLineField.setAccessible(true);
                    String lineContent = ((TileEntitySign) titleSignField.get(signGui)).signText[(int) editLineField.get(signGui)].getFormattedText();
                    String clipboard = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
                    ((TileEntitySign) titleSignField.get(signGui)).signText[(int) editLineField.get(signGui)] = new ChatComponentText(lineContent + clipboard);
                } catch (NoSuchFieldException | IllegalAccessException | IOException |
                         UnsupportedFlavorException e) {
                    throw new RuntimeException(e);
                }
            }

        }

    }

    public static void handleModuleToggle(int key) {
        GuiScreen gui = Minecraft.getMinecraft().currentScreen;
        if (!(gui instanceof GuiContainer || gui == null )) return;
        if (key == ConfigFile.blink_Key) {
            if (VMod.toggleBlinkButton == null) {
                return;
            }
            PacketUtils.toggleBlink();
        } if (key == ConfigFile.saveGui_Key) {
            GuiUtils.saveAndCloseScreen();
        } if (key == ConfigFile.ghostBlock_Key) {
            GhostBlock.deleteblock();
        } if (key == ConfigFile.restoreGhosts_Key) {
            GhostBlock.restoreghosts();
        } if (key == ConfigFile.reopenGui_Key){
            GuiUtils.displayGUI();
        } if (key == ConfigFile.toggleTouch_Key) {
            Minecraft.getMinecraft().gameSettings.touchscreen = !Minecraft.getMinecraft().gameSettings.touchscreen;
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(
                    VMod.PREFIX + "Toggled touchscreen."));
        } if (key == ConfigFile.printNbt_Key) {
            GuiUtils.printNBT();
        }
    }
}

