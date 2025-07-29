package me.vinzy.vmod.utils;

import me.vinzy.vmod.VMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import org.lwjgl.input.Keyboard;

public class ModuleManager {

    public static void handleKeybinding (int key) {
        GuiScreen gui = Minecraft.getMinecraft().currentScreen;
        if (!(gui instanceof GuiContainer || gui == null )) return;
        switch (key) {
            case Keyboard.KEY_B:
                if(VMod.toggleBlinkButton == null) {
                    break;
                }
                PacketUtils.toggleBlink();
                break;

            case Keyboard.KEY_F6:
                GuiUtils.saveAndCloseScreen();
            break;

            case Keyboard.KEY_V:
                GuiUtils.displayGUI();
            break;

            case Keyboard.KEY_G:
                GhostBlock.deleteblock();
            break;
        }

    }

}

