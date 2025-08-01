package com.gtocore.data.recipe.classified;

import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.common.data.GTMaterials;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import static com.gtocore.common.data.GTORecipeTypes.FISHING_GROUND_RECIPES;

final class FishingGround {

    public static void init() {
        FISHING_GROUND_RECIPES.recipeBuilder("fishing_ground4")
                .notConsumable(new ItemStack(Items.PUFFERFISH.asItem(), 64))
                .inputItems(TagPrefix.dustTiny, GTMaterials.Meat, 64)
                .outputItems(new ItemStack(Items.PUFFERFISH.asItem(), 32))
                .EUt(1)
                .duration(2000)
                .save();

        FISHING_GROUND_RECIPES.recipeBuilder("fishing_ground3")
                .notConsumable(new ItemStack(Items.TROPICAL_FISH.asItem(), 64))
                .inputItems(TagPrefix.dustTiny, GTMaterials.Meat, 64)
                .outputItems(new ItemStack(Items.TROPICAL_FISH.asItem(), 32))
                .EUt(1)
                .duration(2000)
                .save();

        FISHING_GROUND_RECIPES.recipeBuilder("fishing_ground2")
                .notConsumable(new ItemStack(Items.SALMON.asItem(), 64))
                .inputItems(TagPrefix.dustTiny, GTMaterials.Meat, 64)
                .outputItems(new ItemStack(Items.SALMON.asItem(), 32))
                .EUt(1)
                .duration(2000)
                .save();

        FISHING_GROUND_RECIPES.recipeBuilder("fishing_ground1")
                .notConsumable(new ItemStack(Items.COD.asItem(), 64))
                .inputItems(TagPrefix.dustTiny, GTMaterials.Meat, 64)
                .outputItems(new ItemStack(Items.COD.asItem(), 32))
                .EUt(1)
                .duration(2000)
                .save();
    }
}
