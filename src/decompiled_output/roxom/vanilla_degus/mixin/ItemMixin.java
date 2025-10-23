package roxom.vanilla_degus.mixin;

import java.util.Arrays;
import net.minecraft.class_1268;
import net.minecraft.class_1271;
import net.minecraft.class_1657;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_1937;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import roxom.vanilla_degus.DeguMod;

@Mixin({class_1792.class})
abstract class ItemMixin {
   @Inject(
      method = {"use"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void useInjected(class_1937 world, class_1657 user, class_1268 hand, CallbackInfoReturnable<class_1271<class_1799>> cir) {
      boolean isGrass = Arrays.asList(class_1802.field_8602, class_1802.field_8256, class_1802.field_8689).contains((class_1792)this);
      if (isGrass && !user.method_6059(DeguMod.DEGUS_GRACE_EFFECT)) {
         cir.setReturnValue(class_1271.method_22430(user.method_5998(hand)));
      }
   }
}
