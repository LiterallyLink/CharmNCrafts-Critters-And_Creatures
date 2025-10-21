package com.charmed.charmncraft;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class ModSounds {
    public static final SoundEvent RED_PANDA_IDLE = registerSound("entity.ydms_redpanda.idle");
    public static final SoundEvent RED_PANDA_DEATH = registerSound("entity.ydms_redpanda.death");

    private static SoundEvent registerSound(String id) {
        Identifier identifier = new Identifier("ydms_redpanda", id.replace("entity.ydms_redpanda.", ""));
        return Registry.register(Registries.SOUND_EVENT, identifier, SoundEvent.of(identifier));
    }

    public static void initialize() {
        CharmNCraft.LOGGER.info("Registering sounds.");
    }
}
