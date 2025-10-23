package roxom.vanilla_degus.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_2960;
import net.minecraft.class_3883;
import net.minecraft.class_3887;
import net.minecraft.class_4587;
import net.minecraft.class_4597;
import roxom.vanilla_degus.common.DeguEntity;

@Environment(EnvType.CLIENT)
public class DeguCollarLayer extends class_3887<DeguEntity, DeguModel> {
   private static final class_2960 DEGU_COLLAR = new class_2960("vanilla_degus", "textures/entity/degu/degu_collar.png");

   public DeguCollarLayer(class_3883<DeguEntity, DeguModel> rendererIn) {
      super(rendererIn);
   }

   public void render(
      class_4587 matrixStack,
      class_4597 provider,
      int packedLightIn,
      DeguEntity deguEntity,
      float limbSwing,
      float limbSwingAmount,
      float partialTicks,
      float ageInTicks,
      float netHeadYaw,
      float headPitch
   ) {
      if (deguEntity.method_6181() && !deguEntity.method_5767()) {
         float[] afloat = deguEntity.getCollarColor().method_7787();
         method_23199(this.method_17165(), DEGU_COLLAR, matrixStack, provider, packedLightIn, deguEntity, afloat[0], afloat[1], afloat[2]);
      }
   }
}
