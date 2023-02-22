package zaftnotameni.creatania.mana;

public class ManaPoolValues {
  public static final int MAX_MANA = 1000000;
  public static final int MAX_MANA_DILLUTED = 10000;

  public static int dilluted(float x) { return (int) (dilluted() * x); }
  public static int dilluted(int x) { return dilluted() * x; }
  public static int dilluted() { return MAX_MANA_DILLUTED / 1; }

  public static int full() { return MAX_MANA / 1; }
  public static int half() { return MAX_MANA / 2; }
  public static int third() { return MAX_MANA / 3; }
  public static int quarter() { return MAX_MANA / 4; }
  public static int fifth() { return MAX_MANA / 5; }
  public static int eight() { return MAX_MANA / 8; }
  public static int tenth() { return MAX_MANA / 10; }
  public static int sixteenth() { return MAX_MANA / 10; }
  public static int hundreth() { return MAX_MANA / 100; }
  public static int full(int x) { return full() * x; }
  public static int half(int x) { return half() * x; }
  public static int third(int x) { return third() * x; }
  public static int quarter(int x) { return quarter() * x; }
  public static int fifth(int x) { return fifth() * x; }
  public static int eight(int x) { return eight() * x; }
  public static int tenth(int x) { return tenth() * x; }
  public static int sixteenth(int x) { return sixteenth() * x; }
  public static int hundreth(int x ) { return hundreth()  * x; }
}
