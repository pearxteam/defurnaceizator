package ru.pearx.defurnaceizator

import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.FurnaceRecipes
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

fun replaceSmeltingOreDict(inputPrefix: String, outputPrefix: String, outputPrefixReplace: String) {
    val toAdd = mutableMapOf<ItemStack, Pair<ItemStack, Float>>()
    val iter = FurnaceRecipes.instance().smeltingList.iterator()
    for((input, output) in iter) { // for each recipe
        val inputOres = input.getOreNames()
        val outputOres = output.getOreNames()
        for(inputOre in inputOres) { // for each ore name of input stack
            if(inputOre.startsWith(inputPrefix)) {
                val oreName = inputOre.substring(inputPrefix.length)
                if("$outputPrefix$oreName" in outputOres) {
                    val nuggets = OreDictionary.getOres("$outputPrefixReplace$oreName")
                    if(!nuggets.isEmpty()) {
                        val xp = FurnaceRecipes.instance().getSmeltingExperience(output)
                        Defurnaceizator.log.info("Removing the ${input.toString(true)} > ${output.toString(true)} (XP: $xp) furnace recipe")
                        iter.remove()
                        toAdd[input] = nuggets.first() to xp
                        break
                    }
                }
            }
        }
    }
    for((input, outputPair) in toAdd) {
        val (output, xp) = outputPair
        Defurnaceizator.log.info("Adding the ${input.toString(true)} > ${output.toString(true)} (XP: $xp) furnace recipe")
        FurnaceRecipes.instance().addSmeltingRecipe(input, output, xp)
    }
}