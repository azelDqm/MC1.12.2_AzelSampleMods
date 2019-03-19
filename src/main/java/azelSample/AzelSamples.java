package azelSample;

import org.apache.logging.log4j.Logger;

import azelSample.teleportItem.ItemTeleport;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

@Mod(modid = AzelSamples.MODID, name = AzelSamples.NAME, version = AzelSamples.VERSION)
public class AzelSamples
{
    public static final String MODID = "azelsamples";
    public static final String NAME = "Azel sample Mod";
    public static final String VERSION = "1.0";

    private static Logger logger;

    public static Item itemTeleport;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();

        //テレポートアイテム登録
        itemTeleport = new ItemTeleport().setUnlocalizedName("itemTeleport").setRegistryName(
				new ResourceLocation(MODID, "itemTeleport"));

        ForgeRegistries.ITEMS.register(itemTeleport);
        ///////////////////////////////////////////
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        // some example code
        logger.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }
}
