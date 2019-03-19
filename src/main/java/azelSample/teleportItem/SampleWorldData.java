package azelSample.teleportItem;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.storage.WorldSavedData;

public class SampleWorldData extends WorldSavedData{

	public double teleX;
	public double teleY;
	public double teleZ;
	public int teleDim = 0;
	public int teleEnable = 0;

	public SampleWorldData(String name) {
		super(name);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {

		if(nbt == null)
		{
			nbt = new NBTTagCompound();
		}
		// TODO 自動生成されたメソッド・スタブ
		this.teleX = nbt.getDouble("teleX");
		this.teleY = nbt.getDouble("teleY");
		this.teleZ = nbt.getDouble("teleZ");
		this.teleDim = nbt.getInteger("teleDim");
		this.teleEnable = nbt.getInteger("teleEnable");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {

		if(compound == null)
		{
			compound = new NBTTagCompound();
		}
		// TODO 自動生成されたメソッド・スタブ
		compound.setDouble("teleX", this.teleX);
		compound.setDouble("teleY", this.teleY);
		compound.setDouble("teleZ", this.teleZ);
		compound.setInteger("teleDim", this.teleDim);
		compound.setInteger("teleEnable", this.teleEnable);

		return compound;
	}

	public void setTeleLocation(double parX, double parY, double parZ, int dim)
	{
		this.teleX = parX;
		this.teleY = parY;
		this.teleZ = parZ;
		this.teleDim = dim;
		this.teleEnable = 1;
	}

	public double[] getTeleLocation()
	{
		return new double[] {this.teleX, this.teleY, this.teleZ, this.teleDim};
	}
}
