package io.github.darealturtywurty.tutorialmod.common.block.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import io.github.darealturtywurty.tutorialmod.common.entity.SittableEntity;
import io.github.darealturtywurty.tutorialmod.core.init.BlockEntityInit;
import io.github.darealturtywurty.tutorialmod.core.init.PacketHandler;
import io.github.darealturtywurty.tutorialmod.core.init.SoundInit;
import io.github.darealturtywurty.tutorialmod.core.network.ClientboundUpdateToiletPacket;
import io.github.darealturtywurty.tutorialmod.core.network.ServerboundToiletUpdatePacket;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.PacketDistributor;

public class ToiletBlockEntity extends BlockEntity {
    public static final int FART_TIME = 40;
    public SittableEntity seat;
    public int ticks = 0, fartTicker = 0;
    public final Map<UUID, Integer> playerUses = new HashMap<>();
    public boolean isShitting;

    public ToiletBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityInit.TOILET.get(), pos, state);
    }

    public SittableEntity getOrCreateSeat() {
        if (this.seat == null) {
            final var seat = new SittableEntity(this.level);
            seat.absMoveTo(this.worldPosition.getX() + 0.5D, this.worldPosition.getY(),
                    this.worldPosition.getZ() + 0.5D,
                    getBlockState().getValue(HorizontalDirectionalBlock.FACING).toYRot(), 0.0f);
            this.level.addFreshEntity(seat);
            this.seat = seat;
        }

        return this.seat;
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return serializeNBT();
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        load(tag);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);

        final ListTag playerUses = nbt.getList("PlayerUseMap", Tag.TAG_COMPOUND);
        playerUses.forEach(player -> {
            if (player instanceof final CompoundTag tag) {
                final UUID uuid = tag.getUUID("UUID");
                final int uses = tag.getInt("Uses");
                this.playerUses.put(uuid, uses);
            }
        });
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        load(pkt.getTag());
    }

    @Override
    public void onLoad() {
        super.onLoad();
        getOrCreateSeat();
    }

    @Override
    public void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);

        final var playerUses = new ListTag();
        this.playerUses.forEach((uuid, uses) -> {
            final var playerTag = new CompoundTag();
            playerTag.putUUID("UUID", uuid);
            playerTag.putInt("Uses", uses);
            playerUses.add(playerTag);
        });

        nbt.put("PlayerUseMap", playerUses);
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        if (this.seat != null) {
            this.seat.kill();
        }
    }

    public void setShitting() {
        if (!this.isShitting) {
            this.isShitting = true;
            this.fartTicker = 0;
            PacketHandler.INSTANCE.sendToServer(new ServerboundToiletUpdatePacket(this.worldPosition));
        }
    }

    public void tick() {
        if (this.isShitting) {
            if (this.fartTicker <= 0) {
                this.level.playSound((Player) null, this.worldPosition, SoundInit.FART.get(), SoundSource.BLOCKS, 1.0f,
                        1.0f);
            }

            if (++this.fartTicker >= FART_TIME) {
                this.isShitting = false;
                this.fartTicker = 0;
                this.seat.ejectPassengers();
                PacketHandler.INSTANCE.send(
                        PacketDistributor.TRACKING_CHUNK.with(() -> this.level.getChunkAt(this.worldPosition)),
                        new ClientboundUpdateToiletPacket(this.worldPosition));
            }
        }

        if (this.ticks % 5 == 0 && (this.seat == null || this.seat.isRemoved())) {
            if (this.seat != null) {
                this.seat.kill();
                this.seat = null;
            }
            getOrCreateSeat();
        }
        this.ticks++;
    }
}
