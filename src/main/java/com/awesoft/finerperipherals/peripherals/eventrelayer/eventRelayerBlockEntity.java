package com.awesoft.finerperipherals.peripherals.eventrelayer;

import com.awesoft.finerperipherals.FinerPeripherals;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class eventRelayerBlockEntity extends BlockEntity {
    public eventRelayerBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(FinerPeripherals.EVENTRELAYER_BE, blockPos, blockState);
    }
}