package io.github.codetoil.redstoneelectronic.tileentities;

import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public class MotorTileEntity extends TileEntity implements ITickableTileEntity {
    private BlockPos posOfFinalBlock;
    private int rotationalVelocity;

    public MotorTileEntity() {
        super(RETileEntityTypes.MOTOR_TYLE_ENTITY_TYPE.get()); // temporary
    }

    @Override
    public void tick() {
        // TODO Auto-generated method stub
        
    }
    
}
