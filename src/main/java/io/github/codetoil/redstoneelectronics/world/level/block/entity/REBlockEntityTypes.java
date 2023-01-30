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

package io.github.codetoil.redstoneelectronics.world.level.block.entity;

import io.github.codetoil.redstoneelectronics.RedstoneElectronics;
import io.github.codetoil.redstoneelectronics.world.level.block.REBlocks;
import io.github.codetoil.redstoneelectronics.world.level.block.servo_motor.ServoMotorBlockEntity;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class REBlockEntityTypes {
    private static final DeferredRegister<BlockEntityType<?>> RE_BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, RedstoneElectronics.MODID);

    public static final RegistryObject<BlockEntityType<ServoMotorBlockEntity>> SERVO_MOTOR_TYLE_ENTITY_TYPE = RE_BLOCK_ENTITIES.register("servo_motor_block_entity", () -> BlockEntityType.Builder.of(ServoMotorBlockEntity::newBlockEntity, REBlocks.SERVO_MOTOR_BLOCK.get()).build(null));

    public static void init() {
        RE_BLOCK_ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
