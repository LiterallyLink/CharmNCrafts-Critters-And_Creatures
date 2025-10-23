package roxom.vanilla_degus.common;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1266;
import net.minecraft.class_1268;
import net.minecraft.class_1269;
import net.minecraft.class_1282;
import net.minecraft.class_1293;
import net.minecraft.class_1294;
import net.minecraft.class_1296;
import net.minecraft.class_1299;
import net.minecraft.class_1308;
import net.minecraft.class_1309;
import net.minecraft.class_1315;
import net.minecraft.class_1321;
import net.minecraft.class_1338;
import net.minecraft.class_1341;
import net.minecraft.class_1347;
import net.minecraft.class_1350;
import net.minecraft.class_1361;
import net.minecraft.class_1374;
import net.minecraft.class_1376;
import net.minecraft.class_1386;
import net.minecraft.class_1391;
import net.minecraft.class_1429;
import net.minecraft.class_1430;
import net.minecraft.class_1451;
import net.minecraft.class_1456;
import net.minecraft.class_1463;
import net.minecraft.class_1493;
import net.minecraft.class_1657;
import net.minecraft.class_1767;
import net.minecraft.class_1769;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_1813;
import net.minecraft.class_1856;
import net.minecraft.class_1924;
import net.minecraft.class_1935;
import net.minecraft.class_1936;
import net.minecraft.class_1937;
import net.minecraft.class_1959;
import net.minecraft.class_1972;
import net.minecraft.class_2246;
import net.minecraft.class_2338;
import net.minecraft.class_2394;
import net.minecraft.class_2398;
import net.minecraft.class_2487;
import net.minecraft.class_2498;
import net.minecraft.class_2680;
import net.minecraft.class_2940;
import net.minecraft.class_2943;
import net.minecraft.class_2945;
import net.minecraft.class_3218;
import net.minecraft.class_3414;
import net.minecraft.class_3481;
import net.minecraft.class_3532;
import net.minecraft.class_3701;
import net.minecraft.class_3730;
import net.minecraft.class_4019;
import net.minecraft.class_4048;
import net.minecraft.class_4050;
import net.minecraft.class_5134;
import net.minecraft.class_5425;
import net.minecraft.class_5819;
import net.minecraft.class_6880;
import net.minecraft.class_6908;
import net.minecraft.class_5132.class_5133;
import org.jetbrains.annotations.Nullable;
import roxom.vanilla_degus.DeguMod;

public class DeguEntity extends class_1321 implements IDeguModAnimalCallable {
   public final EntityRecordListener recordListener = new EntityRecordListener(this);
   private static final class_1856 BREEDING_ITEMS = class_1856.method_8091(
      new class_1935[]{class_1802.field_8602, class_1802.field_8256, class_1802.field_8689}
   );
   private static final class_1856 OTHER_FAVORITE_ITEMS = class_1856.method_8091(
      new class_1935[]{
         class_1802.field_8491,
         class_1802.field_8880,
         class_1802.field_17525,
         class_1802.field_17520,
         class_1802.field_8317,
         class_1802.field_8706,
         class_1802.field_8309,
         class_1802.field_8188,
         class_1802.field_42696
      }
   );
   private static final class_1856 JUNK_FOOD_ITEMS = class_1856.method_8091(
      new class_1935[]{
         class_1802.field_8861,
         class_1802.field_8179,
         class_1802.field_8567,
         class_1802.field_8186,
         class_1802.field_8279,
         class_1802.field_16998,
         class_1802.field_28659,
         class_1802.field_8497
      }
   );
   private static final class_1856 DENGER_FOOD_ITEMS = class_1856.method_8091(
      new class_1935[]{
         class_1802.field_17534,
         class_1802.field_8741,
         class_1802.field_8423,
         class_1802.field_20414,
         class_1802.field_20417,
         class_1802.field_17522,
         class_1802.field_17518,
         class_1802.field_8229
      }
   );
   private static final class_2940<Integer> DATA_TYPE_ID = class_2945.method_12791(DeguEntity.class, class_2943.field_13327);
   private static final class_2940<Integer> COLLAR_COLOR = class_2945.method_12791(DeguEntity.class, class_2943.field_13327);
   private static final class_2940<Float> OBESITY = class_2945.method_12791(DeguEntity.class, class_2943.field_13320);
   private static final class_2940<Float> HAPPINESS = class_2945.method_12791(DeguEntity.class, class_2943.field_13320);
   private static final class_2940<Float> DEGUS_GRACE_TIMER = class_2945.method_12791(DeguEntity.class, class_2943.field_13320);
   private static final class_2940<Float> HUNGER_TIMER = class_2945.method_12791(DeguEntity.class, class_2943.field_13320);
   protected static final float DEFAULT_HEAL_AMOUNT = 2.0F;
   protected static final float MAX_HAPPINESS = 10.0F;

