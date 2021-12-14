package io.github.darealturtywurty.tutorialmod.client.renderer;

import io.github.darealturtywurty.tutorialmod.TutorialMod;
import io.github.darealturtywurty.tutorialmod.common.entity.SittableEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;

public class SeatRenderer extends EntityRenderer<SittableEntity> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(TutorialMod.MODID, "");

    public SeatRenderer(Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(SittableEntity entity) {
        return TEXTURE;
    }
}
