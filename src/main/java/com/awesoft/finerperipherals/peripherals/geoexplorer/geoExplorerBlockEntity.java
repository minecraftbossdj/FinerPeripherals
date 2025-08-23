package com.awesoft.finerperipherals.peripherals.geoexplorer;

import com.awesoft.finerperipherals.FinerPeripherals;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class geoExplorerBlockEntity extends BlockEntity {
    public geoExplorerBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(FinerPeripherals.GEOEXPLORER_BE, blockPos, blockState);
    }
}
