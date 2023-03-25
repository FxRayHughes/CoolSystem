package top.maplex.coolsystem.event

import org.bukkit.entity.Player
import taboolib.module.nms.PacketReceiveEvent
import taboolib.platform.type.BukkitProxyEvent

class CoolSystemFilterEvent(
    val player: Player,
    val listener: PacketReceiveEvent,
    val info: String,
    val cancelled:Boolean
) : BukkitProxyEvent()