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

package io.github.codetoil.redstoneelectronics.world.level.block.entity;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;

import io.github.codetoil.redstoneelectronics.world.level.block.state.properties;

public class MotorBlockEntity extends BlockEntity {
    private BlockPos posOfRotatingBlock;
    private SelectorOrientation goalOrientation;
    private float progress;

    public MotorBlockEntity(BlockPos pos, BlockState state) {
        super(REBlockEntityTypes.MOTOR_BLOCK_ENTITY_TYPE.get(), pos, state);
    }

    protected void saveAdditional(CompoundTag tag) {

    }

    void load(CompoundTag tag) {

    }

    public static void tick(Level level, BlockPos pos, BlockState state, MotorBlockEntity blockEntity) {
        
    }
}
