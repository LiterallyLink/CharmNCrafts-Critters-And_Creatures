package com.charmed.charmncraft.client.renderer;

import com.charmed.charmncraft.client.model.SnuffleModel;
import com.charmed.charmncraft.entity.SnuffleEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SnuffleRenderer extends GeoEntityRenderer<SnuffleEntity> {
    public SnuffleRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new SnuffleModel());
        this.shadowRadius = 0.5f;
    }
}
