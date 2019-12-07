package nadebot.hub;

import javax.security.auth.login.LoginException;

import nadebot.commands.moderation.*;
import nadebot.commands.moderation.owner.MyTestPerms;
import nadebot.commands.music.*;
import nadebot.commands.other.*;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;


public class Main {
    public static JDA jda;

    static final String youtubeKey = "AIzaSyAs8unCQNnDS3W-F6UQcXXQkhUXy26FAF0";

    public static void main(String[] args) throws LoginException, InterruptedException {
        jda = new JDABuilder(AccountType.BOT).setToken("NjMyMzIzMjU1MjcyODY1ODAy.XdSt2A.7B_7q_enBsq6Lw0IVYe40jxYmno").build();
        jda.getPresence().setStatus(net.dv8tion.jda.api.OnlineStatus.ONLINE);
        jda.getPresence().setActivity(Activity.watching("n!help"));

        jda.addEventListener(new Listener());
        jda.addEventListener(new Invite());
        jda.addEventListener(new Help());
        jda.addEventListener(new MyTestPerms());
        jda.addEventListener(new Purge());
        jda.addEventListener(new Mute());
        jda.addEventListener(new Roast());
        jda.addEventListener(new Ban());
        jda.addEventListener(new UserInformationSystem());
        jda.addEventListener(new JoinCommand());
        jda.addEventListener(new LeaveCommand());
        jda.addEventListener(new PlayCommand());
        jda.addEventListener(new StopCommand());
        jda.addEventListener(new PauseCommand());
        jda.addEventListener(new SkipCommand());
        jda.addEventListener(new NowPlayingCommand());
        jda.addEventListener(new QueueCommand());

        jda.awaitReady();
    }
}
