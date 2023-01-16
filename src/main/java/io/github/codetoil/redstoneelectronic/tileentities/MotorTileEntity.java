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

package io.github.codetoil.redstoneelectronic.tileentities;

import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public class MotorTileEntity extends TileEntity implements ITickableTileEntity {
    private BlockPos posOfFinalBlock;
    private int rotationalVelocity;

    public MotorTileEntity() {
        super(RETileEntityTypes.MOTOR_TYLE_ENTITY_TYPE.get()); // temporary
    }

    @Override
    public void tick() {
        // TODO Auto-generated method stub
        
    }
    
}
