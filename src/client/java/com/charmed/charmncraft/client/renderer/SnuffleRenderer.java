package com.charmed.charmncraft.client.renderer;

import com.charmed.charmncraft.client.model.SnuffleModel;
import com.charmed.charmncraft.entity.SnuffleEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class SnuffleRenderer extends MobEntityRenderer<SnuffleEntity, SnuffleModel<SnuffleEntity>> {
    private static final Identifier[] SNUFFLE_LOCATIONS = {
            new Identifier("snuffles", "textures/entity/snuffle/snuffle_default.png"),
            new Identifier("snuffles", "textures/entity/snuffle/snuffle_sheepdog.png"),
            new Identifier("snuffles", "textures/entity/snuffle/snuffle_poro.png"),
            new Identifier("snuffles", "textures/entity/snuffle/snuffle_horseshoe.png")
    };
    private static final Identifier[] FROSTY_LOCATIONS = {
            new Identifier("snuffles", "textures/entity/snuffle/frosty_default.png"),
            new Identifier("snuffles", "textures/entity/snuffle/frosty_sheepdog.png"),
            new Identifier("snuffles", "textures/entity/snuffle/frosty_poro.png"),
            new Identifier("snuffles", "textures/entity/snuffle/frosty_horseshoe.png")
    };

    public SnuffleRenderer(EntityRendererFactory.Context context) {
        super(context, new SnuffleModel<>(context.getPart(ModEntityModelLayers.SNUFFLE)), 0.7F);
        this.addFeature(new SnuffleFluffLayer<>(this));
    }

    @Override
    public Identifier getTexture(SnuffleEntity snuffle) {
        int i = snuffle.getHairstyleId();
        return snuffle.isFrosty() ? FROSTY_LOCATIONS[i] : SNUFFLE_LOCATIONS[i];
    }
}