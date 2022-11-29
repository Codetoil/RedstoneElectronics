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
