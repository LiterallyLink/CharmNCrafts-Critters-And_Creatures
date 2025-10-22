package com.charmed.charmncraft.mixin;

import com.charmed.charmncraft.ModBlocks;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractFireBlock.class)
public class FireBlockMixin {

    @Inject(method = "isFlammable", at = @At("HEAD"), cancellable = true)
    private static void makeSnuffleFluffFlammable(BlockState state, CallbackInfoReturnable<Boolean> cir) {
        if (state.isOf(ModBlocks.SNUFFLE_FLUFF) ||
            state.isOf(ModBlocks.FROSTY_FLUFF) ||
            state.isOf(ModBlocks.SNUFFLE_FLUFF_CARPET) ||
            state.isOf(ModBlocks.FROSTY_FLUFF_CARPET)) {
            cir.setReturnValue(true);
        }
    }
}
