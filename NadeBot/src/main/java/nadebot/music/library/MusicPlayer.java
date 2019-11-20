package nadebot.music.library;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;

import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.HashMap;
import java.util.Map;

public class MusicPlayer {
    private static MusicPlayer INSTANCE;
    private final AudioPlayerManager MusicPlayer;
    private final Map<Long, GuildMusicManager> musicManager;

    private MusicPlayer() {
        this.musicManager = new HashMap<>();

        this.MusicPlayer = new DefaultAudioPlayerManager();
        AudioSourceManagers.registerRemoteSources(MusicPlayer);
        AudioSourceManagers.registerLocalSource(MusicPlayer);
    }

    public synchronized GuildMusicManager getGuildMusicManager(Guild guild) {
        long guildId = guild.getIdLong();
        GuildMusicManager musicManagers = musicManager.get(guildId);

        if (musicManagers == null) {
            musicManagers = new GuildMusicManager(MusicPlayer);
            musicManager.put(guildId, musicManagers);
        }

        guild.getAudioManager().setSendingHandler(musicManagers.getSendHandler());

        return musicManagers;
    }

    public void loadAndPlay(TextChannel channel, String trackUrl) {
        GuildMusicManager musicManagers = getGuildMusicManager(channel.getGuild());

        MusicPlayer.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {

                EmbedBuilder adding2Queue = new EmbedBuilder();
                adding2Queue.setColor(0x22ff2a);
                adding2Queue.setTitle("✔ Success!:");
                adding2Queue.setDescription("Adding music to queue: " + track.getInfo().title);
                channel.sendMessage(adding2Queue.build()).queue();

                play(musicManagers, track);
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                AudioTrack firstTrack = playlist.getSelectedTrack();

                if (firstTrack == null) {
                    firstTrack = playlist.getTracks().remove(0);
                }

                EmbedBuilder adding2Queue = new EmbedBuilder();
                adding2Queue.setColor(0x22ff2a);
                adding2Queue.setTitle("✔ Success!:");
                adding2Queue.setDescription("Adding Track to queue: " + firstTrack.getInfo().title + " first track in playlist " + playlist.getName() + ")");
                channel.sendMessage(adding2Queue.build()).queue();

                play(musicManagers, firstTrack);

                playlist.getTracks().forEach(musicManagers.scheduler::queue);
            }

            @Override
            public void noMatches() {
                EmbedBuilder failed1 = new EmbedBuilder();
                failed1.setColor(0xff3923);
                failed1.setTitle("❌ Failed!:");
                failed1.setDescription("Sorry, I searched far and wide but couldn't find: " + trackUrl);
                channel.sendMessage(failed1.build()).queue();
            }

            @Override
            public void loadFailed(FriendlyException exception) {
                EmbedBuilder failed2 = new EmbedBuilder();
                failed2.setColor(0xff3923);
                failed2.setTitle("❌ Failed!:");
                failed2.setDescription("Sorry I can't play: " + exception.getMessage() + "to get help do `n!support` and join our support discord!");
                channel.sendMessage(failed2.build()).queue();
            }
        });
    }

    private void play(GuildMusicManager musicManager, AudioTrack track) {
        musicManager.scheduler.queue(track);
    }

    public static synchronized MusicPlayer getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MusicPlayer();
        }

        return INSTANCE;
    }
}
