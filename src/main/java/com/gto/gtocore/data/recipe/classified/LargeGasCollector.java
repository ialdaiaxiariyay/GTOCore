package com.gto.gtocore.data.recipe.classified;

import com.gto.gtocore.GTOCore;
import com.gto.gtocore.api.data.GTODimensions;
import com.gto.gtocore.common.data.GTOItems;

import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.gregtechceu.gtceu.common.data.machines.GTMultiMachines;

import static com.gto.gtocore.common.data.GTORecipeTypes.LARGE_GAS_COLLECTOR_RECIPES;

interface LargeGasCollector {

    static void init() {
        LARGE_GAS_COLLECTOR_RECIPES.recipeBuilder(GTOCore.id("1"))
                .notConsumable(GTOItems.DIMENSION_DATA.get().getDimensionData(GTODimensions.OVERWORLD))
                .circuitMeta(1)
                .outputFluids(GTMaterials.Air.getFluid(100000))
                .EUt(120)
                .duration(200)
                .save();

        LARGE_GAS_COLLECTOR_RECIPES.recipeBuilder(GTOCore.id("3"))
                .notConsumable(GTOItems.DIMENSION_DATA.get().getDimensionData(GTODimensions.THE_END))
                .circuitMeta(1)
                .outputFluids(GTMaterials.EnderAir.getFluid(100000))
                .EUt(1920)
                .duration(200)
                .save();

        LARGE_GAS_COLLECTOR_RECIPES.recipeBuilder(GTOCore.id("2"))
                .notConsumable(GTOItems.DIMENSION_DATA.get().getDimensionData(GTODimensions.THE_NETHER))
                .circuitMeta(1)
                .outputFluids(GTMaterials.NetherAir.getFluid(100000))
                .EUt(480)
                .duration(200)
                .save();

        LARGE_GAS_COLLECTOR_RECIPES.recipeBuilder(GTOCore.id("5"))
                .notConsumable(GTOItems.DIMENSION_DATA.get().getDimensionData(GTODimensions.THE_NETHER))
                .notConsumable(GTMultiMachines.VACUUM_FREEZER.getItem())
                .outputFluids(GTMaterials.LiquidNetherAir.getFluid(100000))
                .EUt(1920)
                .duration(2000)
                .save();

        LARGE_GAS_COLLECTOR_RECIPES.recipeBuilder(GTOCore.id("4"))
                .notConsumable(GTOItems.DIMENSION_DATA.get().getDimensionData(GTODimensions.OVERWORLD))
                .notConsumable(GTMultiMachines.VACUUM_FREEZER.getItem())
                .outputFluids(GTMaterials.LiquidAir.getFluid(100000))
                .EUt(480)
                .duration(2000)
                .save();

        LARGE_GAS_COLLECTOR_RECIPES.recipeBuilder(GTOCore.id("6"))
                .notConsumable(GTOItems.DIMENSION_DATA.get().getDimensionData(GTODimensions.THE_END))
                .notConsumable(GTMultiMachines.VACUUM_FREEZER.getItem())
                .outputFluids(GTMaterials.LiquidEnderAir.getFluid(100000))
                .EUt(7680)
                .duration(2000)
                .save();
    }
}
