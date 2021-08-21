package com.devin12422.tweaks.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Arm;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Difficulty;
import java.util.Objects;
import com.devin12422.tweaks.util.ClientStorage;
import com.devin12422.tweaks.util.PlayerProperties;

public class BarElements {
	private final PlayerProperties playerProperties = new PlayerProperties();

	private final MinecraftClient client = MinecraftClient.getInstance();
	private final Difficulty difficulty = Objects.requireNonNull(client.getCameraEntity()).world.getDifficulty();
	private final MatrixStack stack;

	public BarElements(MatrixStack matrixStack) {
		stack = matrixStack;
	}

	int scaledWidth = client.getWindow().getScaledWidth();
	int scaledHeight = client.getWindow().getScaledHeight();

	public static int GetPreciseInt(float number) {
		float precision = 10000.0F;
		return MathHelper.ceil(number * precision);
	}

	public void renderOneBar() {

		PlayerEntity playerEntity = MinecraftClient.getInstance().player;
		if (playerEntity != null) {
			staminaBar();
			magickaBar();

//			barBackground();
//			naturalRegenerationBar();
//			regenerationBar();
			healthBar();
//			poisonBar();
//			witherBar();
//			hungerEffectBar();
//			hungerBar();
//			fireBar();
//			airBar();
//			xpBar();
//			barText();
//			heldFoodBar();
//            armorBar();
		}
	}

	private void healthBar() {
		DrawableHelper.fill(stack,
				baseRelativeStartW(scaledWidth / 2, 60,
						GetPreciseInt(playerProperties.rawHealth),
						GetPreciseInt(playerProperties.maxRawHealth)),
				scaledHeight - 35,
				baseRelativeEndW(scaledWidth / 2, 60,
						GetPreciseInt(playerProperties.rawHealth),
						GetPreciseInt(playerProperties.maxRawHealth)),
				scaledHeight - 38, 0xFFa60f0f);
	}
//
//	private void poisonBar() {
//		DrawableHelper.fill(stack,
//				clientProperties.baseRelativeStartW(playerProperties.maxHealth - playerProperties.poisonHealth,
//						playerProperties.maxHealth),
//				clientProperties.baseStartH, clientProperties.baseEndW, clientProperties.baseEndH,
//				config.badThings.poisonColor);
//	}
//
//	private void witherBar() {
//		DrawableHelper.fill(stack,
//				clientProperties.baseRelativeStartW(playerProperties.maxHealth - playerProperties.witherHealth,
//						playerProperties.maxHealth),
//				clientProperties.baseStartH, clientProperties.baseEndW, clientProperties.baseEndH,
//				config.badThings.witherColor);
//	}
	private void staminaBar() {
		DrawableHelper.fill(stack, baseRelativeStartW(scaledWidth / 2 - scaledWidth / 8, 45, ClientStorage.stamina, playerProperties.maxStamina),
				scaledHeight - 29, baseRelativeEndW(scaledWidth / 2 - scaledWidth / 8, 45, ClientStorage.stamina, playerProperties.maxStamina),
				scaledHeight - 32, 0xFF276c43);
	}

	private void magickaBar() {
		DrawableHelper.fill(stack, baseRelativeStartW(scaledWidth / 2 + scaledWidth / 8, 45, ClientStorage.magicka, playerProperties.maxMagicka),
				scaledHeight - 29, baseRelativeEndW(scaledWidth / 2 + scaledWidth / 8, 45, ClientStorage.magicka, playerProperties.maxMagicka),
				scaledHeight - 32, 0xFF233aac);
	}

	public int baseRelativeEndW(int center, int width, float value, float total) {
		if (value < total)
			return MathHelper.ceil(center + ((float) width / total * value));
		else
			return center + width;
	}

	public int baseRelativeStartW(int center, int width, float value, float total) {
		if (value < total)
			return MathHelper.ceil(center - ((float) width / total * value));
		else
			return center - width;
	}

}