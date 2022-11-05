/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.Block$Properties
 *  net.minecraft.block.BlockState
 *  net.minecraft.block.DirectionalBlock
 *  net.minecraft.block.IWaterLoggable
 *  net.minecraft.block.material.PushReaction
 *  net.minecraft.fluid.Fluid
 *  net.minecraft.fluid.Fluids
 *  net.minecraft.fluid.IFluidState
 *  net.minecraft.item.BlockItemUseContext
 *  net.minecraft.state.IProperty
 *  net.minecraft.state.StateContainer$Builder
 *  net.minecraft.state.properties.BlockStateProperties
 *  net.minecraft.util.Direction
 *  net.minecraft.util.Mirror
 *  net.minecraft.util.Rotation
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.shapes.ISelectionContext
 *  net.minecraft.util.math.shapes.VoxelShape
 *  net.minecraft.world.IBlockReader
 *  net.minecraft.world.IWorld
 *  net.minecraft.world.IWorldReader
 *  net.minecraft.world.World
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package io.github.codetoil.redstoneelectronic.block;

import io.github.codetoil.redstoneelectronic.properties.PropertiesRE;
import io.github.codetoil.redstoneelectronic.properties.Rotation_H;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DirectionalBlock;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.block.material.PushReaction;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.IProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class StickBlock
extends DirectionalBlock
implements IWaterLoggable {
    protected static final VoxelShape STICK_VERTICAL_AABB = Block.func_208617_a((double)7.0, (double)0.0, (double)7.0, (double)9.0, (double)16.0, (double)9.0);
    protected static final VoxelShape STICK_NS_AABB = Block.func_208617_a((double)7.0, (double)7.0, (double)0.0, (double)9.0, (double)9.0, (double)16.0);
    protected static final VoxelShape STICK_EW_AABB = Block.func_208617_a((double)0.0, (double)7.0, (double)7.0, (double)16.0, (double)9.0, (double)9.0);

    public StickBlock(Block.Properties p_i48415_1_) {
        super(p_i48415_1_);
        this.func_180632_j((BlockState)((BlockState)((BlockState)((BlockState)this.field_176227_L.func_177621_b()).func_206870_a((IProperty)field_176387_N, (Comparable)Direction.NORTH)).func_206870_a(PropertiesRE.ROTATION_H, (Comparable)((Object)Rotation_H.FRONT))).func_206870_a((IProperty)BlockStateProperties.field_208198_y, (Comparable)Boolean.FALSE));
    }

    public BlockState func_185499_a(BlockState p_185499_1_, Rotation p_185499_2_) {
        return (BlockState)p_185499_1_.func_206870_a((IProperty)field_176387_N, (Comparable)p_185499_2_.func_185831_a((Direction)p_185499_1_.func_177229_b((IProperty)field_176387_N)));
    }

    public BlockState func_185471_a(BlockState p_185471_1_, Mirror p_185471_2_) {
        return (BlockState)p_185471_1_.func_206870_a((IProperty)field_176387_N, (Comparable)p_185471_2_.func_185803_b((Direction)p_185471_1_.func_177229_b((IProperty)field_176387_N)));
    }

    public VoxelShape func_220053_a(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
        switch (((Direction)p_220053_1_.func_177229_b((IProperty)field_176387_N)).func_176740_k()) {
            default: {
                return STICK_EW_AABB;
            }
            case Z: {
                return STICK_NS_AABB;
            }
            case Y: 
        }
        return STICK_VERTICAL_AABB;
    }

    public String func_149739_a() {
        return "item.minecraft.stick";
    }

    public BlockState func_196258_a(BlockItemUseContext p_196258_1_) {
        Direction lvt_2_1_ = p_196258_1_.func_196000_l();
        BlockState lvt_3_1_ = p_196258_1_.func_195991_k().func_180495_p(p_196258_1_.func_195995_a().func_177972_a(lvt_2_1_.func_176734_d()));
        Fluid fluid = p_196258_1_.func_195991_k().func_204610_c(p_196258_1_.func_195995_a()).func_206886_c();
        BlockState state1 = (BlockState)this.func_176223_P().func_206870_a((IProperty)BlockStateProperties.field_208198_y, (Comparable)Boolean.valueOf(fluid == Fluids.field_204546_a || fluid == Fluids.field_207212_b));
        return lvt_3_1_.func_177230_c() == this && lvt_3_1_.func_177229_b((IProperty)field_176387_N) == lvt_2_1_ ? (BlockState)state1.func_206870_a((IProperty)field_176387_N, (Comparable)lvt_2_1_.func_176734_d()) : (BlockState)state1.func_206870_a((IProperty)field_176387_N, (Comparable)lvt_2_1_);
    }

    @OnlyIn(value=Dist.CLIENT)
    public void func_180655_c(BlockState p_180655_1_, World p_180655_2_, BlockPos p_180655_3_, Random p_180655_4_) {
    }

    protected void func_206840_a(StateContainer.Builder<Block, BlockState> p_206840_1_) {
        p_206840_1_.func_206894_a(new IProperty[]{field_176387_N, PropertiesRE.ROTATION_H, BlockStateProperties.field_208198_y});
    }

    public boolean func_220074_n(BlockState p_220074_1_) {
        return true;
    }

    public BlockState func_196271_a(BlockState p_196271_1_, Direction p_196271_2_, BlockState p_196271_3_, IWorld p_196271_4_, BlockPos p_196271_5_, BlockPos p_196271_6_) {
        if (((Boolean)p_196271_1_.func_177229_b((IProperty)BlockStateProperties.field_208198_y)).booleanValue()) {
            p_196271_4_.func_205219_F_().func_205360_a(p_196271_5_, (Object)Fluids.field_204546_a, Fluids.field_204546_a.func_205569_a((IWorldReader)p_196271_4_));
        }
        return super.func_196271_a(p_196271_1_, p_196271_2_, p_196271_3_, p_196271_4_, p_196271_5_, p_196271_6_);
    }

    public PushReaction func_149656_h(BlockState p_149656_1_) {
        return PushReaction.NORMAL;
    }

    public IFluidState func_204507_t(BlockState state) {
        return (Boolean)state.func_177229_b((IProperty)BlockStateProperties.field_208198_y) != false ? Fluids.field_204546_a.func_207204_a(false) : super.func_204507_t(state);
    }
}

