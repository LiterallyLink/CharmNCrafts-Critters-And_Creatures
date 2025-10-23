package roxom.vanilla_degus.mixin;

import net.minecraft.class_1309;
import net.minecraft.class_1813;
import net.minecraft.class_2338;
import net.minecraft.class_238;
import net.minecraft.class_3414;
import net.minecraft.class_638;
import net.minecraft.class_761;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import roxom.vanilla_degus.common.IDeguModAnimalCallable;

@Mixin({class_761.class})
abstract class WorldRendererMixin {
   private class_638 world;

   @Inject(
      method = {"setWorld"},
      at = {@At("TAIL")}
   )
   private void setWorldInjected(@Nullable class_638 world, CallbackInfo info) {
      this.world = world;
   }

   @Inject(
      method = {"playSong"},
      at = {@At("TAIL")}
   )
   private void NotifyJukeboxEvent(class_3414 song, class_2338 songPosition, CallbackInfo info) {
      class_1813 musicDiscItem = class_1813.method_8012(song);

      for (class_1309 livingEntity : this.world.method_18467(class_1309.class, new class_238(songPosition).method_1014(3.0))) {
         if (livingEntity instanceof IDeguModAnimalCallable dmAnimal) {
            dmAnimal.OnListeningSong(musicDiscItem, songPosition, song != null);
         }
      }
   }
}
