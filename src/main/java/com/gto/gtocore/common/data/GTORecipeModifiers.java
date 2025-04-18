package com.gto.gtocore.common.data;

import com.gto.gtocore.api.capability.recipe.ManaRecipeCapability;
import com.gto.gtocore.api.machine.feature.IOverclockConfigMachine;
import com.gto.gtocore.api.machine.feature.IUpgradeMachine;
import com.gto.gtocore.api.machine.feature.multiblock.ICoilMachine;
import com.gto.gtocore.api.machine.trait.IEnhancedRecipeLogic;
import com.gto.gtocore.api.recipe.ContentBuilder;
import com.gto.gtocore.api.recipe.IGTRecipe;
import com.gto.gtocore.api.recipe.RecipeRunnerHelper;
import com.gto.gtocore.common.machine.multiblock.generator.GeneratorArrayMachine;
import com.gto.gtocore.utils.GTOUtils;
import com.gto.gtocore.utils.MachineUtils;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.block.ICoilType;
import com.gregtechceu.gtceu.api.capability.recipe.EURecipeCapability;
import com.gregtechceu.gtceu.api.capability.recipe.IRecipeCapabilityHolder;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.SimpleGeneratorMachine;
import com.gregtechceu.gtceu.api.machine.feature.IOverclockMachine;
import com.gregtechceu.gtceu.api.machine.feature.IRecipeLogicMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.OverclockingLogic;
import com.gregtechceu.gtceu.api.recipe.RecipeHelper;
import com.gregtechceu.gtceu.api.recipe.content.Content;
import com.gregtechceu.gtceu.api.recipe.content.ContentModifier;
import com.gregtechceu.gtceu.api.recipe.modifier.ModifierFunction;
import com.gregtechceu.gtceu.api.recipe.modifier.ParallelLogic;
import com.gregtechceu.gtceu.api.recipe.modifier.RecipeModifier;
import com.gregtechceu.gtceu.api.recipe.modifier.RecipeModifierList;
import com.gregtechceu.gtceu.common.machine.multiblock.steam.LargeBoilerMachine;

import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;

import javax.annotation.ParametersAreNonnullByDefault;

@SuppressWarnings("unused")
@ParametersAreNonnullByDefault
public interface GTORecipeModifiers {

    RecipeModifier PARALLELIZABLE_OVERCLOCK = new RecipeModifierList((machine, r) -> recipe -> overclocking(machine, hatchParallel(machine, recipe)));
    RecipeModifier PARALLELIZABLE_PERFECT_OVERCLOCK = new RecipeModifierList((machine, r) -> recipe -> perfectOverclocking(machine, hatchParallel(machine, recipe)));

    RecipeModifier GCYM_OVERCLOCKING = new RecipeModifierList((machine, r) -> recipe -> overclocking(machine, hatchParallel(machine, recipe), false, false, 0.8, 0.6));

    Set<RecipeModifier> TRY_AGAIN = Set.of(PARALLELIZABLE_OVERCLOCK, PARALLELIZABLE_PERFECT_OVERCLOCK, GCYM_OVERCLOCKING);

    RecipeModifier GENERATOR_OVERCLOCKING = (machine, r) -> recipe -> generatorOverclocking(machine, recipe);
    RecipeModifier PERFECT_OVERCLOCKING = (machine, r) -> recipe -> perfectOverclocking(machine, recipe);
    RecipeModifier OVERCLOCKING = (machine, r) -> recipe -> overclocking(machine, recipe);

    RecipeModifier HATCH_PARALLEL = (machine, r) -> recipe -> hatchParallel(machine, recipe);

    static RecipeModifier overclocking(boolean perfect, double reductionEUt, double reductionDuration) {
        return (machine, r) -> recipe -> overclocking(machine, recipe, perfect, false, reductionEUt, reductionDuration);
    }

    static RecipeModifier accurateParallel(int parallel) {
        return (machine, r) -> recipe -> accurateParallel(machine, recipe, parallel);
    }

    static RecipeModifier recipeReduction(double reductionEUt, double reductionDuration) {
        return (machine, r) -> recipe -> recipeReduction(machine, recipe, reductionEUt, reductionDuration);
    }

