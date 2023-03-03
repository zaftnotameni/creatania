package zaftnotameni.creatania.machines.managenerator

import zaftnotameni.creatania.config.CommonConfig
import kotlin.math.abs
import kotlin.math.ceil
import kotlin.math.max
import kotlin.math.round

fun conversionRate() = max(1, CommonConfig.MANA_GENERATOR_MANA_CONVERSION_RATE.get())
fun manaPerRpm() = max(1, CommonConfig.MANA_GENERATOR_MANA_PER_RPM_PER_TICK.get())
fun suPerRpm() = max(1, CommonConfig.MANA_GENERATOR_SU_PER_RPM.get())

fun pureManaAtRpm(rpm : Float) = max(1, ceil(abs(rpm) * manaPerRpm()).toInt())
fun suAtRpm(rpm : Float) = abs(rpm) * suPerRpm()
fun potentialRealManaAtRpm(rpm : Float) = max(1f, ceil(pureManaAtRpm(rpm).toFloat() / conversionRate().toFloat()))
fun realManaAtRpmWhileConsuming(rpm : Float, consumedPureMana : Int) = max(1, round(
  (consumedPureMana.toFloat()/pureManaAtRpm(rpm)) * potentialRealManaAtRpm(rpm)).toInt())