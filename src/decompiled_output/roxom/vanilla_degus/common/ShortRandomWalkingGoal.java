package roxom.vanilla_degus.common;

import net.minecraft.class_1314;
import net.minecraft.class_1379;
import net.minecraft.class_243;
import net.minecraft.class_5534;

public class ShortRandomWalkingGoal extends class_1379 {
   protected final float selectDefaultGoalProbability = 0.001F;
   protected final int maxXZ = 2;
   protected final int maxY = 7;

   public ShortRandomWalkingGoal(class_1314 creature, double speedIn) {
      this(creature, speedIn, 60);
   }

   public ShortRandomWalkingGoal(class_1314 creature, double speedIn, int passProb) {
      super(creature, speedIn, passProb);
   }

   protected class_243 method_6302() {
      if (this.field_6566.method_5816()) {
         class_243 vector3d = class_5534.method_31527(this.field_6566, 15, 7);
         return vector3d == null ? super.method_6302() : vector3d;
      } else {
         boolean isSelectingLand = this.field_6566.method_6051().method_43057() >= 0.001F;
         return isSelectingLand ? class_5534.method_31527(this.field_6566, 2, 7) : super.method_6302();
      }
   }
}
