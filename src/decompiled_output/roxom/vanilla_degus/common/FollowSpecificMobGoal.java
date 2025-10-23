package roxom.vanilla_degus.common;

import java.lang.reflect.Field;
import java.util.function.Predicate;
import net.minecraft.class_1308;
import net.minecraft.class_1348;

public class FollowSpecificMobGoal extends class_1348 {
   protected class_1308 mobEntity;
   protected int passExecutionProb;

   public FollowSpecificMobGoal(
      class_1308 mobEntity, Class<? extends class_1308> specificMobClass, double speed, float stopDistance, float areaSize, int passProb
   ) {
      super(mobEntity, speed, stopDistance, areaSize);
      this.mobEntity = mobEntity;
      this.passExecutionProb = passProb;

      try {
         Field field = this.getClass().getSuperclass().getDeclaredField("targetPredicate");
         field.setAccessible(true);
         Predicate<class_1308> followPredicate = entity -> specificMobClass == entity.getClass();
         field.set(this, followPredicate);
      } catch (IllegalAccessException | NoSuchFieldException var10) {
      }
   }

   public boolean method_6264() {
      return this.mobEntity.method_6051().method_43048(this.passExecutionProb) == 0 && super.method_6264();
   }
}
