package io.github.codetoil.redstoneelectronic.properties;

import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.IntegerProperty;

public class REProperties {
    public static final EnumProperty<SelectorOrientation> SELECTOR_ORIENTATION = EnumProperty.create("selector_orientation", SelectorOrientation.class);
    public static final IntegerProperty RESISTANCE_1_4 = IntegerProperty.create("resistance", (int)1, (int)4);
    public static final BooleanProperty CURRENTLY_ROTATING = BooleanProperty.create("currently_rotating");

    public static void init() {
    }
}

