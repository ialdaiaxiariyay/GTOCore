package com.gto.gtocore.data.recipe.classified;

import com.gto.gtocore.GTOCore;
import com.gto.gtocore.common.data.GTOItems;
import com.gto.gtocore.common.data.GTOMaterials;

import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.common.data.GTMaterials;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import static com.gto.gtocore.common.data.GTORecipeTypes.FORGE_HAMMER_RECIPES;

interface ForgeHammer {

    static void init() {
        FORGE_HAMMER_RECIPES.recipeBuilder(GTOCore.id("prismarine_crystals"))
                .inputItems(new ItemStack(Items.PRISMARINE_SHARD.asItem()))
                .outputItems(new ItemStack(Items.PRISMARINE_CRYSTALS.asItem()))
                .EUt(16)
                .duration(20)
                .save();

        FORGE_HAMMER_RECIPES.recipeBuilder(GTOCore.id("long_magmatter_rod"))
                .inputItems(TagPrefix.rod, GTOMaterials.Magmatter, 2)
                .outputItems(TagPrefix.rodLong, GTOMaterials.Magmatter)
                .EUt(2013265920)
                .duration(300)
                .save();

        FORGE_HAMMER_RECIPES.recipeBuilder(GTOCore.id("special_ceramics_dust"))
                .inputItems(TagPrefix.block, GTOMaterials.TitaniumNitrideCeramic)
                .outputItems(TagPrefix.dust, GTOMaterials.SpecialCeramics, 4)
                .EUt(7680)
                .duration(400)
                .save();

        FORGE_HAMMER_RECIPES.recipeBuilder(GTOCore.id("wrought_iron"))
                .inputItems(GTOItems.HOT_IRON_INGOT.asItem())
                .outputItems(TagPrefix.ingot, GTMaterials.WroughtIron)
                .EUt(16)
                .duration(200)
                .save();

        FORGE_HAMMER_RECIPES.recipeBuilder(GTOCore.id("diamond_lattice"))
                .inputItems("avaritia:diamond_lattice_block")
                .outputItems("avaritia:diamond_lattice", 9)
                .EUt(1920)
                .duration(200)
                .save();
    }
}
