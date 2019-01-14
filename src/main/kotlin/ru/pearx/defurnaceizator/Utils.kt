package ru.pearx.defurnaceizator

import net.minecraft.item.ItemStack
import net.minecraftforge.oredict.OreDictionary

fun ItemStack.getOreNames(): List<String> = arrayListOf<String>().also { lst ->
    if(isEmpty) return@also
    val stack = if (itemDamage == OreDictionary.WILDCARD_VALUE) copy().apply { itemDamage = 0 } else this
    for (id in OreDictionary.getOreIDs(stack)) {
        lst.add(OreDictionary.getOreName(id))
    }
}

fun ItemStack.toString(wildcardMetaAsAny: Boolean = false) = StringBuilder().apply {
    if (isEmpty) return@apply

    append(item.registryName.toString())

    if (metadata != 0) {
        append(':')
        if (wildcardMetaAsAny && metadata == OreDictionary.WILDCARD_VALUE)
            append('*')
        else
            append(metadata.toString())
    }

    if (hasTagCompound()) {
        append(' ')
        append(tagCompound.toString())
    }

    if (count != 1) {
        append(" x")
        append(count.toString())
    }
}.toString()