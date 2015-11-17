# Java Telegram Bot Api
**Notice** this API is still under development.

### Simple example
```java
public static void main(String[] args) {
    new TelegramBot.Builder(args[0]).
        register(MessageType.COMMAND, this::onCommand).
        build();
}

private static void onCommand(TelegramEvent<TelegramCommandMessage> event, TelgramApi api) {
    TelegramCommandMessage msg = event.getMessage();
    api.sendText(msg.getChat().getId(), "Hello!");
}
```
Execute as `java YourClass <bot token>`

### Introduction

telegram-bot-api is a Java implementation of the [Telegram Bot API](https://core.telegram.org/bots/api). Unlike the Telegram API, which is classless (it doens't have a data type hierarchy), this API very much offers a such hierarchy. Combined with a functional Java 8 MessageHandler format, this API is the easiest way to get fast, stable bot into the world!

Documentation, test cases and more to follow!

In it current state -it works- but probably not very well, and since no automated testing has been setup. Webhooks and long polling are supported, but webhooks requires a proxy server to handle the HTTPS atm. This will soon change though!
