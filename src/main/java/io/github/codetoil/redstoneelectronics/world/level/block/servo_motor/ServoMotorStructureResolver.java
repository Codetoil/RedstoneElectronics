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

package io.github.codetoil.redstoneelectronics.world.level.block.motor.MotorBlock;


import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import io.github.codetoil.redstoneelectronics.world.level.block.state.properties.SelectorOrientation;

public class ServoMotorStructureResolver {
    private final Level level;
    private final BlockPos startPos;
    private final Direction.Axis axis;
    private final SelectorOrientation goalOrientation;
    private final List<BlockPos> toRotate = Lists.newArrayList();

    public ServoMotorStructureResolver(Level level, BlockPos startPos, Direction.Axis axis, SelectorOrientation goalOrientation) {
        this.level = level;
        this.startPos = startPos;
        this.axis = axis;
        this.goalOrientation = goalOrientation;
    }

    public boolean resolve() {
        this.toRotate.clear();
        if (!this.level.getBlockState(this.startPos).hasProperty(REProperties.SELECTOR_ORIENTATION))
        { // TODO Add stick rotation.
            return false;
        }
        this.toRotate.add(this.startPos);
        return true;
    }

    public Direction.Axis getRotationAxis() {
        return this.axis;
    }

    public List<BlockPos> getToRotate() {
        return this.toRotate;
    }
}