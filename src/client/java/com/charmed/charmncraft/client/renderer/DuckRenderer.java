package com.charmed.charmncraft.client.renderer;

import com.charmed.charmncraft.client.model.DuckModel;
import com.charmed.charmncraft.entity.DuckEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class DuckRenderer extends GeoEntityRenderer<DuckEntity> {
    public DuckRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new DuckModel());
        this.shadowRadius = 0.3f;
    }
}
