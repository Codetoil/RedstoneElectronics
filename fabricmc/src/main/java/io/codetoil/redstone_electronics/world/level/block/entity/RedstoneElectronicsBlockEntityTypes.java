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

package io.codetoil.redstone_electronics.world.level.block.entity;

import io.codetoil.redstone_electronics.Constants;
import io.codetoil.redstone_electronics.world.level.block.RedstoneElectronicsBlocks;
import io.codetoil.redstone_electronics.world.level.block.servo_motor.ServoMotorBlock;
import io.codetoil.redstone_electronics.world.level.block.servo_motor.ServoMotorBlockEntity;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class RedstoneElectronicsBlockEntityTypes
{
	public static final BlockEntityType<ServoMotorBlockEntity> SERVO_MOTOR_BLOCK_ENTITY_TYPE =
		register("servo_motor", ((ServoMotorBlock) RedstoneElectronicsBlocks.SERVO_MOTOR_BLOCK)::newBlockEntity,
			RedstoneElectronicsBlocks.SERVO_MOTOR_BLOCK);

	@SuppressWarnings("SameParameterValue")
	private static <T extends BlockEntity> BlockEntityType<T> register(String name,
		BlockEntityType.BlockEntitySupplier<T> builder, Block... blocks) {
		ResourceLocation id = ResourceLocation.tryBuild(Constants.MOD_ID, name);
		return Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, id,
			BlockEntityType.Builder.of(builder, blocks).build());
	}

	public static void init() { }
}
