package nadebot.commands.other;

import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Listener extends ListenerAdapter {
    public void onReady(ReadyEvent event) {
        System.out.printf("Logged in as %#s\n", event.getJDA().getSelfUser());
    }
}
