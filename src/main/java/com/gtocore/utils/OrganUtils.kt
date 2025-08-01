package com.gtocore.utils

import com.gtocore.common.data.GTOOrganItems
import com.gtocore.common.item.misc.OrganItemBase
import com.gtocore.common.item.misc.OrganType

import net.minecraft.world.item.ItemStack

import com.gtolib.api.player.PlayerData

import kotlin.collections.filter

fun PlayerData.ktGetOrganStack(): Map<OrganType, List<ItemStack>> = this.organItemStacks
    .filter { it.item is OrganItemBase }
    .groupBy { (it.item as OrganItemBase).organType }
fun PlayerData.ktMatchOrganTier(tier: Int): Boolean {
    val mapValues: Map<OrganType, Int> = ktGetOrganStack()
        .mapValues { it.value.filter { it.item is OrganItemBase.TierOrganItem } }
        .filter { it.value.isNotEmpty() }
        .mapValues { it.value.maxOf { (it.item as OrganItemBase.TierOrganItem).tier } }
    return GTOOrganItems.TierOrganTypes.all { (mapValues[it] ?: -1) >= tier }
}

fun PlayerData.ktFreshOrganState() {
    this.organTierCache.clear()
    (0..4).forEach { tier ->
        if (this.ktMatchOrganTier(tier)) {
            this.organTierCache.add(tier)
        }
    }
}