    static ModifierFunction simpleGeneratorMachineModifier(MetaMachine machine, GTRecipe r) {
        if (machine instanceof SimpleGeneratorMachine generator) {
            return recipe -> {
                var EUt = RecipeHelper.getOutputEUt(recipe);
                if (EUt > 0) {
                    recipe = accurateParallel(machine, recipe, (int) (generator.getOverclockVoltage() / EUt));
                    recipe.duration = recipe.duration * GeneratorArrayMachine.getEfficiency(generator.getRecipeType(), generator.getTier()) / 100;
                    if (recipe.duration < 1) return null;
                    return recipe;
                }
                return recipe;
            };
        }
        return ModifierFunction.NULL;
    }

    static ModifierFunction largeBoilerModifier(MetaMachine machine, GTRecipe r) {
        return recipe -> {
            if (machine instanceof LargeBoilerMachine largeBoilerMachine) {
                int temperature = recipe.data.getInt("temperature");
                if (temperature > 0 && largeBoilerMachine.getCurrentTemperature() < temperature) return null;
                double duration = recipe.duration * 1600.0D / largeBoilerMachine.maxTemperature;
                if (duration < 1) {
                    recipe = accurateParallel(machine, recipe, (int) (1 / duration));
                }
                if (largeBoilerMachine.getThrottle() < 100) {
                    duration = duration * 100 / largeBoilerMachine.getThrottle();
                }
                recipe.duration = (int) duration;
            }
            return recipe;
        };
    }

    static ModifierFunction polymerizationOverclock(MetaMachine machine, GTRecipe r) {
        return coilReductionOverclock(machine, r, false);
    }

    static ModifierFunction largeChemicaloroverclock(MetaMachine machine, GTRecipe r) {
        return recipe -> {
            if (machine instanceof WorkableElectricMultiblockMachine multiblockMachine && multiblockMachine.getMultiblockState().getMatchContext().get("CoilType") instanceof ICoilType coil && coil.getTier() >= multiblockMachine.getTier()) {
                return perfectOverclocking(machine, recipe);
            }
            return overclocking(machine, recipe);
        };
    }

    static ModifierFunction chemicalPlantOverclock(MetaMachine machine, GTRecipe r) {
        return coilReductionOverclock(machine, r, true);
    }

    private static ModifierFunction coilReductionOverclock(MetaMachine machine, GTRecipe r, boolean perfect) {
        return recipe -> {
            if (machine instanceof ICoilMachine coilMachine) {
                return overclocking(machine, hatchParallel(machine, recipe), perfect, false, (1.0 - coilMachine.getCoilTier() * 0.05), (1.0 - coilMachine.getCoilTier() * 0.05));
            }
            return null;
        };
    }

    static ModifierFunction crackerOverclock(MetaMachine machine, GTRecipe r) {
        return recipe -> {
            if (machine instanceof ICoilMachine coilMachine) {
                return overclocking(machine, recipe, false, false, (1.0 - coilMachine.getCoilTier() * 0.1), 1);
            }
            return null;
        };
    }

    static ModifierFunction pyrolyseOvenOverclock(MetaMachine machine, GTRecipe r) {
        return recipe -> {
            if (machine instanceof ICoilMachine coilMachine) {
                if (coilMachine.getCoilTier() == 0) {
                    return overclocking(machine, recipe, false, false, 1, 1.33);
                } else {
                    return overclocking(machine, recipe, false, false, 1, 2.0 / (coilMachine.getCoilTier() + 1));
                }
            }
            return null;
        };
    }

