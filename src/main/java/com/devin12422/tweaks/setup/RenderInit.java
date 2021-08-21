package com.devin12422.tweaks.setup;
import  com.devin12422.tweaks.entities.renderers.ChainDemonRenderer;
import com.devin12422.tweaks.item.magic.SpellType;
import com.devin12422.tweaks.item.magic.destruction.MagicProjectileRenderer;
import com.devin12422.tweaks.item.magic.destruction.fire.FireBallRenderer;
import com.devin12422.tweaks.item.magic.destruction.fire.FireType;
import com.devin12422.tweaks.item.magic.destruction.ice.IceBallRenderer;
import com.devin12422.tweaks.item.magic.destruction.ice.IceType;
import com.devin12422.tweaks.item.weapon.onehanded.hammer.HammerModel;
import com.devin12422.tweaks.item.weapon.onehanded.hammer.HammerRenderer;
import com.devin12422.tweaks.item.weapon.onehanded.hammer.doom.DoomModel;
import com.devin12422.tweaks.item.weapon.onehanded.hammer.doom.DoomRenderer;

import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

public class RenderInit {

	public static final EntityModelLayer SMALL_HAMMER_LAYER = new EntityModelLayer(
			new Identifier("tweaks:small_hammer_render_layer"), "small_hammer_render_layer");
	public static final EntityModelLayer DOOM_HAMMER_LAYER = new EntityModelLayer(
			new Identifier("tweaks:doom_hammer_render_layer"), "doom_hammer_render_layer");
//    public static final EntityModelLayer FRANCISCA_LT_LAYER = new EntityModelLayer(new Identifier("medievalweapons:francisca_lt_render_layer"), "francisca_lt_render_layer");
//    public static final EntityModelLayer HEALING_STAFF_LAYER = new EntityModelLayer(new Identifier("medievalweapons:healing_staff_render_layer"), "healing_staff_render_layer");
//    public static final EntityModelLayer JAVELIN_LAYER = new EntityModelLayer(new Identifier("medievalweapons:javelin_render_layer"), "javelin_render_layer");
//    public static final EntityModelLayer LANCE_LAYER = new EntityModelLayer(new Identifier("medievalweapons:lance_render_layer"), "lance_render_layer");
//    public static final EntityModelLayer MACE_LAYER = new EntityModelLayer(new Identifier("medievalweapons:mace_render_layer"), "mace_render_layer");
//    public static final EntityModelLayer THALLEOUS_SWORD_LAYER = new EntityModelLayer(new Identifier("medievalweapons:thalleous_sword_render_layer"), "thalleous_sword_render_layer");

	public static void init() {
		// Packet
//        ClientPlayNetworking.registerGlobalReceiver(EntitySpawnPacket.ID, EntitySpawnPacket::onPacket);
		// Francisca
		EntityRendererRegistry.INSTANCE.register(EntityInit.IRON_HAMMER_ENTITY, HammerRenderer::new);
		EntityRendererRegistry.INSTANCE.register(EntityInit.DIAMOND_HAMMER_ENTITY, HammerRenderer::new);

		EntityRendererRegistry.INSTANCE.register(EntityInit.DOOM_HAMMER_ENTITY, DoomRenderer::new);
		EntityRendererRegistry.INSTANCE.register(EntityInit.CHAIN_DEMON_ENTITY, ChainDemonRenderer::new);

//        EntityInit.BALL_SPELLS.forEach(null);
//        EntityRendererRegistry.INSTANCE.register(EntityInit.ICE_BALL_ENTITY, MagicProjectileRenderer<MagicProjectileEntity>::new);
		EntityRendererRegistry.INSTANCE.register(EntityInit.FIRE_BALL_ENTITY, FireBallRenderer::new);
		EntityRendererRegistry.INSTANCE.register(EntityInit.ICE_BALL_ENTITY, IceBallRenderer::new);
//        EntityRendererRegistry.INSTANCE.register(EntityInit.STONE_FRANCISCA_LT, Francisca_LT_Entity_Renderer::new);
//        EntityRendererRegistry.INSTANCE.register(EntityInit.IRON_FRANCISCA_HT, Francisca_HT_Entity_Renderer::new);
//        EntityRendererRegistry.INSTANCE.register(EntityInit.GOLDEN_FRANCISCA_HT, Francisca_HT_Entity_Renderer::new);
//        EntityRendererRegistry.INSTANCE.register(EntityInit.DIAMOND_FRANCISCA_HT, Francisca_HT_Entity_Renderer::new);
//        EntityRendererRegistry.INSTANCE.register(EntityInit.NETHERITE_FRANCISCA_HT, Francisca_HT_Entity_Renderer::new);
		// Javelin
//        EntityRendererRegistry.INSTANCE.register(EntityInit.WOODEN_JAVELIN, Javelin_Entity_Renderer::new);
//        EntityRendererRegistry.INSTANCE.register(EntityInit.STONE_JAVELIN, Javelin_Entity_Renderer::new);
//        EntityRendererRegistry.INSTANCE.register(EntityInit.IRON_JAVELIN, Javelin_Entity_Renderer::new);
//        EntityRendererRegistry.INSTANCE.register(EntityInit.GOLDEN_JAVELIN, Javelin_Entity_Renderer::new);
//        EntityRendererRegistry.INSTANCE.register(EntityInit.DIAMOND_JAVELIN, Javelin_Entity_Renderer::new);
//        EntityRendererRegistry.INSTANCE.register(EntityInit.NETHERITE_JAVELIN, Javelin_Entity_Renderer::new);
		// Healing Ball
//        EntityRendererRegistry.INSTANCE.register(EntityInit.HEALING_BALL_ENTITY, Healing_Ball_Entity_Renderer::new);
		// Particle
//        ParticleFactoryRegistry.getInstance().register(ParticleInit.HEALING_AURA_PARTICLE, HealingAuraParticle.Factory::new);
		// Compat

		EntityModelLayerRegistry.registerModelLayer(SMALL_HAMMER_LAYER, HammerModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(DOOM_HAMMER_LAYER, DoomModel::getTexturedModelData);
//        EntityModelLayerRegistry.registerModelLayer(FRANCISCA_LT_LAYER, Francisca_LT_Entity_Model::getTexturedModelData);
//        EntityModelLayerRegistry.registerModelLayer(HEALING_STAFF_LAYER, Healing_Staff_Entity_Model::getTexturedModelData);
//        EntityModelLayerRegistry.registerModelLayer(JAVELIN_LAYER, Javelin_Entity_Model::getTexturedModelData);
//        EntityModelLayerRegistry.registerModelLayer(LANCE_LAYER, Lance_Entity_Model::getTexturedModelData);
//        EntityModelLayerRegistry.registerModelLayer(MACE_LAYER, Mace_Entity_Model::getTexturedModelData);
//        EntityModelLayerRegistry.registerModelLayer(THALLEOUS_SWORD_LAYER, Thalleous_Sword_Entity_Model::getTexturedModelData);

	}

}
