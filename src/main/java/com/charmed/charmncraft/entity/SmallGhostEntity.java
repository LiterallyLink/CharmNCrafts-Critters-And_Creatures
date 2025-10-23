package com.charmed.charmncraft.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
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

    // Tracked data
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
        this.moveControl = new FlightMoveControl(this, 20, true);
    }

    @Override
    protected EntityNavigation createNavigation(World world) {
        BirdNavigation birdNavigation = new BirdNavigation(this, world);
        birdNavigation.setCanPathThroughDoors(false);
        birdNavigation.setCanSwim(true);
        birdNavigation.setCanEnterOpenDoors(true);
        return birdNavigation;
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
        this.goalSelector.add(2, new SmallGhostSitGoal());
        this.goalSelector.add(3, new SmallGhostFollowOwnerGoal(1.0, 10.0F, 2.0F));
        this.goalSelector.add(5, new SmallGhostFlyGoal());
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(7, new LookAroundGoal(this));
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(IS_SITTING, false);
        this.dataTracker.startTracking(OWNER_UUID, java.util.Optional.empty());
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("Sitting", this.isSitting());
        if (this.getOwnerUuid() != null) {
            nbt.putUuid("Owner", this.getOwnerUuid());
        }
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setSitting(nbt.getBoolean("Sitting"));
        if (nbt.containsUuid("Owner")) {
            this.setOwnerUuid(nbt.getUuid("Owner"));
        }
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
            if (this.isOwner(player)) {
                // Toggle sitting state like dogs
                this.setSitting(!this.isSitting());
                this.jumping = false;
                this.navigation.stop();
                this.setTarget(null);
                return ActionResult.SUCCESS;
            }
        } else if (itemStack.isOf(Items.GLOWSTONE_DUST)) {
            if (!player.getAbilities().creativeMode) {
                itemStack.decrement(1);
            }
            this.setOwner(player);
            this.setSitting(false); // Start following immediately after taming
            this.navigation.stop();
            this.setTarget(null);
            this.getWorld().sendEntityStatus(this, (byte) 7);
            return ActionResult.SUCCESS;
        }

        return super.interactMob(player, hand);
    }

    public boolean isOwner(PlayerEntity player) {
        return player.getUuid().equals(this.getOwnerUuid());
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

    /*
     * Custom AI Goals
     */

    class SmallGhostSitGoal extends Goal {
        public SmallGhostSitGoal() {
            this.setControls(java.util.EnumSet.of(Goal.Control.MOVE, Goal.Control.JUMP));
        }

        @Override
        public boolean canStart() {
            return SmallGhostEntity.this.isSitting();
        }

        @Override
        public boolean shouldContinue() {
            return SmallGhostEntity.this.isSitting();
        }

        @Override
        public void start() {
            SmallGhostEntity.this.getNavigation().stop();
        }
    }

    class SmallGhostFollowOwnerGoal extends Goal {
        private final double speed;
        private final float minDistance;
        private final float maxDistance;
        private PlayerEntity owner;
        private int updateCountdownTicks;

        public SmallGhostFollowOwnerGoal(double speed, float maxDistance, float minDistance) {
            this.speed = speed;
            this.minDistance = minDistance;
            this.maxDistance = maxDistance;
            this.setControls(java.util.EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
        }

        @Override
        public boolean canStart() {
            PlayerEntity owner = SmallGhostEntity.this.getOwner();
            if (owner == null) {
                return false;
            }
            if (SmallGhostEntity.this.isSitting()) {
                return false;
            }
            if (SmallGhostEntity.this.squaredDistanceTo(owner) < (double)(this.minDistance * this.minDistance)) {
                return false;
            }
            this.owner = owner;
            return true;
        }

        @Override
        public boolean shouldContinue() {
            if (SmallGhostEntity.this.getNavigation().isIdle()) {
                return false;
            }
            if (SmallGhostEntity.this.isSitting()) {
                return false;
            }
            return SmallGhostEntity.this.squaredDistanceTo(this.owner) > (double)(this.maxDistance * this.maxDistance);
        }

        @Override
        public void start() {
            this.updateCountdownTicks = 0;
        }

        @Override
        public void stop() {
            this.owner = null;
            SmallGhostEntity.this.getNavigation().stop();
        }

        @Override
        public void tick() {
            SmallGhostEntity.this.getLookControl().lookAt(this.owner, 10.0F, (float)SmallGhostEntity.this.getMaxLookPitchChange());
            if (--this.updateCountdownTicks <= 0) {
                this.updateCountdownTicks = 10;
                if (!SmallGhostEntity.this.isLeashed() && !SmallGhostEntity.this.hasVehicle()) {
                    if (SmallGhostEntity.this.squaredDistanceTo(this.owner) >= 144.0) {
                        this.tryTeleport();
                    } else {
                        SmallGhostEntity.this.getNavigation().startMovingTo(this.owner, this.speed);
                    }
                }
            }
        }

        private void tryTeleport() {
            net.minecraft.util.math.BlockPos ownerPos = this.owner.getBlockPos();
            for (int i = 0; i < 10; i++) {
                int x = this.getRandomInt(-3, 3);
                int y = this.getRandomInt(-1, 1);
                int z = this.getRandomInt(-3, 3);
                if (this.tryTeleportTo(ownerPos.getX() + x, ownerPos.getY() + y, ownerPos.getZ() + z)) {
                    return;
                }
            }
        }

        private boolean tryTeleportTo(int x, int y, int z) {
            if (Math.abs((double)x - this.owner.getX()) < 2.0 && Math.abs((double)z - this.owner.getZ()) < 2.0) {
                return false;
            }
            if (!this.canTeleportTo(new net.minecraft.util.math.BlockPos(x, y, z))) {
                return false;
            }
            SmallGhostEntity.this.refreshPositionAndAngles((double)x + 0.5, y, (double)z + 0.5, SmallGhostEntity.this.getYaw(), SmallGhostEntity.this.getPitch());
            SmallGhostEntity.this.getNavigation().stop();
            return true;
        }

        private boolean canTeleportTo(net.minecraft.util.math.BlockPos pos) {
            net.minecraft.world.WorldView world = SmallGhostEntity.this.getWorld();
            return world.isSpaceEmpty(SmallGhostEntity.this, SmallGhostEntity.this.getBoundingBox().offset(pos.subtract(SmallGhostEntity.this.getBlockPos())));
        }

        private int getRandomInt(int min, int max) {
            return SmallGhostEntity.this.getRandom().nextInt(max - min + 1) + min;
        }
    }

    class SmallGhostFlyGoal extends Goal {
        private static final int MAX_DISTANCE = 10;
        private static final int MIN_DISTANCE = 5;

        public SmallGhostFlyGoal() {
            this.setControls(java.util.EnumSet.of(Goal.Control.MOVE));
        }

        @Override
        public boolean canStart() {
            // Only fly around if untamed (like wild mobs)
            if (SmallGhostEntity.this.isTamed()) {
                return false;
            }

            // Don't start a new wander if already navigating
            if (!SmallGhostEntity.this.getNavigation().isIdle()) {
                return false;
            }

            // Random chance to start wandering (like vanilla mobs)
            return SmallGhostEntity.this.getRandom().nextInt(120) == 0;
        }

        @Override
        public boolean shouldContinue() {
            if (SmallGhostEntity.this.isTamed()) {
                return false;
            }
            return !SmallGhostEntity.this.getNavigation().isIdle();
        }

        @Override
        public void start() {
            // Generate a random 3D position to fly to
            net.minecraft.util.math.Vec3d currentPos = SmallGhostEntity.this.getPos();
            net.minecraft.util.math.Random random = SmallGhostEntity.this.getRandom();

            // Random offset in all 3 dimensions
            double offsetX = (random.nextDouble() * 2.0 - 1.0) * MAX_DISTANCE;
            double offsetY = (random.nextDouble() * 2.0 - 1.0) * (MAX_DISTANCE / 2); // Less vertical range
            double offsetZ = (random.nextDouble() * 2.0 - 1.0) * MAX_DISTANCE;

            net.minecraft.util.math.BlockPos targetPos = net.minecraft.util.math.BlockPos.ofFloored(
                currentPos.x + offsetX,
                currentPos.y + offsetY,
                currentPos.z + offsetZ
            );

            // Navigate to the random position
            SmallGhostEntity.this.getNavigation().startMovingTo(targetPos.getX(), targetPos.getY(), targetPos.getZ(), FLYING_SPEED);
        }
    }
}
