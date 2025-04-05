package com.gto.gtocore.api.init;

import com.gto.gtocore.GTOCore;

import com.gregtechceu.gtceu.data.pack.GTDynamicDataPack;

import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

public interface InitRecipe {

    /**
     * 供其他模组注册配方到GT动态数据包
     * 
     * @param consumer 配方消费者（调用accept方法添加配方）
     */
    void registerRecipes(Consumer<FinishedRecipe> consumer);

    static void init() {
        long time = System.currentTimeMillis();
        Consumer<FinishedRecipe> consumer = GTDynamicDataPack::addRecipe;
        AddonRecipeManager.gatherAddonRecipes(consumer);
        GTOCore.LOGGER.info("配方初始化时间 {}ms", System.currentTimeMillis() - time);
    }
}
