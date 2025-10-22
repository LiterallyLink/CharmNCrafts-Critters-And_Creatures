package com.charmed.charmncraft.entity;

import com.charmed.charmncraft.ModEntities;
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
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class CaracalEntity extends AnimalEntity implements GeoEntity {
    // Constants
    private static final double MAX_HEALTH = 10.0;
    private static final double MOVEMENT_SPEED = 0.3;
    private static final double ATTACK_DAMAGE = 3.0;
    private static final double ESCAPE_DANGER_SPEED = 1.5;
    private static final double MATING_SPEED = 1.0;
    private static final double TEMPT_SPEED = 1.0;
    private static final double FOLLOW_PARENT_SPEED = 1.25;
    private static final double WANDER_SPEED = 0.8;
    private static final float LOOK_AT_PLAYER_DISTANCE = 8.0F;
    private static final int ANIMATION_TRANSITION_TICKS = 5;

    private static final TrackedData<Boolean> SITTING = DataTracker.registerData(CaracalEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> SLEEPING = DataTracker.registerData(CaracalEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    private static final RawAnimation IDLE_ANIM = RawAnimation.begin().thenLoop("animation.caracal.idle");
    private static final RawAnimation WALK_ANIM = RawAnimation.begin().thenLoop("animation.caracal.walk");
    private static final RawAnimation RUN_ANIM = RawAnimation.begin().thenLoop("animation.caracal.run");
    private static final RawAnimation SIT_ANIM = RawAnimation.begin().thenLoop("animation.caracal.sit");
    private static final RawAnimation SLEEP_ANIM = RawAnimation.begin().thenLoop("animation.caracal.sleep");
    private static final RawAnimation SNEAK_ANIM = RawAnimation.begin().thenLoop("animation.caracal.sneak");

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public CaracalEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createCaracalAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, MAX_HEALTH)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, MOVEMENT_SPEED)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, ATTACK_DAMAGE);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new EscapeDangerGoal(this, ESCAPE_DANGER_SPEED));
        this.goalSelector.add(2, new AnimalMateGoal(this, MATING_SPEED));
        this.goalSelector.add(3, new TemptGoal(this, TEMPT_SPEED, Ingredient.ofItems(Items.COD, Items.SALMON, Items.TROPICAL_FISH, Items.PUFFERFISH), false));
        this.goalSelector.add(4, new FollowParentGoal(this, FOLLOW_PARENT_SPEED));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, WANDER_SPEED));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, LOOK_AT_PLAYER_DISTANCE));
        this.goalSelector.add(7, new LookAroundGoal(this));
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(SITTING, false);
        this.dataTracker.startTracking(SLEEPING, false);
    }

    public boolean isSitting() {
        return this.dataTracker.get(SITTING);
    }

    public void setSitting(boolean sitting) {
        this.dataTracker.set(SITTING, sitting);
    }

    public boolean isSleeping() {
        return this.dataTracker.get(SLEEPING);
    }

    public void setSleeping(boolean sleeping) {
        this.dataTracker.set(SLEEPING, sleeping);
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return ModEntities.CARACAL.create(world);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.isOf(Items.COD) || stack.isOf(Items.SALMON) ||
               stack.isOf(Items.TROPICAL_FISH) || stack.isOf(Items.PUFFERFISH);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_CAT_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_CAT_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_CAT_DEATH;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", ANIMATION_TRANSITION_TICKS, state -> {
            if (isSleeping()) {
                return state.setAndContinue(SLEEP_ANIM);
            }
            if (isSitting()) {
                return state.setAndContinue(SIT_ANIM);
            }
            if (this.isSneaking()) {
                return state.setAndContinue(SNEAK_ANIM);
            }
            if (state.isMoving()) {
                if (this.isSprinting()) {
                    return state.setAndContinue(RUN_ANIM);
                }
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
