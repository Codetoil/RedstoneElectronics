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

import java.util.Random;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class StickBlock
        extends DirectionalBlock
        implements SimpleWaterloggedBlock {
    protected static final VoxelShape STICK_VERTICAL_AABB = Block.box(7.0, 0.0, 7.0, 9.0, 16.0, 9.0);
    protected static final VoxelShape STICK_NS_AABB = Block.box(7.0, 7.0, 0.0, 9.0, 9.0, 16.0);
    protected static final VoxelShape STICK_EW_AABB = Block.box(0.0, 7.0, 7.0, 16.0, 9.0, 9.0);

    public StickBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(StickBlock.FACING, Direction.NORTH)
                .setValue(BlockStateProperties.WATERLOGGED, Boolean.FALSE));
    }

    public BlockState rotate(BlockState blockState, Rotation rotation) {
        return blockState.setValue(FACING, rotation.rotate(blockState.getValue(FACING)));
    }

    public BlockState mirror(BlockState blockState, Mirror mirror) {
        return blockState.setValue(FACING, mirror.mirror(blockState.getValue(FACING)));
    }

    public VoxelShape getShape(BlockState blockState, BlockGetter blockReader, BlockPos blockPos,
            CollisionContext selectionContext) {
        switch ((blockState.getValue(FACING)).getAxis()) {
            default: {
                return STICK_EW_AABB;
            }
            case Z: {
                return STICK_NS_AABB;
            }
            case Y:
        }
        return STICK_VERTICAL_AABB;
    }

    public String getDescriptionId() {
        return "item.minecraft.stick";
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction direction = context.getClickedFace();
        BlockState state1 = context.getLevel()
                .getBlockState(context.getClickedPos().offset(direction.getOpposite().getNormal()));
        Fluid fluid = context.getLevel().getFluidState(context.getClickedPos()).getType();
        BlockState state2 = (BlockState) this.stateDefinition.any().setValue(
                BlockStateProperties.WATERLOGGED,
                Boolean.valueOf(fluid == Fluids.WATER || fluid == Fluids.FLOWING_WATER));
        return state1.getBlock() == this && state1.getValue(FACING) == direction
                ? state2.setValue(FACING, direction.getOpposite())
                : state2.setValue(FACING, direction);
    }

    @OnlyIn(value = Dist.CLIENT)
    public void animateTick(BlockState p_180655_1_, Level p_180655_2_, BlockPos p_180655_3_, Random p_180655_4_) {
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateContainerBuilder) {
        stateContainerBuilder.add(FACING, BlockStateProperties.WATERLOGGED);
    }

    public boolean useShapeForLightOcclusion(BlockState state) {
        return true;
    }

    public BlockState rotate(BlockState state, LevelAccessor world, BlockPos pos, Rotation direction) {
        if (state.getValue(BlockStateProperties.WATERLOGGED).booleanValue()) {
            world.getLiquidTicks().scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
        }
        return super.rotate(state, world, pos, direction);
    }

    public PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.DESTROY;
    }

    @SuppressWarnings("deprecation")
    public FluidState getFluidState(BlockState state) {
        return state.getValue(BlockStateProperties.WATERLOGGED) != false ? Fluids.WATER.getSource(false)
            : super.getFluidState(state);
    }

    public boolean isPathfindable(BlockState state, BlockGetter world, BlockPos pos, PathComputationType pathType) {
        return false;
    }
}
