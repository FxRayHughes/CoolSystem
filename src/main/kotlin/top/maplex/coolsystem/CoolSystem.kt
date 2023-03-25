package top.maplex.coolsystem

import taboolib.common.platform.Plugin
import taboolib.common.platform.function.info
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

object CoolSystem : Plugin() {

    val data = ConcurrentHashMap<UUID, Long>()

    val api by lazy {
        API
    }

    fun getAPI(): API {
        return api
    }
}