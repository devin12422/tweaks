package com.devin12422.tweaks.setup;
import com.devin12422.tweaks.entities.renderers.ChainDemonRenderer;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import org.lwjgl.glfw.GLFW;

import com.devin12422.tweaks.Emote;
import com.devin12422.tweaks.EmotePlayerInterface;
import com.devin12422.tweaks.ExampleMod;
import com.devin12422.tweaks.network.EmotePacket;
import com.devin12422.tweaks.network.StopPacket;

import ca.weblite.objc.Client;
import io.netty.buffer.Unpooled;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.fabricmc.fabric.api.network.PacketContext;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;

@Environment(EnvType.CLIENT)
public class ClientInit implements ClientModInitializer {
	public static net.minecraft.client.option.KeyBinding rAttack = new KeyBinding("key.tweaks.stance",
			InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_R, "category.tweaks.binds");
	static final String TexturePath = "textures/gui/recipe_button.png";

	@Override
	public void onInitializeClient() {

		
		RenderInit.init();
		KeyBindingHelper.registerKeyBinding(rAttack);
//		 initNetworkClient(); 
//		 initEmotes(); 
//		ClientTickEvents.END_CLIENT_TICK.register(client -> {
//			while (rAttack.wasPressed()) {
//				client.player.sendMessage(new LiteralText("Key 1 was pressed!"), false);
//			}
//
//	      HitResult hitResult = this.client.crosshairTarget;
//		 Entity entity = ((EntityHitResult)hitResult).getEntity();
//         Identifier identifier = Registry.ENTITY_TYPE.getId(entity.getType());

//		});
//		ClientSidePacketRegistry.INSTANCE.register(ExampleMod.PLAY_PARTICLE_PACKET_ID,
//				(packetContext, attachedData) -> {
//					// Get the BlockPos we put earlier, in the networking thread
//					BlockPos pos = attachedData.readBlockPos();
//					packetContext.getTaskQueue().execute(() -> {
//						// Use the pos in the main thread
//						MinecraftClient.getInstance().particleManager.addParticle(ParticleTypes.EXPLOSION, pos.getX(),
//								pos.getY(), pos.getZ(), 0.0D, 0.0D, 0.0D);
//					});
//				});

	}
    public static void initEmotes(){
        //Serialize emotes
//        EmoteHolder.clearEmotes();
//
//        serializeInternalEmotes("waving");
//        serializeInternalEmotes("clap");
//        serializeInternalEmotes("crying");
//        serializeInternalEmotes("point");
//        serializeInternalEmotes("here");
//        serializeInternalEmotes("palm");
//        serializeInternalEmotes("backflip");
        //TODO add internal emotes to the list


//        if(!externalEmotes.isDirectory())externalEmotes.mkdirs();
//        serializeExternalEmotes();

//        Exa.config.assignEmotes();
    }
//    private static void serializeInternalEmotes(String name){
//        InputStream stream = Client.class.getResourceAsStream("/assets/tweaks/emotes/" + name + ".json");
//        InputStreamReader streamReader = new InputStreamReader(stream, StandardCharsets.UTF_8);
//        Reader reader = new BufferedReader(streamReader);
//        EmoteHolder emoteHolder = Serializer.serializer.fromJson(reader, EmoteHolder.class);
////        EmoteHolder.addEmoteToList(emoteHolder);
////        emoteHolder.bindIcon((String)("/assets/tweaks/emotes/" + name + ".png"));
//    }
//    private void initNetworkClient(){
//        ClientSidePacketRegistry.INSTANCE.register(ExampleMod.EMOTE_PLAY_NETWORK_PACKET_ID, ((packetContext, packetByteBuf) -> {
//            EmotePacket emotePacket;
//            Emote emote;
//            emotePacket = new EmotePacket();
//            if(!emotePacket.read(packetByteBuf, false)) return;
//
//            emote = emotePacket.getEmote();
//            boolean isRepeat = emotePacket.isRepeat;
//            packetContext.getTaskQueue().execute(() ->{
//                PlayerEntity playerEntity = MinecraftClient.getInstance().world.getPlayerByUuid(emotePacket.getPlayer());
//                if(playerEntity != null) {
//                    if(!isRepeat || !Emote.isRunningEmote(((EmotePlayerInterface) playerEntity).getEmote())) {
//                        ((EmotePlayerInterface) playerEntity).playEmote(emote);
//                        ((EmotePlayerInterface) playerEntity).getEmote().start();
//                    }
//                    else {
//                        ((EmotePlayerInterface)playerEntity).resetLastUpdated();
//                    }
//                }
//            });
//        }));
//
//        ClientSidePacketRegistry.INSTANCE.register(ExampleMod.EMOTE_STOP_NETWORK_PACKET_ID, ((packetContex, packetByyeBuf) -> {
//            StopPacket packet = new StopPacket();
//            packet.read(packetByyeBuf);
//
//            packetContex.getTaskQueue().execute(()-> {
//                EmotePlayerInterface player = (EmotePlayerInterface) MinecraftClient.getInstance().world.getPlayerByUuid(packet.getPlayer());
//                if(player != null && Emote.isRunningEmote(player.getEmote()))player.getEmote().stop();
//            });
//        }));
//    }
	public class EntityPacketOnClient {
		@Environment(EnvType.CLIENT)
		public static void onPacket(PacketContext context, PacketByteBuf byteBuf) {
			EntityType<?> type = Registry.ENTITY_TYPE.get(byteBuf.readVarInt());
			UUID entityUUID = byteBuf.readUuid();
			int entityID = byteBuf.readVarInt();
			double x = byteBuf.readDouble();
			double y = byteBuf.readDouble();
			double z = byteBuf.readDouble();
			float pitch = (byteBuf.readByte() * 360) / 256.0F;
			float yaw = (byteBuf.readByte() * 360) / 256.0F;
			context.getTaskQueue().execute(() -> {
				@SuppressWarnings("resource")
				ClientWorld world = MinecraftClient.getInstance().world;
				Entity entity = type.create(world);
				if (entity != null) {
					entity.updatePosition(x, y, z);
					entity.updateTrackedPosition(x, y, z);
					entity.setPitch(pitch);
					entity.setYaw(yaw);
					entity.setId(entityID);
					entity.setUuid(entityUUID);
					world.addEntity(entityID, entity);
				}
			});
		}
	}

	public class EntityPacket {
		public static final Identifier ID = new Identifier("tweaks", "spawn_entity");

		public static Packet<?> createPacket(Entity entity) {
			PacketByteBuf buf = createBuffer();
			buf.writeVarInt(Registry.ENTITY_TYPE.getRawId(entity.getType()));
			buf.writeUuid(entity.getUuid());
			buf.writeVarInt(entity.getId());
			buf.writeDouble(entity.getX());
			buf.writeDouble(entity.getY());
			buf.writeDouble(entity.getZ());
			buf.writeByte(MathHelper.floor(entity.getPitch() * 256.0F / 360.0F));
			buf.writeByte(MathHelper.floor(entity.getYaw() * 256.0F / 360.0F));
			buf.writeFloat(entity.getPitch());
			buf.writeFloat(entity.getYaw());
			return ServerPlayNetworking.createS2CPacket(ID, buf);
		}

		private static PacketByteBuf createBuffer() {
			return new PacketByteBuf(Unpooled.buffer());
		}

	}
}