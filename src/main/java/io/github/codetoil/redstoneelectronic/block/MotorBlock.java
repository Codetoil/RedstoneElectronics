/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.Block$Properties
 *  net.minecraft.block.BlockState
 *  net.minecraft.block.DirectionalBlock
 *  net.minecraft.state.IProperty
 *  net.minecraft.state.StateContainer$Builder
 *  net.minecraft.state.properties.BlockStateProperties
 *  net.minecraft.util.Direction
 *  net.minecraft.util.Mirror
 *  net.minecraft.util.Rotation
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.world.IWorld
 */
package io.github.codetoil.redstoneelectronic.block;

import io.github.codetoil.redstoneelectronic.properties.PropertiesRE;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DirectionalBlock;
import net.minecraft.state.IProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

public class MotorBlock
extends DirectionalBlock {
    public MotorBlock(Block.Properties p_i48415_1_) {
        super(p_i48415_1_);
        this.func_180632_j((BlockState)((BlockState)((BlockState)((BlockState)this.field_176227_L.func_177621_b()).func_206870_a((IProperty)field_176387_N, (Comparable)Direction.NORTH)).func_206870_a((IProperty)BlockStateProperties.field_208194_u, (Comparable)Boolean.valueOf(false))).func_206870_a((IProperty)PropertiesRE.POWERED_LAST_TICK, (Comparable)Boolean.valueOf(false)));
    }

    public BlockState func_185499_a(BlockState state, Rotation rot) {
        return (BlockState)state.func_206870_a((IProperty)field_176387_N, (Comparable)rot.func_185831_a((Direction)state.func_177229_b((IProperty)field_176387_N)));
    }

    public BlockState rotate(BlockState state, IWorld world, BlockPos pos, Rotation direction) {
        return super.rotate(state, world, pos, direction);
    }

    public BlockState func_185471_a(BlockState state, Mirror mirrorIn) {
        return state.func_185907_a(mirrorIn.func_185800_a((Direction)state.func_177229_b((IProperty)field_176387_N)));
    }

    protected void func_206840_a(StateContainer.Builder<Block, BlockState> builder) {
        builder.func_206894_a(new IProperty[]{field_176387_N, BlockStateProperties.field_208194_u, PropertiesRE.POWERED_LAST_TICK});
    }
}

