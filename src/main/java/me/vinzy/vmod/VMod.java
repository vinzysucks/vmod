package me.vinzy.vmod;

import me.vinzy.vmod.commands.CommandVMod;
import me.vinzy.vmod.utils.ConfigFile;
import me.vinzy.vmod.utils.GuiUtils;
import me.vinzy.vmod.utils.PacketUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiScreenBook;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiEditSign;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = VMod.MODID, name = "VMod", version = VMod.VERSION, clientSideOnly = true)
public class VMod {
    public static final String MODID = "vmod";
    public static final String VERSION = "1.0";
    public static final Minecraft mc = Minecraft.getMinecraft();
    public static final String PREFIX = EnumChatFormatting.DARK_PURPLE + "[VMod] " + EnumChatFormatting.RESET;
    private static final int CLOSE_BUTTON_ID = 1420;
    private static final int TOGGLE_BLINK_BUTTON_ID = 1421;
    public static GuiButton toggleBlinkButton;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        ClientCommandHandler.instance.registerCommand(new CommandVMod());
        MinecraftForge.EVENT_BUS.register(this);
        ConfigFile.checkForConfig();
    }


    @SubscribeEvent
    public void onGuiInit(GuiScreenEvent.InitGuiEvent.Post event) {
        GuiScreen gui = event.gui;

        if (!(gui instanceof GuiContainer || gui instanceof GuiEditSign || gui instanceof GuiScreenBook)) {
            return;
        }

        int x = 10;
        int y = 10;

        event.buttonList.add(new GuiButton(CLOSE_BUTTON_ID, x, y, 110, 20, "Close Without Packet"));
        String blinkLabel = "Blink: " + (PacketUtils.isBlinkEnabled()
                ? EnumChatFormatting.GREEN + "Enabled"
                : EnumChatFormatting.RED + "Disabled");
        toggleBlinkButton = new GuiButton(TOGGLE_BLINK_BUTTON_ID, x, y + 25, 110, 20, blinkLabel);
        event.buttonList.add(toggleBlinkButton);
    }

    @SubscribeEvent
    public void onGuiButton(GuiScreenEvent.ActionPerformedEvent.Post event) {
        if (event.button.id == CLOSE_BUTTON_ID) {
            GuiUtils.saveAndCloseScreen();
        }

        if (event.button.id == TOGGLE_BLINK_BUTTON_ID) {
            PacketUtils.toggleBlink();
        }
    }
}