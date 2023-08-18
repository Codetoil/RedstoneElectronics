/**
 *  Redstone Electronics is a MC Mod that adds redstone components.
 *  Redstone Electronics (C) 2020-2023  Codetoil
 * <p>
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 * <p>
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 * <p>
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package io.github.codetoil.redstoneelectronics.world.level.block;

import io.github.codetoil.redstoneelectronics.world.level.block.state.properties.REProperties;
import io.github.codetoil.redstoneelectronics.world.level.block.state.properties.SelectorOrientation;
import java.util.EnumSet;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DiodeBlock;
import net.minecraft.world.level.block.RedStoneWireBlock;
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

public class RedstoneRotarySelectorBlock
extends DiodeBlock {
    public RedstoneRotarySelectorBlock(BlockBehaviour.Properties builder) {
        super(builder);
        this.registerDefaultState(this.stateDefinition.any()
            .setValue(FACING, Direction.NORTH)
            .setValue(REProperties.SELECTOR_ORIENTATION, SelectorOrientation.FRONT)
            .setValue(POWERED, Boolean.FALSE)
            .setValue(REProperties.DRIVEN, Boolean.FALSE));
    }

    protected int getDelay(BlockState state) {
        return 2;
    }

    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult blockHitResult) {
        if (!player.mayBuild()) {
            return InteractionResult.PASS;
        }
        SelectorOrientation current = state.getValue(REProperties.SELECTOR_ORIENTATION);
        state = state.setValue(REProperties.SELECTOR_ORIENTATION, current.next(state));
        level.setBlock(pos, state, 3);
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    protected int getInputSignal(Level level, BlockPos pos, BlockState state) {
        Direction direction = state.getValue(FACING);
        direction = state.getValue(REProperties.SELECTOR_ORIENTATION).reverseApply(direction);
        BlockPos blockpos = pos.offset(direction.getNormal());
        int i = level.getSignal(blockpos, direction);
        if (i >= 15) {
            return i;
        }
        BlockState blockstate = level.getBlockState(blockpos);
        return Math.max(i, blockstate.getBlock() == Blocks.REDSTONE_WIRE ? blockstate.getValue(RedStoneWireBlock.POWER) : 0);
    }

    public int getSignal(BlockState blockState, BlockGetter blockGetter, BlockPos pos, Direction direction) {
        if (!(blockState.getValue(POWERED)).booleanValue()) {
            return 0;
        }
        return blockState.getValue(FACING) == direction ? this.getOutputSignal(blockGetter, pos, blockState) : 0;
    }

    protected void updateNeighborsInFront(Level level, BlockPos pos, BlockState state) {
        Direction direction = state.getValue(FACING);
        BlockPos blockpos1 = pos.offset(direction.getOpposite().getNormal());
        BlockPos blockpos2 = pos.offset(direction.getClockWise().getNormal());
        BlockPos blockpos3 = pos.offset(direction.getCounterClockWise().getNormal());
        if (ForgeEventFactory.onNeighborNotify(level, pos, level.getBlockState(pos), EnumSet.of(direction.getOpposite()), false).isCanceled()
         || ForgeEventFactory.onNeighborNotify(level, pos, level.getBlockState(pos), EnumSet.of(direction.getClockWise()), false).isCanceled()
         || ForgeEventFactory.onNeighborNotify(level, pos, level.getBlockState(pos), EnumSet.of(direction.getCounterClockWise()), false).isCanceled()) {
            return;
        }
        level.neighborChanged(blockpos1, this, pos);
        level.updateNeighborsAtExceptFromFacing(blockpos1, this, direction);
        level.neighborChanged(blockpos2, this, pos);
        level.updateNeighborsAtExceptFromFacing(blockpos2, this, direction);
        level.neighborChanged(blockpos3, this, pos);
        level.updateNeighborsAtExceptFromFacing(blockpos3, this, direction);
    }

    protected int getOutputSignal(BlockGetter blockGetter, BlockPos pos, BlockState state) {
        if (!(blockGetter instanceof Level)) {
            return 0;
        }
        return Math.max(this.getInputSignal((Level) blockGetter, pos, state) - 1, 0);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, REProperties.SELECTOR_ORIENTATION, POWERED, REProperties.DRIVEN);
    }
}

