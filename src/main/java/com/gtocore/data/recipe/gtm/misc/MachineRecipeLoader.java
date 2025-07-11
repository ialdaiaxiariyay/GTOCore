package com.gtocore.data.recipe.gtm.misc;

import com.gtocore.common.data.machines.GTAEMachines;

import com.gtolib.utils.ItemUtils;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper;
import com.gregtechceu.gtceu.api.data.chemical.material.MarkerMaterial;
import com.gregtechceu.gtceu.api.data.chemical.material.MarkerMaterials;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.PropertyKey;
import com.gregtechceu.gtceu.api.data.chemical.material.stack.MaterialEntry;
import com.gregtechceu.gtceu.api.data.chemical.material.stack.MaterialStack;
import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.api.machine.multiblock.CleanroomType;
import com.gregtechceu.gtceu.common.block.LampBlock;
import com.gregtechceu.gtceu.common.block.StoneBlockType;
import com.gregtechceu.gtceu.common.data.GTBlocks;
import com.gregtechceu.gtceu.common.data.GTItems;
import com.gregtechceu.gtceu.common.data.GTMachines;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.gregtechceu.gtceu.common.data.machines.GTMachineUtils;
import com.gregtechceu.gtceu.config.ConfigHolder;
import com.gregtechceu.gtceu.data.recipe.CustomTags;
import com.gregtechceu.gtceu.data.recipe.VanillaRecipeHelper;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;

import com.tterrag.registrate.util.entry.ItemEntry;

import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.*;
import static com.gregtechceu.gtceu.common.data.GTItems.*;
import static com.gregtechceu.gtceu.common.data.GTMachines.*;
import static com.gregtechceu.gtceu.common.data.GTMaterials.*;
import static com.gtocore.common.data.GTORecipeTypes.*;

public final class MachineRecipeLoader {

    private MachineRecipeLoader() {}

    public static void init(Consumer<FinishedRecipe> provider) {
        ComputerRecipes.init();

        registerDecompositionRecipes();
        registerBlastFurnaceRecipes();
        registerAssemblerRecipes(provider);
        registerAlloyRecipes();
        registerBendingCompressingRecipes();
        registerCokeOvenRecipes();
        registerFluidRecipes();
        registerMixingCrystallizationRecipes();
        registerPrimitiveBlastFurnaceRecipes();
        registerRecyclingRecipes();
        registerStoneBricksRecipes(provider);
        registerNBTRemoval(provider);
        registerHatchConversion(provider);
    }

    private static void registerBendingCompressingRecipes() {
        COMPRESSOR_RECIPES.recipeBuilder("compressed_fireclay")
                .inputItems(dust, Fireclay)
                .outputItems(COMPRESSED_FIRECLAY)
                .duration(80).EUt(4)
                .save();

        for (ItemEntry<Item> shapeMold : SHAPE_MOLDS) {
            FORMING_PRESS_RECIPES.recipeBuilder("copy_mold_" + shapeMold.get())
                    .duration(120).EUt(22)
                    .notConsumable(shapeMold)
                    .inputItems(SHAPE_EMPTY)
                    .outputItems(shapeMold)
                    .save();
        }

        for (ItemEntry<Item> shapeExtruder : SHAPE_EXTRUDERS) {
            if (shapeExtruder == null) continue;
            FORMING_PRESS_RECIPES.recipeBuilder("copy_shape_" + shapeExtruder.get())
                    .duration(120).EUt(22)
                    .notConsumable(shapeExtruder)
                    .inputItems(SHAPE_EMPTY)
                    .outputItems(shapeExtruder)
                    .save();
        }

        BENDER_RECIPES.recipeBuilder("empty_shape")
                .circuitMeta(4)
                .inputItems(plate, Steel, 4)
                .outputItems(SHAPE_EMPTY)
                .duration(180).EUt(12).save();

        BENDER_RECIPES.recipeBuilder("fluid_cell_tin")
                .circuitMeta(12)
                .inputItems(plate, Tin, 2)
                .outputItems(FLUID_CELL)
                .duration(200).EUt(VA[ULV])
                .save();

        BENDER_RECIPES.recipeBuilder("fluid_cell_steel")
                .circuitMeta(12)
                .inputItems(plate, Steel)
                .outputItems(FLUID_CELL)
                .duration(100).EUt(VA[ULV])
                .save();

        BENDER_RECIPES.recipeBuilder("fluid_cell_ptfe")
                .circuitMeta(12)
                .inputItems(plate, Polytetrafluoroethylene)
                .outputItems(FLUID_CELL, 4)
                .duration(100).EUt(VA[ULV])
                .save();

        BENDER_RECIPES.recipeBuilder("fluid_cell_pbi")
                .circuitMeta(12)
                .inputItems(plate, Polybenzimidazole)
                .outputItems(FLUID_CELL, 16)
                .duration(100).EUt(VA[ULV])
                .save();

        EXTRUDER_RECIPES.recipeBuilder("fluid_cell_tin")
                .inputItems(ingot, Tin, 2)
                .notConsumable(SHAPE_EXTRUDER_CELL)
                .outputItems(FLUID_CELL)
                .duration(128).EUt(VA[LV])
                .save();

        EXTRUDER_RECIPES.recipeBuilder("fluid_cell_steel")
                .inputItems(ingot, Steel)
                .notConsumable(SHAPE_EXTRUDER_CELL)
                .outputItems(FLUID_CELL)
                .duration(128).EUt(VA[LV])
                .save();

        EXTRUDER_RECIPES.recipeBuilder("fluid_cell_ptfe")
                .inputItems(ingot, Polytetrafluoroethylene)
                .notConsumable(SHAPE_EXTRUDER_CELL)
                .outputItems(FLUID_CELL, 4)
                .duration(128).EUt(VA[LV])
                .save();

        EXTRUDER_RECIPES.recipeBuilder("fluid_cell_pbi")
                .inputItems(ingot, Polybenzimidazole)
                .notConsumable(SHAPE_EXTRUDER_CELL)
                .outputItems(FLUID_CELL, 16)
                .duration(128).EUt(VA[LV])
                .save();

        EXTRUDER_RECIPES.recipeBuilder("glass_vial")
                .inputItems(dust, Glass)
                .notConsumable(SHAPE_EXTRUDER_CELL)
                .outputItems(FLUID_CELL_GLASS_VIAL, 4)
                .duration(128).EUt(VA[LV]).save();

        COMPRESSOR_RECIPES.recipeBuilder("nether_quartz_plate")
                .inputItems(dust, NetherQuartz)
                .outputItems(plate, NetherQuartz)
                .duration(400).EUt(2).save();

        COMPRESSOR_RECIPES.recipeBuilder("certus_quartz_plate")
                .inputItems(dust, CertusQuartz)
                .outputItems(plate, CertusQuartz)
                .duration(400).EUt(2).save();

        COMPRESSOR_RECIPES.recipeBuilder("quartzite_plate")
                .inputItems(dust, Quartzite)
                .outputItems(plate, Quartzite)
                .duration(400).EUt(2).save();

        COMPRESSOR_RECIPES.recipeBuilder("coke_bricks")
                .inputItems(COKE_OVEN_BRICK, 4)
                .outputItems(GTBlocks.CASING_COKE_BRICKS.asStack())
                .duration(300).EUt(2).save();
    }

    private static void registerPrimitiveBlastFurnaceRecipes() {
        PRIMITIVE_BLAST_FURNACE_RECIPES.recipeBuilder("steel_from_coal_gem").inputItems(ingot, Iron)
                .inputItems(gem, Coal, 2).outputItems(ingot, Steel).outputItems(dustTiny, DarkAsh, 2).duration(1800)
                .save();
        PRIMITIVE_BLAST_FURNACE_RECIPES.recipeBuilder("steel_from_coal_dust").inputItems(ingot, Iron)
                .inputItems(dust, Coal, 2).outputItems(ingot, Steel).outputItems(dustTiny, DarkAsh, 2).duration(1800)
                .save();
        PRIMITIVE_BLAST_FURNACE_RECIPES.recipeBuilder("steel_from_charcoal_gem").inputItems(ingot, Iron)
                .inputItems(gem, Charcoal, 2).outputItems(ingot, Steel).outputItems(dustTiny, DarkAsh, 2)
                .duration(1800)
                .save();
        PRIMITIVE_BLAST_FURNACE_RECIPES.recipeBuilder("steel_from_charcoal_dust").inputItems(ingot, Iron)
                .inputItems(dust, Charcoal, 2).outputItems(ingot, Steel).outputItems(dustTiny, DarkAsh, 2)
                .duration(1800).save();
        PRIMITIVE_BLAST_FURNACE_RECIPES.recipeBuilder("steel_from_coke_gem").inputItems(ingot, Iron)
                .inputItems(gem, Coke).outputItems(ingot, Steel).chancedOutput(dust, Ash, 1111, 0).duration(1500)
                .save();
        PRIMITIVE_BLAST_FURNACE_RECIPES.recipeBuilder("steel_from_coke_dust").inputItems(ingot, Iron)
                .inputItems(dust, Coke).outputItems(ingot, Steel).chancedOutput(dust, Ash, 1111, 0).duration(1500)
                .save();

        PRIMITIVE_BLAST_FURNACE_RECIPES.recipeBuilder("steel_from_coal_gem_wrought").inputItems(ingot, WroughtIron)
                .inputItems(gem, Coal, 2).outputItems(ingot, Steel).outputItems(dustTiny, DarkAsh, 2).duration(800)
                .save();
        PRIMITIVE_BLAST_FURNACE_RECIPES.recipeBuilder("steel_from_coal_dust_wrought").inputItems(ingot, WroughtIron)
                .inputItems(dust, Coal, 2).outputItems(ingot, Steel).outputItems(dustTiny, DarkAsh, 2).duration(800)
                .save();
        PRIMITIVE_BLAST_FURNACE_RECIPES.recipeBuilder("steel_from_charcoal_gem_wrought").inputItems(ingot, WroughtIron)
                .inputItems(gem, Charcoal, 2).outputItems(ingot, Steel).outputItems(dustTiny, DarkAsh, 2).duration(800)
                .save();
        PRIMITIVE_BLAST_FURNACE_RECIPES.recipeBuilder("steel_from_charcoal_dust_wrought").inputItems(ingot, WroughtIron)
                .inputItems(dust, Charcoal, 2).outputItems(ingot, Steel).outputItems(dustTiny, DarkAsh, 2)
                .duration(800)
                .save();
        PRIMITIVE_BLAST_FURNACE_RECIPES.recipeBuilder("steel_from_coke_gem_wrought").inputItems(ingot, WroughtIron)
                .inputItems(gem, Coke).outputItems(ingot, Steel).chancedOutput(dust, Ash, 1111, 0).duration(600)
                .save();
        PRIMITIVE_BLAST_FURNACE_RECIPES.recipeBuilder("steel_from_coke_dust_wrought").inputItems(ingot, WroughtIron)
                .inputItems(dust, Coke).outputItems(ingot, Steel).chancedOutput(dust, Ash, 1111, 0).duration(600)
                .save();
    }

