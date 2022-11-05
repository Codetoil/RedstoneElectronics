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
 *  net.minecraftforge.event.ForgeEventFactory
 */
package io.github.codetoil.redstoneelectronic.block;

import io.github.codetoil.redstoneelectronic.properties.PropertiesRE;
import io.github.codetoil.redstoneelectronic.properties.Rotation_LFR;
import java.util.EnumSet;
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
import net.minecraftforge.event.ForgeEventFactory;

public class RedstoneRotaryDistributor
extends RedstoneDiodeBlock {
    public RedstoneRotaryDistributor(Block.Properties builder) {
        super(builder);
        this.func_180632_j((BlockState)((BlockState)((BlockState)((BlockState)this.field_176227_L.func_177621_b()).func_206870_a((IProperty)field_185512_D, (Comparable)Direction.NORTH)).func_206870_a(PropertiesRE.ROTATION_LFR, (Comparable)((Object)Rotation_LFR.FRONT))).func_206870_a((IProperty)field_196348_c, (Comparable)Boolean.FALSE));
    }

    protected int func_196346_i(BlockState p_196346_1_) {
        return 2;
    }

    public int func_180656_a(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side) {
        if (!((Boolean)blockState.func_177229_b((IProperty)field_196348_c)).booleanValue()) {
            return 0;
        }
        return blockState.func_177229_b((IProperty)field_185512_D) == ((Rotation_LFR)((Object)blockState.func_177229_b(PropertiesRE.ROTATION_LFR))).reverseApply(side) ? this.func_176408_a(blockAccess, pos, blockState) : 0;
    }

    protected void func_176400_h(World worldIn, BlockPos pos, BlockState state) {
        Direction direction = (Direction)state.func_177229_b((IProperty)field_185512_D);
        BlockPos blockpos1 = pos.func_177972_a(direction.func_176734_d());
        BlockPos blockpos2 = pos.func_177972_a(direction.func_176746_e());
        BlockPos blockpos3 = pos.func_177972_a(direction.func_176735_f());
        if (ForgeEventFactory.onNeighborNotify((World)worldIn, (BlockPos)pos, (BlockState)worldIn.func_180495_p(pos), EnumSet.of(direction.func_176734_d()), (boolean)false).isCanceled() || ForgeEventFactory.onNeighborNotify((World)worldIn, (BlockPos)pos, (BlockState)worldIn.func_180495_p(pos), EnumSet.of(direction.func_176746_e()), (boolean)false).isCanceled() || ForgeEventFactory.onNeighborNotify((World)worldIn, (BlockPos)pos, (BlockState)worldIn.func_180495_p(pos), EnumSet.of(direction.func_176735_f()), (boolean)false).isCanceled()) {
            return;
        }
        worldIn.func_190524_a(blockpos1, (Block)this, pos);
        worldIn.func_175695_a(blockpos1, (Block)this, direction);
        worldIn.func_190524_a(blockpos2, (Block)this, pos);
        worldIn.func_175695_a(blockpos2, (Block)this, direction);
        worldIn.func_190524_a(blockpos3, (Block)this, pos);
        worldIn.func_175695_a(blockpos1, (Block)this, direction);
    }

    protected int func_176408_a(IBlockReader worldIn, BlockPos pos, BlockState state) {
        if (!(worldIn instanceof World)) {
            return 0;
        }
        return Math.max(this.func_176397_f((World)worldIn, pos, state) - 1, 0);
    }

    public ActionResultType func_225533_a_(BlockState p_225533_1_, World p_225533_2_, BlockPos p_225533_3_, PlayerEntity p_225533_4_, Hand p_225533_5_, BlockRayTraceResult p_225533_6_) {
        if (!p_225533_4_.field_71075_bZ.field_75099_e) {
            return ActionResultType.PASS;
        }
        p_225533_2_.func_180501_a(p_225533_3_, (BlockState)p_225533_1_.func_177231_a(PropertiesRE.ROTATION_LFR), 3);
        return ActionResultType.SUCCESS;
    }

    protected void func_206840_a(StateContainer.Builder<Block, BlockState> builder) {
        builder.func_206894_a(new IProperty[]{field_185512_D, PropertiesRE.ROTATION_LFR, field_196348_c});
    }
}

