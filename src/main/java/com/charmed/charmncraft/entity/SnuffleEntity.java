package com.charmed.charmncraft.entity;

import com.charmed.charmncraft.ModSounds;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Shearable;
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
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class SnuffleEntity extends AnimalEntity implements Shearable {
    private static final TrackedData<Boolean> SHEARED = DataTracker.registerData(SnuffleEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Integer> VARIANT = DataTracker.registerData(SnuffleEntity.class, TrackedDataHandlerRegistry.INTEGER);

    public SnuffleEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createSnuffleAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 12.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.23);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new EscapeDangerGoal(this, 1.25));
        this.goalSelector.add(2, new AnimalMateGoal(this, 1.0));
        this.goalSelector.add(3, new TemptGoal(this, 1.1, Ingredient.ofItems(Items.SWEET_BERRIES, Items.GLOW_BERRIES), false));
        this.goalSelector.add(4, new FollowParentGoal(this, 1.1));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 6.0f));
        this.goalSelector.add(7, new LookAroundGoal(this));
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(SHEARED, false);
        this.dataTracker.startTracking(VARIANT, 0);
    }

    public boolean isSheared() {
        return this.dataTracker.get(SHEARED);
    }

    public void setSheared(boolean sheared) {
        this.dataTracker.set(SHEARED, sheared);
    }

    public int getVariant() {
        return this.dataTracker.get(VARIANT);
    }

    public void setVariant(int variant) {
        this.dataTracker.set(VARIANT, variant);
    }

    @Override
    public boolean isShearable() {
        return !this.isSheared() && !this.isBaby();
    }

    @Override
    public void sheared(SoundCategory shearedSoundCategory) {
        this.getWorld().playSoundFromEntity(null, this, SoundEvents.ENTITY_SHEEP_SHEAR, shearedSoundCategory, 1.0f, 1.0f);
        this.setSheared(true);

        // Drop 1-3 snuffle fluff
        int fluffCount = 1 + this.random.nextInt(3);
        for (int i = 0; i < fluffCount; i++) {
            this.dropStack(new ItemStack(Items.WHITE_WOOL)); // Placeholder - will be replaced with snuffle_fluff item
        }
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        // TODO: Create baby snuffle with variant inheritance
        return null;
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.isOf(Items.SWEET_BERRIES) || stack.isOf(Items.GLOW_BERRIES);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.ENTITY_SNUFFLE_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSounds.ENTITY_SNUFFLE_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.ENTITY_SNUFFLE_DEATH;
    }

    @Override
    protected void playStepSound(net.minecraft.util.math.BlockPos pos, net.minecraft.block.BlockState state) {
        this.playSound(ModSounds.ENTITY_SNUFFLE_STEP, 0.15f, 1.0f);
    }
}
