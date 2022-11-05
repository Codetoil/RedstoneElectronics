/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.Block$Properties
 *  net.minecraft.block.BlockState
 *  net.minecraft.block.RedstoneDiodeBlock
 *  net.minecraft.entity.player.PlayerEntity
 *  net.minecraft.state.IProperty
 *  net.minecraft.state.StateContainer$Builder
 *  net.minecraft.util.ActionResultType
 *  net.minecraft.util.Direction
 *  net.minecraft.util.Hand
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.BlockRayTraceResult
 *  net.minecraft.world.IBlockReader
 *  net.minecraft.world.World
 */
package io.github.codetoil.redstoneelectronic.block;

import io.github.codetoil.redstoneelectronic.properties.PropertiesRE;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RedstoneDiodeBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.IProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class ResistorBlock
extends RedstoneDiodeBlock {
    public ResistorBlock(Block.Properties builder) {
        super(builder);
        this.func_180632_j((BlockState)((BlockState)((BlockState)((BlockState)this.field_176227_L.func_177621_b()).func_206870_a((IProperty)field_185512_D, (Comparable)Direction.NORTH)).func_206870_a((IProperty)PropertiesRE.RESISTANCE_1_4, (Comparable)Integer.valueOf(1))).func_206870_a((IProperty)field_196348_c, (Comparable)Boolean.FALSE));
    }

    protected int func_196346_i(BlockState p_196346_1_) {
        return 2;
    }

    protected int func_176408_a(IBlockReader worldIn, BlockPos pos, BlockState state) {
        if (!(worldIn instanceof World)) {
            return 0;
        }
        return Math.max(this.func_176397_f((World)worldIn, pos, state) - (Integer)state.func_177229_b((IProperty)PropertiesRE.RESISTANCE_1_4), 0);
    }

    public ActionResultType func_225533_a_(BlockState p_225533_1_, World p_225533_2_, BlockPos p_225533_3_, PlayerEntity p_225533_4_, Hand p_225533_5_, BlockRayTraceResult p_225533_6_) {
        if (!p_225533_4_.field_71075_bZ.field_75099_e) {
            return ActionResultType.PASS;
        }
        p_225533_2_.func_180501_a(p_225533_3_, (BlockState)p_225533_1_.func_177231_a((IProperty)PropertiesRE.RESISTANCE_1_4), 3);
        return ActionResultType.SUCCESS;
    }

    protected void func_206840_a(StateContainer.Builder<Block, BlockState> builder) {
        builder.func_206894_a(new IProperty[]{field_185512_D, PropertiesRE.RESISTANCE_1_4, field_196348_c});
    }
}

