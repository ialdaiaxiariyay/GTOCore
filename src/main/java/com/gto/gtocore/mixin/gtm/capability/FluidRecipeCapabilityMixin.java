package com.gto.gtocore.mixin.gtm.capability;

import com.gto.gtocore.api.fluid.StrictFluidStack;
import com.gto.gtocore.api.machine.trait.IEnhancedRecipeLogic;
import com.gto.gtocore.api.machine.trait.IPatternBufferRecipeHandler;
import com.gto.gtocore.api.recipe.MapFluid;
import com.gto.gtocore.mixin.gtm.api.recipe.FluidIngredientAccessor;

import com.gregtechceu.gtceu.api.capability.recipe.*;
import com.gregtechceu.gtceu.api.machine.feature.IRecipeLogicMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.content.Content;
import com.gregtechceu.gtceu.api.recipe.ingredient.FluidIngredient;
import com.gregtechceu.gtceu.api.recipe.lookup.AbstractMapIngredient;
import com.gregtechceu.gtceu.api.recipe.lookup.MapFluidTagIngredient;

import net.minecraftforge.fluids.FluidStack;

import it.unimi.dsi.fastutil.objects.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.List;
import java.util.Map;

@Mixin(FluidRecipeCapability.class)
public class FluidRecipeCapabilityMixin {

    /**
     * @author .
     * @reason .
     */
    @Overwrite(remap = false)
    public List<AbstractMapIngredient> convertToMapIngredient(Object obj) {
        List<AbstractMapIngredient> ingredients = new ObjectArrayList<>(1);
        if (obj instanceof FluidIngredient ingredient) {
            for (FluidIngredient.Value value : ingredient.values) {
                if (value instanceof FluidIngredient.TagValue tagValue) {
                    ingredients.add(new MapFluidTagIngredient(tagValue.getTag()));
                } else {
                    ingredients.add(new MapFluid(((FluidIngredientAccessor) value).getFluid(), ingredient.getNbt()));
                }
            }
        } else if (obj instanceof FluidStack stack) {
            ingredients.add(new MapFluid(stack.getFluid(), stack.getTag()));
        }
        return ingredients;
    }

    /**
     * @author .
     * @reason 优化性能，支持隔离
     */
    @Overwrite(remap = false)
    public int getMaxParallelRatio(IRecipeCapabilityHolder holder, GTRecipe recipe, int parallelAmount) {
        if (holder instanceof IRecipeLogicMachine machine && machine.getRecipeLogic() instanceof IEnhancedRecipeLogic recipeLogic) {
            Object2LongOpenHashMap<FluidStack> map = recipeLogic.gtocore$getParallelFluidMap();
            Object2LongOpenHashMap<StrictFluidStack> ingredientStacks = recipeLogic.gtocore$getFluidIngredientStacks();

            var recipeHandlerList = holder.getCapabilitiesFlat(IO.IN, FluidRecipeCapability.CAP);

            for (IRecipeHandler<?> container : recipeHandlerList) {
                boolean patternBuffer = container instanceof IPatternBufferRecipeHandler;
                Object2LongOpenHashMap<FluidStack> fluidMap;
                if (patternBuffer) {
                    fluidMap = ((IPatternBufferRecipeHandler) container).getFluidMap();
                } else {
                    fluidMap = recipeLogic.gtocore$getFluidMap();
                    for (Object object : container.getContents()) {
                        if (object instanceof FluidStack fluidStack) {
                            fluidMap.computeLong(fluidStack, (k, v) -> v == null ? fluidStack.getAmount() : v + fluidStack.getAmount());
                        }
                    }
                }

                if (container.isDistinct()) {
                    for (var entry : fluidMap.object2LongEntrySet()) {
                        ingredientStacks.put(new StrictFluidStack(entry.getKey()), entry.getLongValue());
                    }
                } else {
                    for (Object2LongMap.Entry<FluidStack> obj : fluidMap.object2LongEntrySet()) {
                        map.computeLong(obj.getKey(), (k, v) -> v == null ? obj.getLongValue() : v + obj.getLongValue());
                    }
                }
                if (!patternBuffer) fluidMap.clear();
            }
            for (var entry : map.object2LongEntrySet()) {
                ingredientStacks.put(new StrictFluidStack(entry.getKey()), entry.getLongValue());
            }

            long minMultiplier = Integer.MAX_VALUE - 1;
            Object2IntOpenHashMap<FluidIngredient> notConsumableMap = recipeLogic.gtocore$getFluidNotConsumableMap();
            Object2IntOpenHashMap<FluidIngredient> fluidCountMap = recipeLogic.gtocore$getFluidConsumableMap();
            for (Content content : recipe.getInputContents(FluidRecipeCapability.CAP)) {
                FluidIngredient fluidInput = FluidRecipeCapability.CAP.of(content.content);
                int fluidAmount = fluidInput.getAmount();
                if (content.chance == 0) {
                    notConsumableMap.computeIfPresent(fluidInput, (k, v) -> v + fluidAmount);
                    notConsumableMap.putIfAbsent(fluidInput, fluidAmount);
                } else {
                    fluidCountMap.computeIfPresent(fluidInput, (k, v) -> v + fluidAmount);
                    fluidCountMap.putIfAbsent(fluidInput, fluidAmount);
                }
            }

            for (Map.Entry<FluidIngredient, Integer> notConsumableFluid : notConsumableMap.object2IntEntrySet()) {
                long needed = notConsumableFluid.getValue();
                long available = 0;
                for (Map.Entry<StrictFluidStack, Long> inputFluid : ingredientStacks.object2LongEntrySet()) {
                    if (notConsumableFluid.getKey().test(inputFluid.getKey())) {
                        available = inputFluid.getValue();
                        if (available > needed) {
                            inputFluid.setValue(available - needed);
                            needed -= available;
                            break;
                        } else {
                            inputFluid.setValue(0L);
                            notConsumableFluid.setValue((int) (needed - available));
                            needed -= available;
                        }
                    }
                }
                if (needed >= available) {
                    return 0;
                }
            }

            if (fluidCountMap.isEmpty() && !notConsumableMap.isEmpty()) {
                return parallelAmount;
            }

            for (Map.Entry<FluidIngredient, Integer> fs : fluidCountMap.object2IntEntrySet()) {
                long needed = fs.getValue();
                long available = 0;
                for (Map.Entry<StrictFluidStack, Long> inputFluid : ingredientStacks.object2LongEntrySet()) {
                    if (fs.getKey().test(inputFluid.getKey())) {
                        available += inputFluid.getValue();
                    }
                }
                if (available >= needed) {
                    int ratio = (int) Math.min(parallelAmount, (float) available / needed);
                    if (ratio < minMultiplier) {
                        minMultiplier = ratio;
                    }
                } else {
                    return 0;
                }
            }
            return (int) minMultiplier;
        }
        return 1;
    }
}
