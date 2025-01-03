package com.awesoft.finerperipherals.peripherals.eventrelayer.pocket;

import com.awesoft.finerperipherals.peripherals.eventrelayer.eventRelayerPeripheral;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.pocket.AbstractPocketUpgrade;
import dan200.computercraft.api.pocket.IPocketAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class eventRelayerUpgrade extends AbstractPocketUpgrade {



    public eventRelayerUpgrade(ResourceLocation id, ItemStack stack) {
        super(id, stack);
    }

    @Override
    public IPeripheral createPeripheral(IPocketAccess access) {
        return new eventRelayerPeripheral();
    }
}
