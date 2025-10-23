package roxom.vanilla_degus;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import roxom.vanilla_degus.client.DeguRenderer;

@Environment(EnvType.CLIENT)
public class DeguModClient implements ClientModInitializer {
   public void onInitializeClient() {
      EntityRendererRegistry.register(DeguMod.DEGU, DeguRenderer::new);
   }
}
