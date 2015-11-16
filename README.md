# telegram-bot-api
telegram-bot-api is a Java implementation of the [https://core.telegram.org/bots/api](Telegram Bot API). Unlike the Telegram API, which is classless (it doens't have a data type hierarchy), this API very much offers a such hierarchy. Combined with a functional Java 8 MessageHandler format, this API is the easiest way to get fast, stable bot into the world!

Documentation, test cases and more to follow!

In it current state -it works- but probably not very well, and since no automated testing has been setup. Only webhooks are supported to get updates, no long polling, and it requires a proxy server to handle the HTTPS. This will soon change though!
