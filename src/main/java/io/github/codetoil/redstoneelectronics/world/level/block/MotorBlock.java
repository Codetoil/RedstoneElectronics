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

import javax.annotation.Nullable;

import io.github.codetoil.redstoneelectronics.world.level.block.state.properties.REProperties;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehavior;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;

public class MotorBlock
extends DirectionalBlock {
    public MotorBlock(BlockBehavior.Properties builder) {
        super(builder);
        this.registerDefaultState(this.stateDefinition.any()
        .setValue(FACING, Direction.NORTH)
        .setValue(BlockStateProperties.POWERED, Boolean.FALSE)
        .setValue(REProperties.CURRENTLY_ROTATING, Boolean.FALSE));
    }

    public BlockState rotate(BlockState state, Rotation rotate) {
        return state.setValue(FACING, rotate.rotate(state.getValue(FACING)));
    }

    public BlockState rotate(BlockState state, LevelAccessor world, BlockPos pos, Rotation direction) {
        return super.rotate(state, world, pos, direction);
    }

    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.setValue(FACING, mirrorIn.mirror(state.getValue(FACING)));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, BlockStateProperties.POWERED, REProperties.CURRENTLY_ROTATING);
    }

    @Override
    public boolean hasBlockEntity(BlockState state) {
        return super.hasBlockEntity(state);
    }

    @Override
    @Nullable
    public BlockEntity createBlockEntity(BlockState state, BlockGetter world) {
        return super.createBlockEntity(state, world);
    }
}

