package com.gto.gtocore.data.recipe.classified;

import com.gto.gtocore.GTOCore;
import com.gto.gtocore.api.data.tag.GTOTagPrefix;
import com.gto.gtocore.common.data.GTOMaterials;

import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.common.data.GTMaterials;

import static com.gto.gtocore.common.data.GTORecipeTypes.FLOTATING_BENEFICIATION_RECIPES;

interface FlotatingBeneficiation {

    static void init() {
        FLOTATING_BENEFICIATION_RECIPES.recipeBuilder(GTOCore.id("pyrope_front"))
                .inputItems(TagPrefix.dust, GTOMaterials.SodiumEthylxanthate, 32)
                .inputItems(GTOTagPrefix.MILLED, GTMaterials.Pyrope, 64)
                .inputFluids(GTOMaterials.Turpentine.getFluid(8000))
                .outputFluids(GTOMaterials.PyropeFront.getFluid(1000))
                .EUt(7680)
                .duration(4800)
                .save();

        FLOTATING_BENEFICIATION_RECIPES.recipeBuilder(GTOCore.id("redstone_front"))
                .inputItems(TagPrefix.dust, GTOMaterials.SodiumEthylxanthate, 32)
                .inputItems(GTOTagPrefix.MILLED, GTMaterials.Redstone, 64)
                .inputFluids(GTOMaterials.Turpentine.getFluid(13000))
                .outputFluids(GTOMaterials.RedstoneFront.getFluid(1000))
                .EUt(7680)
                .duration(4800)
                .save();

        FLOTATING_BENEFICIATION_RECIPES.recipeBuilder(GTOCore.id("chalcopyrite_front"))
                .inputItems(TagPrefix.dust, GTOMaterials.PotassiumEthylxanthate, 32)
                .inputItems(GTOTagPrefix.MILLED, GTMaterials.Chalcopyrite, 64)
                .inputFluids(GTOMaterials.Turpentine.getFluid(12000))
                .outputFluids(GTOMaterials.ChalcopyriteFront.getFluid(1000))
                .EUt(7680)
                .duration(4800)
                .save();

        FLOTATING_BENEFICIATION_RECIPES.recipeBuilder(GTOCore.id("monazite_front"))
                .inputItems(TagPrefix.dust, GTOMaterials.SodiumEthylxanthate, 32)
                .inputItems(GTOTagPrefix.MILLED, GTMaterials.Monazite, 64)
                .inputFluids(GTOMaterials.Turpentine.getFluid(30000))
                .outputFluids(GTOMaterials.MonaziteFront.getFluid(1000))
                .EUt(30720)
                .duration(4800)
                .save();

        FLOTATING_BENEFICIATION_RECIPES.recipeBuilder(GTOCore.id("enriched_naquadah_front"))
                .inputItems(TagPrefix.dust, GTOMaterials.PotassiumEthylxanthate, 64)
                .inputItems(GTOTagPrefix.MILLED, GTMaterials.NaquadahEnriched, 64)
                .inputFluids(GTOMaterials.Turpentine.getFluid(280000))
                .outputFluids(GTOMaterials.EnrichedNaquadahFront.getFluid(1000))
                .EUt(491520)
                .duration(2400)
                .save();

        FLOTATING_BENEFICIATION_RECIPES.recipeBuilder(GTOCore.id("grossular_front"))
                .inputItems(TagPrefix.dust, GTOMaterials.PotassiumEthylxanthate, 32)
                .inputItems(GTOTagPrefix.MILLED, GTMaterials.Grossular, 64)
                .inputFluids(GTOMaterials.Turpentine.getFluid(28000))
                .outputFluids(GTOMaterials.GrossularFront.getFluid(1000))
                .EUt(30720)
                .duration(4800)
                .save();

        FLOTATING_BENEFICIATION_RECIPES.recipeBuilder(GTOCore.id("nickel_front"))
                .inputItems(TagPrefix.dust, GTOMaterials.PotassiumEthylxanthate, 32)
                .inputItems(GTOTagPrefix.MILLED, GTMaterials.Nickel, 64)
                .inputFluids(GTOMaterials.Turpentine.getFluid(25000))
                .outputFluids(GTOMaterials.NickelFront.getFluid(1000))
                .EUt(7680)
                .duration(4800)
                .save();

        FLOTATING_BENEFICIATION_RECIPES.recipeBuilder(GTOCore.id("almandine_front"))
                .inputItems(TagPrefix.dust, GTOMaterials.SodiumEthylxanthate, 32)
                .inputItems(GTOTagPrefix.MILLED, GTMaterials.Almandine, 64)
                .inputFluids(GTOMaterials.Turpentine.getFluid(18000))
                .outputFluids(GTOMaterials.AlmandineFront.getFluid(1000))
                .EUt(7680)
                .duration(4800)
                .save();

        FLOTATING_BENEFICIATION_RECIPES.recipeBuilder(GTOCore.id("platinum_front"))
                .inputItems(TagPrefix.dust, GTOMaterials.SodiumEthylxanthate, 32)
                .inputItems(GTOTagPrefix.MILLED, GTMaterials.Platinum, 64)
                .inputFluids(GTOMaterials.Turpentine.getFluid(35000))
                .outputFluids(GTOMaterials.PlatinumFront.getFluid(1000))
                .EUt(30720)
                .duration(4800)
                .save();

        FLOTATING_BENEFICIATION_RECIPES.recipeBuilder(GTOCore.id("pentlandite_front"))
                .inputItems(TagPrefix.dust, GTOMaterials.PotassiumEthylxanthate, 32)
                .inputItems(GTOTagPrefix.MILLED, GTMaterials.Pentlandite, 64)
                .inputFluids(GTOMaterials.Turpentine.getFluid(14000))
                .outputFluids(GTOMaterials.PentlanditeFront.getFluid(1000))
                .EUt(30720)
                .duration(4800)
                .save();

        FLOTATING_BENEFICIATION_RECIPES.recipeBuilder(GTOCore.id("spessartine_front"))
                .inputItems(TagPrefix.dust, GTOMaterials.PotassiumEthylxanthate, 32)
                .inputItems(GTOTagPrefix.MILLED, GTMaterials.Spessartine, 64)
                .inputFluids(GTOMaterials.Turpentine.getFluid(35000))
                .outputFluids(GTOMaterials.SpessartineFront.getFluid(1000))
                .EUt(30720)
                .duration(4800)
                .save();

        FLOTATING_BENEFICIATION_RECIPES.recipeBuilder(GTOCore.id("sphalerite_front"))
                .inputItems(TagPrefix.dust, GTOMaterials.SodiumEthylxanthate, 32)
                .inputItems(GTOTagPrefix.MILLED, GTMaterials.Sphalerite, 64)
                .inputFluids(GTOMaterials.Turpentine.getFluid(14000))
                .outputFluids(GTOMaterials.SphaleriteFront.getFluid(1000))
                .EUt(30720)
                .duration(4800)
                .save();
    }
}
