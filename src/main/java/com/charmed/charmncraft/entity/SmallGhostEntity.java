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
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;
import com.charmed.charmncraft.ModSounds;

import java.util.UUID;

public class SmallGhostEntity extends AnimalEntity implements GeoEntity {
    // Constants
    private static final double MAX_HEALTH = 10.0;
    private static final double MOVEMENT_SPEED = 0.25;
    private static final double FLYING_SPEED = 0.35;
    private static final double FOLLOW_RANGE = 12.0;
    private static final int ANIMATION_TRANSITION_TICKS = 5;
    private static final int MAX_INTERACTION_STATE = 2;

    // Tracked data
    private static final TrackedData<Integer> INTERACTION_STATE = DataTracker.registerData(SmallGhostEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Boolean> IS_HOLDING_ITEM = DataTracker.registerData(SmallGhostEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> IS_SITTING = DataTracker.registerData(SmallGhostEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<java.util.Optional<UUID>> OWNER_UUID = DataTracker.registerData(SmallGhostEntity.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);

    // Animations
    private static final RawAnimation IDLE_ANIM = RawAnimation.begin().thenLoop("ghost_idle");
    private static final RawAnimation MOVE_ANIM = RawAnimation.begin().thenLoop("ghost_move");
    private static final RawAnimation SITTING_ANIM = RawAnimation.begin().thenLoop("ghost_sitting");

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public SmallGhostEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
        this.setNoGravity(true);
    }

    public static DefaultAttributeContainer.Builder createSmallGhostAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, MAX_HEALTH)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, MOVEMENT_SPEED)
                .add(EntityAttributes.GENERIC_FLYING_SPEED, FLYING_SPEED)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, FOLLOW_RANGE);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(7, new LookAroundGoal(this));
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(INTERACTION_STATE, 0);
        this.dataTracker.startTracking(IS_HOLDING_ITEM, false);
        this.dataTracker.startTracking(IS_SITTING, false);
        this.dataTracker.startTracking(OWNER_UUID, java.util.Optional.empty());
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("InteractionState", this.getInteractionState());
        nbt.putBoolean("IsHoldingItem", this.isHoldingItem());
        nbt.putBoolean("Sitting", this.isSitting());
        if (this.getOwnerUuid() != null) {
            nbt.putUuid("Owner", this.getOwnerUuid());
        }
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setInteractionState(nbt.getInt("InteractionState"));
        this.setHoldingItem(nbt.getBoolean("IsHoldingItem"));
        this.setSitting(nbt.getBoolean("Sitting"));
        if (nbt.containsUuid("Owner")) {
            this.setOwnerUuid(nbt.getUuid("Owner"));
        }
    }

    public int getInteractionState() {
        return this.dataTracker.get(INTERACTION_STATE);
    }

    public void setInteractionState(int state) {
        this.dataTracker.set(INTERACTION_STATE, state);
    }

    public boolean isHoldingItem() {
        return this.dataTracker.get(IS_HOLDING_ITEM);
    }

    public void setHoldingItem(boolean holding) {
        this.dataTracker.set(IS_HOLDING_ITEM, holding);
    }

    public boolean isSitting() {
        return this.dataTracker.get(IS_SITTING);
    }

    public void setSitting(boolean sitting) {
        this.dataTracker.set(IS_SITTING, sitting);
    }

    @Nullable
    public UUID getOwnerUuid() {
        return this.dataTracker.get(OWNER_UUID).orElse(null);
    }

    public void setOwnerUuid(@Nullable UUID uuid) {
        this.dataTracker.set(OWNER_UUID, java.util.Optional.ofNullable(uuid));
    }

    @Nullable
    public PlayerEntity getOwner() {
        UUID uuid = this.getOwnerUuid();
        return uuid == null ? null : this.getWorld().getPlayerByUuid(uuid);
    }

    public void setOwner(@Nullable PlayerEntity player) {
        this.setOwnerUuid(player == null ? null : player.getUuid());
    }

    public boolean isTamed() {
        return this.getOwnerUuid() != null;
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);

        if (this.getWorld().isClient) {
            return ActionResult.CONSUME;
        }

        if (this.isTamed()) {
            // Cycle through interaction states: 0=sitting, 1=following, 2=wandering
            int currentState = this.getInteractionState();
            int nextState = (currentState + 1) % (MAX_INTERACTION_STATE + 1);
            this.setInteractionState(nextState);

            // Update sitting state
            this.setSitting(nextState == 0);

            // Send message to player
            String messageKey = "entity.ghosts.client_message.interaction_" + nextState;
            player.sendMessage(Text.translatable(messageKey, this.getName()), false);

            return ActionResult.SUCCESS;
        } else if (itemStack.isOf(Items.GLOWSTONE_DUST)) {
            if (!player.getAbilities().creativeMode) {
                itemStack.decrement(1);
            }
            this.setOwner(player);
            this.navigation.stop();
            this.setTarget(null);
            this.getWorld().sendEntityStatus(this, (byte) 7);
            return ActionResult.SUCCESS;
        }

        return super.interactMob(player, hand);
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null; // Small ghosts don't breed
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return false; // Small ghosts don't breed
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.ENTITY_SMALL_GHOST_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSounds.ENTITY_SMALL_GHOST_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.ENTITY_SMALL_GHOST_DEATH;
    }

    @Override
    protected void playStepSound(net.minecraft.util.math.BlockPos pos, net.minecraft.block.BlockState state) {
        // Small ghosts don't make step sounds (they float)
    }

    @Override
    public void tick() {
        super.tick();

        // Keep small ghosts floating
        if (!this.getWorld().isClient && !this.hasNoGravity()) {
            this.setNoGravity(true);
        }
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", ANIMATION_TRANSITION_TICKS, state -> {
            if (this.isSitting()) {
                return state.setAndContinue(SITTING_ANIM);
            } else if (state.isMoving()) {
                return state.setAndContinue(MOVE_ANIM);
            }
            return state.setAndContinue(IDLE_ANIM);
        }));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public boolean canBeLeashedBy(PlayerEntity player) {
        return this.isTamed() && super.canBeLeashedBy(player);
    }
}
