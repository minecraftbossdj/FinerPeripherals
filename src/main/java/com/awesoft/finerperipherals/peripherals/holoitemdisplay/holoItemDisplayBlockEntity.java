package com.awesoft.finerperipherals.peripherals.holoitemdisplay;

import com.awesoft.finerperipherals.FinerPeripherals;
import com.awesoft.finerperipherals.network.holoItemDisplayPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.intellij.lang.annotations.Identifier;

public class holoItemDisplayBlockEntity extends BlockEntity {
    public holoItemDisplayBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(FinerPeripherals.HOLOITEMDISPLAY_BE, blockPos, blockState);
    }

    String storedItem = "minecraft:air"; // Default value


    // Save custom data to NBT
    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putString("StoredItem", this.storedItem);
    }

    public void sendNBTToClients() {
        CompoundTag tag = new CompoundTag();
        this.saveAdditional(tag); // Save the block entity's NBT data

        // Send the updated NBT to all clients
        if (this.level instanceof ServerLevel serverLevel) {
            holoItemDisplayPacket.sendToAllClients(serverLevel, this.getBlockPos(), tag);
        } else {
            FinerPeripherals.LOGGER.warn("This operation can only be performed on the server side.");
        }
    }

    // Load custom data from NBT
    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains("StoredItem")) {
            this.storedItem = tag.getString("StoredItem");
        }
        sendNBTToClients();
    }

    // Setter for the stored item
    public void setStoredItem(String item) {
        FinerPeripherals.LOGGER.info("setting item: "+item);
        this.storedItem = item;
        this.setChanged();

        sendNBTToClients();
    }

    public String getStoredItem() {
        //FinerPeripherals.LOGGER.info("getting stored item!");
        return this.storedItem;
    }
}
