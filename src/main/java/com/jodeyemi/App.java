package com.jodeyemi;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Scanner;

public class App extends ListenerAdapter
{
    public static void main(String[] args) throws Exception
    {
        // Create a Java Discord Bot
        JDA jda = new JDABuilder(AccountType.BOT).setToken(Ref.token).buildBlocking();

        // A new instance of this class is used as an EventListener
        jda.addEventListener(new App());
    }

    // Runs when a user sends a message to the bot
    @Override
    public void onMessageReceived(MessageReceivedEvent event)
    {
        // Get the user who sent the message
        User objUser = event.getAuthor();

        // Get the message channel
        MessageChannel objMessageChannel = event.getChannel();

        // Get the message itself
        Message objMessage = event.getMessage();

        // Create a Scanner for the message
        Scanner input = new Scanner(objMessage.getContentRaw());

        //List of Commands available to the user
        ArrayList<String> commands = new ArrayList<>();

        // Ping Command
        commands.add("ping");
        if (objMessage.getContentRaw().equalsIgnoreCase(Ref.prefix + "ping"))
        {
            objMessageChannel.sendMessage(objUser.getAsMention() + " Pong!").queue();
        }

        // Add Command
        commands.add("add");
        if (objMessage.getContentRaw().contains(Ref.prefix + "add"))
        {
            add(objUser, objMessageChannel, input);
        }

        // Multiply command
        commands.add("multiply");
        if (objMessage.getContentRaw().contains(Ref.prefix + "multiply"))
        {
            multiply(objUser, objMessageChannel, input);
        }

        // Stats Command
        commands.add("stats");
        if (objMessage.getContentRaw().contains(Ref.prefix + "stats"))
        {
            stats(objUser, objMessageChannel, input);
        }

        // Divide Command
        commands.add("divide");
        if (objMessage.getContentRaw().contains(Ref.prefix + "divide"))
        {
            divide(objUser, objMessageChannel, input);
        }

        // Divide Command
        commands.add("factorial");
        if (objMessage.getContentRaw().contains(Ref.prefix + "factorial"))
        {
            factorial(objUser, objMessageChannel, input);
        }

        // Help Command
        commands.add("commands");
        if (objMessage.getContentRaw().equalsIgnoreCase(Ref.prefix + "commands"))
        {
            // Send a list of each command available
            objMessageChannel.sendMessage(objUser.getAsMention() + commands).queue();
        }
    }

    // Sends message to user with some Fortnite statistics for a player on a specific platform
    private void stats(User objUser, MessageChannel objMessageChannel, Scanner input)
    {
        // Gets rid of the irrelevant text
        while (!input.next().equals(Ref.prefix + "stats"))
            input.next();

        // Strings
        String ign;
        String platform;
        String wins;
        String matches;
        String kills;
        String kpg;
        String winRatio;

        try {

            // Get the users in-game name and platform
            ign = input.next();
            platform = input.next();

            // URL needed to connect to the API
            URL url = new URL("https://api.fortnitetracker.com/v1/profile/" + platform + "/" + ign);
            System.out.println("URL: " + url.toString());

            // Open a connection to the url using the API key
            URLConnection urlConnection = url.openConnection();
            urlConnection.setRequestProperty("TRN-Api-Key", Ref.APIKey);

            // Create an inputStream from the url
            InputStream inputStream = urlConnection.getInputStream();

            // Create a scanner to read the inputStream
            Scanner streamReader = new Scanner(inputStream);

            // Create a StringBuilder
            StringBuilder stringBuilder = new StringBuilder();

            // Add all of the JSON from inputStream to stringBuilder
            while(streamReader.hasNext())
            {
                stringBuilder.append(streamReader.next()).append(" ");
            }

            // Create a JSONObject to parse the JSON stored in stringBuilder
            JSONObject jsonObject = new JSONObject(stringBuilder.toString());

            // Parse JSON using methods in the JSONObject class to get player statistics
            matches = jsonObject.getJSONObject("stats").getJSONObject("p2").getJSONObject("matches").getString("displayValue");
            wins = jsonObject.getJSONObject("stats").getJSONObject("p2").getJSONObject("top1").getString("displayValue");
            winRatio = jsonObject.getJSONObject("stats").getJSONObject("p2").getJSONObject("winRatio").getString("displayValue");
            kills = jsonObject.getJSONObject("stats").getJSONObject("p2").getJSONObject("kills").getString("displayValue");
            kpg = jsonObject.getJSONObject("stats").getJSONObject("p2").getJSONObject("kpg").getString("displayValue");

            // Send message with amount of wins
            objMessageChannel.sendMessage(objUser.getAsMention() +
                    "```javascript\nMatches:    " + matches + "\n"
                    + "Wins:       " + wins + "\n"
                    + "Win Ratio:  " + winRatio + "%\n"
                    + "Kills:      " + kills + "\n"
                    + "KPG:        " + kpg + "```").queue();
        }
        catch (IOException ex)
        {
            objMessageChannel.sendMessage(objUser.getAsMention() + "    An error has occurred").queue();
        }
        catch(JSONException ex)
        {
            objMessageChannel.sendMessage(objUser.getAsMention() + "    Invalid player name").queue();
        }
    }

    // Sends message to user with the sum of the numbers given
    private void add(User objUser, MessageChannel objMessageChannel, Scanner input)
    {
        // Gets rid of text before the command call
        while (!input.next().equals(Ref.prefix + "add"))
            input.next();

        // Used to store the sum
        double sum = 0.0;

        // Adds the value of each integer in the message to sum
        while(input.hasNext())
            sum += Double.valueOf(input.next());

        // Returns the sum of firstNumber and secondNumber
        objMessageChannel.sendMessage(objUser.getAsMention() + "    Sum: " + sum).queue();
    }

    // Sends message to user with the product of the numbers given
    private void multiply(User objUser, MessageChannel objMessageChannel, Scanner input)
    {
        // Gets rid of text before the command call
        while (!input.next().equals(Ref.prefix + "multiply"))
            input.next();

        // Used as a base
        double product = 1.0;

        // Multiplies the value of each double in the message by the product
        while(input.hasNext())
            product *= Double.valueOf(input.next());

        // Returns the product of each number in the method call
        objMessageChannel.sendMessage(objUser.getAsMention() + "    Product: " + product).queue();
    }

    // Sends message to user with the quotient of the numbers given
    private void divide(User objUser, MessageChannel objMessageChannel, Scanner input)
    {
        // Gets rid of text before the command call
        while (!input.next().equals(Ref.prefix + "divide"))
            input.next();

        // Used to store the sum
        double quotient = Double.valueOf(input.next());

        // Adds the value of each integer in the message to sum
        while(input.hasNext())
            quotient /= Double.valueOf(input.next());

        // Returns the sum of firstNumber and secondNumber
        objMessageChannel.sendMessage(objUser.getAsMention() + "    Quotient: " + quotient).queue();
    }

    // Sends message to user with the factorial of the integer given
    private void factorial(User objUser, MessageChannel objMessageChannel, Scanner input)
    {
        // Gets rid of text before the command call
        while (!input.next().equals(Ref.prefix + "factorial"))
            input.next();

        // Returns the sum of firstNumber and secondNumber
        objMessageChannel.sendMessage(objUser.getAsMention() + "    Product: " + factorial(Integer.valueOf(input.next()))).queue();
    }

    // Recursive Factorial method
    private int factorial(int number)
    {
        if (number == 0)
            return 1;
        return number * factorial(number - 1);
    }
}
