package zaftnotameni.creatania.machines.manacondenser

import net.minecraft.network.chat.Component
import zaftnotameni.creatania.config.CommonConfig
import zaftnotameni.creatania.util.Text
import kotlin.math.abs
import kotlin.math.ceil

fun gogglesTooltip(tooltip : MutableList<Component>, crouching : Boolean, condenser : ManaCondenserBlockEntity) {
  if (!crouching) return
  emptyLine(tooltip)

  Text.purple("Corrupt Mana Production:").forGoggles(tooltip)
  if (abs(condenser.speed) > 0) Text.muted("At current speed: every").space()
    .add(Text.purple(condenser.everyTicks.toString())).space()
    .add(Text.muted("ticks")).forGoggles(tooltip) else Text.muted("At current speed:").space()
    .add(Text.red("Never")).forGoggles(tooltip)

  emptyLine(tooltip)

  val correctionFactor = 1.coerceAtLeast(CommonConfig.MANA_CONDENSER_THROTTLE_PER_RPM_BELOW_MAX.get()).toFloat() * 1.coerceAtLeast(CommonConfig.MANA_CONDENSER_THROTTLE_MINIMUM_TICKS_PER_BLOCK.get()).toFloat()
  Text.muted("At max speed: every").space()
    .add(Text.purple(ceil(correctionFactor.toDouble()).toInt().toString())).space()
    .add(Text.muted("ticks")).forGoggles(tooltip)
}

private fun emptyLine(tooltip : MutableList<Component>) {
  Text.purple("").forGoggles(tooltip)
}