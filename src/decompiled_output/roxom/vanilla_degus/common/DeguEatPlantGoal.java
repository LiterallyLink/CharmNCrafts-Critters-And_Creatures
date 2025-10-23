package roxom.vanilla_degus.common;

import java.util.Arrays;
import java.util.List;
import net.minecraft.class_1314;
import net.minecraft.class_1367;
import net.minecraft.class_1937;
import net.minecraft.class_2246;
import net.minecraft.class_2248;
import net.minecraft.class_2320;
import net.minecraft.class_2338;
import net.minecraft.class_2756;
import net.minecraft.class_4538;

public class DeguEatPlantGoal extends class_1367 {
   protected final List<class_2248> targetBlocks = Arrays.asList(class_2246.field_10479, class_2246.field_10214, class_2246.field_10428);
   protected int passExecutionProb;
   protected class_1937 entityWorld;

   public DeguEatPlantGoal(class_1314 mob, double speed, int range, int passExecutionProb) {
      super(mob, speed, range, 2);
      this.passExecutionProb = passExecutionProb;
      this.entityWorld = mob.method_5770();
   }

   public boolean method_6264() {
      return this.field_6516.method_6051().method_43048(this.passExecutionProb) == 0 && super.method_6264();
   }

   protected boolean method_6296(class_4538 world, class_2338 pos) {
      class_2248 block = world.method_8320(pos).method_26204();
      boolean isLowerBlock;
      if (block instanceof class_2320) {
         isLowerBlock = world.method_8320(pos).method_11654(class_2320.field_10929) == class_2756.field_12607;
      } else {
         isLowerBlock = true;
      }

      return this.targetBlocks.contains(block) && isLowerBlock;
   }

   public void method_6268() {
      super.method_6268();
      if (this.method_6295()) {
         this.entityWorld.method_22352(this.field_6512, false);
         this.field_6516.method_5983();
      }
   }

   protected class_2338 method_30953() {
      return this.field_6512;
   }
}
