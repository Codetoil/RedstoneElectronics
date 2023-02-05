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


import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import io.github.codetoil.redstoneelectronics.world.level.block.state.properties.REProperties;
import io.github.codetoil.redstoneelectronics.world.level.block.state.properties.SelectorOrientation;

public class ServoMotorStructureResolver {
    private final Level level;
    private final BlockPos motorPos;
    private BlockPos finalPos;
    private final Direction direction;
    private SelectorOrientation goal;
    private final List<BlockPos> toRotate = Lists.newArrayList();

    public ServoMotorStructureResolver(Level level, BlockPos motorPos, Direction direction) {
        this.level = level;
        this.motorPos = motorPos;
        this.direction = direction;
    }

    public boolean resolve() {
        this.toRotate.clear();
        this.finalPos = this.motorPos.relative(this.direction); // TODO Temporary. Add stick rotation.
        BlockState state = this.level.getBlockState(this.finalPos);
        if (!state.hasProperty(REProperties.SELECTOR_ORIENTATION))
        {
            return false;
        }
        SelectorOrientation current = state.getValue(REProperties.SELECTOR_ORIENTATION);
        this.goal = current.next(state);
        return true;
    }

    public Direction getMotorFacingDirection() {
        return this.direction;
    }

    public Direction.Axis getMotorAxis() {
        return this.direction.getAxis();
    }

    public SelectorOrientation getGoal() {
        return this.goal;
    }

    public List<BlockPos> getBlocksToRotate() {
        return this.toRotate;
    }

    public BlockPos getBlockToCycle() {
        return this.finalPos;
    }
}