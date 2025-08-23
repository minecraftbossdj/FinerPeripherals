package com.awesoft.finerperipherals.peripherals.chatbox.pocket;

import com.awesoft.finerperipherals.peripherals.chatbox.chatBoxAttachedManager;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.lua.MethodResult;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.pocket.IPocketAccess;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public class chatBoxPeripheralPocket implements IPeripheral {

    IPocketAccess access;
    private int compId;

    public chatBoxPeripheralPocket(IPocketAccess access)
    {
        this.access=access;
    }

    @Override
    public String getType() {
        return "chatbox";
    }

    @Override
    public boolean equals(IPeripheral other) {
        return other instanceof chatBoxPeripheralPocket;
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
        access.getEntity().level().getServer().getPlayerList().getPlayers().forEach(player -> {
            player.sendSystemMessage(Component.literal(brackets.charAt(0)+username+brackets.charAt(1)+" "+msg));
        });
        return MethodResult.of(true);
    }

    @LuaFunction
    public final MethodResult sendMessageToPlayer(String msg, String plr, String username, String brackets) {
        access.getEntity().level().getServer().getPlayerList().getPlayerByName(plr).sendSystemMessage(Component.literal(brackets.charAt(0)+username+brackets.charAt(1)+" "+msg));
        return MethodResult.of(true);
    }

    @LuaFunction
    public final MethodResult tellraw(String msg) {
        access.getEntity().level().getServer().getPlayerList().getPlayers().forEach(player -> {
            player.sendSystemMessage(Component.literal(msg));
        });
        return MethodResult.of(true);
    }

    @LuaFunction
    public final MethodResult tellrawToPlayer(String msg, String plr) {
        access.getEntity().level().getServer().getPlayerList().getPlayerByName(plr).sendSystemMessage(Component.literal(msg));
        return MethodResult.of(true);
    }

}
