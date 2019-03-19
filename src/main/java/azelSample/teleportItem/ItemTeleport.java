package azelSample.teleportItem;

import azelSample.AzelSamples;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class ItemTeleport extends Item{

	public ItemTeleport()
	{
		super();
		this.setCreativeTab(CreativeTabs.MISC);// 追加するクリエイティブタブ
		this.setMaxStackSize(16); // 最大のスタック数
	}


	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand hand)
	{
		ItemStack stack = player.getHeldItem(hand);

		if(!player.world.isRemote)
		{

			if(player.isSneaking())
			{
				//Sneakの場合 座標登録モード

				//SampleWorldDataを取得する
				SampleWorldData wd = (SampleWorldData)player.world.loadData(SampleWorldData.class, AzelSamples.MODID);

				if(wd == null)
				{
					//wdがnull(まだ1回も生成されていない)場合、生成する
					wd = new SampleWorldData(AzelSamples.MODID);
				}

				//wdの、座標登録メソッドをコール
				wd.setTeleLocation(player.posX, player.posY, player.posZ, player.dimension);

				//ワールドデータを保存
				wd.markDirty();
				player.world.setData(AzelSamples.MODID, wd);

				player.sendMessage(new TextComponentString("座標を登録しました : x["+ (int)player.posX +"] y["+ (int)player.posY +"] z["+ (int)player.posZ +"] dim[" +player.dimension + "]"));
			}else
			{
				//通常使用で、テレポート
				if(player.world.loadData(SampleWorldData.class, AzelSamples.MODID) == null)
				{
					player.sendMessage(new TextComponentString("座標が登録されていません"));
					return new ActionResult(EnumActionResult.SUCCESS, stack);
				}
				SampleWorldData wd = (SampleWorldData)player.world.loadData(SampleWorldData.class, AzelSamples.MODID);

				System.out.println("CASE1");
				if(wd != null)
				{
					System.out.println("CASE2");
					//wdがnull(まだ1回も生成されていない)場合、生成する
					double[] loc = wd.getTeleLocation();
					if(loc != null)
					{
						System.out.println("CASE3");
						double telX = loc[0];
						double telY = loc[1];
						double telZ = loc[2];
						int telDim = (int)loc[3];

						//player.sendMessage(new TextComponentString("登録されている座標 : x["+ (int)telX +"] y["+ (int)telY +"] z["+ (int)telZ +"] dim[" + telDim + "]"));
						//player.setLocationAndAngles(telX, telY, telZ, player.rotationYaw, player.rotationPitch);
						if(player.dimension != telDim)
						{
							System.out.println("CASE4");
							EntityPlayerMP entityPlayerMP=(EntityPlayerMP)player;
							int dim = player.getEntityWorld().provider.getDimension();
							MinecraftServer minecraftServer = entityPlayerMP.mcServer;
							WorldServer worldServer=minecraftServer.getWorld(dim);
							minecraftServer.getPlayerList().transferPlayerToDimension(entityPlayerMP, telDim, new KeyTeleporter(worldServer, telX, telY, telZ));
							player.sendMessage(new TextComponentString("x["+ (int)telX +"] y["+ (int)telY +"] z["+ (int)telZ +"] dim[" + telDim + "] にテレポートしました"));
						}
						else
						{
							System.out.println("CASE5");
							player.setPositionAndUpdate(telX, telY, telZ);
							player.sendMessage(new TextComponentString("x["+ (int)telX +"] y["+ (int)telY +"] z["+ (int)telZ +"] dim[" + telDim + "] にテレポートしました"));
						}
					}else
					{
						//入ってないケースはないと思うけど一応
						player.sendMessage(new TextComponentString("Error!"));
					}
				}else
				{
					player.sendMessage(new TextComponentString("座標が登録されていません"));
				}
			}


		}

		return new ActionResult(EnumActionResult.SUCCESS, stack);

	}
}
