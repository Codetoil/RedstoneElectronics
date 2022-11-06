package io.github.codetoil.redstoneelectronic.properties;

import java.util.function.Function;
import net.minecraft.util.Direction;
import net.minecraft.util.IStringSerializable;

public enum SelectorOrientation implements IStringSerializable
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

