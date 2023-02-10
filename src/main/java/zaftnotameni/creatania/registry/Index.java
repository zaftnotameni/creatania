package zaftnotameni.creatania.registry;
import com.simibubi.create.foundation.data.CreateRegistrate;
import zaftnotameni.creatania.Constants;
public class Index {
  public static final CreateRegistrate CREATE_REGISTRATE = CreateRegistrate.create(Constants.MODID);

  public static CreateRegistrate getCreateRegistrate() { return CREATE_REGISTRATE; }
}
