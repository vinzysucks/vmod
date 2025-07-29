package me.vinzy.vmod.utils;

import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import me.vinzy.vmod.VMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import java.util.LinkedList;

/**
 * Utilities for packets
 */


//Ty to doclic for this code

public class PacketUtils {
    private static final String PREFIX = EnumChatFormatting.DARK_PURPLE + "[VMod] " + EnumChatFormatting.RESET;
    private static boolean blinkEnabled = false;

    public static void toggleBlink() {
        blinkEnabled = !blinkEnabled;
        VMod.toggleBlinkButton.displayString = "Blink: " + (blinkEnabled
                ? EnumChatFormatting.GREEN + "Enabled"
                : EnumChatFormatting.RED + "Disabled");
        VMod.mc.thePlayer.addChatMessage(new ChatComponentText(
                VMod.PREFIX + "Blink " + (blinkEnabled ? "enabled" : "disabled")));
        if (blinkEnabled) {
            disableWriting();
        } else {
            enableWriting();
        }
    }

    public static boolean isBlinkEnabled() {
        return blinkEnabled;
    }

    /**
     * The Packet Disabler
     */
    private static final CustomPacketHandler PACKET_DISABLER = new CustomPacketHandler() {

        @Override
        public String getName() { return "packet_disabler"; }
        @Override
        public boolean onWrite(Object packet) {
            QUEUED_PACKETS.add((Packet<? extends INetHandler>) packet); return false; }
        @Override
        public boolean onRead(Object packet) { return true; }

    };
    /**
     * The queued outgoing (Server bound) packets
     * Check https://web.archive.org/web/20151222133335/https://wiki.vg/Protocol for more information
     */
    private static final LinkedList<Packet<? extends INetHandler>> QUEUED_PACKETS = new LinkedList<Packet<? extends INetHandler>>();

    /**
     * This function stops disable sending Packets to the server
     * They are still queued and will be all sent when {@link #enableWriting()} is called
     */
    public static void disableWriting() {

        // Getting the Minecraft instance
        final Minecraft minecraft = Minecraft.getMinecraft();

        // Registering the Packet Handler to the pipeline
        if (!addPacketHandler(PACKET_DISABLER)) {

            // Error message
            minecraft.thePlayer.addChatMessage(new ChatComponentText(PREFIX + EnumChatFormatting.RED + "Missing subcommand. Use /vza help"));
            return;

        }

        // Info

    }

    /**
     * This function enables writing Packets to the server
     * It also sends every queued Packets
     */
    public static void enableWriting() {

        // Getting variables
        final EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;

        // Removing the Packet Handler from the Pipeline
        if (!removePacketHandler(PACKET_DISABLER)) {

            // Error message
            player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Packet writing is already enabled!"));
            return;

        }

        // Info

        // Sending the Packets
        final int size = QUEUED_PACKETS.size();
        for (int i = 0; i < size; i++) {

            sendPacket(QUEUED_PACKETS.getFirst());
            QUEUED_PACKETS.removeFirst();

        }

    }

    /**
     * Converts a Packet to a String used in the /packetviewer command
     *
     * @param packet The packet you want to convert to a string
     * @return The String for the packet
     */
    public static String packetToString(Packet<? extends INetHandler> packet) {

        final String className = packet.getClass().getName();
        final String[] splitClassName = className.split("Packet");


        try { return splitClassName[1] + "ID: " + splitClassName[0].substring(1); }
        catch (ArrayIndexOutOfBoundsException e) { e.printStackTrace(); System.out.println(className); }
        return null;

    }

    /**
     * Sends a Packet to the server
     *
     * @param packet The Packet you're sending
     */
    public static void sendPacket(Packet<? extends INetHandler> packet) {

        Minecraft.getMinecraft().getNetHandler().addToSendQueue(packet);

    }

    /**
     * Adds a {@link CustomPacketHandler} to the pipeline
     *
     * @param packetHandler The Packet Handler you're adding
     * @return true if the {@link CustomPacketHandler} was registered
     */
    public static boolean addPacketHandler(final CustomPacketHandler packetHandler) {

        if (isPacketHandlerRegistered(packetHandler.getName())) return false;
        Minecraft.getMinecraft().getNetHandler().getNetworkManager().channel().pipeline().addBefore(
                "packet_handler",
                packetHandler.getName(),
                new ChannelDuplexHandler() {

                    @Override
                    public void write(ChannelHandlerContext context, Object packet, ChannelPromise promise) throws Exception {
                        if (packetHandler.onWrite(packet)) super.write(context, packet, promise); }
                    @Override
                    public void channelRead(ChannelHandlerContext context, Object packet) throws Exception {
                        if (packetHandler.onRead(packet)) super.channelRead(context, packet); }

                }
        );
        return true;

    }

    /**
     * Removes a {@link CustomPacketHandler} from the pipeline
     *
     * @param packetHandler The Packet Handler you're removing
     * @return true if the {@link CustomPacketHandler} was removed
     */
    public static boolean removePacketHandler(CustomPacketHandler packetHandler) { return removePacketHandler(packetHandler.getName()); }

    /**
     * Removes a {@link CustomPacketHandler} from the pipeline
     *
     * @param name The name of the Packet Handler you're removing
     * @return true if the {@link CustomPacketHandler} was removed
     */
    public static boolean removePacketHandler(final String name) {

        if (!isPacketHandlerRegistered(name)) return false;
        final Channel channel = Minecraft.getMinecraft().getNetHandler().getNetworkManager().channel();
        channel.eventLoop().submit(new Runnable() {
            @Override
            public void run() { channel.pipeline().remove(name); }
        });
        return true;

    }

    /**
     * Returns true if the {@link CustomPacketHandler} was already added
     *
     * @param packetHandler The Packet Handler you're checking
     * @return true if the {@link CustomPacketHandler} was already added
     */
    public static boolean isPacketHandlerRegistered(CustomPacketHandler packetHandler) { return isPacketHandlerRegistered(packetHandler.getName()); }

    /**
     * Returns true if the {@link CustomPacketHandler} was already added
     *
     * @param name The name of the {@link CustomPacketHandler}
     * @return true if the {@link CustomPacketHandler} was already added
     */
    public static boolean isPacketHandlerRegistered(String name) {

        return Minecraft.getMinecraft().getNetHandler().getNetworkManager().channel().pipeline().get(name) != null;

    }

    /**
     * Allows you to handle packets your own way
     */
    public interface CustomPacketHandler {

        /**
         * Returns the name of the {@link ChannelDuplexHandler} in the pipeline
         *
         * @return the name of the {@link ChannelDuplexHandler} in the pipeline
         */
        String getName();

        /**
         * Called when the client sends a Packet to the server
         *
         * @param packet The packet sent
         * @return true if the packet should be written by the game
         */
        boolean onWrite(Object packet);

        /**
         * Called when the server sends a Packet to the client
         *
         * @param packet The packet sent
         * @return true if the packet should be read by the game
         */
        boolean onRead(Object packet);

    }

}