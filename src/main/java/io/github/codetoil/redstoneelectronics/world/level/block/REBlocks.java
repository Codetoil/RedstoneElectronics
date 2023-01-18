/**
 *  Redstone Electronics is a MC Mod that adds restone components.
 *  Redstone Electronics (C) 2020-2023  Codetoil
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 * 
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package io.github.codetoil.redstoneelectronics.world.level.block;

import io.github.codetoil.redstoneelectronic.RedstoneElectronics;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehavior;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class REBlocks {
    private static final DeferredRegister<Block> RE_BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, RedstoneElectronics.MODID);
    private static final DeferredRegister<Block> MC_BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, "minecraft");

    public static final RegistryObject<Block> RESISTOR_BLOCK = RE_BLOCKS.register("redstone_resistor", () -> new ResistorBlock(BlockBehavior.Properties.of(Material.DECORATION).strength(0.0f).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> ROTARY_DISTRIBUTOR_BLOCK = RE_BLOCKS.register("redstone_rotary_selector", () -> new RedstoneRotarySelectorBlock(BlockBehavior.Properties.of(Material.DECORATION).strength(0.0f).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> ROTARY_SELECTOR_BLOCK = RE_BLOCKS.register("redstone_rotary_distributer", () -> new RedstoneRotaryDistributorBlock(BlockBehavior.Properties.of(Material.DECORATION).strength(0.0f).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> MOTOR_BLOCK = RE_BLOCKS.register("redstone_motor", () -> new MotorBlock(BlockBehavior.Properties.of(Material.PISTON).strength(0.5f)));
    public static final RegistryObject<Block> STICK_BLOCK = MC_BLOCKS.register("stick", () -> new StickBlock(BlockBehavior.Properties.of(Material.WOOD).strength(2.0f).sound(SoundType.WOOD)));

    public static void init() {
        RE_BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        MC_BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}