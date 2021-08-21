package com.devin12422.tweaks.item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;

import com.devin12422.tweaks.ExampleMod;
import com.devin12422.tweaks.block.Blocks;
import com.devin12422.tweaks.item.magic.destruction.ProjectileSpellItem;
import com.devin12422.tweaks.item.magic.destruction.fire.FireType;
import com.devin12422.tweaks.item.magic.destruction.ice.IceType;
import com.devin12422.tweaks.item.weapon.onehanded.OneHandedWeaponItem;
import com.devin12422.tweaks.item.weapon.onehanded.fist.FistItem;
import com.devin12422.tweaks.item.weapon.onehanded.hammer.HammerItem;
import com.devin12422.tweaks.item.weapon.onehanded.rapier.ArclightRapier;
import com.devin12422.tweaks.item.weapon.twohanded.bardiche.BardicheItem;
import com.devin12422.tweaks.setup.EntityInit;

import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.TallBlockItem;
import net.minecraft.item.ToolMaterials;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.registry.Registry;

public class Items {
	public static final Item WOOD_HAMMER = register("wood_hammer", "weapon/onehanded/hammer/",
			new HammerItem(ToolMaterials.WOOD, 1, -2.4F, new Item.Settings().group(ItemGroup.COMBAT)));
	public static final Item STONE_HAMMER = register("stone_hammer", "weapon/onehanded/hammer/",
			new HammerItem(ToolMaterials.STONE, 1, -2.4F, new Item.Settings().group(ItemGroup.COMBAT)));
	public static final Item GOLD_HAMMER = register("gold_hammer", "weapon/onehanded/hammer/",
			new HammerItem(ToolMaterials.GOLD, 1, -2.4F, new Item.Settings().group(ItemGroup.COMBAT)));
	public static final Item IRON_HAMMER = register("iron_hammer", "weapon/onehanded/hammer/",
			new HammerItem(ToolMaterials.IRON, 1, -2.4F, new Item.Settings().group(ItemGroup.COMBAT)));
	public static final Item DIAMOND_HAMMER = register("diamond_hammer", "weapon/onehanded/hammer/",
			new HammerItem(ToolMaterials.DIAMOND, 1, -2.4F, new Item.Settings().group(ItemGroup.COMBAT)));
	public static final Item NETHERITE_HAMMER = register("netherite_hammer", "weapon/onehanded/hammer/",
			new HammerItem(ToolMaterials.NETHERITE, 1, -2.4F, new Item.Settings().group(ItemGroup.COMBAT)));
//	Tekkos
	public static final Item WOOD_FISTS = register("wood_fist", "weapon/onehanded/fist/",
			new FistItem(ToolMaterials.WOOD, 1, 0F, new Item.Settings().group(ItemGroup.COMBAT)));
	public static final Item STONE_FISTS = register("stone_fist", "weapon/onehanded/fist/",
			new FistItem(ToolMaterials.STONE, 1, 0F, new Item.Settings().group(ItemGroup.COMBAT)));
	public static final Item IRON_FISTS = register("iron_fist", "weapon/onehanded/fist/",
			new FistItem(ToolMaterials.IRON, 1, 0F, new Item.Settings().group(ItemGroup.COMBAT)));
	public static final Item GOLD_FISTS = register("gold_fist", "weapon/onehanded/fist/",
			new FistItem(ToolMaterials.GOLD, 1, 0F, new Item.Settings().group(ItemGroup.COMBAT)));
	public static final Item DIAMOND_FISTS = register("diamond_fist", "weapon/onehanded/fist/",
			new FistItem(ToolMaterials.DIAMOND, 1, 0F, new Item.Settings().group(ItemGroup.COMBAT)));
	public static final Item NETHERITE_FISTS = register("netherite_fist", "weapon/onehanded/fist/",
			new FistItem(ToolMaterials.NETHERITE, 1, 0F, new Item.Settings().group(ItemGroup.COMBAT)));
	public static final Item ARCLIGHT_RAPIER = register("arclight_rapier", "weapon/onehanded/rapier/",
			new ArclightRapier(ToolMaterials.NETHERITE, 1, 0F, new Item.Settings().group(ItemGroup.COMBAT)));
//	Bardiches
	public static final Item IRON_BARDICHE = register("iron_bardiche", "weapon/twohanded/bardiche/",
			new BardicheItem(ToolMaterials.IRON, 1, 0F, new Item.Settings().group(ItemGroup.COMBAT)));
	public static final Item DIAMOND_BARDICHE = register("diamond_bardiche", "weapon/twohanded/bardiche/",
			new BardicheItem(ToolMaterials.DIAMOND, 1, 0F, new Item.Settings().group(ItemGroup.COMBAT)));
	public static final Item NETHERITE_BARDICHE = register("netherite_bardiche", "weapon/twohanded/bardiche/",
			new BardicheItem(ToolMaterials.NETHERITE, 1, 0F, new Item.Settings().group(ItemGroup.COMBAT)));

//	static ArrayList<Item> projectileSpells = new ArrayList<Item>();
	public static final Item CUSTOM_SEEDS = register("corn_seeds", "seeds/",
			(BlockItem) (new TallBlockItem(Blocks.CUSTOM_CROP_BLOCK, new Item.Settings().group(ItemGroup.MISC))));
	public static final Item FIRE_SPELL = register("fire", "spell/destruction/projectile/",
			new ProjectileSpellItem(new Item.Settings().group(ExampleMod.MAGIC_GROUP), 5, new FireType(),EntityInit.FIRE_BALL_ENTITY));
	public static final Item ICE_SPELL = register("ice", "spell/destruction/projectile/",
			new ProjectileSpellItem(new Item.Settings().group(ExampleMod.MAGIC_GROUP), 5, new IceType(),EntityInit.ICE_BALL_ENTITY));
//	static ArrayList<Item> projectileSpells = new ArrayList<Item>();

	public static Item register(String name, String type, Item item) {
		if (item instanceof OneHandedWeaponItem) {
			FabricModelPredicateProviderRegistry.register(item, new Identifier("parrying"), (stack, world, entity,
					i) -> entity != null && entity.isUsingItem() && entity.getActiveItem() == stack ? 1.0F : 0.0F);
		}
		return Registry.register(Registry.ITEM, new Identifier("tweaks", type + name), item);
	}

	public static void init() {
//		for (Item item : projectileSpells) {
//			GameRegistry.registerItem(item, item.getUnlocalizedName());
//		}
//		projectileSpells.forEach(new Consumer<SpellProjectile>() {
//
//			@Override
//			public void accept(SpellProjectile t) {
//				register(t.getSpellType() + "_ball_spell", "item/handheld/", (Item) t);
//			}
//
//		});
	}

}
