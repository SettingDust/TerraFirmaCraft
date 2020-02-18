/*
 * Work under Copyright. Licensed under the EUPL.
 * See the project README.md and LICENSE.txt for more information.
 */

package net.dries007.tfc.world.chunkdata;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunk;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import net.dries007.tfc.world.gen.rock.RockData;

public class ChunkData implements ICapabilitySerializable<CompoundNBT>
{
    /**
     * Helper method, since lazy optionals and instanceof checks together are ugly
     */
    public static LazyOptional<ChunkData> get(IChunk chunk)
    {
        if (chunk instanceof Chunk)
        {
            return ((Chunk) chunk).getCapability(ChunkDataCapability.CAPABILITY);
        }
        return LazyOptional.empty();
    }

    private final LazyOptional<ChunkData> capability = LazyOptional.of(() -> this);

    private RockData rockData;
    private float rainfall;
    private float regionalTemp;
    private float avgTemp;

    public ChunkData()
    {
        rockData = new RockData();
        rainfall = 250;
        regionalTemp = 0;
        avgTemp = 10;
    }

    @Nonnull
    public RockData getRockData()
    {
        return rockData;
    }

    public void setRockData(RockData rockData)
    {
        this.rockData = rockData;
    }

    public float getRainfall()
    {
        return rainfall;
    }

    public void setRainfall(float rainfall)
    {
        this.rainfall = rainfall;
    }

    public float getRegionalTemp()
    {
        return regionalTemp;
    }

    public void setRegionalTemp(float regionalTemp)
    {
        this.regionalTemp = regionalTemp;
    }

    public float getAvgTemp()
    {
        return avgTemp;
    }

    public void setAvgTemp(float avgTemp)
    {
        this.avgTemp = avgTemp;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side)
    {
        return cap == ChunkDataCapability.CAPABILITY ? capability.cast() : LazyOptional.empty();
    }

    @Override
    public CompoundNBT serializeNBT()
    {
        CompoundNBT nbt = new CompoundNBT();

        nbt.put("rockData", rockData.serializeNBT());
        nbt.putFloat("rainfall", rainfall);
        nbt.putFloat("regionalTemp", regionalTemp);
        nbt.putFloat("avgTemp", avgTemp);

        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt)
    {
        if (nbt != null)
        {
            rockData.deserializeNBT(nbt.getCompound("rockData"));
            rainfall = nbt.getFloat("rainfall");
            regionalTemp = nbt.getFloat("regionalTemp");
            avgTemp = nbt.getFloat("avgTemp");
        }
    }
}
