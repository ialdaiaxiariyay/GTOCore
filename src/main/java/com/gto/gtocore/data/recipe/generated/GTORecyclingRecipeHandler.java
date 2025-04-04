package com.gto.gtocore.data.recipe.generated;

import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.PropertyKey;
import com.gregtechceu.gtceu.api.data.chemical.material.stack.MaterialStack;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.data.recipe.misc.RecyclingRecipes;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.ItemStack;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Set;
import java.util.function.Consumer;

import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.*;

interface GTORecyclingRecipeHandler {

    Set<TagPrefix> IGNORE_ARC_SMELTING = Set.of(ingot, gem, nugget);

    static void run(@NotNull Consumer<FinishedRecipe> provider, @NotNull Material material) {
        for (TagPrefix prefix : TagPrefix.values()) {
            if (prefix.generateRecycling()) {
                processCrushing(provider, prefix, material);
            }
        }
    }

    private static void processCrushing(@NotNull Consumer<FinishedRecipe> provider, @NotNull TagPrefix prefix, @NotNull Material material) {
        ItemStack stack = ChemicalHelper.get(prefix, material);
        if (stack.isEmpty()) return;
        ArrayList<MaterialStack> materialStacks = new ArrayList<>();
        materialStacks.add(new MaterialStack(material, prefix.getMaterialAmount(material)));
        materialStacks.addAll(prefix.secondaryMaterials());
        // only ignore arc smelting for blacklisted prefixes if yielded material is the same as input material
        // if arc smelting gives different material, allow it
        boolean ignoreArcSmelting = IGNORE_ARC_SMELTING.contains(prefix) &&
                !(material.hasProperty(PropertyKey.INGOT) &&
                        material.getProperty(PropertyKey.INGOT).getArcSmeltingInto() != material);
        RecyclingRecipes.registerRecyclingRecipes(provider, stack, materialStacks,
                ignoreArcSmelting, prefix);
    }
}
