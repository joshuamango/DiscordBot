package com.jodeyemi;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

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

        //Add Command
        commands.add("add");
        if (objMessage.getContentRaw().contains(Ref.prefix + "add"))
        {
            // Gets rid of the command call
            input.next();

            // Used to store the sum
            double sum = 0.0;

            // Adds the value of each integer in the message to sum
            while(input.hasNext())
            {
                sum += Double.valueOf(input.next());
            }

            // Returns the sum of firstNumber and secondNumber
            objMessageChannel.sendMessage(objUser.getAsMention() + "    Sum: " + sum).queue();
        }

        //Help Command
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
}
