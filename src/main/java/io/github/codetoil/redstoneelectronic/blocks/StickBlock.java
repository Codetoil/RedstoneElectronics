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

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DirectionalBlock;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.block.material.PushReaction;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class StickBlock
        extends DirectionalBlock
        implements IWaterLoggable {
    protected static final VoxelShape STICK_VERTICAL_AABB = Block.box(7.0, 0.0, 7.0, 9.0, 16.0, 9.0);
    protected static final VoxelShape STICK_NS_AABB = Block.box(7.0, 7.0, 0.0, 9.0, 9.0, 16.0);
    protected static final VoxelShape STICK_EW_AABB = Block.box(0.0, 7.0, 7.0, 16.0, 9.0, 9.0);

    public StickBlock(Block.Properties properties) {
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

    public VoxelShape getShape(BlockState blockState, IBlockReader blockReader, BlockPos blockPos,
            ISelectionContext selectionContext) {
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

    public BlockState getStateForPlacement(BlockItemUseContext blockItemUseContext) {
        Direction direction = blockItemUseContext.getClickedFace();
        BlockState state1 = blockItemUseContext.getLevel()
                .getBlockState(blockItemUseContext.getClickedPos().offset(direction.getOpposite().getNormal()));
        Fluid fluid = blockItemUseContext.getLevel().getFluidState(blockItemUseContext.getClickedPos()).getType();
        BlockState state2 = (BlockState) this.stateDefinition.any().setValue(
                BlockStateProperties.WATERLOGGED,
                Boolean.valueOf(fluid == Fluids.WATER || fluid == Fluids.FLOWING_WATER));
        return state1.getBlock() == this && state1.getValue(FACING) == direction
                ? state2.setValue(FACING, direction.getOpposite())
                : state2.setValue(FACING, direction);
    }

    @OnlyIn(value = Dist.CLIENT)
    public void animateTick(BlockState p_180655_1_, World p_180655_2_, BlockPos p_180655_3_, Random p_180655_4_) {
    }

    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> stateContainerBuilder) {
        stateContainerBuilder.add(FACING, BlockStateProperties.WATERLOGGED);
    }

    public boolean useShapeForLightOcclusion(BlockState state) {
        return true;
    }

    public BlockState rotate(BlockState state, IWorld world, BlockPos pos, Rotation direction) {
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

    public boolean isPathfindable(BlockState state, IBlockReader world, BlockPos pos, PathType pathType) {
        return false;
    }
}
