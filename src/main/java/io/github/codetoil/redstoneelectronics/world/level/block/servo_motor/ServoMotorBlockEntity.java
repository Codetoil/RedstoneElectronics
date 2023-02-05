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
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.util.Mth;

import java.util.List;

import org.apache.commons.compress.utils.Lists;

import io.github.codetoil.redstoneelectronics.RedstoneElectronics;
import io.github.codetoil.redstoneelectronics.world.level.block.entity.REBlockEntityTypes;
import io.github.codetoil.redstoneelectronics.world.level.block.state.properties.SelectorOrientation;

public class ServoMotorBlockEntity extends BlockEntity {
    private BlockState cycledState;
    private final List<BlockState> rotatedStates = Lists.<BlockState>newArrayList(); 
    private Direction direction;
    private SelectorOrientation goalOrientation;
    @SuppressWarnings("unused")
    private float progress;
    private float progressO;
    private long lastTicked;

    public ServoMotorBlockEntity(BlockPos motorPos, BlockState motorState) {
        super(REBlockEntityTypes.SERVO_MOTOR_BLOCK_ENTITY_TYPE.get(), motorPos, motorState);
    }

    public ServoMotorBlockEntity(BlockPos motorPos, BlockState motorState, BlockState cycledState, List<BlockState> rotatedStates, Direction direction, SelectorOrientation goalOrientation) {
        this(motorPos, motorState);
        this.cycledState = cycledState;
        this.rotatedStates.addAll(rotatedStates);
        this.direction = direction;
        this.goalOrientation = goalOrientation;
    }

    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        if (this.cycledState == null)
        {
            RedstoneElectronics.logger.error("Invalid Servo Motor Block Entity, skipping...");
            tag.putInt("invalid", 0); // TODO Temporary
            return;
        }
        tag.put("blockState", NbtUtils.writeBlockState(this.cycledState));
        tag.putInt("direction", this.direction.get3DDataValue());
        tag.putFloat("progress", this.progressO);
        tag.putString("goal", this.goalOrientation.getSerializedName());
    }

    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains("invalid"))
        {
            RedstoneElectronics.logger.error("Invalid Servo Motor Block Entity, skipping...");
            return; // TODO Temporary
        }
        this.cycledState = NbtUtils.readBlockState(tag.getCompound("blockState"));
        this.direction = Direction.from3DDataValue(tag.getInt("direction"));
        this.progressO = this.progress = tag.getFloat("progress");
        this.goalOrientation = SelectorOrientation.valueOf(tag.getString("goal"));
    }

    @Override
    public CompoundTag getUpdateTag() {
        return new CompoundTag(); // TODO Create Update Tag
    }

    @SuppressWarnings("null")
    public void finalTick() {
        if (this.level != null && (this.progressO < 1.0f || this.level.isClientSide)) {
            this.progressO = this.progress = 1.0f;
            this.level.removeBlockEntity(this.worldPosition);
            this.setRemoved();
        }
    }

    public static void tick(Level level, BlockPos pos, BlockState state, ServoMotorBlockEntity blockEntity) {
        // TODO Create Tick Method
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

}
