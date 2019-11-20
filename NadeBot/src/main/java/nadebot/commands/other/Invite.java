package nadebot.commands.other;

import nadebot.hub.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Invite extends ListenerAdapter {
    public void onGuildMessageReceivedEvent (GuildMessageReceivedEvent event) {
        String[] circle = event.getMessage().getContentRaw().split("\\s+");
        if (circle[0].equalsIgnoreCase(Main.prefix + "invite")) {
            EmbedBuilder invite = new EmbedBuilder();
            invite.setColor(0x66d8ff);
            invite.setTitle("Invite NadeBot to YOUR SERVER!");
            invite.setDescription("https://bots.ondiscord.xyz/bots/632323255272865802");
            event.getChannel().sendMessage(invite.build()).queue();
            System.out.println("invite completed");
        }
    }

}
