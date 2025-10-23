package com.charmed.charmncraft.client.renderer;

import com.charmed.charmncraft.client.model.DeguModel;
import com.charmed.charmncraft.entity.DeguEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class DeguRenderer extends GeoEntityRenderer<DeguEntity> {
    public DeguRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new DeguModel());
        this.shadowRadius = 0.3f;

        // Add collar layer for tamed degus
        addRenderLayer(new CollarRenderLayer(this));
    }

    // Collar render layer
    private static class CollarRenderLayer extends GeoRenderLayer<DeguEntity> {
        private static final Identifier COLLAR_TEXTURE = new Identifier("vanilla_degus", "textures/entity/degu/degu_collar.png");

        public CollarRenderLayer(GeoEntityRenderer<DeguEntity> entityRendererIn) {
            super(entityRendererIn);
        }

        @Override
        public void render(MatrixStack poseStack, DeguEntity animatable, BakedGeoModel bakedModel,
                         RenderLayer renderType, VertexConsumerProvider bufferSource, VertexConsumer buffer,
                         float partialTick, int packedLight, int packedOverlay) {
            // Only render collar if tamed
            if (animatable.isTamed()) {
                RenderLayer collarRenderType = RenderLayer.getEntityCutoutNoCull(COLLAR_TEXTURE);

                // Get the collar color from the entity and apply it
                float[] colorComponents = animatable.getCollarColor().getColorComponents();

                this.getRenderer().reRender(bakedModel, poseStack, bufferSource, animatable, collarRenderType,
                        bufferSource.getBuffer(collarRenderType), partialTick, packedLight, packedOverlay,
                        colorComponents[0], colorComponents[1], colorComponents[2], 1.0f);
            }
        }
    }
}
