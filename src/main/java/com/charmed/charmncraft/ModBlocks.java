package com.charmed.charmncraft;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.CarpetBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class ModBlocks {
    // Snuffle Fluff Blocks
    public static final Block SNUFFLE_FLUFF = registerBlock("snuffle_fluff",
            new Block(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)
                    .sounds(BlockSoundGroup.WOOL)),
            "snuffles");

    public static final Block FROSTY_FLUFF = registerBlock("frosty_fluff",
            new Block(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)
                    .sounds(BlockSoundGroup.WOOL)),
            "snuffles");

    public static final Block SNUFFLE_FLUFF_CARPET = registerBlock("snuffle_fluff_carpet",
            new CarpetBlock(FabricBlockSettings.copyOf(Blocks.WHITE_CARPET)
                    .sounds(BlockSoundGroup.WOOL)),
            "snuffles");

    public static final Block FROSTY_FLUFF_CARPET = registerBlock("frosty_fluff_carpet",
            new CarpetBlock(FabricBlockSettings.copyOf(Blocks.WHITE_CARPET)
                    .sounds(BlockSoundGroup.WOOL)),
            "snuffles");

    private static Block registerBlock(String name, Block block, String namespace) {
        Identifier id = new Identifier(namespace, name);
        Block registeredBlock = Registry.register(Registries.BLOCK, id, block);
        Registry.register(Registries.ITEM, id, new BlockItem(registeredBlock, new FabricItemSettings()));
        return registeredBlock;
    }

    public static void initialize() {
        CharmNCraft.LOGGER.info("Registering Snuffle blocks.");

        // Add blocks to creative inventory
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(content -> {
            content.add(SNUFFLE_FLUFF);
            content.add(FROSTY_FLUFF);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(content -> {
            content.add(SNUFFLE_FLUFF_CARPET);
            content.add(FROSTY_FLUFF_CARPET);
        });
    }
}
