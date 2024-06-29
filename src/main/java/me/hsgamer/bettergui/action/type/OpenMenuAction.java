package me.hsgamer.bettergui.action.type;

import io.github.projectunified.minelib.scheduler.entity.EntityScheduler;
import me.hsgamer.bettergui.BetterGUI;
import me.hsgamer.bettergui.api.action.MenuActionInput;
import me.hsgamer.bettergui.api.menu.Menu;
import me.hsgamer.hscore.action.common.Action;
import me.hsgamer.hscore.bukkit.utils.MessageUtils;
import me.hsgamer.hscore.common.StringReplacer;
import me.hsgamer.hscore.task.element.TaskProcess;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.UUID;

import static me.hsgamer.bettergui.BetterGUI.getInstance;

public class OpenMenuAction implements Action {
  private final Menu menu;
  private final String value;
  private final boolean bypass;

  public OpenMenuAction(MenuActionInput input) {
    this.menu = input.getMenu();
    this.value = input.getValue();
    this.bypass = input.getOption().equalsIgnoreCase("bypassChecks");
  }

  @Override
  public void apply(UUID uuid, TaskProcess process, StringReplacer stringReplacer) {
    // Get Menu and Arguments
    String[] split = stringReplacer.replaceOrOriginal(value, uuid).split(" ");
    String menu = split[0];
    String[] args = new String[0];
    if (split.length > 1) {
      args = Arrays.copyOfRange(split, 1, split.length);
    }

    Player player = Bukkit.getPlayer(uuid);
    if (player == null) {
      process.next();
      return;
    }

    // Open menu
    if (getInstance().getMenuManager().contains(menu)) {
      String[] finalArgs = args;
      Menu parentMenu = this.menu;
      EntityScheduler.get(BetterGUI.getInstance(), player)
        .run(() -> {
          try {
            getInstance().getMenuManager().openMenu(menu, player, finalArgs, parentMenu, bypass);
          } finally {
            process.next();
          }
        }, process::next);
    } else {
      MessageUtils.sendMessage(player, getInstance().getMessageConfig().getMenuNotFound());
      process.next();
    }
  }
}
