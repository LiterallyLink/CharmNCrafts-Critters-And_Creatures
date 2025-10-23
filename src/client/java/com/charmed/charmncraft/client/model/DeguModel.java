package com.charmed.charmncraft.client.model;

import com.charmed.charmncraft.entity.DeguEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class DeguModel extends GeoModel<DeguEntity> {
    @Override
    public Identifier getModelResource(DeguEntity entity) {
        return new Identifier("vanilla_degus", "geo/degu.geo.json");
    }

    @Override
    public Identifier getTextureResource(DeguEntity entity) {
        String textureName = entity.getTextureName();
        return new Identifier("vanilla_degus", "textures/entity/degu/" + textureName + ".png");
    }

    @Override
    public Identifier getAnimationResource(DeguEntity entity) {
        return new Identifier("vanilla_degus", "animations/degu.animation.json");
    }
}
