package com.charmed.charmncraft;

import com.charmed.charmncraft.client.model.SnuffleModel;
import com.charmed.charmncraft.client.particle.SnufflesSnowflakeParticle;
import com.charmed.charmncraft.client.renderer.CaracalRenderer;
import com.charmed.charmncraft.client.renderer.DuckRenderer;
import com.charmed.charmncraft.client.renderer.GhostRenderer;
import com.charmed.charmncraft.client.renderer.ModEntityModelLayers;
import com.charmed.charmncraft.client.renderer.QuacklingRenderer;
import com.charmed.charmncraft.client.renderer.SmallGhostRenderer;
import com.charmed.charmncraft.client.renderer.SnuffleRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class CharmNCraftClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// Register entity renderers
		EntityRendererRegistry.register(ModEntities.DUCK, DuckRenderer::new);
		EntityRendererRegistry.register(ModEntities.QUACKLING, QuacklingRenderer::new);
		EntityRendererRegistry.register(ModEntities.CARACAL, CaracalRenderer::new);
		EntityRendererRegistry.register(ModEntities.SNUFFLE, SnuffleRenderer::new);
		EntityRendererRegistry.register(ModEntities.GHOST, GhostRenderer::new);
		EntityRendererRegistry.register(ModEntities.SMALL_GHOST, SmallGhostRenderer::new);

		// Register entity model layers
		EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.SNUFFLE, SnuffleModel::getTexturedModelData);

		// Register particle factories
		ParticleFactoryRegistry.getInstance().register(ModParticles.SNOWFLAKE, SnufflesSnowflakeParticle.Factory::new);
	}
}
