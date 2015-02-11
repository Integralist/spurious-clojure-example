(ns spurious-clojure-example.routes.home
  (:use [amazonica.aws.s3])
  (:require [compojure.core :refer :all]
            [spurious-clojure-example.views.layout :as layout]))

(if (System/getenv "DEBUG")
  (do
    (require '[spurious-aws-sdk-helper.utils :refer [credentials]])
    (require '[spurious-aws-sdk-helper.core :as core])
    (core/configure
      {:s3  "a"}
      {:sqs "b"}
      {:ddb (slurp "./resources/config/schema.yaml")})))

; (def cred (assoc credentials :endpoint "s3.spurious.localhost:49154"))

; (def content (apply str (line-seq
;               (clojure.java.io/reader
;                 (:object-content
;                   (get-object
;                     cred
;                     :bucket-name "shared"
;                     :key "news-archive/dev/election2014-council_title"))))))
(defn home []
  (layout/common [:h1 "hai"]))

(defroutes home-routes
  (GET "/" [] (home)))
