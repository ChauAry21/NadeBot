package nadebot.commands.moderation;

import nadebot.hub.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.List;

public class Purge extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        //purge command
        String[] lines1 = event.getMessage().getContentRaw().split("\\s+");

        if(lines1[0].equalsIgnoreCase(Main.prefix + "purge")) {
            if(lines1.length < 2) {
                EmbedBuilder usage = new EmbedBuilder();
                usage.setColor(0xff3923);
                usage.setTitle("How much should i delete?");
                usage.setDescription("usage: `" + Main.prefix + "purge [#of messages you want to delete]`");
                event.getChannel().sendMessage(usage.build()).queue();
            }
            else {
                try {
                    List<Message> purgeValue = event.getChannel().getHistory().retrievePast(Integer.parseInt(lines1[1])).complete();
                    event.getChannel().deleteMessages(purgeValue).queue();

                    EmbedBuilder purgeCompletionMSG = new EmbedBuilder();
                    purgeCompletionMSG.setColor(0x22ff2a);
                    purgeCompletionMSG.setTitle("Successfully purged chat!: ");
                    purgeCompletionMSG.setDescription("Successfully purged chat of " + lines1[1] + " Messages!");
                    event.getChannel().sendMessage(purgeCompletionMSG.build()).queue();
                    System.out.println("Successfully purged chat!");
                }
                catch(IllegalArgumentException e) {
                    if (e.toString().startsWith("java.lang.IllegalArgumentException: Message retrieval")){
                        EmbedBuilder error = new EmbedBuilder();
                        error.setColor(0xff3923);
                        error.setTitle("Error: Too many Messages Selected");
                        error.setDescription("Can only select between 1-100 messages!");
                        event.getChannel().sendMessage(error.build()).queue();
                        System.out.println("Error: Too many Messages Selected");
                    }
                    else {
                        EmbedBuilder error = new EmbedBuilder();
                        error.setColor(0xff3923);
                        error.setTitle("Error: Messages are too old!");
                        error.setDescription("Messages can't be over 2 weeks old!");
                        event.getChannel().sendMessage(error.build()).queue();
                        System.out.println("Error: Too many Messages Selected");
                    }
                    e.printStackTrace();
                }
            }
        }
    }
}