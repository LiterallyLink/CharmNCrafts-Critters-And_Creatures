package com.charmed.charmncraft;

import com.charmed.charmncraft.client.renderer.CaracalRenderer;
import com.charmed.charmncraft.client.renderer.DuckRenderer;
import com.charmed.charmncraft.client.renderer.QuacklingRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class CharmNCraftClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// Register entity renderers
		EntityRendererRegistry.register(ModEntities.DUCK, DuckRenderer::new);
		EntityRendererRegistry.register(ModEntities.QUACKLING, QuacklingRenderer::new);
		EntityRendererRegistry.register(ModEntities.CARACAL, CaracalRenderer::new);
	}
}
