package zaftnotameni.creatania.util

import com.mojang.logging.LogUtils
import org.slf4j.Logger
import java.lang.StackWalker.StackFrame
import java.util.*
import java.util.function.Consumer
import java.util.stream.Stream

fun log(fn : Consumer<Logger>) = fn.accept(log())
fun log() : Logger = LogUtils.getLogger()
fun debug(s : String) = log().debug(s)
fun debug(s : String, vararg args : Any) = log().debug(s, *args)
fun info(s : String) = log().info(s)
fun info(s : String, vararg args : Any) = log().info(s, *args)
fun warn(s : String) = log().warn(s)
fun warn(s : String, vararg args : Any) = log().warn(s, *args)
fun error(s : String) = log().error(s)
fun error(s : String, vararg args : Any) = log().error(s, *args)

val StatsKeeper : HashMap<String, LogStats> = HashMap()
val nope = Pair(false, defaultLogStats())

fun stack() : Optional<StackFrame> = StackWalker.getInstance().walk(::streamMatches)
fun streamMatches(s : Stream<StackFrame>) : Optional<StackFrame> = s.filter(::frameMatches).findFirst()
fun frameMatches(f: StackFrame) = f.className.contains("zaft") && !f.className.contains("LogKt")
fun deltaT(x : Long) = (System.currentTimeMillis() * 0.02 - x)
fun defaultLogStats() = LogStats(counter = 0, lastTime = 0)
fun seconds(x : Int) = x * 20
fun tlog(logFn : Consumer<Logger>) = tlog(seconds(30), logFn)

fun keeper(threshold : Int) : Pair<Boolean, LogStats> {
  val maybeFrame = stack()
  if (maybeFrame.isEmpty) return nope
  val stats = StatsKeeper.getOrPut(maybeFrame.get().descriptor, ::defaultLogStats)
  if (canLogBasedOnNumberOfAttempts(stats, threshold)) return Pair(true, stats)
  if (canLogBasedOnTime(stats, threshold)) return Pair(true, stats)
  return nope
}

fun canLogBasedOnTime(stats : LogStats, threshold : Int) = deltaT(stats.lastTime) > threshold
fun canLogBasedOnNumberOfAttempts(stats : LogStats, threshold : Int) = stats.counter++ > threshold

fun reset(stats : LogStats) {
  stats.counter = 0
  stats.lastTime = System.currentTimeMillis()
}

fun tlog(threshold : Int, logFn : Consumer<Logger>) {
  val kept = keeper(threshold)
  val canLog = kept.first
  val stats = kept.second
  if (!canLog) return
  reset(stats)
  logFn.accept(log())
}

data class LogStats(var counter : Int, var lastTime : Long)