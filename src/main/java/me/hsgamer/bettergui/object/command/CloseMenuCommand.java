package me.hsgamer.bettergui.object.command;

import co.aikar.taskchain.TaskChain;
import me.hsgamer.bettergui.object.Command;
import org.bukkit.entity.Player;

public class CloseMenuCommand extends Command {

  public CloseMenuCommand(String string) {
    super(string);
  }

  @Override
  public void addToTaskChain(Player player, TaskChain<?> taskChain) {
    getIcon().ifPresent(icon -> taskChain.sync(() -> icon.getMenu().closeInventory(player)));
  }
}
