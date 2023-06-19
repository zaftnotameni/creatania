package zaftnotameni.creatania.machines.managenerator

import com.simibubi.create.infrastructure.config.AllConfigs
import net.minecraft.network.chat.Component
import zaftnotameni.creatania.util.Text
import kotlin.math.roundToInt

val manaLabel = arrayOf("ALMOST NOTHING", "EXTREMELY LOW", "VERY LOW", "LOW")

fun gogglesTooltip(tooltip : MutableList<Component>, crouching : Boolean, generator : ManaGeneratorBlockEntity) {
  if (!crouching) return
  emptyLine(tooltip)

  Text.muted("Pure Mana Consumed at current speed:").space()
    .add(Text.aqua("${pureManaAtRpm(generator.speed)}")).forGoggles(tooltip)

  emptyLine(tooltip)

  Text.muted("Pure Mana consumed per RPM:").space()
    .add(Text.red("${pureManaAtRpm(1f)}")).forGoggles(tooltip)

  emptyLine(tooltip)

  val index = (Math.abs(generator.speed) * 3f / AllConfigs.server().kinetics.maxRotationSpeed.get()).roundToInt()
  Text.muted("Real Mana Produced at current speed:").space()
    .add(Text.red(manaLabel[index % 4])).forGoggles(tooltip)
}

private fun emptyLine(tooltip : MutableList<Component>) {
  Text.purple("").forGoggles(tooltip)
}