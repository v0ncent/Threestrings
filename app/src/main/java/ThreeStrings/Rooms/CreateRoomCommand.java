//Vincent Banks
//CreateRoomCommand Class
//COPYRIGHT Vincent Banks
package ThreeStrings.Rooms;
import ThreeStrings.Database.MONGODB;
import ThreeStrings.command.CommandContext;
import ThreeStrings.command.ICommand;
import org.bson.Document;

import java.util.Arrays;

public class CreateRoomCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        MONGODB mongo = new MONGODB(); //get mongo class
        long discordId = ctx.getAuthor().getIdLong(); //pull user's discord id as long value
        String[][] defaultRoom = {{"0","0","0","0","0","n"},
                {"0","0","0","0","0","n"},
                {"0","0","0","0","0","n"},
                {"0","0","0","0","0","n"},
                {"0","0","0","0","0","n"}}; //create default room
        Document defaultDoc = new Document("id", discordId).append("room", Arrays.deepToString(defaultRoom)
                .replaceAll("\\[","").replaceAll(",","").replaceAll("\\]","")).append("cash", "100"); // create default document to check if user has already been registered
        if (!mongo.checkIfExists(defaultDoc)) { // if document doest exist in database
            try {
                mongo.insert(defaultDoc);
                ctx.getChannel().sendMessage("Alright I have a spot open for ya!\n" +
                        "I have registered " + ctx.getAuthor().getName() + " into the tavern!").queue();
            } catch (Exception e) {
                ctx.getChannel().sendMessage("I uhh dont feel to good...\n" + e).queue();
                e.printStackTrace();
            }
        } else {
            ctx.getChannel().sendMessage("You already have a spot in the tavern!").queue();
        }
    }
        @Override
        public String getName () {
            //noinspection SpellCheckingInspection
            return "createroom";
        }
        @Override
        public String getHelp () {
            return "Creates a tavern room for you to customize";
        }
    }
