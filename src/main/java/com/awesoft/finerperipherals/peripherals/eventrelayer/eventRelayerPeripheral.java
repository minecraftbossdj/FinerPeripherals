package com.awesoft.finerperipherals.peripherals.eventrelayer;

import com.awesoft.finerperipherals.peripherals.chatbox.chatBoxAttachedManager;
import dan200.computercraft.api.lua.IArguments;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.lua.MethodResult;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class eventRelayerPeripheral implements IPeripheral {

    private int compId;
    private IComputerAccess comp;

    public eventRelayerPeripheral() {}

    @Override
    public String getType() {
        return "eventRelayer";
    }

    @Override
    public boolean equals(IPeripheral other) {
        return other instanceof eventRelayerBlock;
    }



    @Override
    public void attach(IComputerAccess computer) {
        IPeripheral.super.attach(computer);
        compId= computer.getID();
        comp= computer;
        eventRelayerAttachedManager.getInstance().addComputer(computer);
    }

    @Override
    public void detach(@NotNull IComputerAccess computer) {
        eventRelayerAttachedManager.getInstance().removeComputer(computer);
    }

    @LuaFunction(mainThread = true)
    public final MethodResult relayEvent(int targetId, String message)  {
        eventRelayerAttachedManager.getInstance().sendMessageToComputer(targetId, "secure_message", compId, message);
        return MethodResult.of(true);
    }

}
