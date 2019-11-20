package nadebot.commands.moderation;

import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MsgReader extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        User author = event.getAuthor();
        Message message = event.getMessage();
        String content = message.getContentDisplay();

        if (event.isFromType(ChannelType.TEXT)) {

            Guild guild = event.getGuild();
            TextChannel textChannel = event.getTextChannel();

            System.out.printf("(%s)[%s]<%#s>: %s\n", guild.getName(), textChannel.getName(), author, content);
        }
        else if (event.isFromType(ChannelType.PRIVATE)) {
            System.out.printf("[PRIV]<%#s>: %s\n", author, content);
        }
    }
}
