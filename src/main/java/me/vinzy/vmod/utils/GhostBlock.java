package me.vinzy.vmod.utils;

import me.vinzy.vmod.VMod;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MovingObjectPosition;

import java.util.HashMap;
import java.util.Map;

public class GhostBlock {
    private static Map<BlockPos, IBlockState> ghosts = new HashMap<>();

    public static void deleteblock () {
        MovingObjectPosition movingObjectPosition = Minecraft.getMinecraft().thePlayer.rayTrace(100.0, 0f);
        BlockPos pos = movingObjectPosition.getBlockPos();
        IBlockState state = Minecraft.getMinecraft().theWorld.getBlockState(pos);
        ghosts.put(pos, state);
        Minecraft.getMinecraft().thePlayer.swingItem();
        Minecraft.getMinecraft().theWorld.setBlockToAir(pos);
    }

    public static void restoreghosts() {
        for (BlockPos key : ghosts.keySet()) {
            Minecraft.getMinecraft().theWorld.setBlockState(key, ghosts.get(key));
        }
        ghosts.clear();
        VMod.mc.thePlayer.addChatMessage(new ChatComponentText(VMod.PREFIX + "Restored ghost blocks."));
    }

}
