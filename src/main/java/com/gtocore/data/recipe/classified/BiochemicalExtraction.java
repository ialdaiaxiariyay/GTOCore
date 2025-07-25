package com.gtocore.data.recipe.classified;

import com.gtocore.common.data.GTOItems;
import com.gtocore.common.data.GTOMaterials;
import com.gtocore.data.tag.Tags;

import com.gregtechceu.gtceu.common.data.GTMaterials;

import net.minecraft.world.item.Items;

import static com.gtocore.common.data.GTORecipeTypes.BIOCHEMICAL_EXTRACTION_RECIPES;

final class BiochemicalExtraction {

    public static void init() {
        BIOCHEMICAL_EXTRACTION_RECIPES.recipeBuilder("cow_spawn_egg")
                .notConsumable(Items.COW_SPAWN_EGG.asItem())
                .outputFluids(GTMaterials.Milk.getFluid(1000))
                .EUt(2)
                .duration(200)
                .save();

        BIOCHEMICAL_EXTRACTION_RECIPES.recipeBuilder("phantom_membrane")
                .notConsumable(Items.PHANTOM_SPAWN_EGG.asItem())
                .outputItems(Items.PHANTOM_MEMBRANE.asItem(), 2)
                .EUt(2)
                .duration(200)
                .save();

        BIOCHEMICAL_EXTRACTION_RECIPES.recipeBuilder("cerebrum")
                .notConsumable(Tags.HUMAN_EGG)
                .outputItems(GTOItems.CEREBRUM)
                .duration(200)
                .EUt(30)
                .save();

        BIOCHEMICAL_EXTRACTION_RECIPES.recipeBuilder("mycgene")
                .notConsumable("ad_astra:pygro_spawn_egg")
                .inputFluids(GTOMaterials.RapidlyReplicatingAnimalCells.getFluid(1000))
                .outputFluids(GTOMaterials.MycGene.getFluid(100))
                .EUt(480)
                .duration(200)
                .save();

        BIOCHEMICAL_EXTRACTION_RECIPES.recipeBuilder("oct4gene")
                .notConsumable("ad_astra:pygro_brute_spawn_egg")
                .inputFluids(GTOMaterials.RapidlyReplicatingAnimalCells.getFluid(1000))
                .outputFluids(GTOMaterials.Oct4Gene.getFluid(100))
                .EUt(480)
                .duration(200)
                .save();

        BIOCHEMICAL_EXTRACTION_RECIPES.recipeBuilder("sox2gene")
                .notConsumable("ad_astra:mogler_spawn_egg")
                .inputFluids(GTOMaterials.RapidlyReplicatingAnimalCells.getFluid(1000))
                .outputFluids(GTOMaterials.Sox2Gene.getFluid(100))
                .EUt(480)
                .duration(200)
                .save();

        BIOCHEMICAL_EXTRACTION_RECIPES.recipeBuilder("kfl4gene")
                .notConsumable("ad_astra:sulfur_creeper_spawn_egg")
                .inputFluids(GTOMaterials.RapidlyReplicatingAnimalCells.getFluid(1000))
                .outputFluids(GTOMaterials.Kfl4Gene.getFluid(100))
                .EUt(480)
                .duration(200)
                .save();

        BIOCHEMICAL_EXTRACTION_RECIPES.builder("wilden_horn")
                .notConsumable("ars_nouveau:wilden_hunter_se")
                .outputItems("ars_nouveau:wilden_horn")
                .EUt(120)
                .duration(200)
                .save();

        BIOCHEMICAL_EXTRACTION_RECIPES.builder("egg")
                .notConsumable(Items.CHICKEN_SPAWN_EGG.asItem())
                .outputItems(Items.EGG.asItem(), 4)
                .EUt(30)
                .duration(200)
                .save();
    }
}
