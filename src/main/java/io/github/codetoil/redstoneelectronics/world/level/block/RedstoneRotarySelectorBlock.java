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
import java.util.Random;

import net.minecraft.core.particles.DustParticleOptions;
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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.ForgeEventFactory;
import org.jetbrains.annotations.NotNull;

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

    protected int getDelay(@NotNull BlockState state) {
        return 2;
    }

    protected int getOutputSignal(@NotNull BlockGetter blockGetter, @NotNull BlockPos pos, @NotNull BlockState state) {
        if (!(blockGetter instanceof Level)) {
            return 0;
        }
        return Math.max(this.getInputSignal((Level) blockGetter, pos, state) - 1, 0);
    }

    public @NotNull InteractionResult use(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos,
                                          Player player, @NotNull InteractionHand hand,
                                          @NotNull BlockHitResult blockHitResult) {
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
        BlockPos blockpos = pos.m_142300_(direction);
        int i = level.m_46681_(blockpos, direction);
        if (i >= 15) {
            return i;
        }
        BlockState blockstate = level.getBlockState(blockpos);
        return Math.max(i, blockstate.getBlock() == Blocks.REDSTONE_WIRE ? blockstate.getValue(RedStoneWireBlock.POWER)
                : 0);
    }

    public int getSignal(BlockState blockState, @NotNull BlockGetter blockGetter, @NotNull BlockPos pos,
                         @NotNull Direction direction) {
        if (!blockState.getValue(POWERED)) {
            return 0;
        }
        return blockState.getValue(FACING) == direction ? this.getOutputSignal(blockGetter, pos, blockState) : 0;
    }

    @OnlyIn(value = Dist.CLIENT)
    public void m_7100_(BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Random random) {
        if (state.getValue(POWERED)) {
            Direction direction = state.getValue(FACING);
            double d0 = (double)pos.getX() + 0.5D + (random.nextDouble() - 0.5D) * 0.2D;
            double d1 = (double)pos.getY() + 0.4D + (random.nextDouble() - 0.5D) * 0.2D;
            double d2 = (double)pos.getZ() + 0.5D + (random.nextDouble() - 0.5D) * 0.2D;
            float f = random.nextBoolean() ? -0.3125F : 0.0625F;

            double d3 = f * (float)direction.getStepX();
            double d4 = f * (float)direction.getStepZ();
            level.addParticle(DustParticleOptions.REDSTONE, d0 + d3, d1, d2 + d4,
                    0.0D, 0.0D, 0.0D);
        }
    }

    protected void updateNeighborsInFront(@NotNull Level level, BlockPos pos, BlockState state) {
        Direction direction = state.getValue(FACING);
        BlockPos blockpos1 = pos.m_142300_(direction.getOpposite());
        BlockPos blockpos2 = pos.m_142300_(direction.getClockWise());
        BlockPos blockpos3 = pos.m_142300_(direction.getCounterClockWise());
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

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, REProperties.SELECTOR_ORIENTATION, POWERED, REProperties.DRIVEN);
    }
}

