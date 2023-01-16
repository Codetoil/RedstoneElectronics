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

package io.github.codetoil.redstoneelectronic.tileentities;

import io.github.codetoil.redstoneelectronic.RedstoneElectronics;
import io.github.codetoil.redstoneelectronic.blocks.REBlocks;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RETileEntityTypes {
    private static final DeferredRegister<TileEntityType<?>> RE_TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, RedstoneElectronics.MODID);

    public static final RegistryObject<TileEntityType<MotorTileEntity>> MOTOR_TYLE_ENTITY_TYPE = RE_TILE_ENTITIES.register("redstone_motor_tile_entity", () -> TileEntityType.Builder.of(() -> new MotorTileEntity(), REBlocks.MOTOR_BLOCK.get()).build(null));

    public static void init() {
        RE_TILE_ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
