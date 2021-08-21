package com.devin12422.tweaks.item.magic.destruction;

import com.devin12422.tweaks.item.magic.SpellType;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3f;

@Environment(EnvType.CLIENT)
public class MagicProjectileRenderer<T extends PersistentProjectileEntity, V extends SpellType>
		extends ProjectileEntityRenderer<T> {
	private EntityRendererFactory.Context ctx;
//	private V v;

	public MagicProjectileRenderer(EntityRendererFactory.Context context) {
		super(context);
		this.ctx = context;
//      context.getItemRenderer().

		this.itemRenderer = context.getItemRenderer();

	}

	private static final float MIN_DISTANCE = 12.25F;
	private final ItemRenderer itemRenderer;

	protected int getBlockLight(T entity, BlockPos pos) {
		return true ? 15 : super.getBlockLight(entity, pos);
	}

	public Item getBallItem() {
		return Items.FIRE_CHARGE;
	}

	public void render(T entity, float yaw, float tickDelta, MatrixStack matrices,
			VertexConsumerProvider vertexConsumers, int light) {
		if (entity.age >= 2 || !(this.dispatcher.camera.getFocusedEntity().squaredDistanceTo(entity) < 12.25D)) {
			matrices.push();
//			System.out.println(this.v.getType());
//			System.out.println((((MagicProjectileEntity<?>) ctx.getRenderDispatcher().targetedEntity).get()));
			matrices.scale(1, 1, 1);
			matrices.multiply(this.dispatcher.getRotation());
			matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180.0F));
			this.itemRenderer.renderItem(new ItemStack(getBallItem()), ModelTransformation.Mode.GROUND, light,
					OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getId());
			matrices.pop();
//         System.out.println(getTexture(this.g))
//         super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
		}
	}

	public Identifier getTexture(T entity) {
		return ((MagicProjectileEntity<SpellType>) entity).TEXTURE;
	}
}