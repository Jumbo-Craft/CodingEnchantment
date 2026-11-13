package io.github.smile_ns.codingEnchantment

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.enchantment.EnchantItemEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class CodingEnchantment : JavaPlugin(), Listener {

    override fun onEnable() {
        // Plugin startup logic
        server.pluginManager.registerEvents(this, this)
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler
    fun onEnchant(event: EnchantItemEvent) {
        val item: ItemStack = event.item
        val file = File("world\\datapacks\\CodingEnchantment\\data\\codingenchantment\\enchantment\\sample.json")
        val json: JsonObject = Json.parseToJsonElement(
            file.readText()
        ).jsonObject

        val loreJson = json["coding"]!!
            .jsonObject["lore"]!!
            .jsonArray

        val lore: List<Component> = loreJson.map { element ->
            GsonComponentSerializer.gson()
                .deserialize(element.toString())
        }

        val meta: ItemMeta = item.itemMeta
        meta.lore(lore)

        item.itemMeta = meta
    }
}
