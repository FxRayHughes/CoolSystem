package top.maplex.coolsystem

import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import taboolib.common.platform.command.CommandBody
import taboolib.common.platform.command.CommandHeader
import taboolib.common.platform.command.mainCommand
import taboolib.common.platform.command.subCommand
import taboolib.expansion.createHelper
import java.text.SimpleDateFormat

@CommandHeader(name = "coolsystem", ["cs", "cool"], permission = "coolsystem.use")
object Command {

    @CommandBody
    val main = mainCommand {
        createHelper()
    }

    @CommandBody
    val set = subCommand {
        dynamic("player") {
            suggestion<CommandSender> { _, _ ->
                Bukkit.getOnlinePlayers().map { it.name }
            }
            dynamic("seconds") {
                execute<CommandSender> { sender, context, argument ->
                    val player = Bukkit.getPlayerExact(context["player"]) ?: return@execute run {
                        sender.sendMessage("§c玩家不在线")
                    }
                    val time = context["seconds"]
                    API.setCoolTime(player.uniqueId, time.toLong() * 1000)
                }
            }
        }
    }

    @CommandBody
    val tryset = subCommand {
        dynamic("player") {
            suggestion<CommandSender> { _, _ ->
                Bukkit.getOnlinePlayers().map { it.name }
            }
            dynamic("seconds") {
                execute<CommandSender> { sender, context, argument ->
                    val player = Bukkit.getPlayerExact(context["player"]) ?: return@execute run {
                        sender.sendMessage("§c玩家不在线")
                    }
                    val time = context["seconds"]
                    API.trySetCoolTime(player.uniqueId, time.toLong() * 1000)
                }
            }
        }
    }

    @CommandBody
    val add = subCommand {
        dynamic("player") {
            suggestion<CommandSender> { _, _ ->
                Bukkit.getOnlinePlayers().map { it.name }
            }
            dynamic("seconds") {
                execute<CommandSender> { sender, context, argument ->
                    val player = Bukkit.getPlayerExact(context["player"]) ?: return@execute run {
                        sender.sendMessage("§c玩家不在线")
                    }
                    val time = context["seconds"]
                    API.addCoolTime(player.uniqueId, time.toLong() * 1000)
                }
            }
        }
    }

    @CommandBody
    val clear = subCommand {
        dynamic("player") {
            suggestion<CommandSender> { _, _ ->
                Bukkit.getOnlinePlayers().map { it.name }
            }
            execute<CommandSender> { sender, context, argument ->
                val player = Bukkit.getPlayerExact(context["player"]) ?: return@execute run {
                    sender.sendMessage("§c玩家不在线")
                }
                API.clearCoolTime(player.uniqueId)
            }
        }
    }

    @CommandBody
    val info = subCommand {
        dynamic("player") {
            suggestion<CommandSender> { _, _ ->
                Bukkit.getOnlinePlayers().map { it.name }
            }
            execute<CommandSender> { sender, context, argument ->
                val player = Bukkit.getPlayerExact(context["player"]) ?: return@execute run {
                    sender.sendMessage("§c玩家不在线")
                }
                sender.sendMessage("§7玩家:§f ${player.name}")
                sender.sendMessage("§7是否在冷却中:§f ${API.isCool(player.uniqueId)}")
                sender.sendMessage("§7剩余冷却时间:§f ${API.getRemainTime(player.uniqueId)} (${API.getRemainTime(player.uniqueId) / 1000})s")
                val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                val formattedDate: String = sdf.format(API.getOverTime(player.uniqueId))
                sender.sendMessage("§7结束冷却时间:§f ${API.getOverTime(player.uniqueId)} (${formattedDate})")
            }
        }
    }

    @CommandBody
    val reload = subCommand {
        execute<CommandSender> { sender, context, argument ->
            Listener.config.reload()
            sender.sendMessage("§a重载完成")
        }
    }

}