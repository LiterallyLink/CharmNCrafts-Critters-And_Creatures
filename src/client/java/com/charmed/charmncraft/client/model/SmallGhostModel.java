package com.charmed.charmncraft.client.model;

import com.charmed.charmncraft.entity.SmallGhostEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class SmallGhostModel extends GeoModel<SmallGhostEntity> {
    @Override
    public Identifier getModelResource(SmallGhostEntity entity) {
        return new Identifier("ghosts", "geo/small_ghost.geo.json");
    }

    @Override
    public Identifier getTextureResource(SmallGhostEntity entity) {
        return new Identifier("ghosts", "textures/entity/small_ghost.png");
    }

    @Override
    public Identifier getAnimationResource(SmallGhostEntity entity) {
        return new Identifier("ghosts", "animations/small_ghost.animation.json");
    }
}
