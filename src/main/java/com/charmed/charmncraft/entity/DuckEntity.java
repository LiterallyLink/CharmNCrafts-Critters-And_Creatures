package com.charmed.charmncraft.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

public class DuckEntity extends AnimalEntity implements GeoEntity {
    // Constants
    private static final double MAX_HEALTH = 4.0;
    private static final double MOVEMENT_SPEED = 0.25;
    private static final double ESCAPE_DANGER_SPEED = 1.4;
    private static final double MATING_SPEED = 1.0;
    private static final double TEMPT_SPEED = 1.0;
    private static final double FOLLOW_PARENT_SPEED = 1.1;
    private static final double WANDER_SPEED = 1.0;
    private static final float LOOK_AT_PLAYER_DISTANCE = 6.0F;
    private static final int ANIMATION_TRANSITION_TICKS = 5;

    private static final TrackedData<Integer> VARIANT = DataTracker.registerData(DuckEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final RawAnimation IDLE_ANIM = RawAnimation.begin().thenLoop("idle");
    private static final RawAnimation WALK_ANIM = RawAnimation.begin().thenLoop("walk");

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public DuckEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createDuckAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, MAX_HEALTH)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, MOVEMENT_SPEED);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new EscapeDangerGoal(this, ESCAPE_DANGER_SPEED));
        this.goalSelector.add(2, new AnimalMateGoal(this, MATING_SPEED));
        this.goalSelector.add(3, new TemptGoal(this, TEMPT_SPEED, Ingredient.ofItems(Items.WHEAT_SEEDS, Items.MELON_SEEDS, Items.PUMPKIN_SEEDS, Items.BEETROOT_SEEDS), false));
        this.goalSelector.add(4, new FollowParentGoal(this, FOLLOW_PARENT_SPEED));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, WANDER_SPEED));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, LOOK_AT_PLAYER_DISTANCE));
        this.goalSelector.add(7, new LookAroundGoal(this));
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(VARIANT, 0);
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null; // Ducks lay eggs instead of direct breeding
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.isOf(Items.WHEAT_SEEDS) || stack.isOf(Items.MELON_SEEDS) ||
               stack.isOf(Items.PUMPKIN_SEEDS) || stack.isOf(Items.BEETROOT_SEEDS);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_CHICKEN_AMBIENT; // We'll replace this with custom sound later
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_CHICKEN_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_CHICKEN_DEATH;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", ANIMATION_TRANSITION_TICKS, state -> {
            if (state.isMoving()) {
                return state.setAndContinue(WALK_ANIM);
            }
            return state.setAndContinue(IDLE_ANIM);
        }));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
