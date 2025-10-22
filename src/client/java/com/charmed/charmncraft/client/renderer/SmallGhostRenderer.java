package com.charmed.charmncraft.client.renderer;

import com.charmed.charmncraft.client.model.SmallGhostModel;
import com.charmed.charmncraft.entity.SmallGhostEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SmallGhostRenderer extends GeoEntityRenderer<SmallGhostEntity> {
    public SmallGhostRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new SmallGhostModel());
        this.shadowRadius = 0.3f;
    }
}
