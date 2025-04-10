package com.gto.gtocore.mixin.gtm.machine;

import com.gto.gtocore.api.machine.feature.IPerformanceDisplayMachine;
import com.gto.gtocore.common.machine.noenergy.PerformanceMonitorMachine;

import com.gregtechceu.gtceu.api.block.BlockProperties;
import com.gregtechceu.gtceu.api.item.tool.GTToolType;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.TickableSubscription;
import com.gregtechceu.gtceu.integration.ae2.machine.feature.IGridConnectedMachine;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import com.mojang.datafixers.util.Pair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

@Mixin(MetaMachine.class)
public abstract class MetaMachineMixin implements IPerformanceDisplayMachine {

    @Unique
    private long gTOCore$lastExecutionTime;

    @Unique
    private int gTOCore$averageTickTime;

    @Unique
    private long gTOCore$totaTtickCount;

    @Unique
    private boolean gtocore$observe;

    @Shadow(remap = false)
    public abstract boolean isRemote();

    @Shadow(remap = false)
    @Final
    public IMachineBlockEntity holder;

    @Shadow(remap = false)
    protected abstract void executeTick();

    @Shadow(remap = false)
    public abstract @Nullable Level getLevel();

    @Shadow(remap = false)
    public abstract BlockPos getPos();

    @Shadow(remap = false)
    public abstract BlockState getBlockState();

    @Shadow(remap = false)
    public abstract long getOffsetTimer();

    @Shadow(remap = false)
    @Final
    private List<TickableSubscription> serverTicks;

    @Shadow(remap = false)
    @Final
    private List<TickableSubscription> waitingToAdd;

    @Shadow(remap = false)
    public abstract boolean isInValid();

    @Override
    public int gtocore$getTickTime() {
        return gTOCore$averageTickTime;
    }

    @Override
    public void gtocore$observe() {
        gtocore$observe = true;
    }

    @Override
    @SuppressWarnings("all")
    public void runTick() {
        executeTick();
    }

    /**
     * @author .
     * @reason .
     */
    @Overwrite(remap = false)
    public final void serverTick() {
        if (cancelTick()) return;
        long currentTime = System.currentTimeMillis();
        if (currentTime - gTOCore$lastExecutionTime < 40) {
            return;
        }
        gTOCore$lastExecutionTime = currentTime;
        boolean observe = PerformanceMonitorMachine.observe || gtocore$observe;
        if (observe) currentTime = System.nanoTime();
        runTick();
        if (observe) {
            gTOCore$totaTtickCount += System.nanoTime() - currentTime;
            if (getOffsetTimer() % 40 == 0) {
                gtocore$observe = false;
                gTOCore$averageTickTime = (int) (gTOCore$totaTtickCount / 40000);
                gTOCore$totaTtickCount = 0;
            }
            if (PerformanceMonitorMachine.observe) PerformanceMonitorMachine.PERFORMANCE_MAP.put((MetaMachine) (Object) this, gTOCore$averageTickTime);
        } else if (!keepTick() && serverTicks.isEmpty() && waitingToAdd.isEmpty() && !isInValid()) {
            gTOCore$averageTickTime = 0;
            gTOCore$totaTtickCount = 0;
            getLevel().setBlockAndUpdate(getPos(), getBlockState().setValue(BlockProperties.SERVER_TICK, false));
        }
    }

    @Inject(method = "onToolClick", at = @At("RETURN"), remap = false, cancellable = true)
    private void onToolClick(Set<@NotNull GTToolType> toolType, ItemStack itemStack, UseOnContext context, CallbackInfoReturnable<Pair<GTToolType, InteractionResult>> cir) {
        if (cir.getReturnValue().getSecond() == InteractionResult.PASS && toolType.contains(GTToolType.WIRE_CUTTER)) {
            Player player = context.getPlayer();
            if (player == null) return;
            if (holder.getMetaMachine() instanceof IGridConnectedMachine gridConnectedMachine) {
                cir.setReturnValue(Pair.of(GTToolType.WIRE_CUTTER, gtoCore$onWireCutterClick(player, context.getHand(), gridConnectedMachine)));
            }
        }
    }

    @Inject(method = "shouldRenderGrid", at = @At("HEAD"), remap = false, cancellable = true)
    private void shouldRenderGrid(Player player, BlockPos pos, BlockState state, ItemStack held, Set<GTToolType> toolTypes, CallbackInfoReturnable<Boolean> cir) {
        if (toolTypes.contains(GTToolType.WIRE_CUTTER)) {
            MetaMachine metaMachine = holder.getMetaMachine();
            if (metaMachine instanceof IGridConnectedMachine) cir.setReturnValue(true);
        }
    }

    @Unique
    private InteractionResult gtoCore$onWireCutterClick(Player playerIn, InteractionHand hand, IGridConnectedMachine machine) {
        playerIn.swing(hand);
        if (holder.self().getPersistentData().getBoolean("isAllFacing")) {
            machine.getMainNode().setExposedOnSides(EnumSet.of(((MetaMachine) machine).getFrontFacing()));
            if (isRemote()) {
                playerIn.displayClientMessage(Component.translatable("gtocore.me_front"), true);
            }
            holder.self().getPersistentData().putBoolean("isAllFacing", false);
        } else {
            machine.getMainNode().setExposedOnSides(EnumSet.allOf(Direction.class));
            if (isRemote()) {
                playerIn.displayClientMessage(Component.translatable("gtocore.me_any"), true);
            }
            holder.self().getPersistentData().putBoolean("isAllFacing", true);
        }
        return InteractionResult.CONSUME;
    }
}
