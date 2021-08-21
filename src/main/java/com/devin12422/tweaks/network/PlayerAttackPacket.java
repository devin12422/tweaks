package com.devin12422.tweaks.network;

import com.devin12422.tweaks.PlayerAccess;
import com.devin12422.tweaks.item.weapon.twohanded.TwoHandedWeaponItem;
import com.devin12422.tweaks.setup.TagInit;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public class PlayerAttackPacket {

    public static final Identifier ATTACK_PACKET = new Identifier("tweaks", "attack_entity");

    public static Packet<?> attackPacket(Entity entity) {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        buf.writeInt(entity.getId());
        return ClientPlayNetworking.createC2SPacket(ATTACK_PACKET, buf);
    }

    public static void init() {
        ServerPlayNetworking.registerGlobalReceiver(ATTACK_PACKET, (server, player, handler, buffer, sender) -> {
            ((PlayerAccess) player).setOffhandAttack();
          //  ((PlayerAccess) player).resetLastOffhandAttackTicks();
            player.updateLastActionTime();
            player.attack(player.world.getEntityById(buffer.getInt(0)));
        });

    }

    public static boolean medievalWeaponsDoubleHanded(ItemStack offHandItemStack) {
        if (offHandItemStack.getItem() instanceof TwoHandedWeaponItem) {
            return false;
        } else
            return true;
    }

}