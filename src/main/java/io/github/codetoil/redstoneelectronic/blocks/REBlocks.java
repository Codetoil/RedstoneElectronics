package io.github.codetoil.redstoneelectronic.blocks;

import io.github.codetoil.redstoneelectronic.RedstoneElectronics;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class REBlocks {
    private static final DeferredRegister<Block> RE_BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, RedstoneElectronics.MODID);
    private static final DeferredRegister<Block> MC_BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, "minecraft");

    public static final RegistryObject<Block> RESISTOR_BLOCK = RE_BLOCKS.register("redstone_resistor", () -> new ResistorBlock(Block.Properties.of(Material.DECORATION).strength(0.0f).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> ROTARY_DISTRIBUTOR_BLOCK = RE_BLOCKS.register("redstone_rotary_selector", () -> new RedstoneRotarySelector(Block.Properties.of(Material.DECORATION).strength(0.0f).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> ROTARY_SELECTOR_BLOCK = RE_BLOCKS.register("redstone_rotary_distributer", () -> new RedstoneRotaryDistributor(Block.Properties.of((Material)Material.DECORATION).strength(0.0f).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> MOTOR_BLOCK = RE_BLOCKS.register("redstone_motor", () -> new MotorBlock(Block.Properties.of(Material.PISTON).strength(0.5f)));
    public static final RegistryObject<Block> STICK_BLOCK = MC_BLOCKS.register("stick", () -> new StickBlock(Block.Properties.of(Material.WOOD).strength(2.0f).sound(SoundType.WOOD)));

    public static void init() {
        RE_BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        MC_BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
