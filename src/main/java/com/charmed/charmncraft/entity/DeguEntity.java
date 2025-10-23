package com.charmed.charmncraft.entity;

import com.charmed.charmncraft.ModEntities;
import com.charmed.charmncraft.ModSounds;
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
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Util;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

public class DeguEntity extends AnimalEntity implements GeoEntity {
    // Constants
    private static final double MAX_HEALTH = 8.0;
    private static final double MOVEMENT_SPEED = 0.25;
    private static final double ESCAPE_DANGER_SPEED = 1.5;
    private static final double MATING_SPEED = 1.0;
    private static final double TEMPT_SPEED = 1.2;
    private static final double FOLLOW_PARENT_SPEED = 1.25;
    private static final double WANDER_SPEED = 1.0;
    private static final float LOOK_AT_PLAYER_DISTANCE = 6.0F;
    private static final int ANIMATION_TRANSITION_TICKS = 5;

    // Tracked data for variant (color)
    private static final TrackedData<Integer> VARIANT = DataTracker.registerData(DeguEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Byte> COLLAR_COLOR = DataTracker.registerData(DeguEntity.class, TrackedDataHandlerRegistry.BYTE);
    private static final TrackedData<Boolean> TAMED = DataTracker.registerData(DeguEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    // Animation definitions
    private static final RawAnimation IDLE_ANIM = RawAnimation.begin().thenLoop("animation.degu.idle");
    private static final RawAnimation WALK_ANIM = RawAnimation.begin().thenLoop("animation.degu.walk");
    private static final RawAnimation RUN_ANIM = RawAnimation.begin().thenLoop("animation.degu.run");

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    // Degu variants (0-5 for the 6 color variants)
    private static final int VARIANT_COUNT = 6;

    public DeguEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createDeguAttributes() {
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
        this.dataTracker.startTracking(COLLAR_COLOR, (byte) DyeColor.RED.getId());
        this.dataTracker.startTracking(TAMED, false);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("Variant", this.getVariant());
        nbt.putByte("CollarColor", (byte) this.getCollarColor().getId());
        nbt.putBoolean("Tamed", this.isTamed());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setVariant(nbt.getInt("Variant"));
        if (nbt.contains("CollarColor", 99)) {
            this.setCollarColor(DyeColor.byId(nbt.getByte("CollarColor")));
        }
        this.setTamed(nbt.getBoolean("Tamed"));
    }

    public int getVariant() {
        return this.dataTracker.get(VARIANT);
    }

    public void setVariant(int variant) {
        this.dataTracker.set(VARIANT, variant);
    }

    public DyeColor getCollarColor() {
        return DyeColor.byId(this.dataTracker.get(COLLAR_COLOR));
    }

    public void setCollarColor(DyeColor color) {
        this.dataTracker.set(COLLAR_COLOR, (byte) color.getId());
    }

    public boolean isTamed() {
        return this.dataTracker.get(TAMED);
    }

    public void setTamed(boolean tamed) {
        this.dataTracker.set(TAMED, tamed);
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        DeguEntity baby = ModEntities.DEGU.create(world);
        if (baby != null) {
            // Random variant or inherit from parents
            if (entity instanceof DeguEntity otherParent) {
                baby.setVariant(this.random.nextBoolean() ? this.getVariant() : otherParent.getVariant());
            } else {
                baby.setVariant(this.random.nextInt(VARIANT_COUNT));
            }
        }
        return baby;
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.isOf(Items.WHEAT_SEEDS) || stack.isOf(Items.MELON_SEEDS) ||
               stack.isOf(Items.PUMPKIN_SEEDS) || stack.isOf(Items.BEETROOT_SEEDS);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.ENTITY_DEGU_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSounds.ENTITY_DEGU_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.ENTITY_DEGU_DEATH;
    }

    @Override
    protected float getSoundVolume() {
        return 0.4F;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", ANIMATION_TRANSITION_TICKS, state -> {
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

    @Nullable
    @Override
    protected void initEquipment(net.minecraft.util.math.random.Random random, net.minecraft.world.LocalDifficulty difficulty) {
        super.initEquipment(random, difficulty);
        this.setVariant(random.nextInt(VARIANT_COUNT));
    }

    // Get texture name based on variant
    public String getTextureName() {
        return switch (this.getVariant()) {
            case 1 -> "blue";
            case 2 -> "sand";
            case 3 -> "agouti";
            case 4 -> "agoutipied";
            case 5 -> "black";
            default -> "white";
        };
    }
}
