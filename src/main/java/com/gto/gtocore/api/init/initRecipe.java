package com.gto.gtocore.api.init;

import net.minecraft.data.recipes.FinishedRecipe;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public interface InitRecipe {

    List<Consumer<Consumer<FinishedRecipe>>> RECIPE_CALLBACKS = new ArrayList<>();

    /**
     * 允许其他模组注册配方回调。
     */
    static void registerRecipeHandler(Consumer<Consumer<FinishedRecipe>> callback) {
        RECIPE_CALLBACKS.add(callback);
    }
}
