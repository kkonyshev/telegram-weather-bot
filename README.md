## Start from console

- Build binaries: `sbt clean stage`
- Run the bot: `./target/universal/stage/bin/telegram-weather-bot -Dbot.api_token=SET_API_TOKEN_HERE`


## Build docker image

- Build image: `sbt docker:publishLocal`
- Run container: ` docker run --rm -e BOT_WEATHER_API_TOKEN=SET_YOUR_BOT_TOKEN_HERE telegram-weather-bot:0.1`

#### Links
- [how to run sbt app in docker](https://medium.com/jeroen-rosenberg/lightweight-docker-containers-for-scala-apps-11b99cf1a666) 

## Usage

- Share a location with the bot
