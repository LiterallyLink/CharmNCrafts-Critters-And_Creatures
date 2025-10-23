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
    public static final SoundEvent ENTITY_SNUFFLE_SHEAR = registerSound("entity.snuffle.shear");
    public static final SoundEvent ENTITY_SNUFFLE_STYLE = registerSound("entity.snuffle.style");
    public static final SoundEvent ENTITY_SNUFFLE_THAW = registerSound("entity.snuffle.thaw");

    // Ghost sounds
    public static final SoundEvent ENTITY_GHOST_AMBIENT = registerGhostSound("entity.ghost.ambient");
    public static final SoundEvent ENTITY_GHOST_DEATH = registerGhostSound("entity.ghost.death");
    public static final SoundEvent ENTITY_GHOST_HURT = registerGhostSound("entity.ghost.hurt");

    // Small Ghost sounds
    public static final SoundEvent ENTITY_SMALL_GHOST_AMBIENT = registerGhostSound("entity.small_ghost.ambient");
    public static final SoundEvent ENTITY_SMALL_GHOST_DEATH = registerGhostSound("entity.small_ghost.death");
    public static final SoundEvent ENTITY_SMALL_GHOST_HURT = registerGhostSound("entity.small_ghost.hurt");

    // Degu sounds
    public static final SoundEvent ENTITY_DEGU_AMBIENT = registerDeguSound("degu_ambient");
    public static final SoundEvent ENTITY_DEGU_HURT = registerDeguSound("degu_angry");
    public static final SoundEvent ENTITY_DEGU_DEATH = registerDeguSound("degu_death");

    private static SoundEvent registerSound(String id) {
        Identifier identifier = new Identifier("snuffles", id);
        return Registry.register(Registries.SOUND_EVENT, identifier, SoundEvent.of(identifier));
    }

    private static SoundEvent registerGhostSound(String id) {
        Identifier identifier = new Identifier("ghosts", id);
        return Registry.register(Registries.SOUND_EVENT, identifier, SoundEvent.of(identifier));
    }

    private static SoundEvent registerDeguSound(String id) {
        Identifier identifier = new Identifier("vanilla_degus", id);
        return Registry.register(Registries.SOUND_EVENT, identifier, SoundEvent.of(identifier));
    }

    public static void initialize() {
        CharmNCraft.LOGGER.info("Registering Snuffle sounds.");
        CharmNCraft.LOGGER.info("Registering Ghost sounds.");
        CharmNCraft.LOGGER.info("Registering Degu sounds.");
    }
}
