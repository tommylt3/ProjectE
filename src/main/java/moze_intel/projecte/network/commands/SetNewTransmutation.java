package moze_intel.projecte.network.commands;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import moze_intel.projecte.PECore;
import moze_intel.projecte.api.ItemInfo;
import moze_intel.projecte.integration.crafttweaker.WorldTransmutation;
import moze_intel.projecte.network.commands.argument.NSSItemArgument;
import moze_intel.projecte.network.commands.parser.NSSItemParser.NSSItemResult;
import moze_intel.projecte.utils.EMCHelper;
import moze_intel.projecte.utils.WorldTransmutations;
import moze_intel.projecte.utils.text.PELang;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.ForgeRegistries;

public class SetNewTransmutation {
   public static ArgumentBuilder<CommandSourceStack, ?> register() {
		return Commands.literal("setnewtransmutation")
				.requires(cs -> cs.hasPermission(2))
            .then(Commands.argument("itemOne", new NSSItemArgument()))
            .then(Commands.argument("itemTwo", new NSSItemArgument()))
				      .executes(ctx -> execute(ctx, NSSItemArgument.getNSS(ctx, "itemTwo"), NSSItemArgument.getNSS(ctx, "itemOne")));
	}

   public static int execute(CommandContext<CommandSourceStack> ctx, NSSItemResult stackOne, NSSItemResult stackTwo){
      String fromItemOne = stackOne.getStringRepresentation();
      String toItemTwo = stackTwo.getStringRepresentation();

      
      ctx.getSource().sendSuccess(PELang.COMMAND_TRANSMUTATION_SET_SUCCESS.translate(fromItemOne, toItemTwo), true);
		ctx.getSource().sendSuccess(PELang.RELOAD_NOTICE.translate(), true);
		return Command.SINGLE_SUCCESS;
	}
}
