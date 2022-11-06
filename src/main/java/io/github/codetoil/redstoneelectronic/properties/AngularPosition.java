package io.github.codetoil.redstoneelectronic.properties;

import java.util.function.Function;
import net.minecraft.util.Direction;
import net.minecraft.util.IStringSerializable;

public enum AngularPosition implements IStringSerializable
{
    COUNTERCLOCKWISE_RIGHT_ANGLE(Direction::getCounterClockWise, Direction::getClockWise, "ccw"),
    NULL_ANGLE(a -> a, a -> a, "zero"),
    CLOCKWISE_RIGHT_ANGLE(Direction::getClockWise, Direction::getCounterClockWise, "cw"),
    STRAIGHT_ANGLE(Direction::getOpposite, Direction::getOpposite, "straight");

    private final Function<Direction, Direction> onApply;
    private final String name;
    private final Function<Direction, Direction> reverseApply;

    private AngularPosition(Function<Direction, Direction> onApply, Function<Direction, Direction> reverseApply, String name) {
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

