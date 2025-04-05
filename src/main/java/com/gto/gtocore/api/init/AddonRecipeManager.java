package com.gto.gtocore.api.init;

import net.minecraft.data.recipes.FinishedRecipe;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class AddonRecipeManager {

    private static final List<InitRecipe> PROVIDERS = new ArrayList<>();

    /**
     * 供其他模组注册配方提供者
     */
    public static void registerProvider(InitRecipe provider) {
        PROVIDERS.add(provider);
    }

    /**
     * 触发所有第三方配方注册
     */
    public static void gatherAddonRecipes(Consumer<FinishedRecipe> consumer) {
        PROVIDERS.forEach(provider -> provider.registerRecipes(consumer));
    }
}
