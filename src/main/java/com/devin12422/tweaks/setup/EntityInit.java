package com.devin12422.tweaks.setup;

import java.util.HashMap;
import java.util.Map;

import com.devin12422.tweaks.entities.ChainDemon;
import com.devin12422.tweaks.item.magic.SpellType;
import com.devin12422.tweaks.item.magic.destruction.MagicProjectileEntity;
import com.devin12422.tweaks.item.magic.destruction.ProjectileSpellItem;
import com.devin12422.tweaks.item.magic.destruction.fire.FireProjectileEntity;
import com.devin12422.tweaks.item.magic.destruction.fire.FireType;
import com.devin12422.tweaks.item.magic.destruction.ice.IceProjectileEntity;
import com.devin12422.tweaks.item.magic.destruction.ice.IceType;
import com.devin12422.tweaks.item.weapon.onehanded.hammer.HammerEntity;
import com.devin12422.tweaks.item.weapon.onehanded.hammer.doom.DoomEntity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import software.bernie.example.registry.EntityRegistryBuilder;

public class EntityInit {
	
	public static HashMap<String, EntityType<? extends PersistentProjectileEntity>> MAGIC_PROJECTILE_ENTITIES = new HashMap<String, EntityType<? extends PersistentProjectileEntity>>();;
	public static EntityType<HammerEntity> IRON_HAMMER_ENTITY = Registry.register(Registry.ENTITY_TYPE,
			new Identifier("tweaks", "hammer_thrown"),
			FabricEntityTypeBuilder.create(SpawnGroup.MISC, (EntityType.EntityFactory<HammerEntity>) HammerEntity::new)
					.dimensions(EntityDimensions.fixed(0.75f, 0.75f)).build());
	public static EntityType<DoomEntity> DOOM_HAMMER_ENTITY = Registry.register(Registry.ENTITY_TYPE,
			new Identifier("tweaks", "doom_thrown"),
			FabricEntityTypeBuilder.create(SpawnGroup.MISC, (EntityType.EntityFactory<DoomEntity>) DoomEntity::new)
					.dimensions(EntityDimensions.fixed(0.75f, 0.75f)).build());
	public static EntityType<HammerEntity> DIAMOND_HAMMER_ENTITY = Registry.register(Registry.ENTITY_TYPE,
			new Identifier("tweaks", "diamond_thrown"),
			FabricEntityTypeBuilder.create(SpawnGroup.MISC, (EntityType.EntityFactory<HammerEntity>) HammerEntity::new)
					.dimensions(EntityDimensions.fixed(1.2f, 2.75f)).build());
	public static EntityType<FireProjectileEntity> FIRE_BALL_ENTITY = Registry.register(Registry.ENTITY_TYPE,
			new Identifier("tweaks", "fire_ball_entity"),
			FabricEntityTypeBuilder.create(SpawnGroup.MISC,
					(EntityType.EntityFactory<FireProjectileEntity>) FireProjectileEntity::new)
					.dimensions(EntityDimensions.fixed(0.3f, 0.3f)).build());
	public static EntityType<IceProjectileEntity> ICE_BALL_ENTITY = Registry.register(Registry.ENTITY_TYPE,
			new Identifier("tweaks", "ice_ball_entity"),
			FabricEntityTypeBuilder.create(SpawnGroup.MISC,
					(EntityType.EntityFactory<IceProjectileEntity>) IceProjectileEntity::new)
					.dimensions(EntityDimensions.fixed(0.3f, 0.3f)).build());
	public static final EntityType<ChainDemon> CHAIN_DEMON_ENTITY = buildEntity(ChainDemon::new,
			ChainDemon.class, .7F, 1.3F, SpawnGroup.CREATURE);
	


	public static<T extends Entity> EntityType<T> buildEntity(EntityType.EntityFactory<T> entity, Class<T> entityClass,
			float width, float height, SpawnGroup group) {
		if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
			String name = entityClass.getSimpleName().toLowerCase();
			return EntityRegistryBuilder.<T>createBuilder(new Identifier("tweaks", name)).entity(entity)
					.category(group).dimensions(EntityDimensions.changing(width, height)).build();
		}
		return null;
	}
	public static void init() {

	}

}