    private static void registerCokeOvenRecipes() {
        COKE_OVEN_RECIPES.recipeBuilder("log_to_charcoal").inputItems(ItemTags.LOGS_THAT_BURN)
                .outputItems(gem, Charcoal)
                .outputFluids(Creosote.getFluid(250)).duration(900).save();
        COKE_OVEN_RECIPES.recipeBuilder("coal_to_coke").inputItems(gem, Coal).outputItems(gem, Coke)
                .outputFluids(Creosote.getFluid(500)).duration(900).save();
        COKE_OVEN_RECIPES.recipeBuilder("coal_to_coke_block").inputItems(block, Coal).outputItems(block, Coke)
                .outputFluids(Creosote.getFluid(4500)).duration(8100).save();
    }

    private static void registerStoneBricksRecipes(Consumer<FinishedRecipe> provider) {
        // normal variant -> cobble variant
        List<ItemStack> cobbles = GTBlocks.STONE_BLOCKS.row(StoneBlockType.COBBLE).values().stream().map(ItemStack::new)
                .toList();
        List<ItemStack> mossCobbles = GTBlocks.STONE_BLOCKS.row(StoneBlockType.COBBLE_MOSSY).values().stream()
                .map(ItemStack::new).toList();
        List<ItemStack> stones = GTBlocks.STONE_BLOCKS.row(StoneBlockType.STONE).values().stream().map(ItemStack::new)
                .toList();
        List<ItemStack> polisheds = GTBlocks.STONE_BLOCKS.row(StoneBlockType.POLISHED).values().stream()
                .map(ItemStack::new).toList();
        List<ItemStack> bricks = GTBlocks.STONE_BLOCKS.row(StoneBlockType.BRICKS).values().stream().map(ItemStack::new)
                .toList();
        List<ItemStack> crackedBricks = GTBlocks.STONE_BLOCKS.row(StoneBlockType.BRICKS_CRACKED).values().stream()
                .map(ItemStack::new).toList();
        List<ItemStack> mossBricks = GTBlocks.STONE_BLOCKS.row(StoneBlockType.BRICKS_MOSSY).values().stream()
                .map(ItemStack::new).toList();
        List<ItemStack> chiseledBricks = GTBlocks.STONE_BLOCKS.row(StoneBlockType.CHISELED).values().stream()
                .map(ItemStack::new).toList();
        List<ItemStack> tiledBricks = GTBlocks.STONE_BLOCKS.row(StoneBlockType.TILED).values().stream()
                .map(ItemStack::new).toList();
        List<ItemStack> smallTiledBricks = GTBlocks.STONE_BLOCKS.row(StoneBlockType.TILED_SMALL).values().stream()
                .map(ItemStack::new).toList();
        List<ItemStack> windmillA = GTBlocks.STONE_BLOCKS.row(StoneBlockType.WINDMILL_A).values().stream()
                .map(ItemStack::new).toList();
        List<ItemStack> windmillB = GTBlocks.STONE_BLOCKS.row(StoneBlockType.WINDMILL_B).values().stream()
                .map(ItemStack::new).toList();
        List<ItemStack> squareBricks = GTBlocks.STONE_BLOCKS.row(StoneBlockType.BRICKS_SQUARE).values().stream()
                .map(ItemStack::new).toList();
        List<ItemStack> smallBricks = GTBlocks.STONE_BLOCKS.row(StoneBlockType.BRICKS_SMALL).values().stream()
                .map(ItemStack::new).toList();

        registerSmoothRecipe(provider, cobbles, stones);
        registerCobbleRecipe(stones, cobbles);
        registerMossRecipe(cobbles, mossCobbles);
        registerSmoothRecipe(provider, stones, polisheds);
        registerBricksRecipe(polisheds, bricks, MarkerMaterials.Color.LightBlue);
        registerCobbleRecipe(bricks, crackedBricks);
        registerMossRecipe(bricks, mossBricks);
        registerBricksRecipe(polisheds, chiseledBricks, MarkerMaterials.Color.White);
        registerBricksRecipe(polisheds, tiledBricks, MarkerMaterials.Color.Red);
        registerBricksRecipe(tiledBricks, smallTiledBricks, MarkerMaterials.Color.Red);
        registerBricksRecipe(polisheds, windmillA, MarkerMaterials.Color.Blue);
        registerBricksRecipe(polisheds, windmillB, MarkerMaterials.Color.Yellow);
        registerBricksRecipe(polisheds, squareBricks, MarkerMaterials.Color.Green);
        registerBricksRecipe(polisheds, smallBricks, MarkerMaterials.Color.Pink);

        for (int i = 0; i < stones.size(); i++) {
            ResourceLocation bricksId = ItemUtils.getIdLocation(bricks.get(i).getItem());
            EXTRUDER_RECIPES.recipeBuilder("extrude_" + bricksId.getPath())
                    .inputItems(stones.get(i))
                    .notConsumable(SHAPE_EXTRUDER_INGOT)
                    .outputItems(bricks.get(i))
                    .duration(24).EUt(8).save();
        }
    }

    private static void registerMixingCrystallizationRecipes() {
        AUTOCLAVE_RECIPES.recipeBuilder("silicon_dioxide_to_quartzite_gem")
                .inputItems(dust, SiliconDioxide)
                .inputFluids(DistilledWater.getFluid(250))
                .chancedOutput(ChemicalHelper.get(gem, Quartzite), 1000, 1000)
                .duration(1200).EUt(24).save();

        // todo find UU-Matter replacement
        // AUTOCLAVE_RECIPES.recipeBuilder()
        // .inputItems(dust, NetherStar)
        // .inputFluids(UUMatter.getFluid(576))
        // .chancedOutput(new ItemStack(Items.NETHER_STAR), 3333, 3333)
        // .duration(72000).EUt(VA[HV]).save();

        MIXER_RECIPES.recipeBuilder("indium_concentrate")
                .inputItems(crushedPurified, Sphalerite)
                .inputItems(crushedPurified, Galena)
                .inputFluids(SulfuricAcid.getFluid(4000))
                .outputFluids(IndiumConcentrate.getFluid(1000))
                .duration(60).EUt(150).save();
    }

    private static final MaterialStack[][] alloySmelterList = {
            { new MaterialStack(Copper, 3L), new MaterialStack(Tin, 1), new MaterialStack(Bronze, 4L) },
            { new MaterialStack(Copper, 3L), new MaterialStack(Zinc, 1), new MaterialStack(Brass, 4L) },
            { new MaterialStack(Copper, 1), new MaterialStack(Nickel, 1), new MaterialStack(Cupronickel, 2L) },
            { new MaterialStack(Copper, 1), new MaterialStack(Redstone, 4L), new MaterialStack(RedAlloy, 1) },
            { new MaterialStack(AnnealedCopper, 3L), new MaterialStack(Tin, 1), new MaterialStack(Bronze, 4L) },
            { new MaterialStack(AnnealedCopper, 3L), new MaterialStack(Zinc, 1), new MaterialStack(Brass, 4L) },
            { new MaterialStack(AnnealedCopper, 1), new MaterialStack(Nickel, 1), new MaterialStack(Cupronickel, 2L) },
            { new MaterialStack(AnnealedCopper, 1), new MaterialStack(Redstone, 4L), new MaterialStack(RedAlloy, 1) },
            { new MaterialStack(Iron, 1), new MaterialStack(Tin, 1), new MaterialStack(TinAlloy, 2L) },
            { new MaterialStack(WroughtIron, 1), new MaterialStack(Tin, 1), new MaterialStack(TinAlloy, 2L) },
            { new MaterialStack(Iron, 2L), new MaterialStack(Nickel, 1), new MaterialStack(Invar, 3L) },
            { new MaterialStack(WroughtIron, 2L), new MaterialStack(Nickel, 1), new MaterialStack(Invar, 3L) },
            { new MaterialStack(Lead, 4L), new MaterialStack(Antimony, 1), new MaterialStack(BatteryAlloy, 5L) },
            { new MaterialStack(Gold, 1), new MaterialStack(Silver, 1), new MaterialStack(Electrum, 2L) },
            { new MaterialStack(Magnesium, 1), new MaterialStack(Aluminium, 2L), new MaterialStack(Magnalium, 3L) },
            { new MaterialStack(Silver, 1), new MaterialStack(Electrotine, 4), new MaterialStack(BlueAlloy, 1) } };