    static ModifierFunction ebfOverclock(MetaMachine machine, GTRecipe r) {
        return recipe -> {
            if (machine instanceof ICoilMachine coilMachine && machine instanceof IOverclockMachine overclockMachine) {
                int temperature = coilMachine.getCoilType().getCoilTemperature() + (100 * Math.max(0, ((WorkableElectricMultiblockMachine) coilMachine).getTier() - GTValues.MV));
                int recipeTemp = recipe.data.getInt("ebf_temp");
                if (recipeTemp > temperature) return null;
                long recipeVoltage = (long) (RecipeHelper.getInputEUt(recipe) * OverclockingLogic.getCoilEUtDiscount(recipeTemp, temperature));
                int duration = recipe.duration;
                if (machine instanceof IUpgradeMachine upgradeMachine) {
                    recipeVoltage = (long) (recipeVoltage * upgradeMachine.gtocore$getEnergy());
                    duration = (int) (duration * upgradeMachine.gtocore$getSpeed());
                }
                long maxVoltage = overclockMachine.getOverclockVoltage();
                int amountPerfectOC = Math.max(0, (temperature - recipeTemp) / 900);
                long overclockVoltage;
                int limit;
                if (machine instanceof IOverclockConfigMachine configMachine) {
                    limit = configMachine.gTOCore$getOCLimit();
                } else {
                    limit = 20;
                }
                int ocLevel = 0;
                while (duration > limit) {
                    int factor = amountPerfectOC > 0 ? 1 : 2;
                    overclockVoltage = recipeVoltage << factor;
                    if (overclockVoltage > maxVoltage) break;
                    amountPerfectOC--;
                    recipeVoltage = overclockVoltage;
                    duration >>= 1;
                    ocLevel += factor;
                }
                recipe.ocLevel = ocLevel / 2;
                recipe.duration = duration;
                recipe.tickInputs.put(EURecipeCapability.CAP, List.of(ContentBuilder.builderEU(recipeVoltage)));
            }
            return recipe;
        };
    }

    static GTRecipe hatchParallel(MetaMachine machine, GTRecipe recipe) {
        return accurateParallel(machine, recipe, MachineUtils.getHatchParallel(machine));
    }

    static GTRecipe accurateParallel(MetaMachine machine, GTRecipe recipe, int maxParallel) {
        if (maxParallel > 1 && machine instanceof IRecipeLogicMachine holder && holder.getRecipeLogic() instanceof IEnhancedRecipeLogic logic) {
            if (logic.gtocore$getlastParallel() == maxParallel) {
                GTRecipe copied = recipe.copy(ContentModifier.multiplier(maxParallel), false);
                if (RecipeRunnerHelper.matchRecipe(holder, copied)) {
                    copied.parallels = maxParallel;
                    return copied;
                }
            }
            maxParallel = ParallelLogic.getParallelAmount(machine, recipe, maxParallel);
            logic.gtocore$cleanParallelMap();
            return matchParallel(holder, recipe, maxParallel);
        }
        return recipe;
    }

    private static GTRecipe matchParallel(IRecipeCapabilityHolder holder, GTRecipe recipe, int parallel) {
        if (parallel > 1) {
            GTRecipe copied = recipe.copy(ContentModifier.multiplier(parallel), false);
            if (RecipeRunnerHelper.matchRecipe(holder, copied)) {
                copied.parallels *= parallel;
                return copied;
            } else {
                return matchParallel(holder, recipe, parallel / 2);
            }
        }
        return recipe;
    }

    static GTRecipe recipeReduction(MetaMachine machine, GTRecipe recipe, double reductionEUt, double reductionDuration) {
        if (reductionEUt != 1) {
            recipe.tickInputs.put(EURecipeCapability.CAP, List.of(ContentBuilder.builderEU((long) Math.max(1, RecipeHelper.getInputEUt(recipe) * reductionEUt))));
        }
        if (reductionDuration != 1) {
            recipe.duration = (int) Math.max(1, recipe.duration * reductionDuration);
        }
        return recipe;
    }

    static GTRecipe laserLossOverclocking(MetaMachine machine, @Nullable GTRecipe recipe) {
        return overclocking(machine, recipe, false, true, false, 1, 1);
    }

    static GTRecipe generatorOverclocking(MetaMachine machine, @Nullable GTRecipe recipe) {
        return overclocking(machine, recipe, true, true, 1, 1);
    }

    static GTRecipe perfectOverclocking(MetaMachine machine, @Nullable GTRecipe recipe) {
        return overclocking(machine, recipe, true, false, 1, 1);
    }

    static GTRecipe overclocking(MetaMachine machine, @Nullable GTRecipe recipe) {
        return overclocking(machine, recipe, false, false, 1, 1);
    }

    static GTRecipe overclocking(MetaMachine machine, @Nullable GTRecipe recipe, boolean perfect, boolean generator, double reductionEUt, double reductionDuration) {
        return overclocking(machine, recipe, perfect, false, generator, reductionEUt, reductionDuration);
    }

