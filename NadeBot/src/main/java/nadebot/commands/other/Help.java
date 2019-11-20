package nadebot.commands.other;

import nadebot.hub.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Help extends ListenerAdapter {
    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {

        if(event.getMessage().getContentRaw().equalsIgnoreCase(Main.prefix + "help")) {
            EmbedBuilder help = new EmbedBuilder();
            help.setColor(0xd400ff);
            help.setTitle("Command:");
            help.setDescription("`n!invite` \n `n!roast `\n `n!purge` \n `n!mutehow` \n `n!mute (tag member) [OPTIONAL](time)` \n \n Music: \n `n!join[make sure to be in a voice channel]` \n `n!leave` \n `n!play[url]` \n `n!skip` \n `n!queue`: Shows queue'd song information");
            event.getChannel().sendMessage(help.build()).queue();
            System.out.println("help cmd completed");
        }

        //mute help
        if(event.getMessage().getContentRaw().equalsIgnoreCase(Main.prefix + "mutehow")) {
            EmbedBuilder muteHelp = new EmbedBuilder();
            muteHelp.setColor(0xeeff00);
            muteHelp.setTitle("How to set mutes:");
            muteHelp.setDescription("Create a new role called 'muted' and deactivate all role permissions except the following: read text channels, read message history, and connect. If you are looking to mute someone do `n!mute (tag user)`. To unmute, do `n!mute (tag user)` again. If you want to mute someone for a specific amount of seconds, do `n!mute (tag user)[time]` \n \n example: n!mute @.medalbot 6 \n \n this will mute medalbot for 6 seconds then automatically unmute him");
            event.getChannel().sendMessage(muteHelp.build()).queue();
            System.out.println("mute help cmd completed");
        }
    }
}