    private static void registerAlloyRecipes() {
        for (MaterialStack[] stack : alloySmelterList) {
            String recipeNape = stack[0].material().getName() + "_%s_and_" + stack[1].material().getName() +
                    "_%s_into_" + stack[2].material().getName();
            if (stack[0].material().hasProperty(PropertyKey.INGOT)) {
                ALLOY_SMELTER_RECIPES.recipeBuilder(String.format(recipeNape, "ingot", "dust"))
                        .duration((int) stack[2].amount() * 50).EUt(16)
                        .inputItems(ingot, stack[0].material(), (int) stack[0].amount())
                        .inputItems(dust, stack[1].material(), (int) stack[1].amount())
                        .outputItems(ChemicalHelper.get(ingot, stack[2].material(), (int) stack[2].amount()))
                        .save();
            }
            if (stack[1].material().hasProperty(PropertyKey.INGOT)) {
                ALLOY_SMELTER_RECIPES.recipeBuilder(String.format(recipeNape, "dust", "ingot"))
                        .duration((int) stack[2].amount() * 50).EUt(16)
                        .inputItems(dust, stack[0].material(), (int) stack[0].amount())
                        .inputItems(ingot, stack[1].material(), (int) stack[1].amount())
                        .outputItems(ChemicalHelper.get(ingot, stack[2].material(), (int) stack[2].amount()))
                        .save();
            }
            if (stack[0].material().hasProperty(PropertyKey.INGOT) &&
                    stack[1].material().hasProperty(PropertyKey.INGOT)) {
                ALLOY_SMELTER_RECIPES.recipeBuilder(String.format(recipeNape, "ingot", "ingot"))
                        .duration((int) stack[2].amount() * 50).EUt(16)
                        .inputItems(ingot, stack[0].material(), (int) stack[0].amount())
                        .inputItems(ingot, stack[1].material(), (int) stack[1].amount())
                        .outputItems(ChemicalHelper.get(ingot, stack[2].material(), (int) stack[2].amount()))
                        .save();
            }
            ALLOY_SMELTER_RECIPES.recipeBuilder(String.format(recipeNape, "dust", "dust"))
                    .duration((int) stack[2].amount() * 50).EUt(16)
                    .inputItems(dust, stack[0].material(), (int) stack[0].amount())
                    .inputItems(dust, stack[1].material(), (int) stack[1].amount())
                    .outputItems(ChemicalHelper.get(ingot, stack[2].material(), (int) stack[2].amount()))
                    .save();
        }

        COMPRESSOR_RECIPES.recipeBuilder("carbon_mesh").inputItems(CARBON_FIBERS, 2).outputItems(CARBON_MESH)
                .duration(100).EUt(2).save();
        COMPRESSOR_RECIPES.recipeBuilder("carbon_fiber_plate").inputItems(CARBON_MESH).outputItems(CARBON_FIBER_PLATE)
                .duration(200).EUt(2).save();

        ALLOY_SMELTER_RECIPES.recipeBuilder("rubber_sheet").duration(10).EUt(VA[ULV]).inputItems(ingot, Rubber, 2)
                .notConsumable(SHAPE_MOLD_PLATE).outputItems(plate, Rubber).save();
        ALLOY_SMELTER_RECIPES.recipeBuilder("rubber_bar").duration(100).EUt(VA[ULV]).inputItems(dust, Sulfur)
                .inputItems(dust, RawRubber, 3).outputItems(ingot, Rubber).save();

        ALLOY_SMELTER_RECIPES.recipeBuilder("coke_oven_brick").duration(150).EUt(VA[ULV])
                .inputItems(Tags.Items.SAND)
                .inputItems(new ItemStack(Items.CLAY_BALL))
                .outputItems(COKE_OVEN_BRICK, 2)
                .save();
    }

