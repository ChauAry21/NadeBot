package nadebot.commands.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import nadebot.hub.Main;
import nadebot.music.library.GuildMusicManager;
import nadebot.music.library.MusicPlayer;
import nadebot.music.library.TrackScheduler;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class SkipCommand extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] skip = event.getMessage().getContentRaw().split("\\s+");

        if (skip[0].equalsIgnoreCase(Main.prefix + "skip")) {
            MusicPlayer musicPlayer = MusicPlayer.getInstance();
            GuildMusicManager musicManager = musicPlayer.getGuildMusicManager(event.getGuild());
            TrackScheduler scheduler = musicManager.scheduler;
            AudioPlayer player = musicManager.player;

            if (player.getPlayingTrack() == null) {
                event.getChannel().sendMessage("The Player sint Currently playing a song lul").queue();
                return;
            }
            scheduler.nextTrack();
            EmbedBuilder skipBuilder = new EmbedBuilder();
            skipBuilder.setColor(0x22ff2a);
            skipBuilder.setTitle("✔ Success!:");
            skipBuilder.setDescription("⏩ Skipping Track ");
            event.getChannel().sendMessage(skipBuilder.build()).queue();
        }
    }
}
