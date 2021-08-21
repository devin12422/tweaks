package com.devin12422.tweaks.item.weapon.onehanded.hammer;


import java.util.function.Function;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)

public class HammerModel extends Model {
	int textureWidth;
	int textureHeight;
	private ModelPart root;
	public static final Identifier TEXTURE = new Identifier("tweaks", "textures/item/weapon/hammer/doomthrown.png");


	public HammerModel(ModelPart root) {
		super(RenderLayer::getEntitySolid);
		this.root = root;
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData bb_main = new ModelData();
		ModelPartData modelPartData = bb_main.getRoot();
		modelPartData.addChild("1", ModelPartBuilder.create().uv(32, 16).cuboid(-4.0F, -3.0F, -2.0F, 6.0F, 6.0F, 4.0F),
				ModelTransform.NONE);
		modelPartData.addChild("2", ModelPartBuilder.create().uv(8, 16).cuboid(-11.0F, -3.0F, -3.0F, 6.0F, 6.0F, 6.0F),
				ModelTransform.NONE);
		modelPartData.addChild("3", ModelPartBuilder.create().uv(0, 0).cuboid(-2.0F, -4.0F, -1.0F, 2.0F, 26.0F, 2.0F),
				ModelTransform.NONE);
		modelPartData.addChild("4", ModelPartBuilder.create().uv(8, 12).cuboid(-10.0F, -1.0F, -1.0F, 17.0F, 2.0F, 2.0F),
				ModelTransform.NONE);
		modelPartData.addChild("5", ModelPartBuilder.create().uv(8, 0).cuboid(5.0F, -2.0F, -2.0F, 1.0F, 4.0F, 4.0F),
				ModelTransform.NONE);
		modelPartData.addChild("6", ModelPartBuilder.create().uv(18, 0).cuboid(3.0F, -3.0F, -3.0F, 2.0F, 6.0F, 6.0F),
				ModelTransform.NONE);
		return TexturedModelData.of(bb_main, 64, 64);
	}

//
//	public DoomModel() {
//

//	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green,
			float blue, float alpha) {
		this.root.render(matrices, vertices, light, overlay, red, green, blue, alpha);
	}

}
