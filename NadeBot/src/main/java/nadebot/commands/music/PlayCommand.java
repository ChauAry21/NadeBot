package nadebot.commands.music;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchResult;

import nadebot.hub.Main;
import nadebot.music.library.MusicPlayer;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import sun.security.krb5.Config;

import javax.annotation.Nullable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class PlayCommand extends ListenerAdapter {
    private final YouTube youTube;

    public PlayCommand() {
        YouTube temp = null;

        try {
            temp = new YouTube.Builder(
                    GoogleNetHttpTransport.newTrustedTransport(),
                    JacksonFactory.getDefaultInstance(),
                    null
            )
                    .setApplicationName("NadeBot")
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        youTube = temp;
    }

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] play = event.getMessage().getContentRaw().split("\\s+");
        boolean loopQueue = false;

        TextChannel channel = event.getChannel();

        if (play[0].equalsIgnoreCase(Main.prefix + "loop")) {
            loopQueue = true;
            EmbedBuilder loopQueueBuilder = new EmbedBuilder();
            loopQueueBuilder.setColor(0x22ff2a);
            loopQueueBuilder.setTitle("✔ Success!:");
            loopQueueBuilder.setDescription("Will loop song until the end of time!");
            channel.sendMessage(loopQueueBuilder.build()).queue();
        }
        if (play[0].equalsIgnoreCase(Main.prefix + "play")) {


            if (play.length != 2) {
                EmbedBuilder joinyes = new EmbedBuilder();
                joinyes.setColor(0x22ff2a);
                joinyes.setTitle("✔ Success!:");
                joinyes.setDescription("Please provide a url");
                channel.sendMessage(joinyes.build()).queue();
            }

            String input = String.join(" ", play[1]);
            while (loopQueue) {
                input = String.join(" ", play[1]);
            }

            if (!isUrl(input)) {
                String searchedYT = searchYoutube(input);

                if (searchedYT == null) {
                    channel.sendMessage("I searched far and wide but couldnt find that #sadboihours").queue();

                    return;
                }

                input = searchedYT;
            }

            MusicPlayer manager = MusicPlayer.getInstance();

            manager.loadAndPlay(event.getChannel(), input);
        }
    }
    private boolean isUrl(String input) {
        try {
            new URL(input);
            return true;
        } catch (MalformedURLException ignored) {
            return false;
        }
    }
    @Nullable
    private String searchYoutube(String input) {
        try {
            List<SearchResult> results = youTube.search()
                    .list("id.snippet")
                    .setQ(input)
                    .setMaxResults(1L)
                    .setType("video")
                    .setFields("items")
                    .setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)")
                    .setKey(Config.getInstance().get("youtubeKey"))
                    .execute()
                    .getItems();

            if (!results.isEmpty()) {
                String vidId = results.get(0).getId().getVideoId();

                return "https//www.youtube.com/watch?v=" + vidId;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}