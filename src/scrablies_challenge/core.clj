(ns scrablies-challenge.core
  (:require [taoensso.timbre :as log :refer [debug]]))

(defn- reduce-word-string
  "Cross off available letter (taken from `available-letters`) in `word`. Expected result is empty map if all letters in word are in the available-letters, otherwise the leftovers are returned."
  [available-letters word-string]
  (reduce (fn [processed-map letter]
            (let [result (case (get processed-map letter)
                           nil processed-map
                           1 (dissoc processed-map letter)
                           (update-in processed-map [letter] dec))]
              (if (empty? result)
                (reduced result)
                result)))
          (frequencies word-string) available-letters))

(defn scramble? [available-letters word-string]
  "Runs reduce-word-string and parse the output - empty map success, non-empty map means that word cannot be created from the available letters"
  (if (empty? (reduce-word-string available-letters word-string))
    true
    false))