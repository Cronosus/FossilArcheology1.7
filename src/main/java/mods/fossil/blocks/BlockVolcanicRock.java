package mods.fossil.blocks;

import mods.fossil.Fossil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class BlockVolcanicRock extends Block
{
    public BlockVolcanicRock()
    {
        super(Material.rock);
    }

    @Override
    public void registerBlockIcons(IIconRegister par1IconRegister)
    {
        this.blockIcon = par1IconRegister.registerIcon("fossil:Volcanic_Rock");
    }

    public void updateTick(World var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8)
    {
        if (var1.getBlock(var2 + var6, var3 + var7, var4 + var8) == Blocks.stone)
        {
            var1.setBlock(var2 + var6, var3 + var7, var4 + var8, Fossil.volcanicAsh);
        }
    }
}