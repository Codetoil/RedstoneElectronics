package io.github.codetoil.redstoneelectronic.properties;

import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.IntegerProperty;

public class PropertiesRE {
    public static final EnumProperty<Rotation_H> ROTATION_H = EnumProperty.create("rotation_h", Rotation_H.class);
    public static final EnumProperty<Rotation_LFR> ROTATION_LFR = EnumProperty.create("rotation_lfr", Rotation_LFR.class);
    public static final IntegerProperty RESISTANCE_1_4 = IntegerProperty.create("resistance", (int)1, (int)4);
    public static final BooleanProperty POWERED_LAST_TICK = BooleanProperty.create("powered_last_tick");

    public static void init() {
    }
}

