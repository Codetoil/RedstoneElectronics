/**
 *  Redstone Electronics is a MC Mod that adds redstone components.
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

package io.github.codetoil.redstoneelectronics.world.level.block.servo_motor;

import net.minecraft.world.level.Level;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.util.Mth;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.compress.utils.Lists;

import io.github.codetoil.redstoneelectronics.world.level.block.entity.REBlockEntityTypes;
import io.github.codetoil.redstoneelectronics.world.level.block.state.properties.REProperties;
import io.github.codetoil.redstoneelectronics.world.level.block.state.properties.SelectorOrientation;

public class ServoMotorBlockEntity extends BlockEntity {
    private BlockPos cycledPos;
    private final List<BlockPos> rotatedPositions = Lists.<BlockPos>newArrayList(); 
    private Direction direction;
    private SelectorOrientation goalOrientation;
    private float progress;
    private float progressO;
    private long lastTicked;
    private int deathTicks;

    public ServoMotorBlockEntity(BlockPos motorPos, BlockState motorState) {
        super(REBlockEntityTypes.SERVO_MOTOR_BLOCK_ENTITY_TYPE.get(), motorPos, motorState);
    }

    public ServoMotorBlockEntity(BlockPos motorPos, BlockState motorState, BlockPos cycledPos, List<BlockPos> rotatedPositions, Direction direction, SelectorOrientation goalOrientation) {
        this(motorPos, motorState);
        this.cycledPos = cycledPos;
        this.rotatedPositions.addAll(rotatedPositions);
        this.direction = direction;
        this.goalOrientation = goalOrientation;
    }

    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("cycledPos", NbtUtils.writeBlockPos(this.cycledPos));
        ListTag rotatedTag = new ListTag();
        rotatedTag.addAll(rotatedPositions
            .stream()
            .map(NbtUtils::writeBlockPos)
            .collect(Collectors.toList()));
        tag.putInt("rotatedPositionsLength", rotatedTag.size());
        tag.put("rotatedPositions", rotatedTag);
        tag.putInt("direction", this.direction.get3DDataValue());
        tag.putFloat("progress", this.progressO);
        tag.putString("goal", this.goalOrientation.getSerializedName());
    }

    public void load(CompoundTag tag) {
        super.load(tag);
        this.cycledPos = NbtUtils.readBlockPos(tag.getCompound("blockPos"));
        int rotatedPositionsSize = tag.getInt("rotatedPositionsLength");
        ListTag rotatedTags = tag.getList("rotatedPositions", rotatedPositionsSize);
        this.rotatedPositions.addAll(rotatedTags.stream()
            .map(rotatedTag -> (CompoundTag) rotatedTag)
            .map(NbtUtils::readBlockPos)
            .collect(Collectors.toList()));
        this.direction = Direction.from3DDataValue(tag.getInt("direction"));
        this.progressO = this.progress = tag.getFloat("progress");
        this.goalOrientation = SelectorOrientation.valueOf(tag.getString("goal"));
    }

    @Override
    public CompoundTag getUpdateTag() {
        return this.saveWithoutMetadata();
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
            float f = this.progress + 0.5f;
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
        return "" + this.cycledPos + "," + this.rotatedPositions + "," + this.direction + "," + this.goalOrientation;
    }

}
