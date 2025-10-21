package com.charmed.charmncraft.client.model;

import com.charmed.charmncraft.entity.QuacklingEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class QuacklingModel extends GeoModel<QuacklingEntity> {
    @Override
    public Identifier getModelResource(QuacklingEntity entity) {
        return new Identifier("duckling", "geo/quackling.geo.json");
    }

    @Override
    public Identifier getTextureResource(QuacklingEntity entity) {
        return new Identifier("duckling", "textures/entity/quackling.png");
    }

    @Override
    public Identifier getAnimationResource(QuacklingEntity entity) {
        return new Identifier("duckling", "animations/quackling.animation.json");
    }
}
