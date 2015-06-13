package com.github.revival.common.block;

import com.github.revival.common.creativetab.FATabRegistry;
import com.github.revival.common.handler.LocalizationStrings;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.Random;

public class BlockAncientWoodPillar extends Block
{
    @SideOnly(Side.CLIENT)
    private IIcon Top;

    public BlockAncientWoodPillar()
    {
        super(Material.wood);
        setCreativeTab(FATabRegistry.tabFBlocks);
        setHardness(2.0F);
        setBlockName(LocalizationStrings.ANCIENT_WOOD_PILLAR_NAME);
    }

    public static int limitToValidMetadata(int var0)
    {
        return var0 & 3;
    }

    @Override
    public void registerBlockIcons(IIconRegister par1IconRegister)
    {
        this.blockIcon = par1IconRegister.registerIcon("fossil:Ancient_Wood_Pillar"); //adding in a texture, 1.5.1 style!
        this.Top = par1IconRegister.registerIcon("fossil:Ancient_Wood_Pillar_Top");
    }

    // this sets the amount droped when broken.
    public int quantityDropped(Random par1Random)
    {
        return 1;
    }

    // this tells the game what to drop if the block is brocken with an explosion. an example of this would be creeper explosions
    // making stone drop cobblestone.
    public Item getItemDropped(int var1, Random var2, int var3)
    {
        return Item.getItemFromBlock(FABlockRegistry.palmLog);
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public IIcon getIcon(int var1, int var2)
    {
        return ((var2 & 12) == 0 && var1 < 2) || ((var2 & 12) == 8 && var1 > 1 && var1 < 4) || ((var2 & 12) == 4 && var1 > 3) ? this.Top : this.blockIcon;
    }

    /**
     * Called when a block is placed using its ItemBlock. Args: World, X, Y, Z, side, hitX, hitY, hitZ, block metadata
     */
    public int onBlockPlaced(World var1, int var2, int var3, int var4, int var5, float var6, float var7, float var8, int var9)
    {
        int var10 = var9 & 3;
        byte var11 = 0;

        switch (var5)
        {
            case 0:
            case 1:
                var11 = 0;
                break;

            case 2:
            case 3:
                var11 = 8;
                break;

            case 4:
            case 5:
                var11 = 4;
        }

        return var10 | var11;
    }

    public int damageDropped(int var1)
    {
        return var1 & 3;
    }

    /**
     * Returns an item stack containing a single instance of the current block type. 'i' is the block's subtype/damage
     * and is ignored for blocks which do not support subtypes. Blocks which cannot be harvested should return null.
     */
    protected ItemStack createStackedBlock(int var1)
    {
        return new ItemStack(this, 1, limitToValidMetadata(var1));
    }

    public boolean canSustainLeaves(World var1, int var2, int var3, int var4)
    {
        return true;
    }

    public boolean isWood(World var1, int var2, int var3, int var4)
    {
        return true;
    }
}
