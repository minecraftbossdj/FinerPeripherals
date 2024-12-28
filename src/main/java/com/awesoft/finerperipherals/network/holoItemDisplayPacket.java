package com.awesoft.finerperipherals.network;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.BlockEntity;

public class holoItemDisplayPacket {
    private BlockPos pos;
    private CompoundTag nbtData;

    public holoItemDisplayPacket(BlockPos pos, CompoundTag nbtData) {
        this.pos = pos;
        this.nbtData = nbtData;
    }

    public BlockPos getPos() {
        return pos;
    }

    public CompoundTag getNbtData() {
        return nbtData;
    }

    public static void encode(holoItemDisplayPacket packet, FriendlyByteBuf buffer) {
        buffer.writeBlockPos(packet.pos);
        buffer.writeNbt(packet.nbtData);
    }

    public static holoItemDisplayPacket decode(FriendlyByteBuf buffer) {
        BlockPos pos = buffer.readBlockPos();
        CompoundTag nbtData = buffer.readNbt();
        return new holoItemDisplayPacket(pos, nbtData);
    }

    public static void handle(holoItemDisplayPacket packet, net.minecraft.server.level.ServerLevel world) {
        BlockEntity blockEntity = world.getBlockEntity(packet.pos);
        if (blockEntity != null) {
            // Apply NBT data to the block entity
            blockEntity.load(packet.nbtData);
        }
    }

    public static void sendToClient(ServerPlayer player, holoItemDisplayPacket packet) {
        FriendlyByteBuf buffer = new FriendlyByteBuf(Unpooled.buffer());
        holoItemDisplayPacket.encode(packet, buffer);
        ServerPlayNetworking.send(player, new ResourceLocation("finerperipherals", "update_holo_item_display_nbt"), buffer);
    }

    public static void sendToAllClients(ServerLevel world, BlockPos pos, CompoundTag nbtData) {
        world.getPlayers(player -> true).forEach(player -> {
           sendToClient(player, new holoItemDisplayPacket(pos, nbtData));
        });
    };
}
