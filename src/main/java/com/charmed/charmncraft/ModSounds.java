package com.charmed.charmncraft;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class ModSounds {
    // Snuffle sounds
    public static final SoundEvent ENTITY_SNUFFLE_AMBIENT = registerSound("entity.snuffle.ambient");
    public static final SoundEvent ENTITY_SNUFFLE_DEATH = registerSound("entity.snuffle.death");
    public static final SoundEvent ENTITY_SNUFFLE_HURT = registerSound("entity.snuffle.hurt");
    public static final SoundEvent ENTITY_SNUFFLE_EAT = registerSound("entity.snuffle.eat");
    public static final SoundEvent ENTITY_SNUFFLE_SHAKE = registerSound("entity.snuffle.shake");
    public static final SoundEvent ENTITY_SNUFFLE_STEP = registerSound("entity.snuffle.step");

    private static SoundEvent registerSound(String id) {
        Identifier identifier = new Identifier("snuffles", id);
        return Registry.register(Registries.SOUND_EVENT, identifier, SoundEvent.of(identifier));
    }

    public static void initialize() {
        CharmNCraft.LOGGER.info("Registering Snuffle sounds.");
    }
}
