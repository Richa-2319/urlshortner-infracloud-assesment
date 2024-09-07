# urlshortner-infracloud-assesment
simple in-memory url shortner assesment for infracloud

- Prerequisite to run this project 
  - System should have preinstalled Java 17

- How to build this project 
  - Run the command from root dir: `./gradlew clean build`

- How to run this project
  - Once the project is build jar will be created
  - Execute jar using command : `
   java -jar build/libs/shorturl-0.0.1-SNAPSHOT.jar`
    - Alternate way using docker container
    `docker-compose up -d --build`

* API curl samples for testing 
  * For generating short url:
  `curl --location 'http://localhost:8080/url/shorten' \
--header 'Content-Type: application/json' \
--data '{
    "longUrl":"https://www.google.co.in/search?q=india"
}
'
`
  * For checking redirection logic, from the above curl response will be 
  `{
    "longUrl": "https://www.google.co.in/search?q=india",
    "shortUrl": "M1X4XZJ2"
}`
    * Copy the short url and make a url like `http://localhost:8080/url/M1X4XZJ2`. Put the url in browser it will be redirected to original.
  * For getting the top 3 domain for which shorturl generated.
    `curl --location --request GET 'http://localhost:8080/url/top-domains' \
--header 'Content-Type: application/json' \
--data '{
    "longUrl":"https://www.google.co.in/search?q=india"
}
'`
    * Expected Response: `[
    "www.google.co.in (1)"
]`

## Application Image is available on DockerHub
  * Simply use this command to run this application in your local
    `docker run vivekvikash/urlshortner`
