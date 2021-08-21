package com.devin12422.tweaks.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.devin12422.tweaks.ExampleMod;
import com.devin12422.tweaks.util.ClientStorage;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

@Mixin(InventoryScreen.class)
public abstract class InventoryScreenMixin extends net.minecraft.client.gui.screen.Screen {
	@Shadow
	RecipeBookWidget recipeBook;

	Identifier BUTTONS = new Identifier("tweaks", "textures/gui/buttons.png");
//	Identifier BACKGROUND = new Identifier(HideArmor.MODID, "textures/gui/background.png");
	TexturedButtonWidget button;

//	ArrayList<ToggleableButtonWidget> hideYourArmorButtons;
//	ArrayList<ToggleableButtonWidget> hideOtherPlayersArmorButtons;\
//	@Shadow
	private final Text text;

//	private final PlayerEntity player;
	public InventoryScreenMixin(Text text) {
		super(text);
		this.text = text;
//		this.player = player;
	}

//	@Redirect(method = "init()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/Screen;addDrawableChild()"))
//	private void redirectAddRecipeBook() {
//		System.out.println("reas");
//		return;
//	}

	@Inject(method = "init", at = @At("RETURN"))
	private void onInit(CallbackInfo ci) {
		// buttons
//		hideYourArmorButtons = Lists.newArrayList();
//		hideOtherPlayersArmorButtons = Lists.newArrayList();
//		for (int i=0; i<Utils.ARMOR_SLOTS.size(); ++i) {
//			EquipmentSlot slot = Utils.ARMOR_SLOTS.get(i);
//			// hide your armor
//			ToggleableButtonWidget toggle = new ToggleableButtonWidget(0, 0, 18, 18, i*18, 0, 18, BUTTONS, 128, 128, (button) -> {
//				((ToggleableButtonWidget) button).toggle();
//				Config.hideYourArmor.put(slot, ((ToggleableButtonWidget) button).isToggled());
//				Config.writeToFile();
//			}, (button, matrices, mouseX, mouseY) -> {
//				this.renderTooltip(matrices, Text.of(
//						(((ToggleableButtonWidget) button).isToggled() ? Formatting.RED+"Hiding" : Formatting.GREEN+"Showing")+
//						Formatting.WHITE+" your "+Utils.ARMOR_SLOT_INFO.get(slot).nameSingular), mouseX, mouseY);
//			}, LiteralText.EMPTY);
//			hideYourArmorButtons.add(toggle);
//			toggle.visible = Config.expandedGui;
//			if (Config.hideYourArmor.get(slot).booleanValue())
//				toggle.toggle();
//			this.addDrawableChild(toggle);
//			// hide other player's armor
//			toggle = new ToggleableButtonWidget(0, 0, 18, 18, i*18, 36, 18, BUTTONS, 128, 128, (button) -> {
//				((ToggleableButtonWidget) button).toggle();
//				Config.hideOtherPlayerArmor.put(slot, ((ToggleableButtonWidget) button).isToggled());
//				Config.writeToFile();
//			}, (button, matrices, mouseX, mouseY) -> {
//				this.renderTooltip(matrices, Text.of(
//						(((ToggleableButtonWidget) button).isToggled() ? Formatting.RED+"Hiding" : Formatting.GREEN+"Showing")+
//						Formatting.WHITE+" other player's "+Utils.ARMOR_SLOT_INFO.get(slot).namePlural), mouseX, mouseY);
//			}, LiteralText.EMPTY);
//			hideOtherPlayersArmorButtons.add(toggle);
//			toggle.visible = Config.expandedGui;
//			if (Config.hideOtherPlayerArmor.get(slot).booleanValue())
//				toggle.toggle();
//			this.addDrawableChild(toggle);
//		}
		// button to expand
//		this.addDrawableChild(
//				new TexturedButtonWidget(this.width / 2, this.height / 2, 36, 36, 0, 0, 36, BUTTONS, (button) -> {
//					ClientStorage.expandedGui = !ClientStorage.expandedGui;
//					MinecraftClient.getInstance().setScreen(new Screen(new MagicGui()));
//				}));
//		this.addDrawableChild(
//				new TexturedButtonWidget(this.width / 2 + 72, this.height / 2, 36, 36, 0, 0, 36, BUTTONS, (button) -> {
//					ClientStorage.expandedGui = !ClientStorage.expandedGui;
////					client.player.closeScreen();
//					PacketByteBuf buf = PacketByteBufs.create();
////				        buf.writeBlockPos(target);
//					ClientPlayNetworking.send(ExampleMod.openMagicId, buf);
//
////			 		client.setScreen(new CottonClientScreen(new MagicGuiDescription.HandlerFactory())); 
////client.player.currentScreenHandler = new MagicGuiDescription(height, null, null);
////					MinecraftClient.getInstance().setScreen(new CottonClientScreen(new MagicGui()));
//
////					@Override
////					public ScreenHandler createMenu(int syncId, PlayerInventory inventory, PlayerEntity player) {
////						return new ExampleGuiDescription(syncId, inventory, ScreenHandlerContext.create(world, pos));
////					NamedScreenHandlerFactory screenHandlerFactory = this.createScreenHandlerFactory();
////					client.player.openHandledScreen(createScreenHandlerFactory());
//
//				}));
//		this.button = new TexturedButtonWidget(30, 30, 72, 72, 0, 0, 18, BUTTONS, 72, 72, (button) -> {
//			ClientStorage.expandedGui = !ClientStorage.expandedGui;
//			MinecraftClient.getInstance().setScreen(new Screen(new MagicGui()));
		// hide/show buttons
//			this.button.visible = !this.button.visible;
//			if (clientstorage.expandedGui) {
//				for (ToggleableButtonWidget button2 : hideYourArmorButtons)
//					button2.visible = true;
//				for (ToggleableButtonWidget button2 : hideOtherPlayersArmorButtons)
//					button2.visible = true;
//			}
//			else {
//				for (ToggleableButtonWidget button2 : hideYourArmorButtons)
//					button2.visible = false;
//				for (ToggleableButtonWidget button2 : hideOtherPlayersArmorButtons)
//					button2.visible = false;
//			}
//		});
//		this.addDrawableChilsd(this.button);
		// reset button positions
//		this.resetButtonPositions();
	}

//	public NamedScreenHandlerFactory createScreenHandlerFactory() {
//		return new SimpleNamedScreenHandlerFactory((syncId, inventory, player) -> {
//			return new MagicGuiDescription(syncId, inventory, null);
//		}, SCREEN_TITLE);
//	}
//	@Inject(method = "drawForeground", at = @At("HEAD"))
//	private void onDrawForeground(MatrixStack matrices, int mouseX, int mouseY, CallbackInfo ci) {
//		if (ClientStorage.expandedGui) 
//			// draw text
//			drawCenteredText(matrices, textRenderer, Formatting.WHITE+"Hide Armor", this.backgroundWidth+40, 12, 16777215);
//	}

//	@Inject(method = "drawBackground", at = @At("HEAD"))
//	private void onDrawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY, CallbackInfo ci) {
//		if (ClientStorage.expandedGui) {
//			// draw background
////			RenderSystem.setShader(GameRenderer::getPositionTexShader);
////			RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
////			RenderSystem.setShaderTexture(0, this.BACKGROUND);
////			this.drawTexture(matrices, x+this.backgroundWidth+2, this.y, 0, 0, 75, 117);
//		}
//	}

//	@Inject(method = "mouseClicked", at = @At("RETURN"))
//	private void onMouseClicked(double mouseX, double mouseY, int button, CallbackInfoReturnable ci) {
////		resetButtonPositions();
//	}

//	private void resetButtonPositions() {
//		this.button.setPos(this.x + 143, this.height / 2 - 22);
//		for (int i=0; i<this.hideYourArmorButtons.size(); ++i) {
//			int x = this.x+this.backgroundWidth+17;
//			int y = this.y+i*21+24;
//			this.hideYourArmorButtons.get(i).setPos(x, y);
//			this.hideOtherPlayersArmorButtons.get(i).setPos(x+25, y);
//		}
//	}

}