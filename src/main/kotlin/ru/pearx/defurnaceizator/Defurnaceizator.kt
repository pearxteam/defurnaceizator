package ru.pearx.defurnaceizator

import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.FurnaceRecipes
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.oredict.OreDictionary
import org.apache.logging.log4j.Logger

@Mod(modLanguageAdapter = "net.shadowfacts.forgelin.KotlinAdapter", name = NAME, modid = ID, version = VERSION, acceptedMinecraftVersions = ACCEPTED_MINECRAFT_VERSIONS, acceptableRemoteVersions = "*", dependencies = DEPENDENCIES)
object Defurnaceizator {
    lateinit var log: Logger private set

    @Mod.EventHandler
    fun preInit(event: FMLPreInitializationEvent) {
        log = event.modLog
        with(event.modMetadata) {
            autogenerated = false
            modId = ID
            name = NAME
            description = DESCRIPTION
            version = VERSION
            authorList = AUTHORS
        }
    }

    @Mod.EventHandler
    fun postInit(event: FMLPostInitializationEvent) {
        replaceSmeltingOreDict("ore", "ingot", "nugget")
        replaceSmeltingOreDict("dust", "ingot", "nugget")
    }
}