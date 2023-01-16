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

package io.github.codetoil.redstoneelectronic.properties;

import java.util.function.Function;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;

public enum SelectorOrientation implements StringRepresentable
{
    LEFT(Direction::getCounterClockWise, Direction::getClockWise, "left"),
    FRONT(a -> a, a -> a, "front"),
    RIGHT(Direction::getClockWise, Direction::getCounterClockWise, "right");

    private final Function<Direction, Direction> onApply;
    private final String name;
    private final Function<Direction, Direction> reverseApply;

    private SelectorOrientation(Function<Direction, Direction> onApply, Function<Direction, Direction> reverseApply, String name) {
        this.name = name;
        this.onApply = onApply;
        this.reverseApply = reverseApply;
    }

    public Direction onApply(Direction direction) {
        return this.onApply.apply(direction);
    }

    public Direction reverseApply(Direction direction) {
        return this.reverseApply.apply(direction);
    }

    public String getSerializedName() {
        return this.name;
    }
}

