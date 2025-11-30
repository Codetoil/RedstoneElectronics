package io.codetoil.redstone_electronics.platform.neoforge;

import io.codetoil.redstone_electronics.platform.services.IPlatformHelper;
import io.codetoil.redstone_electronics.world.level.block.entity.RedstoneElectronicsBlockEntityTypes;
import io.codetoil.redstone_electronics.world.level.block.servo_motor.ServoMotorBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;

import java.util.function.Supplier;

public class NeoForgePlatformHelper implements IPlatformHelper
{

	@Override
	public String getPlatformName() {

		return "NeoForge";
	}

	@Override
	public boolean isModLoaded(String modId) {

		return ModList.get().isLoaded(modId);
	}

	@Override
	public boolean isDevelopmentEnvironment() {

		return !FMLLoader.isProduction();
	}

	@Override
	public Supplier<BlockEntityType<ServoMotorBlockEntity>> getServoMotorBlockEntityType() {
		return RedstoneElectronicsBlockEntityTypes.SERVO_MOTOR_BLOCK_ENTITY_TYPE;
	}
}
