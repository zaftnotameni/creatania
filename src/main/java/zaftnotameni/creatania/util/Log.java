package zaftnotameni.creatania.util;

import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

import java.util.function.Consumer;

public class Log {
  public static final Logger LOGGER = LogUtils.getLogger();

  public static RateLimited throttled(int rate) {
    return new RateLimited(rate);
  }

  public interface IHasTickLogger {
    RateLimited getLogger();
    void setLogger(RateLimited logSampler);
  }
  public static class RateLimited {
    public int rate;
    public int counter;
    public RateLimited(int pRate) {
      this.rate = pRate;
    }
    public static RateLimited of(IHasTickLogger hasLogger, int rate) {
      if (hasLogger.getLogger() == null) hasLogger.setLogger(new RateLimited(rate));
      return hasLogger.getLogger();
    }
    public void log(Consumer<Logger> doLog) {
      counter++;
      if (counter % rate != 0) return;
      counter = 0;
      doLog.accept(LOGGER);
    }
  }
}
