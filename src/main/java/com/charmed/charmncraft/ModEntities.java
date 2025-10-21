package com.charmed.charmncraft;

import com.charmed.charmncraft.entity.CaracalEntity;
import com.charmed.charmncraft.entity.DuckEntity;
import com.charmed.charmncraft.entity.QuacklingEntity;
import com.charmed.charmncraft.entity.RedPandaEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;

public class ModEntities {
    public static final EntityType<DuckEntity> DUCK = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier("duckling", "duck"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, DuckEntity::new)
                    .dimensions(EntityType.CHICKEN.getDimensions())
                    .build()
    );

    public static final EntityType<QuacklingEntity> QUACKLING = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier("duckling", "quackling"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, QuacklingEntity::new)
                    .dimensions(EntityType.CHICKEN.getDimensions().scaled(0.5f))
                    .build()
    );

    public static final EntityType<CaracalEntity> CARACAL = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier("aqupd", "caracal"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, CaracalEntity::new)
                    .dimensions(EntityType.CAT.getDimensions())
                    .build()
    );

    public static final EntityType<RedPandaEntity> RED_PANDA = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier("ydms_redpanda", "redpanda"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, RedPandaEntity::new)
                    .dimensions(EntityType.CAT.getDimensions())
                    .build()
    );

    public static void initialize() {
        CharmNCraft.LOGGER.info("Registering entities.");

        FabricDefaultAttributeRegistry.register(DUCK, DuckEntity.createDuckAttributes());
        FabricDefaultAttributeRegistry.register(QUACKLING, QuacklingEntity.createQuacklingAttributes());
        FabricDefaultAttributeRegistry.register(CARACAL, CaracalEntity.createCaracalAttributes());
        FabricDefaultAttributeRegistry.register(RED_PANDA, RedPandaEntity.createRedPandaAttributes());
    }
}