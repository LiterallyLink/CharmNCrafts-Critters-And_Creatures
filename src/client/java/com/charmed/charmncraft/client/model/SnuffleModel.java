package com.charmed.charmncraft.client.model;

import com.charmed.charmncraft.entity.SnuffleEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class SnuffleModel extends GeoModel<SnuffleEntity> {
    @Override
    public Identifier getModelResource(SnuffleEntity entity) {
        return new Identifier("snuffles", "geo/snuffle.geo.json");
    }

    @Override
    public Identifier getTextureResource(SnuffleEntity entity) {
        int variant = entity.getVariant();
        String[] variants = {
            "snuffle_default",
            "snuffle_fluff",
            "snuffle_horseshoe",
            "snuffle_sheepdog",
            "snuffle_poro",
            "snuffle_fluff_horseshoe",
            "snuffle_fluff_sheepdog",
            "frosty_default",
            "frosty_fluff",
            "frosty_horseshoe",
            "frosty_sheepdog",
            "frosty_poro",
            "frosty_fluff_horseshoe",
            "frosty_fluff_sheepdog"
        };

        String textureName = (variant >= 0 && variant < variants.length)
            ? variants[variant]
            : "snuffle_default";

        return new Identifier("snuffles", "textures/entity/snuffle/" + textureName + ".png");
    }

    @Override
    public Identifier getAnimationResource(SnuffleEntity entity) {
        return new Identifier("snuffles", "animations/snuffle.animation.json");
    }
}
