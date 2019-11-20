package nadebot.commands.music;

import nadebot.hub.Main;
import nadebot.music.library.GuildMusicManager;
import nadebot.music.library.MusicPlayer;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class StopCommand extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] stop = event.getMessage().getContentRaw().split("\\s+");

        if (stop[0].equalsIgnoreCase(Main.prefix + "stop")) {
            MusicPlayer musicPlayer = MusicPlayer.getInstance();
            GuildMusicManager guildMusicManager = musicPlayer.getGuildMusicManager(event.getGuild());

            guildMusicManager.scheduler.getQueue().clear();
            guildMusicManager.player.stopTrack();
            guildMusicManager.player.setPaused(false);

            event.getChannel().sendMessage("Stopping Music...").queue();
            event.getChannel().sendMessage("Music Stopped and Queue Cleared!").queue();
        }
    }
}
