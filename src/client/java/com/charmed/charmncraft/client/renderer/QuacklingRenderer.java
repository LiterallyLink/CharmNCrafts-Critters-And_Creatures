package com.charmed.charmncraft.client.renderer;

import com.charmed.charmncraft.client.model.QuacklingModel;
import com.charmed.charmncraft.entity.QuacklingEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class QuacklingRenderer extends GeoEntityRenderer<QuacklingEntity> {
    public QuacklingRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new QuacklingModel());
        this.shadowRadius = 0.2f;
    }
}