    static GTRecipe overclocking(MetaMachine machine, @Nullable GTRecipe recipe, boolean perfect, boolean laserLoss, boolean generator, double reductionEUt, double reductionDuration) {
        if (machine instanceof IOverclockMachine overclockMachine) {
            if (machine instanceof IUpgradeMachine upgradeMachine) {
                reductionEUt = reductionEUt * upgradeMachine.gtocore$getEnergy();
                reductionDuration = reductionDuration * upgradeMachine.gtocore$getSpeed();
            }
            return overclocking(machine, recipe, (long) ((generator ? RecipeHelper.getOutputEUt(recipe) : RecipeHelper.getInputEUt(recipe)) * reductionEUt), overclockMachine.getOverclockVoltage(), perfect, laserLoss, generator, reductionDuration);
        }
        return recipe;
    }

    static GTRecipe overclocking(MetaMachine machine, @Nullable GTRecipe recipe, long recipeVoltage, long maxVoltage, boolean perfect, boolean laserLoss, boolean generator, double reductionDuration) {
        if (recipe instanceof IGTRecipe igtRecipe) {
            int duration = (int) (recipe.duration * reductionDuration);
            int factor = (perfect || igtRecipe.gtocore$perfect()) ? 1 : 2;
            int limit;
            if (machine instanceof IOverclockConfigMachine configMachine) {
                limit = configMachine.gTOCore$getOCLimit();
            } else {
                limit = 2;
            }
            int ocLevel = 0;
            while (duration > limit) {
                long overclockVoltage = recipeVoltage << factor;
                if (overclockVoltage > maxVoltage) break;
                if (laserLoss) duration = duration * 5 / 4;
                recipeVoltage = overclockVoltage;
                duration >>= 1;
                ocLevel += factor;
            }
            recipe.ocLevel = ocLevel / 2;
            recipe.duration = duration;
            List<Content> content = List.of(ContentBuilder.builderEU(recipeVoltage));
            if (generator) {
                recipe.tickOutputs.put(EURecipeCapability.CAP, content);
            } else {
                recipe.tickInputs.put(EURecipeCapability.CAP, content);
            }
        }
        return recipe;
    }

    static GTRecipe externalEnergyOverclocking(MetaMachine machine, @Nullable GTRecipe recipe, long recipeVoltage, long maxVoltage, boolean perfect, double reductionEUt, double reductionDuration) {
        return overclocking(machine, recipe, recipeVoltage, (long) (maxVoltage * reductionEUt), perfect, false, false, reductionDuration);
    }

    static GTRecipe manaOverclocking(MetaMachine machine, @Nullable GTRecipe recipe, long maxMana, boolean perfect, double reductionManat, double reductionDuration) {
        if (recipe != null) {
            long recipeMana = GTOUtils.getInputMANAt(recipe);
            int duration = (int) (recipe.duration * reductionDuration);
            int factor = perfect ? 1 : 2;
            int limit;
            if (machine instanceof IOverclockConfigMachine configMachine) {
                limit = configMachine.gTOCore$getOCLimit();
            } else {
                limit = 20;
            }
            int ocLevel = 0;
            while (duration > limit) {
                long overclockVoltage = recipeMana << factor;
                if (overclockVoltage > maxMana) break;
                recipeMana = overclockVoltage;
                duration >>= 1;
                ocLevel += factor;
            }
            recipe.ocLevel = ocLevel / 2;
            recipe.duration = duration;
            List<Content> content = List.of(ContentBuilder.builderEU(recipeMana));
            recipe.tickInputs.put(ManaRecipeCapability.CAP, content);
        }
        return recipe;
    }

    class Overclocking implements OverclockingLogic {

        private final boolean perfect;

        public Overclocking(boolean perfect) {
            this.perfect = perfect;
        }

        @Override
        public OCResult runOverclockingLogic(OCParams ocParams, long maxVoltage) {
            int duration = ocParams.duration();
            long recipeVoltage = ocParams.eut();
            int factor = perfect ? 1 : 2;
            int ocLevel = 0;
            while (duration > 2) {
                long overclockVoltage = recipeVoltage << factor;
                if (overclockVoltage > maxVoltage) break;
                recipeVoltage = overclockVoltage;
                duration >>= 1;
                ocLevel += factor;
            }
            return new OCResult((double) recipeVoltage / ocParams.eut(), (double) duration / ocParams.duration(), ocLevel / 2, ocParams.maxParallels());
        }
    }
}
