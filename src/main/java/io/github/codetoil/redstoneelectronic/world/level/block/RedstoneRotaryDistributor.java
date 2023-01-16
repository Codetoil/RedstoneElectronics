/**
 *  Redstone Electronics is a MC Mod that adds restone components.
 *  Redstone Electronics (C) 2020-2023  Codetoil
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 * 
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package io.github.codetoil.redstoneelectronic.blocks;

import io.github.codetoil.redstoneelectronic.properties.REProperties;
import io.github.codetoil.redstoneelectronic.properties.SelectorOrientation;
import java.util.EnumSet;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RedstoneDiodeBlock;
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

public class RedstoneRotaryDistributor
extends RedstoneDiodeBlock {
    public RedstoneRotaryDistributor(Block.Properties builder) {
        super(builder);
        this.registerDefaultState(this.stateDefinition.any()
            .setValue(FACING, Direction.NORTH)
            .setValue(REProperties.SELECTOR_ORIENTATION, SelectorOrientation.FRONT)
            .setValue(POWERED, Boolean.FALSE));
    }

    protected int getDelay(BlockState state) {
        return 2;
    }

    public int getSignal(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side) {
        if (!((Boolean)blockState.getValue(POWERED)).booleanValue()) {
            return 0;
        }
        return blockState.getValue(FACING) == blockState.getValue(REProperties.SELECTOR_ORIENTATION).reverseApply(side) ? this.getSignal(blockAccess, pos, blockState) : 0;
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

    protected int getSignal(IBlockReader worldIn, BlockPos pos, BlockState state) {
        if (!(worldIn instanceof World)) {
            return 0;
        }
        return Math.max(this.getInputSignal((World)worldIn, pos, state) - 1, 0);
    }

    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult blockRayTraceResult) {
        if (!player.abilities.mayBuild) {
            return ActionResultType.PASS;
        }
        world.setBlock(pos, state.cycle(REProperties.SELECTOR_ORIENTATION), 3);
        return ActionResultType.SUCCESS;
    }

    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING, REProperties.SELECTOR_ORIENTATION, POWERED);
    }
}

