package com.gto.gtocore.mixin.emi;

import com.gto.gtocore.GTOCore;
import com.gto.gtocore.integration.emi.EMIManager;

import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.registry.EmiRecipes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;
import java.util.Map;

@Mixin(EmiRecipes.class)
public final class EmiRecipesMixin {

    @Shadow(remap = false)
    private static Map<EmiRecipeCategory, List<EmiIngredient>> workstations;

    @Shadow(remap = false)
    private static List<EmiRecipe> recipes;

    /**
     * @author .
     * @reason .
     */
    @Overwrite(remap = false)
    public static void bake() {
        long start = System.currentTimeMillis();
        EmiRecipes.manager = new EMIManager(EmiRecipes.categories, workstations, recipes);
        GTOCore.LOGGER.info("加载 {} 配方时间 {}ms", recipes.size(), System.currentTimeMillis() - start);
    }
}
