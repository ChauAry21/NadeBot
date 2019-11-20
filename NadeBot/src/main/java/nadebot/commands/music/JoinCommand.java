package nadebot.commands.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import nadebot.hub.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;

public class JoinCommand extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] joinMusic = event.getMessage().getContentRaw().split("\\s+");

        if (joinMusic[0].equalsIgnoreCase(Main.prefix + "join")) {

            AudioManager audioManager = event.getGuild().getAudioManager();

            if (audioManager.isConnected()) {
                event.getChannel().sendMessage("BRUH IM ALREADY IN A VOICE CHANNEL!").queue();
            }

            GuildVoiceState memberVoiceState = event.getMember().getVoiceState();

            if (!memberVoiceState.inVoiceChannel()) {
                event.getChannel().sendMessage("Join a voice channel before adding me").queue();
            }

            VoiceChannel voiceChannel = memberVoiceState.getChannel();
            Member selfMember = event.getGuild().getSelfMember();

            if (!selfMember.hasPermission(voiceChannel, Permission.VOICE_CONNECT)) {
                event.getChannel().sendMessageFormat("sorry! i dont have permission to join %s", voiceChannel).queue();
            }

            audioManager.openAudioConnection(voiceChannel);
            EmbedBuilder Joining = new EmbedBuilder();
            Joining.setColor(0x22ff2a);
            Joining.setTitle("✔ Success!:");
            Joining.setDescription("⏳ Joining....");
            event.getChannel().sendMessage(Joining.build()).queue();


        }
    }
}
