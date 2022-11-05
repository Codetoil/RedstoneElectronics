/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.state.BooleanProperty
 *  net.minecraft.state.EnumProperty
 *  net.minecraft.state.IntegerProperty
 */
package io.github.codetoil.redstoneelectronic.properties;

import io.github.codetoil.redstoneelectronic.properties.Rotation_H;
import io.github.codetoil.redstoneelectronic.properties.Rotation_LFR;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.IntegerProperty;

public class PropertiesRE {
    public static final EnumProperty<Rotation_H> ROTATION_H = EnumProperty.func_177709_a((String)"rotation_h", Rotation_H.class);
    public static final EnumProperty<Rotation_LFR> ROTATION_LFR = EnumProperty.func_177709_a((String)"rotation_lfr", Rotation_LFR.class);
    public static final IntegerProperty RESISTANCE_1_4 = IntegerProperty.func_177719_a((String)"resistance", (int)1, (int)4);
    public static final BooleanProperty POWERED_LAST_TICK = BooleanProperty.func_177716_a((String)"powered_last_tick");

    public static void init() {
    }
}

