package com.locusthorror;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

@Mod(LocustHorror.MODID)
public final class LocustHorror {
 public static final String MODID="locusthorror";
 public static final DeferredRegister<EntityType<?>> ENTITIES=DeferredRegister.create(ForgeRegistries.ENTITY_TYPES,MODID);
 public static final RegistryObject<EntityType<LocustEntity>> LOCUST=ENTITIES.register("the_locust",()->EntityType.Builder.of(LocustEntity::new,MobCategory.MONSTER).sized(0.85f,2.4f).clientTrackingRange(12).build("locusthorror:the_locust"));
 public static final DeferredRegister<SoundEvent> SOUNDS=DeferredRegister.create(ForgeRegistries.SOUND_EVENTS,MODID);
 public static final String[] SOUND_NAMES={"chase_music","footsteps","heartbeat","impact","voice_watcher_2","breathing","door_knock","paranoia_ambient","teleport","voice_mimic_1","voice_watcher_1","window_scratch"};
 static {for(String s:SOUND_NAMES) SOUNDS.register(s,()->SoundEvent.createVariableRangeEvent(new ResourceLocation(MODID,s)));}
 public LocustHorror(){IEventBus bus=FMLJavaModLoadingContext.get().getModEventBus();ENTITIES.register(bus);SOUNDS.register(bus);bus.addListener(this::attributes);}
 private void attributes(EntityAttributeCreationEvent e){e.put(LOCUST.get(),AttributeSupplier.builder().add(Attributes.MAX_HEALTH,120).add(Attributes.MOVEMENT_SPEED,0.30).add(Attributes.ATTACK_DAMAGE,8).add(Attributes.FOLLOW_RANGE,48).add(Attributes.KNOCKBACK_RESISTANCE,1).build());}
}
