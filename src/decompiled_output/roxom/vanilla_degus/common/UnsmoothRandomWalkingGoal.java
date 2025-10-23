package roxom.vanilla_degus.common;

import net.minecraft.class_1314;
import net.minecraft.class_243;

public class UnsmoothRandomWalkingGoal extends ShortRandomWalkingGoal {
   protected boolean isReached;
   protected int currentWalkingCount;
   protected int maxWalkingCount;
   protected float idleTimer;

   public UnsmoothRandomWalkingGoal(class_1314 creature, double speedIn, int passProb) {
      super(creature, speedIn, passProb);
   }

   public boolean method_6266() {
      return this.currentWalkingCount < this.maxWalkingCount && !this.field_6566.method_5782();
   }

   public void method_6269() {
      this.currentWalkingCount = 0;
      this.setIdleTimer();
      this.maxWalkingCount = Math.abs((int)(this.field_6566.method_6051().method_43059() * 4.0)) + 1;
      super.method_6269();
   }

   public void method_6268() {
      this.isReached = this.field_6566.method_5942().method_6357();
      if (this.isReached) {
         this.idleTimer--;
         if (this.idleTimer < 0.0F) {
            this.setIdleTimer();
            this.currentWalkingCount++;
            class_243 newPosition = this.method_6302();
            if (newPosition != null) {
               this.field_6563 = newPosition.field_1352;
               this.field_6562 = newPosition.field_1351;
               this.field_6561 = newPosition.field_1350;
               this.field_6566.method_5942().method_6337(this.field_6563, this.field_6562, this.field_6561, this.field_6567);
            }
         }
      }
   }

   protected void setIdleTimer() {
      this.idleTimer = this.field_6566.method_6051().method_43057() * 3.0F + 10.0F;
   }
}
