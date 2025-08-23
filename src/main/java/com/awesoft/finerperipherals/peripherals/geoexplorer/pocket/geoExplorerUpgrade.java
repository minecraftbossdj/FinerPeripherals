package com.awesoft.finerperipherals.peripherals.geoexplorer.pocket;

import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.pocket.AbstractPocketUpgrade;
import dan200.computercraft.api.pocket.IPocketAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class geoExplorerUpgrade extends AbstractPocketUpgrade {



    public geoExplorerUpgrade(ResourceLocation id, ItemStack stack) {
        super(id, stack);
    }

    @Override
    public IPeripheral createPeripheral(IPocketAccess access) {
        return new geoExplorerPeripheralPocket(access);
    }
}
