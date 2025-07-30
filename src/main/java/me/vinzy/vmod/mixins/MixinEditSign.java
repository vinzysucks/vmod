package me.vinzy.vmod.mixins;

import net.minecraft.client.gui.inventory.GuiEditSign;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;


@Mixin (GuiEditSign.class)
public class MixinEditSign {

    @ModifyConstant(method = "keyTyped", constant = @Constant(intValue = 90))
    private int removeSignLimit(int max) {
        return Integer.MAX_VALUE;
    }

}
