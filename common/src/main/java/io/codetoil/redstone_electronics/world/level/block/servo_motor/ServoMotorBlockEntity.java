/**
 * Redstone Electronics is a MC Mod that adds redstone components.
 * Redstone Electronics (C) 2020-2023  Codetoil
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

package io.codetoil.redstone_electronics.world.level.block.servo_motor;

import io.codetoil.redstone_electronics.platform.Services;
import io.codetoil.redstone_electronics.world.level.block.state.properties.REProperties;
import io.codetoil.redstone_electronics.world.level.block.state.properties.SelectorOrientation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.commons.compress.utils.Lists;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ServoMotorBlockEntity extends BlockEntity
{
	private final List<BlockPos> rotatedPositions = Lists.newArrayList();
	private BlockPos cycledPos;
	private Direction direction;
	private SelectorOrientation goalOrientation;
	private float progress;
	private float progressO;
	private long lastTicked;
	private int deathTicks;

	public ServoMotorBlockEntity(BlockPos motorPos, BlockState motorState) {
		super(Services.PLATFORM.getServoMotorBlockEntityType().get(), motorPos, motorState);
	}

	public ServoMotorBlockEntity(
		BlockPos motorPos,
		BlockState motorState,
		BlockPos cycledPos,
		List<BlockPos> rotatedPositions,
		Direction direction,
		SelectorOrientation goalOrientation
	) {
		this(motorPos, motorState);
		this.cycledPos = cycledPos;
		this.rotatedPositions.addAll(rotatedPositions);
		this.direction = direction;
		this.goalOrientation = goalOrientation;
	}

	@Override
	protected void saveAdditional(@NotNull CompoundTag tag, HolderLookup.Provider registries) {
		super.saveAdditional(tag, registries);
		tag.put("cycledPos", NbtUtils.writeBlockPos(this.cycledPos));
		ListTag rotatedTag = new ListTag();
		rotatedPositions
			.stream()
			.map(NbtUtils::writeBlockPos)
			.forEach(rotatedTag::add);
		tag.putInt("rotatedPositionsLength", rotatedTag.size());
		tag.put("rotatedPositions", rotatedTag);
		tag.putInt("direction", this.direction.get3DDataValue());
		tag.putFloat("progress", this.progressO);
		tag.putString("goal", this.goalOrientation.getSerializedName());
	}

	@Override
	public void loadAdditional(@NotNull CompoundTag tag, HolderLookup.Provider registries) {
		super.loadAdditional(tag, registries);
		this.cycledPos = NbtUtils.readBlockPos(tag, "blockPos").orElseThrow();
		int rotatedPositionsSize = tag.getInt("rotatedPositionsLength");
		ListTag rotatedTags = tag.getList("rotatedPositions", rotatedPositionsSize);
		rotatedTags.stream()
			.map(rotatedTag -> (CompoundTag) rotatedTag)
			.map(BlockEntity::getPosFromTag)
			.forEach(rotatedPositions::add);
		this.direction = Direction.from3DDataValue(tag.getInt("direction"));
		this.progressO = this.progress = tag.getFloat("progress");
		this.goalOrientation = SelectorOrientation.valueOf(tag.getString("goal"));
	}

	@Override
	public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	@SuppressWarnings("null") // Invalid warnings, suppress them.
	public void finalTick() {
		if (this.level != null && !this.level.isClientSide) {
			this.progressO = this.progress = 1.0f;
			this.level.removeBlockEntity(this.worldPosition);
			this.setRemoved();
			this.level.setBlock(this.worldPosition, this.level.getBlockState(this.worldPosition)
					.setValue(REProperties.SPINNING, false)
					.setValue(REProperties.HAS_BEEN_ACTIVATED, true),
				Block.UPDATE_CLIENTS | Block.UPDATE_NEIGHBORS);
			this.level.setBlock(this.cycledPos, this.level.getBlockState(this.cycledPos)
					.setValue(REProperties.DRIVEN, true)
					.setValue(REProperties.SELECTOR_ORIENTATION, this.goalOrientation),
				Block.UPDATE_CLIENTS | Block.UPDATE_NEIGHBORS);
		}
	}

	public void tick(Level level, BlockPos pos, BlockState state) {
		this.lastTicked = level.getGameTime();
		this.progressO = this.progress;
		if (this.progressO >= 1.0f) {
			if (level.isClientSide && this.deathTicks < 5) {
				++this.deathTicks;
			} else {
				this.finalTick();
			}
		} else {
			float f = this.progress + ((this.goalOrientation == SelectorOrientation.LEFT) ? 0.25f:0.5f);
			this.progress = f;
			if (this.progress >= 1.0f) {
				this.progress = 1.0f;
			}
		}
		this.setChanged();
	}

	public float getProgress(float tValue) {
		if (tValue > 1.0f) {
			tValue = 1.0f;
		}
		return Mth.lerp(tValue, this.progressO, this.progress);
	}

	public long getLastTicked() {
		return this.lastTicked;
	}

	@Override
	public String toString() {
		return "ServoMotorBlockEntity[" + this.cycledPos + "," + this.rotatedPositions + "," + this.direction + ","
			   + this.goalOrientation + "]";
	}

}
