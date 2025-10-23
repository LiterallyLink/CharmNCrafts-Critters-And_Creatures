package roxom.vanilla_degus.common;

import net.minecraft.class_1309;
import net.minecraft.class_1321;
import net.minecraft.class_1338;
import net.minecraft.class_243;
import net.minecraft.class_4051;
import net.minecraft.class_5532;

public class AvoidFightingGoal extends class_1338<class_1309> {
   protected class_1321 tameable;

   public AvoidFightingGoal(class_1321 tameable, float distance, double slowSpeed, double fastSpeed) {
      super(tameable, class_1309.class, distance, slowSpeed, fastSpeed);
      this.tameable = tameable;
   }

   public boolean method_6264() {
      if (this.tameable.method_24345()) {
         return false;
      } else {
         if (this.tameable.method_6181()) {
            class_1309 owner = this.tameable.method_35057();
            if (owner == null) {
               return false;
            }

            if (owner.method_6065() != null && owner.field_6012 - owner.method_6117() < 40) {
               this.field_6390 = owner.method_6065();
            } else if (owner.method_6052() != null && owner.field_6012 - owner.method_6083() < 40) {
               this.field_6390 = owner.method_6052();
            }
         } else {
            class_4051 predicate = class_4051.method_36626().method_18418((double)this.field_6386).method_18420(degu -> degu.method_6065() != null);
            class_1309 otherDegu = this.field_6391
               .method_37908()
               .method_18468(
                  this.field_6391
                     .method_37908()
                     .method_8390(
                        DeguEntity.class,
                        this.field_6391.method_5829().method_1009((double)this.field_6386, 3.0, (double)this.field_6386),
                        livingEntity -> true
                     ),
                  predicate,
                  this.field_6391,
                  this.field_6391.method_23317(),
                  this.field_6391.method_23318(),
                  this.field_6391.method_23321()
               );
            if (otherDegu != null) {
               this.field_6390 = otherDegu.method_6065();
            }
         }

         if (this.field_6390 == null) {
            return false;
         } else if (this.field_6390.method_5858(this.field_6391) > Math.pow((double)this.field_6386, 2.0)) {
            return false;
         } else {
            class_243 vec3d = class_5532.method_31511(this.field_6391, (int)this.field_6386, 5, this.field_6390.method_19538());
            if (vec3d == null) {
               return false;
            } else if (this.field_6390.method_5649(vec3d.field_1352, vec3d.field_1351, vec3d.field_1350) < this.field_6390.method_5858(this.field_6391)) {
               return false;
            } else {
               this.field_6387 = this.field_6394.method_6352(vec3d.field_1352, vec3d.field_1351, vec3d.field_1350, 0);
               return this.field_6387 != null;
            }
         }
      }
   }
}
