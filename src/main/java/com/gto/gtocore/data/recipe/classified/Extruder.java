package com.gto.gtocore.data.recipe.classified;

import com.gto.gtocore.GTOCore;
import com.gto.gtocore.common.data.GTOItems;
import com.gto.gtocore.common.data.GTOMaterials;

import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.common.data.GTItems;

import static com.gto.gtocore.common.data.GTORecipeTypes.EXTRUDER_RECIPES;

interface Extruder {

    static void init() {
        EXTRUDER_RECIPES.recipeBuilder(GTOCore.id("special_ceramics"))
                .inputItems(TagPrefix.dust, GTOMaterials.SpecialCeramics, 2)
                .notConsumable(GTItems.SHAPE_EXTRUDER_PLATE.asItem())
                .outputItems(GTOItems.SPECIAL_CERAMICS.asItem())
                .EUt(480)
                .duration(20)
                .save();
    }
}
