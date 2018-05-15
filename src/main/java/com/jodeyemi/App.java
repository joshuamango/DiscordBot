package com.jodeyemi;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

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

        // Multiply command
        commands.add("multiply");
        if (objMessage.getContentRaw().contains(Ref.prefix + "multiply"))
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

        // Stats Command
        commands.add("stats");
        if (objMessage.getContentRaw().contains(Ref.prefix + "stats"))
        {
            // Gets rid of the irrelevant text
            while (!input.next().equals(Ref.prefix + "stats"))
                input.next();

            // Strings
            String ign;
            String platform;
            String stats = "";

            try {

                // Get the name of the user
                ign = input.next();
                System.out.println("IGN: " + ign);

                // Get the platform the user plays on
                platform = input.next();
                System.out.println("Platform: " + platform);

                // URL needed to connect to the API
                URL url = new URL("https://api.fortnitetracker.com/v1/profile/" + platform + "/" + ign);
                System.out.println("URL: " + url.toString());

                // Create a connection object
                URLConnection urlConnection = url.openConnection();
                urlConnection.setRequestProperty("TRN-Api-Key", "4c6bac44-9183-4505-b77d-4d7cd93756f9");

                InputStream inputStream = urlConnection.getInputStream();
                Scanner streamReader = new Scanner(inputStream);

                while(streamReader.hasNext())
                {
                    if (streamReader.next().contains("value")) {
                        streamReader.next();
                        streamReader.next();
                        objMessageChannel.sendMessage(objUser.getAsMention() + "    TRN: " + streamReader.next()).queue();
                        break;
                    }
                }

            }
            catch (IOException ex)
            {
                stats = "Error: Could not find stats";
            }

        }

        // Divide Command
        commands.add("divide");
        if (objMessage.getContentRaw().contains(Ref.prefix + "divide"))
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

        // Divide Command
        commands.add("factorial");
        if (objMessage.getContentRaw().contains(Ref.prefix + "factorial"))
        {
            // Gets rid of text before the command call
            while (!input.next().equals(Ref.prefix + "factorial"))
                input.next();

            // Returns the sum of firstNumber and secondNumber
            objMessageChannel.sendMessage(objUser.getAsMention() + "    Product: " + factorial(Integer.valueOf(input.next()))).queue();
        }

        // Help Command
        commands.add("commands");
        if (objMessage.getContentRaw().equalsIgnoreCase(Ref.prefix + "commands"))
        {
            objMessageChannel.sendMessage(objUser.getAsMention() + commands).queue();
        }
    }

    // Recursive Factorial
    private int factorial(int number)
    {
        if (number == 0)
            return 1;
        return number * factorial(number - 1);
    }


    private String getStats(String ign) throws IOException
    {

        return "";
    }
}
