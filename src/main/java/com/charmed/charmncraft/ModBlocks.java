package com.charmed.charmncraft;

import com.charmed.charmncraft.block.FrostyFluffBlock;
import com.charmed.charmncraft.block.SnuffleFluffBlock;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class ModBlocks {
    // Snuffle fluff blocks (dropped when sheared)
    public static final Block SNUFFLE_FLUFF = registerBlock("snuffle_fluff",
            new SnuffleFluffBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.WHITE)
                    .ticksRandomly()
                    .strength(0.6F)
                    .sounds(BlockSoundGroup.WOOL)));

    public static final Block FROSTY_FLUFF = registerBlock("frosty_fluff",
            new FrostyFluffBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.WHITE)
                    .strength(0.6F)
                    .sounds(BlockSoundGroup.WOOL)));

    private static Block registerBlock(String name, Block block) {
        Registry.register(Registries.ITEM, new Identifier("snuffles", name),
                new BlockItem(block, new FabricItemSettings()));
        return Registry.register(Registries.BLOCK, new Identifier("snuffles", name), block);
    }

    public static void initialize() {
        CharmNCraft.LOGGER.info("Registering Snuffle blocks.");

        // Add to creative inventory
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(content -> {
            content.add(SNUFFLE_FLUFF);
            content.add(FROSTY_FLUFF);
        });
    }
}
