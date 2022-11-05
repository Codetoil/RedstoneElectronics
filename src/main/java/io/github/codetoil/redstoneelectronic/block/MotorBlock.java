package io.github.codetoil.redstoneelectronic.block;

import io.github.codetoil.redstoneelectronic.properties.PropertiesRE;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DirectionalBlock;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

public class MotorBlock
extends DirectionalBlock {
    public MotorBlock(Block.Properties p_i48415_1_) {
        super(p_i48415_1_);
        this.registerDefaultState(this.stateDefinition.any()
        .setValue(FACING, Direction.NORTH)
        .setValue(BlockStateProperties.POWERED, Boolean.FALSE)
        .setValue(PropertiesRE.POWERED_LAST_TICK, Boolean.FALSE));
    }

    public BlockState rotate(BlockState state, Rotation rotate) {
        return state.setValue(FACING, rotate.rotate(state.getValue(FACING)));
    }

    public BlockState rotate(BlockState state, IWorld world, BlockPos pos, Rotation direction) {
        return super.rotate(state, world, pos, direction);
    }

    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.setValue(FACING, mirrorIn.mirror(state.getValue(FACING)));
    }

    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING, BlockStateProperties.POWERED, PropertiesRE.POWERED_LAST_TICK);
    }
}

