/**
 *  Redstone Electronics is a MC Mod that adds redstone components.
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

package io.github.codetoil.redstoneelectronics.world.level.block.servo_motor;

import javax.annotation.Nullable;

import io.github.codetoil.redstoneelectronics.world.level.block.state.properties.REProperties;
import io.github.codetoil.redstoneelectronics.world.level.block.state.properties.SelectorOrientation;
import io.github.codetoil.redstoneelectronics.world.level.block.entity.REBlockEntityTypes;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;


public class ServoMotorBlock
extends DirectionalBlock implements EntityBlock {
    public ServoMotorBlock(BlockBehaviour.Properties builder) {
        super(builder);
        this.registerDefaultState(this.stateDefinition.any()
        .setValue(FACING, Direction.NORTH)
        .setValue(REProperties.SPINNING, Boolean.FALSE));
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotate) {
        return state.setValue(FACING, rotate.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState rotate(BlockState state, LevelAccessor world, BlockPos pos, Rotation direction) {
        return super.rotate(state, world, pos, direction);
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.setValue(FACING, mirrorIn.mirror(state.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, REProperties.SPINNING);
    }

    @Override
    public ServoMotorBlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return null;
    }

    private void checkIfItShouldRotate(ServerLevel level, BlockPos pos, BlockState state)
    {
        Direction direction = state.getValue(FACING);
        boolean isPowered = level.hasNeighborSignal(pos);
        if (isPowered && !state.getValue(REProperties.SPINNING).booleanValue())
        {
            if (new ServoMotorStructureResolver(level, pos, direction).resolve())
            {
                level.blockEvent(pos, this, 0, direction.get3DDataValue());
            }
        }
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity entity, ItemStack itemStack) {
        if (!level.isClientSide && level instanceof ServerLevel) {
            this.checkIfItShouldRotate((ServerLevel) level, pos, state);
        }
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos pos2, boolean flag) {
        if (!level.isClientSide) {
            this.checkIfItShouldRotate((ServerLevel) level, pos, state);
        }
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState state2, boolean flag) {
        if (!state2.is(state.getBlock()) && !level.isClientSide && level.getBlockEntity(pos) == null) {
            this.checkIfItShouldRotate((ServerLevel) level, pos, state);
        }
    }

    @Override
    public boolean triggerEvent(BlockState state, Level level, BlockPos pos, int id, int param) {
        Direction direction = state.getValue(FACING);
        if (id != 0)
        {
            return false;
        }
        
        if (!this.startRotation(level, pos, direction))
        {
            return false;
        }

        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity == null)
        {
            return false;
        }
        return blockEntity.triggerEvent(id, param);
    }

    private boolean startRotation(Level level, BlockPos motorPos, Direction direction) {
        ServoMotorStructureResolver servoMotorStructureResolver;
        if (!(servoMotorStructureResolver = new ServoMotorStructureResolver(level, motorPos, direction)).resolve())
        {
            return false;
        }
        BlockPos finalPos = servoMotorStructureResolver.getBlockToCycle();
        if (!level.setBlock(motorPos, level.getBlockState(motorPos)
                                            .setValue(REProperties.SPINNING, true), 
                                            Block.UPDATE_CLIENTS))
        {
            return false;
        }
        if (!level.setBlock(finalPos, level.getBlockState(finalPos)
                                            .setValue(REProperties.SELECTOR_ORIENTATION, SelectorOrientation.ROTATING),
                                            Block.UPDATE_CLIENTS))
        {
            return false;
        }
        ServoMotorBlockEntity blockEntity = new ServoMotorBlockEntity(motorPos,
                                            level.getBlockState(motorPos), 
                                            finalPos, 
                                            servoMotorStructureResolver
                                                .getBlocksToRotate(),
                                            direction,
                                            servoMotorStructureResolver.getGoal());
        level.setBlockEntity(blockEntity);
        return true;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return type == REBlockEntityTypes.SERVO_MOTOR_BLOCK_ENTITY_TYPE.get() ? CastBlockEntityTicker::tick : null;
    }

    private class CastBlockEntityTicker {
        public static <T extends BlockEntity> void tick(Level level, BlockPos pos, BlockState state, T blockEntity) {
            ((ServoMotorBlockEntity) blockEntity).tick(level, pos, state);
        }
    }
}

