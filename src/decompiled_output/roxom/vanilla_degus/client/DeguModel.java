package roxom.vanilla_degus.client;

import com.google.common.collect.ImmutableList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_3532;
import net.minecraft.class_4592;
import net.minecraft.class_5603;
import net.minecraft.class_5606;
import net.minecraft.class_5607;
import net.minecraft.class_5609;
import net.minecraft.class_5610;
import net.minecraft.class_630;
import roxom.vanilla_degus.common.DeguEntity;

@Environment(EnvType.CLIENT)
public class DeguModel extends class_4592<DeguEntity> {
   private final class_630 root = getTexturedModelData().method_32109();
   private final class_630 back_left_leg;
   private final class_630 back_right_leg;
   private final class_630 front_left_leg;
   private final class_630 front_right_leg;
   private final class_630 tail;
   private final class_630 tail2;
   private final class_630 head = this.root.method_32086("head");
   private final class_630 body = this.root.method_32086("body");
   private static final int texWidth = 32;
   private static final int texHeight = 32;

   public DeguModel() {
      super(true, 17.0F, 0.6F, 2.5F, 2.0F, 24.0F);
      this.tail = this.root.method_32086("tail");
      this.tail2 = this.root.method_32086("tail2");
      this.back_left_leg = this.root.method_32086("left_hind_leg");
      this.back_right_leg = this.root.method_32086("right_hind_leg");
      this.front_left_leg = this.root.method_32086("left_front_leg");
      this.front_right_leg = this.root.method_32086("right_front_leg");
   }

   public static class_5607 getTexturedModelData() {
      class_5609 modelData = new class_5609();
      class_5610 root = modelData.method_32111();
      root.method_32117(
         "left_hind_leg",
         class_5606.method_32108()
            .method_32101(21, 11)
            .method_32100(-0.65F, 2.0F, 0.0F, 1.0F, 2.0F, 1.0F, false)
            .method_32101(23, 0)
            .method_32100(-0.65F, 3.0F, -1.0F, 1.0F, 1.0F, 1.0F, false)
            .method_32101(8, 19)
            .method_32100(-0.5F, -0.4F, -1.5F, 1.0F, 3.0F, 3.0F, false),
         class_5603.method_32090(2.9F, 20.0F, 2.0F)
      );
      root.method_32117(
         "right_hind_leg",
         class_5606.method_32108()
            .method_32101(0, 18)
            .method_32100(-0.5F, -0.4F, -1.5F, 1.0F, 3.0F, 3.0F, false)
            .method_32101(0, 12)
            .method_32100(-0.35F, 2.0F, 0.0F, 1.0F, 2.0F, 1.0F, false)
            .method_32101(4, 24)
            .method_32100(-0.35F, 3.0F, -1.0F, 1.0F, 1.0F, 1.0F, false),
         class_5603.method_32090(-2.9F, 20.0F, 2.0F)
      );
      root.method_32117(
         "left_front_leg",
         class_5606.method_32108()
            .method_32101(20, 22)
            .method_32100(-0.55F, 0.0F, -1.0F, 1.0F, 2.0F, 1.0F, false)
            .method_32101(12, 12)
            .method_32100(-1.55F, 2.0F, -2.0F, 3.0F, 0.0F, 2.0F, false),
         class_5603.method_32090(1.8F, 22.0F, -2.0F)
      );
      root.method_32117(
         "right_front_leg",
         class_5606.method_32108()
            .method_32101(22, 5)
            .method_32100(-0.45F, 0.0F, -1.0F, 1.0F, 2.0F, 1.0F, false)
            .method_32101(15, 0)
            .method_32100(-1.45F, 2.0F, -2.0F, 3.0F, 0.0F, 2.0F, false),
         class_5603.method_32090(-1.8F, 22.0F, -2.0F)
      );
      root.method_32117(
         "tail",
         class_5606.method_32108().method_32101(16, 22).method_32100(-0.5F, 0.1F, 0.0F, 1.0F, 4.0F, 1.0F, false),
         class_5603.method_32091(0.0F, 20.5F, 4.0F, 0.9F, 0.0F, 0.0F)
      );
      class_5610 modelTail2 = root.method_32117(
         "tail2",
         class_5606.method_32108().method_32101(0, 0).method_32100(-0.5F, 0.0F, -1.0F, 1.0F, 4.0F, 1.0F, false),
         class_5603.method_32090(0.0F, 22.0F, 7.0F)
      );
      modelTail2.method_32117(
         "tail3",
         class_5606.method_32108().method_32101(21, 2).method_32100(-0.5F, 0.0F, -1.0F, 1.0F, 2.0F, 1.0F, false),
         class_5603.method_32090(0.0F, 4.0F, 0.0F)
      );
      root.method_32117(
         "head",
         class_5606.method_32108()
            .method_32101(14, 14)
            .method_32100(-2.0F, -1.4F, -4.0F, 4.0F, 4.0F, 4.0F, false)
            .method_32101(14, 14)
            .method_32100(-0.5F, 0.75F, -4.3F, 1.0F, 1.0F, 1.0F, false)
            .method_32101(0, 24)
            .method_32100(-1.25F, -3.25F, -1.0F, 1.0F, 2.0F, 1.0F, false)
            .method_32101(5, 18)
            .method_32100(-2.0F, -3.25F, -1.0F, 1.0F, 2.0F, 1.0F, false)
            .method_32101(22, 8)
            .method_32100(0.25F, -3.25F, -1.0F, 1.0F, 2.0F, 1.0F, false)
            .method_32101(17, 2)
            .method_32100(1.0F, -3.25F, -1.0F, 1.0F, 2.0F, 1.0F, false),
         class_5603.method_32090(0.0F, 19.0F, -4.0F)
      );
      class_5610 modelBody = root.method_32117("body", class_5606.method_32108(), class_5603.method_32090(0.0F, 19.0F, -1.9F));
      modelBody.method_32117(
         "body",
         class_5606.method_32108()
            .method_32101(0, 12)
            .method_32100(-3.25F, 1.9F, -2.1F, 5.0F, 2.0F, 4.0F, false)
            .method_32101(0, 0)
            .method_32100(-3.75F, -4.1F, -2.5F, 6.0F, 7.0F, 5.0F, false),
         class_5603.method_32091(0.75F, 1.0F, 2.0F, 1.5708F, 0.0F, 0.0F)
      );
      return class_5607.method_32110(modelData, 32, 32);
   }

