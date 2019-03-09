package us.noks.smallpractice.utils;

import java.util.List;
import java.util.StringJoiner;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.util.com.google.common.collect.Lists;
import us.noks.smallpractice.Main;
import us.noks.smallpractice.objects.Duel;

public class Messages {
	
	public static String[] WELCOME_MESSAGE = new String[] {
			ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH + "----------------------------------------------------",
			ChatColor.YELLOW + "Welcome on the " + ChatColor.DARK_AQUA.toString() + ChatColor.BOLD + "Halka" + ChatColor.YELLOW + " practice " + Main.getInstance().getDescription().getVersion() + " server",
			"",
			ChatColor.AQUA + "Noksio (Creator) Twitter -> " + ChatColor.DARK_AQUA + "https://twitter.com/NotNoksio",
			ChatColor.BLUE + "Noksio (Creator) Discord -> " + ChatColor.DARK_AQUA + "https://discord.gg/TZhyPnB",
			ChatColor.DARK_PURPLE + "xelo_o (Server Owner) Twitch -> " + ChatColor.DARK_AQUA + "https://www.twitch.tv/xelo_o",
			ChatColor.RED + "-> Keep in mind this is a beta ^^",
			ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH + "----------------------------------------------------"};
	public static String NO_PERMISSION = ChatColor.RED + "No permission.";
	public static String PLAYER_NOT_ONLINE = ChatColor.RED + "This player is not online.";
	public static String NOT_YOURSELF = ChatColor.RED + "You can't execute that command on yourself!";
	
	public static String CONTACT_NOKSIO = ChatColor.RED + "Please contact Noksio!";
	
	public static void deathMessage(Duel duel, int winningTeamNumber) {
		List<UUID> winnerTeam = null;
		List<UUID> loserTeam = null;
		switch (winningTeamNumber) {
		case 1:
			winnerTeam = duel.getFirstTeam();
			loserTeam = duel.getSecondTeam();
			break;
		case 2:
			winnerTeam = duel.getSecondTeam();
			loserTeam = duel.getFirstTeam();
			break;
		default:
			break;
		}
		boolean partyFight = (duel.getFirstTeamPartyLeaderUUID() != null && duel.getSecondTeamPartyLeaderUUID() != null);
		
		String winnerMessage = ChatColor.DARK_AQUA + "Winner: " + ChatColor.YELLOW + Bukkit.getPlayer(winnerTeam.get(0)).getName() + (partyFight ? "'s party" : "");
		
	    TextComponent l1 = new TextComponent();
	    l1.setText("Inventories (Click):");
	    l1.setColor(net.md_5.bungee.api.ChatColor.DARK_AQUA);
	    
	    List<BaseComponent> inventoriesTextList = Lists.newArrayList();
	    
	    for (UUID wUUID : winnerTeam) {
	    	Player winners = Bukkit.getPlayer(wUUID);
	    	TextComponent l1a = new TextComponent();
	    	
	    	l1a.setText(winners.getName());
		    l1a.setColor(net.md_5.bungee.api.ChatColor.GREEN);
		    l1a.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GREEN + "Click to view " + winners.getName() + "'s inventory").create()));
		    l1a.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/inventory " + winners.getUniqueId()));
		    
		    inventoriesTextList.add(new TextComponent(" "));
		    inventoriesTextList.add(l1a);
	    }
	    
	    for (UUID lUUID : loserTeam) {
	    	Player losers = Bukkit.getPlayer(lUUID);
	    	TextComponent l1b = new TextComponent();
	    	
	    	l1b.setText(losers.getName());
		    l1b.setColor(net.md_5.bungee.api.ChatColor.RED);
		    l1b.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.RED + "Click to view " + losers.getName() + "'s inventory").create()));
		    l1b.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/inventory " + losers.getUniqueId()));
		    
		    inventoriesTextList.add(new TextComponent(" "));
		    inventoriesTextList.add(l1b);
	    }
	    
	    l1.setExtra(inventoriesTextList);
	    l1.addExtra(net.md_5.bungee.api.ChatColor.DARK_AQUA + ".");
	    
	    StringJoiner spect = new StringJoiner(ChatColor.DARK_AQUA + ", ");
	    if (duel.hasSpectator()) {
	    	for (UUID specs : duel.getAllSpectators()) {
	    		Player spec = Bukkit.getPlayer(specs);
	    		spect.add(ChatColor.YELLOW + spec.getName());
	    	}
	    }
	    String spectatorMessage = ChatColor.DARK_AQUA + "Spectator" + (duel.getAllSpectators().size() > 1 ? "s: " : ": ") + spect.toString();
	    
	    List<UUID> duelPlayers = Lists.newArrayList(duel.getFirstTeam());
	    duelPlayers.addAll(duel.getSecondTeam());
	    duelPlayers.addAll(duel.getAllSpectators());
	    
	    for (UUID dpUUID : duelPlayers) {
	    	Player duelPlayer = Bukkit.getPlayer(dpUUID);
	    	if (duelPlayer == null) continue;
	    	duelPlayer.sendMessage(winnerMessage);
	    	duelPlayer.spigot().sendMessage(l1);
	    	if (duel.hasSpectator()) duelPlayer.sendMessage(spectatorMessage);
	    }
	}
}