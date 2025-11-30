/**
 * Redstone Electronics is a MC Mod that adds redstone components.
 * Redstone Electronics (C) 2020-2025  Codetoil
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package io.codetoil.redstone_electronics.world.level.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class AxleBlock
	extends DirectionalBlock
	implements SimpleWaterloggedBlock
{
	public static final MapCodec<AxleBlock> CODEC = simpleCodec(AxleBlock::new);
	protected static final VoxelShape STICK_VERTICAL_AABB = Block.box(7.0, 0.0, 7.0, 9.0, 16.0, 9.0);
	protected static final VoxelShape STICK_NS_AABB = Block.box(7.0, 7.0, 0.0, 9.0, 9.0, 16.0);
	protected static final VoxelShape STICK_EW_AABB = Block.box(0.0, 7.0, 7.0, 16.0, 9.0, 9.0);

	public AxleBlock(BlockBehaviour.Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any()
			.setValue(AxleBlock.FACING, Direction.NORTH)
			.setValue(BlockStateProperties.WATERLOGGED, Boolean.FALSE));
	}

	@Override
	public @NotNull MapCodec<AxleBlock> codec() {
		return CODEC;
	}

	public @NotNull BlockState rotate(BlockState blockState, Rotation rotation) {
		return blockState.setValue(FACING, rotation.rotate(blockState.getValue(FACING)));
	}

	public @NotNull BlockState mirror(BlockState blockState, Mirror mirror) {
		return blockState.setValue(FACING, mirror.mirror(blockState.getValue(FACING)));
	}

	public @NotNull VoxelShape getShape(
		BlockState blockState, BlockGetter blockReader, BlockPos blockPos,
		CollisionContext selectionContext
	) {
		switch ((blockState.getValue(FACING)).getAxis()) {
			case X: {
				return STICK_EW_AABB;
			}
			case Z: {
				return STICK_NS_AABB;
			}
			case Y:
		}
		return STICK_VERTICAL_AABB;
	}

	public boolean propagatesSkylightDown(
		@NotNull BlockState state, @NotNull BlockGetter block,
		@NotNull BlockPos pos
	) {
		return true;
	}

	public BlockState getStateForPlacement(BlockPlaceContext context) {
		Direction direction = context.getClickedFace();
		BlockState state1 = context.getLevel()
			.getBlockState(context.getClickedPos().relative(direction.getOpposite()));
		Fluid fluid = context.getLevel().getFluidState(context.getClickedPos()).getType();
		BlockState state2 = this.stateDefinition.any().setValue(
			BlockStateProperties.WATERLOGGED,
			fluid == Fluids.WATER || fluid == Fluids.FLOWING_WATER);
		return state1.getBlock() == this && state1.getValue(FACING) == direction
			? state2.setValue(FACING, direction.getOpposite())
			:state2.setValue(FACING, direction);
	}

	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateContainerBuilder) {
		stateContainerBuilder.add(FACING, BlockStateProperties.WATERLOGGED);
	}

	public boolean useShapeForLightOcclusion(@NotNull BlockState state) {
		return true;
	}

	public @NotNull BlockState updateShape(
		BlockState state, Direction direction, BlockState neighborState,
		LevelAccessor level, BlockPos pos, BlockPos neighborPos
	) {
		if (state.getValue(BlockStateProperties.WATERLOGGED)) {
			level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
		}
		return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
	}

	public @NotNull FluidState getFluidState(BlockState state) {
		return state.getValue(BlockStateProperties.WATERLOGGED) ? Fluids.WATER.getSource(false)
			:super.getFluidState(state);
	}

	@Override
	protected boolean isPathfindable(BlockState state, PathComputationType pathComputationType) {
		return false;
	}
}