   public DeguEntity(class_1299<? extends class_1321> entityType, class_1937 world) {
      super(entityType, world);
   }

   protected void method_5693() {
      super.method_5693();
      this.field_6011.method_12784(DATA_TYPE_ID, 0);
      this.field_6011.method_12784(COLLAR_COLOR, class_1767.field_7964.method_7789());
      this.field_6011.method_12784(OBESITY, 3.0F);
      this.field_6011.method_12784(HAPPINESS, 0.0F);
      this.field_6011.method_12784(DEGUS_GRACE_TIMER, 0.0F);
      this.field_6011.method_12784(HUNGER_TIMER, 0.0F);
   }

   public void method_5652(class_2487 nbt) {
      super.method_5652(nbt);
      nbt.method_10569("DeguType", this.getDeguType());
      nbt.method_10567("CollarColor", (byte)this.getCollarColor().method_7789());
      nbt.method_10548("Obesity", this.getObesity());
      nbt.method_10548("Happiness", this.getHappiness());
      nbt.method_10548("DegusGrace", this.getDegusGraceTimer());
      nbt.method_10548("Hunger", this.getHungerTimer());
   }

   public void method_5749(class_2487 tag) {
      super.method_5749(tag);
      if (tag.method_10573("DeguType", 3)) {
         this.setDeguType(tag.method_10550("DeguType"));
      }

      if (tag.method_10573("CollarColor", 5)) {
         this.setCollarColor(class_1767.method_7791(tag.method_10550("CollarColor")));
      }

      if (tag.method_10573("Obesity", 5)) {
         this.setObesity(tag.method_10583("Obesity"));
      }

      if (tag.method_10573("Happiness", 5)) {
         this.setHappiness(tag.method_10583("Happiness"));
      }

      if (tag.method_10573("DegusGrace", 5)) {
         this.setDegusGraceTimer(tag.method_10583("DegusGrace"));
      }

      if (tag.method_10573("Hunger", 5)) {
         this.setDegusGraceTimer(tag.method_10583("Hunger"));
      }
   }

   public int getDeguType() {
      return (Integer)this.field_6011.method_12789(DATA_TYPE_ID);
   }

   private void setDeguType(int dataType) {
      this.field_6011.method_12778(DATA_TYPE_ID, dataType);
   }

   public class_1767 getCollarColor() {
      return class_1767.method_7791((Integer)this.field_6011.method_12789(COLLAR_COLOR));
   }

   private void setCollarColor(class_1767 color) {
      this.field_6011.method_12778(COLLAR_COLOR, color.method_7789());
   }

   public float getObesity() {
      return (Float)this.field_6011.method_12789(OBESITY);
   }

   public void setObesity(float obesity) {
      this.field_6011.method_12778(OBESITY, class_3532.method_15363(obesity, 0.0F, 10.0F));
   }

   public float getHappiness() {
      return (Float)this.field_6011.method_12789(HAPPINESS);
   }

   public void setHappiness(float happiness) {
      this.field_6011.method_12778(HAPPINESS, class_3532.method_15363(happiness, 0.0F, 10.0F));
   }

   public float getDegusGraceTimer() {
      return (Float)this.field_6011.method_12789(DEGUS_GRACE_TIMER);
   }

   public void setDegusGraceTimer(float timer) {
      this.field_6011.method_12778(DEGUS_GRACE_TIMER, timer);
   }

   public float getHungerTimer() {
      return (Float)this.field_6011.method_12789(HUNGER_TIMER);
   }

   public void setHungerTimer(float timer) {
      this.field_6011.method_12778(HUNGER_TIMER, timer);
   }

