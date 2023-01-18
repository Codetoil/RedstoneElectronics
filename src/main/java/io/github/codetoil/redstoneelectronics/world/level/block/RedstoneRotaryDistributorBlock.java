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

package io.github.codetoil.redstoneelectronics.world.level.block;

import io.github.codetoil.redstoneelectronics.world.level.block.state.properties.REProperties;
import io.github.codetoil.redstoneelectronics.world.level.block.state.properties.SelectorOrientation;
import java.util.EnumSet;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DiodeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraftforge.event.ForgeEventFactory;

public class RedstoneRotaryDistributorBlock
extends DiodeBlock {
    public RedstoneRotaryDistributorBlock(BlockBehaviour.Properties builder) {
        super(builder);
        this.registerDefaultState(this.stateDefinition.any()
            .setValue(FACING, Direction.NORTH)
            .setValue(REProperties.SELECTOR_ORIENTATION, SelectorOrientation.FRONT)
            .setValue(POWERED, Boolean.FALSE));
    }

    protected int getDelay(BlockState state) {
        return 2;
    }

    public int getSignal(BlockState blockState, BlockGetter blockAccess, BlockPos pos, Direction side) {
        if (!((Boolean)blockState.getValue(POWERED)).booleanValue()) {
            return 0;
        }
        return blockState.getValue(FACING) == blockState.getValue(REProperties.SELECTOR_ORIENTATION).reverseApply(side) ? this.getSignal(blockAccess, pos, blockState) : 0;
    }

    protected void updateNeighborsInFront(Level worldIn, BlockPos pos, BlockState state) {
        Direction direction = state.getValue(FACING);
        BlockPos blockpos1 = pos.offset(direction.getOpposite().getNormal());
        BlockPos blockpos2 = pos.offset(direction.getClockWise().getNormal());
        BlockPos blockpos3 = pos.offset(direction.getCounterClockWise().getNormal());
        if (ForgeEventFactory.onNeighborNotify(worldIn, pos, worldIn.getBlockState(pos), EnumSet.of(direction.getOpposite()), false).isCanceled()
         || ForgeEventFactory.onNeighborNotify(worldIn, pos, worldIn.getBlockState(pos), EnumSet.of(direction.getClockWise()), false).isCanceled()
         || ForgeEventFactory.onNeighborNotify(worldIn, pos, worldIn.getBlockState(pos), EnumSet.of(direction.getCounterClockWise()), false).isCanceled()) {
            return;
        }
        worldIn.neighborChanged(blockpos1, this, pos);
        worldIn.updateNeighborsAtExceptFromFacing(blockpos1, this, direction);
        worldIn.neighborChanged(blockpos2, this, pos);
        worldIn.updateNeighborsAtExceptFromFacing(blockpos2, this, direction);
        worldIn.neighborChanged(blockpos3, this, pos);
        worldIn.updateNeighborsAtExceptFromFacing(blockpos3, this, direction);
    }

    protected int getSignal(BlockGetter worldIn, BlockPos pos, BlockState state) {
        if (!(worldIn instanceof Level)) {
            return 0;
        }
        return Math.max(this.getInputSignal((Level) worldIn, pos, state) - 1, 0);
    }

    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult blockRayTraceResult) {
        if (!player.mayBuild()) {
            return InteractionResult.PASS;
        }
        world.setBlock(pos, state.cycle(REProperties.SELECTOR_ORIENTATION), 3);
        return InteractionResult.SUCCESS;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, REProperties.SELECTOR_ORIENTATION, POWERED);
    }
}

