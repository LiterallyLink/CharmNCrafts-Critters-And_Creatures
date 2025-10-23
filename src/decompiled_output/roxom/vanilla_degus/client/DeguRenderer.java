package roxom.vanilla_degus.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_2960;
import net.minecraft.class_4587;
import net.minecraft.class_927;
import net.minecraft.class_5617.class_5618;
import roxom.vanilla_degus.common.DeguEntity;

@Environment(EnvType.CLIENT)
public class DeguRenderer extends class_927<DeguEntity, DeguModel> {
   private static final class_2960 DEGU_AGOUTI_LOCATION = new class_2960("vanilla_degus", "textures/entity/degu/agouti.png");
   private static final class_2960 DEGU_SAND_LOCATION = new class_2960("vanilla_degus", "textures/entity/degu/sand.png");
   private static final class_2960 DEGU_BLUE_LOCATION = new class_2960("vanilla_degus", "textures/entity/degu/blue.png");
   private static final class_2960 DEGU_AGOUTIPIED_LOCATION = new class_2960("vanilla_degus", "textures/entity/degu/agoutipied.png");
   private static final class_2960 DEGU_WHITE_LOCATION = new class_2960("vanilla_degus", "textures/entity/degu/white.png");
   private static final class_2960 DEGU_BLACK_LOCATION = new class_2960("vanilla_degus", "textures/entity/degu/black.png");

   public DeguRenderer(class_5618 context) {
      super(context, new DeguModel(), 0.3F);
      this.method_4046(new DeguCollarLayer(this));
   }

   public class_2960 getTexture(DeguEntity entity) {
      return switch (entity.getDeguType()) {
         case 1 -> DEGU_SAND_LOCATION;
         case 2 -> DEGU_BLUE_LOCATION;
         case 3 -> DEGU_AGOUTIPIED_LOCATION;
         case 4 -> DEGU_WHITE_LOCATION;
         case 5 -> DEGU_BLACK_LOCATION;
         default -> DEGU_AGOUTI_LOCATION;
      };
   }

   protected void scale(DeguEntity entity, class_4587 matrixStack, float f) {
      super.method_4042(entity, matrixStack, f);
      if (entity.getObesity() <= 5.0F) {
         matrixStack.method_22905(1.0F, 1.0F, 1.0F);
      } else if (entity.getObesity() <= 8.0F) {
         matrixStack.method_22905(1.1F, 1.0F, 1.0F);
      } else {
         matrixStack.method_22905(1.2F, 1.0F, 1.1F);
      }
   }
}
