package me.vinzy.vmod.utils;

import me.vinzy.vmod.VMod;
import me.vinzy.vmod.commands.CommandVMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
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
                CommandVMod.toggleBlink();
                boolean blinkEnabled = CommandVMod.isBlinkEnabled();
                VMod.toggleBlinkButton.displayString = "Blink: " + (blinkEnabled
                        ? EnumChatFormatting.GREEN + "Enabled"
                        : EnumChatFormatting.RED + "Disabled");
                VMod.mc.thePlayer.addChatMessage(new ChatComponentText(
                        VMod.PREFIX + "Blink " + (blinkEnabled ? "enabled" : "disabled")));
                break;
        }

    }

}

