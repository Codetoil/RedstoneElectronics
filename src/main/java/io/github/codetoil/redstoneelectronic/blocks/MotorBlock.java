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
    public MotorBlock(Block.Properties builder) {
        super(builder);
        this.registerDefaultState(this.stateDefinition.any()
        .setValue(FACING, Direction.NORTH)
        .setValue(BlockStateProperties.POWERED, Boolean.FALSE)
        .setValue(REProperties.CURRENTLY_ROTATING, Boolean.FALSE));
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
        builder.add(FACING, BlockStateProperties.POWERED, REProperties.CURRENTLY_ROTATING);
    }
}