    private static void registerAssemblerRecipes(Consumer<FinishedRecipe> provider) {
        for (int i = 0; i < CHEMICAL_DYES.length; i++) {
            CANNER_RECIPES.recipeBuilder("spray_can_" + CHEMICAL_DYES[i].getName())
                    .inputItems(SPRAY_EMPTY)
                    .inputFluids(CHEMICAL_DYES[i].getFluid(L << 2))
                    .outputItems(SPRAY_CAN_DYES[i])
                    .EUt(VA[ULV]).duration(200)

                    .save();

            DyeColor color = DyeColor.byId(i);

            LampBlock lamp = GTBlocks.LAMPS.get(color).get();
            for (int lampMeta = 0; lampMeta < 8; lampMeta++) {
                ASSEMBLER_RECIPES.recipeBuilder("lamp_" + color + "_" + lampMeta)
                        .inputItems(plate, Glass, 6)
                        .inputItems(dust, Glowstone, 1)
                        .inputFluids(GTMaterials.CHEMICAL_DYES[i].getFluid(GTValues.L))
                        .outputItems(lamp.getStackFromIndex(lampMeta))
                        .circuitMeta(lampMeta + 1).EUt(VA[ULV]).duration(40)

                        .save();
            }
            lamp = GTBlocks.BORDERLESS_LAMPS.get(color).get();
            for (int lampMeta = 0; lampMeta < 8; lampMeta++) {
                ASSEMBLER_RECIPES.recipeBuilder("borderless_lamp_" + color + "_" + lampMeta)
                        .inputItems(plate, Glass, 6)
                        .inputItems(dust, Glowstone, 1)
                        .inputFluids(GTMaterials.CHEMICAL_DYES[i].getFluid(GTValues.L))
                        .outputItems(lamp.getStackFromIndex(lampMeta))
                        .circuitMeta(lampMeta + 9).EUt(VA[ULV]).duration(40)

                        .save();
            }

        }

        CANNER_RECIPES.recipeBuilder("spray_can_solvent")
                .inputItems(SPRAY_EMPTY)
                .inputFluids(Acetone.getFluid(1000))
                .outputItems(SPRAY_SOLVENT)
                .EUt(VA[ULV]).duration(200).save();

        CANNER_RECIPES.recipeBuilder("mask_filter")
                .inputItems(plate, Polyethylene, 4)
                .inputItems(dust, ActivatedCarbon, 2)
                .outputItems(MASK_FILTER)

                .duration(100).EUt(2).save();

        CANNER_RECIPES.recipeBuilder("pack_paracetamol")
                .inputItems(dust, Paracetamol, 16)
                .notConsumable(GTItems.SHAPE_MOLD_PILL)
                .outputItems(GTItems.PARACETAMOL_PILL.asStack(16))
                .duration(60).EUt(VA[LV]).save();

        CANNER_RECIPES.recipeBuilder("pack_rad_away")
                .inputItems(dust, RadAway, 16)
                .notConsumable(GTItems.SHAPE_MOLD_PILL)
                .outputItems(GTItems.RAD_AWAY_PILL.asStack(16))
                .duration(60).EUt(VA[LV]).save();

        ASSEMBLER_RECIPES.recipeBuilder("cover_shutter")
                .inputItems(new ItemStack(Items.IRON_DOOR))
                .inputItems(plate, Iron, 2)
                .outputItems(COVER_SHUTTER, 2)
                .EUt(16).duration(100).save();

        ASSEMBLER_RECIPES.recipeBuilder("cover_machine_controller")
                .inputItems(new ItemStack(Blocks.LEVER))
                .inputItems(plate, Iron)
                .inputFluids(SolderingAlloy, L / 2)
                .outputItems(COVER_MACHINE_CONTROLLER)
                .EUt(16).duration(100).save();

        ASSEMBLER_RECIPES.recipeBuilder("cover_energy_detector")
                .inputItems(cableGtSingle, Copper, 4)
                .inputItems(CustomTags.LV_CIRCUITS)
                .inputItems(plate, Iron)
                .inputFluids(SolderingAlloy, L / 2)
                .outputItems(COVER_ENERGY_DETECTOR)
                .EUt(16).duration(100).save();

        ASSEMBLER_RECIPES.recipeBuilder("cover_advanced_energy_detector")
                .inputItems(COVER_ENERGY_DETECTOR)
                .inputItems(SENSOR_HV)
                .inputFluids(SolderingAlloy, L / 2)
                .outputItems(COVER_ENERGY_DETECTOR_ADVANCED)
                .EUt(16).duration(100).save();

        ASSEMBLER_RECIPES.recipeBuilder("cover_activity_detector")
                .inputItems(new ItemStack(Blocks.REDSTONE_TORCH))
                .inputItems(plate, Iron)
                .inputFluids(SolderingAlloy, L / 2)
                .outputItems(COVER_ACTIVITY_DETECTOR)
                .EUt(16).duration(100).save();

        ASSEMBLER_RECIPES.recipeBuilder("cover_advanced_activity_cover")
                .inputItems(wireFine, Gold, 4)
                .inputItems(CustomTags.HV_CIRCUITS)
                .inputItems(plate, Aluminium)
                .inputFluids(SolderingAlloy, L / 2)
                .outputItems(COVER_ACTIVITY_DETECTOR_ADVANCED)
                .EUt(16).duration(100).save();

        ASSEMBLER_RECIPES.recipeBuilder("cover_fluid_detector")
                .inputItems(new ItemStack(Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE))
                .inputItems(plate, Iron)
                .inputFluids(SolderingAlloy, L / 2)
                .outputItems(COVER_FLUID_DETECTOR)
                .EUt(16).duration(100).save();

        ASSEMBLER_RECIPES.recipeBuilder("cover_item_detector")
                .inputItems(new ItemStack(Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE))
                .inputItems(plate, Iron)
                .inputFluids(SolderingAlloy, L / 2)
                .outputItems(COVER_ITEM_DETECTOR)
                .EUt(16).duration(100).save();

        ASSEMBLER_RECIPES.recipeBuilder("cover_advanced_fluid_detector")
                .inputItems(COVER_FLUID_DETECTOR)
                .inputItems(SENSOR_HV)
                .inputFluids(SolderingAlloy, L / 2)
                .outputItems(COVER_FLUID_DETECTOR_ADVANCED)
                .EUt(16).duration(100).save();

        ASSEMBLER_RECIPES.recipeBuilder("cover_screen")
                .inputItems(plate, Glass)
                .inputItems(foil, Aluminium, 4)
                .inputItems(CustomTags.LV_CIRCUITS)
                .inputItems(wireFine, Copper, 4)
                .outputItems(COVER_SCREEN)
                .EUt(16).duration(50).save();

        ASSEMBLER_RECIPES.recipeBuilder("cover_infinite_water")
                .inputItems(ELECTRIC_PUMP_HV, 2)
                .inputItems(new ItemStack(Items.CAULDRON))
                .inputItems(CustomTags.HV_CIRCUITS)
                .outputItems(COVER_INFINITE_WATER)
                .EUt(VA[HV]).duration(100).save();

        ASSEMBLER_RECIPES.recipeBuilder("cover_ender_fluid_link")
                .inputItems(plate, EnderPearl, 9)
                .inputItems(plateDouble, StainlessSteel)
                .inputItems(SENSOR_HV)
                .inputItems(EMITTER_HV)
                .inputItems(ELECTRIC_PUMP_HV)
                .inputFluids(Polyethylene, L << 1)
                .outputItems(COVER_ENDER_FLUID_LINK)
                .EUt(VA[HV]).duration(320).save();

        ASSEMBLER_RECIPES.recipeBuilder("cover_storage")
                .inputItems(Tags.Items.CHESTS_WOODEN)
                .inputItems(ELECTRIC_PISTON_LV)
                .inputItems(plate, Iron)
                .inputFluids(SolderingAlloy, L / 2)
                .outputItems(COVER_STORAGE)
                .EUt(16)
                .duration(100).save();

        ASSEMBLER_RECIPES.recipeBuilder("casing_bronze_bricks").EUt(16).inputItems(plate, Bronze, 6)
                .inputItems(new ItemStack(Blocks.BRICKS)).circuitMeta(6)
                .outputItems(GTBlocks.CASING_BRONZE_BRICKS.asStack(ConfigHolder.INSTANCE.recipes.casingsPerCraft))
                .duration(50).save();
        ASSEMBLER_RECIPES.recipeBuilder("casing_invar_heatproof").EUt(16).inputItems(plate, Invar, 6)
                .inputItems(frameGt, Invar).circuitMeta(6)
                .outputItems(GTBlocks.CASING_INVAR_HEATPROOF.asStack(ConfigHolder.INSTANCE.recipes.casingsPerCraft))
                .duration(50).save();
        ASSEMBLER_RECIPES.recipeBuilder("casing_steel_solid").EUt(16).inputItems(plate, Steel, 6)
                .inputItems(frameGt, Steel).circuitMeta(6)
                .outputItems(GTBlocks.CASING_STEEL_SOLID.asStack(ConfigHolder.INSTANCE.recipes.casingsPerCraft))
                .duration(50).save();
        ASSEMBLER_RECIPES.recipeBuilder("casing_aluminium_frostproof").EUt(16).inputItems(plate, Aluminium, 6)
                .inputItems(frameGt, Aluminium).circuitMeta(6)
                .outputItems(
                        GTBlocks.CASING_ALUMINIUM_FROSTPROOF.asStack(ConfigHolder.INSTANCE.recipes.casingsPerCraft))
                .duration(50).save();
        ASSEMBLER_RECIPES.recipeBuilder("casing_tungsteensteel_robust").EUt(16).inputItems(plate, TungstenSteel, 6)
                .inputItems(frameGt, TungstenSteel).circuitMeta(6)
                .outputItems(
                        GTBlocks.CASING_TUNGSTENSTEEL_ROBUST.asStack(ConfigHolder.INSTANCE.recipes.casingsPerCraft))
                .duration(50).save();
        ASSEMBLER_RECIPES.recipeBuilder("casing_stainless_clean").EUt(16).inputItems(plate, StainlessSteel, 6)
                .inputItems(frameGt, StainlessSteel).circuitMeta(6)
                .outputItems(GTBlocks.CASING_STAINLESS_CLEAN.asStack(ConfigHolder.INSTANCE.recipes.casingsPerCraft))
                .duration(50).save();
        ASSEMBLER_RECIPES.recipeBuilder("casing_titanium_stable").EUt(16).inputItems(plate, Titanium, 6)
                .inputItems(frameGt, Titanium).circuitMeta(6)
                .outputItems(GTBlocks.CASING_TITANIUM_STABLE.asStack(ConfigHolder.INSTANCE.recipes.casingsPerCraft))
                .duration(50).save();
        ASSEMBLER_RECIPES.recipeBuilder("casing_hsse_sturdy").EUt(16).inputItems(plate, HSSE, 6)
                .inputItems(frameGt, Europium).circuitMeta(6)
                .outputItems(GTBlocks.CASING_HSSE_STURDY.asStack(ConfigHolder.INSTANCE.recipes.casingsPerCraft))
                .duration(50).save();
        ASSEMBLER_RECIPES.recipeBuilder("casing_palladium_substation").EUt(16).inputItems(plate, Palladium, 6)
                .inputItems(frameGt, Iridium).circuitMeta(6)
                .outputItems(
                        GTBlocks.CASING_PALLADIUM_SUBSTATION.asStack(ConfigHolder.INSTANCE.recipes.casingsPerCraft))
                .duration(50).save();

        ASSEMBLER_RECIPES.recipeBuilder("casing_ptfe_inert").EUt(16).inputItems(GTBlocks.CASING_STEEL_SOLID.asStack())
                .inputFluids(Polytetrafluoroethylene.getFluid(216)).circuitMeta(6)
                .outputItems(GTBlocks.CASING_PTFE_INERT.asStack()).duration(50)
                .save();

        ASSEMBLER_RECIPES.recipeBuilder("casing_bronze_firebox")
                .inputItems(rod, Bronze, 3)
                .inputItems(frameGt, Bronze)
                .inputItems(plate, Bronze, 3)
                .outputItems(GTBlocks.FIREBOX_BRONZE, 2)
                .duration(100)
                .EUt(VA[LV])
                .save();
        ASSEMBLER_RECIPES.recipeBuilder("casing_steel_firebox")
                .inputItems(rod, Steel, 3)
                .inputItems(frameGt, Steel)
                .inputItems(plate, Steel, 3)
                .outputItems(GTBlocks.FIREBOX_STEEL, 2)
                .duration(200)
                .EUt(VA[LV])
                .save();
        ASSEMBLER_RECIPES.recipeBuilder("casing_titanium_firebox")
                .inputItems(rod, Titanium, 3)
                .inputItems(frameGt, Titanium)
                .inputItems(plate, Titanium, 3)
                .outputItems(GTBlocks.FIREBOX_TITANIUM, 2)
                .duration(300)
                .EUt(VA[HV])
                .save();
        ASSEMBLER_RECIPES.recipeBuilder("casing_tungstensteel_firebox")
                .inputItems(rod, TungstenSteel, 3)
                .inputItems(frameGt, TungstenSteel)
                .inputItems(plate, TungstenSteel, 3)
                .outputItems(GTBlocks.FIREBOX_TUNGSTENSTEEL, 2)
                .duration(400)
                .EUt(VA[EV])
                .save();

        ASSEMBLER_RECIPES.recipeBuilder("superconducting_coil_luv").EUt(VA[LuV])
                .inputItems(wireGtDouble, IndiumTinBariumTitaniumCuprate, 32).inputItems(foil, NiobiumTitanium, 32)
                .inputFluids(Trinium, GTValues.L * 24).outputItems(GTBlocks.SUPERCONDUCTING_COIL.asStack())
                .duration(100).save();
        ASSEMBLER_RECIPES.recipeBuilder("superconducting_coil_zpm").EUt(VA[ZPM])
                .inputItems(wireGtDouble, UraniumRhodiumDinaquadide, 16).inputItems(foil, NiobiumTitanium, 16)
                .inputFluids(Trinium, GTValues.L << 4).outputItems(GTBlocks.SUPERCONDUCTING_COIL.asStack())
                .duration(100).save();
        ASSEMBLER_RECIPES.recipeBuilder("superconducting_coil_uv").EUt(VA[UV])
                .inputItems(wireGtDouble, EnrichedNaquadahTriniumEuropiumDuranide, 8)
                .inputItems(foil, NiobiumTitanium, 8).inputFluids(Trinium, GTValues.L << 3)
                .outputItems(GTBlocks.SUPERCONDUCTING_COIL.asStack()).duration(100).save();
        ASSEMBLER_RECIPES.recipeBuilder("fusion_coil").EUt(VA[ZPM]).inputItems(GTBlocks.SUPERCONDUCTING_COIL.asStack())
                .inputItems(FIELD_GENERATOR_IV.asStack(2)).inputItems(ELECTRIC_PUMP_IV)
                .inputItems(NEUTRON_REFLECTOR.asStack(2)).inputItems(CustomTags.LuV_CIRCUITS, 4)
                .inputItems(pipeSmallFluid, Naquadah, 4).inputItems(plate, Europium, 4)
                .inputFluids(VanadiumGallium, GTValues.L << 2).outputItems(GTBlocks.FUSION_COIL.asStack())
                .duration(100).cleanroom(CleanroomType.CLEANROOM)
                .save();
        ASSEMBLER_RECIPES.recipeBuilder("fusion_glass").EUt(VA[LuV])
                .inputItems(GTBlocks.CASING_LAMINATED_GLASS.asStack()).inputItems(plate, Naquadah, 4)
                .inputItems(NEUTRON_REFLECTOR.asStack(4)).outputItems(GTBlocks.FUSION_GLASS.asStack(2))
                .inputFluids(Polybenzimidazole, GTValues.L).duration(50).cleanroom(CleanroomType.CLEANROOM)
                .save();
        ASSEMBLER_RECIPES.recipeBuilder("fusion_casing").EUt(VA[LuV]).inputItems(GTBlocks.MACHINE_CASING_LuV.asStack())
                .inputItems(GTBlocks.SUPERCONDUCTING_COIL.asStack()).inputItems(NEUTRON_REFLECTOR)
                .inputItems(ELECTRIC_PUMP_LuV).inputItems(plate, TungstenSteel, 6)
                .inputFluids(Polybenzimidazole, GTValues.L)
                .outputItems(GTBlocks.FUSION_CASING.asStack(ConfigHolder.INSTANCE.recipes.casingsPerCraft))

                .duration(100).cleanroom(CleanroomType.CLEANROOM).save();
        ASSEMBLER_RECIPES.recipeBuilder("fusion_casing_mk2").EUt(VA[ZPM])
                .inputItems(GTBlocks.MACHINE_CASING_ZPM.asStack()).inputItems(GTBlocks.FUSION_COIL.asStack())
                .inputItems(VOLTAGE_COIL_ZPM.asStack(2)).inputItems(FIELD_GENERATOR_LuV).inputItems(plate, Europium, 6)
                .inputFluids(Polybenzimidazole, GTValues.L << 1)
                .outputItems(GTBlocks.FUSION_CASING_MK2.asStack(ConfigHolder.INSTANCE.recipes.casingsPerCraft))
                .duration(100).cleanroom(CleanroomType.CLEANROOM)
                .save();
        ASSEMBLER_RECIPES.recipeBuilder("fusion_casing_mk3").EUt(VA[UV])
                .inputItems(GTBlocks.MACHINE_CASING_UV.asStack()).inputItems(GTBlocks.FUSION_COIL.asStack())
                .inputItems(VOLTAGE_COIL_UV.asStack(2)).inputItems(FIELD_GENERATOR_ZPM).inputItems(plate, Americium, 6)
                .inputFluids(Polybenzimidazole, GTValues.L << 2)
                .outputItems(GTBlocks.FUSION_CASING_MK3.asStack(ConfigHolder.INSTANCE.recipes.casingsPerCraft))
                .duration(100).cleanroom(CleanroomType.CLEANROOM)
                .save();

        ASSEMBLER_RECIPES.recipeBuilder("casing_steel_turbine").EUt(16).inputItems(plate, Magnalium, 6)
                .inputItems(frameGt, BlueSteel, 1).circuitMeta(6)
                .outputItems(GTBlocks.CASING_STEEL_TURBINE.asStack(ConfigHolder.INSTANCE.recipes.casingsPerCraft))
                .duration(50)
                .save();
        ASSEMBLER_RECIPES.recipeBuilder("casing_stainless_steel_turbine").EUt(16)
                .inputItems(GTBlocks.CASING_STEEL_TURBINE.asStack()).inputItems(plate, StainlessSteel, 6).circuitMeta(6)
                .outputItems(GTBlocks.CASING_STAINLESS_TURBINE.asStack(ConfigHolder.INSTANCE.recipes.casingsPerCraft))
                .duration(50)
                .save();
        ASSEMBLER_RECIPES.recipeBuilder("casing_titanium_turbine").EUt(16)
                .inputItems(GTBlocks.CASING_STEEL_TURBINE.asStack()).inputItems(plate, Titanium, 6).circuitMeta(6)
                .outputItems(GTBlocks.CASING_TITANIUM_TURBINE.asStack(ConfigHolder.INSTANCE.recipes.casingsPerCraft))
                .duration(50)
                .save();
        ASSEMBLER_RECIPES.recipeBuilder("casing_tungstensteel_turbine").EUt(16)
                .inputItems(GTBlocks.CASING_STEEL_TURBINE.asStack()).inputItems(plate, TungstenSteel, 6).circuitMeta(6)
                .outputItems(
                        GTBlocks.CASING_TUNGSTENSTEEL_TURBINE.asStack(ConfigHolder.INSTANCE.recipes.casingsPerCraft))
                .duration(50)
                .save();

        ASSEMBLER_RECIPES.recipeBuilder("casing_grate_casing")
                .inputItems(Items.IRON_BARS, 6)
                .inputItems(frameGt, Steel)
                .inputItems(ELECTRIC_MOTOR_MV)
                .inputItems(rotor, Steel)
                .outputItems(GTBlocks.CASING_GRATE, ConfigHolder.INSTANCE.recipes.casingsPerCraft)
                .duration(800)
                .EUt(VA[IV])
                .save();

        ASSEMBLER_RECIPES.recipeBuilder("assembly_line_casing")
                .inputItems(gear, Ruridit, 2)
                .inputItems(plate, Steel, 4)
                .inputItems(ROBOT_ARM_IV, 2)
                .inputItems(frameGt, TungstenSteel)
                .outputItems(GTBlocks.CASING_ASSEMBLY_LINE, ConfigHolder.INSTANCE.recipes.casingsPerCraft)
                .duration(650)
                .EUt(VA[IV])
                .save();

        ASSEMBLER_RECIPES.recipeBuilder("assembly_control_casing")
                .inputItems(HIGH_POWER_INTEGRATED_CIRCUIT)
                .inputItems(CustomTags.EV_CIRCUITS, 4)
                .inputItems(SENSOR_IV)
                .inputItems(EMITTER_IV)
                .inputItems(ELECTRIC_MOTOR_IV)
                .inputItems(frameGt, TungstenSteel)
                .outputItems(GTBlocks.CASING_ASSEMBLY_CONTROL, ConfigHolder.INSTANCE.recipes.casingsPerCraft)
                .duration(650)
                .EUt(VA[IV])
                .save();

        ASSEMBLER_RECIPES.recipeBuilder("plascrete").EUt(48).inputItems(frameGt, Steel)
                .inputItems(plate, Polyethylene, 6).inputFluids(Concrete, L)
                .outputItems(GTBlocks.PLASTCRETE.asStack(ConfigHolder.INSTANCE.recipes.casingsPerCraft)).duration(200)
                .save();
        ASSEMBLER_RECIPES.recipeBuilder("cleanroom_glass").EUt(48).inputItems(frameGt, Steel)
                .inputItems(plate, Polyethylene, 6).inputFluids(Glass, L)
                .outputItems(GTBlocks.CLEANROOM_GLASS.asStack(ConfigHolder.INSTANCE.recipes.casingsPerCraft))
                .duration(200)
                .save();

        ASSEMBLER_RECIPES.recipeBuilder("hopper_wrought_iron").EUt(2).inputItems(Tags.Items.CHESTS_WOODEN)
                .inputItems(plate, WroughtIron, 5).outputItems(new ItemStack(Blocks.HOPPER)).duration(800)
                .save();

        ASSEMBLER_RECIPES.recipeBuilder("wooden_crate").EUt(16).inputItems(ItemTags.PLANKS, 4)
                .inputItems(screw, Iron, 4).outputItems(WOODEN_CRATE).duration(100).circuitMeta(5)
                .save();
        ASSEMBLER_RECIPES.recipeBuilder("bronze_crate").EUt(16).inputItems(rodLong, Bronze, 4)
                .inputItems(plate, Bronze, 4).outputItems(BRONZE_CRATE).duration(200).circuitMeta(1)
                .save();
        ASSEMBLER_RECIPES.recipeBuilder("steel_crate").EUt(16).inputItems(rodLong, Steel, 4)
                .inputItems(plate, Steel, 4)
                .outputItems(STEEL_CRATE).duration(200).circuitMeta(1)
                .save();
        ASSEMBLER_RECIPES.recipeBuilder("aluminium_crate").EUt(16).inputItems(rodLong, Aluminium, 4)
                .inputItems(plate, Aluminium, 4).outputItems(ALUMINIUM_CRATE).duration(200).circuitMeta(1)
                .save();
        ASSEMBLER_RECIPES.recipeBuilder("stainless_steel_crate").EUt(16).inputItems(rodLong, StainlessSteel, 4)
                .inputItems(plate, StainlessSteel, 4).outputItems(STAINLESS_STEEL_CRATE).circuitMeta(1).duration(200)
                .save();
        ASSEMBLER_RECIPES.recipeBuilder("titanium_crate").EUt(16).inputItems(rodLong, Titanium, 4)
                .inputItems(plate, Titanium, 4).outputItems(TITANIUM_CRATE).duration(200).circuitMeta(1)
                .save();
        ASSEMBLER_RECIPES.recipeBuilder("tungstensteel_crate").EUt(16).inputItems(rodLong, TungstenSteel, 4)
                .inputItems(plate, TungstenSteel, 4).outputItems(TUNGSTENSTEEL_CRATE).duration(200).circuitMeta(1)
                .save();

        ASSEMBLER_RECIPES.recipeBuilder("wood_barrel").EUt(16).inputItems(ItemTags.PLANKS, 4)
                .inputItems(rodLong, Iron, 2).outputItems(WOODEN_DRUM).duration(200).circuitMeta(2)
                .save();
        ASSEMBLER_RECIPES.recipeBuilder("bronze_drum").EUt(16).inputItems(rodLong, Bronze, 2)
                .inputItems(plate, Bronze, 4).outputItems(BRONZE_DRUM).duration(200).circuitMeta(2)
                .save();
        ASSEMBLER_RECIPES.recipeBuilder("steel_drum").EUt(16).inputItems(rodLong, Steel, 2).inputItems(plate, Steel, 4)
                .outputItems(STEEL_DRUM).duration(200).circuitMeta(2)
                .save();
        ASSEMBLER_RECIPES.recipeBuilder("aluminium_drum").EUt(16).inputItems(rodLong, Aluminium, 2)
                .inputItems(plate, Aluminium, 4).outputItems(ALUMINIUM_DRUM).duration(200).circuitMeta(2)
                .save();
        ASSEMBLER_RECIPES.recipeBuilder("stainless_steel_drum").EUt(16).inputItems(rodLong, StainlessSteel, 2)
                .inputItems(plate, StainlessSteel, 4).outputItems(STAINLESS_STEEL_DRUM).duration(200).circuitMeta(2)
                .save();
        ASSEMBLER_RECIPES.recipeBuilder("gold_drum").EUt(16).inputItems(rodLong, Gold, 2).inputItems(plate, Gold, 4)
                .outputItems(GOLD_DRUM).duration(200).circuitMeta(2)
                .save();
        ASSEMBLER_RECIPES.recipeBuilder("titanium_drum").EUt(16).inputItems(rodLong, Titanium, 2)
                .inputItems(plate, Titanium, 4).outputItems(TITANIUM_DRUM).duration(200).circuitMeta(2)
                .save();
        ASSEMBLER_RECIPES.recipeBuilder("tungstensteel_drum").EUt(16).inputItems(rodLong, TungstenSteel, 2)
                .inputItems(plate, TungstenSteel, 4).outputItems(TUNGSTENSTEEL_DRUM).duration(200).circuitMeta(2)
                .save();

        ASSEMBLER_RECIPES.recipeBuilder("duct_tape_polyethylene").EUt(VA[LV]).inputItems(foil, Polyethylene, 4)
                .inputItems(CARBON_MESH).inputFluids(Polyethylene.getFluid(288)).outputItems(DUCT_TAPE).duration(100)
                .save();
        ASSEMBLER_RECIPES.recipeBuilder("duct_tape_silicone_rubber").EUt(VA[LV]).inputItems(foil, SiliconeRubber, 2)
                .inputItems(CARBON_MESH).inputFluids(Polyethylene.getFluid(288)).outputItems(DUCT_TAPE, 2).duration(100)
                .save();
        ASSEMBLER_RECIPES.recipeBuilder("duct_tape_polycaprolactam").EUt(VA[LV]).inputItems(foil, Polycaprolactam, 2)
                .inputItems(CARBON_MESH).inputFluids(Polyethylene.getFluid(L)).outputItems(DUCT_TAPE, 4).duration(100)
                .save();
        ASSEMBLER_RECIPES.recipeBuilder("duct_tape_polybenzimidazole").EUt(VA[LV]).inputItems(foil, Polybenzimidazole)
                .inputItems(CARBON_MESH).inputFluids(Polyethylene.getFluid(72)).outputItems(DUCT_TAPE, 8).duration(100)
                .save();

        VanillaRecipeHelper.addShapedRecipe(provider, "basic_tape", BASIC_TAPE.asStack(),
                " P ", "PSP", " P ", 'P', new MaterialEntry(plate, Paper), 'S', STICKY_RESIN.asItem());
        ASSEMBLER_RECIPES.recipeBuilder("basic_tape").EUt(VA[ULV]).inputItems(plate, Paper, 2).inputItems(STICKY_RESIN)
                .outputItems(BASIC_TAPE, 2)
                .duration(100).save();

        ASSEMBLER_RECIPES.recipeBuilder("fluid_cell_large_steel")
                .inputItems(plateDouble, Steel, 2)
                .inputItems(ring, Bronze, 2)
                .outputItems(FLUID_CELL_LARGE_STEEL)
                .duration(200).EUt(VA[LV])
                .save();

        ASSEMBLER_RECIPES.recipeBuilder("fluid_cell_large_aluminium")
                .inputItems(plateDouble, Aluminium, 2)
                .inputItems(ring, Silver, 2)
                .outputItems(FLUID_CELL_LARGE_ALUMINIUM)
                .duration(200).EUt(64)
                .save();

        ASSEMBLER_RECIPES.recipeBuilder("fluid_cell_large_stainless_steel")
                .inputItems(plateDouble, StainlessSteel, 3)
                .inputItems(ring, Electrum, 3)
                .outputItems(FLUID_CELL_LARGE_STAINLESS_STEEL)
                .duration(200).EUt(VA[MV])
                .save();

        ASSEMBLER_RECIPES.recipeBuilder("fluid_cell_large_titanium")
                .inputItems(plateDouble, Titanium, 3)
                .inputItems(ring, RoseGold, 3)
                .outputItems(FLUID_CELL_LARGE_TITANIUM)
                .duration(200).EUt(256)
                .save();

        ASSEMBLER_RECIPES.recipeBuilder("fluid_cell_large_tungstensteel")
                .inputItems(plateDouble, TungstenSteel, 4)
                .inputItems(ring, Platinum, 4)
                .outputItems(FLUID_CELL_LARGE_TUNGSTEN_STEEL)
                .duration(200).EUt(VA[HV])
                .save();
    }

