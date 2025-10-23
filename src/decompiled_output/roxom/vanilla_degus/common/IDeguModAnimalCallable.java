package roxom.vanilla_degus.common;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1813;
import net.minecraft.class_2338;

public interface IDeguModAnimalCallable {
   @Environment(EnvType.CLIENT)
   void OnListeningSong(class_1813 var1, class_2338 var2, boolean var3);
}
