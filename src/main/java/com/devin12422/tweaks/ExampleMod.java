package com.devin12422.tweaks;

import com.devin12422.tweaks.block.Blocks;
import com.devin12422.tweaks.item.Items;
import com.devin12422.tweaks.item.weapon.onehanded.hammer.doom.DoomItem;
import com.devin12422.tweaks.network.EmotePacket;
import com.devin12422.tweaks.network.PlayerAttackPacket;
import com.devin12422.tweaks.network.StopPacket;
import com.devin12422.tweaks.setup.EntityInit;
import com.devin12422.tweaks.setup.TagInit;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import com.devin12422.tweaks.util.EntityUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterials;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.server.PlayerStream;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import java.util.stream.Stream;
import net.minecraft.world.World;
import software.bernie.geckolib3.GeckoLib;

public class ExampleMod implements ModInitializer {
	public static final Identifier HIT_SOUND = new Identifier("tweaks:hit");
	public static SoundEvent HIT_SOUND_EVENT = new SoundEvent(HIT_SOUND);
	public static final Identifier HIT_HEAVY_SOUND = new Identifier("tweaks:hit_heavy");
	public static SoundEvent HIT_HEAVY_SOUND_EVENT = new SoundEvent(HIT_HEAVY_SOUND);
	public static final Identifier HIT_LIGHT_SOUND = new Identifier("tweaks:hit_light");
	public static SoundEvent HIT_LIGHT_SOUND_EVENT = new SoundEvent(HIT_LIGHT_SOUND);
	public static final Identifier HAMMER_SOUND = new Identifier("tweaks:hammer");
	public static SoundEvent HAMMER_SOUND_EVENT = new SoundEvent(HAMMER_SOUND);
	public static final ToolItem DOOM_HAMMER = new DoomItem(ToolMaterials.DIAMOND, 1, -1F,
			new Item.Settings().group(ItemGroup.COMBAT));

	public static final ItemGroup MAGIC_GROUP = FabricItemGroupBuilder.create(new Identifier("tweaks", "magic"))
			.icon(() -> new ItemStack(Items.FIRE_SPELL)).build();
//	public static EntityType<HammerEntity> IRON_HAMMER_ENTITY = Registry.register(Registry.ENTITY_TYPE,
//			new Identifier("tweaks", "hammer_thrown"),
//			FabricEntityTypeBuilder.create(SpawnGroup.MISC, (EntityType.EntityFactory<HammerEntity>) HammerEntity::new)
//					.dimensions(EntityDimensions.fixed(0.75f, 0.75f)).build());
//	public static EntityType<DoomEntity> DOOM_HAMMER_ENTITY = Registry.register(Registry.ENTITY_TYPE,
//			new Identifier("tweaks", "doom_thrown"),
//			FabricEntityTypeBuilder.create(SpawnGroup.MISC, (EntityType.EntityFactory<DoomEntity>) DoomEntity::new)
//					.dimensions(EntityDimensions.fixed(0.75f, 0.75f)).build());
//	public static EntityType<HammerEntity> DIAMOND_HAMMER_ENTITY = Registry.register(Registry.ENTITY_TYPE,
//			new Identifier("tweaks", "diamond_thrown"),
//			FabricEntityTypeBuilder.create(SpawnGroup.MISC, (EntityType.EntityFactory<HammerEntity>) HammerEntity::new)
//					.dimensions(EntityDimensions.fixed(1.2f, 2.75f)).build());

//	public static ScreenHandlerType<MagicScreenHandler> BAG_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(new Identifier("magic"), MagicScreenHandler::new);


	@SuppressWarnings("deprecation")
	@Override
	public void onInitialize() {

		PlayerAttackPacket.init();
//	    initServerNetwork();
		EntityInit.init();
		GeckoLib.initialize();

		FabricDefaultAttributeRegistry.register(EntityInit.CHAIN_DEMON_ENTITY,
				EntityUtils.createGenericEntityAttributes());

//		ServerPlayNetworking.registerGlobalReceiver(new Identifier("openMagic"), ChannelHandler channelHandler)
//		MAGIC_GUI_HANDLERTYPE = ScreenHandlerRegistry.registerSimple(new Identifier("tweaks", "magic"),
//				(syncId, inventory) -> new MagicGuiDescription(syncId, inventory, ScreenHandlerContext.EMPTY));
		TagInit.init();

//		DodgeCallback.EVENT.register((player) -> {
////			ItemStack stack = new ItemStack(Items.DIAMOND);
////			ItemEntity itemEntity = new ItemEntity(player.world, sheep.x, sheep.y, sheep.z, stack);
////			player.world.spawnEntity(itemEntity);
//
//			return ActionResult.FAIL;
//		});
//		Stats.CUSTOM.getOrCreateStat(INTERACT_WITH_COOL_BLOCK, StatFormatter.DEFAULT);

		com.devin12422.tweaks.item.Items.init();
		Blocks.init();

		Registry.register(Registry.ITEM, new Identifier("tweaks", "doom_hammer"), DOOM_HAMMER);
		Registry.register(Registry.SOUND_EVENT, ExampleMod.HIT_SOUND, HIT_SOUND_EVENT);
		Registry.register(Registry.SOUND_EVENT, ExampleMod.HIT_LIGHT_SOUND, HIT_LIGHT_SOUND_EVENT);
		Registry.register(Registry.SOUND_EVENT, ExampleMod.HIT_HEAVY_SOUND, HIT_HEAVY_SOUND_EVENT);
		Registry.register(Registry.SOUND_EVENT, ExampleMod.HAMMER_SOUND, HAMMER_SOUND_EVENT);
//
//		EntityRendererRegistry.INSTANCE.register(ExampleMod.IRON_HAMMER_ENTITY, HammerRenderer::new);
//		EntityRendererRegistry.INSTANCE.register(ExampleMod.FIREBALL_ENTITY, MagicProjectileRenderer<FireballEntity>::new);
//
//		EntityRendererRegistry.INSTANCE.register(ExampleMod.DOOM_HAMMER_ENTITY, DoomRenderer::new);
//		EntityRendererRegistry.INSTANCE.register(ExampleMod.DIAMOND_HAMMER_ENTITY, HammerRenderer::new);

	}
}
