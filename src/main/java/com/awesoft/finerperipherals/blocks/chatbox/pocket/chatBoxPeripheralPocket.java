package com.awesoft.finerperipherals.blocks.chatbox;

import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.lua.MethodResult;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.pocket.IPocketAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
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
        return "chatBox";
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
    public final MethodResult sendMessage(String msg) {
        access.getEntity().level().getServer().getPlayerList().getPlayers().forEach(player -> {
            // Now, send the message
            // To send a message, we need a Component(In this case a literal text component).
            player.sendSystemMessage(Component.literal(msg));
        });
        return MethodResult.of(true);
    }

    @LuaFunction
    public final MethodResult sendMessageToPlayer(String msg, String plr) {
        access.getEntity().level().getServer().getPlayerList().getPlayerByName(plr).sendSystemMessage(Component.literal(msg));
        return MethodResult.of(true);
    }

}
