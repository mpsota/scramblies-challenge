(ns scrablies-challenge.api
  (:require [compojure.core :refer [routes ANY defroutes GET]]
            [compojure.route :as route]
            [liberator.core :refer [defresource log! to-location]]
            [liberator.dev :refer [wrap-trace]]
            [clojure.spec.alpha :as s]
            [scrablies-challenge.algorithms :refer [scramble?]]
            [taoensso.timbre :as log]))

;; short spec to verify input strings

(s/def ::valid-string #(re-matches #"[a-z]+" %))

(defn- basic-error-handler [ctx]
  (.getMessage (:exception ctx)))

(defresource index-html
  :handle-exception basic-error-handler
  :available-media-types ["text/html"]
  :allowed-methods [:get]
  :existed? true
  :exists? false
  :moved-permanently? (fn [_ctx] {:location "index.html"}))

(defresource scramble [available-letters word-string]
  :handle-exception basic-error-handler
  :available-media-types ["application/json"]
  :allowed-methods [:get]
  :malformed? (fn [_]
                (or (not (s/valid? ::valid-string available-letters))
                    (not (s/valid? ::valid-string word-string))))
  :handle-ok (fn [_]
               {:result (scramble? available-letters word-string)}))

(defn app-routes [_]
  (routes
    (ANY "/" [] index-html)
    (GET "/api/scramble" [available-letters word-string] (scramble available-letters word-string))
    (route/resources "/")
    (route/not-found "Page not found")))