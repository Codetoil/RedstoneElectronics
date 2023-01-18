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

import io.github.codetoil.redstoneelectronic.properties.REProperties;
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

public class ResistorBlock
extends DiodeBlock {
    public ResistorBlock(BlockBehaviour.Properties builder) {
        super(builder);
        this.registerDefaultState(this.stateDefinition.any()
            .setValue(FACING, Direction.NORTH)
            .setValue(REProperties.RESISTANCE_1_4, Integer.valueOf(1))
            .setValue(POWERED, Boolean.FALSE));
    }

    protected int getDelay(BlockState state) {
        return 2;
    }

    protected int getOutputSignal(BlockGetter worldIn, BlockPos pos, BlockState state) {
        if (!(worldIn instanceof Level)) {
            return 0;
        }
        return Math.max(this.getInputSignal((Level) worldIn, pos, state) - state.getValue(REProperties.RESISTANCE_1_4), 0);
    }

    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult blockRayTraceResult) {
        if (!player.abilities.mayBuild) {
            return InteractionResult.PASS;
        }
        world.setBlock(pos, state.cycle(REProperties.RESISTANCE_1_4), 3);
        return InteractionResult.SUCCESS;
    }

    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING, REProperties.RESISTANCE_1_4, POWERED);
    }
}

