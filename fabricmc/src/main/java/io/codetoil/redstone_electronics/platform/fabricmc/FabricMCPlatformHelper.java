package io.codetoil.redstone_electronics.platform.fabricmc;

import io.codetoil.redstone_electronics.platform.services.IPlatformHelper;
import io.codetoil.redstone_electronics.world.level.block.entity.RedstoneElectronicsBlockEntityTypes;
import io.codetoil.redstone_electronics.world.level.block.servo_motor.ServoMotorBlockEntity;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.Supplier;

public class FabricMCPlatformHelper implements IPlatformHelper
{
	@Override
	public String getPlatformName() {
		return "FabricMC";
	}

	@Override
	public boolean isModLoaded(String modId) {

		return FabricLoader.getInstance().isModLoaded(modId);
	}

	@Override
	public boolean isDevelopmentEnvironment() {

		return FabricLoader.getInstance().isDevelopmentEnvironment();
	}

	@Override
	public Supplier<BlockEntityType<ServoMotorBlockEntity>> getServoMotorBlockEntityType() {
		return () -> RedstoneElectronicsBlockEntityTypes.SERVO_MOTOR_BLOCK_ENTITY_TYPE;
	}
}
