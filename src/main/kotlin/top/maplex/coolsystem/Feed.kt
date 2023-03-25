package top.maplex.coolsystem

import org.bukkit.Bukkit
import org.bukkit.event.player.PlayerJoinEvent
import taboolib.common.platform.Schedule
import taboolib.common.platform.event.SubscribeEvent

object Feed {

    @SubscribeEvent
    fun onJoin(event: PlayerJoinEvent) {
        val adder = Listener.config.getLong("JoinAdd", 0)
        CoolSystem.data[event.player.uniqueId] = System.currentTimeMillis() + adder
    }

    @Schedule(period = 1200)
    fun clear() {
        CoolSystem.data.toMap().forEach { (t, _) ->
            if (!Bukkit.getOfflinePlayer(t).isOnline) {
                CoolSystem.data.remove(t)
            }
        }
    }

}