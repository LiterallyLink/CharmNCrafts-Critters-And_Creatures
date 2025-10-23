package roxom.vanilla_degus.mixin;

import net.minecraft.class_1792;
import net.minecraft.class_4174;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({class_1792.class})
public interface ItemAccessor {
   @Mutable
   @Accessor("foodComponent")
   void setFoodComponent(class_4174 var1);
}
