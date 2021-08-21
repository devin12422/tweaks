package com.devin12422.tweaks.entities.renderers;

import com.devin12422.tweaks.entities.ChainDemon;
import com.devin12422.tweaks.entities.models.ChainDemonModel;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class ChainDemonRenderer extends GeoEntityRenderer<ChainDemon> {
	public ChainDemonRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new ChainDemonModel());
	}

	@Override
	public RenderLayer getRenderType(ChainDemon animatable, float partialTicks, MatrixStack stack,
			VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			Identifier textureLocation) {
		return RenderLayer.getEntityTranslucent(this.getTextureLocation(animatable));
	}

}