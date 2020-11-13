package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.io.File;
import java.util.logging.Logger;

import net.minecraft.clothutils.GameruleManager;
import net.minecraft.server.MinecraftServer;

public class NetServerHandler extends NetHandler
    implements ICommandListener
{

    public NetServerHandler(MinecraftServer minecraftserver, NetworkManager networkmanager, EntityPlayerMP entityplayermp)
    {
        field_18_c = false;
        field_15_f = 0;
        field_9006_j = true;
        field_10_k = null;
        mcServer = minecraftserver;
        netManager = networkmanager;
        networkmanager.setNetHandler(this);
        playerEntity = entityplayermp;
        entityplayermp.field_421_a = this;
    }

    public void func_42_a()
    {
        netManager.processReadPackets();
        if(field_15_f++ % 20 == 0)
        {
            netManager.addToSendQueue(new Packet0KeepAlive());
        }
    }

    public void func_43_c(String s)
    {
        netManager.addToSendQueue(new Packet255KickDisconnect(s));
        netManager.serverShutdown();
        mcServer.configManager.sendPacketToAllPlayers(new Packet3Chat((new StringBuilder()).append("\247e").append(playerEntity.username).append(" left the game.").toString()));
        mcServer.configManager.playerLoggedOut(playerEntity);
        field_18_c = true;
    }

    public void handleFlying(Packet10Flying packet10flying)
    {
        if(!field_9006_j)
        {
            double d = packet10flying.yPosition - field_9008_h;
            if(packet10flying.xPosition == field_9009_g && d * d < 0.01D && packet10flying.zPosition == field_9007_i)
            {
                field_9006_j = true;
            }
        }
        if(field_9006_j)
        {
            if(playerEntity.field_327_g != null)
            {
                float f = playerEntity.rotationYaw;
                float f1 = playerEntity.rotationPitch;
                playerEntity.field_327_g.func_127_w();
                double d2 = playerEntity.posX;
                double d4 = playerEntity.posY;
                double d6 = playerEntity.posZ;
                double d8 = 0.0D;
                double d9 = 0.0D;
                if(packet10flying.rotating)
                {
                    f = packet10flying.yaw;
                    f1 = packet10flying.pitch;
                }
                if(packet10flying.moving && packet10flying.yPosition == -999D && packet10flying.stance == -999D)
                {
                    d8 = packet10flying.xPosition;
                    d9 = packet10flying.zPosition;
                }
                playerEntity.onGround = packet10flying.onGround;
                playerEntity.func_175_i();
                playerEntity.moveEntity(d8, 0.0D, d9);
                playerEntity.setPositionAndRotation(d2, d4, d6, f, f1);
                playerEntity.motionX = d8;
                playerEntity.motionZ = d9;
                if(playerEntity.field_327_g != null)
                {
                    mcServer.worldMngr.func_12017_b(playerEntity.field_327_g, true);
                }
                if(playerEntity.field_327_g != null)
                {
                    playerEntity.field_327_g.func_127_w();
                }
                mcServer.configManager.func_613_b(playerEntity);
                field_9009_g = playerEntity.posX;
                field_9008_h = playerEntity.posY;
                field_9007_i = playerEntity.posZ;
                mcServer.worldMngr.func_520_e(playerEntity);
                return;
            }
            double d1 = playerEntity.posY;
            field_9009_g = playerEntity.posX;
            field_9008_h = playerEntity.posY;
            field_9007_i = playerEntity.posZ;
            double d3 = playerEntity.posX;
            double d5 = playerEntity.posY;
            double d7 = playerEntity.posZ;
            float f2 = playerEntity.rotationYaw;
            float f3 = playerEntity.rotationPitch;
            if(packet10flying.moving && packet10flying.yPosition == -999D && packet10flying.stance == -999D)
            {
                packet10flying.moving = false;
            }
            if(packet10flying.moving)
            {
                d3 = packet10flying.xPosition;
                d5 = packet10flying.yPosition;
                d7 = packet10flying.zPosition;
                double d10 = packet10flying.stance - packet10flying.yPosition;
                if(d10 > 1.6499999999999999D || d10 < 0.10000000000000001D)
                {
                    func_43_c("Illegal stance");
                    logger.warning((new StringBuilder()).append(playerEntity.username).append(" had an illegal stance: ").append(d10).toString());
                }
                playerEntity.field_418_ai = packet10flying.stance;
            }
            if(packet10flying.rotating)
            {
                f2 = packet10flying.yaw;
                f3 = packet10flying.pitch;
            }
            playerEntity.func_175_i();
            playerEntity.field_9068_R = 0.0F;
            playerEntity.setPositionAndRotation(field_9009_g, field_9008_h, field_9007_i, f2, f3);
            double d11 = d3 - playerEntity.posX;
            double d12 = d5 - playerEntity.posY;
            double d13 = d7 - playerEntity.posZ;
            float f4 = 0.0625F;
            boolean flag = mcServer.worldMngr.getCollidingBoundingBoxes(playerEntity, playerEntity.boundingBox.copy().func_694_e(f4, f4, f4)).size() == 0;
            playerEntity.moveEntity(d11, d12, d13);
            d11 = d3 - playerEntity.posX;
            d12 = d5 - playerEntity.posY;
            if(d12 > -0.5D || d12 < 0.5D)
            {
                d12 = 0.0D;
            }
            d13 = d7 - playerEntity.posZ;
            double d14 = d11 * d11 + d12 * d12 + d13 * d13;
            boolean flag1 = false;
            if(d14 > 0.0625D)
            {
                flag1 = true;
                logger.warning((new StringBuilder()).append(playerEntity.username).append(" moved wrongly!").toString());
                System.out.println((new StringBuilder()).append("Got position ").append(d3).append(", ").append(d5).append(", ").append(d7).toString());
                System.out.println((new StringBuilder()).append("Expected ").append(playerEntity.posX).append(", ").append(playerEntity.posY).append(", ").append(playerEntity.posZ).toString());
            }
            playerEntity.setPositionAndRotation(d3, d5, d7, f2, f3);
            boolean flag2 = mcServer.worldMngr.getCollidingBoundingBoxes(playerEntity, playerEntity.boundingBox.copy().func_694_e(f4, f4, f4)).size() == 0;
            if(flag && (flag1 || !flag2))
            {
                func_41_a(field_9009_g, field_9008_h, field_9007_i, f2, f3);
                return;
            }
            playerEntity.onGround = packet10flying.onGround;
            mcServer.configManager.func_613_b(playerEntity);
            playerEntity.func_9153_b(playerEntity.posY - d1, packet10flying.onGround);
        }
    }

    public void func_41_a(double d, double d1, double d2, float f, 
            float f1)
    {
        field_9006_j = false;
        field_9009_g = d;
        field_9008_h = d1;
        field_9007_i = d2;
        playerEntity.setPositionAndRotation(d, d1, d2, f, f1);
        playerEntity.field_421_a.sendPacket(new Packet13PlayerLookMove(d, d1 + 1.6200000047683716D, d1, d2, f, f1, false));
    }

    public void handleBlockDig(Packet14BlockDig packet14blockdig)
    {
        playerEntity.inventory.mainInventory[playerEntity.inventory.currentItem] = field_10_k;
        boolean flag = mcServer.worldMngr.field_819_z = mcServer.configManager.isOp(playerEntity.username);
        boolean flag1 = false;
        if(packet14blockdig.status == 0)
        {
            flag1 = true;
        }
        if(packet14blockdig.status == 1)
        {
            flag1 = true;
        }
        int i = packet14blockdig.xPosition;
        int j = packet14blockdig.yPosition;
        int k = packet14blockdig.zPosition;
        if(flag1)
        {
            double d = playerEntity.posX - ((double)i + 0.5D);
            double d1 = playerEntity.posY - ((double)j + 0.5D);
            double d3 = playerEntity.posZ - ((double)k + 0.5D);
            double d5 = d * d + d1 * d1 + d3 * d3;
            if(d5 > 36D)
            {
                return;
            }
            double d7 = playerEntity.posY;
            playerEntity.posY = playerEntity.field_418_ai;
            playerEntity.posY = d7;
        }
        int l = packet14blockdig.face;
        int i1 = (int)MathHelper.abs(i - mcServer.worldMngr.spawnX);
        int j1 = (int)MathHelper.abs(k - mcServer.worldMngr.spawnZ);
        if(i1 > j1)
        {
            j1 = i1;
        }
        if(packet14blockdig.status == 0)
        {
            if(j1 > 16 || flag)
            {
                playerEntity.field_425_ad.func_324_a(i, j, k);
            }
        } else
        if(packet14blockdig.status == 2)
        {
            playerEntity.field_425_ad.func_328_a();
        } else
        if(packet14blockdig.status == 1)
        {
            if(j1 > 16 || flag)
            {
                playerEntity.field_425_ad.func_326_a(i, j, k, l);
            }
        } else
        if(packet14blockdig.status == 3)
        {
            double d2 = playerEntity.posX - ((double)i + 0.5D);
            double d4 = playerEntity.posY - ((double)j + 0.5D);
            double d6 = playerEntity.posZ - ((double)k + 0.5D);
            double d8 = d2 * d2 + d4 * d4 + d6 * d6;
            if(d8 < 256D)
            {
                playerEntity.field_421_a.sendPacket(new Packet53BlockChange(i, j, k, mcServer.worldMngr));
            }
        }
        mcServer.worldMngr.field_819_z = false;
    }

    public void handlePlace(Packet15Place packet15place)
    {
        boolean flag = mcServer.worldMngr.field_819_z = mcServer.configManager.isOp(playerEntity.username);
        if(packet15place.direction == 255)
        {
            ItemStack itemstack = packet15place.id < 0 ? null : new ItemStack(packet15place.id);
            playerEntity.field_425_ad.func_6154_a(playerEntity, mcServer.worldMngr, itemstack);
        } else
        {
            int i = packet15place.xPosition;
            int j = packet15place.yPosition;
            int k = packet15place.zPosition;
            int l = packet15place.direction;
            int i1 = (int)MathHelper.abs(i - mcServer.worldMngr.spawnX);
            int j1 = (int)MathHelper.abs(k - mcServer.worldMngr.spawnZ);
            if(i1 > j1)
            {
                j1 = i1;
            }
            if(j1 > 16 || flag)
            {
                ItemStack itemstack1 = packet15place.id < 0 ? null : new ItemStack(packet15place.id);
                playerEntity.field_425_ad.func_327_a(playerEntity, mcServer.worldMngr, itemstack1, i, j, k, l);
            }
            playerEntity.field_421_a.sendPacket(new Packet53BlockChange(i, j, k, mcServer.worldMngr));
            if(l == 0)
            {
                j--;
            }
            if(l == 1)
            {
                j++;
            }
            if(l == 2)
            {
                k--;
            }
            if(l == 3)
            {
                k++;
            }
            if(l == 4)
            {
                i--;
            }
            if(l == 5)
            {
                i++;
            }
            playerEntity.field_421_a.sendPacket(new Packet53BlockChange(i, j, k, mcServer.worldMngr));
        }
        mcServer.worldMngr.field_819_z = false;
    }

    public void handleErrorMessage(String s)
    {
        logger.info((new StringBuilder()).append(playerEntity.username).append(" lost connection: ").append(s).toString());
        mcServer.configManager.sendPacketToAllPlayers(new Packet3Chat((new StringBuilder()).append("\247e").append(playerEntity.username).append(" left the game.").toString()));
        mcServer.configManager.playerLoggedOut(playerEntity);
        field_18_c = true;
    }

    public void func_6001_a(Packet packet)
    {
        logger.warning((new StringBuilder()).append(getClass()).append(" wasn't prepared to deal with a ").append(packet.getClass()).toString());
        func_43_c("Protocol error, unexpected packet");
    }

    public void sendPacket(Packet packet)
    {
        netManager.addToSendQueue(packet);
    }

    public void handleBlockItemSwitch(Packet16BlockItemSwitch packet16blockitemswitch)
    {
        int i = packet16blockitemswitch.id;
        playerEntity.inventory.currentItem = playerEntity.inventory.mainInventory.length - 1;
        if(i == 0)
        {
            field_10_k = null;
        } else
        {
            field_10_k = new ItemStack(i);
        }
        playerEntity.inventory.mainInventory[playerEntity.inventory.currentItem] = field_10_k;
        mcServer.field_6028_k.func_12021_a(playerEntity, new Packet16BlockItemSwitch(playerEntity.field_331_c, i));
    }

    public void handlePickupSpawn(Packet21PickupSpawn packet21pickupspawn)
    {
        double d = (double)packet21pickupspawn.xPosition / 32D;
        double d1 = (double)packet21pickupspawn.yPosition / 32D;
        double d2 = (double)packet21pickupspawn.zPosition / 32D;
        EntityItem entityitem = new EntityItem(mcServer.worldMngr, d, d1, d2, new ItemStack(packet21pickupspawn.itemId, packet21pickupspawn.count));
        entityitem.motionX = (double)packet21pickupspawn.rotation / 128D;
        entityitem.motionY = (double)packet21pickupspawn.pitch / 128D;
        entityitem.motionZ = (double)packet21pickupspawn.roll / 128D;
        entityitem.field_433_ad = 10;
        mcServer.worldMngr.entityJoinedWorld(entityitem);
    }

    public void handleChat(Packet3Chat packet3chat)
    {
        String s = packet3chat.message;
        if(s.length() > 100)
        {
            func_43_c("Chat message too long");
            return;
        }
        s = s.trim();
        for(int i = 0; i < s.length(); i++)
        {
            if(" !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_'abcdefghijklmnopqrstuvwxyz{|}~\u2302\307\374\351\342\344\340\345\347\352\353\350\357\356\354\304\305\311\346\306\364\366\362\373\371\377\326\334\370\243\330\327\u0192\341\355\363\372\361\321\252\272\277\256\254\275\274\241\253\273".indexOf(s.charAt(i)) < 0)
            {
                func_43_c("Illegal characters in chat");
                return;
            }
        }

        if(s.startsWith("/"))
        {
            parseCommand(s);
        } else
        {
            s = (new StringBuilder()).append("<").append(playerEntity.username).append("> ").append(s).toString();
            logger.info(s);
            mcServer.configManager.sendPacketToAllPlayers(new Packet3Chat(s));
        }
    }

    private void parseCommand(String s)
    {
        //PREPROCESS
        s = s.toLowerCase().replace("@p", playerEntity.username);

        //END PREPROCESS

        if(s.toLowerCase().startsWith("/sleepvote ")){
            GameruleManager gameruleManager = new GameruleManager(new File("server.gamerules"));
            String[] Args = s.toLowerCase().split(" ");
            if(Args[1].equals("start") && !mcServer.IsSleepVoteOngoing && gameruleManager.getBooleanGamerule("dosleepvote", false)){
                //start a vote
                mcServer.IsSleepVoteOngoing = true;
               // mcServer.SleepVoteRemainingTime = 60000;
                mcServer.configManager.sendChatMessageToAllPlayers("§b"+playerEntity.username+" §bIs Starting a Sleep Vote! Vote with /sleepvote <yes/no>");
            }
            else if(Args[1].equals("yes") && mcServer.IsSleepVoteOngoing){
                mcServer.SleepVoteYesCount++;
                mcServer.configManager.sendChatMessageToAllPlayers(playerEntity.username+" votes yes for sleeping");
            }
            else if(Args[1].equals("no") && mcServer.IsSleepVoteOngoing){
                mcServer.SleepVoteNoCount++;
                mcServer.configManager.sendChatMessageToAllPlayers(playerEntity.username+" votes no for sleeping");
            }
        }
        if(s.toLowerCase().startsWith("/me "))
        {
            s = (new StringBuilder()).append("* ").append(playerEntity.username).append(" ").append(s.substring(s.indexOf(" ")).trim()).toString();
            logger.info(s);
            mcServer.configManager.sendPacketToAllPlayers(new Packet3Chat(s));
        } else
        if(s.toLowerCase().startsWith("/kill"))
        {
            playerEntity.attackEntity(null, 1000);
        } else
        if(s.toLowerCase().startsWith("/tell "))
        {
            String as[] = s.split(" ");
            if(as.length >= 3)
            {
                s = s.substring(s.indexOf(" ")).trim();
                s = s.substring(s.indexOf(" ")).trim();
                s = (new StringBuilder()).append("\2477").append(playerEntity.username).append(" whispers ").append(s).toString();
                logger.info((new StringBuilder()).append(s).append(" to ").append(as[1]).toString());
                if(!mcServer.configManager.sendPacketToPlayer(as[1], new Packet3Chat(s)))
                {
                    sendPacket(new Packet3Chat("\247cThere's no player by that name online."));
                }
            }
        } else
        if(mcServer.configManager.isOp(playerEntity.username))
        {
            String s1 = s.substring(1);
            logger.info((new StringBuilder()).append(playerEntity.username).append(" issued server command: ").append(s1).toString());
            mcServer.addCommand(s1, this);
        } else
        {
            String s2 = s.substring(1);
            logger.info((new StringBuilder()).append(playerEntity.username).append(" tried command: ").append(s2).toString());
        }
    }

    public void handleArmAnimation(Packet18ArmAnimation packet18armanimation)
    {
        if(packet18armanimation.animate == 1)
        {
            playerEntity.func_168_z();
        } else
        if(packet18armanimation.animate == 104)
        {
            playerEntity.field_12012_al = true;
        } else
        if(packet18armanimation.animate == 105)
        {
            playerEntity.field_12012_al = false;
        }
    }

    public void handleKickDisconnect(Packet255KickDisconnect packet255kickdisconnect)
    {
        netManager.networkShutdown("Quitting");
    }

    public int func_38_b()
    {
        return netManager.getNumChunkDataPackets();
    }

    public void log(String s)
    {
        sendPacket(new Packet3Chat((new StringBuilder()).append("\2477").append(s).toString()));
    }

    public String getUsername()
    {
        return playerEntity.username;
    }

    public void handlePlayerInventory(Packet5PlayerInventory packet5playerinventory)
    {
        if(packet5playerinventory.type == -1)
        {
            playerEntity.inventory.mainInventory = packet5playerinventory.stacks;
        }
        if(packet5playerinventory.type == -2)
        {
            playerEntity.inventory.craftingInventory = packet5playerinventory.stacks;
        }
        if(packet5playerinventory.type == -3)
        {
            playerEntity.inventory.armorInventory = packet5playerinventory.stacks;
        }
    }

    public void func_40_d()
    {
        netManager.addToSendQueue(new Packet5PlayerInventory(-1, playerEntity.inventory.mainInventory));
        netManager.addToSendQueue(new Packet5PlayerInventory(-2, playerEntity.inventory.craftingInventory));
        netManager.addToSendQueue(new Packet5PlayerInventory(-3, playerEntity.inventory.armorInventory));
    }

    public void handleComplexEntity(Packet59ComplexEntity packet59complexentity)
    {
        if(packet59complexentity.entityNBT.getInteger("x") != packet59complexentity.xPosition)
        {
            return;
        }
        if(packet59complexentity.entityNBT.getInteger("y") != packet59complexentity.yPosition)
        {
            return;
        }
        if(packet59complexentity.entityNBT.getInteger("z") != packet59complexentity.zPosition)
        {
            return;
        }

        TileEntity tileentity = mcServer.worldMngr.getBlock(packet59complexentity.xPosition, packet59complexentity.yPosition, packet59complexentity.zPosition);
        if(tileentity != null)
        {
            try
            {
                tileentity.readFromNBT(packet59complexentity.entityNBT);
            }
            catch(Exception exception) { }
            tileentity.func_183_c();
        }
    }

    public void func_6006_a(Packet7 packet7)
    {
        Entity entity = mcServer.worldMngr.func_6158_a(packet7.field_9018_b);
        playerEntity.inventory.mainInventory[playerEntity.inventory.currentItem] = field_10_k;
        if(entity != null && playerEntity.func_145_g(entity))
        {
            if(packet7.field_9020_c == 0)
            {
                playerEntity.func_9145_g(entity);
            } else
            if(packet7.field_9020_c == 1)
            {
                playerEntity.func_9146_h(entity);
            }
        }
    }

    public void func_9002_a(Packet9 packet9)
    {
        if(playerEntity.health > 0)
        {
            return;
        } else
        {
            playerEntity = mcServer.configManager.func_9242_d(playerEntity);
            return;
        }
    }

    public static Logger logger = Logger.getLogger("Minecraft");
    public NetworkManager netManager;
    public boolean field_18_c;
    private MinecraftServer mcServer;
    private EntityPlayerMP playerEntity;
    private int field_15_f;
    private double field_9009_g;
    private double field_9008_h;
    private double field_9007_i;
    private boolean field_9006_j;
    private ItemStack field_10_k;

}
