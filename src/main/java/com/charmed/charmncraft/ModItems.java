package com.charmed.charmncraft;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    // Spawn Eggs
    public static final Item DUCK_SPAWN_EGG = Registry.register(
            Registries.ITEM,
            new Identifier("duckling", "duck_spawn_egg"),
            new SpawnEggItem(ModEntities.DUCK, 0x8B7355, 0xD9A066, new FabricItemSettings())
    );

    public static final Item QUACKLING_SPAWN_EGG = Registry.register(
            Registries.ITEM,
            new Identifier("duckling", "quackling_spawn_egg"),
            new SpawnEggItem(ModEntities.QUACKLING, 0xFFE873, 0xD9A066, new FabricItemSettings())
    );

    public static final Item CARACAL_SPAWN_EGG = Registry.register(
            Registries.ITEM,
            new Identifier("aqupd", "caracal_spawn_egg"),
            new SpawnEggItem(ModEntities.CARACAL, 0xCD853F, 0x8B4513, new FabricItemSettings())
    );

    public static final Item RED_PANDA_SPAWN_EGG = Registry.register(
            Registries.ITEM,
            new Identifier("ydms_redpanda", "redpanda_spawn_egg"),
            new SpawnEggItem(ModEntities.RED_PANDA, 0xDB5B3F, 0x3D2314, new FabricItemSettings())
    );

    // Food Items
    public static final Item RAW_DUCK = Registry.register(
            Registries.ITEM,
            new Identifier("duckling", "raw_duck"),
            new Item(new FabricItemSettings().food(new FoodComponent.Builder().hunger(2).saturationModifier(0.3f).meat().build()))
    );

    public static final Item COOKED_DUCK = Registry.register(
            Registries.ITEM,
            new Identifier("duckling", "cooked_duck"),
            new Item(new FabricItemSettings().food(new FoodComponent.Builder().hunger(6).saturationModifier(0.6f).meat().build()))
    );

    public static final Item DUCK_EGG = Registry.register(
            Registries.ITEM,
            new Identifier("duckling", "duck_egg"),
            new Item(new FabricItemSettings())
    );

    public static void initialize() {
        CharmNCraft.LOGGER.info("Registering items.");

        // Add items to creative inventory
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(content -> {
            content.add(DUCK_SPAWN_EGG);
            content.add(QUACKLING_SPAWN_EGG);
            content.add(CARACAL_SPAWN_EGG);
            content.add(RED_PANDA_SPAWN_EGG);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(content -> {
            content.add(RAW_DUCK);
            content.add(COOKED_DUCK);
            content.add(DUCK_EGG);
        });
    }
}