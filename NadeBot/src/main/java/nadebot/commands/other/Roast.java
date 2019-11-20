package nadebot.commands.other;

import nadebot.hub.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Random;

public class Roast extends ListenerAdapter {

    String[] welcomeMessages = {
            "You have the right to remain silent because whatever you say will probably be stupid anyway.",
            "I’d slap you but I don’t want to make your face look any better.",
            "Were you born this stupid or did you take lessons?",
            "Hey, your village called – they want their idiot back.",
            "It’s better to let someone think you’re stupid than open your mouth and prove it.",
            "When you were born, the doctor came out to the waiting room and said to your dad, “I’m very sorry. We did everything we could. But he pulled through.”",
            "You are proof that evolution can go in reverse.",
            "The zoo called. They’re wondering how you got out of your cage?",
            "Save your breath – you’ll need it to blow up your date.",
            "Your doctor called with your colonoscopy results. Good news – they found your head.",
            "Stupidity’s not a crime, so you’re free to go.",
            "You’ll never be the man your mother is.",
            "Your family tree must be a cactus because everyone on it is a prick.",
            "You’re so fat you could sell shade.",
            "If you’re going to be two-faced, at least make one of them pretty.",

    };
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] roast = event.getMessage().getContentRaw().split("\\s+");

        if (roast[0].equalsIgnoreCase(Main.prefix + "roast")) {
            Random welcomeMsg = new Random();

            int number = welcomeMsg.nextInt(welcomeMessages.length);

            EmbedBuilder welcome = new EmbedBuilder();
            welcome.setColor(0xff0094);
            welcome.setDescription(welcomeMessages[number]);
            event.getChannel().sendMessage(welcome.build()).queue();
        }
    }
}
