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

public class ResistorBlock
extends RedstoneDiodeBlock {
    public ResistorBlock(Block.Properties builder) {
        super(builder);
        this.registerDefaultState(this.stateDefinition.any()
            .setValue(FACING, Direction.NORTH)
            .setValue(REProperties.RESISTANCE_1_4, Integer.valueOf(1))
            .setValue(POWERED, Boolean.FALSE));
    }

    protected int getDelay(BlockState state) {
        return 2;
    }

    protected int getOutputSignal(IBlockReader worldIn, BlockPos pos, BlockState state) {
        if (!(worldIn instanceof World)) {
            return 0;
        }
        return Math.max(this.getInputSignal((World) worldIn, pos, state) - state.getValue(REProperties.RESISTANCE_1_4), 0);
    }

    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult blockRayTraceResult) {
        if (!player.abilities.mayBuild) {
            return ActionResultType.PASS;
        }
        world.setBlock(pos, state.cycle(REProperties.RESISTANCE_1_4), 3);
        return ActionResultType.SUCCESS;
    }

    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING, REProperties.RESISTANCE_1_4, POWERED);
    }
}

