package com.awesoft.finerperipherals.peripherals.playerfinder;

import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.lua.MethodResult;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class playerFinderPeripheral implements IPeripheral {

    BlockEntity blockEntity;
    private int compId;

    public playerFinderPeripheral(BlockEntity entity)
    {
        this.blockEntity=entity;
    }

    @Override
    public String getType() {
        return "playerFinder";
    }

    @Override
    public boolean equals(IPeripheral other) {
        return other instanceof playerFinderBlock;
    }


    @LuaFunction
    public final MethodResult getOnlinePlayers() {

        List<String> data = new ArrayList<>();

        assert blockEntity.getLevel() != null;
        blockEntity.getLevel().getServer().getPlayerList().getPlayers().forEach(player -> {
            data.add(player.getGameProfile().getName());
        });
        return MethodResult.of(true,data);
    }

    @LuaFunction
    public final MethodResult getPlayerPos(String plr) {
        ServerPlayer player = blockEntity.getLevel().getServer().getPlayerList().getPlayerByName(plr);
        if (player != null) {
            HashMap<String, Object> data = new HashMap<>();
            HashMap<String, Object> respawnPos = new HashMap<>();

            if (player.getRespawnPosition() != null) {
                respawnPos.put("x", player.getRespawnPosition().getX());
                respawnPos.put("y", player.getRespawnPosition().getY());
                respawnPos.put("z", player.getRespawnPosition().getZ());
                respawnPos.put("dimension", player.getRespawnDimension().location().toString());
            }

            data.put("x",player.position().x);
            data.put("y",player.position().y);
            data.put("z",player.position().z);
            data.put("dimension",player.level().dimension().location().toString());
            if (player.getRespawnPosition() != null) {data.put("respawnPos",respawnPos);}

            return MethodResult.of(true,data);

        } else {
            return MethodResult.of(false, "Player not online!");
        }
    }
}
