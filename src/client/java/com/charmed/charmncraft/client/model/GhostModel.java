package com.charmed.charmncraft.client.model;

import com.charmed.charmncraft.entity.GhostEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class GhostModel extends GeoModel<GhostEntity> {
    @Override
    public Identifier getModelResource(GhostEntity entity) {
        return new Identifier("ghosts", "geo/ghost.geo.json");
    }

    @Override
    public Identifier getTextureResource(GhostEntity entity) {
        return new Identifier("ghosts", "textures/entity/ghost.png");
    }

    @Override
    public Identifier getAnimationResource(GhostEntity entity) {
        return new Identifier("ghosts", "animations/ghost.animation.json");
    }
}
