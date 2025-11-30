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
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class RedstoneElectronicsBlockEntityTypes
{
	private static final DeferredRegister<BlockEntityType<?>> RE_BLOCK_ENTITY_TYPES =
		DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, Constants.MOD_ID);

	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ServoMotorBlockEntity>>
		SERVO_MOTOR_BLOCK_ENTITY_TYPE = RE_BLOCK_ENTITY_TYPES.register("servo_motor", () ->
		BlockEntityType.Builder.of(((ServoMotorBlock) RedstoneElectronicsBlocks.SERVO_MOTOR_BLOCK.get())::newBlockEntity,
			RedstoneElectronicsBlocks.SERVO_MOTOR_BLOCK.get()).build(null));

	public static void init(IEventBus modEventBus) {
		RE_BLOCK_ENTITY_TYPES.register(modEventBus);
	}
}
