package com.charmed.charmncraft;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModTags {
    public static class Items {
        public static final TagKey<Item> SNUFFLE_FOOD = createTag("snuffle_food");

        private static TagKey<Item> createTag(String name) {
            return TagKey.of(RegistryKeys.ITEM, new Identifier("snuffles", name));
        }
    }

    public static class Blocks {
        public static final TagKey<Block> SNUFFLES_SPAWNABLE_ON = createTag("snuffles_spawnable_on");

        private static TagKey<Block> createTag(String name) {
            return TagKey.of(RegistryKeys.BLOCK, new Identifier("snuffles", name));
        }
    }
}
