package com.awesoft.finerperipherals.peripherals.debugPocket;

import com.awesoft.finerperipherals.peripherals.chatbox.chatBoxAttachedManager;
import com.awesoft.finerperipherals.peripherals.eventrelayer.eventRelayerAttachedManager;
import com.awesoft.finerperipherals.peripherals.eventrelayer.eventRelayerPeripheral;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.lua.MethodResult;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.pocket.IPocketAccess;
import dan200.computercraft.shared.peripheral.speaker.SpeakerPeripheral;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Debug;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;

public class DebugPeripheral implements IPeripheral {

    IPocketAccess access;
    private int compId;
    Object periphClass;

    public DebugPeripheral(IPocketAccess access)
    {
        this.access=access;
    }

    @Override
    public String getType() {
        return "debug";
    }

    @Override
    public boolean equals(IPeripheral other) {
        return other instanceof DebugPeripheral;
    }




    @Override
    public void attach(IComputerAccess computer) {
        IPeripheral.super.attach(computer);
        compId= computer.getID();
        debugAttachedManager.getInstance().addComputer(computer);

    }

    @Override
    public void detach(@NotNull IComputerAccess computer) {
        debugAttachedManager.getInstance().removeComputer(computer);
    }

    @LuaFunction(mainThread = true)
    public final MethodResult periphList()  {
        return MethodResult.of(false, "lil bro this shit dont exist anymore, lock in");
    }


}
