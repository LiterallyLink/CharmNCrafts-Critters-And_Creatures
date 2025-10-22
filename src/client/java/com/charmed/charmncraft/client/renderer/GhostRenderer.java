package com.charmed.charmncraft.client.renderer;

import com.charmed.charmncraft.client.model.GhostModel;
import com.charmed.charmncraft.entity.GhostEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class GhostRenderer extends GeoEntityRenderer<GhostEntity> {
    public GhostRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new GhostModel());
        this.shadowRadius = 0.4f;
    }
}
