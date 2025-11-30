/**
 * Redstone Electronics is a MC Mod that adds redstone components.
 * Redstone Electronics (C) 2020-2025  Codetoil
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package io.codetoil.redstone_electronics.world.level.block;

import io.codetoil.redstone_electronics.Constants;
import io.codetoil.redstone_electronics.world.level.block.servo_motor.ServoMotorBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RedstoneElectronicsBlocks
{
	private static final DeferredRegister<Block> RE_BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,
		Constants.MOD_ID);
	public static final RegistryObject<Block> RESISTOR_BLOCK =
		RE_BLOCKS.register("redstone_resistor", () ->
			new RedstoneResistorBlock(BlockBehaviour.Properties.of().instabreak().sound(SoundType.STONE)
				.pushReaction(PushReaction.DESTROY)));
	public static final RegistryObject<Block> ROTARY_SELECTOR_BLOCK =
		RE_BLOCKS.register("redstone_rotary_selector", () ->
			new RedstoneRotarySelectorBlock(BlockBehaviour.Properties.of().instabreak().sound(SoundType.STONE)
				.pushReaction(PushReaction.DESTROY)));
	public static final RegistryObject<Block> ROTARY_DISTRIBUTOR_BLOCK =
		RE_BLOCKS.register("redstone_rotary_distributor", () ->
			new RedstoneRotaryDistributorBlock(BlockBehaviour.Properties.of().instabreak().sound(SoundType.STONE)
				.pushReaction(PushReaction.DESTROY)));
	public static final RegistryObject<Block> SERVO_MOTOR_BLOCK =
		RE_BLOCKS.register("servo_motor", () ->
			new ServoMotorBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)
				.strength(0.5f)
				.sound(SoundType.STONE)));
	public static final RegistryObject<AxleBlock> OAK_AXLE_BLOCK =
		RE_BLOCKS.register("oak_axle", () ->
			new AxleBlock(BlockBehaviour.Properties.of().strength(2.0f)
				.sound(SoundType.WOOD)));

	public static void init(IEventBus bus) {
		RE_BLOCKS.register(bus);
	}
}