    private static void registerBlastFurnaceRecipes() {
        BLAST_RECIPES.recipeBuilder("steel_from_iron").duration(500).EUt(VA[MV]).inputItems(ingot, Iron)
                .inputFluids(Oxygen.getFluid(200)).outputItems(ingot, Steel).chancedOutput(dust, Ash, 1111, 0)
                .blastFurnaceTemp(1000).save();
        BLAST_RECIPES.recipeBuilder("steel_from_wrought_iron").duration(300).EUt(VA[MV]).inputItems(ingot, WroughtIron)
                .inputFluids(Oxygen.getFluid(200)).outputItems(ingot, Steel).chancedOutput(dust, Ash, 1111, 0)
                .blastFurnaceTemp(1000).save();

        BLAST_RECIPES.recipeBuilder("tempered_glass_blasting")
                .inputItems(block, Glass)
                .inputFluids(Oxygen.getFluid(100))
                .outputItems(GTBlocks.CASING_TEMPERED_GLASS.asStack())
                .blastFurnaceTemp(1000)
                .duration(200).EUt(VA[MV]).save();
    }

    private static void registerDecompositionRecipes() {
        EXTRACTOR_RECIPES.recipeBuilder("raw_rubber_from_resin")
                .inputItems(STICKY_RESIN)
                .outputItems(dust, RawRubber, 3)
                .duration(150).EUt(2)
                .save();

        EXTRACTOR_RECIPES.recipeBuilder("raw_rubber_from_log").duration(300).EUt(2)
                .inputItems(GTBlocks.RUBBER_LOG.asStack())
                .outputItems(dust, RawRubber)
                .save();

        EXTRACTOR_RECIPES.recipeBuilder("raw_rubber_from_sapling").duration(300).EUt(2)
                .inputItems(GTBlocks.RUBBER_SAPLING.asStack())
                .outputItems(dust, RawRubber)
                .save();

        EXTRACTOR_RECIPES.recipeBuilder("raw_rubber_from_slime").duration(150).EUt(2)
                .inputItems(new ItemStack(Items.SLIME_BALL))
                .outputItems(dust, RawRubber, 2)
                .save();

        COMPRESSOR_RECIPES.recipeBuilder("plant_ball_from_sapling").duration(300).EUt(2).inputItems(ItemTags.SAPLINGS)
                .outputItems(PLANT_BALL).save();
        COMPRESSOR_RECIPES.recipeBuilder("plant_ball_from_wheat").duration(300).EUt(2)
                .inputItems(new ItemStack(Items.WHEAT, 8)).outputItems(PLANT_BALL).save();
        COMPRESSOR_RECIPES.recipeBuilder("plant_ball_from_potato").duration(300).EUt(2)
                .inputItems(new ItemStack(Items.POTATO, 8)).outputItems(PLANT_BALL).save();
        COMPRESSOR_RECIPES.recipeBuilder("plant_ball_from_carrot").duration(300).EUt(2)
                .inputItems(new ItemStack(Items.CARROT, 8)).outputItems(PLANT_BALL).save();
        COMPRESSOR_RECIPES.recipeBuilder("plant_ball_from_cactus").duration(300).EUt(2)
                .inputItems(new ItemStack(Blocks.CACTUS, 8)).outputItems(PLANT_BALL).save();
        COMPRESSOR_RECIPES.recipeBuilder("plant_ball_from_sugar_cane").duration(300).EUt(2)
                .inputItems(new ItemStack(Items.SUGAR_CANE, 8)).outputItems(PLANT_BALL).save();
        COMPRESSOR_RECIPES.recipeBuilder("plant_ball_from_brown_mushroom").duration(300).EUt(2)
                .inputItems(new ItemStack(Blocks.BROWN_MUSHROOM, 8)).outputItems(PLANT_BALL).save();
        COMPRESSOR_RECIPES.recipeBuilder("plant_ball_from_red_mushroom").duration(300).EUt(2)
                .inputItems(new ItemStack(Blocks.RED_MUSHROOM, 8)).outputItems(PLANT_BALL).save();
        COMPRESSOR_RECIPES.recipeBuilder("plant_ball_from_beetroot").duration(300).EUt(2)
                .inputItems(new ItemStack(Items.BEETROOT, 8)).outputItems(PLANT_BALL).save();
        COMPRESSOR_RECIPES.recipeBuilder("plant_ball_from_moss").duration(300).EUt(2)
                .inputItems(new ItemStack(Items.MOSS_BLOCK, 8)).outputItems(PLANT_BALL).save();
        COMPRESSOR_RECIPES.recipeBuilder("plant_ball_from_nether_wart").duration(300).EUt(2)
                .inputItems(new ItemStack(Items.NETHER_WART_BLOCK, 8)).outputItems(PLANT_BALL).save();
        COMPRESSOR_RECIPES.recipeBuilder("plant_ball_from_crimson_stem").duration(300).EUt(2)
                .inputItems(new ItemStack(Items.CRIMSON_STEM, 8)).outputItems(PLANT_BALL).save();
        COMPRESSOR_RECIPES.recipeBuilder("plant_ball_from_warped_stem").duration(300).EUt(2)
                .inputItems(new ItemStack(Items.WARPED_STEM, 8)).outputItems(PLANT_BALL).save();
        COMPRESSOR_RECIPES.recipeBuilder("plant_ball_from_brain_coral").duration(300).EUt(2)
                .inputItems(new ItemStack(Items.BRAIN_CORAL, 8)).outputItems(PLANT_BALL).save();
        COMPRESSOR_RECIPES.recipeBuilder("plant_ball_from_bubble_coral").duration(300).EUt(2)
                .inputItems(new ItemStack(Items.BUBBLE_CORAL, 8)).outputItems(PLANT_BALL).save();
        COMPRESSOR_RECIPES.recipeBuilder("plant_ball_from_fire_coral").duration(300).EUt(2)
                .inputItems(new ItemStack(Items.FIRE_CORAL, 8)).outputItems(PLANT_BALL).save();
        COMPRESSOR_RECIPES.recipeBuilder("plant_ball_from_tube_coral").duration(300).EUt(2)
                .inputItems(new ItemStack(Items.TUBE_CORAL, 8)).outputItems(PLANT_BALL).save();
        COMPRESSOR_RECIPES.recipeBuilder("plant_ball_from_horn_coral").duration(300).EUt(2)
                .inputItems(new ItemStack(Items.HORN_CORAL, 8)).outputItems(PLANT_BALL).save();
    }

