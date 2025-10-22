package com.charmed.charmncraft;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModParticles {
    public static final DefaultParticleType SNOWFLAKE = FabricParticleTypes.simple(true);

    public static void initialize() {
        Registry.register(Registries.PARTICLE_TYPE, new Identifier("snuffles", "snowflake"), SNOWFLAKE);
        CharmNCraft.LOGGER.info("Registering Snuffle particles.");
    }
}
