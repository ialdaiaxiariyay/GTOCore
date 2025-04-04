package com.gto.gtocore.common.data.machines;

import com.gto.gtocore.api.misc.PlanetManagement;
import com.gto.gtocore.common.data.GTORecipeModifiers;
import com.gto.gtocore.common.data.GTORecipeTypes;
import com.gto.gtocore.utils.MachineUtils;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility;
import com.gregtechceu.gtceu.api.pattern.FactoryBlockPattern;
import com.gregtechceu.gtceu.api.pattern.Predicates;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.client.util.TooltipHelper;
import com.gregtechceu.gtceu.common.data.GTMachines;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;
import com.gregtechceu.gtceu.common.data.machines.GTMachineUtils;
import com.gregtechceu.gtceu.common.data.machines.GTMultiMachines;
import com.gregtechceu.gtceu.utils.memoization.GTMemoizer;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.UUID;

import static com.gregtechceu.gtceu.api.pattern.Predicates.*;
import static com.gregtechceu.gtceu.common.data.GTBlocks.*;
import static com.gregtechceu.gtceu.common.data.machines.GTMultiMachines.PRIMITIVE_BLAST_FURNACE;
import static com.gto.gtocore.common.data.GTOMachines.PRIMITIVE_BLAST_FURNACE_HATCH;

public interface GTMachineModify {

    static void init() {
        GTMultiMachines.MULTI_SMELTER.setRecipeTypes(new GTRecipeType[] { GTRecipeTypes.FURNACE_RECIPES });
        GTMultiMachines.MULTI_SMELTER.setTooltipBuilder((itemStack, components) -> components.add(Component.translatable("gtceu.machine.available_recipe_map_1.tooltip", Component.translatable("gtceu.electric_furnace"))));
        GTMultiMachines.LARGE_CHEMICAL_REACTOR.setRecipeTypes(new GTRecipeType[] { GTORecipeTypes.CHEMICAL });
        GTMultiMachines.LARGE_CHEMICAL_REACTOR.setTooltipBuilder((a, b) -> b.add(Component.translatable("gtceu.machine.perfect_oc").withStyle(style -> style.withColor(TooltipHelper.BLINKING_RED.getCurrent()))));
        GTMultiMachines.LARGE_BOILER_BRONZE.setRecipeModifier(GTORecipeModifiers::largeBoilerModifier);
        GTMultiMachines.LARGE_BOILER_STEEL.setRecipeModifier(GTORecipeModifiers::largeBoilerModifier);
        GTMultiMachines.LARGE_BOILER_TITANIUM.setRecipeModifier(GTORecipeModifiers::largeBoilerModifier);
        GTMultiMachines.LARGE_BOILER_TUNGSTENSTEEL.setRecipeModifier(GTORecipeModifiers::largeBoilerModifier);
        GTMultiMachines.ELECTRIC_BLAST_FURNACE.setRecipeModifier(GTORecipeModifiers::ebfOverclock);
        GTMultiMachines.PYROLYSE_OVEN.setRecipeModifier(GTORecipeModifiers::pyrolyseOvenOverclock);
        GTMultiMachines.CRACKER.setRecipeModifier(GTORecipeModifiers::crackerOverclock);
        GTMultiMachines.LARGE_CHEMICAL_REACTOR.setRecipeModifier(GTORecipeModifiers::largeChemicaloroverclock);
        GTMultiMachines.IMPLOSION_COMPRESSOR.setRecipeModifier(GTORecipeModifiers.OVERCLOCKING);
        GTMultiMachines.DISTILLATION_TOWER.setRecipeModifier(GTORecipeModifiers.OVERCLOCKING);
        GTMultiMachines.VACUUM_FREEZER.setRecipeModifier(GTORecipeModifiers.OVERCLOCKING);
        GTMultiMachines.ASSEMBLY_LINE.setRecipeModifier(GTORecipeModifiers.OVERCLOCKING);
        GTMultiMachines.PRIMITIVE_BLAST_FURNACE.setPatternFactory(GTMemoizer.memoize(() -> FactoryBlockPattern.start()
                .aisle("XXX", "XXX", "XXX", "XXX")
                .aisle("XXX", "X#X", "X#X", "X#X")
                .aisle("XXX", "XYX", "XXX", "XXX")
                .where('X', blocks(CASING_PRIMITIVE_BRICKS.get()).or(blocks(PRIMITIVE_BLAST_FURNACE_HATCH.get()).setMaxGlobalLimited(5)))
                .where('#', air())
                .where('Y', controller(blocks(PRIMITIVE_BLAST_FURNACE.getBlock())))
                .build()));

        GTMultiMachines.LARGE_BOILER_BRONZE.setPatternFactory(GTMemoizer.memoize(() -> FactoryBlockPattern.start()
                .aisle("XXX", "CCC", "CCC", "CCC")
                .aisle("XXX", "CPC", "CPC", "CCC")
                .aisle("XXX", "CSC", "CCC", "CCC")
                .where('S', Predicates.controller(blocks(GTMultiMachines.LARGE_BOILER_BRONZE.getBlock())))
                .where('P', blocks(CASING_BRONZE_PIPE.get()))
                .where('X', blocks(FIREBOX_BRONZE.get()).setMinGlobalLimited(5)
                        .or(Predicates.abilities(PartAbility.IMPORT_FLUIDS).setMinGlobalLimited(1).setPreviewCount(1))
                        .or(Predicates.abilities(PartAbility.IMPORT_ITEMS).setMaxGlobalLimited(1).setPreviewCount(1)))
                .where('C', blocks(CASING_BRONZE_BRICKS.get()).setMinGlobalLimited(20).or(Predicates.abilities(PartAbility.EXPORT_FLUIDS).setMinGlobalLimited(1).setPreviewCount(1)))
                .build()));

        for (int tier : GTMachineUtils.ELECTRIC_TIERS) {
            if (tier > GTValues.LV) {
                GTMachines.SCANNER[tier].setOnWorking(machine -> {
                    if (machine.getProgress() == machine.getMaxProgress() - 1) {
                        MachineUtils.forEachInputItems(machine, itemStack -> {
                            CompoundTag tag = itemStack.getTag();
                            if (tag != null) {
                                String planet = tag.getString("planet");
                                if (!planet.isEmpty()) {
                                    UUID uuid = tag.getUUID("uuid");
                                    PlanetManagement.unlock(uuid, new ResourceLocation(planet));
                                    itemStack.setCount(0);
                                    return true;
                                }
                            }
                            return false;
                        });
                    }
                    return true;
                });
            }
        }
    }
}
