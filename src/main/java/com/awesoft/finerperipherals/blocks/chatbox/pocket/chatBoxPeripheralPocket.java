package com.awesoft.finerperipherals.blocks.chatbox.pocket;

import com.awesoft.finerperipherals.blocks.chatbox.chatBoxAttachedManager;
import com.awesoft.finerperipherals.blocks.chatbox.chatBoxBlock;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.lua.MethodResult;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public class chatBoxPeripheralPocket implements IPeripheral {
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
        chatBoxAttachedManager.getInstance().addComputer(computer);
    }

    @Override
    public void detach(@NotNull IComputerAccess computer) {
        chatBoxAttachedManager.getInstance().removeComputer(computer);
    }

}
