package com.devin12422.tweaks.item.weapon.onehanded.hammer.doom;

import com.devin12422.tweaks.setup.RenderInit;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;

@Environment(EnvType.CLIENT)
public class DoomRenderer extends EntityRenderer<DoomEntity> {
	public static final Identifier TEXTURE = new Identifier("tweaks", "textures/item/weapon/hammer/doomthrown.png");
	   private final DoomModel model;

	   public DoomRenderer(EntityRendererFactory.Context context) {
	      super(context);
	      this.model = new DoomModel(context.getPart(RenderInit.DOOM_HAMMER_LAYER));
	   }

	   public void render(DoomEntity tridentEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
	      matrixStack.push();
	      matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(MathHelper.lerp(g, tridentEntity.prevYaw, tridentEntity.getYaw()) - 90.0F));
	      matrixStack.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(MathHelper.lerp(g, tridentEntity.prevPitch, tridentEntity.getPitch()) + 90.0F));
	      VertexConsumer vertexConsumer = ItemRenderer.getDirectItemGlintConsumer(vertexConsumerProvider, this.model.getLayer(this.getTexture(tridentEntity)), false, tridentEntity.isEnchanted());
	      this.model.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
	      matrixStack.pop();
	      super.render(tridentEntity, f, g, matrixStack, vertexConsumerProvider, i);
	   }

	   public Identifier getTexture(DoomEntity tridentEntity) {
	      return TEXTURE;
	   }
	}