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

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.TickableBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.BlockPos;

public class MotorBlockEntity extends BlockEntity implements TickableBlockEntity {
    private BlockPos posOfFinalBlock;
    private int rotationalVelocity;
    private float angle;

    public MotorBlockEntity(BlockPos pos, BlockState state) {
        super(REBlockEntityTypes.MOTOR_BLOCK_ENTITY_TYPE.get(), pos, state);
    }

    @Override
    public void tick() {
        
    }


}