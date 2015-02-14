(ns spurious-clojure-example.routes.home
  (:use [amazonica.aws.s3])
  (:require [compojure.core :refer :all]
            [environ.core :refer [env]]
            [spurious-clojure-example.views.layout :as layout]))

(if (env :debug)
  (do
    (require '[spurious-aws-sdk-helper.core :as core])
    (require '[spurious-aws-sdk-helper.utils :refer [endpoint cred]])
    (core/configure {:s3  "test-bucket4"
                     :sqs "test-queue4"
                     :ddb (slurp "./resources/config/schema.yaml")})))

(def bucket-path "news-archive/dev/election2014-council_title")

(def content
  (apply str (line-seq
               (clojure.java.io/reader
                 (:object-content
                   (get-object (cred (endpoint :spurious-s3)) :bucket-name "shared" :key bucket-path))))))

(defn home []
  (layout/common [:h1 content]))

(defroutes home-routes
  (GET "/" [] (home)))
