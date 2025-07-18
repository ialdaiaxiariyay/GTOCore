package com.gtocore.common.data

import com.gtocore.integration.ae.ExchangeStorageMonitorPart

import net.minecraft.world.item.Item

import appeng.api.parts.IPart
import appeng.api.parts.IPartItem
import appeng.api.parts.PartModels
import appeng.core.definitions.ItemDefinition
import appeng.items.parts.PartItem
import appeng.items.parts.PartModelsHelper
import com.gtolib.GTOCore
import com.gtolib.api.annotation.component_builder.ComponentBuilder
import com.gtolib.utils.register.ItemRegisterUtils.*
import com.tterrag.registrate.util.entry.ItemEntry
import com.tterrag.registrate.util.nullness.NonNullBiConsumer

import java.util.function.Function
import java.util.function.Supplier

object GTOAEParts {
    fun init() {
    }

    val EXCHANGE_STORAGE_MONITOR: Supplier<ItemDefinition<PartItem<ExchangeStorageMonitorPart>>> = createPart(
        id = "exchange_storage_monitor",
        en = "Exchange Storage Monitor",
        cn = "交换存储监控器",
        partClass = ExchangeStorageMonitorPart::class.java,
        factory = ::ExchangeStorageMonitorPart,
    )

    private fun <T : IPart> createPart(id: String, en: String, cn: String, partClass: Class<T>, factory: Function<IPartItem<T>, T>): Supplier<ItemDefinition<PartItem<T>>> {
        PartModels.registerModels(PartModelsHelper.createModels(partClass))
        val function: (Item.Properties) -> PartItem<T> = { p -> PartItem(p, partClass, factory) }
        val item: ItemEntry<PartItem<T>> = item(id, cn, function)
            .toolTips(ComponentBuilder.create("此物品可以监控物品的交换速率", "This item can monitor the exchange rate of items", { p -> p }).buildSingle(), ComponentBuilder.create("锁定状态下右击可切换监控间隔", "In locked state, right click to switch monitoring interval", { p -> p }).buildSingle())
            .lang(en)
            .model(NonNullBiConsumer.noop())
            .register()
        val definition = Supplier { ItemDefinition(en, GTOCore.id(id), item.get()) }
        return definition
    }
}