   public static class_5133 createDeguAttributes() {
      return class_1308.method_26828().method_26868(class_5134.field_23716, 10.0).method_26868(class_5134.field_23719, 0.25);
   }

   protected void method_5959() {
      this.field_6201.method_6277(0, new class_1347(this));
      this.field_6201.method_6277(2, new class_1386(this));
      this.field_6201.method_6277(2, new class_1374(this, 2.0));
      this.field_6201.method_6277(3, new class_1341(this, 1.0));
      this.field_6201.method_6277(4, new class_1391(this, 1.5, DENGER_FOOD_ITEMS, false));
      this.field_6201.method_6277(4, new class_1391(this, 1.35, JUNK_FOOD_ITEMS, false));
      this.field_6201.method_6277(4, new class_1391(this, 1.25, OTHER_FAVORITE_ITEMS, false));
      this.field_6201.method_6277(5, new class_1391(this, 1.25, BREEDING_ITEMS, false));
      this.field_6201.method_6277(6, new AvoidFightingGoal(this, 5.0F, 1.6, 1.6));
      this.field_6201.method_6277(6, new class_1338(this, class_1451.class, 8.0F, 1.6, 1.4, other -> !((class_1451)other).method_6181()));
      this.field_6201.method_6277(6, new class_1338(this, class_1493.class, 8.0F, 1.6, 1.4, other -> !((class_1493)other).method_6181()));
      this.field_6201.method_6277(6, new class_1338(this, class_4019.class, 8.0F, 1.6, 1.4));
      this.field_6201.method_6277(6, new class_1338(this, class_3701.class, 8.0F, 1.6, 1.4));
      this.field_6201.method_6277(6, new class_1338(this, class_1456.class, 8.0F, 1.6, 1.4));
      this.field_6201.method_6277(7, new DeguEatPlantGoal(this, 1.25, 8, 1));
      this.field_6201.method_6277(8, new class_1350(this, 1.25, 10.0F, 3.0F, false));
      this.field_6201.method_6277(9, new TameableFollowParentGoal(this, 1.25));
      this.field_6201.method_6277(10, new UnsmoothRandomWalkingGoal(this, 1.5, 60));
      this.field_6201.method_6277(11, new class_1361(this, class_1657.class, 6.0F));
      this.field_6201.method_6277(12, new class_1376(this));
      this.field_6201.method_6277(13, new FollowSpecificMobGoal(this, class_1430.class, 1.25, 1.0F, 7.0F, 60));
      this.field_6201.method_6277(14, new FollowSpecificMobGoal(this, class_1463.class, 1.25, 1.0F, 7.0F, 60));
   }

   protected float method_18394(class_4050 pose, class_4048 dimensions) {
      return dimensions.field_18068 * 0.5F;
   }

   public int method_20240() {
      return 1000;
   }

   public int method_5986() {
      return 750;
   }

   protected int method_23329(float fallDistance, float damageMultiplier) {
      return class_3532.method_15386((fallDistance - 5.0F) * damageMultiplier);
   }

   public boolean method_6481(class_1799 stack) {
      return BREEDING_ITEMS.method_8093(stack);
   }

   private boolean isHealableItem(class_1799 stack) {
      return BREEDING_ITEMS.method_8093(stack)
         || OTHER_FAVORITE_ITEMS.method_8093(stack)
         || JUNK_FOOD_ITEMS.method_8093(stack)
         || DENGER_FOOD_ITEMS.method_8093(stack);
   }

   protected class_3414 method_5994() {
      return DeguMod.DEGU_SOUND_AMBIENT;
   }

   protected class_3414 method_6011(class_1282 damageSource) {
      return DeguMod.DEGU_SOUND_HURT;
   }

   protected class_3414 method_6002() {
      return DeguMod.DEGU_SOUND_DEATH;
   }

   protected void method_5712(class_2338 pos, class_2680 state) {
      if (!this.method_5701()) {
         class_2680 blockState = this.method_37908().method_8320(pos.method_10084());
         class_2498 blockSoundGroup = blockState.method_26164(class_3481.field_28040) ? blockState.method_26231() : state.method_26231();
         this.method_5783(blockSoundGroup.method_10594(), blockSoundGroup.method_10597() * 0.05F, blockSoundGroup.method_10599() * 2.0F);
      }
   }

