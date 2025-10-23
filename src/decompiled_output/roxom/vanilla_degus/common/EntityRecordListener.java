package roxom.vanilla_degus.common;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.class_1309;
import net.minecraft.class_1792;
import net.minecraft.class_1802;
import net.minecraft.class_1813;
import net.minecraft.class_2246;
import net.minecraft.class_2338;

public class EntityRecordListener {
   private final class_1309 entity;
   private class_2338 jukeboxPos;
   private class_1813 record;
   private boolean listeningSong;
   private static final Map<class_1792, Integer> RECORD_BPMS = new HashMap<>();

   public EntityRecordListener(class_1309 entity) {
      this.entity = entity;
   }

   public boolean getListeningSong() {
      return this.listeningSong;
   }

   public void setJukebox(class_1813 musicDiscItem, class_2338 jukeboxPos, boolean listening) {
      this.record = musicDiscItem;
      this.listeningSong = listening;
      this.jukeboxPos = jukeboxPos;
   }

   public void tick() {
      if (this.jukeboxPos == null
         || !this.jukeboxPos.method_19769(this.entity.method_19538(), 3.46)
         || !this.entity.method_37908().method_8320(this.jukeboxPos).method_27852(class_2246.field_10223)) {
         this.listeningSong = false;
         this.jukeboxPos = null;
      }
   }

   public float getCurrentRecordTempo() {
      return (float)RECORD_BPMS.getOrDefault(this.record, 110).intValue();
   }

   static {
      RECORD_BPMS.put(class_1802.field_8144, 77);
      RECORD_BPMS.put(class_1802.field_8075, 112);
      RECORD_BPMS.put(class_1802.field_8425, 110);
      RECORD_BPMS.put(class_1802.field_8623, 110);
      RECORD_BPMS.put(class_1802.field_8502, 82);
      RECORD_BPMS.put(class_1802.field_8534, 115);
      RECORD_BPMS.put(class_1802.field_8344, 90);
      RECORD_BPMS.put(class_1802.field_8834, 105);
      RECORD_BPMS.put(class_1802.field_8065, 94);
      RECORD_BPMS.put(class_1802.field_8355, 107);
      RECORD_BPMS.put(class_1802.field_8731, 0);
      RECORD_BPMS.put(class_1802.field_8806, 114);
      RECORD_BPMS.put(class_1802.field_35358, 92);
      RECORD_BPMS.put(class_1802.field_38973, 0);
      RECORD_BPMS.put(class_1802.field_23984, 170);
      RECORD_BPMS.put(class_1802.field_44705, 135);
      RECORD_BPMS.put(null, 0);
   }
}
