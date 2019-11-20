package nadebot.commands.music;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import nadebot.hub.Main;
import nadebot.music.library.GuildMusicManager;
import nadebot.music.library.MusicPlayer;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class QueueCommand extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] queuecmd = event.getMessage().getContentRaw().split("\\s+");

        if (queuecmd[0].equalsIgnoreCase(Main.prefix + "queue")) {
            TextChannel channel = event.getChannel();
            MusicPlayer musicPlayer = MusicPlayer.getInstance();
            GuildMusicManager musicManager = musicPlayer.getGuildMusicManager(event.getGuild());
            BlockingQueue<AudioTrack> queue = musicManager.scheduler.getQueue();

            if (queue.isEmpty()) {
                EmbedBuilder emptyQueueBuilder = new EmbedBuilder();
                emptyQueueBuilder.setColor(0xff3923);
                emptyQueueBuilder.setTitle("❌ Failed!:");
                emptyQueueBuilder.setDescription("Please provide a valid youtube, soundcloud or bandcamp link");
                channel.sendMessage(emptyQueueBuilder.build()).queue();
                channel.sendMessage("The Queue is empty dum-dum!").queue();
                return;
            }

            int trackCount = Math.min(queue.size(), 20);
            List<AudioTrack> tracks = new ArrayList<>(queue);
            EmbedBuilder builder = new EmbedBuilder()
                    .setTitle("Current Queue (Total: " + queue.size() + "):")
                    .setColor(0xffffff);
            for (int i = 0; i < trackCount; i++) {
                AudioTrack track = tracks.get(i);
                AudioTrackInfo info = track.getInfo();

                builder.appendDescription(String.format(
                        "%s - %s \n",
                        info.title,
                        info.author
                ));
            }
            channel.sendMessage(builder.build()).queue();
        }
    }
}