   protected Iterable<class_630> method_22946() {
      return ImmutableList.of(this.head);
   }

   protected Iterable<class_630> method_22948() {
      return ImmutableList.of(this.body, this.back_left_leg, this.back_right_leg, this.front_left_leg, this.front_right_leg, this.tail, this.tail2);
   }

   public void setAngles(DeguEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
      this.head.field_3654 = headPitch * (float) (Math.PI / 180.0);
      this.head.field_3675 = headYaw * 0.008726646F;
      switch (getPose(entity)) {
         case DANCING:
            float bpm = entity.recordListener.getCurrentRecordTempo();
            float f = class_3532.method_15362((float)entity.field_6012 * (float) Math.PI * 0.1F * bpm / 60.0F) / 2.0F;
            this.body.field_3656 = 17.0F + f;
            this.head.field_3656 = 14.0F + f;
            this.front_left_leg.field_3656 = 17.5F + f;
            this.front_right_leg.field_3656 = 17.5F + f;
            this.tail.field_3656 = 20.5F + f;
            this.tail2.field_3656 = 22.0F + f;
         case SITTING:
         default:
            break;
         case STANDING:
            this.back_left_leg.field_3654 = class_3532.method_15362(limbAngle * 0.6662F) * limbDistance;
            this.back_right_leg.field_3654 = class_3532.method_15362(limbAngle * 0.6662F + 0.3F) * limbDistance;
            this.front_left_leg.field_3654 = class_3532.method_15362(limbAngle * 0.6662F + (float) Math.PI + 0.3F) * limbDistance;
            this.front_right_leg.field_3654 = class_3532.method_15362(limbAngle * 0.6662F + (float) Math.PI) * limbDistance;
            this.tail2.field_3654 = 1.7278761F + (float) (Math.PI / 10) * class_3532.method_15362(limbAngle) * limbDistance;
      }
   }

   public void animateModel(DeguEntity entity, float limbAngle, float limbDistance, float tickDelta) {
      this.tail.field_3656 = 20.5F;
      this.tail2.field_3656 = 22.0F;
      this.tail2.field_3655 = 7.0F;
      this.tail.field_3654 = 0.9F;
      switch (getPose(entity)) {
         case DANCING:
         case SITTING:
            this.body.field_3656 = 17.0F;
            this.body.field_3655 = 1.0F;
            this.body.field_3654 = -1.0F;
            this.head.field_3656 = 14.0F;
            this.head.field_3655 = 0.0F;
            this.back_left_leg.field_3655 = 1.0F;
            this.back_right_leg.field_3655 = 1.0F;
            this.front_left_leg.field_3656 = 17.5F;
            this.front_left_leg.field_3655 = -1.5F;
            this.front_right_leg.field_3656 = 17.5F;
            this.front_right_leg.field_3655 = -1.5F;
            this.back_left_leg.field_3654 = 0.0F;
            this.back_right_leg.field_3654 = 0.0F;
            this.front_left_leg.field_3654 = 0.0F;
            this.front_right_leg.field_3654 = 0.0F;
            this.tail2.field_3654 = 1.7278761F;
            break;
         case STANDING:
            this.body.field_3656 = 19.0F;
            this.body.field_3655 = -1.9F;
            this.body.field_3654 = 0.0F;
            this.head.field_3656 = 19.0F;
            this.head.field_3655 = -4.0F;
            this.back_left_leg.field_3655 = 2.0F;
            this.back_right_leg.field_3655 = 2.0F;
            this.front_left_leg.field_3656 = 22.0F;
            this.front_left_leg.field_3655 = -2.0F;
            this.front_right_leg.field_3656 = 22.0F;
            this.front_right_leg.field_3655 = -2.0F;
      }
   }

   private static DeguModel.Pose getPose(DeguEntity degu) {
      if (degu.isDancing()) {
         return DeguModel.Pose.DANCING;
      } else {
         return degu.method_6172() ? DeguModel.Pose.SITTING : DeguModel.Pose.STANDING;
      }
   }

   @Environment(EnvType.CLIENT)
   public static enum Pose {
      STANDING,
      SITTING,
      DANCING;
   }
}
