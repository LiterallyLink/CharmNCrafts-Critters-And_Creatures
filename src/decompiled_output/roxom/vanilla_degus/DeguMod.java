package roxom.vanilla_degus;

import java.util.Arrays;
import java.util.List;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents.ModifyEntries;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.class_1291;
import net.minecraft.class_1299;
import net.minecraft.class_1311;
import net.minecraft.class_1792;
import net.minecraft.class_1802;
import net.minecraft.class_1826;
import net.minecraft.class_1959;
import net.minecraft.class_1972;
import net.minecraft.class_2378;
import net.minecraft.class_2960;
import net.minecraft.class_3414;
import net.minecraft.class_4048;
import net.minecraft.class_4174;
import net.minecraft.class_5321;
import net.minecraft.class_7706;
import net.minecraft.class_7923;
import net.minecraft.class_1317.class_1319;
import net.minecraft.class_2902.class_2903;
import net.minecraft.class_4174.class_4175;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import roxom.vanilla_degus.common.DeguEntity;
import roxom.vanilla_degus.common.DegusGraceStatusEffect;
import roxom.vanilla_degus.mixin.ItemAccessor;

public class DeguMod implements ModInitializer {
   public static final String MOD_ID = "vanilla_degus";
   public static final Logger LOGGER = LogManager.getLogger();
   public static final class_2960 DEGU_ENTITY_ID = id("degu");
   public static final class_1299<DeguEntity> DEGU = FabricEntityTypeBuilder.createMob()
      .spawnGroup(class_1311.field_6294)
      .entityFactory(DeguEntity::new)
      .defaultAttributes(DeguEntity::createDeguAttributes)
      .dimensions(class_4048.method_18385(0.5F, 0.75F))
      .spawnRestriction(class_1319.field_6317, class_2903.field_13203, DeguEntity::canSpawn)
      .build();
   public static final class_1792 DEGU_SPAWN_EGG = new class_1826(DEGU, 8481358, 14075055, new FabricItemSettings().fireproof().maxCount(64));
   private static final class_2960 DEGU_SOUND_AMBIENT_ID = id("degu_ambient");
   private static final class_2960 DEGU_SOUND_HURT_ID = id("degu_angry");
   private static final class_2960 DEGU_SOUND_DEATH_ID = id("degu_death");
   public static final class_3414 DEGU_SOUND_AMBIENT = class_3414.method_47908(DEGU_SOUND_AMBIENT_ID);
   public static final class_3414 DEGU_SOUND_HURT = class_3414.method_47908(DEGU_SOUND_HURT_ID);
   public static final class_3414 DEGU_SOUND_DEATH = class_3414.method_47908(DEGU_SOUND_DEATH_ID);
   private static final class_2960 DEGUS_GRACE_EFFECT_ID = id("degus_grace");
   public static final class_1291 DEGUS_GRACE_EFFECT = new DegusGraceStatusEffect();
   public static final class_4174 FOOD_GRASS = new class_4175().method_19238(1).method_19237(0.3F).method_19241().method_19242();

   public void onInitialize() {
      class_2378.method_10230(class_7923.field_41177, DEGU_ENTITY_ID, DEGU);
      class_2378.method_10230(class_7923.field_41178, id("degu_spawn_egg"), DEGU_SPAWN_EGG);
      class_2378.method_10230(class_7923.field_41172, DEGU_SOUND_AMBIENT_ID, DEGU_SOUND_AMBIENT);
      class_2378.method_10230(class_7923.field_41172, DEGU_SOUND_HURT_ID, DEGU_SOUND_HURT);
      class_2378.method_10230(class_7923.field_41172, DEGU_SOUND_DEATH_ID, DEGU_SOUND_DEATH);
      class_2378.method_10230(class_7923.field_41174, DEGUS_GRACE_EFFECT_ID, DEGUS_GRACE_EFFECT);
      this.registerSpawn();
      ItemGroupEvents.modifyEntriesEvent(class_7706.field_40205).register((ModifyEntries)entries -> entries.method_45421(DEGU_SPAWN_EGG));
      ((ItemAccessor)class_1802.field_8602).setFoodComponent(FOOD_GRASS);
      ((ItemAccessor)class_1802.field_8256).setFoodComponent(FOOD_GRASS);
      ((ItemAccessor)class_1802.field_8689).setFoodComponent(FOOD_GRASS);
   }

   public void registerSpawn() {
      BiomeModifications.addSpawn(
         BiomeSelectors.includeByKey(
            new class_5321[]{class_1972.field_9424, class_1972.field_9415, class_1972.field_9443, class_1972.field_35110, class_1972.field_34475}
         ),
         DEGU.method_5891(),
         DEGU,
         1,
         2,
         4
      );
      BiomeModifications.addSpawn(
         BiomeSelectors.includeByKey(new class_5321[]{class_1972.field_9449, class_1972.field_34470}), DEGU.method_5891(), DEGU, 5, 3, 7
      );
      List<class_5321<class_1959>> plateauBiomes = Arrays.asList(class_1972.field_9430, class_1972.field_35114);
      BiomeModifications.addSpawn(BiomeSelectors.includeByKey(plateauBiomes), DEGU.method_5891(), DEGU, 10, 3, 7);
   }

   public static class_2960 id(String path) {
      return new class_2960("vanilla_degus", path);
   }
}
