package nadebot.commands.moderation;

import nadebot.hub.Main;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class    Mute extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        //mute command
        String[] lines = event.getMessage().getContentRaw().split("\\s+");
            if (lines[0].equalsIgnoreCase(Main.prefix + "mute")) {
            if (lines.length > 1 && lines.length < 3) {
                Member member = event.getGuild().getMemberById(lines[1].replace("<@", "").replace(">", ""));
                Role role = event.getGuild().getRoleById("633387160531238949");
                if(!member.getRoles().contains(role)) {
                    //mute the boi
                    event.getChannel().sendMessage(lines[1] +" Thao Shall Not SPeAkeTh!").queue();
                    event.getGuild().addRoleToMember(member, role).complete();
                    System.out.println("user muted");
                }
                else {
                    //unmute the boi
                    event.getChannel().sendMessage(lines[1] + " Thao Shall SPeAkeTh!").queue();
                    event.getGuild().removeRoleFromMember(member, role).complete();
                    System.out.println("user un-muted");
                }
            }
            else if (lines.length == 3) {
                Member member = event.getGuild().getMemberById(lines[1].replace("<@", "").replace(">", ""));
                Role role = event.getGuild().getRoleById("633387160531238949");

                event.getChannel().sendMessage(lines[1] +" Thao Shall Not SPeAkeTh for " + lines[2] + " sEcoNDs!").queue();
                event.getGuild().addRoleToMember(member, role).complete();

                new java.util.Timer().schedule(
                        new java.util.TimerTask() {

                            @Override
                            public void run() {

                            event.getChannel().sendMessage(lines[1] +" Thao Shall SPeAkeTh!").queue();
                            event.getGuild().removeRoleFromMember(member, role).complete();
                            System.out.println("user un-muted");
                            }
                        },
                        Integer.parseInt(lines[2]) * 1000
                );
            }
            else {
                event.getChannel().sendMessage("Please tell me who to mute (and for how long [OPTIONAL])... if still unsure, please check n!help for more info!").queue();
            }
        }
    }
}
