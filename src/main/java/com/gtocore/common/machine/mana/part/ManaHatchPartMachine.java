package com.gtocore.common.machine.mana.part;

import com.gtolib.api.GTOValues;
import com.gtolib.api.machine.ManaDistributorMachine;
import com.gtolib.api.machine.mana.feature.IManaMachine;
import com.gtolib.api.machine.mana.trait.NotifiableManaContainer;
import com.gtolib.utils.MathUtil;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.TickableSubscription;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IMultiController;
import com.gregtechceu.gtceu.api.machine.multiblock.part.TieredIOPartMachine;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.BlockHitResult;

import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder;
import org.jetbrains.annotations.NotNull;
import org.joml.Math;
import vazkii.botania.api.mana.ManaCollector;
import vazkii.botania.api.mana.ManaPool;
import vazkii.botania.api.mana.ManaReceiver;
import vazkii.botania.xplat.XplatAbstractions;

public class ManaHatchPartMachine extends TieredIOPartMachine implements IManaMachine {

    private static final ManagedFieldHolder MANAGED_FIELD_HOLDER = new ManagedFieldHolder(ManaHatchPartMachine.class, TieredIOPartMachine.MANAGED_FIELD_HOLDER);
    TickableSubscription tickSubs;
    @Persisted
    private final NotifiableManaContainer manaContainer;

    public ManaHatchPartMachine(IMachineBlockEntity holder, int tier, IO io, int rate) {
        super(holder, tier, io);
        manaContainer = createManaContainer(rate);
        manaContainer.setAcceptDistributor(io == IO.IN);
    }

    @Override
    @NotNull
    public ManagedFieldHolder getFieldHolder() {
        return MANAGED_FIELD_HOLDER;
    }

    NotifiableManaContainer createManaContainer(int rate) {
        int tierMana = GTOValues.MANA[tier] * rate;
        if (io == IO.OUT) {
            return new NotifiableManaContainer(this, IO.OUT, 256L * tierMana, 4L * tierMana);
        } else return new NotifiableManaContainer(this, IO.IN, 256L * tierMana, 4L * tierMana);
    }

    @Override
    public void addedToController(@NotNull IMultiController controller) {
        super.addedToController(controller);
        if (controller instanceof ManaDistributorMachine) {
            manaContainer.setAcceptDistributor(false);
        }
    }

    @Override
    public void onLoad() {
        super.onLoad();
        if (!isRemote() && io == IO.OUT) {
            tickSubs = subscribeServerTick(tickSubs, this::tickUpdate);
        }
    }

    @Override
    public void onUnload() {
        super.onUnload();
        if (tickSubs != null) {
            tickSubs.unsubscribe();
            tickSubs = null;
        }
    }

    void tickUpdate() {
        if (getOffsetTimer() % 20 != 0) return;
        ManaReceiver receiver = XplatAbstractions.INSTANCE.findManaReceiver(getLevel(), getPos().relative(getFrontFacing()), null);
        if (receiver != null && !receiver.isFull()) {
            int mana = MathUtil.saturatedCast(manaContainer.getCurrentMana());
            if (receiver instanceof ManaCollector collector) {
                mana = Math.min(mana, collector.getMaxMana() - collector.getCurrentMana());
            } else if (receiver instanceof ManaPool pool) {
                mana = Math.min(mana, pool.getMaxMana() - pool.getCurrentMana());
            }
            int change = MathUtil.saturatedCast(manaContainer.removeMana(mana, 20, false));
            if (change > 0) {
                receiver.receiveMana(change);
                manaContainer.notifyListeners();
            }
        }
    }

    @Override
    public boolean shouldOpenUI(Player player, InteractionHand hand, BlockHitResult hit) {
        return false;
    }

    @Override
    public int tintColor(int index) {
        if (index == 2) {
            return GTValues.VC[getTier()];
        }
        return super.tintColor(index);
    }

    @Override
    public boolean canReceiveManaFromBursts() {
        return io == IO.IN;
    }

    @Override
    public @NotNull NotifiableManaContainer getManaContainer() {
        return this.manaContainer;
    }
}
