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
import net.minecraft.core.particles.DustParticleOptions;
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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class ResistorBlock
extends DiodeBlock {
    public ResistorBlock(BlockBehaviour.Properties builder) {
        super(builder);
        this.registerDefaultState(this.stateDefinition.any()
            .setValue(FACING, Direction.NORTH)
            .setValue(REProperties.RESISTANCE_1_4, 1)
            .setValue(POWERED, Boolean.FALSE));
    }

    public @NotNull InteractionResult use(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos,
                                          Player player, @NotNull InteractionHand hand,
                                          @NotNull BlockHitResult blockHitResult) {
        if (!player.mayBuild()) {
            return InteractionResult.PASS;
        }
        level.setBlock(pos, state.cycle(REProperties.RESISTANCE_1_4), 3);
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    protected int getDelay(@NotNull BlockState state) {
        return 2;
    }

    protected int getOutputSignal(@NotNull BlockGetter blockGetter, @NotNull BlockPos pos, @NotNull BlockState state) {
        if (!(blockGetter instanceof Level)) {
            return 0;
        }
        return Math.max(this.getInputSignal((Level) blockGetter, pos, state) - state.getValue(REProperties.RESISTANCE_1_4), 0);
    }

    protected boolean m_6137_(@NotNull BlockState state) {
        return isDiode(state);
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

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, REProperties.RESISTANCE_1_4, POWERED);
    }
}

