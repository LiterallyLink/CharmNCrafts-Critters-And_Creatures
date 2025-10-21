package com.charmed.charmncraft.client.model;

import com.charmed.charmncraft.entity.DuckEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class DuckModel extends GeoModel<DuckEntity> {
    @Override
    public Identifier getModelResource(DuckEntity entity) {
        return new Identifier("duckling", "geo/duck.geo.json");
    }

    @Override
    public Identifier getTextureResource(DuckEntity entity) {
        return new Identifier("duckling", "textures/entity/duck.png");
    }

    @Override
    public Identifier getAnimationResource(DuckEntity entity) {
        return new Identifier("duckling", "animations/duck.animation.json");
    }
}
