# spurious-clojure-example

This is an example application that utilises the [Spurious Clojure AWS SDK Helper](https://github.com/Integralist/spurious-clojure-aws-sdk-helper).

## Prerequisites

You'll need to have [Spurious][4] running (see project for installation steps).

You'll also need some content put into the Spurious S3 bucket. [Here][3] is an example script that utilises Ruby to put content into the Spurious S3 bucket (I used Ruby instead of Clojure as a way to demonstrate how Spurious works with multiple languages). Simply modify the bucket name and object path to suit your needs.

If you're not using [Docker][1], then you'll need [Leiningen][2] 1.7.0 or above installed.

[1]: https://www.docker.com/
[2]: https://github.com/technomancy/leiningen
[3]: https://gist.github.com/Integralist/424899da2fbf9c977932
[4]: https://github.com/spurious-io/spurious

## Running

### Standard Web App

To start a web server for the application, run:

    lein ring server

### Docker

If you want to use the application from inside a Docker container you'll need to modify the application code for `src/spurious_clojure_example/routes/home.clj` to use `:docker` instead of `:app`.

#### Building the Docker image

To build the Docker image from the provided `Dockerfile`, execute the following command:

```bash
docker build -t spurious-clojure-example .
```

> Note: the `Dockerfile` has been set-up in such a way as to cache dependencies and to only download them if they change; but I have noticed issues when testing libraries locally, so you might want to add the `--no-cache` flag (but be careful, you'll end up running out of disk space eventually)

#### Running the Docker container

> Note: the `--env` flag is for testing purposes only  
> also change `--publish` flag to your own preference

Multi-line version (for readability)...

```bash
docker run -it --rm \
  --name spurious-app \
  --env DEBUG=true \
  --publish 4000:8080 \
  --link spurious-s3:s3.spurious.localhost \
  --link spurious-sqs:sqs.spurious.localhost \
  --link spurious-dynamo:dynamodb.spurious.localhost \
  spurious-clojure-example
```

One liner...

```bash
docker run -it --rm --name spurious-app --env DEBUG=true --publish 4000:8080 --link spurious-s3:s3.spurious.localhost --link spurious-sqs:sqs.spurious.localhost --link spurious-dynamo:dynamodb.spurious.localhost spurious-clojure-example
```

#### Testing the Docker container

I wanted to test the [spurious-clojure-aws-sdk-helper](https://github.com/Integralist/spurious-clojure-aws-sdk-helper) before releasing it officially to the public Clojars repository.

To do this I ran `lein install` from that code repo - which installs it locally to your `~/.m2` cache. Now I'm able to add this cache directory into my Docker container to use instead of the actual Clojars endpoint. To do this follow these steps:

- Add `ADD .m2/repository /root/.m2/repository` to the `Dockerfile`
- Run `rm -rf .m2` to remove the directory from this current repo (in case you followed these steps previously)
- Run `cp -r ~/.m2 ./` (to get a fresh copy of your lein cache into this repo)
- Run `docker build -t spurious-clojure-example .` to build a new image with the fresh cache
- Run the container as before

#### Messing around with the official Clojure Docker image

It can be useful to jump inside a running Clojure based Docker container by using the following command. For example, I needed to verify the location of the lein cache directory within a non-mac environment):

```bash
docker run -it -v /path/to/your/app:/app clojure bash
```

## Notes

The following notes all refer to the `spurious-clojure-example.routes.home` namespace...

1. You'll notice that the helper is loaded within the `ns` macros rather than dynamically loaded at runtime. This is because the application (when deployed) is compiled into a Jar file with all dependences included; this means that there is no point doing a dynamic `require` of a library when in dev/debug mode.

2. There are two keynames `:bucket-name` and `:key` (which is an S3 object path). Make sure to set the values to an appropriate location which matches your own (Spurious/Live) S3 objects.

3. The `credentials` function is only to demonstrate the principle of using a basic `if` condition to switch between dev/debug and live credentials. Obviously (well, it probably should be obvious) storing your access keys in your code is a bad idea and they should be referenced via some external mechanism (such as environment variables).

## License

Copyright © 2015 Integralist
