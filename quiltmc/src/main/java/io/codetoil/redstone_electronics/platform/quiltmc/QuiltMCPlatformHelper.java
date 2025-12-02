package io.codetoil.redstone_electronics.platform.quiltmc;

import io.codetoil.redstone_electronics.platform.services.IPlatformHelper;
import io.codetoil.redstone_electronics.world.level.block.entity.RedstoneElectronicsBlockEntityTypes;
import io.codetoil.redstone_electronics.world.level.block.servo_motor.ServoMotorBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.quiltmc.loader.api.QuiltLoader;

import java.util.function.Supplier;

public class QuiltMCPlatformHelper implements IPlatformHelper
{
	@Override
	public String getPlatformName() {
		return "QuiltMC";
	}

	@Override
	public boolean isModLoaded(String modId) {

		return QuiltLoader.isModLoaded(modId);
	}

	@Override
	public boolean isDevelopmentEnvironment() {

		return QuiltLoader.isDevelopmentEnvironment();
	}

	@Override
	public Supplier<BlockEntityType<ServoMotorBlockEntity>> getServoMotorBlockEntityType() {
		return () -> RedstoneElectronicsBlockEntityTypes.SERVO_MOTOR_BLOCK_ENTITY_TYPE;
	}
}
