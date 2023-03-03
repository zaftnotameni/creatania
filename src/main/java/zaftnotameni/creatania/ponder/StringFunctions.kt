package zaftnotameni.creatania.ponder

import com.simibubi.create.foundation.fluid.FluidIngredient.FluidStackIngredient
import net.minecraft.world.level.block.Block

fun nameOf(fsi : FluidStackIngredient) : String = fsi?.getMatchingFluidStacks()?.first()?.displayName?.string ?: "UNKNOWN"
fun nameOf(block : Block) : String = block?.name?.string  ?: "UNKNOWN"