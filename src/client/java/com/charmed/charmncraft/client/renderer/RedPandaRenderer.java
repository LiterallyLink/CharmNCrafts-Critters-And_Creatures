package com.charmed.charmncraft.client.renderer;

import com.charmed.charmncraft.client.model.RedPandaModel;
import com.charmed.charmncraft.entity.RedPandaEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class RedPandaRenderer extends GeoEntityRenderer<RedPandaEntity> {
    public RedPandaRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new RedPandaModel());
        this.shadowRadius = 0.4f;
    }
}
