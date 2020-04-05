(ns scrablies-challenge.ui
  (:require [devtools.core :as devtools]
            [reagent.dom :as rdom]
            [scrablies-challenge.view :as view]))

(devtools/install!)

(defn render []
  (rdom/render [view/index] (js/document.getElementById "app")))

(defn ^:dev/after-load clear-cache-and-render!
  []
  (render))

(defn ^:export start-frontend []
  (render))