package nadebot.commands.music;

import nadebot.hub.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;

public class LeaveCommand extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] leave = event.getMessage().getContentRaw().split("\\s+");

        if  (leave[0].equalsIgnoreCase(Main.prefix + "leave")) {
            TextChannel channel = event.getChannel();
            AudioManager audioManager = event.getGuild().getAudioManager();

            if (!audioManager.isConnected()) {
                event.getChannel().sendMessage("BRUH IM ALREADY IN A VOICE CHANNEL!").queue();
            }

            VoiceChannel voiceChannel = audioManager.getConnectedChannel();

            if (!voiceChannel.getMembers().contains(event.getMember())) {
                event.getChannel().sendMessage("You have to be in the same voice channel as me broski").queue();
            }

            audioManager.closeAudioConnection();
            EmbedBuilder disconnect = new EmbedBuilder();
            disconnect.setColor(0x22ff2a);
            disconnect.setTitle("✔ Success!:");
            disconnect.setDescription("Disconnecting....");
            channel.sendMessage(disconnect.build()).queue();
        }
    }
}