    private static void registerRecyclingRecipes() {
        MACERATOR_RECIPES.recipeBuilder("end_stone")
                .inputItems(new ItemStack(Blocks.END_STONE))
                .outputItems(dust, Endstone)
                .chancedOutput(dust, Tungstate, 130, 30)
                .duration(150).EUt(2)
                .save();

        MACERATOR_RECIPES.recipeBuilder("netherrack")
                .inputItems(new ItemStack(Blocks.NETHERRACK))
                .outputItems(dust, Netherrack)
                .chancedOutput(nugget, Gold, 500, 120)
                .duration(150).EUt(2)
                .save();

        MACERATOR_RECIPES.recipeBuilder("obsidian")
                .inputItems(new ItemStack(Blocks.OBSIDIAN))
                .outputItems(dust, Obsidian)
                .duration(150).EUt(2)
                .save();

        MACERATOR_RECIPES.recipeBuilder("basalt")
                .inputItems(Blocks.BASALT.asItem())
                .outputItems(dust, Basalt)
                .chancedOutput(dust, Basalt, 1000, 380)
                .duration(150).EUt(2)
                .save();

        MACERATOR_RECIPES.recipeBuilder("deepslate")
                .inputItems(Blocks.DEEPSLATE.asItem())
                .outputItems(dust, Deepslate)
                .chancedOutput(dust, Thorium, 100, 40)
                .duration(150).EUt(2)
                .save();

        MACERATOR_RECIPES.recipeBuilder("red_granite")
                .inputItems(rock, GraniteRed)
                .outputItems(dust, GraniteRed)
                .chancedOutput(dust, Uranium238, 10, 5)
                .duration(150).EUt(2)
                .save();

        MACERATOR_RECIPES.recipeBuilder("andesite")
                .inputItems(Blocks.ANDESITE.asItem())
                .outputItems(dust, Andesite)
                .chancedOutput(dust, Stone, 10, 5)
                .duration(150).EUt(2)
                .save();

        MACERATOR_RECIPES.recipeBuilder("diorite")
                .inputItems(Blocks.DIORITE.asItem())
                .outputItems(dust, Diorite)
                .chancedOutput(dust, Stone, 10, 5)
                .duration(150).EUt(2)
                .save();

        MACERATOR_RECIPES.recipeBuilder("granite")
                .inputItems(Blocks.GRANITE.asItem())
                .outputItems(dust, Granite)
                .chancedOutput(dust, Stone, 10, 5)
                .duration(150).EUt(2)
                .save();

        MACERATOR_RECIPES.recipeBuilder("calcite")
                .inputItems(Blocks.CALCITE.asItem())
                .outputItems(dust, Calcite)
                .chancedOutput(dust, Stone, 10, 5)
                .duration(150).EUt(2)
                .save();

        MACERATOR_RECIPES.recipeBuilder("coral_block")
                .inputItems(CustomTags.CORAL_BLOCK_ITEMS)
                .outputItems(dust, Calcite)
                .duration(150).EUt(2)
                .save();

        MACERATOR_RECIPES.recipeBuilder("coral")
                .inputItems(CustomTags.CORAL_ITEMS)
                .outputItems(dustTiny, Calcite)
                .duration(25).EUt(2)
                .save();

        MACERATOR_RECIPES.recipeBuilder("pork_chop")
                .inputItems(new ItemStack(Items.PORKCHOP))
                .outputItems(dust, Meat)
                .chancedOutput(dust, Meat, 5000, 0)
                .outputItems(dustTiny, Bone)
                .duration(102).EUt(2).save();

        MACERATOR_RECIPES.recipeBuilder("fish")
                .inputItems(ItemTags.FISHES)
                .outputItems(dust, Meat)
                .chancedOutput(dust, Meat, 5000, 0)
                .outputItems(dustTiny, Bone)
                .duration(102).EUt(2).save();

        MACERATOR_RECIPES.recipeBuilder("chicken")
                .inputItems(new ItemStack(Items.CHICKEN))
                .outputItems(dust, Meat)
                .outputItems(dustTiny, Bone)
                .duration(102).EUt(2).save();

        MACERATOR_RECIPES.recipeBuilder("steak")
                .inputItems(new ItemStack(Items.BEEF))
                .outputItems(dust, Meat)
                .chancedOutput(dust, Meat, 5000, 0)
                .outputItems(dustTiny, Bone)
                .duration(102).EUt(2).save();

        MACERATOR_RECIPES.recipeBuilder("rabbit")
                .inputItems(new ItemStack(Items.RABBIT))
                .outputItems(dust, Meat)
                .chancedOutput(dust, Meat, 5000, 0)
                .outputItems(dustTiny, Bone)
                .duration(102).EUt(2).save();

        MACERATOR_RECIPES.recipeBuilder("mutton")
                .inputItems(new ItemStack(Items.MUTTON))
                .outputItems(dust, Meat)
                .outputItems(dustTiny, Bone)
                .duration(102).EUt(2).save();
    }

