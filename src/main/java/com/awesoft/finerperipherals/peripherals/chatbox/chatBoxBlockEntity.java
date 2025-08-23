package com.awesoft.finerperipherals.peripherals.chatbox;

import com.awesoft.finerperipherals.FinerPeripherals;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class chatBoxBlockEntity extends BlockEntity {
    public chatBoxBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(FinerPeripherals.CHATBOX_BE, blockPos, blockState);
    }





}
