package com.charmed.charmncraft.client.model;

import com.charmed.charmncraft.entity.RedPandaEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class RedPandaModel extends GeoModel<RedPandaEntity> {
    @Override
    public Identifier getModelResource(RedPandaEntity entity) {
        return new Identifier("ydms_redpanda", "geo/redpanda.geo.json");
    }

    @Override
    public Identifier getTextureResource(RedPandaEntity entity) {
        return new Identifier("ydms_redpanda", "textures/entity/redpanda.png");
    }

    @Override
    public Identifier getAnimationResource(RedPandaEntity entity) {
        return new Identifier("ydms_redpanda", "animations/redpanda.animation.json");
    }
}
