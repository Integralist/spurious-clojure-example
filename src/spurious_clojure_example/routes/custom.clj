(ns spurious-clojure-example.routes.custom
  (:use [amazonica.aws.s3])
  (:require [compojure.core :refer :all]
            [environ.core :refer [env]]
            [spurious-aws-sdk-helper.core :as core]
            [spurious-aws-sdk-helper.utils :refer [endpoint cred]]
            [spurious-clojure-example.views.layout :as layout]))

(when (env :debug)
  (core/configure :app))

(defn credentials []
  (if (env :debug)
    (cred (endpoint :app :spurious-s3))
    {:access-key "foo"
     :secret-key "bar"}))

(defn content [bucket object]
  (apply str (line-seq
               (clojure.java.io/reader
                 (:object-content
                   (get-object (credentials) :bucket-name bucket :key object))))))

(def cache-content (memoize content))

(defn custom []
  (create-bucket (credentials) "made-up-bucket")
  (layout/common (str "<div style='background:yellow'>" (cache-content "foo-bucket" "bar/baz/header") "</div>")))

(defroutes custom-routes
  (GET "/custom" [] (custom)))
