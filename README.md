# spurious-clojure-example

This is an example application that utilises the Spurious Clojure AWS SDK Helper.

## Prerequisites

You will need [Leiningen][1] 1.7.0 or above installed.

You'll also need some content put into the Spurious S3 bucket.

In `src/spurious_clojure_example/routes/home.clj` you'll find a reference to `:bucket-name` and `:key` (S3 object path). Make sure to set the values to the relevant location in the Spurious S3 bucket.

[Here][2] is an example script that utilises Ruby to put content into the Spurious S3 bucket.

[1]: https://github.com/technomancy/leiningen
[2]: https://gist.github.com/Integralist/424899da2fbf9c977932

## Running

To start a web server for the application, run:

    lein ring server

## License

Copyright © 2015 Integralist
