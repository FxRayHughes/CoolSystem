package top.maplex.coolsystem

import java.util.*

object API {

    //设置冷却时间 n毫秒后
    fun setCoolTime(player: UUID, time: Long) {
        CoolSystem.data[player] = System.currentTimeMillis() + time
    }
    //重置冷却时间
    fun clearCoolTime(player: UUID) {
        CoolSystem.data.remove(player)
    }
    //增加 n毫秒冷却
    fun addCoolTime(player: UUID, time: Long) {
        getOverTime(player).let {
            if (it == 0L){
                CoolSystem.data[player] = System.currentTimeMillis() + time
            }else{
                CoolSystem.data[player] = it + time
            }
        }
    }
    //尝试设置冷却 剩余冷却如果大于设置值则不设置
    fun trySetCoolTime(player: UUID, time: Long) {
        if (getRemainTime(player) >= time) {
            return
        } else {
            setCoolTime(player, time)
        }
    }
    //获取结束冷却时间
    fun getOverTime(player: UUID): Long {
        return CoolSystem.data[player] ?: 0
    }
    //当前是否再冷却中
    fun isCool(player: UUID): Boolean {
        return (CoolSystem.data[player] ?: 0) > System.currentTimeMillis()
    }
    //获取剩余冷却时间
    fun getRemainTime(player: UUID): Long {
        ((CoolSystem.data[player] ?: 0) - System.currentTimeMillis()).let {
            return if (it <= 0) {
                0
            } else {
                it
            }
        }
    }

}