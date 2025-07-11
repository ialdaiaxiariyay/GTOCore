package com.gtocore.data.recipe.classified;

import com.gtocore.common.data.GTOMaterials;

import com.gregtechceu.gtceu.api.fluids.store.FluidStorageKeys;
import com.gregtechceu.gtceu.common.data.GTMaterials;

import static com.gtocore.common.data.GTORecipeTypes.SUPER_PARTICLE_COLLIDER_RECIPES;

final class SuperParticleCollider {

    public static void init() {
        SUPER_PARTICLE_COLLIDER_RECIPES.recipeBuilder("antimatter")
                .inputFluids(GTOMaterials.Antihydrogen.getFluid(2000))
                .inputFluids(GTOMaterials.Antineutron.getFluid(2000))
                .outputFluids(GTOMaterials.Antimatter.getFluid(100))
                .EUt(491520)
                .duration(200)
                .save();

        SUPER_PARTICLE_COLLIDER_RECIPES.recipeBuilder("antineutron")
                .inputFluids(GTOMaterials.PositiveElectron.getFluid(100))
                .inputFluids(GTOMaterials.Antiproton.getFluid(100))
                .outputFluids(GTOMaterials.Antineutron.getFluid(2))
                .EUt(491520)
                .duration(200)
                .save();

        SUPER_PARTICLE_COLLIDER_RECIPES.recipeBuilder("roentgeniuma")
                .inputFluids(GTMaterials.Meitnerium.getFluid(4096))
                .inputFluids(GTMaterials.Helium.getFluid(FluidStorageKeys.PLASMA, 4096))
                .outputFluids(GTMaterials.Roentgenium.getFluid(4000))
                .EUt(491520)
                .duration(200)
                .save();

        SUPER_PARTICLE_COLLIDER_RECIPES.recipeBuilder("positive_electron")
                .inputFluids(GTMaterials.Phosphorus.getFluid(200))
                .inputFluids(GTMaterials.Lithium.getFluid(200))
                .outputFluids(GTOMaterials.PositiveElectron.getFluid(100))
                .EUt(491520)
                .duration(200)
                .save();

        SUPER_PARTICLE_COLLIDER_RECIPES.recipeBuilder("antiproton")
                .inputFluids(GTOMaterials.LiquidHydrogen.getFluid(1000))
                .inputFluids(GTMaterials.Helium.getFluid(FluidStorageKeys.PLASMA, 200))
                .outputFluids(GTOMaterials.Antiproton.getFluid(100))
                .EUt(491520)
                .duration(200)
                .save();

        SUPER_PARTICLE_COLLIDER_RECIPES.recipeBuilder("nihoniuma")
                .inputFluids(GTMaterials.Roentgenium.getFluid(4096))
                .inputFluids(GTMaterials.Helium.getFluid(FluidStorageKeys.PLASMA, 4096))
                .outputFluids(GTMaterials.Nihonium.getFluid(4000))
                .EUt(491520)
                .duration(200)
                .save();

        SUPER_PARTICLE_COLLIDER_RECIPES.recipeBuilder("einsteiniuma")
                .inputFluids(GTMaterials.Curium.getFluid(4096))
                .inputFluids(GTMaterials.Helium.getFluid(FluidStorageKeys.PLASMA, 4096))
                .outputFluids(GTMaterials.Einsteinium.getFluid(4000))
                .EUt(491520)
                .duration(200)
                .save();

        SUPER_PARTICLE_COLLIDER_RECIPES.recipeBuilder("nobeliuma")
                .inputFluids(GTMaterials.Fermium.getFluid(4096))
                .inputFluids(GTMaterials.Helium.getFluid(FluidStorageKeys.PLASMA, 4096))
                .outputFluids(GTMaterials.Nobelium.getFluid(4000))
                .EUt(491520)
                .duration(200)
                .save();

        SUPER_PARTICLE_COLLIDER_RECIPES.recipeBuilder("californiuma")
                .inputFluids(GTMaterials.Berkelium.getFluid(4096))
                .inputFluids(GTMaterials.Helium.getFluid(FluidStorageKeys.PLASMA, 4096))
                .outputFluids(GTMaterials.Californium.getFluid(4000))
                .EUt(491520)
                .duration(200)
                .save();

        SUPER_PARTICLE_COLLIDER_RECIPES.recipeBuilder("plutoniuma")
                .inputFluids(GTMaterials.Uranium238.getFluid(4096))
                .inputFluids(GTMaterials.Helium.getFluid(FluidStorageKeys.PLASMA, 4096))
                .outputFluids(GTMaterials.Plutonium239.getFluid(4000))
                .EUt(491520)
                .duration(200)
                .save();

        SUPER_PARTICLE_COLLIDER_RECIPES.recipeBuilder("berkeliuma")
                .inputFluids(GTMaterials.Americium.getFluid(4096))
                .inputFluids(GTMaterials.Helium.getFluid(FluidStorageKeys.PLASMA, 4096))
                .outputFluids(GTMaterials.Berkelium.getFluid(4000))
                .EUt(491520)
                .duration(200)
                .save();

        SUPER_PARTICLE_COLLIDER_RECIPES.recipeBuilder("curiuma")
                .inputFluids(GTMaterials.Plutonium239.getFluid(4096))
                .inputFluids(GTMaterials.Helium.getFluid(FluidStorageKeys.PLASMA, 4096))
                .outputFluids(GTMaterials.Curium.getFluid(4000))
                .EUt(491520)
                .duration(200)
                .save();

        SUPER_PARTICLE_COLLIDER_RECIPES.recipeBuilder("neptuniuma")
                .inputFluids(GTMaterials.Protactinium.getFluid(4096))
                .inputFluids(GTMaterials.Helium.getFluid(FluidStorageKeys.PLASMA, 4096))
                .outputFluids(GTMaterials.Neptunium.getFluid(4000))
                .EUt(491520)
                .duration(200)
                .save();

        SUPER_PARTICLE_COLLIDER_RECIPES.recipeBuilder("uraniuma")
                .inputFluids(GTMaterials.Thorium.getFluid(4096))
                .inputFluids(GTMaterials.Helium.getFluid(FluidStorageKeys.PLASMA, 4096))
                .outputFluids(GTMaterials.Uranium238.getFluid(4000))
                .EUt(491520)
                .duration(200)
                .save();

        SUPER_PARTICLE_COLLIDER_RECIPES.recipeBuilder("astatinea")
                .inputFluids(GTMaterials.Bismuth.getFluid(4096))
                .inputFluids(GTMaterials.Helium.getFluid(FluidStorageKeys.PLASMA, 4096))
                .outputFluids(GTMaterials.Astatine.getFluid(4000))
                .EUt(491520)
                .duration(200)
                .save();

        SUPER_PARTICLE_COLLIDER_RECIPES.recipeBuilder("lawrenciuma")
                .inputFluids(GTMaterials.Mendelevium.getFluid(4096))
                .inputFluids(GTMaterials.Helium.getFluid(FluidStorageKeys.PLASMA, 4096))
                .outputFluids(GTMaterials.Lawrencium.getFluid(4000))
                .EUt(491520)
                .duration(200)
                .save();

        SUPER_PARTICLE_COLLIDER_RECIPES.recipeBuilder("mendeleviuma")
                .inputFluids(GTMaterials.Einsteinium.getFluid(4096))
                .inputFluids(GTMaterials.Helium.getFluid(FluidStorageKeys.PLASMA, 4096))
                .outputFluids(GTMaterials.Mendelevium.getFluid(4000))
                .EUt(491520)
                .duration(200)
                .save();

        SUPER_PARTICLE_COLLIDER_RECIPES.recipeBuilder("coperniciuma")
                .inputFluids(GTMaterials.Darmstadtium.getFluid(4096))
                .inputFluids(GTMaterials.Helium.getFluid(FluidStorageKeys.PLASMA, 4096))
                .outputFluids(GTMaterials.Copernicium.getFluid(4000))
                .EUt(491520)
                .duration(200)
                .save();

        SUPER_PARTICLE_COLLIDER_RECIPES.recipeBuilder("bohriuma")
                .inputFluids(GTMaterials.Dubnium.getFluid(4096))
                .inputFluids(GTMaterials.Helium.getFluid(FluidStorageKeys.PLASMA, 4096))
                .outputFluids(GTMaterials.Bohrium.getFluid(4000))
                .EUt(491520)
                .duration(200)
                .save();

        SUPER_PARTICLE_COLLIDER_RECIPES.recipeBuilder("fermiuma")
                .inputFluids(GTMaterials.Californium.getFluid(4096))
                .inputFluids(GTMaterials.Helium.getFluid(FluidStorageKeys.PLASMA, 4096))
                .outputFluids(GTMaterials.Fermium.getFluid(4000))
                .EUt(491520)
                .duration(200)
                .save();
    }
}
