package com.awesoft.finerperipherals.peripherals.chatbox.pocket;

import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.pocket.AbstractPocketUpgrade;
import dan200.computercraft.api.pocket.IPocketAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class ChatBoxUpgrade extends AbstractPocketUpgrade {



    public ChatBoxUpgrade(ResourceLocation id, ItemStack stack) {
        super(id, stack);
    }

    @Override
    public IPeripheral createPeripheral(IPocketAccess access) {
        return new chatBoxPeripheralPocket(access);
    }
}
