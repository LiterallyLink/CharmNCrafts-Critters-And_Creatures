package com.charmed.charmncraft.client.model;

import com.charmed.charmncraft.entity.CaracalEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class CaracalModel extends GeoModel<CaracalEntity> {
    @Override
    public Identifier getModelResource(CaracalEntity entity) {
        return new Identifier("aqupd", "geo/caracal.geo.json");
    }

    @Override
    public Identifier getTextureResource(CaracalEntity entity) {
        return new Identifier("aqupd", "textures/entity/caracal.png");
    }

    @Override
    public Identifier getAnimationResource(CaracalEntity entity) {
        return new Identifier("aqupd", "animations/caracal.animation.json");
    }
}