   public class_1296 method_5613(class_3218 world, class_1296 partner) {
      DeguEntity childDeguEntity = (DeguEntity)DeguMod.DEGU.method_5883(world);

      assert childDeguEntity != null;

      if (partner instanceof DeguEntity) {
         if (this.field_5974.method_43056()) {
            childDeguEntity.setDeguType(this.getDeguType());
         } else {
            childDeguEntity.setDeguType(((DeguEntity)partner).getDeguType());
         }

         if (this.method_6181()) {
            childDeguEntity.method_6174(this.method_6139());
            childDeguEntity.method_6173(true);
            if (this.field_5974.method_43056()) {
               childDeguEntity.setCollarColor(this.getCollarColor());
            } else {
               childDeguEntity.setCollarColor(((DeguEntity)partner).getCollarColor());
            }
         }
      }

      return childDeguEntity;
   }

   public class_1269 method_5992(class_1657 player, class_1268 hand) {
      class_1799 itemStack = player.method_5998(hand);
      class_1792 item = itemStack.method_7909();
      boolean tamable = this.method_6481(itemStack) && !this.method_6181();
      boolean healable = this.isHealableItem(itemStack) && this.method_6032() < this.method_6063() && this.method_6181();
      boolean dyeable = this.method_6171(player) && item instanceof class_1769;
      if (this.method_37908().field_9236) {
         boolean bl = tamable || healable || dyeable || this.method_6181();
         return bl ? class_1269.field_21466 : class_1269.field_5811;
      } else if (tamable) {
         this.method_6475(player, hand, itemStack);
         this.method_6025(2.0F);
         if (this.field_5974.method_43048(3) == 0) {
            this.setObesity(3.0F);
            this.method_6170(player);
            this.method_37908().method_8421(this, (byte)7);
         } else {
            this.method_37908().method_8421(this, (byte)6);
         }

         return class_1269.field_5812;
      } else if (healable) {
         this.eatDeguFood(itemStack, player);
         this.method_5783(this.method_18869(itemStack), 0.1F, 10.0F);
         this.method_6475(player, hand, itemStack);
         this.tryToAddPoison();
         return class_1269.field_5812;
      } else if (dyeable) {
         class_1767 dyecolor = ((class_1769)item).method_7802();
         if (dyecolor != this.getCollarColor()) {
            this.method_6475(player, hand, itemStack);
            this.setCollarColor(dyecolor);
         }

         return class_1269.field_5812;
      } else if (!this.method_6181()) {
         return class_1269.field_5811;
      } else {
         if (this.method_6481(itemStack)) {
            if (this.method_6482() || this.method_6109()) {
               if (this.method_6109() && this.method_6171(player)) {
                  this.method_24346(!this.method_24345());
               }

               this.eatDeguFood(itemStack, player);
               this.tryToAddDeguGrace(player);
               return super.method_5992(player, hand);
            }
         } else if (this.isHealableItem(itemStack) && !this.method_6481(itemStack) && this.getHungerTimer() <= 0.0F) {
            this.eatDeguFood(itemStack, player);
            this.method_6475(player, hand, itemStack);
            this.setHungerTimer(480.0F);
            this.method_37908().method_8421(this, (byte)102);
            return class_1269.field_5812;
         }

         if (this.method_6171(player)) {
            this.method_24346(!this.method_24345());
            return class_1269.field_5812;
         } else {
            return class_1269.field_5811;
         }
      }
   }

   public boolean method_5643(class_1282 source, float amount) {
      boolean retValue = super.method_5643(source, amount);
      if (this.method_6065() == this.method_35057()) {
         this.setHappiness(this.getHappiness() - 2.0F);
      }

      return retValue;
   }

   public void method_5983() {
      if (this.method_6109()) {
         this.method_5615(60);
      }

      this.eatDeguFood(new class_1799(class_1802.field_8602), null);
   }

