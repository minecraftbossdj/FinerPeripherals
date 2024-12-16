package com.awesoft.finerperipherals.blocks.chatbox.pocket;

import com.awesoft.finerperipherals.FinerPeripherals;
import com.awesoft.finerperipherals.blocks.chatbox.chatBoxBlockEntity;
import com.awesoft.finerperipherals.blocks.chatbox.chatBoxPeripheral;
import com.awesoft.finerperipherals.blocks.chatbox.pocket.chatBoxPeripheralPocket;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.pocket.AbstractPocketUpgrade;
import dan200.computercraft.api.pocket.IPocketAccess;
import dan200.computercraft.api.pocket.IPocketUpgrade;
import dan200.computercraft.api.upgrades.UpgradeBase;
import dan200.computercraft.api.upgrades.UpgradeDataProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import org.w3c.dom.Text;

public class ChatBoxUpgrade extends AbstractPocketUpgrade {



    public ChatBoxUpgrade(ResourceLocation id, ItemStack stack) {
        super(id, stack);
    }

    @Override
    public IPeripheral createPeripheral(IPocketAccess access) {
        return new chatBoxPeripheralPocket(access);
    }
}
