package com.gto.gtocore.data.recipe.classified;

import com.gto.gtocore.GTOCore;
import com.gto.gtocore.api.data.tag.GTOTagPrefix;
import com.gto.gtocore.common.data.GTOMaterials;

import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.common.data.GTMaterials;

import static com.gto.gtocore.common.data.GTORecipeTypes.ISOSTATIC_PRESSING_RECIPES;

interface IsostaticPressing {

    static void init() {
        ISOSTATIC_PRESSING_RECIPES.recipeBuilder(GTOCore.id("barium_titanate_ceramic_rough_blank"))
                .inputItems(TagPrefix.dust, GTOMaterials.BariumTitanateCeramic, 9)
                .inputFluids(GTMaterials.Epoxy.getFluid(1000))
                .outputItems(GTOTagPrefix.ROUGH_BLANK, GTOMaterials.BariumTitanateCeramic)
                .EUt(500)
                .duration(200)
                .save();

        ISOSTATIC_PRESSING_RECIPES.recipeBuilder(GTOCore.id("tungsten_tetraboride_ceramics_rough_blank"))
                .inputItems(TagPrefix.dust, GTOMaterials.TungstenTetraborideCeramics, 9)
                .inputFluids(GTMaterials.Nickel.getFluid(1000))
                .outputItems(GTOTagPrefix.ROUGH_BLANK, GTOMaterials.TungstenTetraborideCeramics)
                .EUt(500)
                .duration(200)
                .save();

        ISOSTATIC_PRESSING_RECIPES.recipeBuilder(GTOCore.id("silica_ceramic_rough_blank"))
                .inputItems(TagPrefix.dust, GTOMaterials.SilicaCeramic, 9)
                .inputFluids(GTMaterials.Epoxy.getFluid(1000))
                .outputItems(GTOTagPrefix.ROUGH_BLANK, GTOMaterials.SilicaCeramic)
                .EUt(500)
                .duration(200)
                .save();

        ISOSTATIC_PRESSING_RECIPES.recipeBuilder(GTOCore.id("hydroxyapatite_ceramic_rough_blank"))
                .inputItems(TagPrefix.dust, GTOMaterials.HydroxyapatiteCeramic, 9)
                .inputFluids(GTMaterials.Epoxy.getFluid(1000))
                .outputItems(GTOTagPrefix.ROUGH_BLANK, GTOMaterials.HydroxyapatiteCeramic)
                .EUt(500)
                .duration(200)
                .save();

        ISOSTATIC_PRESSING_RECIPES.recipeBuilder(GTOCore.id("tellurate_ceramics_rough_blank"))
                .inputItems(TagPrefix.dust, GTOMaterials.TellurateCeramics, 9)
                .inputFluids(GTMaterials.Epoxy.getFluid(1000))
                .outputItems(GTOTagPrefix.ROUGH_BLANK, GTOMaterials.TellurateCeramics)
                .EUt(500)
                .duration(200)
                .save();

        ISOSTATIC_PRESSING_RECIPES.recipeBuilder(GTOCore.id("thulium_hexaboride_ceramics_rough_blank"))
                .inputItems(TagPrefix.dust, GTOMaterials.ThuliumHexaborideCeramics, 9)
                .inputFluids(GTMaterials.Aluminium.getFluid(1000))
                .outputItems(GTOTagPrefix.ROUGH_BLANK, GTOMaterials.ThuliumHexaborideCeramics)
                .EUt(500)
                .duration(200)
                .save();

        ISOSTATIC_PRESSING_RECIPES.recipeBuilder(GTOCore.id("silicon_nitride_ceramic_rough_blank"))
                .inputItems(TagPrefix.dust, GTOMaterials.SiliconNitrideCeramic, 9)
                .inputFluids(GTOMaterials.PolyurethaneResin.getFluid(1000))
                .outputItems(GTOTagPrefix.ROUGH_BLANK, GTOMaterials.SiliconNitrideCeramic)
                .EUt(500)
                .duration(200)
                .save();

        ISOSTATIC_PRESSING_RECIPES.recipeBuilder(GTOCore.id("cobalt_oxide_ceramic_rough_blank"))
                .inputItems(TagPrefix.dust, GTOMaterials.CobaltOxideCeramic, 9)
                .inputFluids(GTMaterials.Glue.getFluid(1000))
                .outputItems(GTOTagPrefix.ROUGH_BLANK, GTOMaterials.CobaltOxideCeramic)
                .EUt(500)
                .duration(200)
                .save();

        ISOSTATIC_PRESSING_RECIPES.recipeBuilder(GTOCore.id("calcium_oxide_ceramic_rough_blank"))
                .inputItems(TagPrefix.dust, GTOMaterials.CalciumOxideCeramic, 9)
                .inputFluids(GTMaterials.Glue.getFluid(1000))
                .outputItems(GTOTagPrefix.ROUGH_BLANK, GTOMaterials.CalciumOxideCeramic)
                .EUt(500)
                .duration(200)
                .save();

        ISOSTATIC_PRESSING_RECIPES.recipeBuilder(GTOCore.id("lithium_oxide_ceramics_rough_blank"))
                .inputItems(TagPrefix.dust, GTOMaterials.LithiumOxideCeramics, 9)
                .inputFluids(GTOMaterials.PhenolicResin.getFluid(1000))
                .outputItems(GTOTagPrefix.ROUGH_BLANK, GTOMaterials.LithiumOxideCeramics)
                .EUt(500)
                .duration(200)
                .save();

        ISOSTATIC_PRESSING_RECIPES.recipeBuilder(GTOCore.id("magnesium_oxide_ceramic_rough_blank"))
                .inputItems(TagPrefix.dust, GTOMaterials.MagnesiumOxideCeramic, 9)
                .inputFluids(GTMaterials.Glue.getFluid(1000))
                .outputItems(GTOTagPrefix.ROUGH_BLANK, GTOMaterials.MagnesiumOxideCeramic)
                .EUt(500)
                .duration(200)
                .save();

        ISOSTATIC_PRESSING_RECIPES.recipeBuilder(GTOCore.id("tricalcium_phosphate_ceramic_rough_blank"))
                .inputItems(TagPrefix.dust, GTOMaterials.TricalciumPhosphateCeramic, 9)
                .inputFluids(GTOMaterials.PhenolicResin.getFluid(1000))
                .outputItems(GTOTagPrefix.ROUGH_BLANK, GTOMaterials.TricalciumPhosphateCeramic)
                .EUt(500)
                .duration(200)
                .save();

        ISOSTATIC_PRESSING_RECIPES.recipeBuilder(GTOCore.id("titanium_nitride_ceramic_rough_blank"))
                .inputItems(TagPrefix.dust, GTOMaterials.TitaniumNitrideCeramic, 9)
                .inputFluids(GTMaterials.Epoxy.getFluid(1000))
                .outputItems(GTOTagPrefix.ROUGH_BLANK, GTOMaterials.TitaniumNitrideCeramic)
                .EUt(500)
                .duration(200)
                .save();

        ISOSTATIC_PRESSING_RECIPES.recipeBuilder(GTOCore.id("zirconia_ceramic_rough_blank"))
                .inputItems(TagPrefix.dust, GTOMaterials.ZirconiaCeramic, 9)
                .inputFluids(GTMaterials.Epoxy.getFluid(1000))
                .outputItems(GTOTagPrefix.ROUGH_BLANK, GTOMaterials.ZirconiaCeramic)
                .EUt(500)
                .duration(200)
                .save();

        ISOSTATIC_PRESSING_RECIPES.recipeBuilder(GTOCore.id("strontium_carbonate_ceramic_rough_blank"))
                .inputItems(TagPrefix.dust, GTOMaterials.StrontiumCarbonateCeramic, 9)
                .inputFluids(GTMaterials.Epoxy.getFluid(1000))
                .outputItems(GTOTagPrefix.ROUGH_BLANK, GTOMaterials.StrontiumCarbonateCeramic)
                .EUt(500)
                .duration(200)
                .save();

        ISOSTATIC_PRESSING_RECIPES.recipeBuilder(GTOCore.id("aluminaceramic_ceramic_rough_blank"))
                .inputItems(TagPrefix.dust, GTOMaterials.AluminaCeramic, 9)
                .outputItems(GTOTagPrefix.ROUGH_BLANK, GTOMaterials.AluminaCeramic)
                .inputFluids(GTMaterials.Glue.getFluid(1000))
                .EUt(500)
                .duration(200)
                .save();

        ISOSTATIC_PRESSING_RECIPES.recipeBuilder(GTOCore.id("boroncarbideceramics_ceramic_rough_blank"))
                .inputItems(TagPrefix.dust, GTOMaterials.BoronCarbideCeramics, 9)
                .outputItems(GTOTagPrefix.ROUGH_BLANK, GTOMaterials.BoronCarbideCeramics)
                .inputFluids(GTMaterials.Epoxy.getFluid(1000))
                .EUt(500)
                .duration(200)
                .save();
    }
}
