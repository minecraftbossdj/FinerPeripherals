package com.awesoft.finerperipherals.peripherals.playerfinder;

import com.awesoft.finerperipherals.FinerPeripherals;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class playerFinderBlockEntity extends BlockEntity {
    public playerFinderBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(FinerPeripherals.PLAYER_FINDER_BE, blockPos, blockState);
    }

}