   protected void eatDeguFood(class_1799 itemStack, class_1657 feedingPlayer) {
      boolean isFeedByOwner = feedingPlayer != null && feedingPlayer == this.method_35057();
      float prevObesity = this.getObesity();
      float prevHappiness = this.getHappiness();
      boolean isHappinessChanged = true;
      if (class_1856.method_8091(new class_1935[]{class_1802.field_28659}).method_8093(itemStack)) {
         this.method_6092(new class_1293(class_1294.field_5912, 200));
      }

      if (BREEDING_ITEMS.method_8093(itemStack)) {
         this.method_6025(2.0F);
         this.setObesity(prevObesity - 0.3125F);
         if (isFeedByOwner) {
            this.setHappiness(prevHappiness + 0.0625F);
         }
      } else if (OTHER_FAVORITE_ITEMS.method_8093(itemStack)) {
         this.method_6025(1.0F);
         this.setObesity(prevObesity + 0.09375F);
         if (isFeedByOwner) {
            this.setHappiness(prevHappiness + 0.0625F);
         }
      } else if (JUNK_FOOD_ITEMS.method_8093(itemStack)) {
         this.method_6025(5.0F);
         this.setObesity(prevObesity + 0.375F);
         if (isFeedByOwner) {
            this.setHappiness(prevHappiness + 0.3125F);
         }
      } else if (DENGER_FOOD_ITEMS.method_8093(itemStack)) {
         this.method_6025(10.0F);
         this.setObesity(prevObesity + 0.75F);
         if (isFeedByOwner) {
            this.setHappiness(prevHappiness + 0.75F);
         }
      } else {
         isHappinessChanged = false;
      }

      int prevFatState = this.getFatState(prevObesity);
      int currFatState = this.getFatState(this.getObesity());
      if (prevFatState < currFatState) {
         this.method_37908().method_8421(this, (byte)100);
      } else if (prevFatState > currFatState) {
         this.method_37908().method_8421(this, (byte)101);
      }
   }

   private int getFatState(float obesity) {
      if (obesity <= 5.0F) {
         return 0;
      } else {
         return obesity <= 8.0F ? 1 : 2;
      }
   }

   protected void tryToAddDeguGrace(class_1657 player) {
      if (!this.method_6109() && this.method_6181() && this.method_6171(player)) {
         if ((double)this.getHappiness() >= 8.0 && !this.method_6109()) {
            this.method_6092(new class_1293(DeguMod.DEGUS_GRACE_EFFECT, 20));
            this.setDegusGraceTimer(480.0F);
         }
      }
   }

   protected void tryToAddPoison() {
      if (!this.method_6109() && this.method_6181()) {
         if ((double)this.getObesity() >= 9.5) {
            this.method_6092(new class_1293(class_1294.field_5899, 90));
            this.setObesity(9.0F);
         }
      }
   }

   @Environment(EnvType.CLIENT)
   @Override
   public void OnListeningSong(class_1813 musicDiscItem, class_2338 songPosition, boolean listening) {
      this.recordListener.setJukebox(musicDiscItem, songPosition, listening);
   }

   @Environment(EnvType.CLIENT)
   public boolean isDancing() {
      return this.recordListener.getListeningSong() && this.method_6172();
   }

   public void method_6007() {
      super.method_6007();
      if (this.method_37908().field_9236) {
         this.recordListener.tick();
      }
   }

   public void method_5958() {
      super.method_5958();
      if (this.method_6181() && this.method_35057() != null) {
         if (this.getDegusGraceTimer() > 0.0F && this.method_5858(this.method_35057()) < 100.0 && this.field_5974.method_43048(6) == 0) {
            this.method_35057().method_6092(new class_1293(DeguMod.DEGUS_GRACE_EFFECT, 180));
         }

         if (this.getDegusGraceTimer() > 0.0F && this.field_6012 % 20 == 0) {
            this.setDegusGraceTimer(this.getDegusGraceTimer() - 1.0F);
         }

         if (this.getHappiness() < 10.0F && this.field_6012 % 20 == 0 && this.method_5858(this.method_35057()) < 100.0) {
            this.setHappiness(this.getHappiness() + 0.0010416667F);
         }
      }

      if (this.method_6181() && this.getHungerTimer() > 0.0F && this.field_6012 % 20 == 0) {
         this.setHungerTimer(this.getHungerTimer() - 1.0F);
      }
   }

