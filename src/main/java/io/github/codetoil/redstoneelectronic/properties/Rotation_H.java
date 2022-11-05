/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.Direction
 *  net.minecraft.util.IStringSerializable
 */
package io.github.codetoil.redstoneelectronic.properties;

import java.util.function.Function;
import net.minecraft.util.Direction;
import net.minecraft.util.IStringSerializable;

public enum Rotation_H implements IStringSerializable
{
    LEFT(Direction::func_176735_f, Direction::func_176746_e, "left"),
    FRONT(a -> a, a -> a, "front"),
    RIGHT(Direction::func_176746_e, Direction::func_176735_f, "right"),
    BACK(Direction::func_176734_d, Direction::func_176734_d, "back");

    private final Function<Direction, Direction> onApply;
    private final String name;
    private final Function<Direction, Direction> reverseApply;

    private Rotation_H(Function<Direction, Direction> onApply, Function<Direction, Direction> reverseApply, String name) {
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

    public String func_176610_l() {
        return this.name;
    }
}

