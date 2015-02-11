(defproject spurious-clojure-example "0.1.0"
  :description "This is an example application that utilises the Spurious Clojure AWS SDK Helper"
  :url "https://github.com/integralist/spurious-clojure-example"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [compojure "1.1.6"]
                 [hiccup "1.0.5"]
                 [ring-server "0.3.1"]
                 [amazonica "0.3.18"]
                 [environ "1.0.0"]
                 [spurious-aws-sdk-helper "0.1.0"]]
  :plugins [[lein-ring "0.8.12"]
            [lein-environ "1.0.0"]]
  :main ^:skip-aot spurious-clojure-example.repl
  :ring {:handler spurious-clojure-example.handler/app
         :init spurious-clojure-example.handler/init
         :destroy spurious-clojure-example.handler/destroy}
  :profiles
  {:uberjar {:aot :all}
   :production
   {:ring
    {:open-browser? false, :stacktraces? false, :auto-reload? false}}
   :dev
   {:dependencies [[ring-mock "0.1.5"]
                   [ring/ring-devel "1.3.1"]]}})
