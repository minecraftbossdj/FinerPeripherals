package com.awesoft.finerperipherals.peripherals.chatbox;

import dan200.computercraft.api.lua.IArguments;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.lua.MethodResult;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import net.minecraft.core.Direction;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.injection.struct.InjectorGroupInfo;

import java.util.ArrayList;
import java.util.HashMap;

public class chatBoxPeripheral implements IPeripheral {

    BlockEntity blockEntity;
    private int compId;

    public chatBoxPeripheral(BlockEntity entity)
    {
        this.blockEntity=entity;
    }

    @Override
    public String getType() {
        return "chatBox";
    }

    @Override
    public boolean equals(IPeripheral other) {
        return other instanceof chatBoxBlock;
    }



    @Override
    public void attach(IComputerAccess computer) {
        IPeripheral.super.attach(computer);
        compId= computer.getID();
        chatBoxAttachedManager.getInstance().addComputer(computer);
    }

    @Override
    public void detach(@NotNull IComputerAccess computer) {
        chatBoxAttachedManager.getInstance().removeComputer(computer);

    }

    @LuaFunction
    public final MethodResult sendMessage(String msg, String username, String brackets) {
        blockEntity.getLevel().getServer().getPlayerList().getPlayers().forEach(player -> {
            player.sendSystemMessage(Component.literal(brackets.charAt(0)+username+brackets.charAt(1)+" "+msg));
        });
        return MethodResult.of(true);
    }

    @LuaFunction
    public final MethodResult sendMessageToPlayer(String msg, String plr, String username, String brackets) {
        blockEntity.getLevel().getServer().getPlayerList().getPlayerByName(plr).sendSystemMessage(Component.literal(brackets.charAt(0)+username+brackets.charAt(1)+" "+msg));
        return MethodResult.of(true);
    }

    @LuaFunction
    public final MethodResult tellraw(String msg) {
        blockEntity.getLevel().getServer().getPlayerList().getPlayers().forEach(player -> {
            player.sendSystemMessage(Component.literal(msg));
        });
        return MethodResult.of(true);
    }

    @LuaFunction
    public final MethodResult tellrawToPlayer(String msg, String plr) {
        blockEntity.getLevel().getServer().getPlayerList().getPlayerByName(plr).sendSystemMessage(Component.literal(msg));
        return MethodResult.of(true);
    }
}
