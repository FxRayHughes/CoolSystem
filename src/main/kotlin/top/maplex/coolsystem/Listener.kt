package top.maplex.coolsystem

import net.minecraft.server.v1_12_R1.PacketPlayInChat
import taboolib.common.platform.event.SubscribeEvent
import taboolib.module.configuration.Config
import taboolib.module.configuration.ConfigFile
import taboolib.module.lang.event.PlayerSelectLocaleEvent
import taboolib.module.lang.event.SystemSelectLocaleEvent
import taboolib.module.nms.PacketReceiveEvent
import taboolib.platform.util.sendLang
import top.maplex.coolsystem.event.CoolSystemFilterEvent
import java.util.regex.Pattern

object Listener {

    @Config
    lateinit var config: ConfigFile

    @SubscribeEvent
    fun onCommand(event: PacketReceiveEvent) {
        if (event.packet.name != "PacketPlayInChat") {
            return
        }
        val packet = event.packet.source as? PacketPlayInChat ?: return
        val info = packet.a()
        if (!info.startsWith("/")) {
            return
        }
        if (config.getStringList("filter").map {
                Pattern.compile(it).matcher(info.replace("/", "")).matches()
            }.contains(true)) {
            if (!API.isCool(event.player.uniqueId)) {
                return
            }
            val events = CoolSystemFilterEvent(
                event.player,
                event,
                info,
                true
            )
            events.call()
            event.isCancelled = events.cancelled
            if (events.cancelled) {
                event.player.sendLang("in-cool-down", info, (API.getRemainTime(event.player.uniqueId) / 1000))
            }
        }

    }

    @SubscribeEvent
    fun lang(event: PlayerSelectLocaleEvent) {
        event.locale = config.getString("Lang", "zh_CN")!!
    }

    @SubscribeEvent
    fun lang(event: SystemSelectLocaleEvent) {
        event.locale = config.getString("Lang", "zh_CN")!!
    }

}