    private static void registerFluidRecipes() {
        FLUID_HEATER_RECIPES.recipeBuilder("heat_ice_to_water").duration(32).EUt(4)
                .inputFluids(Ice.getFluid(L))
                .circuitMeta(1)
                .outputFluids(Water.getFluid(L)).save();

        FLUID_SOLIDFICATION_RECIPES.recipeBuilder("solidify_gelled_toluene")
                .inputFluids(Toluene.getFluid(100))
                .notConsumable(SHAPE_MOLD_BALL)
                .outputItems(GELLED_TOLUENE)
                .duration(100).EUt(16).save();

        for (int i = 0; i < CHEMICAL_DYES.length; i++) {
            FLUID_SOLIDFICATION_RECIPES.recipeBuilder("solidify_" + CHEMICAL_DYES[i].getName() + "_to_ball")
                    .inputFluids(CHEMICAL_DYES[i].getFluid(L / 2))
                    .notConsumable(SHAPE_MOLD_BALL)
                    .outputItems(DYE_ONLY_ITEMS[i])
                    .duration(100).EUt(16).save();
        }

        FLUID_HEATER_RECIPES.recipeBuilder("heat_water_to_steam").duration(30).EUt(VA[LV])
                .inputFluids(Water.getFluid(6)).circuitMeta(1).outputFluids(Steam.getFluid(960)).save();
        FLUID_HEATER_RECIPES.recipeBuilder("heat_distilled_to_steam").duration(30).EUt(VA[LV])
                .inputFluids(DistilledWater.getFluid(6)).circuitMeta(1).outputFluids(Steam.getFluid(960))
                .save();
    }

    private static void registerSmoothRecipe(Consumer<FinishedRecipe> provider, List<ItemStack> roughStack,
                                             List<ItemStack> stoneStack) {
        for (int i = 0; i < roughStack.size(); i++) {
            ResourceLocation stoneId = ItemUtils.getIdLocation(stoneStack.get(i).getItem());
            VanillaRecipeHelper.addSmeltingRecipe(provider, "smelt_" + stoneId.getPath(), roughStack.get(i),
                    stoneStack.get(i), 0.1f);

            EXTRUDER_RECIPES.recipeBuilder("extrude_" + stoneId.getPath())
                    .inputItems(roughStack.get(i))
                    .notConsumable(SHAPE_EXTRUDER_BLOCK.asStack())
                    .outputItems(stoneStack.get(i))
                    .duration(24).EUt(8).save();
        }
    }

    private static void registerCobbleRecipe(List<ItemStack> stoneStack,
                                             List<ItemStack> cobbleStack) {
        for (int i = 0; i < stoneStack.size(); i++) {
            ResourceLocation cobbleId = ItemUtils.getIdLocation(cobbleStack.get(i).getItem());
            FORGE_HAMMER_RECIPES.recipeBuilder("hammer_" + cobbleId.getPath())
                    .inputItems(stoneStack.get(i))
                    .outputItems(cobbleStack.get(i))
                    .duration(12).EUt(4).save();
        }
    }

    private static void registerBricksRecipe(List<ItemStack> polishedStack,
                                             List<ItemStack> brickStack, MarkerMaterial color) {
        for (int i = 0; i < polishedStack.size(); i++) {
            ResourceLocation brickId = ItemUtils.getIdLocation(brickStack.get(i).getItem());
            LASER_ENGRAVER_RECIPES.recipeBuilder("engrave_" + brickId.getPath())
                    .inputItems(polishedStack.get(i))
                    .notConsumable(lens, color)
                    .outputItems(brickStack.get(i))
                    .duration(50).EUt(16).save();
        }
    }

    private static void registerMossRecipe(List<ItemStack> regularStack,
                                           List<ItemStack> mossStack) {
        for (int i = 0; i < regularStack.size(); i++) {
            ResourceLocation mossId = ItemUtils.getIdLocation(mossStack.get(i).getItem());
            MIXER_RECIPES.recipeBuilder(mossId.getPath() + "_from_moss_block")
                    .inputItems(regularStack.get(i))
                    .inputItems(new ItemStack(Blocks.MOSS_BLOCK))
                    .inputFluids(Water.getFluid(250))
                    .outputItems(mossStack.get(i))
                    .duration(40).EUt(1).save();

            MIXER_RECIPES.recipeBuilder(mossId.getPath() + "_from_vine")
                    .inputItems(regularStack.get(i))
                    .inputItems(new ItemStack(Blocks.VINE))
                    .inputFluids(Water.getFluid(250))
                    .outputItems(mossStack.get(i))
                    .duration(40).EUt(1).save();
        }
    }