   public void method_5711(byte status) {
      if (status == 100) {
         this.produceParticles(class_2398.field_11245);
      } else if (status == 101) {
         this.produceParticles(class_2398.field_11213);
      } else if (status == 102) {
         this.produceParticles(class_2398.field_11201);
      } else if (status == 103) {
         this.produceParticles(class_2398.field_11211);
      } else if (status == 104) {
         this.produceParticles(class_2398.field_11231);
      } else {
         super.method_5711(status);
      }
   }

   protected void produceParticles(class_2394 parameter) {
      for (int i = 0; i < 2; i++) {
         double d = this.field_5974.method_43059() * 0.02;
         double e = this.field_5974.method_43059() * 0.02;
         double f = this.field_5974.method_43059() * 0.02;
         this.method_37908().method_8406(parameter, this.method_23322(1.0), this.method_23319() + 0.2, this.method_23325(1.0), d, e, f);
      }
   }

   @Nullable
   public class_1315 method_5943(
      class_5425 serverWorld, class_1266 difficulty, class_3730 reason, @Nullable class_1315 entityData, @Nullable class_2487 entityNbt
   ) {
      if (reason != class_3730.field_16462 && reason != class_3730.field_16465) {
         this.setDeguType(this.getRandomDeguTypeByBiome(serverWorld));
      } else {
         this.setDeguType(this.getRandomDeguType());
      }

      return super.method_5943(serverWorld, difficulty, reason, entityData, entityNbt);
   }

   private int getRandomDeguType() {
      return this.field_5974.method_43048(6);
   }

   private int getRandomDeguTypeByBiome(class_1936 world) {
      class_6880<class_1959> biome = world.method_23753(this.method_24515());
      class_2680 currentBlock = world.method_8320(this.method_24515());
      class_2680 footBlock = world.method_8320(this.method_24515().method_10074());
      if (footBlock.method_26164(class_3481.field_25806)) {
         return 2;
      } else if (footBlock.method_27852(class_2246.field_10143)) {
         return 1;
      } else if (footBlock.method_27852(class_2246.field_27114) || footBlock.method_27852(class_2246.field_10611)) {
         return 4;
      } else if (footBlock.method_26164(class_3481.field_29193) || footBlock.method_27852(class_2246.field_10123)) {
         return 5;
      } else if (currentBlock.method_27852(class_2246.field_10573) || currentBlock.method_27852(class_2246.field_10554)) {
         return 4;
      } else if (biome.method_40225(class_1972.field_9424)) {
         int[] colorTypes = new int[]{1, 4};
         int[] weights = new int[]{5, 1};
         int index = this.getWeightedRandomIndex(weights);
         return colorTypes[index];
      } else if (biome.method_40220(class_6908.field_37392)) {
         int[] colorTypes = new int[]{0, 2, 3, 5};
         int[] weights = new int[]{12, 4, 4, 1};
         int index = this.getWeightedRandomIndex(weights);
         return colorTypes[index];
      } else {
         int[] colorTypes = new int[]{0, 2, 3, 4, 5};
         int[] weights = new int[]{12, 4, 4, 1, 1};
         int index = this.getWeightedRandomIndex(weights);
         return colorTypes[index];
      }
   }

   private int getWeightedRandomIndex(int[] weights) {
      double totalWeight = 0.0;

      for (int weight : weights) {
         totalWeight += (double)weight;
      }

      int index = 0;

      for (double r = this.field_5974.method_43058() * totalWeight; index < weights.length - 1; index++) {
         r -= (double)weights[index];
         if (r <= 0.0) {
            break;
         }
      }

      return index;
   }

   public static boolean canSpawn(class_1299<? extends class_1429> type, class_5425 world, class_3730 spawnReason, class_2338 pos, class_5819 random) {
      return true;
   }

   public class_1924 method_48926() {
      return this.method_37908();
   }

   @Nullable
   public class_1309 method_35057() {
      return super.method_35057();
   }

   public boolean method_36320() {
      return super.method_36320();
   }

   public static class DeguEntityStatuses {
      public static final byte INCREASE_OBESITY = 100;
      public static final byte DECREASE_OBESITY = 101;
      public static final byte HEART = 102;
      public static final byte HAPPY = 103;
      public static final byte ANGRY = 104;
   }
}
