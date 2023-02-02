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
import io.github.codetoil.redstoneelectronics.world.level.block.entity.REBlockEntityTypes;
import io.github.codetoil.redstoneelectronics.world.level.block.state.properties.SelectorOrientation;

public class ServoMotorBlockEntity extends BlockEntity {
    private BlockState rotatedState;
    private Direction direction;
    private SelectorOrientation goalOrientation;
    private float progress;
    private float progressO;
    private long lastTicked;

    public ServoMotorBlockEntity(BlockPos pos, BlockState state) {
        super(REBlockEntityTypes.SERVO_MOTOR_BLOCK_ENTITY_TYPE.get(), pos, state);
    }

    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("blockState", NbtUtils.writeBlockState(this.rotatedState));
        tag.putInt("direction", this.direction.get3DDataValue());
        tag.putFloat("progress", this.progressO);
        tag.putString("goal", this.goalOrientation.getSerializedName());
    }

    public void load(CompoundTag tag) {
        super.load(tag);
        this.rotatedState = NbtUtils.readBlockState(tag.getCompound("blockState"));
        this.direction = Direction.from3DDataValue(tag.getInt("direction"));
        this.progressO = this.progress = tag.getFloat("progress");
        this.goalOrientation = SelectorOrientation.valueOf(tag.getString("goal"));
    }

    @Override
    public CompoundTag getUpdateTag() {
        return new CompoundTag();
    }

    public void finalTick() {
        if (this.level != null && (this.progressO < 1.0f || this.level.isClientSide)) {
            this.progressO = this.progress = 1.0f;
            this.level.removeBlockEntity(this.worldPosition);
            this.setRemoved();
        }
    }

    public static void tick(Level level, BlockPos pos, BlockState state, ServoMotorBlockEntity blockEntity) {
        
    }

    public long getLastTicked() {
        return this.lastTicked;
    }

}