    private static void registerNBTRemoval(Consumer<FinishedRecipe> provider) {
        for (MachineDefinition chest : GTMachines.SUPER_CHEST) {
            if (chest != null) {
                VanillaRecipeHelper.addShapelessNBTClearingRecipe(provider, "super_chest_nbt_" + chest.getTier(),
                        chest.asStack(), chest.asStack());
            }
        }

        for (MachineDefinition tank : GTMachines.SUPER_TANK) {
            if (tank != null) {
                VanillaRecipeHelper.addShapelessNBTClearingRecipe(provider, "super_tank_nbt_" + tank.getTier(),
                        tank.asStack(), tank.asStack());
            }
        }

        for (MachineDefinition chest : GTMachines.QUANTUM_CHEST) {
            if (chest != null) {
                VanillaRecipeHelper.addShapelessNBTClearingRecipe(provider, "quantum_chest_nbt_" + chest.getTier(),
                        chest.asStack(), chest.asStack());
            }
        }

        for (MachineDefinition tank : GTMachines.QUANTUM_TANK) {
            if (tank != null) {
                VanillaRecipeHelper.addShapelessNBTClearingRecipe(provider, "quantum_tank_nbt_" + tank.getTier(),
                        tank.asStack(), tank.asStack());
            }
        }

        // Drums
        VanillaRecipeHelper.addShapelessNBTClearingRecipe(provider, "drum_nbt_wood", GTMachines.WOODEN_DRUM.asStack(),
                GTMachines.WOODEN_DRUM.asStack());
        VanillaRecipeHelper.addShapelessNBTClearingRecipe(provider, "drum_nbt_bronze", GTMachines.BRONZE_DRUM.asStack(),
                GTMachines.BRONZE_DRUM.asStack());
        VanillaRecipeHelper.addShapelessNBTClearingRecipe(provider, "drum_nbt_steel", GTMachines.STEEL_DRUM.asStack(),
                GTMachines.STEEL_DRUM.asStack());
        VanillaRecipeHelper.addShapelessNBTClearingRecipe(provider, "drum_nbt_aluminium",
                GTMachines.ALUMINIUM_DRUM.asStack(), GTMachines.ALUMINIUM_DRUM.asStack());
        VanillaRecipeHelper.addShapelessNBTClearingRecipe(provider, "drum_nbt_stainless_steel",
                GTMachines.STAINLESS_STEEL_DRUM.asStack(), GTMachines.STAINLESS_STEEL_DRUM.asStack());
        VanillaRecipeHelper.addShapelessNBTClearingRecipe(provider, "drum_nbt_gold", GTMachines.GOLD_DRUM.asStack(),
                GTMachines.GOLD_DRUM.asStack());
        VanillaRecipeHelper.addShapelessNBTClearingRecipe(provider, "drum_nbt_titanium",
                GTMachines.TITANIUM_DRUM.asStack(), GTMachines.TITANIUM_DRUM.asStack());
        VanillaRecipeHelper.addShapelessNBTClearingRecipe(provider, "drum_nbt_tungstensteel",
                GTMachines.TUNGSTENSTEEL_DRUM.asStack(), GTMachines.TUNGSTENSTEEL_DRUM.asStack());

        // Cells
        VanillaRecipeHelper.addShapedNBTClearingRecipe(provider, "cell_nbt_regular", FLUID_CELL.asStack(), " C", "  ",
                'C', FLUID_CELL.asStack());
        VanillaRecipeHelper.addShapedNBTClearingRecipe(provider, "cell_nbt_universal", FLUID_CELL_UNIVERSAL.asStack(),
                " C", "  ", 'C', FLUID_CELL_UNIVERSAL.asStack());
        VanillaRecipeHelper.addShapelessNBTClearingRecipe(provider, "cell_nbt_steel", FLUID_CELL_LARGE_STEEL.asStack(),
                FLUID_CELL_LARGE_STEEL.asStack());
        VanillaRecipeHelper.addShapelessNBTClearingRecipe(provider, "cell_nbt_aluminium",
                FLUID_CELL_LARGE_ALUMINIUM.asStack(), FLUID_CELL_LARGE_ALUMINIUM.asStack());
        VanillaRecipeHelper.addShapelessNBTClearingRecipe(provider, "cell_nbt_stainless_steel",
                FLUID_CELL_LARGE_STAINLESS_STEEL.asStack(), FLUID_CELL_LARGE_STAINLESS_STEEL.asStack());
        VanillaRecipeHelper.addShapelessNBTClearingRecipe(provider, "cell_nbt_titanium",
                FLUID_CELL_LARGE_TITANIUM.asStack(), FLUID_CELL_LARGE_TITANIUM.asStack());
        VanillaRecipeHelper.addShapelessNBTClearingRecipe(provider, "cell_nbt_tungstensteel",
                FLUID_CELL_LARGE_TUNGSTEN_STEEL.asStack(), FLUID_CELL_LARGE_TUNGSTEN_STEEL.asStack());
        VanillaRecipeHelper.addShapelessNBTClearingRecipe(provider, "cell_vial_nbt", FLUID_CELL_GLASS_VIAL.asStack(),
                FLUID_CELL_GLASS_VIAL.asStack());

        // Data Items
        VanillaRecipeHelper.addShapelessNBTClearingRecipe(provider, "data_stick_nbt", TOOL_DATA_STICK.asStack(),
                TOOL_DATA_STICK.asStack());
        VanillaRecipeHelper.addShapelessNBTClearingRecipe(provider, "data_orb_nbt", TOOL_DATA_ORB.asStack(),
                TOOL_DATA_ORB.asStack());
        VanillaRecipeHelper.addShapelessNBTClearingRecipe(provider, "data_module_nbt", TOOL_DATA_MODULE.asStack(),
                TOOL_DATA_MODULE.asStack());

        // Jetpacks
        VanillaRecipeHelper.addShapelessRecipe(provider, "fluid_jetpack_clear", LIQUID_FUEL_JETPACK.asStack(),
                LIQUID_FUEL_JETPACK.asStack());

        VanillaRecipeHelper.addShapelessRecipe(provider, "item_filter_nbt", ITEM_FILTER.asStack(),
                ITEM_FILTER.asStack());
        VanillaRecipeHelper.addShapelessRecipe(provider, "fluid_filter_nbt", FLUID_FILTER.asStack(),
                FLUID_FILTER.asStack());
        VanillaRecipeHelper.addShapelessRecipe(provider, "item_tag_filter_nbt", TAG_FILTER.asStack(),
                TAG_FILTER.asStack());
        VanillaRecipeHelper.addShapelessRecipe(provider, "fluid_tag_filter_nbt", TAG_FLUID_FILTER.asStack(),
                TAG_FLUID_FILTER.asStack());
    }

    private static void registerHatchConversion(Consumer<FinishedRecipe> provider) {
        for (int i = 0; i < FLUID_IMPORT_HATCH.length; i++) {
            if (FLUID_IMPORT_HATCH[i] != null && FLUID_EXPORT_HATCH[i] != null) {

                VanillaRecipeHelper.addShapedRecipe(provider,
                        "fluid_hatch_output_to_input_" + FLUID_IMPORT_HATCH[i].getTier(),
                        FLUID_IMPORT_HATCH[i].asStack(),
                        "d", "B", 'B', FLUID_EXPORT_HATCH[i].asStack());
                VanillaRecipeHelper.addShapedRecipe(provider,
                        "fluid_hatch_input_to_output_" + FLUID_EXPORT_HATCH[i].getTier(),
                        FLUID_EXPORT_HATCH[i].asStack(),
                        "d", "B", 'B', FLUID_IMPORT_HATCH[i].asStack());
            }
        }
        for (int i = 0; i < ITEM_IMPORT_BUS.length; i++) {
            if (ITEM_IMPORT_BUS[i] != null && ITEM_EXPORT_BUS[i] != null) {

                VanillaRecipeHelper.addShapedRecipe(provider,
                        "item_bus_output_to_input_" + ITEM_IMPORT_BUS[i].getTier(), ITEM_IMPORT_BUS[i].asStack(),
                        "d", "B", 'B', ITEM_EXPORT_BUS[i].asStack());
                VanillaRecipeHelper.addShapedRecipe(provider,
                        "item_bus_input_to_output_" + ITEM_EXPORT_BUS[i].getTier(), ITEM_EXPORT_BUS[i].asStack(),
                        "d", "B", 'B', ITEM_IMPORT_BUS[i].asStack());
            }
        }

        for (int tier : GTMachineUtils.MULTI_HATCH_TIERS) {
            var tierName = VN[tier].toLowerCase(Locale.ROOT);

            var importHatch4x = FLUID_IMPORT_HATCH_4X[tier];
            var exportHatch4x = FLUID_EXPORT_HATCH_4X[tier];
            var importHatch9x = FLUID_IMPORT_HATCH_9X[tier];
            var exportHatch9x = FLUID_EXPORT_HATCH_9X[tier];

            VanillaRecipeHelper.addShapedRecipe(
                    provider, "fluid_hatch_4x_output_to_input_" + tierName,
                    importHatch4x.asStack(), "d", "B",
                    'B', exportHatch4x.asStack());
            VanillaRecipeHelper.addShapedRecipe(
                    provider, "fluid_hatch_4x_input_to_output_" + tierName,
                    exportHatch4x.asStack(), "d", "B",
                    'B', importHatch4x.asStack());

            VanillaRecipeHelper.addShapedRecipe(
                    provider, "fluid_hatch_9x_output_to_input_" + tierName,
                    importHatch9x.asStack(), "d", "B",
                    'B', exportHatch9x.asStack());
            VanillaRecipeHelper.addShapedRecipe(
                    provider, "fluid_hatch_9x_input_to_output_" + tierName,
                    exportHatch9x.asStack(), "d", "B",
                    'B', importHatch9x.asStack());
        }

        for (int tier : GTMachineUtils.DUAL_HATCH_TIERS) {
            var tierName = VN[tier].toLowerCase(Locale.ROOT);

            var inputBuffer = DUAL_IMPORT_HATCH[tier];
            var outputBuffer = DUAL_EXPORT_HATCH[tier];

            VanillaRecipeHelper.addShapedRecipe(
                    provider,
                    "dual_hatch_output_to_input_" + tierName,
                    inputBuffer.asStack(),
                    "d",
                    "B",
                    'B',
                    outputBuffer.asStack());
            VanillaRecipeHelper.addShapedRecipe(
                    provider,
                    "dual_hatch_input_to_output_" + tierName,
                    outputBuffer.asStack(),
                    "d",
                    "B",
                    'B',
                    inputBuffer.asStack());
        }

        // Steam
        VanillaRecipeHelper.addShapedRecipe(provider, "steam_bus_output_to_input", STEAM_EXPORT_BUS.asStack(),
                "d", "B", 'B', STEAM_IMPORT_BUS.asStack());
        VanillaRecipeHelper.addShapedRecipe(provider, "steam_bus_input_to_output", STEAM_IMPORT_BUS.asStack(),
                "d", "B", 'B', STEAM_EXPORT_BUS.asStack());

        if (GTCEu.Mods.isAE2Loaded()) {
            VanillaRecipeHelper.addShapedRecipe(provider, "me_fluid_hatch_output_to_input",
                    GTAEMachines.FLUID_IMPORT_HATCH_ME.asStack(), "d", "B", 'B',
                    GTAEMachines.FLUID_EXPORT_HATCH_ME.asStack());
            VanillaRecipeHelper.addShapedRecipe(provider, "me_fluid_hatch_input_to_output",
                    GTAEMachines.FLUID_EXPORT_HATCH_ME.asStack(), "d", "B", 'B',
                    GTAEMachines.FLUID_IMPORT_HATCH_ME.asStack());
            VanillaRecipeHelper.addShapedRecipe(provider, "me_item_bus_output_to_input",
                    GTAEMachines.ITEM_IMPORT_BUS_ME.asStack(), "d", "B", 'B',
                    GTAEMachines.ITEM_EXPORT_BUS_ME.asStack());
            VanillaRecipeHelper.addShapedRecipe(provider, "me_item_bus_input_to_output",
                    GTAEMachines.ITEM_EXPORT_BUS_ME.asStack(), "d", "B", 'B',
                    GTAEMachines.ITEM_IMPORT_BUS_ME.asStack());
        }
    }
}
