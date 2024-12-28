package com.awesoft.finerperipherals.peripherals.eventrelayer;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class eventRelayerBlock extends BaseEntityBlock {
    public eventRelayerBlock(Properties properties) {
        super(properties);

    }

    @Override
    public RenderShape getRenderShape(BlockState blockState) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new eventRelayerBlockEntity(blockPos,blockState);
    }
}
