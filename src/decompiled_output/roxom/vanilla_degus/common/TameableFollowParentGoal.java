package roxom.vanilla_degus.common;

import net.minecraft.class_1321;
import net.minecraft.class_1353;

public class TameableFollowParentGoal extends class_1353 {
   protected final class_1321 entity;

   public TameableFollowParentGoal(class_1321 tameableEntity, double speed) {
      super(tameableEntity, speed);
      this.entity = tameableEntity;
   }

   public boolean method_6264() {
      return !this.entity.method_24345() && super.method_6264();
   }

   public boolean method_6266() {
      return !this.entity.method_24345() && super.method_6266();
   }
}
