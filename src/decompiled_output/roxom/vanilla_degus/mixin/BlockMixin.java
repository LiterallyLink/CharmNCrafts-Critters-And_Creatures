package roxom.vanilla_degus.mixin;

import net.minecraft.class_1657;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_1937;
import net.minecraft.class_2246;
import net.minecraft.class_2248;
import net.minecraft.class_2338;
import net.minecraft.class_2680;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import roxom.vanilla_degus.DeguMod;

@Mixin({class_2248.class})
abstract class BlockMixin {
   @Inject(
      method = {"onBreak"},
      at = {@At("TAIL")}
   )
   private void onBreak(class_1937 world, class_2338 pos, class_2680 state, class_1657 player, CallbackInfo info) {
      if (player.method_6059(DeguMod.DEGUS_GRACE_EFFECT)) {
         if (state.method_27852(class_2246.field_10479) || state.method_27852(class_2246.field_10214) || state.method_27852(class_2246.field_10428)) {
            player.method_7344().method_7579(class_1802.field_8602, new class_1799(class_1802.field_8602));
         }
      }
   }
}
