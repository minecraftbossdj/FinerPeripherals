package com.awesoft.finerperipherals.peripherals.holoitemdisplay;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import static net.minecraft.world.level.block.DirectionalBlock.FACING;

public class holoItemDisplayBlock extends BaseEntityBlock {
    public holoItemDisplayBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.DOWN));
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        Direction facing = blockState.getValue(FACING);

        switch (facing) {
            case DOWN:
                return Block.box(2, 0, 2, 14, 3, 14); // Default shape for DOWN
            case UP:
                return Block.box(2, 13, 2, 14, 16, 14); // Adjust for UP-facing
            case NORTH:
                return Block.box(2, 2, 0, 14, 14, 3); // Adjust for NORTH-facing
            case SOUTH:
                return Block.box(2, 2, 13, 14, 14, 16); // Adjust for SOUTH-facing
            case EAST:
                return Block.box(13, 2, 2, 16, 14, 14); // Adjust for EAST-facing
            case WEST:
                return Block.box(0, 2, 2, 3, 14, 14); // Adjust for WEST-facing
            default:
                return Block.box(2, 0, 2, 14, 3, 14); // Default fallback (DOWN)
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getClickedFace().getOpposite());
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        Direction facing = state.getValue(FACING); // Get the block's facing direction
        BlockPos supportPos = pos.relative(facing); // Get the block it’s attached to
        BlockState supportState = level.getBlockState(supportPos);
        return supportState.isFaceSturdy(level, supportPos, facing);
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos neighborPos, boolean moved) {
        if (!state.canSurvive(level, pos)) {
            level.destroyBlock(pos, true); // Drop the block and break it
        }
        super.neighborChanged(state, level, pos, block, neighborPos, moved);
    }

        @Override
    public RenderShape getRenderShape(BlockState blockState) {

        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new holoItemDisplayBlockEntity(blockPos,blockState);
    }
}
