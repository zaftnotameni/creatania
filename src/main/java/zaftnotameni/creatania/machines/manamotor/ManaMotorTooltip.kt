package zaftnotameni.creatania.machines.manamotor

import com.simibubi.create.infrastructure.config.AllConfigs
import net.minecraft.network.chat.Component
import zaftnotameni.creatania.util.Text

fun gogglesTooltip(tooltip : MutableList<Component>, crouching : Boolean, motor : ManaMotorBlockEntity) {
  if (!crouching) return

  emptyLine(tooltip)

  Text.muted("Maximum SU Produced:").space()
    .add(Text.gray(motor.getManaMachine().maximumSUPossible.toString())).space()
    .add(Text.muted("at")).space()
    .add(Text.gray(AllConfigs.server().kinetics.maxRotationSpeed.get().toString())).space()
    .add(Text.gray("RPM")).forGoggles(tooltip)

  emptyLine(tooltip)

  Text.muted("Mana consumed per RPM:").space()
    .add(Text.red("HIGH")).forGoggles(tooltip)
}

private fun emptyLine(tooltip : MutableList<Component>) {
  Text.purple("").forGoggles(tooltip)
}