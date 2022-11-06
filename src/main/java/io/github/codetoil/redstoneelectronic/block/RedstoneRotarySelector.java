package io.github.codetoil.redstoneelectronic.block;

import io.github.codetoil.redstoneelectronic.properties.REProperties;
import io.github.codetoil.redstoneelectronic.properties.SelectorOrientation;
import java.util.EnumSet;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.RedstoneDiodeBlock;
import net.minecraft.block.RedstoneWireBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

public class RedstoneRotarySelector
extends RedstoneDiodeBlock {
    public RedstoneRotarySelector(Block.Properties builder) {
        super(builder);
        this.registerDefaultState(this.stateDefinition.any()
            .setValue(FACING, Direction.NORTH)
            .setValue(REProperties.SELECTOR_ORIENTATION, SelectorOrientation.FRONT)
            .setValue(POWERED, Boolean.FALSE));
    }

    protected int getDelay(BlockState state) {
        return 2;
    }

    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult blockRayTraceResult) {
        if (!player.abilities.mayBuild) {
            return ActionResultType.PASS;
        }
        world.setBlock(pos, state.cycle(REProperties.SELECTOR_ORIENTATION), 3);
        return ActionResultType.SUCCESS;
    }

    protected int getInputSignal(World worldIn, BlockPos pos, BlockState state) {
        Direction direction = state.getValue(FACING);
        direction = state.getValue(REProperties.SELECTOR_ORIENTATION).reverseApply(direction);
        BlockPos blockpos = pos.offset(direction.getNormal());
        int i = worldIn.getSignal(blockpos, direction);
        if (i >= 15) {
            return i;
        }
        BlockState blockstate = worldIn.getBlockState(blockpos);
        return Math.max(i, blockstate.getBlock() == Blocks.REDSTONE_WIRE ? blockstate.getValue(RedstoneWireBlock.POWER) : 0);
    }

    public int getSignal(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side) {
        if (!(blockState.getValue(POWERED)).booleanValue()) {
            return 0;
        }
        return blockState.getValue(FACING) == side ? this.getOutputSignal(blockAccess, pos, blockState) : 0;
    }

    protected void updateNeighborsInFront(World worldIn, BlockPos pos, BlockState state) {
        Direction direction = state.getValue(FACING);
        BlockPos blockpos1 = pos.offset(direction.getOpposite().getNormal());
        BlockPos blockpos2 = pos.offset(direction.getClockWise().getNormal());
        BlockPos blockpos3 = pos.offset(direction.getCounterClockWise().getNormal());
        if (ForgeEventFactory.onNeighborNotify((World)worldIn, pos, worldIn.getBlockState(pos), EnumSet.of(direction.getOpposite()), false).isCanceled()
         || ForgeEventFactory.onNeighborNotify((World)worldIn, pos, worldIn.getBlockState(pos), EnumSet.of(direction.getClockWise()), false).isCanceled()
         || ForgeEventFactory.onNeighborNotify((World)worldIn, pos, worldIn.getBlockState(pos), EnumSet.of(direction.getCounterClockWise()), false).isCanceled()) {
            return;
        }
        worldIn.neighborChanged(blockpos1, this, pos);
        worldIn.updateNeighborsAtExceptFromFacing(blockpos1, this, direction);
        worldIn.neighborChanged(blockpos2, this, pos);
        worldIn.updateNeighborsAtExceptFromFacing(blockpos2, this, direction);
        worldIn.neighborChanged(blockpos3, this, pos);
        worldIn.updateNeighborsAtExceptFromFacing(blockpos3, this, direction);
    }

    protected int getOutputSignal(IBlockReader worldIn, BlockPos pos, BlockState state) {
        if (!(worldIn instanceof World)) {
            return 0;
        }
        return Math.max(this.getInputSignal((World)worldIn, pos, state) - 1, 0);
    }

    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING, REProperties.SELECTOR_ORIENTATION, POWERED);
    }
}

