package com.locusthorror;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.model.ZombieModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.client.model.HumanoidModel;

/** Temporary vanilla renderer until GFBS GLB API is wired and verified. */
@Mod.EventBusSubscriber(modid=LocustHorror.MODID,bus=Mod.EventBusSubscriber.Bus.MOD,value=Dist.CLIENT)
public final class ClientSetup {
 @SubscribeEvent public static void register(EntityRenderersEvent.RegisterRenderers event){event.registerEntityRenderer(LocustHorror.LOCUST.get(),PlaceholderRenderer::new);}
 private static class PlaceholderRenderer
        extends MobRenderer<LocustEntity, HumanoidModel<LocustEntity>> {

    PlaceholderRenderer(EntityRendererProvider.Context ctx) {
        super(
            ctx,
            new HumanoidModel<>(ctx.bakeLayer(ModelLayers.ZOMBIE)),
            0.5f
        );
    }

    @Override
    public ResourceLocation getTextureLocation(LocustEntity entity) {
        return new ResourceLocation(
            "minecraft",
            "textures/entity/zombie/zombie.png"
        );
    }
}
