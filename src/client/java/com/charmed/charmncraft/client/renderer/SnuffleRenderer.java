package com.charmed.charmncraft.client.renderer;

import com.charmed.charmncraft.client.model.SnuffleEntityModel;
import com.charmed.charmncraft.entity.SnuffleEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class SnuffleRenderer extends MobEntityRenderer<SnuffleEntity, SnuffleEntityModel> {
    private static final Identifier[] TEXTURES = {
        new Identifier("snuffles", "textures/entity/snuffle/snuffle_default.png"),
        new Identifier("snuffles", "textures/entity/snuffle/snuffle_fluff.png"),
        new Identifier("snuffles", "textures/entity/snuffle/snuffle_horseshoe.png"),
        new Identifier("snuffles", "textures/entity/snuffle/snuffle_sheepdog.png"),
        new Identifier("snuffles", "textures/entity/snuffle/snuffle_poro.png"),
        new Identifier("snuffles", "textures/entity/snuffle/snuffle_fluff_horseshoe.png"),
        new Identifier("snuffles", "textures/entity/snuffle/snuffle_fluff_sheepdog.png"),
        new Identifier("snuffles", "textures/entity/snuffle/frosty_default.png"),
        new Identifier("snuffles", "textures/entity/snuffle/frosty_fluff.png"),
        new Identifier("snuffles", "textures/entity/snuffle/frosty_horseshoe.png"),
        new Identifier("snuffles", "textures/entity/snuffle/frosty_sheepdog.png"),
        new Identifier("snuffles", "textures/entity/snuffle/frosty_poro.png"),
        new Identifier("snuffles", "textures/entity/snuffle/frosty_fluff_horseshoe.png"),
        new Identifier("snuffles", "textures/entity/snuffle/frosty_fluff_sheepdog.png")
    };

    public SnuffleRenderer(EntityRendererFactory.Context context) {
        super(context, new SnuffleEntityModel(context.getPart(ModEntityModelLayers.SNUFFLE)), 0.5f);
    }

    @Override
    public Identifier getTexture(SnuffleEntity entity) {
        int variant = entity.getVariant();
        if (variant < 0 || variant >= TEXTURES.length) {
            variant = 0;
        }
        return TEXTURES[variant];
    }
}
