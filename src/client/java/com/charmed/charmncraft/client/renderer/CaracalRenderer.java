package com.charmed.charmncraft.client.renderer;

import com.charmed.charmncraft.client.model.CaracalModel;
import com.charmed.charmncraft.entity.CaracalEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class CaracalRenderer extends GeoEntityRenderer<CaracalEntity> {
    public CaracalRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new CaracalModel());
        this.shadowRadius = 0.4f;
    }
}
