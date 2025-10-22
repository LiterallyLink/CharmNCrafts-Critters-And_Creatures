package com.charmed.charmncraft.entity;

import com.charmed.charmncraft.ModBlocks;
import com.charmed.charmncraft.ModEntities;
import com.charmed.charmncraft.ModParticles;
import com.charmed.charmncraft.ModSounds;
import com.charmed.charmncraft.ModTags;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalItemTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.Shearable;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.AnimalMateGoal;
import net.minecraft.entity.ai.goal.EscapeDangerGoal;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
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
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class SnuffleEntity extends AnimalEntity implements Shearable {

    // Constants
    private static final int FLUFF_GROWTH_BASE_TICKS = 18000; // 15 minutes (18000 ticks = 900 seconds)
    private static final int FLUFF_GROWTH_RANDOM_TICKS = 6000; // Up to 5 additional minutes
    private static final int MAX_HAIRSTYLES = 4;
    private static final int MIN_HAIRSTYLE_ID = 0;
    private static final int MAX_HAIRSTYLE_ID = 3;
    private static final int BABY_BREEDING_AGE_TICKS = -24000; // -20 minutes
    private static final int FROST_PARTICLE_COUNT = 4;
    private static final double PARTICLE_OFFSET_SMALL = 0.4D;
    private static final double PARTICLE_OFFSET_LARGE = 0.8D;

    // Entity Attributes
    private static final double MAX_HEALTH = 20.0D;
    private static final double MOVEMENT_SPEED = 0.2D;
    private static final double ATTACK_DAMAGE = 2.0D;

    // AI Goal Speeds
    private static final double ESCAPE_DANGER_SPEED = 1.5D;
    private static final double MATING_SPEED = 1.0D;
    private static final double TEMPT_SPEED = 1.1D;
    private static final double FOLLOW_PARENT_SPEED = 1.1D;
    private static final double WANDER_SPEED = 1.0D;
    private static final float LOOK_AT_PLAYER_DISTANCE = 6.0F;

    // Leash Offset Multipliers
    private static final float LEASH_OFFSET_HEIGHT_MULTIPLIER = 0.6F;
    private static final float LEASH_OFFSET_WIDTH_MULTIPLIER = 0.4F;

    private static final TrackedData<Integer> FROST_COUNTER = DataTracker.registerData(SnuffleEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> DATA_HAIRSTYLE_ID = DataTracker.registerData(SnuffleEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Boolean> DATA_FLUFF = DataTracker.registerData(SnuffleEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> DATA_FROSTY = DataTracker.registerData(SnuffleEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> IS_LICKING = DataTracker.registerData(SnuffleEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    private int fluffGrowTime = FLUFF_GROWTH_BASE_TICKS + this.getRandom().nextInt(FLUFF_GROWTH_RANDOM_TICKS);

    public SnuffleEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createSnuffleAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, MAX_HEALTH)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, MOVEMENT_SPEED)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, ATTACK_DAMAGE);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(2, new EscapeDangerGoal(this, ESCAPE_DANGER_SPEED));
        this.goalSelector.add(3, new AnimalMateGoal(this, MATING_SPEED));
        this.goalSelector.add(4, new SnuffleTemptGoal(TEMPT_SPEED, Ingredient.fromTag(ModTags.Items.SNUFFLE_FOOD), false));
        this.goalSelector.add(5, new FollowParentGoal(this, FOLLOW_PARENT_SPEED));
        this.goalSelector.add(6, new FrostGoal());
        this.goalSelector.add(7, new WanderAroundFarGoal(this, WANDER_SPEED));
        this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, LOOK_AT_PLAYER_DISTANCE));
        this.goalSelector.add(9, new LookAroundGoal(this));
    }

    @Override
    public Vec3d getLeashOffset() {
        return new Vec3d(0.0D, LEASH_OFFSET_HEIGHT_MULTIPLIER * this.getStandingEyeHeight(), this.getWidth() * LEASH_OFFSET_WIDTH_MULTIPLIER);
    }

    /*
     * Data Methods
     */

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(FROST_COUNTER, 0);
        this.dataTracker.startTracking(DATA_HAIRSTYLE_ID, 0);
        this.dataTracker.startTracking(DATA_FLUFF, false);
        this.dataTracker.startTracking(DATA_FROSTY, false);
        this.dataTracker.startTracking(IS_LICKING, false);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound compound) {
        super.writeCustomDataToNbt(compound);
        compound.putInt("Hairstyle", this.getHairstyleId());
        compound.putBoolean("HasFluff", this.hasFluff());
        compound.putBoolean("Frosty", this.isFrosty());
        compound.putInt("FluffGrowTime", this.fluffGrowTime);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound compound) {
        super.readCustomDataFromNbt(compound);
        this.setHairstyleId(compound.getInt("Hairstyle"));
        this.setFluff(compound.getBoolean("HasFluff"));
        this.setFrosty(compound.getBoolean("Frosty"));
        this.fluffGrowTime = compound.getInt("FluffGrowTime");
    }

    public void setFrostCounter(int counter) {
        this.dataTracker.set(FROST_COUNTER, counter);
    }

    public int getFrostCounter() {
        return this.dataTracker.get(FROST_COUNTER);
    }

    public boolean isFrosting() {
        return this.getFrostCounter() > 0;
    }

    public void setHairstyleId(int id) {
        this.dataTracker.set(DATA_HAIRSTYLE_ID, id);
    }

    public int getHairstyleId() {
        return MathHelper.clamp(this.dataTracker.get(DATA_HAIRSTYLE_ID), MIN_HAIRSTYLE_ID, MAX_HAIRSTYLE_ID);
    }

    public Hairstyle getHairstyle() {
        return Hairstyle.getHairstyleById(this.getHairstyleId());
    }

    public void setFluff(boolean hasFluff) {
        this.dataTracker.set(DATA_FLUFF, hasFluff);
    }

    public boolean hasFluff() {
        return this.dataTracker.get(DATA_FLUFF);
    }

    public void setFrosty(boolean isFrosty) {
        this.dataTracker.set(DATA_FROSTY, isFrosty);
    }

    public boolean isFrosty() {
        return this.dataTracker.get(DATA_FROSTY);
    }

    public void setLicking(boolean isLicking) {
        this.dataTracker.set(IS_LICKING, isLicking);
    }

    public boolean isLicking() {
        return this.dataTracker.get(IS_LICKING);
    }

    /*
     * Frost & Fluff Methods
     */

    @Override
    public void tick() {
        super.tick();

        if (this.isFrosty()) {
            if (this.isOnFire()) {
                this.extinguish();
                this.setFrosty(false);
                this.playExtinguishSound();
            }

            if ((this.lastRenderX != this.getX() || this.lastRenderY != this.getY() || this.lastRenderZ != this.getZ()) && this.getRandom().nextBoolean())
                this.getWorld().addParticle(ModParticles.SNOWFLAKE, this.getParticleX(PARTICLE_OFFSET_SMALL), this.getRandomBodyY(), this.getParticleZ(PARTICLE_OFFSET_SMALL), 0.0D, 0.0D, 0.0D);
        }

        if (!this.hasFluff() && !this.isBaby()) {
            if (this.fluffGrowTime > 0)
                --this.fluffGrowTime;
            else
                this.setFluff(true);
        }
    }

    private boolean isSnowingAt(World world, BlockPos pos) {
        if (!world.isRaining())
            return false;
        else if (!world.isSkyVisible(pos))
            return false;
        else if (world.getTopPosition(Heightmap.Type.MOTION_BLOCKING, pos).getY() > pos.getY())
            return false;
        else
            return world.getBiome(pos).value().isCold(pos);
    }

    /*
     * Interaction Methods
     */

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);

        if (stack.isOf(Items.SLIME_BALL)) {
            if (!this.getWorld().isClient()) {
                this.eat(player, hand, stack);
                this.setHairstyleId((this.getHairstyleId() + 1) % MAX_HAIRSTYLES);
                this.playSound(ModSounds.ENTITY_SNUFFLE_STYLE, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
            }

            return ActionResult.success(this.getWorld().isClient);
        } else if (stack.isOf(Items.MAGMA_CREAM) && this.isFrosty()) {
            if (!this.getWorld().isClient()) {
                this.setFrosty(false);
                this.eat(player, hand, stack);
                this.playSound(ModSounds.ENTITY_SNUFFLE_THAW, 0.7F, 1.6F + (this.random.nextFloat() - this.random.nextFloat()) * 0.4F);
            }

            return ActionResult.success(this.getWorld().isClient);
        } else  if (stack.isIn(ConventionalItemTags.SHEARS)) {
            if (!this.getWorld().isClient() && this.isShearable()) {
                this.sheared(SoundCategory.PLAYERS);
                this.emitGameEvent(GameEvent.SHEAR, player);
                stack.damage(1, player, (playerx) -> playerx.sendToolBreakStatus(hand));

                return ActionResult.SUCCESS;
            }
            return ActionResult.CONSUME;
        }

        return super.interactMob(player, hand);
    }

    @Override
    protected void eat(PlayerEntity player, Hand hand, ItemStack stack) {
        if (this.isBreedingItem(stack))
            this.playSound(ModSounds.ENTITY_SNUFFLE_EAT, 1.0F, 1.0F);

        super.eat(player, hand, stack);
    }

    /*
     * Sound Methods
     */

    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.ENTITY_SNUFFLE_AMBIENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.ENTITY_SNUFFLE_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSounds.ENTITY_SNUFFLE_HURT;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(ModSounds.ENTITY_SNUFFLE_STEP, 0.15F, 1.0F);
    }

    /*
     * Client Method
     */

    @Override
    public void handleStatus(byte id) {
        if (id == 10) {
            for (int i = 0; i < FROST_PARTICLE_COUNT; i ++)
                this.getWorld().addParticle(ModParticles.SNOWFLAKE, this.getParticleX(PARTICLE_OFFSET_LARGE), this.getEyeY(), this.getParticleZ(PARTICLE_OFFSET_LARGE), 0.0D, 0.1D, 0.0D);
        } else
            super.handleStatus(id);
    }

    /*
     * Shearing Methods
     */

    @Override
    public void sheared(SoundCategory shearedSoundCategory) {
        this.getWorld().playSoundFromEntity(null, this, ModSounds.ENTITY_SNUFFLE_SHEAR, shearedSoundCategory, 1.0F, 1.0F);
        this.setFluff(false);
        this.fluffGrowTime = FLUFF_GROWTH_BASE_TICKS + this.getRandom().nextInt(FLUFF_GROWTH_RANDOM_TICKS);
        ItemEntity itemEntity = this.dropStack(new ItemStack(this.isFrosty() ? ModBlocks.FROSTY_FLUFF : ModBlocks.SNUFFLE_FLUFF), 1.0F);
        if (itemEntity != null) {
            itemEntity.setVelocity(itemEntity.getVelocity()
                    .add((
                                    this.random.nextFloat() - this.random.nextFloat()) * 0.1f,
                            this.random.nextFloat() * 0.05f,
                            (this.random.nextFloat() - this.random.nextFloat()) * 0.1f));
        }
    }

    @Override
    public boolean isShearable() {
        return this.isAlive() && !this.isBaby() && this.hasFluff();
    }

    public static boolean checkSnuffleSpawnRules(EntityType<SnuffleEntity> snuffle, WorldAccess world, SpawnReason spawnType, BlockPos pos, Random random) {
        return world.getBlockState(pos.down()).isIn(ModTags.Blocks.SNUFFLES_SPAWNABLE_ON) && isLightLevelValidForNaturalSpawn(world, pos);
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnType, @Nullable EntityData groupData, @Nullable NbtCompound compound) {
        boolean frosty = world.getBiome(this.getBlockPos()).value().isCold(this.getBlockPos());

        if (groupData instanceof SnuffleGroupData)
            frosty = ((SnuffleGroupData) groupData).frosty;
        else
            groupData = new SnuffleGroupData(frosty);

        SnuffleGroupData snuffleGroupData = (SnuffleGroupData) groupData;
        if (snuffleGroupData.getSpawnedCount() > 0 && this.random.nextFloat() <= snuffleGroupData.getBabyChance())
            this.setBreedingAge(BABY_BREEDING_AGE_TICKS);
        else
            this.setFluff(true);

        this.setFrosty(frosty);
        return super.initialize(world, difficulty, spawnType, groupData, compound);
    }

    /*
     * Breeding Methods
     */

    @Nullable
    @Override
    public SnuffleEntity createChild(ServerWorld world, PassiveEntity entity) {
        return ModEntities.SNUFFLE.create(world);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.isIn(ModTags.Items.SNUFFLE_FOOD);
    }

    /*
     * Death Method
     */

    @Override
    public void onDeath(DamageSource source) {
        this.setFrostCounter(0);
        super.onDeath(source);
    }

    /*
     * Data
     */

    public static class SnuffleGroupData extends PassiveEntity.PassiveData {
        public final boolean frosty;

        public SnuffleGroupData(boolean frosty) {
            super(false);
            this.frosty = frosty;
        }
    }

    /*
     * Hairstyle Enum
     */

    public enum Hairstyle {
        DEFAULT,
        SHEEPDOG,
        PORO,
        HORSESHOE;

        private static final Hairstyle[] HAIRSTYLES = Hairstyle.values();

        public static Hairstyle getHairstyleById(int id) {
            return HAIRSTYLES[id];
        }
    }

    /*
     * AI Goals
     */

    class SnuffleTemptGoal extends TemptGoal {
        public SnuffleTemptGoal(double speedModifier, Ingredient items, boolean canScare) {
            super(SnuffleEntity.this, speedModifier, items, canScare);
        }

        public void start() {
            super.start();
            SnuffleEntity.this.setLicking(true);
        }

        public void stop() {
            super.stop();
            SnuffleEntity.this.setLicking(false);
        }
    }

    class FrostGoal extends Goal {
        private static final int WAIT_TIME_BEFORE_FROST = toGoalTicks(140);
        private int countdown = SnuffleEntity.this.random.nextInt(WAIT_TIME_BEFORE_FROST);

        public FrostGoal() {
            this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK, Goal.Control.JUMP));
        }

        @Override
        public boolean canStart() {
            return SnuffleEntity.this.sidewaysSpeed == 0.0F && SnuffleEntity.this.upwardSpeed == 0.0F && SnuffleEntity.this.forwardSpeed == 0.0F && this.canFrost();
        }

        @Override
        public boolean shouldContinue() {
            return SnuffleEntity.this.isFrosting() && this.canFrost();
        }

        private boolean canFrost() {
            if (this.countdown > 0) {
                --this.countdown;
                return false;
            } else {
                World world = SnuffleEntity.this.getWorld();
                BlockPos pos = SnuffleEntity.this.getBlockPos();
                return !SnuffleEntity.this.isFrosty() && (SnuffleEntity.this.isSnowingAt(world, pos) || world.getBlockState(pos).isOf(Blocks.POWDER_SNOW));
            }
        }

        @Override
        public void tick() {
            SnuffleEntity.this.setFrostCounter(Math.max(0, SnuffleEntity.this.getFrostCounter() - 1));

            if (SnuffleEntity.this.getFrostCounter() % this.getTickCount(4) == 0) {
                SnuffleEntity.this.playSound(ModSounds.ENTITY_SNUFFLE_SHAKE, 1.0F, (SnuffleEntity.this.random.nextFloat() - SnuffleEntity.this.random.nextFloat()) * 0.2F + 1.0F);
                SnuffleEntity.this.setFrosty(SnuffleEntity.this.getFrostCounter() == this.getTickCount(4));
                SnuffleEntity.this.getWorld().sendEntityStatus(SnuffleEntity.this, (byte) 10);
            }
        }

        @Override
        public void start() {
            SnuffleEntity.this.setFrostCounter(this.getTickCount(40));
            SnuffleEntity.this.emitGameEvent(GameEvent.ENTITY_SHAKE);
            SnuffleEntity.this.getNavigation().stop();
        }

        @Override
        public void stop() {
            SnuffleEntity.this.setFrostCounter(0);
            this.countdown = SnuffleEntity.this.random.nextInt(WAIT_TIME_BEFORE_FROST);
        }
    }
}