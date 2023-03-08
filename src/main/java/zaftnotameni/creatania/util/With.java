package zaftnotameni.creatania.util;

import java.util.function.Consumer;

public class With<T> {
  public T self;
  public static <R> With<R> of(R self) { var x = new With<R>(); x.self = self; return x; }
  public T with(Consumer<T> fn) {
    fn.accept(self);
    return self;
  }
  public static <R> R with(R self, Consumer<R> fn) {
    fn.accept(self);
    return self;
  }
}