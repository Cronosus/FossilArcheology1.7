package mods.fossil.guiBlocks;

import java.util.Random;

import mods.fossil.Fossil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockWorktable extends BlockContainer
{
    private Random furnaceRand = new Random();
    private final boolean isActive;
    private static boolean keepFurnaceInventory = false;
    private IIcon Top;
    private IIcon Bottom;
    private IIcon Side1;

    public BlockWorktable(boolean var2)
    {
        super(Material.rock);
        this.isActive = var2;
        //this.blockIndexInTexture = 45;
    }

    @SideOnly(Side.CLIENT)
    public Item getItem(World world, int x, int y, int z)
    {
        return Item.getItemFromBlock(Fossil.blockworktableIdle);
    }
    
    /**
     * Returns the ID of the items to drop on destruction.
     */
    @Override
    public Item getItemDropped(int var1, Random var2, int var3)
    {
        return Item.getItemFromBlock(Fossil.blockworktableIdle);
    }

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onBlockAdded(World world, int x, int y, int z)
    {
        super.onBlockAdded(world, x, y, z);
        this.setDefaultDirection(world, x, y, z);
    }

    /**
     * set a blocks direction
     */
    private void setDefaultDirection(World world, int x, int y, int z)
    {
        if (!world.isRemote)
        {
        	Block block = world.getBlock(x, y, z - 1);
            Block block1 = world.getBlock(x, y, z + 1);
            Block block2 = world.getBlock(x - 1, y, z);
            Block block3 = world.getBlock(x + 1, y, z);
            byte b0 = 3;

            if (block.func_149730_j() && !block1.func_149730_j())
            {
                b0 = 3;
            }

            if (block1.func_149730_j() && !block.func_149730_j())
            {
                b0 = 2;
            }

            if (block2.func_149730_j() && !block3.func_149730_j())
            {
                b0 = 5;
            }

            if (block3.func_149730_j() && !block2.func_149730_j())
            {
                b0 = 4;
            }

            world.setBlockMetadataWithNotify(x, y, z, b0, 2);
        }
    }

    @SideOnly(Side.CLIENT)

    /**
     * When this method is called, your block should register all the icons it needs with the given IconRegister. This
     * is the only chance you get to register icons.
     */
    public void registerBlockIcons(IIconRegister par1IconRegister)
    {
        this.blockIcon = par1IconRegister.registerIcon("fossil:Arch_Table_Side2");
        this.Side1 = this.isActive ? par1IconRegister.registerIcon("fossil:Arch_Table_Side1_Active") : par1IconRegister.registerIcon("fossil:Arch_Table_Side1");
        this.Top = this.isActive ? par1IconRegister.registerIcon("fossil:Arch_Table_Top_Active") : par1IconRegister.registerIcon("fossil:Arch_Table_Top_Idle");
        this.Bottom = par1IconRegister.registerIcon("fossil:Arch_Table_Bottom");
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public IIcon getIcon(int par1, int par2)
    {
        return par1 == 1 ? this.Top : (par1 == 0 ? this.Bottom : (par1 != par2 ? this.blockIcon : this.Side1));
    }

    /**
     * Called upon block activation (right click on the block.)
     */
    public boolean onBlockActivated(World var1, int var2, int var3, int var4, EntityPlayer var5, int var6, float var7, float var8, float var9)
    {
        if (var1.isRemote)
        {
            return true;
        }
        else
        {
            TileEntityWorktable tileentityworktable = (TileEntityWorktable)var1.getTileEntity(var2, var3, var4);

            if (tileentityworktable != null)
            {
                var5.openGui(Fossil.instance, 3, var1, var2, var3, var4);
            }

            return true;
        }
    }

    public static void updateFurnaceBlockState(boolean active, World world, int x, int y, int z)
    {
        int meta = world.getBlockMetadata(x, y, z);
        TileEntity tileentity = world.getTileEntity(x, y, z);
        keepFurnaceInventory = true;

        if (active)
        {
        	world.setBlock(x, y, z, Fossil.blockworktableActive);
        }
        else
        {
        	world.setBlock(x, y, z, Fossil.blockworktableIdle);
        }

        keepFurnaceInventory = false;
        world.setBlockMetadataWithNotify(x, y, z, meta, 2);

        if (tileentity != null)
        {
            tileentity.validate();
            world.setTileEntity(x, y, z, tileentity);
        }
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    public TileEntity createNewTileEntity(World world, int par2)
    {
        return new TileEntityWorktable();
    }

    /**
     * Called when the block is placed in the world.
     */
    public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack)
    {
        int l = MathHelper.floor_double((double)(par5EntityLivingBase.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

        if (l == 0)
        {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 2, 2);
        }

        if (l == 1)
        {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 5, 2);
        }

        if (l == 2)
        {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 3, 2);
        }

        if (l == 3)
        {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 4, 2);
        }

        if (par6ItemStack.hasDisplayName())
        {
            ((TileEntityWorktable)par1World.getTileEntity(par2, par3, par4)).setGuiDisplayName(par6ItemStack.getDisplayName());
        }
    }

    /**
     * ejects contained items into the world, and notifies neighbours of an update, as appropriate
     */
    public void breakBlock(World world, int x, int y, int z, Block block, int var6)
    {
        if (!keepFurnaceInventory)
        {
            TileEntityWorktable tileentity = (TileEntityWorktable)world.getTileEntity(x, y, z);

            if (tileentity != null)
            {
                for (int i = 0; i < tileentity.getSizeInventory(); ++i)
                {
                    ItemStack itemstack = tileentity.getStackInSlot(i);

                    if (itemstack != null)
                    {
                        float xOffset = this.furnaceRand.nextFloat() * 0.8F + 0.1F;
                        float yOffset = this.furnaceRand.nextFloat() * 0.8F + 0.1F;
                        float zOffset = this.furnaceRand.nextFloat() * 0.8F + 0.1F;

                        while (itemstack.stackSize > 0)
                        {
                            int rand = this.furnaceRand.nextInt(21) + 10;

                            if (rand > itemstack.stackSize)
                            {
                            	rand = itemstack.stackSize;
                            }

                            itemstack.stackSize -= rand;
                            EntityItem entityItem = new EntityItem(world, (double)((float)x + xOffset), (double)((float)y + yOffset), (double)((float)z + zOffset), new ItemStack(itemstack.getItem(), rand, itemstack.getItemDamage()));

                            if (itemstack.hasTagCompound())
                            {
                            	entityItem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
                            }

                            float offset = 0.05F;
                            entityItem.motionX = (double)((float)this.furnaceRand.nextGaussian() * offset);
                            entityItem.motionY = (double)((float)this.furnaceRand.nextGaussian() * offset + 0.2F);
                            entityItem.motionZ = (double)((float)this.furnaceRand.nextGaussian() * offset);
                            world.spawnEntityInWorld(entityItem);
                        }
                    }
                }
            }
        }

        super.breakBlock(world, x, y, z, block, var6);
    }
}
