package zaftnotameni.creatania.util

import com.mojang.logging.LogUtils
import org.slf4j.Logger
import java.util.function.Consumer

object Log {
  @JvmField
  val LOGGER = LogUtils.getLogger()
  @JvmStatic
  fun throttled(rate : Int) : RateLimited {
    return RateLimited(rate)
  }

  interface IHasTickLogger {
    fun getLogger() : RateLimited?
    fun setLogger(logSampler : RateLimited?)
  }

  class RateLimited(var rate : Int) {
    var counter = 0
    fun log(doLog : Consumer<Logger?>) {
      counter++
      if (counter % rate != 0) return
      counter = 0
      doLog.accept(LOGGER)
    }

    companion object {
      @JvmStatic
      fun of(hasLogger : IHasTickLogger, rate : Int) : RateLimited? {
        if (hasLogger.getLogger() == null) hasLogger.setLogger(RateLimited(rate))
        return hasLogger.getLogger()
      }
    }
  }
}