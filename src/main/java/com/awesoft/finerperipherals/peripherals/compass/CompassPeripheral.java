package com.awesoft.finerperipherals.peripherals.compass;

import com.awesoft.finerperipherals.peripherals.chatbox.chatBoxAttachedManager;
import com.awesoft.finerperipherals.peripherals.chatbox.chatBoxBlock;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.lua.MethodResult;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Set;

public class CompassPeripheral implements IPeripheral {

    BlockEntity blockEntity;
    private int compId;

    public CompassPeripheral(BlockEntity entity)
    {
        this.blockEntity=entity;
    }

    @Override
    public String getType() {
        return "compass";
    }

    @Override
    public boolean equals(IPeripheral other) {
        return other instanceof CompassPeripheral;
    }


    @Override
    public void attach(IComputerAccess computer) {
        IPeripheral.super.attach(computer);
        compId= computer.getID();
    }


    @LuaFunction
    public final MethodResult getDirection () {
        return MethodResult.of(blockEntity.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING).getName());
    }

    @LuaFunction
    public final MethodResult getDirectionInHub() {
        return MethodResult.of(blockEntity.getLevel().getBlockEntity(blockEntity.getBlockPos()).getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING).getName());
    }
    //"in hub" means inside of peripheralium hub

}
